package cn.edu.zucc.service;

import cn.edu.zucc.StudioDemoApplicationTests;
import cn.edu.zucc.domain.entity.User;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
//import org.hamcrest.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class UserServiceTest extends StudioDemoApplicationTests{
    @Autowired
    UserService userService;
    @Test
    public void a/*create*/()
    {
        User user1=new User();
        user1.setId(1L);
        user1.setName("HH");
        user1.setAge(10);
        User user2=new User();
        user2.setId(2L);
        user2.setName("Alice");
        user2.setAge(10);
        userService.create(user1);
        userService.create(user2);
        Assert.assertThat(user1.getId(),is(1L));
        Assert.assertThat(user2.getId(),is(2L));
    }
    @Test
    public void b/*update*/()
    {
        User user1=new User();
        user1.setId(1L);
        user1.setName("HH");
        user1.setAge(11);
        userService.update(user1);
        User user2=userService.getUser(1L);
        Assert.assertThat(user1.getAge(),is(user2.getAge()));
    }
    @Test
    public void c/*3select*/()
    {
        User user2= userService.getUser(1L);
        Assert.assertThat(user2.getId(),is(1L));
    }

    @Test
    public void d/*test004getAllUser*/()
    {
        List<User> userlist1=userService.getAllUsers();
        User user1=userlist1.get(0);
        User user2=userlist1.get(1);
        Assert.assertThat(user1.getName(),is("HH"));
        Assert.assertThat(user2.getName(),is("Alice"));
    }

    @Test
    public void e/*test005delete*/()
    {
        User user2=new User();
        user2.setId(2L);
        user2.setName("Alice");
        user2.setAge(10);
        userService.deleteById(2L);
        List<User> userlist=userService.getAllUsers();
        Assert.assertTrue(!userlist.contains(user2));

    }
    @Test
    public void f/*delete*/()
    {
        userService.deleteAllUser();
        List<User> userlist2=new ArrayList<>();
        Assert.assertEquals(userService.getAllUsers(),userlist2);
    }

}
