package com.testerhome.nativeandroid.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.TopicEntity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bin Li on 2015/9/16.
 */
public class TopicsListAdapter extends BaseAdapter<TopicEntity> {


    public TopicsListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_topic, parent, false);
        return new TopicItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TopicItemViewHolder holder = (TopicItemViewHolder)viewHolder;

        TopicEntity topic = mItems.get(position);

        holder.textViewTopicTitle.setText(topic.getTitle());
    }

    public class TopicItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_topic_title)
        TextView textViewTopicTitle;

        public TopicItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
