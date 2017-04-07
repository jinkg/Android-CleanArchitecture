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

package com.yalin.cleanarchitecture.presenter;

import android.support.annotation.NonNull;

import com.yalin.cleanarchitecture.domain.User;
import com.yalin.cleanarchitecture.domain.exception.DefaultErrorBundle;
import com.yalin.cleanarchitecture.domain.exception.ErrorBundle;
import com.yalin.cleanarchitecture.domain.interactor.DefaultObserver;
import com.yalin.cleanarchitecture.domain.interactor.GetUserDetails;
import com.yalin.cleanarchitecture.domain.interactor.GetUserDetails.Params;
import com.yalin.cleanarchitecture.exception.ErrorMessageFactory;
import com.yalin.cleanarchitecture.internal.di.PerActivity;
import com.yalin.cleanarchitecture.mapper.UserModelDataMapper;
import com.yalin.cleanarchitecture.model.UserModel;
import com.yalin.cleanarchitecture.view.UserDetailsView;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 *
 * @author jinyalin
 * @since 2017/4/7.
 */
@PerActivity
public class UserDetailsPresenter implements Presenter {
    private UserDetailsView userDetailsView;

    private final GetUserDetails getUserDetailsUseCase;
    private final UserModelDataMapper userModelDataMapper;

    @Inject
    public UserDetailsPresenter(GetUserDetails getUserDetailsUseCase,
                                UserModelDataMapper userModelDataMapper) {
        this.getUserDetailsUseCase = getUserDetailsUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void setView(@NonNull UserDetailsView userDetailsView) {
        this.userDetailsView = userDetailsView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getUserDetailsUseCase.dispose();
        userDetailsView = null;
    }

    /**
     * Initializes the presenter by showing/hiding proper views
     * and retrieving user details.
     */
    public void initialize(int userId) {
        hideViewRetry();
        showViewLoading();
        getUserDetails(userId);
    }

    private void getUserDetails(int userId) {
        getUserDetailsUseCase.execute(new UserDetailsObserver(), Params.forUser(userId));
    }

    private void showViewLoading() {
        userDetailsView.showLoading();
    }

    private void hideViewLoading() {
        userDetailsView.hideLoading();
    }

    private void showViewRetry() {
        userDetailsView.showRetry();
    }

    private void hideViewRetry() {
        userDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(userDetailsView.context(),
                errorBundle.getException());
        userDetailsView.showError(errorMessage);
    }

    private void showUserDetailsInView(User user) {
        UserModel userModel = userModelDataMapper.transform(user);
        userDetailsView.renderUser(userModel);
    }

    private final class UserDetailsObserver extends DefaultObserver<User> {
        @Override
        public void onNext(User user) {
            showUserDetailsInView(user);
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
            showViewRetry();
        }

        @Override
        public void onComplete() {
            hideViewLoading();
        }
    }
}
