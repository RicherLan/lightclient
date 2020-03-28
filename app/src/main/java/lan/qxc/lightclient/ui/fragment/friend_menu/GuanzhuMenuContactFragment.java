package lan.qxc.lightclient.ui.fragment.friend_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.dongtai.DongtaiAdapter;
import lan.qxc.lightclient.adapter.friend_menu.GuanzhuMenuAdapter;
import lan.qxc.lightclient.config.dt_config.Dongtai_catch_util;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.entity.FriendVO;
import lan.qxc.lightclient.ui.widget.imagewarker.SpaceItemDecoration;

public class GuanzhuMenuContactFragment extends Fragment implements View.OnClickListener {

    View view;
    RecyclerView recyclerview_guanzhu_menu_contact_frag;

    private GuanzhuMenuAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
            view = inflater.inflate(R.layout.fragment_guanzhu_menu_contact,container,false);
            initView();
            initEvent();

         }

        return view;

    }

    private void initView(){

        for(int i=0;i<30;++i){
            FriendVO friendVO = new FriendVO();
            friendVO.setUsername("莎士比亚");
            friendVO.setGuanzhu_type(1);
            friendVO.setIntroduce("人生如戏剧");
            friendVO.setIcon("/uploadfile/headic/20200326_18233623.jpg");
            FriendCatcheUtil.guanzhuList.add(friendVO);
        }


        recyclerview_guanzhu_menu_contact_frag = view.findViewById(R.id.recyclerview_guanzhu_menu_contact_frag);

        adapter = new GuanzhuMenuAdapter(getActivity(), FriendCatcheUtil.guanzhuList);

        recyclerview_guanzhu_menu_contact_frag.setLayoutManager(new LinearLayoutManager(getContext()));
      //  recyclerview_guanzhu_menu_contact_frag.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(12).setSpaceColor(0xFFEEEEEE));

        recyclerview_guanzhu_menu_contact_frag.setAdapter(adapter);
    }

    private void initEvent(){

        adapter.setClickUserLayoutListener(new ClickUserLayoutListener() {
            @Override
            public void getPosition(int pos) {
                Toast.makeText(getContext(),"点击了第"+pos+"个",Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setClickGzMenuListener(new ClickGzMenuListener() {
            @Override
            public void getPosition(int pos) {
                int gzType = FriendCatcheUtil.guanzhuList.get(pos).getGuanzhu_type();
                if(gzType==0||gzType==1){
                    FriendCatcheUtil.guanzhuList.get(pos).setGuanzhu_type(2);
                }else{
                    FriendCatcheUtil.guanzhuList.get(pos).setGuanzhu_type(1);
                }

                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

        }

    }


    public interface ClickGzMenuListener{
        void getPosition(int pos);
    }

    public interface ClickUserLayoutListener{
        void getPosition(int pos);
    }
}
