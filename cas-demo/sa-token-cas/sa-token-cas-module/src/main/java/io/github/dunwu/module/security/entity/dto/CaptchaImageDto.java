package io.github.dunwu.module.security.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录验证码配置信息
 *
 * @author peng.zhang
 * @date 2021-09-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaImageDto {

    private String img;
    private String uuid;

}
