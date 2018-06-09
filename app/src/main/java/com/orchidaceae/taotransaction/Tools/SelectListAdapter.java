package com.orchidaceae.taotransaction.Tools;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orchidaceae.taotransaction.ProductInfoActivity;
import com.orchidaceae.taotransaction.R;

import java.util.List;
//RecyclerView的适配器
public class SelectListAdapter extends RecyclerView.Adapter<SelectListAdapter.ViewHolder> {

    private Context mContext;
    private List<SelectList> mSelectList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView list_title;
        TextView list_user;
        TextView list_price;
        TextView list_time;
        ImageView list_image;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            list_title = view.findViewById(R.id.list_title);
            list_user = view.findViewById(R.id.list_user);
            list_price = view.findViewById(R.id.list_price);
            list_time = view.findViewById(R.id.list_time);
            list_image = view.findViewById(R.id.list_image);
        }
    }
    public SelectListAdapter(List<SelectList> selectLists){
        mSelectList = selectLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item,parent,false);
        //注册点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int post = holder.getAdapterPosition();
                SelectList sl =  mSelectList.get(post);
                Intent intent = new Intent(mContext, ProductInfoActivity.class);
                intent.putExtra("PRODUCT_ID",String.valueOf(sl.getId()));
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SelectList selectList = mSelectList.get(position);
        holder.list_title.setText(selectList.getTitle());
        holder.list_user.setText(selectList.getUser());
        holder.list_price.setText(selectList.getPrice());
        holder.list_time.setText(selectList.getTime());
        Glide.with(mContext).load(selectList.getImage()).into(holder.list_image);
    }

    @Override
    public int getItemCount() {
        return mSelectList.size();
    }

}
