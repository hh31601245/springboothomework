package cn.edu.zucc.controller;

import cn.edu.zucc.common.R;
import cn.edu.zucc.common.Role;
import cn.edu.zucc.common.UserLoginToken;
import cn.edu.zucc.domain.entity.Uleave;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.impl.LeaveServicelmpl;
import cn.edu.zucc.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/leave")
public class LeaveControl {
    @Autowired
    LeaveServicelmpl leaveServicelmpl;
    @Autowired
    UserServiceImpl userService;

    @UserLoginToken
    @RequestMapping(value="/",method= RequestMethod.POST)
    @ResponseBody
    public R<String> askForLeave(@Valid @ModelAttribute Uleave leave,HttpServletRequest request, HttpServletResponse response)  //申请请假
    {
        Date starttime=leave.getStarttime();
        Date endtime=leave.getEndtime();
        Long day1=(Long)(endtime.getTime()-starttime.getTime())/(1000*3600*24);
        String token =request.getHeader("token");//从http请求中取出token
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        Long userid=jwt.getBody().get("id",Long.class); //这里的Long一定要大写
        leave.setApplyuid(userid);
        leave.setSumday(day1);
        leave.setLstatus("发起");
        leaveServicelmpl.askForLeave(leave);
        return R.success("success");
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/{lid}",method=RequestMethod.PUT)
    public R<String> approveLeave(@PathVariable Long lid,@RequestParam String Remark,@RequestParam String Lstatus) //审批请假
    {


        if(StringUtils.isEmpty(Remark))
        {
            throw new NullPointerException("批语不能为空");
        }

        leaveServicelmpl.approveLeave(lid,Remark,Lstatus);
        return R.success("success");
    }
    @UserLoginToken
    @RequestMapping(value="/leavelist",method=RequestMethod.GET)
    @ResponseBody
    public R<List<Uleave>> showUserLeave(HttpServletRequest request, HttpServletResponse response) //显示当前用户的请假记录
    {
        String token =request.getHeader("token");//从http请求中取出token
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        Long userid=jwt.getBody().get("id",Long.class); //这里的Long一定要大写
        List<Uleave> leavelist=leaveServicelmpl.showOneUserAllLeave(userid);
        return R.data(leavelist);
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/askforleavelist",method=RequestMethod.GET)
    public R<List<Uleave>> showAskForUserLeave() //显示所有未审核记录
    {
        return R.data(leaveServicelmpl.showAllAskForLeave());
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/allleavelist",method=RequestMethod.GET)
    public R<List<Uleave>> showAllUserLeave()//显示所有请假记录
    {
        return R.data(leaveServicelmpl.showAllUserAllLeave());
    }
}
