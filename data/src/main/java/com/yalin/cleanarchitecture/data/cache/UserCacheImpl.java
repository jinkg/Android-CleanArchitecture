/*
 * Copyright 2017 Yalin Jin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yalin.cleanarchitecture.data.cache;

import com.yalin.cleanarchitecture.data.entity.UserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link UserCache} implementation.
 *
 * @author jinyalin
 * @since 2017/4/7.
 */
@Singleton
public class UserCacheImpl implements UserCache {
    @Inject
    public UserCacheImpl() {
    }

    @Override
    public Observable<UserEntity> get(int userId) {
        return null;
    }

    @Override
    public void put(UserEntity userEntity) {

    }

    @Override
    public boolean isCached(int userId) {
        return false;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void evictAll() {

    }
}
