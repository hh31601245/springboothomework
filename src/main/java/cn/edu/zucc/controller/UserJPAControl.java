package cn.edu.zucc.controller;

import cn.edu.zucc.common.*;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.domain.dao.UserREpository;
import cn.edu.zucc.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

//import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value="/users3")
public class UserJPAControl {
    //static Map<Long , User> users= Collections.synchronizedMap(new HashMap<Long, User>());
    /*@Autowired
    UserREpository service;*/
    @Autowired
    UserServiceImpl service;
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/",method= RequestMethod.GET)
    public R<List<User>> getUserList()
    {
        //处理/users3/的get 请求，用来获取用户列表
       // return R.data(service.findAll());
        return R.data(service.getAllUsers());
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/",method=RequestMethod.POST)
    public R<String> postUser(@Valid @ModelAttribute User user)
    {
        //处理/users3/的post请求，用来创建用户，
        //除了@ModelAttribute绑定参数，还可以通过@RequestParam从页面中传递参数
        //service.save(user);
        /*if(StringUtils.isEmpty(user.getName()))
        {
            throw new NullPointerException("用户不能为空");
        }*/
       /* user.setSalt(HashKIt.generateSalt(8));
        user.setPassword(HashKIt.md5(user.getPassword()+user.getSalt()));*/
        user.setSalt(HashKIt.generateSalt(8));
        user.setPassword(HashKIt.sha256(user.getPassword()+user.getSalt()));
       service.create(user);
        return R.success("success");
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public R<User> getUser(@PathVariable Long id)
    {
        //处理/users3/{id}的get请求，用来获取url中id值的User信息
        //url中的id可通过@PathVariable绑定到函数的参数中
        return R.data(service.getUser(id));//R.data(service.findById(id).get());
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/Fuzzy/{name}",method=RequestMethod.GET)
    public R<List<User>> getFuzzyUser(@PathVariable String name)
    {
        //处理/users3/{name1}的get 请求，根据name模糊查询
        return R.data(service.getUser(name));//R.data(service.findByName(name));
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public R<String> putUser(@PathVariable Long id,@ModelAttribute User user)
    {
        //处理//users3/{id}的put请求，用来更新User信息
        user.setId(id);
        //service.save(user);
       /* if(StringUtils.isEmpty(user.getName()))
        {
            throw new NullPointerException("用户名不能为空");
        }*/
        service.update(user);
        return R.success("success");
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public R<String> deleteUser(@PathVariable Long id)
    {
        // 处理/users3/{id}的delete请求，用来删除user
        service.deleteById(id);
        return R.success("success");
    }
    @UserLoginToken
    @Role("admin")
    @RequestMapping(value="/",method=RequestMethod.DELETE)
    public R<String> deleteAllUser()  //删除全部用户
    {
        //service.deleteAll();
        service.deleteAllUser();
        return R.success("success");
    }
    @PassToken
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public R<String> loginUser(@RequestParam String name,@RequestParam String password)  //登录
    {
        //User user=service.findByNameAndPassword(name,password);
        User user=service.getOneUser(name);
        if(user==null)
        {
            return R.fail("用户不存在");
        }
       // return user.getPassword().equals(HashKIt.md5(password+user.getSalt()))?R.Loginsuccess(user):R.fail("密码错误");
        return user.getPassword().equals(HashKIt.sha256(password+user.getSalt()))?R.Loginsuccess(user):R.fail("密码错误");
    }
    @UserLoginToken
    @RequestMapping(value="/password",method=RequestMethod.PUT)
    public R<String> ModifyPassword(@RequestParam String name,@RequestParam String newpassword1,@RequestParam String newpassword2)//修改密码
    {
        User user=service.getOneUser(name);
        if(user==null)
        {
            return R.fail("用户不存在");
        }
        if(!newpassword1.equals(newpassword2))
        {
            return R.fail("两次的密码不同");
        }
       // user.setPassword(HashKIt.md5(newpassword1+user.getSalt()));
        user.setPassword(HashKIt.sha256(newpassword1+user.getSalt()));
        service.update(user);
        return R.success("修改成功");
    }
   /* @RequestMapping(value="/yearincome",method = RequestMethod.POST)
    public R<List<Map<String,Object>>> yearincome()
    {
        List<Map<String,Object>> list=new ArrayList<User>(service.getAllUsers());
        list.stream().forEach(user -> user.setYearincome(user.getMonthincome()*12));
        return R.data(list);
    }*/
}


