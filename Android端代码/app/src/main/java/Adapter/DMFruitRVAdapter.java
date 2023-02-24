package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellofood.R;

import java.util.List;

import Bean.DMFoodBeaan;
import Bean.FoodBean;
import Service.ImageLoader;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DMFruitRVAdapter extends RecyclerView.Adapter<DMFruitRVAdapter.recommendholder> {
    private static final String TAG = "DMFruitRVAdapter";
    List<DMFoodBeaan> list;
    private Context context;
    public DMFruitRVAdapter(Context context, List list){
        this.context=context;
        this.list=list;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updata(List<DMFoodBeaan> list){
        this.list=list;
        Log.i(TAG, "updata: 更新数据成功");
        Log.i(TAG, "updata: "+list.get(1).toString());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public recommendholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_rv_dmfruit, parent, false);
        return new recommendholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull recommendholder holder, int position) {
        DMFoodBeaan foodBean = list.get(position);
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
        @BindView(R.id.tv_heat)
        TextView heat;
        @BindView(R.id.iv_cd)
        ImageView image;
        @BindView(R.id.tv_effect)
        TextView tv_effect;


        public recommendholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        //设置数据
        public void setdata(DMFoodBeaan foodBean) {
            title.setText(foodBean.getName());
            ImageLoader.Imageloadenoholder(context,foodBean.getImage(),image);
            heat.setText(foodBean.getHeat());
            tv_effect.setText(foodBean.getEffect());

        }
    }
}
