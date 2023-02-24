package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellofood.R;

import java.util.List;
import java.util.zip.Inflater;

import Bean.FoodBean;
import Bean.RecommendBean;
import Service.ImageLoader;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendRVAdapter extends RecyclerView.Adapter<RecommendRVAdapter.recommendholder> {
    List<FoodBean> list;
    private Context context;
    public RecommendRVAdapter(Context context,List list){
        this.context=context;
        this.list=list;
    }
    public void updata(List<FoodBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public recommendholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_rv_recommendfragment, parent, false);
        return new recommendholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull recommendholder holder, int position) {
        FoodBean foodBean = list.get(position);
        holder.setdata(foodBean);
//        holder.image.setBackground();
    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }
        return list.size();
    }

    public class recommendholder extends RecyclerView.ViewHolder {
//        ImageView image= itemView.findViewById(R.id.iv_cd);
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.iv_cd)
        ImageView image;
        @BindView(R.id.tv_heat)
        TextView heat;
        @BindView(R.id.tv_cho)
        TextView tv_cho;
        @BindView(R.id.tv_fat)
        TextView fat;
        @BindView(R.id.tv_e)
        TextView e460;
        @BindView(R.id.tv_pro)
        TextView pro;

        public recommendholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        //设置数据
        public void setdata(FoodBean foodBean) {
            title.setText(foodBean.getName());
            ImageLoader.Imageloadenoholder(context,foodBean.getImage(),image);
            heat.setText(foodBean.getHeat());
            tv_cho.setText(foodBean.getCho());
            fat.setText(foodBean.getFat());
            e460.setText(foodBean.getE460());
            pro.setText(foodBean.getPro());

        }
    }
}
