package lan.qxc.lightclient.ui.widget.bigimage_looker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;


import java.util.List;

import lan.qxc.lightclient.R;

public class BigImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> paths;
    private List<String> titles;

    private PhotoView pv_item_image;
    private TextView tv_index;
    private TextView tv_title;

    private BigImageLongPressListener longPressListener;


    public BigImagePagerAdapter(Context context,List<String> paths,List<String> titles){
        this.context = context;
        this.paths = paths;
        this.titles = titles;
    }


    public void setLongPressListener(BigImageLongPressListener longPressListener){
        this.longPressListener = longPressListener;
    }

    @Override
    public int getCount() {
        if(paths==null){
            return 0;
        }
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bigimage_looker,null);
        pv_item_image = view.findViewById(R.id.pv_item_image);
        tv_index = view.findViewById(R.id.tv_index_item_bigimage);
        tv_title = view.findViewById(R.id.tv_title_item_bigimage);

        if(titles!=null&&titles.size()>position){
            tv_title.setText(titles.get(position));
        }

        tv_index.setText((position+1)+"/"+paths.size());
        final String url = paths.get(position);//图片的url


        Glide.with(context)
                .load(url)
                .into(pv_item_image);
        container.addView(view);


        pv_item_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longPressListener.longPress(position);
                return true;
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
