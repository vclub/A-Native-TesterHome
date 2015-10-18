package com.testerhome.nativeandroid.views.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.nativeandroid.Config;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.NotificationEntity;
import com.testerhome.nativeandroid.models.TopicDetailResponse;
import com.testerhome.nativeandroid.networks.TesterHomeApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by cvtpc on 2015/10/12.
 */
public class NotificationAdapter extends BaseAdapter<NotificationEntity> {


    public static String TAG = "NotificationAdapter";
    public NotificationAdapter(Context context) {
        super(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.list_item_notification,null);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final NotificationHolder holder = (NotificationHolder)viewHolder;
        holder.notification = mItems.get(position);
        String userName  ;
        try {
            holder.userAvatar.setVisibility(View.VISIBLE);
            holder.notificationTitle.setVisibility(View.VISIBLE);
            userName = holder.notification.getActor().getLogin();

            holder.userAvatar.setImageURI(Uri.parse(Config.getImageUrl(holder.notification.getActor().getAvatar_url())));

            switch (holder.notification.getType()) {
                case Config.TOPIC:
                    holder.notificationTitle.setText(userName + "创建了一个新的帖子");
                    holder.notificationBody.setText("");
                    break;
                case Config.FOLLOW:
                    holder.notificationTitle.setText(userName);
                    holder.notificationBody.setText(mContext.getResources().getString(R.string.follow));
                    break;
                default:
                    holder.notificationTitle.setText(ToDBC(userName + " 在帖子 *** 回复了:"));
                    holder.notificationBody.setText(Html.fromHtml(holder.notification.getReply().getBody_html()));
//                    getTopicDetailInfo(holder, userName, String.valueOf(holder.notification.getReply().getTopic_id()));
                    break;
            }
        }catch (NullPointerException e){
            holder.userAvatar.setVisibility(View.INVISIBLE);
            holder.notificationTitle.setVisibility(View.INVISIBLE);
            holder.notificationBody.setText("此楼相关信息已删除");
        }



    }

    private void getTopicDetailInfo(final NotificationHolder holder, final String userName,String id) {
        TesterHomeApi.getInstance().getTopicsService().getTopicById(id, new Callback<TopicDetailResponse>() {
            @Override
            public void success(TopicDetailResponse topicDetailResponse, Response response) {
                Log.d(TAG, "database not have");

            }
            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private EndlessListener mListener;

    public void setListener(EndlessListener mListener) {
        this.mListener = mListener;
    }

    public interface EndlessListener {
        void onListEnded();
    }




    class NotificationHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.id_user_avatar)
        SimpleDraweeView userAvatar;

        @Bind(R.id.id_notification_title)
        TextView notificationTitle;

        @Bind(R.id.id_notification_body)
        TextView notificationBody;

        NotificationEntity notification;

        public NotificationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


}
