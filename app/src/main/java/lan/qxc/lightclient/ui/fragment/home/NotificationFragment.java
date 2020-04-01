package lan.qxc.lightclient.ui.fragment.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.notificationmsg.NotiMsgAdapter;
import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.entity.message.Message;
import lan.qxc.lightclient.entity.message.MessageType;
import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.FriendMsgService;
import lan.qxc.lightclient.service.service_callback.FriendMsgExecutor;
import lan.qxc.lightclient.service.service_callback.SingleChatMsgExecutor;
import lan.qxc.lightclient.ui.activity.user_activitys.UserDetailInfoActivity;
import lan.qxc.lightclient.ui.chat.activity.SingleChatActivity;
import lan.qxc.lightclient.ui.widget.imagewarker.SpaceItemDecoration;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment implements View.OnClickListener {

    View view=null;

    SwipeRefreshLayout layout_refresh_notifi_frag;
    LinearLayout layout_search_notifi_frag;

    LinearLayout layout_dt_msg_notifi_frag;

    RecyclerView recyview_msg_notifi_frag;
    NotiMsgAdapter notiMsgAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.fragment_notification,container,false);

            initView();
            initEvent();

            freshNotiMsg();

        }

        return view;
    }


    private void initView(){
        layout_refresh_notifi_frag = view.findViewById(R.id.layout_refresh_notifi_frag);
        layout_search_notifi_frag = view.findViewById(R.id.layout_search_notifi_frag);
        layout_dt_msg_notifi_frag = view.findViewById(R.id.layout_dt_msg_notifi_frag);
        recyview_msg_notifi_frag = view.findViewById(R.id.recyview_msg_notifi_frag);

    }

    private void initEvent(){


        layout_search_notifi_frag.setOnClickListener(this);
        layout_dt_msg_notifi_frag.setOnClickListener(this);


        layout_refresh_notifi_frag.setColorSchemeResources(R.color.my_green);
        layout_refresh_notifi_frag.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.my_orange_light));



        notiMsgAdapter = new NotiMsgAdapter(getActivity(), MessageCacheUtil.msgsFrames);

        recyview_msg_notifi_frag.setLayoutManager(new LinearLayoutManager(getContext()));
        recyview_msg_notifi_frag.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(5).setSpaceColor(0xFFFFFFFF));

        recyview_msg_notifi_frag.setAdapter(notiMsgAdapter);

        notiMsgAdapter.setClickLayoutListener(new ClickLayoutListener() {
            @Override
            public void getPosition(int pos) {

                Message message = MessageCacheUtil.msgsFrames.get(pos);
                //好友请求消息
                if(message.getType()== MessageType.FRIEND_MSG){
                    FriendMsgVO friendMsgVO = (FriendMsgVO)message;

                    Long userid = friendMsgVO.getUserid();
                    Intent intent = new Intent(getActivity(), UserDetailInfoActivity.class);
                    intent.putExtra("userid",userid);
                    startActivity(intent);

                    if(friendMsgVO.getReadstate()==0){
                        friendMsgVO.setReadstate(new Byte("1"));
                        notiMsgAdapter.notifyDataSetChanged();

                        FriendMsgExecutor.getInstance().setFriendMsgHadRead(friendMsgVO.getId(), new FriendMsgExecutor.FriendMsgListener() {
                            @Override
                            public void getResult(String message) {

                            }
                        });

                    }

                    //聊天消息
                }else if(message.getType()== MessageType.SINGLE_CHAT_MSG){
                    SingleChatMsg singleChatMsg = (SingleChatMsg)message;

                    Long userid = singleChatMsg.getSendUid();
                    if(userid.equals(GlobalInfoUtil.personalInfo.getUserid())){
                        userid=singleChatMsg.getReceiveUid();
                    }
                    Intent intent = new Intent(getActivity(), SingleChatActivity.class);
                    intent.putExtra("userid",userid);
                    startActivity(intent);
                }

            }
        });


        //下拉刷新
        layout_refresh_notifi_frag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startTimer();
                freshNotiMsg();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_search_notifi_frag:
                break;

            case R.id.layout_dt_msg_notifi_frag:

                break;
        }

    }


    public interface ClickLayoutListener{
        void getPosition(int pos);
    }

    Timer freshMsgTimer;

    void startTimer(){
        if(freshMsgTimer!=null){
            freshMsgTimer.cancel();
        }
        freshMsgTimer = new Timer();
        freshMsgTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(getContext(),"刷新失败!",Toast.LENGTH_SHORT).show();
            }
        },30000);
    }

    void cancleTimer(){
        if(freshMsgTimer!=null){
            freshMsgTimer.cancel();
        }
    }



    public void freshNotiMsg(){

        SingleChatMsgExecutor.getInstance().getSingleChatMsgNotReadOfUid(new SingleChatMsgExecutor.SingleChatMsgListener() {
            @Override
            public void getResult(String message) {
                if(message.equals("SUCCESS")){
                    notiMsgAdapter.notifyDataSetChanged();
                }else{

                }
            }
        });


        Call<Result> call = FriendMsgService.getInstance().getUserFriendMsgNotRead(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                cancleTimer();
                layout_refresh_notifi_frag.setRefreshing(false);

                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<FriendMsgVO> friendMsgVOS = new Gson().fromJson(jsonstr,new TypeToken<List<FriendMsgVO>>(){}.getType());

                    MessageCacheUtil.updateFriendMsgs(friendMsgVOS);

                    notiMsgAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                cancleTimer();
                layout_refresh_notifi_frag.setRefreshing(false);
                Toast.makeText(getContext(),"error!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ContextActionStr.notification_msg_frag_action);
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancleTimer();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ContextActionStr.notification_msg_frag_action.equals(intent.getAction())){

                String type = intent.getStringExtra("type");
                if(type!=null&&type.equals("freshadapter")){
                    notiMsgAdapter.notifyDataSetChanged();
                }

            }
        }
    };


}
