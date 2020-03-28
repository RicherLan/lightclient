package lan.qxc.lightclient.ui.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.GuanzhuService;
import lan.qxc.lightclient.ui.fragment.dongtai.DTTuijianFragment;
import lan.qxc.lightclient.ui.fragment.friend_menu.FensiMenuContactFragment;
import lan.qxc.lightclient.ui.fragment.friend_menu.FriendMenuContactFragment;
import lan.qxc.lightclient.ui.fragment.friend_menu.GroupMenuContactFragment;
import lan.qxc.lightclient.ui.fragment.friend_menu.GuanzhuMenuContactFragment;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactFragment extends Fragment implements View.OnClickListener {

   public static ContactFragment instance;
    View view=null;

    private TextView tv_title_contact_frag;
    private ImageView iv_add_contact_frag;

    public SwipeRefreshLayout layout_refresh_contact_frag;
    private LinearLayout layout_search_contact_frag;

    private LinearLayout layout_guanzhu_menu_contact_frag;
    private TextView tv_guanzhu_menu_contact_frag;

    private LinearLayout layout_friend_menu_contact_frag;
    private TextView tv_friend_menu_contact_frag;

    private LinearLayout layout_fensi_menu_contact_frag;
    private TextView tv_fensi_menu_contact_frag;

    private LinearLayout layout_group_menu_contact_frag;
    private TextView tv_group_menu_contact_frag;

    private ViewPager viewpager_contact_frag;
    private FragmentPagerAdapter pagerAdapter;

    private List<Fragment> fragmentList;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.fragment_contact,container,false);
            initView();
            initViewPager();
            initEvent();
        }
        instance = this;

        return view;
    }

    private void initView(){
        tv_title_contact_frag = view.findViewById(R.id.tv_title_contact_frag);

        TextPaint tp = tv_title_contact_frag.getPaint();
        tp.setFakeBoldText(true);

        iv_add_contact_frag = view.findViewById(R.id.iv_add_contact_frag);

        layout_refresh_contact_frag = view.findViewById(R.id.layout_refresh_contact_frag);

        layout_refresh_contact_frag.setColorSchemeResources(R.color.my_green);
        layout_refresh_contact_frag.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.my_orange_light));


        layout_search_contact_frag = view.findViewById(R.id.layout_search_contact_frag);

        layout_guanzhu_menu_contact_frag = view.findViewById(R.id.layout_guanzhu_menu_contact_frag);
        tv_guanzhu_menu_contact_frag = view.findViewById(R.id.tv_guanzhu_menu_contact_frag);

        layout_friend_menu_contact_frag = view.findViewById(R.id.layout_friend_menu_contact_frag);
        tv_friend_menu_contact_frag = view.findViewById(R.id.tv_friend_menu_contact_frag);

        layout_fensi_menu_contact_frag = view.findViewById(R.id.layout_fensi_menu_contact_frag);
        tv_fensi_menu_contact_frag = view.findViewById(R.id.tv_fensi_menu_contact_frag);

        layout_group_menu_contact_frag = view.findViewById(R.id.layout_group_menu_contact_frag);
        tv_group_menu_contact_frag = view.findViewById(R.id.tv_group_menu_contact_frag);


        viewpager_contact_frag = view.findViewById(R.id.viewpager_contact_frag);


    }

    private void initViewPager(){

        fragmentList=new ArrayList<>();
        fragmentList.add(new GuanzhuMenuContactFragment());
        fragmentList.add(new FriendMenuContactFragment());
        fragmentList.add(new FensiMenuContactFragment());
        fragmentList.add(new GroupMenuContactFragment());

        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {

                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        viewpager_contact_frag.setAdapter(pagerAdapter);
        select(0);

        viewpager_contact_frag.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Toast.makeText(getActivity(),"点击了第"+position+"个界面",Toast.LENGTH_SHORT).show();
                select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvent(){
        iv_add_contact_frag.setOnClickListener(this);
        layout_search_contact_frag.setOnClickListener(this);

        layout_guanzhu_menu_contact_frag.setOnClickListener(this);
        layout_friend_menu_contact_frag.setOnClickListener(this);
        layout_fensi_menu_contact_frag.setOnClickListener(this);
        layout_group_menu_contact_frag.setOnClickListener(this);


        //下拉刷新
        layout_refresh_contact_frag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add_contact_frag:

                break;

            case R.id.layout_search_contact_frag:

                break;
            case R.id.layout_guanzhu_menu_contact_frag:
                select(0);
                break;
            case R.id.layout_friend_menu_contact_frag:
                select(1);
                break;
            case R.id.layout_fensi_menu_contact_frag:
                select(2);
                break;
            case R.id.layout_group_menu_contact_frag:
                select(3);
                break;


        }
    }


    Timer refreshTimer;
    //下拉刷新
    private void refresh(){
        if(refreshTimer!=null){
            refreshTimer.cancel();
        }
        refreshTimer = new Timer();

        refreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        layout_refresh_contact_frag.setRefreshing(false);
                        Toast.makeText(getActivity(),"刷新失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },25000);

        freshAllList();
    }

    public void freshAllList(){
        freshGuanzhuListData();
        freshFriendListData();
        freshFensiListData();
    }

    void disrefresh(){
        if(refreshTimer!=null){
            refreshTimer.cancel();
        }
        layout_refresh_contact_frag.setRefreshing(false);
    }




    private void select(int position){
        switch (position){
            case 0:
                viewpager_contact_frag.setCurrentItem(0);
                layout_guanzhu_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_select_layout));
                tv_guanzhu_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_select));

                layout_friend_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_friend_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_fensi_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_fensi_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_group_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_group_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));
                break;
            case 1:
                viewpager_contact_frag.setCurrentItem(1);
                layout_guanzhu_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_guanzhu_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_friend_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_select_layout));
                tv_friend_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_select));

                layout_fensi_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_fensi_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_group_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_group_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));
                break;
            case 2:
                viewpager_contact_frag.setCurrentItem(2);
                layout_guanzhu_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_guanzhu_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_friend_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_friend_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_fensi_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_select_layout));
                tv_fensi_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_select));

                layout_group_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_group_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));
                break;
            case 3:
                viewpager_contact_frag.setCurrentItem(3);
                layout_guanzhu_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_guanzhu_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_friend_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_friend_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_fensi_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_normal_layout));
                tv_fensi_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_normal));

                layout_group_menu_contact_frag.setBackground(getResources().getDrawable(R.drawable.redis_fenzu_menu_select_layout));
                tv_group_menu_contact_frag.setTextColor(getResources().getColor(R.color.friend_fenzu_font_select));
                break;

        }
    }


    public  void freshGuanzhuListData(){

        Call<Result> call = GuanzhuService.getInstance().getMyGuanzhu(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                disrefresh();

                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<FriendVO> friendVOList = new Gson().fromJson(jsonstr,new TypeToken<List<FriendVO>>(){}.getType());
                    FriendCatcheUtil.guanzhuList.clear();
                    FriendCatcheUtil.guanzhuList.addAll(friendVOList);
                    if(GuanzhuMenuContactFragment.instance!=null){
                        GuanzhuMenuContactFragment.instance.adapter.notifyDataSetChanged();
                    }
//                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                disrefresh();

                ContactFragment.instance.layout_refresh_contact_frag.setRefreshing(false);
                Toast.makeText(getContext(),"error!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void freshFriendListData(){

        Call<Result> call = GuanzhuService.getInstance().getFriendsByUserid(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                disrefresh();

                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<FriendVO> friendVOList = new Gson().fromJson(jsonstr,new TypeToken<List<FriendVO>>(){}.getType());
                    FriendCatcheUtil.friendList.clear();
                    FriendCatcheUtil.friendList.addAll(friendVOList);
                    if(FriendMenuContactFragment.instance!=null){
                        FriendMenuContactFragment.instance.adapter.notifyDataSetChanged();
                    }
//                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                disrefresh();

                ContactFragment.instance.layout_refresh_contact_frag.setRefreshing(false);
                Toast.makeText(getContext(),"error!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void freshFensiListData(){

        Call<Result> call = GuanzhuService.getInstance().getUsersGuanzhuMe(GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                disrefresh();

                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){

                    String jsonstr = JsonUtils.objToJson(result.getData());
                    List<FriendVO> friendVOList = new Gson().fromJson(jsonstr,new TypeToken<List<FriendVO>>(){}.getType());
                    FriendCatcheUtil.fensiList.clear();
                    FriendCatcheUtil.fensiList.addAll(friendVOList);
                    if(FensiMenuContactFragment.instance!=null){
                        FensiMenuContactFragment.instance.adapter.notifyDataSetChanged();
                    }
//                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                disrefresh();

                ContactFragment.instance.layout_refresh_contact_frag.setRefreshing(false);
                Toast.makeText(getContext(),"error!",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
