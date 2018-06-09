package com.orchidaceae.taotransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orchidaceae.taotransaction.db.ProductInfo;

import org.litepal.crud.DataSupport;

public class ProductInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        //获取Intent
        final Intent intent = getIntent();
        final String productId = intent.getStringExtra("PRODUCT_ID");
        //通过Product_Id获取商品信息
        ProductInfo productInfo = DataSupport.where("id = ?",productId).findFirst(ProductInfo.class,true);
        String name = productInfo.getProductName();
        byte[] image = productInfo.getPicture();
        Double price = productInfo.getOldPrice();
        StringBuilder count = new StringBuilder();
        for (int i = 0; i < 300;i++){
            count.append(productInfo.getProductCount());
        }
        final String productionId = String.valueOf(productInfo.getId());
        //绑定控件
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView product_name = findViewById(R.id.product_name);
        TextView product_price = findViewById(R.id.product_price);
        TextView shop_id = findViewById(R.id.shop_id);
        TextView product_count = findViewById(R.id.product_count);
        ImageView product_image = findViewById(R.id.image_view);
        ImageView product_image_copy = findViewById(R.id.image_view_copy);
        FloatingActionButton faButton = findViewById(R.id.fabutton);
        //获取ActionBar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //将工具栏的标题隐藏
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //将获取到的数据传入页面控件
        Glide.with(this).load(image).into(product_image);
        Glide.with(this).load(image).into(product_image_copy);
        product_name.setText(name);
        product_price.setText(String.valueOf(price));
        shop_id.setText(productInfo.getShopId().getUserName());
        product_count.setText(count);
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
                if (!sp.getBoolean("LoginStatus",false)){
                    Toast.makeText(ProductInfoActivity.this,"当前未登录！",Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(ProductInfoActivity.this,LoginActivity.class);
                    startActivity(intent1);
                    return;
                }
                Intent intent1 = new Intent(ProductInfoActivity.this,UserOrderInfo.class);
                intent1.putExtra("ProductionId",productId);
                startActivity(intent1);
            }
        });
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
