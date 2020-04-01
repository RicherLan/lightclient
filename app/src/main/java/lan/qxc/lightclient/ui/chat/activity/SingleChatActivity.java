package lan.qxc.lightclient.ui.chat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.friend_menu.FensiMenuAdapter;
import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.PersonalInfo;
import lan.qxc.lightclient.entity.message.Message;
import lan.qxc.lightclient.entity.message.TextSingleChatMessage;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.activity.user_activitys.UserDetailInfoActivity;
import lan.qxc.lightclient.ui.chat.adapter.SingleChatAdapter;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.MyTimeUtil;
import lan.qxc.lightclient.util.UIUtils;

public class SingleChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back_single_chat_acti;
    private TextView tv_title_single_chat_acti;
    private ImageView iv_more_single_chat_acti;

    private RecyclerView recyview_body_single_chat_acti;

    private SingleChatAdapter chatAdapter;

    Long userid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIUtils.setStatusBarLightMode(this,true);

        setContentView(R.layout.activity_single_chat);


        Intent intent = getIntent();
        userid = intent.getLongExtra("userid",-1);
        if(userid==-1){
            Toast.makeText(this,"error!",Toast.LENGTH_SHORT).show();
            finish();
        }

        initView();
        initEvent();


    }


    private void initView(){
        iv_back_single_chat_acti = this.findViewById(R.id.iv_back_single_chat_acti);
        tv_title_single_chat_acti = this.findViewById(R.id.tv_title_single_chat_acti);
        iv_more_single_chat_acti = this.findViewById(R.id.iv_more_single_chat_acti);
        recyview_body_single_chat_acti = this.findViewById(R.id.recyview_body_single_chat_acti);

        chatAdapter = new SingleChatAdapter(this, MessageCacheUtil.getSingleChatMsgListByUid(userid));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyview_body_single_chat_acti.setLayoutManager(linearLayoutManager);
//        //  recyclerview_friend_menu_contact_frag.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(12).setSpaceColor(0xFFEEEEEE));

        recyview_body_single_chat_acti.setAdapter(chatAdapter);

        //弹起键盘  滚动recyclerview显示最后一条数据
        recyview_body_single_chat_acti.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    recyview_body_single_chat_acti.post(new Runnable() {
                        @Override
                        public void run() {

                            if (chatAdapter.getItemCount() > 0) {
                                recyview_body_single_chat_acti.scrollToPosition(chatAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });

    }

    private void initEvent(){
        iv_back_single_chat_acti.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_single_chat_acti:
                finish();
                break;

            case R.id.iv_more_single_chat_acti:
                Intent intent = new Intent(this, UserDetailInfoActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContextActionStr.single_chat_activity_action);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ContextActionStr.single_chat_activity_action.equals(intent.getAction())){

                String type = intent.getStringExtra("type");
                //收到了消息
                if(type!=null&&type.equals("receivemsg")){
                    Long uid = intent.getLongExtra("userid",-1);
                    if(uid==-1){
                        Toast.makeText(SingleChatActivity.this,"error!",Toast.LENGTH_SHORT).show();
                        finish();
                    }else if(userid.equals(uid)){

                        chatAdapter.notifyDataSetChanged();
                    }
                }

            }
        }
    };
}
