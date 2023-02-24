package Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.hellofood.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import Base.BaseActivity;
import Base.DataManager;
import Bean.FoodBean;
import Contract.MainContract;
import Fragmentuse.FirstFragment;
import Fragmentuse.SecondFragment;
import Fragmentuse.ThirdFragment;
import Fragmentuse.demo;
import Presenter.MainPresenter;
import Service.MyCallBack;
import Service.NetClient;
import Util.DataUtil;
import Util.GsonUtil;
import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.MainView {
    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(new DataManager());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            bottomNavigationView=findViewById(R.id.bottomNavigationView);
            FirstFragment fragment = new FirstFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }
        if (savedInstanceState!=null){
            initEventAndData();
        }
    }


    @Override
    protected void initEventAndData() {

        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.firstFragment:
                        fragment=new FirstFragment();
                        break;
                    case R.id.secondFragment:
                        fragment=new SecondFragment();
                        break;
                    case R.id.thirdFragment:
                        fragment=new ThirdFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                return true;
            }
        });
    }



}