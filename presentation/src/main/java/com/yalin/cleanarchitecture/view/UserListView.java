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

import com.yalin.cleanarchitecture.model.UserModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link UserModel}.
 *
 * @author jinyalin
 * @since 2017/4/6.
 */

public interface UserListView extends LoadDataView {
    /**
     * Render a user list in the UI.
     *
     * @param userModelCollection The collection of {@link UserModel} that will be shown.
     */
    void renderUserList(Collection<UserModel> userModelCollection);

    /**
     * View a {@link UserModel} profile/details.
     *
     * @param userModel The user that will be shown.
     */
    void viewUser(UserModel userModel);
}