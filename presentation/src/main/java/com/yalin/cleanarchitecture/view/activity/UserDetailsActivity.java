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

package com.yalin.cleanarchitecture.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yalin.cleanarchitecture.R;
import com.yalin.cleanarchitecture.internal.di.HasComponent;
import com.yalin.cleanarchitecture.internal.di.components.DaggerUserComponent;
import com.yalin.cleanarchitecture.internal.di.components.UserComponent;
import com.yalin.cleanarchitecture.view.fragment.UserDetailsFragment;

import butterknife.ButterKnife;

/**
 * @author jinyalin
 * @since 2017/4/7.
 */
public class UserDetailsActivity extends BaseActivity implements HasComponent<UserComponent> {

    private static final String INTENT_EXTRA_PARAM_USER_ID = "com.yalin.INTENT_PARAM_USER_ID";
    private static final String STATE_PARAM_USER_ID = "com.yalin.STATE_PARAM_USER_ID";

    public static Intent getCallingIntent(Activity activity, int userId) {
        Intent callingIntent = new Intent(activity, UserDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, userId);
        return callingIntent;
    }

    private int userId;
    private UserComponent userComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        ButterKnife.bind(this);

        initializeActivity(savedInstanceState);
        initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(STATE_PARAM_USER_ID, userId);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            userId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_USER_ID, -1);
            addFragment(R.id.fragment_container, UserDetailsFragment.forUser(userId));
        } else {
            userId = savedInstanceState.getInt(STATE_PARAM_USER_ID);
        }
    }

    private void initializeInjector() {
        userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }
}
