package cn.edu.zucc.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Entity
public class Uleave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Lid;
    Long Applyuid;
    @NotNull(message = "开始时间不能为空")
    Date Starttime;
    @NotNull(message = "结束时间不能为空")
    Date Endtime;
    Long Sumday;
    //@NotEmpty(message = "审批人id不能为空")
    Long Approveuid;
    @NotEmpty(message = "类型不能为空")
    String Type;
    @NotEmpty(message = "理由不能为空")
    String Reason;
    //@NotEmpty(message = "状态不能为空")
    String Lstatus;
    //@NotEmpty(message = "批语不能为空")
    String Remark;

    public Long getLid() {
        return Lid;
    }

    public void setLid(Long lid) {
        Lid = lid;
    }

    public Long getApplyuid() {
        return Applyuid;
    }

    public void setApplyuid(Long applyuid) {
        Applyuid = applyuid;
    }

    public Date getStarttime() {
        return Starttime;
    }

    public void setStarttime(Date starttime) {
        Starttime = starttime;
    }

    public Date getEndtime() {
        return Endtime;
    }

    public void setEndtime(Date endtime) {
        Endtime = endtime;
    }

    public Long getSumday() {
        return Sumday;
    }

    public void setSumday(Long sumday) {
        Sumday = sumday;
    }

    public Long getApproveuid() {
        return Approveuid;
    }

    public void setApproveuid(Long approveuid) {
        Approveuid = approveuid;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getLstatus() {
        return Lstatus;
    }

    public void setLstatus(String Lstatus) {
        this.Lstatus = Lstatus;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
