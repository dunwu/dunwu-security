package io.github.dunwu.module.security.config;

import cn.hutool.core.util.EnumUtil;
import io.github.dunwu.module.security.annotation.AnonymousAccess;
import io.github.dunwu.module.security.filter.AuthenticationFilter;
import io.github.dunwu.module.security.handler.JwtAccessDeniedHandler;
import io.github.dunwu.module.security.handler.JwtAuthenticationEntryPoint;
import io.github.dunwu.module.security.util.JwtTokenUtil;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.tool.web.filter.XssFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author Zheng Jie
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class DunwuWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;
    private final DunwuWebSecurityProperties securityProperties;
    private final AuthService authService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        if (!securityProperties.isEnabled()) {
            return;
        }

        if (securityProperties.isAuthEnabled()) {
            // 搜寻匿名标记 url： @AnonymousAccess
            RequestMappingHandlerMapping requestMappingHandlerMapping =
                (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
            Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
            // 获取匿名标记
            Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);

            // 禁用 CSRF
            httpSecurity.csrf().disable();

            if (securityProperties.isCorsEnabled()) {
                // 允许跨域
                httpSecurity.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
            }

            // 授权异常
            httpSecurity.exceptionHandling()
                        .authenticationEntryPoint(authenticationErrorHandler)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        // 防止iframe 造成跨域
                        .and()
                        .headers()
                        .frameOptions()
                        .disable()
                        // 不创建会话
                        .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .authorizeRequests()
                        // 静态资源等等
                        .antMatchers(
                            HttpMethod.GET,
                            "/*.html",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/webSocket/**"
                        )
                        .permitAll()
                        // swagger 文档
                        .antMatchers("/swagger-ui.html")
                        .permitAll()
                        .antMatchers("/swagger-resources/**")
                        .permitAll()
                        .antMatchers("/webjars/**")
                        .permitAll()
                        .antMatchers("/*/api-docs")
                        .permitAll()
                        // 文件
                        .antMatchers("/avatar/**")
                        .permitAll()
                        .antMatchers("/file/**")
                        .permitAll()
                        // 阿里巴巴 druid
                        .antMatchers("/druid/**")
                        .permitAll()
                        // 放行OPTIONS请求
                        .antMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()
                        // 自定义匿名访问所有url放行：允许匿名和带Token访问，细腻化到每个 Request 类型
                        // GET
                        .antMatchers(HttpMethod.GET, HttpMethod.GET.name())
                        .permitAll()
                        // POST
                        .antMatchers(HttpMethod.POST, HttpMethod.POST.name())
                        .permitAll()
                        // PUT
                        .antMatchers(HttpMethod.PUT, HttpMethod.PUT.name())
                        .permitAll()
                        // PATCH
                        .antMatchers(HttpMethod.PATCH, HttpMethod.PATCH.name())
                        .permitAll()
                        // DELETE
                        .antMatchers(HttpMethod.DELETE, HttpMethod.DELETE.name())
                        .permitAll()
                        // 所有请求都需要认证
                        .anyRequest()
                        .authenticated()
                        .and()
                        .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        } else {
            if (securityProperties.isCorsEnabled()) {
                // 禁用 CSRF
                httpSecurity.csrf().disable();
                // 允许跨域
                httpSecurity.addFilter(corsFilter());
            }
        }
    }

    /**
     * 去除 ROLE_ 前缀
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(jwtTokenUtil, securityProperties, authService);
    }

    private Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Map<String, Set<String>> anonymousUrls = new HashMap<>(6);
        Set<String> get = new HashSet<>();
        Set<String> post = new HashSet<>();
        Set<String> put = new HashSet<>();
        Set<String> patch = new HashSet<>();
        Set<String> delete = new HashSet<>();
        Set<String> all = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                List<RequestMethod> requestMethods = new ArrayList<>(
                    infoEntry.getKey().getMethodsCondition().getMethods());
                HttpMethod httpMethod = EnumUtil.fromString(HttpMethod.class, requestMethods.get(0).name(), null);
                switch (httpMethod) {
                    case GET:
                        get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case POST:
                        post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PUT:
                        put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PATCH:
                        patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case DELETE:
                        delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    default:
                        all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                }
            }
        }
        anonymousUrls.put(HttpMethod.GET.name(), get);
        anonymousUrls.put(HttpMethod.POST.name(), get);
        anonymousUrls.put(HttpMethod.PUT.name(), get);
        anonymousUrls.put(HttpMethod.PATCH.name(), get);
        anonymousUrls.put(HttpMethod.DELETE.name(), get);
        anonymousUrls.put("ALL", all);
        return anonymousUrls;
    }

    // ------------------------------------------------------------------------------------
    // 注册过滤器、拦截器
    // ------------------------------------------------------------------------------------

    /**
     * 跨域过滤器
     */
    @Bean
    @ConditionalOnProperty(name = "dunwu.web.security.corsEnabled", havingValue = "true")
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(securityProperties.getCorsPath(), corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * XSS 过滤器
     */
    @Bean
    @ConditionalOnProperty(name = "dunwu.web.security.xssEnabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>(2);
        initParameters.put("excludes", securityProperties.getXssExcludePath());
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

}
