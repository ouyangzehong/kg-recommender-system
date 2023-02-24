package Fragmentuse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bryant.editlibrary.BSearchEdit;
import com.example.hellofood.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Bean.FoodBean;
import Bean.SingleFoodBean;
import Service.EChartView;
import Service.ImageLoader;
import Service.MyCallBack;
import Service.NetClient;
import Util.DataUtil;
import Util.EChartOptionUtil;
import Util.GsonUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.Request;

public class FindFragment extends Fragment {
    private static final String TAG = "FindFragment";

    private ArrayList<String> list;
    private BSearchEdit bSearchEdit;
    //搜索框
    @BindView(R.id.search_et_input)
    EditText editText;
    //echart
    @BindView(R.id.barChart)
    EChartView barChart;
    //图像 名字 热量等信息
    @BindView(R.id.iv_find)
    ImageView iv_find;
    @BindView(R.id.tv_title_find)
    TextView tv_title_find;
    @BindView(R.id.tv_heat_find)
    TextView tv_heat_find;
    //提示
    @BindView(R.id.tv_tips_find)
    TextView tv_tips_find;
    //显示布局
    @BindView(R.id.rv_find)
    RelativeLayout rv_find;


    private String url="/requirefood";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this,view);
        initedit();
        barChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示

            }
        });
        //设置背景色
        barChart.setBackgroundColor(0);
        //设置填充透明度
//        barChart.getBackground().setAlpha(0);

        return view;


    }

    private void refreshBarChart(SingleFoodBean singleFoodBean){
        String heat = singleFoodBean.getHeat();
        String cho = singleFoodBean.getCho();
        String e460 = singleFoodBean.getE460();
        String fat = singleFoodBean.getFat();
        String pro = singleFoodBean.getPro();
        //定义一个数组x，用来显示星期几
        Object[] x = new Object[]{
                "热量", "碳水化合物", "纤维素", "脂肪", "蛋白质",
        };
        //用来显示每天对应的数据
        Object[] y = new Object[]{
                heat, cho, e460, fat, pro,
        };
        //刷新图表
        barChart.refreshEchartsWithOption(EChartOptionUtil.getBarChartOptions(x, y));
    }

    //初始化搜索框
    private void initedit() {
        list = new ArrayList<>();
        list.add("米饭");
        bSearchEdit = new BSearchEdit((Activity) getContext(),editText,300);
        bSearchEdit.build();
        bSearchEdit.setSearchList(list);
        bSearchEdit.setTextClickListener(new BSearchEdit.TextClickListener() {
            @Override
            public void onTextClick(int position, String text) {
                editText.setText(text);
            }
        });
        bSearchEdit.setPopup_bg(R.color.white30);

        //给软键盘的搜索设置监听
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_SEARCH){
                    //提示隐藏
                    tv_tips_find.setVisibility(View.GONE);
                    //添加搜索记录
                    String text=editText.getText().toString();//输入框的文本内容
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                    list.add(text);//将搜索记录添加进去
                    Collections.reverse(list);//将词条反转
                    Log.i(TAG, "onEditorAction: 历史记录"+list);
                    bSearchEdit.setSearchList(list);
                    // 获取数据 设置请求体
                    FormBody formBody = new FormBody.Builder().add("title", text).build();
                    Log.i(TAG, "onEditorAction: 请求食物"+text);
                    NetClient.getNetClient().postAsyncRequest(url, formBody, new MyCallBack() {
                        @Override
                        public void onFailure(int code) {
                            Log.i(TAG, "onFailure:单个食物信息查询失败 失败码 "+code);
                        }

                        @Override
                        public void onResponse(String json) {
                            SingleFoodBean singleFoodBean = GsonUtil.GsonToBean(json, SingleFoodBean.class);
                            Log.i(TAG, "onResponse: "+singleFoodBean.toString());
                            //更新显示数据 不能在子线程更新ui 使用runonuiThread
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //在这里更新ui
                                    ImageLoader.Imageloadenoholder(getContext(),singleFoodBean.getImage(),iv_find);
                                    tv_title_find.setText(singleFoodBean.getName());
                                    tv_heat_find.setText(singleFoodBean.getHeat());
                                    //显示Relaytivelayout
                                    rv_find.setVisibility(View.VISIBLE);
                                    //更新图表
                                    refreshBarChart(singleFoodBean);
                                    Log.i(TAG, "run: echarts数据更新");

                                }
                            });
                        }
                    });


                }
                return false;
            }
        });
    }

}
