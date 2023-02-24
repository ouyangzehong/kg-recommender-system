package Contract;

import java.util.List;

import Base.AbstractBasePresenter;
import Base.BaseView;
import Bean.FoodBean;

public interface RecommendFragmentContract {

    interface RecommendFragmentView extends BaseView {
        // 数据返回成功 显示数据
        void setdata( List<FoodBean> foodBeans );
        // 数据返回失败
        @Override
        void showError();
    }


    interface Presenter extends AbstractBasePresenter<RecommendFragmentView> {

    }
}
