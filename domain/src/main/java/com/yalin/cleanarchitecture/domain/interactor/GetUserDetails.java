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

package com.yalin.cleanarchitecture.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;
import com.yalin.cleanarchitecture.domain.User;
import com.yalin.cleanarchitecture.domain.executor.PostExecutionThread;
import com.yalin.cleanarchitecture.domain.executor.ThreadExecutor;
import com.yalin.cleanarchitecture.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link User}.
 *
 * @author jinyalin
 * @since 2017/4/7.
 */
public class GetUserDetails extends UseCase<User, GetUserDetails.Params> {

    private UserRepository userRepository;

    @Inject
    GetUserDetails(UserRepository userRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Observable<User> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return userRepository.user(params.userId);
    }

    public static final class Params {
        private final int userId;

        private Params(int userId) {
            this.userId = userId;
        }

        public static Params forUser(int userId) {
            return new Params(userId);
        }
    }
}
