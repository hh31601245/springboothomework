package cn.edu.zucc.service.impl;

import cn.edu.zucc.domain.dao.LeaveRepository;
import cn.edu.zucc.domain.dao.UserREpository;
import cn.edu.zucc.domain.entity.Uleave;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LeaveServicelmpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private UserREpository userREpository;
    @Override  //申请请假
    //@CachePut(value="uleave",key="#Uleave.Lid")
    public void askForLeave(Uleave uleave) {
        leaveRepository.save(uleave);
    }

    @Override  //审批请假
    //@CachePut(value="uleave",key="#Uleave.Lid")
    public void approveLeave(Long lid,String remark,String lstatus) {
        User u=userREpository.findAdmin("admin").get(0);

        Uleave l=leaveRepository.getOne(lid);
        l.setApproveuid(u.getId());
        l.setLstatus(lstatus);
        l.setRemark(remark);
        leaveRepository.save(l);
    }

    @Override  //显示当前用户的所有请假请求
    //@CachePut(value="uleave",key="#applyuid")
    public List<Uleave> showOneUserAllLeave(Long applyuid)
    {
        ;
        return leaveRepository.findByApplyUid(applyuid);
    }

    @Override  //显示所有未审批的请假请求
    public List<Uleave> showAllAskForLeave() {

        return leaveRepository.findByStatus("发起");
    }

    @Override  //显示所有的请假请求
    public List<Uleave> showAllUserAllLeave() {

        return leaveRepository.findAll();
    }
}
