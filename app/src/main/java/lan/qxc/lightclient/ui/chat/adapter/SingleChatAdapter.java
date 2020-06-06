package lan.qxc.lightclient.ui.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lan.qxc.lightclient.R;
import lan.qxc.lightclient.entity.message.ChatMsgType;
import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.retrofit_util.api.APIUtil;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.ImageUtil;
import lan.qxc.lightclient.util.MyTimeUtil;

public class SingleChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SingleChatMsg> messages;
    private LayoutInflater layoutInflater;

    private int pos=-1;

    public static enum ITEM_TYPE{
        ITEM_TYPE_SEND_TEXT,
        ITEM_TYPE_RECEIVE_TEXT,

        ITEM_TYPE_PIC,
        ITEM_TYPE_VOICE,
    }

    public int getPos(){
        return pos;
    }

    public SingleChatAdapter(Context context,List<SingleChatMsg> messages){
        this.context = context;
        this.messages = messages;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType== ITEM_TYPE.ITEM_TYPE_SEND_TEXT.ordinal()){
            return new TextSendViewHolder(layoutInflater.inflate(R.layout.item_single_chat_send_text,parent,false));
        }else if(viewType== ITEM_TYPE.ITEM_TYPE_RECEIVE_TEXT.ordinal()){
            return new TextReceiveViewHolder(layoutInflater.inflate(R.layout.item_single_chat_receive_text,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TextSendViewHolder){
            TextSendViewHolder viewHolder = (TextSendViewHolder)holder;
            SingleChatMsg message = messages.get(position);
            ImageUtil.getInstance().setNetImageToView(context, APIUtil.getUrl(GlobalInfoUtil.personalInfo.getIcon()),viewHolder.iv_headic_send_text_singlechat_item);
            viewHolder.tv_text_send_text_singlechat_item.setText(message.getTextbody());

            String time = message.getCreatetime();
            Date date = MyTimeUtil.getDateByStr(time,"yyyy-MM-dd HH:mm:ss");
            String timestr = MyTimeUtil.getTime(date);
            viewHolder.tv_time_send_text_singlechat_item.setText(timestr);


        }else if(holder instanceof TextReceiveViewHolder){
            TextReceiveViewHolder viewHolder = (TextReceiveViewHolder)holder;

            SingleChatMsg message = messages.get(position);
            ImageUtil.getInstance().setNetImageToView(context, APIUtil.getUrl(message.getSendUicon()),viewHolder.iv_headic_receive_text_singlechat_item);
            viewHolder.tv_text_receive_text_singlechat_item.setText(message.getTextbody());

            String time = message.getCreatetime();
            Date date = MyTimeUtil.getDateByStr(time,"yyyy-MM-dd HH:mm:ss");
            String timestr = MyTimeUtil.getTime(date);
            viewHolder.tv_time_receive_text_singlechat_item.setText(timestr);

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();

    }

    @Override
    public int getItemViewType(int position){

        pos = position;

        SingleChatMsg singleChatMsg = messages.get(position);

        int msgtype = singleChatMsg.getMsgtype();
        //文字消息
        if(msgtype== ChatMsgType.SINGLE_CHAT_TEXT_MSG){
            if(singleChatMsg.getSendUid().equals( GlobalInfoUtil.personalInfo.getUserid())){
                return ITEM_TYPE.ITEM_TYPE_SEND_TEXT.ordinal();
            }
            return ITEM_TYPE.ITEM_TYPE_RECEIVE_TEXT.ordinal();
        }

        return ITEM_TYPE.ITEM_TYPE_SEND_TEXT.ordinal();
    }


    class TextSendViewHolder extends RecyclerView.ViewHolder{

        TextView tv_time_send_text_singlechat_item;
        CircleImageView iv_headic_send_text_singlechat_item;
        TextView tv_text_send_text_singlechat_item;


        public TextSendViewHolder(View view) {
            super(view);
            tv_time_send_text_singlechat_item = view.findViewById(R.id.tv_time_send_text_singlechat_item);
            iv_headic_send_text_singlechat_item = view.findViewById(R.id.iv_headic_send_text_singlechat_item);
            tv_text_send_text_singlechat_item = view.findViewById(R.id.tv_text_send_text_singlechat_item);
        }

    }

    class TextReceiveViewHolder extends RecyclerView.ViewHolder{

        TextView tv_time_receive_text_singlechat_item;
        CircleImageView iv_headic_receive_text_singlechat_item;
        TextView tv_text_receive_text_singlechat_item;


        public TextReceiveViewHolder(View view) {
            super(view);
            tv_time_receive_text_singlechat_item = view.findViewById(R.id.tv_time_receive_text_singlechat_item);
            iv_headic_receive_text_singlechat_item = view.findViewById(R.id.iv_headic_receive_text_singlechat_item);
            tv_text_receive_text_singlechat_item = view.findViewById(R.id.tv_text_receive_text_singlechat_item);
        }

    }



}
