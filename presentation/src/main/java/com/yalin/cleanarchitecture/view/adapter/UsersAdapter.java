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

package com.yalin.cleanarchitecture.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yalin.cleanarchitecture.R;
import com.yalin.cleanarchitecture.model.UserModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adaptar that manages a collection of {@link UserModel}.
 *
 * @author jinyalin
 * @since 2017/4/7.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    public interface OnItemClickListener {
        void onUserItemClick(UserModel userModel);
    }

    private List<UserModel> userModelList;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    UsersAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        userModelList = Collections.emptyList();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.row_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final UserModel userModel = userModelList.get(position);
        holder.titleTextView.setText(userModel.getFullName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onUserItemClick(userModel);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setUserCollection(Collection<UserModel> userCollection) {
        validateUsersCollection(userCollection);
        userModelList = (List<UserModel>) userCollection;
        notifyDataSetChanged();
    }

    private void validateUsersCollection(Collection<UserModel> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public int getItemCount() {
        return (userModelList == null) ? 0 : userModelList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView titleTextView;

        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
