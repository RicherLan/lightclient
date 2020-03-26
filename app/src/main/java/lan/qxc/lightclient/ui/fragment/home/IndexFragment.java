package lan.qxc.lightclient.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.home.WePagerAdapter;
import lan.qxc.lightclient.ui.activity.dongtai.AddNewDongtaiActiviy;
import lan.qxc.lightclient.ui.fragment.dongtai.DTGuanzhuFragment;
import lan.qxc.lightclient.ui.fragment.dongtai.DTTuijianFragment;

public class IndexFragment extends Fragment implements View.OnClickListener {

    View view=null;

    private TextView tv_guanzhu_index_frag;
    private TextView tv_tuijian_index_frag;
    private ImageView iv_add_index_frag;

    private View view_tuijian_index_frag;
    private View view_guanzhu_index_frag;



    private ViewPager viewpager_index_frag;
    private FragmentPagerAdapter pagerAdapter;

    private List<Fragment> fragmentList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.fragment_index,container,false);

            initView();
            initEvent();
        }

        return view;
    }

    private void initView(){
        tv_guanzhu_index_frag = view.findViewById(R.id.tv_guanzhu_index_frag);
        tv_tuijian_index_frag = view.findViewById(R.id.tv_tuijian_index_frag);

        view_tuijian_index_frag = view.findViewById(R.id.view_tuijian_index_frag);
        view_guanzhu_index_frag = view.findViewById(R.id.view_guanzhu_index_frag);


        iv_add_index_frag = view.findViewById(R.id.iv_add_index_frag);
        viewpager_index_frag = view.findViewById(R.id.viewpager_index_frag);

        fragmentList=new ArrayList<>();
        fragmentList.add(new DTTuijianFragment());
        fragmentList.add(new DTTuijianFragment());

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

        viewpager_index_frag.setAdapter(pagerAdapter);
        viewpager_index_frag.setCurrentItem(1);
        setSelect(1);

        viewpager_index_frag.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // Toast.makeText(getActivity(),"点击了第"+position+"个界面",Toast.LENGTH_SHORT).show();
                setSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initEvent(){
        iv_add_index_frag.setOnClickListener(this);
        tv_guanzhu_index_frag.setOnClickListener(this);
        tv_tuijian_index_frag.setOnClickListener(this);

    }


    private void setSelect(int i) {
        switch (i){
            case 0:
                viewpager_index_frag.setCurrentItem(0);
                tv_guanzhu_index_frag.setTextColor(getResources().getColor(R.color.black));
                tv_guanzhu_index_frag.setTextSize(19);

                view_guanzhu_index_frag.setVisibility(View.VISIBLE);
                view_tuijian_index_frag.setVisibility(View.INVISIBLE);

                tv_tuijian_index_frag.setTextColor(getResources().getColor(R.color.font_light_grey));
                tv_tuijian_index_frag.setTextSize(18);

                TextPaint tp = tv_guanzhu_index_frag.getPaint();
                tp.setFakeBoldText(true);
                TextPaint tp2 = tv_tuijian_index_frag.getPaint();
                tp2.setFakeBoldText(false);


                break;
            case 1:
                viewpager_index_frag.setCurrentItem(1);

                tv_guanzhu_index_frag.setTextColor(getResources().getColor(R.color.font_light_grey));
                tv_guanzhu_index_frag.setTextSize(18);

                view_guanzhu_index_frag.setVisibility(View.INVISIBLE);
                view_tuijian_index_frag.setVisibility(View.VISIBLE);

                tv_tuijian_index_frag.setTextColor(getResources().getColor(R.color.black));
                tv_tuijian_index_frag.setTextSize(19);

                TextPaint tp3 = tv_guanzhu_index_frag.getPaint();
                tp3.setFakeBoldText(false);
                TextPaint tp4 = tv_tuijian_index_frag.getPaint();
                tp4.setFakeBoldText(true);

                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add_index_frag:
                Intent intent = new Intent(getContext(), AddNewDongtaiActiviy.class);
                startActivity(intent);
                break;

            case R.id.tv_guanzhu_index_frag:
                setSelect(0);
                break;

            case R.id.tv_tuijian_index_frag:
                setSelect(1);
                break;
        }
    }
}
