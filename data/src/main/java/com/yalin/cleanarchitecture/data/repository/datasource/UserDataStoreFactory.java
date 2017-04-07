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

import android.content.Context;

import com.yalin.cleanarchitecture.data.cache.UserCache;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 *
 * @author jinyalin
 * @since 2017/4/7.
 */
@Singleton
public class UserDataStoreFactory {
    private final Context context;
    private final UserCache userCache;

    @Inject
    public UserDataStoreFactory(Context context, UserCache userCache) {
        this.context = context;
        this.userCache = userCache;
    }

    /**
     * Create {@link UserDataStore} from a user id.
     */
    public UserDataStore create(int userId) {
        UserDataStore userDataStore;
        if (!userCache.isExpired() && userCache.isCached(userId)) {
            userDataStore = new DiskUserDataStore(userCache);
        } else {
            userDataStore = createCloudDataStore();
        }
        return userDataStore;
    }

    /**
     * Create {@link UserDataStore} to retrieve data from the Cloud.
     */
    public UserDataStore createCloudDataStore() {
        return new CloudUserDataStore();
    }
}
