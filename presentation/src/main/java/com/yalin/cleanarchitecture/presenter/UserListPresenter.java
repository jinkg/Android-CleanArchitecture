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

import com.yalin.cleanarchitecture.domain.User;
import com.yalin.cleanarchitecture.domain.exception.DefaultErrorBundle;
import com.yalin.cleanarchitecture.domain.exception.ErrorBundle;
import com.yalin.cleanarchitecture.domain.interactor.DefaultObserver;
import com.yalin.cleanarchitecture.domain.interactor.GetUserList;
import com.yalin.cleanarchitecture.domain.repository.UserRepository;
import com.yalin.cleanarchitecture.exception.ErrorMessageFactory;
import com.yalin.cleanarchitecture.internal.di.PerActivity;
import com.yalin.cleanarchitecture.mapper.UserModelDataMapper;
import com.yalin.cleanarchitecture.model.UserModel;
import com.yalin.cleanarchitecture.view.UserListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * @author jinyalin
 * @since 2017/4/6.
 */
@PerActivity
public class UserListPresenter implements Presenter {

    private UserListView userListView;

    private final GetUserList getUserListUseCase;
    private final UserModelDataMapper userModelDataMapper;

    @Inject
    public UserListPresenter(GetUserList getUserList, UserModelDataMapper userModelDataMapper) {
        this.getUserListUseCase = getUserList;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void setView(UserListView userListView) {
        this.userListView = userListView;
    }

    public void initialize() {
        loadUserList();
    }

    private void loadUserList() {
        hideViewRetry();
        showViewLoading();
        getUserList();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        userListView = null;
    }

    private void showViewLoading() {
        userListView.showLoading();
    }

    private void hideViewLoading() {
        userListView.hideLoading();
    }

    private void showViewRetry() {
        userListView.showRetry();
    }

    private void hideViewRetry() {
        userListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(userListView.context(),
                errorBundle.getException());
        userListView.showError(errorMessage);
    }

    private void showUsersCollectionInView(Collection<User> userCollection) {
        final Collection<UserModel> userModelsCollection =
                userModelDataMapper.transform(userCollection);
        userListView.renderUserList(userModelsCollection);
    }

    private void getUserList() {
        getUserListUseCase.execute(new UserListObserver(), null);
    }

    private final class UserListObserver extends DefaultObserver<List<User>> {
        @Override
        public void onNext(List<User> users) {
            UserListPresenter.this.showUsersCollectionInView(users);
        }

        @Override
        public void onError(Throwable e) {
            UserListPresenter.this.hideViewLoading();
            UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            UserListPresenter.this.showViewRetry();
        }

        @Override
        public void onComplete() {
            UserListPresenter.this.hideViewLoading();
        }
    }
}
