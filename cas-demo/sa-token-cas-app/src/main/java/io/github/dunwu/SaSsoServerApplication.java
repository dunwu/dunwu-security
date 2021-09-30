package io.github.dunwu;

import io.github.dunwu.module.sys.service.LogService;
import io.github.dunwu.tool.web.log.LogStorage;
import io.github.dunwu.tool.web.log.aspect.AppLogAspect;
import io.github.dunwu.tool.web.security.SecurityService;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 应用启动入口 开启审计功能
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-30
 */
@EnableWebMvc
@EnableCaching
@EnableAsync
@RestController
@Api(hidden = true)
@SpringBootApplication(scanBasePackages = "io.github.dunwu")
@EnableTransactionManagement
@MapperScan("io.github.dunwu.module.*.dao.mapper")
public class SaSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaSsoServerApplication.class, args);
        System.out.println("\nSa-Token-SSO 认证中心启动成功");
    }

    @Bean
    public AppLogAspect appLogAspect(LogStorage logStorage, SecurityService securityService) {
        return new AppLogAspect(logStorage, securityService);
    }

}
