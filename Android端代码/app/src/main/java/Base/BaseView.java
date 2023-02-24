package Base;

public interface BaseView {

    /**
     * 正常显示
     */
    void showNormal();

    /**
     * 显示错误
     */
    void showError();

    /**
     * 正在加载
     */
    void showLoading();
    /**
     * 显示错误信息
     * @param errorMsg 错误信息
     */
    void showErrorMsg(String errorMsg);
}
