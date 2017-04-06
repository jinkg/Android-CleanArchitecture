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

import com.yalin.cleanarchitecture.internal.di.PerActivity;
import com.yalin.cleanarchitecture.view.UserListView;

import javax.inject.Inject;

/**
 * @author jinyalin
 * @since 2017/4/6.
 */
@PerActivity
public class UserListPresenter implements Presenter {

    private UserListView userListView;

    @Inject
    public UserListPresenter() {
    }

    public void setView(UserListView userListView) {
        this.userListView = userListView;
    }

    public void initialize() {
        hideViewRetry();
        showViewLoading();
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
}