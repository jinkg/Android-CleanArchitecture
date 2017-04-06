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

import android.app.Fragment;
import android.widget.Toast;

import com.yalin.cleanarchitecture.internal.di.HasComponent;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 *
 * @author jinyalin
 * @since 2017/4/6.
 */

public abstract class BaseFragment extends Fragment {
    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    protected <C> C getComponent(Class<C> componentType) {
        //noinspection unchecked
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
