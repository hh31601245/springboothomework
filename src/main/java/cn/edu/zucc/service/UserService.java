package cn.edu.zucc.service;

import cn.edu.zucc.domain.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 新增一个用户
     * @param user
     */
    void create(User user);
    /**
     * 根据id修改用户
     * @param user
     */
    void update(User user);
    /**
     * 根据id删除用户
     * @param id
     */
    void deleteById(long id);
    /**
     * 获取用户列表
      */
    List<User> getAllUsers();
    /**
     * 获取某个用户
     * @param id
     */
    User getUser(long id);
    /**
     * 根据姓名模糊查询
     * @param name
     */
    List<User> getUser(String name);
    /**
     * 删除所有的用户
     */
    void deleteAllUser();
    /**
     * 通过用户名密码获取某个用户
     * @param name
     * @param password
     */
    User getUser(String name,String password);
    /**
     * 通过用户名获取一个用户
     * @param  name
     */
    User getOneUser(String name);
    /**
     * 获取管理员
     */
    List<User> getAdmin();
}
