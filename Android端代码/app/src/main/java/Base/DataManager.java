package Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.UserAccountBean;
import Service.MyCallBack;
import Service.NetClient;
import Util.GsonUtil;

public class DataManager  {

    private NetClient netClient= NetClient.getNetClient();

    /**
     * get请求
     * url “/login”
     * 获取用户账户信息
     *
     * @return
     */
    public List<UserAccountBean> getUserAccount(){
        List<UserAccountBean> list;
        final Map<String, List<UserAccountBean>> resultMap = new HashMap<>();
        netClient.getAsyncRequest("/login", new MyCallBack() {
            @Override
            public void onFailure(int code) {

            }

            @Override
            public void onResponse(String json) {

                List<UserAccountBean> list1 = GsonUtil.GsonToList(json, UserAccountBean.class);
                resultMap.put("json",list1);
            }
        });
        return resultMap.get("json");
    }
    public void getRecommenddata(MyCallBack mCallback){
        netClient.getAsyncRequest("/recommend",mCallback);
    }

}
