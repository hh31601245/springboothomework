package cn.edu.zucc.service;

import cn.edu.zucc.StudioDemoApplicationTests;
import cn.edu.zucc.domain.entity.Uleave;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class LeaveServiceTest   extends StudioDemoApplicationTests {
    @Autowired
    LeaveService leaveService;
    @Test
    public void a()//askForLeave(Uleave uleave)
    {
        Uleave u=new Uleave();
        u.setLid(10L);
        u.setLstatus("发起");
        u.setApplyuid(13L);
        Date time= new java.sql.Date(new java.util.Date().getTime());
        u.setStarttime(time);
        u.setEndtime(time);
        u.setReason("sfsdf");
        u.setType("事假");
        leaveService.askForLeave(u);
        Assert.assertThat(u.getLid(),is(10L));
    }
    public void b()//approveLeave(Long lid,String remark,String lstatus)
    {
        Long lid=10L;
        String remark="sfsfs";
        String lstatus="审核通过";
        leaveService.approveLeave(lid,remark,lstatus);
        Assert.assertThat(lid,is(10L));
    }
    public void c()//List<Uleave> showOneUserAllLeave(Long applyuid)
    {
        Long applyuid=13L;
        List<Uleave> l=leaveService.showOneUserAllLeave(13L);
        Uleave ul=l.get(0);
        Assert.assertThat(ul.getLid(),is(7L));
    }
    public void d()//List<Uleave> showAllAskForLeave()
    {
        List<Uleave> l=leaveService.showAllAskForLeave();
        Uleave ul=l.get(0);
        Assert.assertThat(ul.getLid(),is(6L));
    }
    public void e()//List<Uleave> showAllUserAllLeave()
    {
        List<Uleave> l=leaveService.showAllUserAllLeave();
        Uleave ul=l.get(0);
        Assert.assertThat(ul.getLid(),is(6L));
    }
}
