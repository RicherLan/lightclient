package lan.qxc.lightclient.ui.fragment.dongtai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import lan.qxc.lightclient.R;

public class DTTuijianFragment extends Fragment implements View.OnClickListener {

    private View view;

    private SwipeRefreshLayout layout_refresh_dt_tuijian_frag;
    private RecyclerView recyclerview_dt_tuijian_frag;

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

    }

    @Override
    public void onClick(View v) {

    }
}
