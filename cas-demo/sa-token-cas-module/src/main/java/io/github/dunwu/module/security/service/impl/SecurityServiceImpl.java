package io.github.dunwu.module.security.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.module.security.constant.enums.DataScopeEnum;
import io.github.dunwu.module.security.entity.vo.UserVo;
import io.github.dunwu.module.security.service.SecurityService;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.service.UserService;
import io.github.dunwu.tool.core.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Spring Security 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    @Override
    public Long getCurrentUserId() {
        UserVo userVo = getCurrentUser();
        return new JSONObject(new JSONObject(userVo).get("user")).get("id", Long.class);
    }

    /**
     * 获取当前用户名称
     */
    @Override
    public String getCurrentUsername() {
        UserVo userDetails = getDefaultUserDetails();
        return userDetails.getUser().getUsername();
    }

    private UserVo getCurrentUser() throws AuthException {
        return getDefaultUserDetails();
    }

    private UserVo getDefaultUserDetails() {
        long userId = StpUtil.getLoginIdAsLong();
        // String token = StpUtil.getTokenValueByLoginId(userId);
        UserDto userDto = userService.pojoById(userId);
        UserVo userVo = new UserVo();
        userVo.setUser(userDto);
        return userVo;
    }

    /**
     * 获取数据权限级别
     *
     * @return 级别
     */
    @Override
    public String getDataScopeType() {
        List<Long> dataScopes = getCurrentUserDataScope();
        if (dataScopes.size() != 0) {
            return "";
        }
        return DataScopeEnum.ALL.getValue();
    }

    /**
     * 获取当前用户的数据权限
     *
     * @return /
     */
    @Override
    public List<Long> getCurrentUserDataScope() {
        UserVo userVo = getCurrentUser();
        JSONArray array = JSONUtil.parseArray(new JSONObject(userVo).get("dataScopes"));
        return JSONUtil.toList(array, Long.class);
    }

}
