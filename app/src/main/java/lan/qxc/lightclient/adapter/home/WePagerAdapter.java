package lan.qxc.lightclient.adapter.home;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class WePagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManger;
    private List<Fragment> mList;

    public WePagerAdapter(FragmentManager fragmentManager, List<Fragment> list){
        super(fragmentManager);
        mFragmentManger=fragmentManager;
        mList=list;
    }

    public Fragment getItem(int arg0){
        return mList.get(arg0);
    }

    public int getCount(){
        return mList.size();
    }
}
