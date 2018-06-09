package com.orchidaceae.taotransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.orchidaceae.taotransaction.db.Users;

import org.litepal.crud.DataSupport;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText use = findViewById(R.id.use);
        final EditText pwd = findViewById(R.id.pwd);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        Button login = findViewById(R.id.onLogin);
        //登陆
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = use.getText().toString();
                String password = pwd.getText().toString();
                //判断不能为空
                if (user.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                //获取用户实例
                Users us = DataSupport.where("phone = ?",user).findFirst(Users.class);
                //判断用户不存在
                if (us == null){
                    Toast.makeText(LoginActivity.this,"用户名不存在！",Toast.LENGTH_LONG).show();
                    return;
                }
                //登陆判断
                if (user.equals(us.getPhone()) && password.equals(us.getPassword())){
                    SharedPreferences.Editor edit = getSharedPreferences("data",MODE_PRIVATE).edit();
                    edit.putBoolean("LoginStatus",true);
                    edit.putString("LoginUser",user);
                    edit.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    finishActivity(MainActivity.class.hashCode());
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_LONG).show();
                }
            }
        });
        //加载返回键
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //将工具栏的标题隐藏
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    //加载返回按键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
