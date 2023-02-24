package Base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import butterknife.ButterKnife;

public abstract class BaseFragment <T extends AbstractBasePresenter> extends Fragment implements BaseView {
    private View parentView;
    protected FragmentActivity activity;
    protected Context context;
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayout(), container, false);
        //获取上下文
        activity = getActivity();
        context=getContext();
        //ButterKnife
        ButterKnife.bind(this,parentView);
        initEventAndData();

        return parentView;
    }

    /**
     * 获取布局对象 留给子类实现
     */
    protected abstract int getLayout();
    /**
     * 创建Presenter
     */
    protected abstract T createPresenter();
    /**
     * 初始化数据留给子类实现
     */
    protected abstract void initEventAndData();

    @Override
    public void onDestroy() {
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
