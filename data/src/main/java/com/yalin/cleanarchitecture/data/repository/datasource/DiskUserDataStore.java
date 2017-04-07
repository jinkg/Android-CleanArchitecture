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

package com.yalin.cleanarchitecture.data.repository.datasource;

import com.yalin.cleanarchitecture.data.cache.UserCache;
import com.yalin.cleanarchitecture.data.entity.UserEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link UserDataStore} implementation based on file system data store.
 *
 * @author jinyalin
 * @since 2017/4/7.
 */
class DiskUserDataStore implements UserDataStore {
    private final UserCache userCache;

    DiskUserDataStore(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<UserEntity> userEntityDetails(int userId) {
        return Observable.create(emitter -> {
            UserEntity userEntity = new UserEntity(userId);
            userEntity.setFullName("Full Name " + userId);
            emitter.onNext(userEntity);
            emitter.onComplete();
        });
    }
}
