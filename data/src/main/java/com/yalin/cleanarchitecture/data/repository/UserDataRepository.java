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

package com.yalin.cleanarchitecture.data.repository;

import com.yalin.cleanarchitecture.data.entity.mapper.UserEntityDataMapper;
import com.yalin.cleanarchitecture.data.repository.datasource.UserDataStore;
import com.yalin.cleanarchitecture.data.repository.datasource.UserDataStoreFactory;
import com.yalin.cleanarchitecture.domain.User;
import com.yalin.cleanarchitecture.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link UserRepository} for retrieving user data.
 *
 * @author jinyalin
 * @since 2017/4/7.
 */
@Singleton
public class UserDataRepository implements UserRepository {
    private final UserDataStoreFactory userDataStoreFactory;
    private final UserEntityDataMapper userEntityDataMapper;

    @Inject
    UserDataRepository(UserDataStoreFactory userDataStoreFactory,
                       UserEntityDataMapper userEntityDataMapper) {
        this.userDataStoreFactory = userDataStoreFactory;
        this.userEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<List<User>> users() {
        final UserDataStore userDataStore = userDataStoreFactory.createCloudDataStore();
        return userDataStore.userEntityList().map(userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> user(int userId) {
        final UserDataStore userDataStore = userDataStoreFactory.create(userId);
        return userDataStore.userEntityDetails(userId).map(userEntityDataMapper::transform);
    }
}
