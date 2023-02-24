package Fragmentuse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellofood.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.DaGeWeekNotesRVAdapter;
import Adapter.WeekNotesRVAdapter;
import Bean.DaynotesBean;
import Bean.WeekNotesBean;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeekNotesFragment2 extends Fragment {

    @BindView(R.id.rv_weeknotes)
    RecyclerView rv_weeknotes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weeknotes2, container, false);
        ButterKnife.bind(this,view);
        initrv();
        return view;

    }

    private void initrv() {
        List<DaynotesBean> daynotesBeanList=new ArrayList<>();
        List<WeekNotesBean> weekNotesBeans=new ArrayList<>();
        weekNotesBeans.add(new WeekNotesBean("周一",daynotesBeanList));
        weekNotesBeans.add(new WeekNotesBean("周二",daynotesBeanList));
        weekNotesBeans.add(new WeekNotesBean("周三",daynotesBeanList));
        weekNotesBeans.add(new WeekNotesBean("周四",daynotesBeanList));
        weekNotesBeans.add(new WeekNotesBean("周五",daynotesBeanList));
        weekNotesBeans.add(new WeekNotesBean("周六",daynotesBeanList));
        weekNotesBeans.add(new WeekNotesBean("周日",daynotesBeanList));
        DaGeWeekNotesRVAdapter adapter=new DaGeWeekNotesRVAdapter(getContext(),weekNotesBeans);
        rv_weeknotes.setAdapter(adapter);
        rv_weeknotes.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}
