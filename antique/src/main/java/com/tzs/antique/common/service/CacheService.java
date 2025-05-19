package com.tzs.antique.common.service;

public interface CacheService {

    Object add(String key, Object value);

    Object update(String key, Object value);

    void del(String key);

    Object find(String key);
}
