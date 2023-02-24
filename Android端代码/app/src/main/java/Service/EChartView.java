package Service;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.abel533.echarts.json.GsonOption;

public class EChartView extends WebView {
    private static final String TAG = EChartView.class.getSimpleName();

    public EChartView(Context context) {
        this(context, null);
    }

    public EChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    	//EChartView其实就是WebView，对WebView的配置也要对EChartView进行相应的配置
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        loadUrl("file:///android_asset/echarts.html");
    }

    /**刷新图表
     * java调用js的loadEcharts方法刷新echart
     * 不能在第一时间就用此方法来显示图表，因为第一时间html的标签还未加载完成，不能获取到标签值
     * @param option
     */
    public void refreshEchartsWithOption(GsonOption option) {
        if (option == null) {
            return;
        }
        String optionString = option.toString();
        String call = "javascript:loadEcharts('" + optionString + "')";
        loadUrl(call);
    }


}
