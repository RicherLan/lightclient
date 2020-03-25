package lan.qxc.lightclient.adapter.dongtai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;
import lan.qxc.lightclient.R;
import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.retrofit_util.api.APIUtil;
import lan.qxc.lightclient.ui.fragment.dongtai.DTTuijianFragment;
import lan.qxc.lightclient.ui.widget.imagewarker.MessagePicturesLayout;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.ImageUtil;

public class DongtaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DongtailVO> dongtais;
    private Context mContext;
    private LayoutInflater layoutInflater;

    private DTTuijianFragment.ClickTransmitListener transmitListener;
    private DTTuijianFragment.ClickCommonListener commonListener;
    private DTTuijianFragment.ClickLikeListener likeListener;

    private MessagePicturesLayout.Callback mCallback;      //点击某张图片  放大显示

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
        TYPE_FOOT;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }


    public DongtaiAdapter(Context context, List<DongtailVO> dongtailVOS){
        this.mContext = context;
        this.dongtais=dongtailVOS;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setTransmitListener(DTTuijianFragment.ClickTransmitListener transmitListener){
        this.transmitListener = transmitListener;
    }
    public void setCommonListener(DTTuijianFragment.ClickCommonListener commonListener){
        this.commonListener = commonListener;
    }
    public void setLikeListener(DTTuijianFragment.ClickLikeListener likeListener){
        this.likeListener = likeListener;
    }

    public DongtaiAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==ITEM_TYPE.ITEM_TYPE_USER.ordinal()){
            return new DongtaiViewHolder(layoutInflater.inflate(R.layout.item_dongtai,parent,false));
        }else if (viewType==ITEM_TYPE.TYPE_FOOT.ordinal()){
            return new FootViewHolder(layoutInflater.inflate(R.layout.item_footer_dongtai_frag,parent,false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {


        if(holder instanceof DongtaiViewHolder){
            DongtailVO dongtailVO = dongtais.get(position);
            ((DongtaiViewHolder)holder).tv_nickname_dt_item.setText(dongtailVO.getUsername());
            ((DongtaiViewHolder)holder).tv_phone_type_dt_item.setText("来自  android 28");
            ((DongtaiViewHolder)holder).tv_time_dt_item.setText(dongtailVO.getDtcreatetime().substring(dongtailVO.getDtcreatetime().indexOf("-")));

            String headIcPath = APIUtil.getUrl(dongtailVO.getIcon());
            ImageUtil.getInstance().setNetImageToView(mContext,headIcPath,((DongtaiViewHolder)holder).iv_headic_dt_item);

            ((DongtaiViewHolder)holder).tv_dongtai_text_dt_item.setText(dongtailVO.getDtcontent());
            if(dongtailVO.getDtpic()!=null&&!dongtailVO.getDtpic().isEmpty()){
                List<String> iclist=new ArrayList<String>();
                String [] ss = dongtailVO.getDtpic().split(" ");
                for(String str : ss){
                    iclist.add(APIUtil.getUrl(str));
                }

                ((DongtaiViewHolder)holder).layout_dongtai_pic_dt_item.set(iclist,iclist);
            }


            ((DongtaiViewHolder)holder).tv_transmit_text_dt_item.setText("863");
            ((DongtaiViewHolder)holder).tv_common_text_dt_item.setText("7");
            ((DongtaiViewHolder)holder).tv_like_text_dt_item.setText("5.3万");


            ((DongtaiViewHolder)holder).layout_transmit_dt_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transmitListener.clickTransmit(position);
                }
            });
            ((DongtaiViewHolder)holder).layout_common_dt_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonListener.clickCommon(position);
                }
            });
            ((DongtaiViewHolder)holder).layout_like_dt_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeListener.clickLike(position);
                }
            });

        }else if(holder instanceof FootViewHolder){

            FootViewHolder footViewHolder = (FootViewHolder) holder;
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

        }


    }

    @Override
    public int getItemCount() {
        return dongtais.size()+1;
    }

    @Override
    public int getItemViewType(int position){
        if(position+1==getItemCount()){
            pos = position;
            return ITEM_TYPE.TYPE_FOOT.ordinal();
        }

        return ITEM_TYPE.ITEM_TYPE_USER.ordinal();
    }


    class DongtaiViewHolder extends RecyclerView.ViewHolder{

        //用户信息
        CircleImageView iv_headic_dt_item;
        TextView tv_nickname_dt_item;
        TextView tv_time_dt_item;
        TextView tv_phone_type_dt_item;

        //右上角关注
        LinearLayout layout_guanzhu_dt_item;
        ImageView iv_guanzhu_addlable_dt_item;
        TextView tv_guanzhu_text_dt_item;

        //动态主体
        TextView tv_dongtai_text_dt_item;
        MessagePicturesLayout layout_dongtai_pic_dt_item;

        //转发
        LinearLayout layout_transmit_dt_item;
        ImageView iv_transmit_label_dt_item;
        TextView tv_transmit_text_dt_item;

        //评论
        LinearLayout layout_common_dt_item;
        ImageView iv_common_label_dt_item;
        TextView tv_common_text_dt_item;

        //点赞
        LinearLayout layout_like_dt_item;
        ImageView iv_like_label_dt_item;
        TextView tv_like_text_dt_item;

        public DongtaiViewHolder(View view){
            super(view);
            iv_headic_dt_item = view.findViewById(R.id.iv_headic_dt_item);
            tv_nickname_dt_item = view.findViewById(R.id.tv_nickname_dt_item);
            tv_time_dt_item = view.findViewById(R.id.tv_time_dt_item);
            tv_phone_type_dt_item = view.findViewById(R.id.tv_phone_type_dt_item);

            layout_guanzhu_dt_item = view.findViewById(R.id.layout_guanzhu_dt_item);
            iv_guanzhu_addlable_dt_item = view.findViewById(R.id.iv_guanzhu_addlable_dt_item);
            tv_guanzhu_text_dt_item = view.findViewById(R.id.tv_guanzhu_text_dt_item);

            tv_dongtai_text_dt_item = view.findViewById(R.id.tv_dongtai_text_dt_item);
            layout_dongtai_pic_dt_item = view.findViewById(R.id.layout_dongtai_pic_dt_item);

            layout_transmit_dt_item = view.findViewById(R.id.layout_transmit_dt_item);
            iv_transmit_label_dt_item = view.findViewById(R.id.iv_transmit_label_dt_item);
            tv_transmit_text_dt_item = view.findViewById(R.id.tv_transmit_text_dt_item);

            layout_common_dt_item = view.findViewById(R.id.layout_common_dt_item);
            iv_common_label_dt_item = view.findViewById(R.id.iv_common_label_dt_item);
            tv_common_text_dt_item = view.findViewById(R.id.tv_common_text_dt_item);

            layout_like_dt_item = view.findViewById(R.id.layout_like_dt_item);
            iv_like_label_dt_item = view.findViewById(R.id.iv_like_label_dt_item);
            tv_like_text_dt_item = view.findViewById(R.id.tv_like_text_dt_item);

            layout_dongtai_pic_dt_item.setCallback(mCallback);
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


}