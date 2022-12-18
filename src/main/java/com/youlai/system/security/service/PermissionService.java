package com.youlai.system.security.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.youlai.system.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.util.Set;

@Service("pms")
@RequiredArgsConstructor
public class PermissionService {

    private final RedisTemplate redisTemplate;

    public boolean hasPermission(String perm) {

        if (StrUtil.isBlank(perm)) {
            return false;
        }

        if (SecurityUtils.isRoot()) {
            return true;
        }

        Long userId = SecurityUtils.getUserId();

        Set<String> perms = (Set<String>) redisTemplate.opsForValue().get("USER_PERMS:" + userId);

        if (CollectionUtil.isEmpty(perms)) {
            return false;
        }
        boolean hasPermission = perms.stream()
                .anyMatch(item -> PatternMatchUtils.simpleMatch(perm, item));
        return hasPermission;
    }


}
