package com.tahn.novelgui.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tahn.novelgui.DataObject.Novel;
import com.tahn.novelgui.MainActivity;
import com.tahn.novelgui.R;

import java.util.ArrayList;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Novel> novelArrayList;
    private View.OnClickListener mOnItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtViewName;
        public TextView txtRate;
        public ImageView imgNovel;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtViewName = itemView.findViewById(R.id.title);
            txtRate = itemView.findViewById(R.id.userrating);
            imgNovel = itemView.findViewById(R.id.thumbnail);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    public NovelAdapter(Context context, ArrayList<Novel> novelArrayList){
        this.context = context;
        this.novelArrayList = novelArrayList;
    }

    @Override
    public NovelAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.novel_card, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NovelAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.txtViewName.setText(novelArrayList.get(i).getName());
        myViewHolder.txtRate.setText("Rate: " + novelArrayList.get(i).getRating());

        Picasso.with(context).load("http://192.168.56.1:8080/sql_server/Image/1.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.endgame)
                .into(myViewHolder.imgNovel);

        //myViewHolder.imgNovel.setImageResource(novelArrayList.get(i).getImg());
    }

    @Override
    public int getItemCount() {
        return novelArrayList.size();
    }
}
