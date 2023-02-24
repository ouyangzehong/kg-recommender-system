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
import Bean.DaynotesBean;
import Service.ImageLoader;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekNotesRVAdapter extends RecyclerView.Adapter<WeekNotesRVAdapter.recommendholder> {
    private static final String TAG = "WeekNotesRVAdapter";
    List<DaynotesBean> list;
    private Context context;
    public WeekNotesRVAdapter(Context context, List list){
        this.context=context;
        this.list=list;
    }
    //更新数据
    @SuppressLint("NotifyDataSetChanged")
    public void updata(List<DaynotesBean> list){
        this.list=list;
        Log.i(TAG, "updata: 更新数据成功");
        Log.i(TAG, "updata: "+list.get(1).toString());
        notifyDataSetChanged();
    }
    //添加一个记录
    @SuppressLint("NotifyDataSetChanged")
    public void addOneNote(DaynotesBean daynotesBean){
        this.list.add(daynotesBean);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public recommendholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_rv_weeknote, parent, false);
        return new recommendholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull recommendholder holder, @SuppressLint("RecyclerView") int position) {
        DaynotesBean daynotesBean = list.get(position);
        holder.setdata(daynotesBean);
//        holder.image.setBackground();
        //点击减号删除一个
        holder.jianhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
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
        @BindView(R.id.tv_name_note)
        TextView name;
        @BindView(R.id.tv_content_note)
        TextView content;
        @BindView(R.id.iv_jianhao_note)
        ImageView jianhao;


        public recommendholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        //设置数据
        public void setdata(DaynotesBean daynotesBean) {
            name.setText(daynotesBean.getName());
            content.setText(daynotesBean.getContent());
        }
    }
}
