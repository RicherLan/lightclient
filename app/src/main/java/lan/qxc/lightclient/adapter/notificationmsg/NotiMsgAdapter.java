package lan.qxc.lightclient.adapter.notificationmsg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lan.qxc.lightclient.R;
import lan.qxc.lightclient.adapter.dongtai.DongtaiAdapter;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.entity.message.ChatMsgType;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.entity.message.Message;
import lan.qxc.lightclient.entity.message.MessageType;
import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.retrofit_util.api.APIUtil;
import lan.qxc.lightclient.ui.fragment.home.NotificationFragment;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.ImageUtil;
import lan.qxc.lightclient.util.MyTimeUtil;

public class NotiMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Message> messages;
    private Context mContext;
    private LayoutInflater layoutInflater;

    private NotificationFragment.ClickLayoutListener clickLayoutListener;

    public NotiMsgAdapter(Context context, List<Message> messages){
        this.mContext = context;
        this.messages=messages;
        layoutInflater = LayoutInflater.from(context);
    }


    public void setClickLayoutListener(NotificationFragment.ClickLayoutListener layoutListener){
        this.clickLayoutListener = layoutListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType== MessageType.FRIEND_MSG){
            return new FriendMsgViewHolder(layoutInflater.inflate(R.layout.item_friend_msg_notifi,parent,false));
        }else if(viewType==MessageType.SINGLE_CHAT_MSG){
            return new SingleChatMsgViewHolder(layoutInflater.inflate(R.layout.item_single_chat_msg_notifi,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FriendMsgViewHolder){

            ((NotiMsgAdapter.FriendMsgViewHolder)holder).layout_friend_msg_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickLayoutListener.getPosition(position);
                }
            });
            Message message = messages.get(position);
            if(message.getType()==MessageType.FRIEND_MSG){
                FriendMsgVO friendMsgVO = (FriendMsgVO)message;
                String icpath = APIUtil.getUrl(friendMsgVO.getIcon());
                ImageUtil.getInstance().setNetImageToView(mContext,icpath,((NotiMsgAdapter.FriendMsgViewHolder)holder).iv_headic_friend_msg_item);

                ((NotiMsgAdapter.FriendMsgViewHolder)holder).tv_username_friend_msg_item.setText(friendMsgVO.getUsername());
                ((NotiMsgAdapter.FriendMsgViewHolder)holder).tv_msg_friend_msg_item.setText("关注了你");

                String time = friendMsgVO.getCreatetime();
                Date date = MyTimeUtil.getDateByStr(time,"yyyy-MM-dd HH:mm:ss");
                if(date!=null){
                    String timetext = MyTimeUtil.getTime(date);
                    ((NotiMsgAdapter.FriendMsgViewHolder)holder).tv_time_friend_msg_item.setText(timetext);
                }

                if(friendMsgVO.getReadstate()==0){
                    ((NotiMsgAdapter.FriendMsgViewHolder)holder).tv_msgnum_friend_msg_item.setVisibility(View.VISIBLE);
                    ((NotiMsgAdapter.FriendMsgViewHolder)holder).tv_msgnum_friend_msg_item.setText("1");
                }else{
                    ((NotiMsgAdapter.FriendMsgViewHolder)holder).tv_msgnum_friend_msg_item.setVisibility(View.INVISIBLE);
                }

            }


        }else if(holder instanceof SingleChatMsgViewHolder){
            ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).layout_single_chat_msg_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickLayoutListener.getPosition(position);
                }
            });
            SingleChatMsg message = (SingleChatMsg)messages.get(position);

            Long uid = message.getSendUid();
            if(uid.equals(GlobalInfoUtil.personalInfo.getUserid())){
                uid = message.getReceiveUid();
            }

                String icpath = APIUtil.getUrl(message.getSendUicon());
                ImageUtil.getInstance().setNetImageToView(mContext,icpath,((NotiMsgAdapter.SingleChatMsgViewHolder)holder).iv_headic_single_chat_msg_item);

                ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).tv_username_single_chat_msg_item.setText(message.getSendUername());

                if(message.getMsgtype()==ChatMsgType.SINGLE_CHAT_TEXT_MSG){
                    ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).tv_msg_single_chat_msg_item.setText(message.getTextbody());
                }else if(message.getMsgtype()==ChatMsgType.SINGLE_CHAT_VOICE_MSG){
                    ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).tv_msg_single_chat_msg_item.setText("语音消息");
                }else if(message.getMsgtype()==ChatMsgType.SINGLE_CHAT_PIC_MSG){
                    ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).tv_msg_single_chat_msg_item.setText("图片消息");
                }

                String time = message.getCreatetime();
                Date date = MyTimeUtil.getDateByStr(time,"yyyy-MM-dd HH:mm:ss");
                if(date!=null){
                    String timetext = MyTimeUtil.getTime(date);
                    ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).tv_time_single_chat_msg_item.setText(timetext);
                }

            int notreadmsgNum = MessageCacheUtil.getSingleChatMsgNotReadNumOfUid(uid);
                if(notreadmsgNum>99){
                    notreadmsgNum = 99;
                }
