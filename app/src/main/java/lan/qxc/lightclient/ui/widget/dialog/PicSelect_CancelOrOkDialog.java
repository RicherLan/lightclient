package lan.qxc.lightclient.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import lan.qxc.lightclient.R;

/**
 * 取消或者确认类型的Dialog
 */

public class PicSelect_CancelOrOkDialog extends Dialog {

    public PicSelect_CancelOrOkDialog(Context context, String title) {
        //使用自定义Dialog样式
        super(context, R.style.picselect_dialog);
        //指定布局
        setContentView(R.layout.dialog_picselect_cancel_or_ok);
        //点击外部不可消失
        setCancelable(false);

        //设置标题
        TextView titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        titleTv.setText(title);

        findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                //取消
                cancel();
            }
        });

        findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                ok();
            }
        });
    }

    //确认
    public void ok() {
    }
}
