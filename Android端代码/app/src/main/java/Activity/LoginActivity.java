package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hellofood.R;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import Base.DataManager;
import Bean.UserAccountBean;
import Service.MyCallBack;
import Service.NetClient;
import Util.GsonUtil;
import io.alterac.blurkit.BlurLayout;

public class LoginActivity extends AppCompatActivity implements TextWatcher {
    private static final String TAG = "LoginActivity";

    private DataManager dataManager;
    //用户账号信息
    private List<UserAccountBean> userlist=new ArrayList<>();

    //屏幕毛玻璃布局
    private BlurLayout blurLayout = null;
    //操作部分毛玻璃布局
    private BlurLayout input_blurLayout = null;

    //猫头鹰
    private ImageView leftHand = null;
    private ImageView rightHand = null;
    private ImageView leftArm = null;
    private ImageView rightArm = null;
    //操作部分
//    private TextView textView = null;
    private EditText userEditText = null;
    private EditText passwordEditText = null;
    private Button loginBtn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取用户信息
//        userlist=dataManager.getUserAccount();
        NetClient.getNetClient().getAsyncRequest("/login", new MyCallBack() {
            @Override
            public void onFailure(int code) {

            }

            @Override
            public void onResponse(String json) {
                LoginActivity.this.userlist= GsonUtil.GsonToList(json,UserAccountBean.class);
            }
        });
        //屏幕毛玻璃布局
        blurLayout = findViewById(R.id.blurLayout);
        //操作部分毛玻璃布局
        input_blurLayout = findViewById(R.id.input_blur);

        //猫头鹰
        leftHand = findViewById(R.id.owl_left_hand);
        rightHand = findViewById(R.id.owl_right_hand);
        leftArm = findViewById(R.id.owl_left_arm);
        rightArm = findViewById(R.id.owl_right_arm);

        //操作部分
//        textView = findViewById(R.id.operation_textview);
        userEditText = findViewById(R.id.operation_edittext_user);
        passwordEditText = findViewById(R.id.operation_edittext_password);
        loginBtn = findViewById(R.id.operation_btn_login);

        //小米手机刘海屏沉浸
        ImmersionBar.with(this).init();

        //监听密码输入框是否获得焦点
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    //获得焦点 关闭眼睛
                    Toast.makeText(getApplicationContext(),"关闭眼睛", Toast.LENGTH_SHORT).show();

                    //动画
                    closeAnimation(true, leftArm, leftHand);
                    closeAnimation(false, rightArm, rightHand);
                }else {
                    //失去焦点 打开眼睛
                    Toast.makeText(getApplicationContext(),"打开眼睛", Toast.LENGTH_SHORT).show();

                    //动画
                    openAnimation(true, leftArm, leftHand);
                    openAnimation(false, rightArm, rightHand);
                }
            }
        });

        //设置两个文本框的代理者
        userEditText.addTextChangedListener(this);
        passwordEditText.addTextChangedListener(this);

        //登录事件
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid=userEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                Log.i(TAG, "onClick: "+userlist.toString());
                int j=0;
                for (int i=j; i < userlist.size(); i++) {
                    UserAccountBean user = userlist.get(i);
                    Log.i(TAG, "onClick: "+user.toString());
                    if (userid.equals(user.getUserid()) && password.equals(user.getPassword())/*&&phoneCode.equals(realCode)*/) {
                        jumpToNextActivity();
                        break;
                    }
                }
                if (j==userlist.size()){
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //屏幕背景的遮罩
        blurLayout.startBlur();
        blurLayout.lockView();

        //操作部分背景的遮罩
        input_blurLayout.startBlur();
        input_blurLayout.lockView();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //屏幕背景的遮罩
        blurLayout.pauseBlur();

        //操作部分背景的遮罩
        input_blurLayout.pauseBlur();
    }

    //监听屏幕的触摸
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //手指按下的时候
        if (event .getAction()== MotionEvent.ACTION_DOWN){
            //判断当前是否有视图获得焦点
            if (getCurrentFocus() != null){
                //1.获取系统输入的管理器
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

                //2.隐藏键盘
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

                //3.取消焦点
                getCurrentFocus().clearFocus();
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 跳转界面
     */
    private void jumpToNextActivity(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 关闭眼睛
     * @param isLeftArm
     * @param arm
     * @param hand
     */
    private void closeAnimation(boolean isLeftArm, ImageView arm, ImageView hand){
        //补间动画 旋转 代码实现的方式
        RotateAnimation rotateAnimation = new RotateAnimation(0f, isLeftArm?120f:-120f, isLeftArm?leftArm.getWidth():0, 0);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        arm.startAnimation(rotateAnimation);

        //补间动画 平移 xml实现的方式
        Animation downAnimation = AnimationUtils.loadAnimation(this, R.anim.hand_down_translate_animation);
        hand.startAnimation(downAnimation);
    }

    /**
     * 打开眼睛
     * @param isLeftArm
     * @param arm
     * @param hand
     */
    private void openAnimation(boolean isLeftArm, ImageView arm, ImageView hand){
        //补间动画 旋转 代码实现的方式
        RotateAnimation rotateAnimation = new RotateAnimation(isLeftArm?120f:-120f, 0f, isLeftArm?leftArm.getWidth():0, 0);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        arm.startAnimation(rotateAnimation);

        //补间动画 平移 xml实现的方式
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.hand_up_translate_animation);
        hand.startAnimation(animation);
    }

    /**
     * 代理方法 文本改变前
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 代理方法 文本改变时
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 代理方法 文本改变后
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (userEditText.getText().toString().length() > 0 &&
                passwordEditText.getText().toString().length() > 0){
            //登录按钮可用
            loginBtn.setEnabled(true);
        }else {
            //登录按钮不可用
            loginBtn.setEnabled(false);
        }
    }
}