package lan.qxc.lightclient.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;
import lan.qxc.lightclient.R;
import lan.qxc.lightclient.retrofit_util.api.APIUtil;
import lan.qxc.lightclient.ui.activity.user_activitys.PersonalActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.ImageUtil;
import lan.qxc.lightclient.util.SharePerferenceUtil;

public class MineFragment extends Fragment implements View.OnClickListener {
    View view=null;

    LinearLayout layout_mine_personal;
    private CircleImageView iv_headic_mine_frag;
    private TextView tv_usernameq_mine_frag;
    private TextView tv_intro_mine_frag;




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

        layout_mine_personal = view.findViewById(R.id.layout_mine_frag);

        iv_headic_mine_frag = view.findViewById(R.id.iv_headic_mine_frag);
        tv_usernameq_mine_frag = view.findViewById(R.id.tv_usernameq_mine_frag);
        tv_intro_mine_frag = view.findViewById(R.id.tv_intro_mine_frag);



    }

    void initEvent(){
        layout_mine_personal.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.layout_mine_frag:
                Intent intent = new Intent(MineFragment.this.getContext(), PersonalActivity.class);
                startActivity(intent);
                break;
        }

    }


    private void setData(){
        tv_usernameq_mine_frag.setText(GlobalInfoUtil.personalInfo.getUsername());
        tv_intro_mine_frag.setText(GlobalInfoUtil.personalInfo.getIntroduce());

        String headIcPath = APIUtil.getUrl(GlobalInfoUtil.personalInfo.getIcon());
        ImageUtil.getInstance().setNetImageToView(getContext(),headIcPath,iv_headic_mine_frag);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}
