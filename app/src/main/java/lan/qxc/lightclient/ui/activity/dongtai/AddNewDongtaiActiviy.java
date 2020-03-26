package lan.qxc.lightclient.ui.activity.dongtai;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.config.PictureSelectConstants;
import lan.qxc.lightclient.result.Result;
import lan.qxc.lightclient.service.DongtaiServicce;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.activity.user_activitys.PersonalActivity;
import lan.qxc.lightclient.ui.fragment.dongtai.DTTuijianFragment;
import lan.qxc.lightclient.ui.widget.nice9layout.ImageNice9Layout;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.MyPictureSelectorUtil;
import lan.qxc.lightclient.util.PermissionUtil;
import lan.qxc.lightclient.util.PhoneSystemUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    发表动态  编辑页面
 */
public class AddNewDongtaiActiviy extends BaseForCloseActivity implements View.OnClickListener {


    private ImageView iv_back_add_dt;
    private TextView tv_submit_adddt_activity;

    private EditText edit_text_add_dt_ac;
    private ImageView iv_camera_select_pic_adddt;
    private TextView tv_hinttext_select_pic_adddt;

    private ImageNice9Layout image_nice9_add_dt_activity;


    private Vector<String > picPathList = new Vector<>();
    int num = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_dongtai);
        initView();
        initEvent();

    }

    private void initView(){
        iv_back_add_dt = this.findViewById(R.id.iv_back_add_dt);
        tv_submit_adddt_activity = this.findViewById(R.id.tv_submit_adddt_activity);
        edit_text_add_dt_ac = this.findViewById(R.id.edit_text_add_dt_ac);
        iv_camera_select_pic_adddt = this.findViewById(R.id.iv_camera_select_pic_adddt);
        tv_hinttext_select_pic_adddt = this.findViewById(R.id.tv_hinttext_select_pic_adddt);
        image_nice9_add_dt_activity = this.findViewById(R.id.image_nice9_add_dt_activity);


    }

    private void initEvent(){
        iv_back_add_dt.setOnClickListener(this);
        tv_submit_adddt_activity.setOnClickListener(this);
        iv_camera_select_pic_adddt.setOnClickListener(this);

        image_nice9_add_dt_activity.setItemDelegate(new ImageNice9Layout.ItemDelegate() {
            @Override
            public void onItemClick(int position) {
                viewPluImg(position);
            }

        });

        image_nice9_add_dt_activity.setItemClickDelete(new ImageNice9Layout.ItemClickDelete(){
            @Override
            public void onItemDelete(int position) {
                picPathList.removeElementAt(position);
                num--;
                int t=9-num;
                tv_hinttext_select_pic_adddt.setText("剩余"+t+"张");

                iv_camera_select_pic_adddt.setImageResource(R.drawable.ic_add_pic);
                iv_camera_select_pic_adddt.setClickable(true);

                bindData();
            }
        });

        bindData();

    }

    private void bindData() {

        image_nice9_add_dt_activity.bindData(picPathList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_add_dt:
                finish();
                break;

            case R.id.tv_submit_adddt_activity:
                doUpload();
                break;

            case R.id.iv_camera_select_pic_adddt:
                selectPic(PictureSelectConstants.MAX_SELECT_PIC_NUM - picPathList.size());
                break;
        }
    }

    //提交
    private void doUpload(){

        String dtText = edit_text_add_dt_ac.getText().toString();

        if((picPathList.size()==0||picPathList==null)&&(dtText==null||dtText.trim().equals(""))){
            return;
        }
        String deviceinfo = PhoneSystemUtil.getPhoneModel();

        List<File> files = new ArrayList<>();
        for(String s : picPathList){
            File file = new File(s);
            files.add(file);
        }

        setSeconds(60000);
        startLoadingDialog();

        Call<Result> call = DongtaiServicce.getInstance().addDongtai(files,dtText,deviceinfo, GlobalInfoUtil.personalInfo.getUserid());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                cancelLoadingDialog();
                Result result = response.body();
                String message = result.getMessage();
                if(message.equals("SUCCESS")){
                    Toast.makeText(AddNewDongtaiActiviy.this,"发布成功",Toast.LENGTH_SHORT).show();

                    if(DTTuijianFragment.tuijianFragment!=null){
                        DTTuijianFragment.tuijianFragment.requestNewDongtai();
                    }
                    finish();
                }else{
                    Toast.makeText(AddNewDongtaiActiviy.this,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                cancelLoadingDialog();
                Toast.makeText(AddNewDongtaiActiviy.this,"error!!!",Toast.LENGTH_SHORT).show();
            }
        });




    }


    /**
     * 打开相册或者照相机选择凭证图片，最多9张
     *  不用申请权限      这个PictureSelector里面自动申请
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {

        MyPictureSelectorUtil.initMultiConfig(this, maxTotal);

    }

    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i=0;i<picPathList.size();i++){
            arrayList.add(picPathList.get(i));
        }
        intent.putStringArrayListExtra(PictureSelectConstants.IMG_LIST,arrayList);
        intent.putExtra(PictureSelectConstants.POSITION, position);
        startActivityForResult(intent, PictureSelectConstants.REQUEST_CODE_MAIN);
    }


    public void onAdd(){
        num ++;
        if (num >= 9) {
            num = 9;
            iv_camera_select_pic_adddt.setImageResource(R.drawable.ic_add_pic_full);
            tv_hinttext_select_pic_adddt.setText("已选满");
            iv_camera_select_pic_adddt.setClickable(false);
        }else {
            iv_camera_select_pic_adddt.setImageResource(R.drawable.ic_add_pic);
            int t=9-num;
            tv_hinttext_select_pic_adddt.setText("剩余"+t+"张");
            iv_camera_select_pic_adddt.setClickable(true);
        }

        bindData();
    }


    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
//            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getPath(); //s图片路径
                picPathList.add(compressPath); //把图片添加到将要上传的图片数组中
                // mGridViewAddImgAdapter.notifyDataSetChanged();
                onAdd();
//            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == PictureSelectConstants.REQUEST_CODE_MAIN && resultCode == PictureSelectConstants.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(PictureSelectConstants.IMG_LIST); //要删除的图片的集合
            picPathList.clear();
            picPathList.addAll(toDeletePicList);
            //mGridViewAddImgAdapter.notifyDataSetChanged();
            num=toDeletePicList.size()-1;
            int t=9-num-1;

            tv_hinttext_select_pic_adddt.setText("剩余"+t+"张");
            bindData();
        }
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//                if(requestCode==1){
//                    if (resultCode != RESULT_OK){
//                        Toast.makeText(AddNewDongtaiActiviy.this,"请打开权限！",Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    if (resultCode == RESULT_OK) {
//                        switch (requestCode) {
//
//                            case PictureConfig.CHOOSE_REQUEST:
//                                // 图片选择结果回调
//                                refreshAdapter(PictureSelector.obtainMultipleResult(data));
//                                // 例如 LocalMedia 里面返回三种path
//                                // 1.media.getPath(); 为原图path
//                                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
//                                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
//                                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                                break;
//                        }
//                    }
//                    if (requestCode == PictureSelectConstants.REQUEST_CODE_MAIN && resultCode == PictureSelectConstants.RESULT_CODE_VIEW_IMG) {
//                        //查看大图页面删除了图片
//                        ArrayList<String> toDeletePicList = data.getStringArrayListExtra(PictureSelectConstants.IMG_LIST); //要删除的图片的集合
//                        picPathList.clear();
//                        picPathList.addAll(toDeletePicList);
//                        //mGridViewAddImgAdapter.notifyDataSetChanged();
//                        num=toDeletePicList.size()-1;
//                        int t=9-num-1;
//
//                        tv_hinttext_select_pic_adddt.setText("剩余"+t+"张");
//                        bindData();
//                    }
//                }
//
//    }


}
