package com.tzs.antique.common.service.impl;

import com.tzs.antique.common.service.CacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "zklj")
public class CacheServiceImpl implements CacheService {

    @Override
    @Cacheable(key = "#key")
    public Object add(String key, Object value) {
        return value;
    }

    @Override
    @CachePut(key = "#key")
    public Object update(String key, Object value) {
        return value;
    }

    @Override
    @CacheEvict(key = "#key")
    public void del(String key) {
        System.out.println("删除缓存key:"+key);
    }

    @Override
    @Cacheable(key = "#key")
    public Object find(String key) {
        return -1;
    }
}
