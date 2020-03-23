package lan.qxc.lightclient.ui.activity.dongtai;

import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.dongtai.ViewPagerAdapter;
import lan.qxc.lightclient.config.PictureSelectConstants;
import lan.qxc.lightclient.ui.activity.base_activitys.BaseForCloseActivity;
import lan.qxc.lightclient.ui.widget.dialog.PicSelect_CancelOrOkDialog;


public class PlusImageActivity extends BaseForCloseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager; //展示图片的ViewPager
    private TextView positionTv; //图片的位置，第几张图片
    private ArrayList<String> imgList; //图片的数据源
    private int mPosition; //第几张图片
    private ViewPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_image);
        imgList = getIntent().getStringArrayListExtra(PictureSelectConstants.IMG_LIST);
        mPosition = getIntent().getIntExtra(PictureSelectConstants.POSITION, 0);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        positionTv = (TextView) findViewById(R.id.position_tv);
        findViewById(R.id.back_iv).setOnClickListener(this);
        findViewById(R.id.delete_iv).setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);

        mAdapter = new ViewPagerAdapter(this, imgList);
        viewPager.setAdapter(mAdapter);
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
    }

    //删除图片
    private void deletePic() {
        PicSelect_CancelOrOkDialog dialog = new PicSelect_CancelOrOkDialog(this, "要删除这张图片吗?") {
            @Override
            public void ok() {
                super.ok();
                imgList.remove(mPosition); //从数据源移除删除的图片
                setPosition();
                dismiss();
            }
        };
        dialog.show();
    }

    //设置当前位置
    private void setPosition() {
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }

    //返回上一个页面
    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(PictureSelectConstants.IMG_LIST, imgList);
        setResult(PictureSelectConstants.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        positionTv.setText(position + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                //返回
                back();
                break;
            case R.id.delete_iv:
                //删除图片
                deletePic();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
