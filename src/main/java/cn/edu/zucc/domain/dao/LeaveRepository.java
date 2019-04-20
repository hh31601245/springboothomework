package cn.edu.zucc.domain.dao;

import cn.edu.zucc.domain.entity.Uleave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Uleave,Long> {
    @Query(value="select * from Uleave f where f.Applyuid=:applyuid",nativeQuery = true)
    List<Uleave> findByApplyUid(@Param("applyuid") Long applyuid);

    @Query(value="select * from Uleave f where f.Lstatus=:lstatus",nativeQuery = true)
    List<Uleave> findByStatus(@Param("lstatus") String lstatus);

}
