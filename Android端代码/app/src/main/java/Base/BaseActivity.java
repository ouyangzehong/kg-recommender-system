package Base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import Util.SetStyle;
import butterknife.ButterKnife;

public abstract class  BaseActivity <T extends AbstractBasePresenter> extends AppCompatActivity implements BaseView{

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        ButterKnife.bind(this);

        initEventAndData();
//        SetStyle.changeStatusBarTextColor(getWindow(),true);
//        SetStyle.changeStatusBarbackColor(getWindow());
        
    }
    /**
     * 获取布局对象 留给子类实现
     */
    protected abstract int getLayout();
    /**
     * 初始化数据留给子类实现
     */
    protected abstract void initEventAndData();
    /**
     * 创建Presenter
     */
    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
    }



    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }
}    
