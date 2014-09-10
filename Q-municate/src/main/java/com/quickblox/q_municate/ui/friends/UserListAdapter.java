package com.quickblox.q_municate.ui.friends;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.quickblox.q_municate.R;
import com.quickblox.q_municate.caching.DatabaseManager;
import com.quickblox.q_municate.model.Friend;
import com.quickblox.q_municate.ui.base.BaseActivity;
import com.quickblox.q_municate.ui.base.BaseListAdapter;
import com.quickblox.q_municate.ui.views.RoundedImageView;
import com.quickblox.q_municate.utils.TextViewHelper;

import java.util.List;

public class UserListAdapter extends BaseListAdapter<Friend> {

    private UserListListener listener;
    private String searchCharacters;

    public UserListAdapter(BaseActivity activity, List<Friend> users, UserListListener listener) {
        super(activity, users);
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Friend user = objectsList.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_user, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String url = user.getAvatarUrl();

        displayImage(url, holder.avatarImageView);

        holder.fullnameTextView.setText(user.getFullname());
        holder.addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserSelected(position);
            }
        });

        if (DatabaseManager.isFriendInBase(baseActivity, user.getId())) {
            holder.addFriendButton.setVisibility(View.INVISIBLE);
        } else {
            holder.addFriendButton.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(searchCharacters)) {
            TextViewHelper.changeTextColorView(baseActivity, holder.fullnameTextView, searchCharacters);
        }

        return convertView;
    }

    public void setSearchCharacters(String searchCharacters) {
        this.searchCharacters = searchCharacters;
    }

    private ViewHolder createViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.avatarImageView = (RoundedImageView) view.findViewById(R.id.avatar_imageview);
        holder.fullnameTextView = (TextView) view.findViewById(R.id.name_textview);
        holder.addFriendButton = (ImageButton) view.findViewById(R.id.addFriendButton);
        return holder;
    }

    public interface UserListListener {

        void onUserSelected(int position);
    }

    private static class ViewHolder {

        public RoundedImageView avatarImageView;
        public TextView fullnameTextView;
        public ImageButton addFriendButton;
    }
}