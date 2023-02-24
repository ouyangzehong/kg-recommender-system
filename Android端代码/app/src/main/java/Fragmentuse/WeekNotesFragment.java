package Fragmentuse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellofood.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.WeekNotesRVAdapter;
import Bean.DaynotesBean;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeekNotesFragment extends Fragment {
    private int flags[]={0,0,0,0,0,0,0};
    private int position;
    private WeekNotesRVAdapter  weekNotesRVAdapter;
    //控件实列化
    @BindView(R.id.rv_monday)
    RecyclerView rv_monday;
    @BindView(R.id.ll_monday_note)
    LinearLayout ll_monday_note;
    @BindView(R.id.ll_addnote_moday)
    LinearLayout ll_addnote_moday;
    @BindView(R.id.iv_add_monday_note)
    ImageView iv_add_monday_note;
    @BindViews({R.id.iv_arrowright_monday})
    List<ImageView> imageViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weeknotes, container, false);
        ButterKnife.bind(this,view);
        initRV();
        return view;

    }


    @OnClick({R.id.ll_monday_note})
    public void llonclick(View view){
        switch (view.getId()){
            case R.id.ll_monday_note:
                position=0;
                if (flags[position]==0){
                    flags[position]=1;
                    imageViews.get(position).setImageResource(R.mipmap.arrow_down);
                    rv_monday.setVisibility(View.VISIBLE);
                    ll_addnote_moday.setVisibility(View.VISIBLE);
                }else {
                    flags[position]=0;
                    imageViews.get(position).setImageResource(R.mipmap.arrow_right);
                    rv_monday.setVisibility(View.GONE);
                    ll_addnote_moday.setVisibility(View.GONE);
                }
        }
    }

    private void initRV() {
        List<DaynotesBean> daynotesBeanList=new ArrayList<>();
        daynotesBeanList.add(new DaynotesBean("橘子","5"));
        daynotesBeanList.add(new DaynotesBean("苹果","10"));
        daynotesBeanList.add(new DaynotesBean("鸡腿","8"));
        weekNotesRVAdapter=new WeekNotesRVAdapter(getContext(),daynotesBeanList);
        rv_monday.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_monday.setAdapter(weekNotesRVAdapter);
    }

}
