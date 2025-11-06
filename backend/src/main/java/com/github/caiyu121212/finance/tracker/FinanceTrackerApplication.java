package com.github.caiyu121212.finance.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *财务追踪系统主应用类
 *Spring Boot 应用的启动入口
*/

@SpringBootApplication
@EnableScheduling  //启动定时任务支持
@CrossOrigin(origins = "http://localhost:3000") //允许前端跨域访问

public class FinanceTrackerApplication{
    /**
    *应用主入口方法
    *@param args 命令行参数
    */
   public static void main(String[] args){
    // 启动Spring Boot 应用
        SpringApplication.run(FinanceTrackerApplication.class, args);

    //打印启动成功信息
        System.out.println("===========================");
        System.out.println("Finance Tracker 2.0启动成功！");
        System.out.println("服务地址：http：//localhost:8080");
        System.out.println("API文档：https://localhost:8080/swagger-ui.html");

   }


}
