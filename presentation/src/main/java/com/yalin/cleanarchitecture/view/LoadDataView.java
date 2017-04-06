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

package com.yalin.cleanarchitecture.view;

import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 *
 * @author jinyalin
 * @since 2017/4/6.
 */
public interface LoadDataView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    void showRetry();

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    void hideRetry();

    /**
     * Show an error message
     *
     * @param errorMessage A string representing an error.
     */
    void showError(String errorMessage);

    /**
     * Get a {@link android.content.Context}.
     */
    Context context();
}
