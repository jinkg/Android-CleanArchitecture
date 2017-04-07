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
import com.yalin.cleanarchitecture.view.adapter.UsersAdapter;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Fragment that shows a list of Users.
 *
 * @author jinyalin
 * @since 2017/4/6.
 */

public class UserListFragment extends BaseFragment implements UserListView {

    RecyclerView recyclerView;
    RelativeLayout rlProgress;
    RelativeLayout rlRetry;
    Button btnRetry;

    @Inject
    UserListPresenter userListPresenter;
    @Inject
    UsersAdapter usersAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(UserComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView =
                inflater.inflate(R.layout.clean_architecture_fragment_user_list, container, false);
        findView(fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    private void findView(View fragmentView) {
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.rv_users);
        rlProgress = (RelativeLayout) fragmentView.findViewById(R.id.rl_progress);
        rlRetry = (RelativeLayout) fragmentView.findViewById(R.id.rl_retry);
        btnRetry = (Button) fragmentView.findViewById(R.id.bt_retry);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userListPresenter.setView(this);
        if (savedInstanceState == null) {
            loadUserList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        userListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userListPresenter.destroy();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(usersAdapter);
    }

    @Override
    public void renderUserList(Collection<UserModel> userModelCollection) {
        if (userModelCollection != null) {
            usersAdapter.setUserCollection(userModelCollection);
        }
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
