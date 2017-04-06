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

package com.yalin.cleanarchitecture.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.yalin.cleanarchitecture.R;
import com.yalin.cleanarchitecture.internal.di.components.UserComponent;
import com.yalin.cleanarchitecture.model.UserModel;
import com.yalin.cleanarchitecture.presenter.UserListPresenter;
import com.yalin.cleanarchitecture.view.UserListView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that shows a list of Users.
 *
 * @author jinyalin
 * @since 2017/4/6.
 */

public class UserListFragment extends BaseFragment implements UserListView {

    @BindView(R.id.rv_users)
    RecyclerView recyclerView;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rl_retry)
    RelativeLayout rlRetry;
    @BindView(R.id.bt_retry)
    Button btnRetry;

    @Inject
    UserListPresenter userListPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(UserComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userListPresenter.setView(this);
        if (savedInstanceState == null) {
            loadUserList();
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void renderUserList(Collection<UserModel> userModelCollection) {

    }

    @Override
    public void viewUser(UserModel userModel) {

    }

    @Override
    public void showLoading() {
        rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        showToastMessage(errorMessage);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        userListPresenter.initialize();
    }
}
