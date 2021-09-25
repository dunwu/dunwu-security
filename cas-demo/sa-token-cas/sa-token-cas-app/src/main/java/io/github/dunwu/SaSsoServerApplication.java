package io.github.dunwu;

import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用启动入口 开启审计功能
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-30
 */
@EnableCaching
@EnableAsync
@RestController
@Api(hidden = true)
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("io.github.dunwu.module.*.dao.mapper")
public class SaSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaSsoServerApplication.class, args);
        System.out.println("\nSa-Token-SSO 认证中心启动成功");
    }

}
