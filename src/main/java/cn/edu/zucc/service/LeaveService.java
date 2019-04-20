package cn.edu.zucc.service;

import cn.edu.zucc.domain.entity.Uleave;

import java.util.List;

public interface LeaveService {
    /**
     * 发起请假
     * @param leave
     */
    void askForLeave(Uleave leave);
    /**
     * 审批
     * @param lid
     * @param remark
     * @param lstatus
     */
    void approveLeave(Long lid,String remark,String lstatus);
    /**
     * 显示当前用户所有请假单
     * @param applyuid
     */
    List<Uleave> showOneUserAllLeave(Long applyuid);
    /**
     * 显示所有发起请假列表
     */
    List<Uleave> showAllAskForLeave();
    /**
     * 显示所有用户所有请假
     */
    List<Uleave> showAllUserAllLeave();

}
