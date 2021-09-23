package io.github.dunwu.module.security.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.module.security.constant.enums.DataScopeEnum;
import io.github.dunwu.module.security.exception.AuthException;
import io.github.dunwu.tool.web.SpringUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Spring Security 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class SecurityUtil {

    /**
     * 获取当前用户身份信息
     */
    public static UserDetails getCurrentUser() throws AuthException {
        UserDetails userDetails = getDefaultUserDetails();
        UserDetailsService userDetailsService = SpringUtil.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(userDetails.getUsername());
    }

    /**
     * 获取当前用户名称
     */
    public static String getCurrentUsername() {
        UserDetails userDetails = getDefaultUserDetails();
        return userDetails.getUsername();
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        return new JSONObject(new JSONObject(userDetails).get("user")).get("id", Long.class);
    }

    private static UserDetails getDefaultUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthException("当前登录状态过期");
        }
        return (UserDetails) authentication.getPrincipal();
    }
    /**
     * 获取当前用户的数据权限
     *
     * @return /
     */
    public static List<Long> getCurrentUserDataScope() {
        UserDetails userDetails = getCurrentUser();
        JSONArray array = JSONUtil.parseArray(new JSONObject(userDetails).get("dataScopes"));
        return JSONUtil.toList(array, Long.class);
    }

    /**
     * 获取数据权限级别
     *
     * @return 级别
     */
    public static String getDataScopeType() {
        List<Long> dataScopes = getCurrentUserDataScope();
        if (dataScopes.size() != 0) {
            return "";
        }
        return DataScopeEnum.ALL.getValue();
    }
}
