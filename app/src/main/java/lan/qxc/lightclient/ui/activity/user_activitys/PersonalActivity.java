package lan.qxc.lightclient.ui.activity.user_activitys;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TimeUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;
import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.home.AdapterPage;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.fragment.personal.Personal_dongtai_fragment;
import lan.qxc.lightclient.ui.fragment.personal.Personal_info_fragment;
import lan.qxc.lightclient.ui.view.movingimage.MovingImageView;
import lan.qxc.lightclient.util.DensityUtil;
import lan.qxc.lightclient.util.DensityUtiltwo;
import lan.qxc.lightclient.util.PermissionUtil;

public class PersonalActivity extends BaseForCloseActivity implements View.OnClickListener {

    private MovingImageView moving_image_personal;

    private ImageView iv_back_personal;
    private TextView tv_title_nickname_personal;
    private TextView tv_edit_userinfo_personal;

    private CircleImageView iv_headicon_personal;
    private TextView tv_nickname_personal;

    private LinearLayout layout_guanzhu_num_personal;
    private LinearLayout layout_fenxi_num_personal;
    private LinearLayout layout_friend_num_personal;

    private TextView tv_guanzhu_num_personal;
    private TextView tv_fenxi_num_personal;
    private TextView tv_friend_num_personal;


    private SmartRefreshLayout smartrefresh_personal_layout;
    private int mOffset = 0;

    private AppBarLayout appabr_personal_layout;
    private CollapsingToolbarLayout collapsingtoolbar_layout_personal;
    private Toolbar toolbar_peronal;

    private TabLayout tablayout_personal;
    private ViewPager view_pager_personal;

    private Uri mImageUri;
    private File imageFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        initView();
        //初始化沉浸式
//        mImmersionBar.titleBar(toolbar_peronal).init();
        initViewPager();
        initEvent();
    }

    void initView(){
        moving_image_personal = this.findViewById(R.id.moving_image_personal);

        iv_back_personal = this.findViewById(R.id.iv_back_personal);
        tv_title_nickname_personal = this.findViewById(R.id.tv_title_nickname_personal);
        tv_edit_userinfo_personal = this.findViewById(R.id.tv_edit_userinfo_personal);

        iv_headicon_personal = this.findViewById(R.id.iv_headicon_personal);
        tv_nickname_personal = this.findViewById(R.id.tv_nickname_personal);

        layout_guanzhu_num_personal = this.findViewById(R.id.layout_guanzhu_num_personal);
        layout_fenxi_num_personal = this.findViewById(R.id.layout_fenxi_num_personal);
        layout_friend_num_personal = this.findViewById(R.id.layout_friend_num_personal);

        tv_guanzhu_num_personal = this.findViewById(R.id.tv_guanzhu_num_personal);
        tv_fenxi_num_personal = this.findViewById(R.id.tv_fensi_num_personal);
        tv_friend_num_personal = this.findViewById(R.id.tv_friend_num_personal);

        smartrefresh_personal_layout = this.findViewById(R.id.smartrefresh_personal_layout);

        appabr_personal_layout = this.findViewById(R.id.appabr_personal_layout);
        collapsingtoolbar_layout_personal = this.findViewById(R.id.collapsingtoolbar_layout_personal);
        toolbar_peronal = this.findViewById(R.id.toolbar_peronal);
        tablayout_personal = this.findViewById(R.id.tablayout_personal);
        view_pager_personal = this.findViewById(R.id.view_pager_personal);


    }

    void initViewPager(){
        AdapterPage adapterPage = new AdapterPage(getSupportFragmentManager());
        adapterPage.addFragment(new Personal_info_fragment(),"资料卡");
        adapterPage.addFragment(new Personal_dongtai_fragment(),"西西墙");
        view_pager_personal.setAdapter(adapterPage);

        tablayout_personal.setupWithViewPager(view_pager_personal);
        tablayout_personal.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

        view_pager_personal.setCurrentItem(1);
        tablayout_personal.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tablayout_personal,30,30);
            }
        });

    }


    void initEvent(){
        iv_back_personal.setOnClickListener(this);
        tv_edit_userinfo_personal.setOnClickListener(this);
        iv_headicon_personal.setOnClickListener(this);

        layout_guanzhu_num_personal.setOnClickListener(this);
        layout_fenxi_num_personal.setOnClickListener(this);
        layout_friend_num_personal.setOnClickListener(this);

        smartrefresh_personal_layout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mOffset = offset / 2;
                moving_image_personal.setTranslationY(mOffset);
            }


