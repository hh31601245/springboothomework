package cn.edu.zucc.domain.dao;

import cn.edu.zucc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserREpository extends JpaRepository<User,Long> {
    @Query(value = "select * from User u where u.name Like CONCAT('%',:name1,'%')",nativeQuery = true)
    List<User> findByName(@Param("name1") String name1);

    @Query("from User u where u.name=:name")
    User findUser( @Param("name") String name);

    @Query("from User u where u.name=:name and u.password=:password")
    User findByNameAndPassword(@Param("name") String name,@Param("password") String password);

    @Query("from User u where u.role=:role")
    List<User> findAdmin( @Param("role") String role);
}
