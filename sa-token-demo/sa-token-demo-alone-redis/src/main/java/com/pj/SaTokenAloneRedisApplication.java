package com.pj;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sa-Token整合SpringBoot 示例
 *
 * @author kong
 */
@SpringBootApplication
public class SaTokenAloneRedisApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(SaTokenAloneRedisApplication.class, args);
        System.out.println("\n启动成功：Sa-Token配置如下：" + SaManager.getConfig());
    }

}
