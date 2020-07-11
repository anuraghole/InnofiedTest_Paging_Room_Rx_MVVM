package com.example.innofiedtest.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.innofiedtest.R;
import com.example.innofiedtest.databinding.LayoutItemUserBinding;
import com.example.innofiedtest.model.User;

public class UserAdapter extends PagedListAdapter<User,UserAdapter.UserViewHolder> {
    private Context context;
    public UserAdapter(Context context) {
        super(User.DIFF_CALLBACK);
        this.context=context;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.layout_item_user, parent, false);

        return new UserViewHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.binding.setModel(getItem(position));
        Glide.with(context).load(getItem(position).getAvatar()).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).into(holder.binding.ivAvatar);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        LayoutItemUserBinding binding;
        public UserViewHolder(LayoutItemUserBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
