package lan.qxc.lightclient.ui.fragment.friend_menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.dongtai.DongtaiAdapter;
import lan.qxc.lightclient.adapter.friend_menu.GuanzhuMenuAdapter;
import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.GuanzhuService;
import lan.qxc.lightclient.service.service_callback.GuanzhuExecutor;
import lan.qxc.lightclient.ui.activity.user_activitys.UserDetailInfoActivity;
import lan.qxc.lightclient.ui.fragment.home.ContactFragment;
import lan.qxc.lightclient.ui.widget.imagewarker.SpaceItemDecoration;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuanzhuMenuContactFragment extends Fragment implements View.OnClickListener {


    public static GuanzhuMenuContactFragment instance;
    View view;
    RecyclerView recyclerview_guanzhu_menu_contact_frag;

    public GuanzhuMenuAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
            view = inflater.inflate(R.layout.fragment_guanzhu_menu_contact,container,false);
            initView();
            initEvent();
            ContactFragment.instance.freshGuanzhuListData();
         }
        instance = this;

        return view;

    }

    private void initView(){


        recyclerview_guanzhu_menu_contact_frag = view.findViewById(R.id.recyclerview_guanzhu_menu_contact_frag);

        adapter = new GuanzhuMenuAdapter(getActivity(), FriendCatcheUtil.guanzhuList);

        recyclerview_guanzhu_menu_contact_frag.setLayoutManager(new LinearLayoutManager(getContext()));
      //  recyclerview_friend_menu_contact_frag.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(12).setSpaceColor(0xFFEEEEEE));

        recyclerview_guanzhu_menu_contact_frag.setAdapter(adapter);
    }

    private void initEvent(){

        adapter.setClickUserLayoutListener(new ClickUserLayoutListener() {
            @Override
            public void getPosition(int pos) {
               // Toast.makeText(getContext(),"点击了第"+pos+"个",Toast.LENGTH_SHORT).show();
                Long userid = FriendCatcheUtil.guanzhuList.get(pos).getUserid();
                Intent intent = new Intent(getActivity(), UserDetailInfoActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });

        adapter.setClickGzMenuListener(new ClickGzMenuListener() {
            @Override
            public void getPosition(int pos) {
                clickGZMenu(pos);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

        }

    }

    private void clickGZMenu(int pos){

        int gzType = FriendCatcheUtil.guanzhuList.get(pos).getGuanzhu_type();

        if(gzType==0||gzType==1){

            AlertDialog alertDialog2 = new AlertDialog.Builder(getContext())
                    .setTitle("确定取消关注吗")
//                    .setMessage("有多个按钮")
//                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delGuanzhu(pos);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create();
            alertDialog2.show();

        }else{
            guanzhu(pos);
        }

    }

    Timer guanzhuTimer;
    void startLoading(){
        if(guanzhuTimer!=null){
            guanzhuTimer.cancel();
        }
        guanzhuTimer = new Timer();
        MyDialog.showBottomLoadingDialog(getContext());
        guanzhuTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(getContext(),"提交失败,请稍后再试!",Toast.LENGTH_SHORT).show();
            }
        },20000);
    }

    void cancleLoading(){
        if(guanzhuTimer!=null){
            guanzhuTimer.cancel();
        }
        MyDialog.dismissBottomLoadingDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(guanzhuTimer!=null){
            guanzhuTimer.cancel();
        }
    }

    private void delGuanzhu(int pos){
        FriendVO friendVO = FriendCatcheUtil.guanzhuList.get(pos);
        startLoading();
        GuanzhuExecutor.getInstance().delGuanzhu(getContext(), GlobalInfoUtil.personalInfo.getUserid(), friendVO.getUserid(),
                new GuanzhuExecutor.GuanzhuListener() {
                    @Override
                    public void getResult(String message) {
                        cancleLoading();
                        if(message.equals("SUCCESS")){
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void guanzhu(int pos){
        FriendVO friendVO = FriendCatcheUtil.guanzhuList.get(pos);
        startLoading();

        GuanzhuExecutor.getInstance().guanzhu(getContext(), GlobalInfoUtil.personalInfo.getUserid(), friendVO.getUserid(),
                new GuanzhuExecutor.GuanzhuListener() {
                    @Override
                    public void getResult(String message) {
                        cancleLoading();
                        if(message.equals("SUCCESS")){
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public interface ClickGzMenuListener{
        void getPosition(int pos);
    }

    public interface ClickUserLayoutListener{
        void getPosition(int pos);
    }


}
