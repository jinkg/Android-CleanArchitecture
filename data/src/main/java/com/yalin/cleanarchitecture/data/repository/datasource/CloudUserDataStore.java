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

import com.yalin.cleanarchitecture.data.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;

/**
 * @author jinyalin
 * @since 2017/4/7.
 */

class CloudUserDataStore implements UserDataStore {
    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return Observable.create(emitter -> {
            Thread.sleep(2000);
            int random = new Random().nextInt(3);
            if (random % 3 == 0) {
                emitter.onError(new Exception("Network error."));
            } else {
                emitter.onNext(getMockData());
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<UserEntity> userEntityDetails(int userId) {
        return Observable.create(emitter -> {
            Thread.sleep(1500);
            UserEntity userEntity = new UserEntity(userId);
            userEntity.setFullName("Full Name " + userId);
            emitter.onNext(userEntity);
            emitter.onComplete();
        });
    }

    private List<UserEntity> getMockData() {
        List<UserEntity> userEntityList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            UserEntity userEntity = new UserEntity(100000 + i);
            userEntity.setFullName("Full Name " + i);
            userEntityList.add(userEntity);
        }
        return userEntityList;
    }
}
