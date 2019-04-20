package cn.edu.zucc.common;

import cn.edu.zucc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SpringTask { //每隔一段时间显示
    @Autowired
    UserService userService;
    @Async
    @Scheduled(cron = "0/1 * * * * *")
    public void scheduled1() throws InterruptedException
    {
        System.out.println(LocalDateTime.now()+"\tbbb的邮箱是"+userService.getOneUser("bbb").getEmail());
    }
}
