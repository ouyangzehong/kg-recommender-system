package Presenter;

import Base.DataManager;
import Base.RxBasePresenter;
import Contract.MainContract;

public class MainPresenter extends RxBasePresenter<MainContract.MainView> implements MainContract.MainActivityPresenter {

    //（Model）
    private DataManager mDataManager;

    public MainPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }


}

