package lan.qxc.lightclient.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.util.UIUtils;

public class MimeFragment extends Fragment {
    View view=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mine,container,false);
//        UIUtils.setStatusBarLightMode(this);
        return view;
    }
}
