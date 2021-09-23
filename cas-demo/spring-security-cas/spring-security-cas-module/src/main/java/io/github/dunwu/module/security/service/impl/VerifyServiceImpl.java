/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.dunwu.module.security.service.impl;

import io.github.dunwu.module.security.service.VerifyService;
import io.github.dunwu.tool.data.redis.RedisHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
@Service
public class VerifyServiceImpl implements VerifyService {

    private final RedisHelper redisHelper;

    public VerifyServiceImpl(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Override
    public void validated(String key, String code) {
        Object value = redisHelper.get(key);
        if (value == null || !value.toString().equals(code)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "无效验证码");
        } else {
            redisHelper.del(key);
        }
    }

}
