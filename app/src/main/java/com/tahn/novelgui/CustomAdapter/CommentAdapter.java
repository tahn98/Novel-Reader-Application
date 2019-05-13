package com.tahn.novelgui.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tahn.novelgui.DataObject.Comment;
import com.tahn.novelgui.R;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    Context context;
    List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.comment_row, null);

        TextView txtUsername = convertView.findViewById(R.id.txtNameUserComment);
        TextView txtDate = convertView.findViewById(R.id.txtDateComment);
        TextView txtContent = convertView.findViewById(R.id.txtCommentContent);

        txtUsername.setText(comments.get(position).getName_user());
        txtDate.setText(comments.get(position).getDate_comment());
        txtContent.setText(comments.get(position).getContent());

        return convertView;
    }
}
