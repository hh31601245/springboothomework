package cn.edu.zucc.domain.dao;

import cn.edu.zucc.domain.entity.Files;
import cn.edu.zucc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository  extends JpaRepository<Files,Long> {

    @Query(value="select * from Files f where f.Uid=:uid",nativeQuery = true)
    List<Files> findByUid(@Param("uid") Long uid);

    @Query(value="from Files f where f.Uid=:uid and f.Fname=:fname")
    Files findByUidAndFname(@Param("uid") Long uid,@Param("fname") String fname);

}
