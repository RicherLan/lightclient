package lan.qxc.lightclient.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.ui.activity.user_activitys.PersonalActivity;
import lan.qxc.lightclient.util.UIUtils;

public class MimeFragment extends Fragment implements View.OnClickListener {
    View view=null;

    LinearLayout layout_mine_personal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.fragment_mine,container,false);
            initView();
            initEvent();
        }
        return view;
    }

    void initView(){
        layout_mine_personal = view.findViewById(R.id.layout_mine_personal);
    }

    void initEvent(){
        layout_mine_personal.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.layout_mine_personal:
                Intent intent = new Intent(MimeFragment.this.getContext(), PersonalActivity.class);
                startActivity(intent);
                break;
        }

    }



}
