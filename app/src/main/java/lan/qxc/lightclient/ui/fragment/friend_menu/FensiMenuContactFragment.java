package lan.qxc.lightclient.ui.fragment.friend_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import lan.qxc.lightclient.R;

public class FensiMenuContactFragment extends Fragment implements View.OnClickListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
            view = inflater.inflate(R.layout.fragment_fensi_menu_contact,container,false);
        }

        return view;

    }

    @Override
    public void onClick(View v) {

    }
}
