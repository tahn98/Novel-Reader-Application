package com.tahn.novelgui.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.R;
import com.tahn.novelgui.Volley_config.Volley_Constant;

import java.util.ArrayList;
import java.util.List;

public class NovelAdapterSimple extends BaseAdapter implements Filterable {

    Context context;
    List<Novel> novels;
    List<Novel> originData;
    private ValueFilter valueFilter;
    private FullFilter fullFill;

    public NovelAdapterSimple(Context context, List<Novel> novels) {
        this.context = context;
        this.novels = novels;
        originData = new ArrayList<>(novels);
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
        ImageView imgView = convertView.findViewById(R.id.img_picNovel);

        txtName.setText(novels.get(position).getName());
        //imgView.setImageResource(novels.get(position).getCover());

        // Picasso processing
        String[] k = novels.get(position).getCover().split("\\/");
        String l = k[k.length-1];

        Picasso.with(context).load(Volley_Constant.Url_Base1 + l)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
                .into(imgView);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null){
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public Filter getFullList(){
        if (fullFill == null){
            fullFill = new FullFilter();
        }
        return fullFill;
    }

    private class ValueFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Novel> filterList = new ArrayList<Novel>();
                for (int i = 0; i < novels.size(); i++) {
                    if ((novels.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        Novel novel = new Novel(novels.get(i).getId(),
                                novels.get(i).getName(),
                                novels.get(i).getDescription(),
                                novels.get(i).getAuthor_name(),
                                novels.get(i).getCover(),
                                novels.get(i).getRating(),
                                novels.get(i).getDateTime());
                        filterList.add(novel);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = novels.size();
                results.values = novels;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            novels = (ArrayList<Novel>) results.values;
            notifyDataSetChanged();
        }
    }

    private class FullFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            results.count = novels.size();
            results.values = novels;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            novels = (ArrayList<Novel>) results.values;
            notifyDataSetChanged();
        }
    }

    public ArrayList<Novel> getSearchList(){
        return (ArrayList<Novel>) novels;
    }
}
