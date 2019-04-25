package com.example.a515_01.homework1.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a515_01.homework1.R;
import com.example.a515_01.homework1.bean.User;
import com.example.a515_01.homework1.sqlite.MyDBOpenHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private EditText et_register_username;
    private EditText et_register_password;
    private EditText et_register_password2;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDBHelper = new MyDBOpenHelper(RegisterActivity.this);
        db = myDBHelper.getWritableDatabase();
        initView();
    }

    private void initView() {
        et_register_username = (EditText) findViewById(R.id.et_register_username);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_password2 = (EditText) findViewById(R.id.et_register_password2);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(this);
    }

    /**
     * 保存用户
     *
     * @param user
     */
    public void insert(User user) {
        db.execSQL("INSERT INTO user(username,password) values(?,?)",
                new String[]{user.getUsername(), user.getPassword()});
    }

    /**
     * 检测用户是否存在
     *
     * @param username
     * @return
     */
    public boolean find(String username) {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ?",
                new String[]{username});
        //存在数据才返回true
        boolean res = cursor.moveToFirst();
        cursor.close();
        return res;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String username = et_register_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_register_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String password2 = et_register_password2.getText().toString().trim();
        if (TextUtils.isEmpty(password2)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(password2)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (find(username)) {
            Toast.makeText(this, "该账号已存在", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(username, password);
        insert(user);
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}
