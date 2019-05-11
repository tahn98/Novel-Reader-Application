package com.tahn.novelgui.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tahn.novelgui.DataObject.ChapterSimple;
import com.tahn.novelgui.R;

import java.util.List;

public class ChapterAdapter extends BaseAdapter {

    Context context;
    List<ChapterSimple> novels;

    public ChapterAdapter(Context context, List<ChapterSimple> novels) {
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
        convertView = inflater.inflate(R.layout.chapter_row, null);

        TextView txtName = convertView.findViewById(R.id.txtChapName);
        TextView txtDate = convertView.findViewById(R.id.txtChapDate);

        txtName.setText(novels.get(position).getNameChap());
        txtDate.setText(novels.get(position).getUploadDate());

        return convertView;
    }

}
