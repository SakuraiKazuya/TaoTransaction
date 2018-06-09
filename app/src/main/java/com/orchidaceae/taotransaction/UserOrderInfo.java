package com.orchidaceae.taotransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orchidaceae.taotransaction.Tools.LoadData;
import com.orchidaceae.taotransaction.db.ProductInfo;
import com.orchidaceae.taotransaction.db.UserOrder;
import com.orchidaceae.taotransaction.db.Users;

import org.litepal.crud.DataSupport;

public class UserOrderInfo extends AppCompatActivity implements View.OnClickListener{

    private ProductInfo product;
    private Users reUser;
    RadioGroup tranAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_info);
        //检查登陆状态
        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        if (!sp.getBoolean("LoginStatus",false)){
            Toast.makeText(this,"当前未登录！",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //获取参数并从数据库查询数据
        Intent intent = getIntent();
        String ProductId = intent.getStringExtra("ProductionId");
        String phone = sp.getString("LoginUser",null);
        product = DataSupport.where("id = ?",ProductId).findFirst(ProductInfo.class,true);
        reUser = DataSupport.where("phone = ?",phone).findFirst(Users.class);
        onLoadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_ok:
                LoadOrder();
                break;
            case R.id.order_cal:
                finish();
                break;
        }
    }

    //获取界面控件实例并填充数据
    private void onLoadData(){
        //获取界面控件实例
        TextView reName = findViewById(R.id.order_re_name);
        TextView reNumber = findViewById(R.id.order_re_number);
        TextView reAddress = findViewById(R.id.order_re_address);
        ImageView productImage = findViewById(R.id.order_product_image);
        TextView productName = findViewById(R.id.order_product_name);
        TextView productPrice = findViewById(R.id.order_product_price);
        TextView productRoName = findViewById(R.id.ro_name);
        TextView roName = findViewById(R.id.order_ro_name);
        TextView roNumber = findViewById(R.id.order_ro_number);
        TextView roAddress = findViewById(R.id.order_ro_address);
        tranAction = findViewById(R.id.transaction_action);
        Button okOrder = findViewById(R.id.order_ok);
        Button calOrder = findViewById(R.id.order_cal);
        //填充控件内容
        reName.setText(reUser.getRealName());
        reNumber.setText(reUser.getPhone());
        reAddress.setText(reUser.getAddress());
        Glide.with(this).load(product.getPicture()).into(productImage);
        productName.setText(product.getProductName());
        productPrice.setText("¥" + product.getOldPrice());
        productRoName.setText(product.getShopId().getUserName());
        roName.setText(product.getShopId().getRealName());
        roNumber.setText(product.getShopId().getPhone());
        roAddress.setText(product.getShopId().getAddress());
        okOrder.setOnClickListener(this);
        calOrder.setOnClickListener(this);
    }
    private void LoadOrder(){
        //检查交易方式
        RadioButton rb = findViewById(tranAction.getCheckedRadioButtonId());
        String action = rb.getText().toString();
        double money;
        double freight;
        String pay;
        if (action.equals("线下交易")){
            money = 0;
            freight = 0;
            pay = "线下";
        }else{
            money = 0;
            freight = 0;
            pay = "线上";
            Toast.makeText(this,"暂不支持",Toast.LENGTH_LONG).show();
            return;
        }
        //检查余额
        if (reUser.getMoney() < money){
            Toast.makeText(this,"余额不足!",Toast.LENGTH_LONG).show();
            return;
        }
        //交易
        LoadData.Transaction(reUser.getId(),money,false);
        UserOrder order = new UserOrder();
        order.setProductInfo(product);
        order.setUserId(reUser);
        order.setTime(System.currentTimeMillis());
        order.setAction(action);
        order.setFreight(freight);
        order.setTotalPrice(product.getOldPrice() + freight);
        order.setPayMent(pay);
        order.setStatus(1);
        order.setRoTherId(product.getShopId());
        order.save();
        product.setStatus(2);
        product.save();
        Toast.makeText(this,"购买成功!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
