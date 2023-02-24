package Service;

import android.app.Application;

import com.xiasuhuei321.loadingdialog.manager.StyleManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StyleManager s = new StyleManager();
    }
}
