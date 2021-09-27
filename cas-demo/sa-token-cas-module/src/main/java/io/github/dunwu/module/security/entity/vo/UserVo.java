package io.github.dunwu.module.security.entity.vo;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
@Data
public class UserVo {

    private UserDto user;

    private Collection<Long> dataScopes;

    public Set<String> getRoles() {
        if (user == null || CollectionUtil.isEmpty(user.getRoles())) {
            return null;
        }
        Set<String> permissions = new HashSet<>();
        if (CollectionUtil.isNotEmpty(user.getRoles())) {
            for (RoleDto role : user.getRoles()) {
                if (CollectionUtil.isNotEmpty(role.getMenus())) {
                    for (MenuDto menu : role.getMenus()) {
                        permissions.add(menu.getPermission());
                    }
                }
            }
        }

        return permissions;
    }

}
