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
import com.yalin.cleanarchitecture.internal.di.components.DaggerApplicationComponent;
import com.yalin.cleanarchitecture.internal.di.components.DaggerUserComponent;
import com.yalin.cleanarchitecture.internal.di.components.UserComponent;
import com.yalin.cleanarchitecture.view.fragment.UserListFragment;

/**
 * @author jinyalin
 * @since 2017/4/6.
 */

public class UserListActivity extends BaseActivity implements HasComponent<UserComponent> {

    public static Intent getCallingIntent(Activity activity) {
        return new Intent(activity, UserListActivity.class);
    }

    private UserComponent userComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        initializeInjector();

        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new UserListFragment());
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
