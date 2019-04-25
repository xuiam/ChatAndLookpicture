package com.example.a515_01.homework1.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a515_01.homework1.R;
import com.example.a515_01.homework1.sqlite.MyDBOpenHelper;
import com.example.a515_01.homework1.util.ConfigUtils;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText et_login_username;//账号输入
    private EditText et_login_password;//密码输入
    private CheckBox cb_login_rememberPassword;//记住密码
    private CheckBox cb_login_autoLogin;//自动登录
    private Button btn_login_login;//登录
    private Button btn_login_register;//注册
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;

    /**
     * 布局组件初始化
     */
    private void initView() {
        et_login_username = findViewById(R.id.et_login_username);
        et_login_password = findViewById(R.id.et_login_password);
        cb_login_rememberPassword = findViewById(R.id.cb_login_rememberPassword);
        cb_login_autoLogin = findViewById(R.id.cb_login_autoLogin);
        btn_login_login = findViewById(R.id.btn_login_login);
        btn_login_register = findViewById(R.id.btn_login_register);
        btn_login_login.setOnClickListener(this);//为组件设置监听
        btn_login_register.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDBHelper = new MyDBOpenHelper(LoginActivity.this);
        db = myDBHelper.getWritableDatabase();
        initView();//初始化组件
        readUser();//读取配置信息
    }

    /**
     * 用户登录
     */
    private void login() {
        final String username = et_login_username.getText().toString().trim();
        final String password = et_login_password.getText().toString().trim();
        if (username.length() < 1 || password.length() < 1) {
            Toast.makeText(this, "用户名和或密码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ?",
                    new String[]{username});
            //存在数据才返回true
            if (cursor.moveToFirst()) {
                String resPassword = cursor.getString(cursor.getColumnIndex("password"));
                Log.d(TAG, "login: "+resPassword);
                if (resPassword.equals(password)) {
                    cursor.close();
                    saveUser(username,password);
                    gotoHome();
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "用户名不存在", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }
    }

    /**
     * 跳转到主页
     */
    private void gotoHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    /**
     * 用户注册
     */
    private void register() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    /**
     * 保存用户配置信息
     *
     * @param username
     * @param password
     */
    private void saveUser(String username, String password) {
        ConfigUtils.put(this, "username", username);
        if (cb_login_rememberPassword.isChecked()) {//是否记住密码
            ConfigUtils.put(this, "password", password);
            ConfigUtils.put(this, "rememberPassword", true);
            if (cb_login_autoLogin.isChecked()) {
                ConfigUtils.put(this, "autoLogin", true);
            } else {
                ConfigUtils.remove(this, "autoLogin");
            }
        } else {
            ConfigUtils.remove(this, "password");
            ConfigUtils.remove(this, "rememberPassword");
            ConfigUtils.remove(this, "autoLogin");
        }
    }

    /**
     * 读取用户配置信息
     */
    private void readUser() {
        et_login_username.setText(ConfigUtils.get(this, "username", "").toString());
        et_login_password.setText(ConfigUtils.get(this, "password", "").toString());
        cb_login_rememberPassword.setChecked((Boolean) ConfigUtils.get(this, "rememberPassword", false));
        if ((Boolean) ConfigUtils.get(this, "autoLogin", false)) {
            cb_login_autoLogin.setChecked(true);
            Toast.makeText(LoginActivity.this, "正在自动登录...", Toast.LENGTH_SHORT).show();
            login();//自动登录
        }
    }

    /**
     * 事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                login();
                break;
            case R.id.btn_login_register:
                register();
                break;
        }
    }
}
