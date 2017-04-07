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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fernandocejas.arrow.checks.Preconditions;
import com.yalin.cleanarchitecture.R;
import com.yalin.cleanarchitecture.internal.di.components.UserComponent;
import com.yalin.cleanarchitecture.model.UserModel;
import com.yalin.cleanarchitecture.presenter.UserDetailsPresenter;
import com.yalin.cleanarchitecture.view.UserDetailsView;

import javax.inject.Inject;

/**
 * @author jinyalin
 * @since 2017/4/6.
 */

public class UserDetailsFragment extends BaseFragment implements UserDetailsView {
    private static final String PARAM_USER_ID = "param_user_id";

    ImageView ivCover;
    TextView tvFullName;
    RelativeLayout rlProgress;
    RelativeLayout rlRetry;
    Button btnRetry;

    @Inject
    UserDetailsPresenter userDetailsPresenter;

    public static UserDetailsFragment forUser(int userId) {
        UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM_USER_ID, userId);
        userDetailsFragment.setArguments(args);
        return userDetailsFragment;
    }

    public UserDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(UserComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.clean_architecture_fragment_user_details,
                container, false);
        findView(fragmentView);
        return fragmentView;
    }

    private void findView(View fragmentView) {
        ivCover = (ImageView) fragmentView.findViewById(R.id.iv_cover);
        tvFullName = (TextView) fragmentView.findViewById(R.id.tv_fullname);
        rlProgress = (RelativeLayout) fragmentView.findViewById(R.id.rl_progress);
        rlRetry = (RelativeLayout) fragmentView.findViewById(R.id.rl_retry);
        btnRetry = (Button) fragmentView.findViewById(R.id.bt_retry);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUserDetails();
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            loadUserDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        userDetailsPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userDetailsPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    @Override
    public void renderUser(UserModel user) {
        tvFullName.setText(user.getFullName());
    }

    private int currentUserId() {
        Bundle args = getArguments();
        Preconditions.checkNotNull(args, "Fragment arguments cannot be null.");
        return args.getInt(PARAM_USER_ID);
    }

    private void loadUserDetails() {
        userDetailsPresenter.initialize(currentUserId());
    }
}
