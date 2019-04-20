package cn.edu.zucc.common;

import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class AuthenticationInterceptor implements HandlerInterceptor { //是否登录的拦截器
    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object object) throws Exception
    {
        String token =httpServletRequest.getHeader("token");//从http请求中取出token
        //如果不是映射到方法，直接通过
        if(!(object instanceof HandlerMethod))
        {

            return true;
        }
        HandlerMethod handlerMEthod=(HandlerMethod)object;
        Method method=handlerMEthod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if(method.isAnnotationPresent(PassToken.class))
        {
            PassToken passToken=method.getAnnotation(PassToken.class);
            if(passToken.required())
            {

                return true;
            }
        }
        //检查有没有需要用户权限的注释
        if(method.isAnnotationPresent(UserLoginToken.class))
        {
            //执行认证
            if(token==null)
            {
                throw new InvalidClientException("无token,请重新登录");
            }
            //获取token中的user,id
            Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
            long userId=jwt.getBody().get("id",Long.class); //这里的Long一定要大写
            //String role=jwt.getBody().get("role",String.class);
            User user=userService.getUser(userId);
            if(user==null)
            {
                throw new Exception("用户不存在，请重新登录");
            }
            if(jwt.getBody().get("exp", Date.class).before(new Date()))
            {
                throw new Exception("登录超时，请重新登录");
            }
            if(method.isAnnotationPresent(Role.class))  //判断登录后再判断权限
            {
                this.userRoleValidate(httpServletRequest,method);
            }

            return true;
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, ModelAndView modelAndView) throws Exception
    {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object,Exception e)throws Exception
    {

    }
    private void userRoleValidate(HttpServletRequest request,Method method) throws Exception //权限判断
    {
        String token =request.getHeader("token");//从http请求中取出token
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        String userrole=jwt.getBody().get("role",String.class);
        Role role=method.getAnnotation(Role.class);
        if(role==null)
        {
            throw new Exception("未配置自定义注解");
        }
        String funcCode=role.value();
        if(!funcCode.equals(userrole))
        {
            throw new Exception("权限不足");
        }

    }
}
