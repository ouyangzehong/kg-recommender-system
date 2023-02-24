package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellofood.R;

import java.util.ArrayList;
import java.util.List;

import Bean.DMFoodBeaan;
import Bean.DaynotesBean;
import Bean.WeekNotesBean;
import Service.ImageLoader;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaGeWeekNotesRVAdapter extends RecyclerView.Adapter<DaGeWeekNotesRVAdapter.recommendholder> {
    private static final String TAG = "DMFruitRVAdapter";
    List<WeekNotesBean> list;
    private Context context;

    public DaGeWeekNotesRVAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updata(List<WeekNotesBean> list) {
        this.list = list;
        Log.i(TAG, "updata: 更新数据成功");
        Log.i(TAG, "updata: " + list.get(1).toString());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public recommendholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_rv_bigweeknotes, parent, false);
        return new recommendholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull recommendholder holder, int position) {
        WeekNotesBean weekNotesBean = list.get(position);
        holder.setdata(weekNotesBean);
//        holder.image.setBackground();
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class recommendholder extends RecyclerView.ViewHolder {
        List<DaynotesBean> daynotesBeanList = new ArrayList<>();
        WeekNotesRVAdapter weekNotesRVAdapter;
        private int flag = 0;
        private int flag_add = 0;
        //控件实列化
        //内部recyclerview
        @BindView(R.id.rv_monday)
        RecyclerView rv_monday;
        //添加数据的ll布局
        @BindView(R.id.ll_addnote_moday)
        LinearLayout ll_addnote_moday;
        //添加数据图标
        @BindView(R.id.iv_add_monday_note)
        ImageView iv_add_monday_note;
        //右箭头
        @BindView(R.id.iv_arrowright_monday)
        ImageView iv_arrowright_monday;
        //两个编辑框
        @BindView(R.id.et_name_monday)
        EditText et_name_monday;
        @BindView(R.id.et_content_monday)
        EditText et_content_monday;
        //添加食物的提示
        @BindView(R.id.tv_tianjiashuju_monday_note)
        TextView tv_tianjiashuju_monday_note;
        //显示周几
        @BindView(R.id.day_of_week)
        TextView day_of_week;


        public recommendholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        //设置数据
        public void setdata(WeekNotesBean weekNotesBean) {

            //显示周几 设置recyclerview
            day_of_week.setText(weekNotesBean.getDay());
            initRV();
        }

        //大的linearlayout
        @OnClick(R.id.ll_monday_note)
        public void llonclick(View view) {

            if (flag == 0) {
                flag = 1;
                iv_arrowright_monday.setImageResource(R.mipmap.arrow_down);
                rv_monday.setVisibility(View.VISIBLE);
                ll_addnote_moday.setVisibility(View.VISIBLE);
            } else {
                flag = 0;
                iv_arrowright_monday.setImageResource(R.mipmap.arrow_right);
                rv_monday.setVisibility(View.GONE);
                ll_addnote_moday.setVisibility(View.GONE);
            }

        }
        //点击添加按钮
        @OnClick(R.id.iv_add_monday_note)
        public void addonclick(View view) {
            if (flag_add==0){
                flag_add=1;
                //提示文字消失 显示两个输入框  改变图片
                tv_tianjiashuju_monday_note.setVisibility(View.GONE);
                et_content_monday.setVisibility(View.VISIBLE);
                et_name_monday.setVisibility(View.VISIBLE);
                iv_add_monday_note.setImageResource(R.mipmap.sava);
            }else {
                flag_add=0;
                // 添加列表数据 两个输入框隐藏 提示出来 图片改为添加
                et_content_monday.setVisibility(View.GONE);
                et_name_monday.setVisibility(View.GONE);
                tv_tianjiashuju_monday_note.setVisibility(View.VISIBLE);
                iv_add_monday_note.setImageResource(R.mipmap.jiahao);
                //获取数据 并进行本地list更新
                String name = et_name_monday.getText().toString();
                String content = et_content_monday.getText().toString();
                weekNotesRVAdapter.addOneNote(new DaynotesBean(name,content));
                Log.i(TAG, "addonclick: "+daynotesBeanList);
                //上传 todo
            }
        }

        private void initRV() {
            daynotesBeanList.add(new DaynotesBean("橘子", "5"));
            daynotesBeanList.add(new DaynotesBean("苹果", "10"));
            daynotesBeanList.add(new DaynotesBean("鸡腿", "8"));
            weekNotesRVAdapter = new WeekNotesRVAdapter(context, daynotesBeanList);
            rv_monday.setLayoutManager(new LinearLayoutManager(context));
            rv_monday.setAdapter(weekNotesRVAdapter);
        }
    }
}
