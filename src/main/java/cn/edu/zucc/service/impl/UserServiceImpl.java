package cn.edu.zucc.service.impl;

import cn.edu.zucc.domain.dao.UserREpository;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserREpository userRepository;

    @Override
    @CachePut(value="user",key="#user.id")  //创建用户
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    @CachePut(value="user",key="#user.id")  //更改用户
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    @CacheEvict(value="user",key="#id")  //删除指定用户
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() { //获取所有用户列表

        return userRepository.findAll();
    }

    @Override
    @Cacheable(value="user",key="#id")  //通过id获取单个用户
    public User getUser(long id) {
        return userRepository.findById(id).get();
    }

    @Override      //通过name获取用户
    public List<User> getUser(String name) {
        return userRepository.findByName(name);
    }

    @Override  //删除所有用户
    public void deleteAllUser() {
        List<User> a=this.getAllUsers();
        for(int i=0;i<a.size();i++)
        {
            this.deleteById(a.get(i).getId());
        }
    }

    @Override   //通过name,password获取用户
    public User getUser(String name, String password) {

        return userRepository.findByNameAndPassword(name,password);
    }

    @Override  //获取一个用户
    @Cacheable(value="user",key="#name")
    public User getOneUser(String name) {
        return userRepository.findByName(name).get(0);
        //return null;
    }

    @Override  //获取管理员列表
    public List<User> getAdmin() {
        return userRepository.findAdmin("admin");
    }
}
