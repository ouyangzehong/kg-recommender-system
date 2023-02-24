package Base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        onViewCreated();

        initEventAndData();
    }
    /**
     * view 的创建 留给子类实现
     */
    protected abstract void onViewCreated();
    /**
     * 初始化数据留给子类实现
     */
    protected abstract void initEventAndData();

    /**
     * 获取布局对象 留给子类实现
     */
    protected abstract int getLayout();

}

