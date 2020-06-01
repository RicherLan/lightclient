package lan.qxc.lightclient.adapter.dongtai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lan.qxc.lightclient.R;
import lan.qxc.lightclient.config.mseeage_config.DongtaiMsgCacheUtil;
import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtaiMsg;
import lan.qxc.lightclient.entity.DongtaiMsgVO;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.retrofit_util.api.APIUtil;
import lan.qxc.lightclient.ui.widget.imagewarker.MessagePicturesLayout;
import lan.qxc.lightclient.util.ImageUtil;
import lan.qxc.lightclient.util.MyTimeUtil;

public class DongtaiMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<DongtailVO> dongtailVOS;
    private List<DongtaiMsgVO> dongtaiMsgs;

    private Context mContext;
    private LayoutInflater layoutInflater;

    private int pos=0;

    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    public static enum ITEM_TYPE{
        ITEM_TYPE_USER,
        ITEM_TYPE_AUTH,
        ITEM_TYPE_AD,
        TYPE_FOOT,
        TYPE_NONE;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public DongtaiMsgAdapter(Context context, List<DongtailVO> dongtailVOS,List<DongtaiMsgVO> dongtaiMsgs){
        this.mContext = context;
        this.dongtailVOS = dongtailVOS;
        this.dongtaiMsgs = dongtaiMsgs;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType== DongtaiMsgAdapter.ITEM_TYPE.ITEM_TYPE_USER.ordinal()){
            return new DongtaiMsgAdapter.DongtaiMsgViewHolder(layoutInflater.inflate(R.layout.item_dongtai_msg,parent,false));
        }else if (viewType== DongtaiMsgAdapter.ITEM_TYPE.TYPE_FOOT.ordinal()){
            return new DongtaiMsgAdapter.FootViewHolder(layoutInflater.inflate(R.layout.item_footer_dongtai_frag,parent,false));
        }

        return new DongtaiMsgAdapter.NoneViewHolder(layoutInflater.inflate(R.layout.item_none_dongtai_frag,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DongtaiMsgAdapter.DongtaiMsgViewHolder){

            DongtailVO dongtailVO = dongtailVOS.get(position);
            DongtaiMsgVO dongtaiMsg = dongtaiMsgs.get(position);


            String headIcPath = APIUtil.getUrl(dongtaiMsg.getIcon());
            ImageUtil.getInstance().setNetImageToView(mContext,headIcPath,((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).iv_headic_dt_msg_item);

            ((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).tv_nickname_dt_msg_item.setText(dongtaiMsg.getUsername());    //昵称


            String time = dongtaiMsg.getCreatetime();
            Date date = MyTimeUtil.getDateByStr(time,"yyyy-MM-dd HH:mm:ss");
            if(date!=null){
                String timetext = MyTimeUtil.getTime(date);
                ((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).tv_time_dt_msg_item.setText(timetext);    //时间
            }

            Byte msgtype = dongtaiMsg.getMsgtype();
            if(msgtype.equals(new Byte("1"))){
                ((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).tv_dongtai_text_dt_msg_item.setText("攒了我");   //类型，显示点赞/评论/转发消息
            }else if(msgtype.equals(new Byte("2"))){
                ((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).tv_dongtai_text_dt_msg_item.setText("评论了我");   //类型，显示点赞/评论/转发消息
            }else{
                ((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).tv_dongtai_text_dt_msg_item.setText("转发消息");   //类型，显示点赞/评论/转发消息
            }

            String picpath = "";
            if(dongtailVO.getDtpic()!=null&&!dongtailVO.getDtpic().isEmpty()) {
                String[] ss = dongtailVO.getDtpic().split(" ");
                for (String str : ss) {
                    picpath = APIUtil.getSLUrl(str);
                    break;
                }
            }
            if(picpath.equals("")){
                ((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).iv_item_dongtaimsg_pic.setVisibility(View.INVISIBLE);     //显示动态的第一张图片
            }else{
                ((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).iv_item_dongtaimsg_pic.setVisibility(View.VISIBLE);
                ImageUtil.getInstance().setNetImageToView(mContext,headIcPath,((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).iv_item_dongtaimsg_pic);
            }

            ((DongtaiMsgAdapter.DongtaiMsgViewHolder)holder).tv_item_dongtaimsg_text.setText(dongtailVO.getUsername()+"："+dongtailVO.getDtcontent());    //显示的内容

          //  EditText et_huifu_dt_msg_acti;     //点击回复的输入框



        }else if(holder instanceof DongtaiMsgAdapter.FootViewHolder){

            DongtaiMsgAdapter.FootViewHolder footViewHolder = (DongtaiMsgAdapter.FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }

        }else if(holder instanceof DongtaiMsgAdapter.NoneViewHolder){

        }


    }

    @Override
    public int getItemCount() {
        return dongtaiMsgs.size()+1;
    }

    @Override
    public int getItemViewType(int position){

        if(getItemCount()==1){
            return  DongtaiMsgAdapter.ITEM_TYPE.TYPE_NONE.ordinal();
        }

        if(position+1==getItemCount()){
            pos = position;
            return DongtaiMsgAdapter.ITEM_TYPE.TYPE_FOOT.ordinal();
        }

        return DongtaiMsgAdapter.ITEM_TYPE.ITEM_TYPE_USER.ordinal();
    }


    class DongtaiMsgViewHolder extends RecyclerView.ViewHolder {

        CircleImageView iv_headic_dt_msg_item;   //头像
        TextView tv_nickname_dt_msg_item;    //昵称
        TextView tv_time_dt_msg_item;    //时间

        TextView tv_dongtai_text_dt_msg_item;   //类型，显示点赞/评论/转发消息

        ImageView iv_item_dongtaimsg_pic;     //显示动态的第一张图片
        TextView tv_item_dongtaimsg_text;    //显示的内容

        EditText et_huifu_dt_msg_acti;     //点击回复的输入框


        DongtaiMsgViewHolder(View itemView) {
            super(itemView);
            iv_headic_dt_msg_item =  itemView.findViewById(R.id.iv_headic_dt_msg_item);
            tv_nickname_dt_msg_item =  itemView.findViewById(R.id.tv_nickname_dt_msg_item);
            tv_time_dt_msg_item =  itemView.findViewById(R.id.tv_time_dt_msg_item);
            tv_dongtai_text_dt_msg_item =  itemView.findViewById(R.id.tv_dongtai_text_dt_msg_item);
            iv_item_dongtaimsg_pic =  itemView.findViewById(R.id.iv_item_dongtaimsg_pic);

            tv_item_dongtaimsg_text =  itemView.findViewById(R.id.tv_item_dongtaimsg_text);
            et_huifu_dt_msg_acti =  itemView.findViewById(R.id.et_huifu_dt_msg_acti);

        }
    }


    class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    class NoneViewHolder extends RecyclerView.ViewHolder{

        NoneViewHolder(View itemView){
            super(itemView);
        }

    }



}
