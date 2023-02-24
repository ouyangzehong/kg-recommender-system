package Fragmentuse.disease.data;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hellofood.R;

import Activity.DMSuitableFoodActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DMFragment extends Fragment {
    /**
     * 糖尿病
     */
    private int flag=0;
    private int flag_princeple=0;
    //控件实列化
    @BindView(R.id.ll_synopsis_dm)
    LinearLayout ll_synopsis_dm;
    @BindView(R.id.iv_synopsis_dm)
    ImageView iv_synopsis_dm;
    @BindView(R.id.tv_synopsis_dm)
    TextView tv_synopsis_dm;
    @BindView(R.id.ll_princeple_dm)
    LinearLayout ll_princeple_dm;
    @BindView(R.id.iv_princeple_dm)
    ImageView iv_princeple_dm;
    @BindView(R.id.tv_princeple_dm)
    TextView tv_princeple_dm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dm, container, false);
        ButterKnife.bind(this,view);
        return view;

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ll_synopsis_dm,R.id.ll_princeple_dm,R.id.ll_suitablefood_dm})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_synopsis_dm:
                if (flag==0){
                    iv_synopsis_dm.setImageResource(R.mipmap.arrow_down);
                    flag=1;
                    tv_synopsis_dm.setVisibility(View.VISIBLE);
                }else {
                    iv_synopsis_dm.setImageResource(R.mipmap.arrow_right);
                    flag=0;
                    tv_synopsis_dm.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_princeple_dm:
                if (flag_princeple==0){
                    iv_princeple_dm.setImageResource(R.mipmap.arrow_down);
                    flag_princeple=1;
                    tv_princeple_dm.setVisibility(View.VISIBLE);
                }else {
                    iv_princeple_dm.setImageResource(R.mipmap.arrow_right);
                    flag_princeple=0;
                    tv_princeple_dm.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_suitablefood_dm:
                //跳转
                DMSuitableFoodActivity.actionStart(getContext());
                break;

        }
    }

}