//            notreadmsgNum = 10;
                if(notreadmsgNum!=0){
                    ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).tv_msgnum_single_chat_msg_item.setVisibility(View.VISIBLE);
                    ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).tv_msgnum_single_chat_msg_item.setText(notreadmsgNum+"");
                }else{
                    ((NotiMsgAdapter.SingleChatMsgViewHolder)holder).tv_msgnum_single_chat_msg_item.setVisibility(View.INVISIBLE);
                }


        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    @Override
    public int getItemViewType(int position) {

        if(messages!=null&&messages.size()>position){
            return messages.get(position).getType();
        }

        return MessageType.SINGLE_CHAT_MSG;
    }


    class FriendMsgViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_friend_msg_item;
        CircleImageView iv_headic_friend_msg_item;

        TextView tv_username_friend_msg_item;
        TextView tv_msg_friend_msg_item;

        TextView tv_time_friend_msg_item;
        TextView tv_msgnum_friend_msg_item;

        FriendMsgViewHolder(View view) {
            super(view);

            layout_friend_msg_item = view.findViewById(R.id.layout_friend_msg_item);
            iv_headic_friend_msg_item = view.findViewById(R.id.iv_headic_friend_msg_item);

            tv_username_friend_msg_item = view.findViewById(R.id.tv_username_friend_msg_item);
            tv_msg_friend_msg_item = view.findViewById(R.id.tv_msg_friend_msg_item);

            tv_time_friend_msg_item = view.findViewById(R.id.tv_time_friend_msg_item);
            tv_msgnum_friend_msg_item = view.findViewById(R.id.tv_msgnum_friend_msg_item);
        }
    }

    class SingleChatMsgViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_single_chat_msg_item;
        CircleImageView iv_headic_single_chat_msg_item;

        TextView tv_username_single_chat_msg_item;
        TextView tv_msg_single_chat_msg_item;

        TextView tv_time_single_chat_msg_item;
        TextView tv_msgnum_single_chat_msg_item;

        SingleChatMsgViewHolder(View view) {
            super(view);

            layout_single_chat_msg_item = view.findViewById(R.id.layout_single_chat_msg_item);
            iv_headic_single_chat_msg_item = view.findViewById(R.id.iv_headic_single_chat_msg_item);

            tv_username_single_chat_msg_item = view.findViewById(R.id.tv_username_single_chat_msg_item);
            tv_msg_single_chat_msg_item = view.findViewById(R.id.tv_msg_single_chat_msg_item);

            tv_time_single_chat_msg_item = view.findViewById(R.id.tv_time_single_chat_msg_item);
            tv_msgnum_single_chat_msg_item = view.findViewById(R.id.tv_msgnum_single_chat_msg_item);
        }
    }

}
