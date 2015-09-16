package com.testerhome.nativeandroid.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bin Li on 2015/9/16.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mItems;
    protected Context mContext;

    public BaseAdapter(Context context){
        this.mContext = context;
        mItems = new ArrayList<>();
    }

    public List<T> getList() {
        return mItems;
    }

    public void setItems(List<T> items){
        if (items == null) return;
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items){
        if (items == null) return;
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }
}
