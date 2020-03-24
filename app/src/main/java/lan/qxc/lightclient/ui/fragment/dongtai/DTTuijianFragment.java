package lan.qxc.lightclient.ui.fragment.dongtai;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.dongtai.DongtaiAdapter;
import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.listener.EndlessRecyclerOnScrollListener;
import lan.qxc.lightclient.ui.widget.imagewarker.SpaceItemDecoration;

public class DTTuijianFragment extends Fragment implements View.OnClickListener {

    private View view;

    private SwipeRefreshLayout layout_refresh_dt_tuijian_frag;
    private RecyclerView recyclerview_dt_tuijian_frag;

    private DongtaiAdapter dongtaiAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.fragment_dt_tuijian,container,false);

            initView();
            initEvent();
        }
        return view;
    }

    private void initView(){
        layout_refresh_dt_tuijian_frag = view.findViewById(R.id.layout_refresh_dt_tuijian_frag);
        recyclerview_dt_tuijian_frag = view.findViewById(R.id.recyclerview_dt_tuijian_frag);



    }

    private void initEvent(){

        layout_refresh_dt_tuijian_frag.setColorSchemeResources(R.color.my_green);
      //  layout_refresh_dt_tuijian_frag.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.my_blue));

        //下拉刷新
        layout_refresh_dt_tuijian_frag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        DongtailVO dongtailVO = new DongtailVO();
        dongtailVO.setUserid(new Long(1));
        dongtailVO.setDtcontent("难道不是生日，也能吃到大厨煮的菜，好吃，好撑，好满足~");
        dongtailVO.setDtcreatetime("2020-3-24 14:28:33");
        dongtailVO.setDtid(new Long(100));
        dongtailVO.setUsername("杨丞琳");

        dongtailVO.setIcon("/uploadfile/headic/20200324_19300369.jpg");
        dongtailVO.setDtpic("/uploadfile/dongtai_ic/1.jpg /uploadfile/dongtai_ic/2.jpg");




        DongtailVO dongtailVO2 = new DongtailVO();
        dongtailVO2.setUserid(new Long(1));
        dongtailVO2.setDtcontent("许多年过去后，站在熟悉的路口\n被时间的大手，推到回忆的尽头");
        dongtailVO2.setDtcreatetime("2020-3-24 20:03:12");
        dongtailVO2.setDtid(new Long(101));
        dongtailVO2.setUsername("Support");

        dongtailVO2.setIcon("/uploadfile/headic/20200320_21014890.jpg");
        String path = "/uploadfile/dongtai_ic/3.jpg /uploadfile/dongtai_ic/4.jpg /uploadfile/dongtai_ic/5.jpg " +
                "/uploadfile/dongtai_ic/6.jpg /uploadfile/dongtai_ic/7.jpg /uploadfile/dongtai_ic/8.jpg " +
                "/uploadfile/dongtai_ic/9.jpg /uploadfile/dongtai_ic/10.jpg /uploadfile/dongtai_ic/11.jpg ";
        dongtailVO2.setDtpic(path);

        List<DongtailVO> dongtailVOS = new ArrayList<>();
        dongtailVOS.add(dongtailVO);
        dongtailVOS.add(dongtailVO2);


        dongtaiAdapter = new DongtaiAdapter(getContext(),dongtailVOS);
        recyclerview_dt_tuijian_frag.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_dt_tuijian_frag.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(12).setSpaceColor(0xFFEEEEEE));

        recyclerview_dt_tuijian_frag.setAdapter(dongtaiAdapter);

        //下拉刷新
        layout_refresh_dt_tuijian_frag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                layout_refresh_dt_tuijian_frag.setRefreshing(false);
                            }
                        });
                    }
                },10000);

                //请求服务器
            }
        });

        //监听上拉刷新
        recyclerview_dt_tuijian_frag.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                dongtaiAdapter.setLoadState(dongtaiAdapter.LOADING);

                //请求服务器

            }
        });

        dongtaiAdapter.setTransmitListener(new ClickTransmitListener() {
            @Override
            public void clickTransmit(int position) {
                Toast.makeText(getContext(),"点击转发 "+position,Toast.LENGTH_SHORT).show();
            }
        });

        dongtaiAdapter.setCommonListener(new ClickCommonListener() {
            @Override
            public void clickCommon(int position) {
                Toast.makeText(getContext(),"点击评论 "+position,Toast.LENGTH_SHORT).show();
            }
        });


        dongtaiAdapter.setLikeListener(new ClickLikeListener() {
            @Override
            public void clickLike(int position) {
                Toast.makeText(getContext(),"点击点赞 "+position,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {

    }

    public interface ClickTransmitListener{
        void clickTransmit(int position);
    }
    public interface ClickCommonListener{
        void clickCommon(int position);
    }
    public interface ClickLikeListener{
        void clickLike(int position);
    }
}
