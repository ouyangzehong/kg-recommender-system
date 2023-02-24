package Base;

public class RxBasePresenter<T extends BaseView> implements AbstractBasePresenter<T>{

    protected T mView;
    
    private DataManager mdatamanager;


    public RxBasePresenter(DataManager dataManager){
        this.mdatamanager=dataManager;
    }

    @Override
    public void attachView(T view) {
       this.mView=view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
