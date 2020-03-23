package lan.qxc.lightclient.ui.fragment.dongtai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import lan.qxc.lightclient.R;

public class DTGuanzhuFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dt_guanzhu,container,false);

        initView();
        initEvent();

        return view;
    }

    private void initView(){

    }

    private void initEvent(){

    }

}
