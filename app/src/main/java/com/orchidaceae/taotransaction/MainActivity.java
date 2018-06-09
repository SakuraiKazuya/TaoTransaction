package com.orchidaceae.taotransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orchidaceae.taotransaction.Tools.CategoryFragment;
import com.orchidaceae.taotransaction.Tools.CategoryPagerAdapter;
import com.orchidaceae.taotransaction.Tools.LoadData;
import com.orchidaceae.taotransaction.db.ProductCategory;
import com.orchidaceae.taotransaction.db.Users;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private EditText searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //数据库初始化
        LitePal.getDatabase();
        LoadData load = new LoadData();
        load.AddProductCategory(this);
        //实例化界面控件
        searchContent = findViewById(R.id.sea_content);
        final Button seaBtn = findViewById(R.id.sea_btn);
        seaBtn.setOnClickListener(this);
        //获取SP实例
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        //获取登陆状态
        Boolean LoginStatus = pref.getBoolean("LoginStatus",false);
        final NavigationView navigation = findViewById(R.id.nav_view);
        final View headerView;
        //检查登陆状态
        if (LoginStatus){
            //加载已登录时的布局
            headerView = navigation.inflateHeaderView(R.layout.nav_header);
            String loginUser = pref.getString("LoginUser",null);
            Users user = DataSupport.where("phone = ?",loginUser).findFirst(Users.class);
            TextView username = headerView.findViewById(R.id.username);
            username.setText(user.getUserName());
            TextView money = headerView.findViewById(R.id.money);
            money.setText("¥" +  user.getMoney());
            TextView reputation = headerView.findViewById(R.id.reputation);
            reputation.setText("信誉：" + user.getReputation());
            Button loginOut = headerView.findViewById(R.id.login_out);
            loginOut.setOnClickListener(this);
            navigation.inflateMenu(R.menu.nav_menu);
            navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.release:
                            Toast.makeText(MainActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.home:
                            Toast.makeText(MainActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.order:
                            Intent order_intent = new Intent(MainActivity.this,OrderManageActivity.class);
                            startActivity(order_intent);
                            break;
                        case R.id.transaction:
                            Toast.makeText(MainActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
                            break;
                    }
                    drawerLayout.closeDrawers();
                    return false;
                }
            });
        }else {
            //加载未登录时的布局
            headerView = navigation.inflateHeaderView(R.layout.nav_no_header);
            Button login = headerView.findViewById(R.id.login);
            login.setOnClickListener(this);
        }
        //创建Top分类的Tab页
        TabLayout categoryTab = findViewById(R.id.category_tab);
        ViewPager categoryViewPager = findViewById(R.id.category_view_pager);
        List<String>cateList = new ArrayList<>();
        List<Fragment>mFragmentList = new ArrayList<>();
        //数据库查询Top分类
        List<ProductCategory> pc = DataSupport.where("subcategory = ?","1").find(ProductCategory.class);
        //循环添加Tab页和碎片页
        for (ProductCategory psc : pc){
            //添加Tab页
            categoryTab.addTab(categoryTab.newTab().setText(psc.getCatName()));
            //添加碎片并传值
            mFragmentList.add(CategoryFragment.newInstance(String.valueOf(psc.getId())));
            //添加Tab页的标题
            cateList.add(psc.getCatName());
        }
        //为ViewPager设置适配器
        categoryViewPager.setAdapter(new CategoryPagerAdapter(getSupportFragmentManager(),mFragmentList,cateList));
        //Tab页与ViewPager绑定
        categoryTab.setupWithViewPager(categoryViewPager);
        //获取按钮和侧边菜单实例
        drawerLayout = findViewById(R.id.drawer);
        final Button home_btn = findViewById(R.id.home_btn);
        home_btn.setOnClickListener(this);
        searchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    seaBtn.performClick();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.login_out:
                SharedPreferences.Editor edit = getSharedPreferences("data",MODE_PRIVATE).edit();
                edit.putBoolean("LoginStatus",false);
                edit.putString("LoginUser",null);
                edit.apply();
                Intent intent1 = new Intent(this,MainActivity.class);
                finish();
                startActivity(intent1);
                Toast.makeText(this,"退出成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sea_btn:
                String content = searchContent.getText().toString();
                if (content.equals("")||content.equals(" "))return;
                Intent seaIntent = new Intent(this,SearchActivity.class);
                seaIntent.putExtra("content",content);
                startActivity(seaIntent);
                break;
            case R.id.home_btn:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.home:
                Toast.makeText(this,"暂未开放",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
