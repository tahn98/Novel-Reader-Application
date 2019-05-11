package com.tahn.novelgui.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.R;

import java.util.ArrayList;
import java.util.List;

public class NovelBookmarkAdapter extends BaseAdapter {

    Context context;
    List<Novel> novels;

    public NovelBookmarkAdapter(Context context, List<Novel> novels) {
        this.context = context;
        this.novels = novels;
    }

    @Override
    public int getCount() {
        return novels.size();
    }

    @Override
    public Object getItem(int position) {
        return novels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.listview_main_custom, null);

        TextView txtName = convertView.findViewById(R.id.txtNovelName);
        TextView txtDesc = convertView.findViewById(R.id.txtDesc);
        TextView txtTime = convertView.findViewById(R.id.txtTimeUpdate);
        ImageView imgView = convertView.findViewById(R.id.img_picNovel);

        txtName.setText(novels.get(position).getName());
        txtDesc.setText(novels.get(position).getDescription());
        txtTime.setText(novels.get(position).getDateTime());
        //imgView.setImageResource(novels.get(position).getCover());

        // Picasso processing
        return convertView;
    }

}