//            @Override
//            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
//
//                mOffset = offset / 2;
//                movingImageView.setTranslationY(mOffset);
//            }
        });

        smartrefresh_personal_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                Log.e("fsdf","jskdfj舒心");
            }
        });

        appabr_personal_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                moving_image_personal.setTranslationY(verticalOffset);
                if (Math.abs(verticalOffset) == DensityUtil.dp2px(300) - toolbar_peronal.getHeight()) {//关闭
//                    if (iswhite) {//变黑
//                        if (ImmersionBar.isSupportStatusBarDarkFont()) {
//                            mImmersionBar.statusBarDarkFont(true).init();
//                            isblack = true;
//                            iswhite = false;
//                        }
//                    }
                    tv_title_nickname_personal.setVisibility(View.VISIBLE);
                    collapsingtoolbar_layout_personal.setContentScrimResource(R.drawable.fragment_mine_pic);

                    iv_back_personal.setBackgroundResource(R.drawable.ic_back_black_30);
                    tv_edit_userinfo_personal.setTextColor(getResources().getColor(R.color.black));

                } else {  //展开
//                    if (isblack) { //变白
//                        mImmersionBar.statusBarDarkFont(false).init();
//                        isblack = false;
//                        iswhite = true;
//                    }
                    tv_title_nickname_personal.setVisibility(View.INVISIBLE);
                    collapsingtoolbar_layout_personal.setContentScrimResource(R.color.transparent);
                    iv_back_personal.setBackgroundResource(R.drawable.ic_back_black_30);
                    tv_edit_userinfo_personal.setTextColor(getResources().getColor(R.color.black));
//                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });


        imageCircleRun(iv_headicon_personal);

    }

    /*
        圆形滚动
     */
    private void imageCircleRun(View view){
        //动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        view.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_personal:
                finish();
                break;

            case R.id.tv_edit_userinfo_personal:

                break;

            case R.id.iv_headicon_personal:
                showChooseHeadIconDialog();
                break;

            case R.id.layout_guanzhu_num_personal:

                break;
            case R.id.layout_fenxi_num_personal:

                break;
            case R.id.layout_friend_num_personal:

                break;

                default:
                    break;

        }
    }




    /**
     * 通过反射修改踏遍layout的宽，其实相当于margin
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
     void setIndicator (TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field slidingTabIndicator=null;
        try {
            slidingTabIndicator = tabLayout.getDeclaredField("slidingTabIndicator");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        slidingTabIndicator.setAccessible(true);

        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) slidingTabIndicator.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    void showChooseHeadIconDialog(){
        final Dialog bottomDialog = new Dialog(this,R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_photo, null);
        bottomDialog.setContentView(contentView);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtiltwo.dp2px(this, 16f);
        params.bottomMargin = DensityUtiltwo.dp2px(this, 16f);
        contentView.setLayoutParams(params);

        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        TextView nextToPhotos=(TextView)contentView.findViewById(R.id.next_choose_photo);
        TextView nextToCamera=(TextView)contentView.findViewById(R.id.next_to_camera);
        TextView cancel=(TextView)contentView.findViewById(R.id.cancel_choose_photo);

        nextToPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] strings = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

                if(new PermissionUtil().isPermissionOk(PersonalActivity.this,strings)){
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    //打开文件
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 2);
                    bottomDialog.dismiss();
                }else{
                    requestPhotoPer(strings);
                }

            }
        });

        nextToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] strings = new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                if(new PermissionUtil().isPermissionOk(PersonalActivity.this,strings)){

                    takePhotoByCamera();

                    bottomDialog.dismiss();

                }else{
                    requestPhotoPer(strings);
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });


    }

    void requestPhotoPer( String[] strings){
        new PermissionUtil().requestPermission(this,1, strings);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                break;
            case 2:           //打开相册选择图片
                if(resultCode == RESULT_OK){
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;

            case 3:
                if(resultCode == RESULT_OK){

                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
                    iv_headicon_personal.setImageBitmap(bitmap);

                    cropPhoto(mImageUri);// 裁剪图片
                }
                break;

            case 4:      //裁剪之后返回图片
                if(resultCode == RESULT_OK){
                    System.out.println("2222222222222222222222222222222222222222222222");
                    Bundle bundle = data.getExtras();
                    setHeadIcon((Bitmap) bundle.getParcelable("data"));
                }
                System.out.println("1111111111111111111111111111111111111111111111111111");

                break;
        }

    }



    void takePhotoByCamera(){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的Intent
        if(takePhotoIntent.resolveActivity(getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            File imageFile = createImageFile();//创建用来保存照片的文件
            if(imageFile!=null){

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    /*7.0以上要通过FileProvider将File转化为Uri*/
                    mImageUri = FileProvider.getUriForFile(this,"lan.qxc.lightclient.provider",imageFile);
                }else {
                    /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                    mImageUri = Uri.fromFile(imageFile);
                }
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);//将用于输出的文件Uri传递给相机
                startActivityForResult(takePhotoIntent, 3);//打开相机
            }
        }

    }
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    public void cropPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //android 7  以上版本要加上这两句话   否则  显示    照片无法加载
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 4);
    }

    private void setHeadIcon(Bitmap bitmap){
        if(bitmap!=null){

            iv_headicon_personal.setImageBitmap(bitmap);

        }
    }

}
