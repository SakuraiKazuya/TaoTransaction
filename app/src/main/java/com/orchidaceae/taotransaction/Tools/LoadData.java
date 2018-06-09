package com.orchidaceae.taotransaction.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.orchidaceae.taotransaction.R;
import com.orchidaceae.taotransaction.db.ProductCategory;
import com.orchidaceae.taotransaction.db.ProductInfo;
import com.orchidaceae.taotransaction.db.Users;
import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//数据加载类
public class LoadData {

    //往数据库添加分类数据
    public boolean AddProductCategory(Context context){
        //顶类数组
        String[] topCategory = {"电子数码","家居日用","鞋服配饰","影音家电"};
        //子类数组
        String[] aCategory = {"手机","笔记本","平板电脑","台式机","一体机","服务器","笔记本配件","电脑配件","游戏机","网络设备"};
        String[] bCategory = {"家具","家饰","日用品","家装建材","面部护理","洗发护发","身体护理","女性专用","化妆品","家庭清洁"};
        String[] cCategory = {"女装","女鞋","男装","男鞋","内衣","配饰","童装","童鞋"};
        String[] dCategory = {"大家电","小家电","厨房电器","耳机","音响","功放","播放器","收音机","图书"};
        //添加顶类到数据库
        ProductTopCategory(topCategory);
        //添加子类到数据库
        ProductSubCategory(aCategory,"电子数码");
        ProductSubCategory(bCategory,"家居日用");
        ProductSubCategory(cCategory,"鞋服配饰");
        ProductSubCategory(dCategory,"影音家电");
        //添加默认账号
        AddUser("帅气的开发者",
                "123456",
                "许嘉伟",
                "男",
                "431XXXXXXXXXXXXXXX",
                "17620106739",
                System.currentTimeMillis(),
                1000,
                5000000.00,
                "广东省惠州市博罗县罗阳镇上塘路广东省技师学院");
        //添加默认商品
        AddProduct(
                "小米5S",
                "5",
                1999,
                ProcessPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.mi5s)),
                "某米公司的小米5S",
                1,
                "1",
                System.currentTimeMillis());
        AddProduct(
                "小米笔记本Pro",
                "6",
                5599,
                ProcessPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.mipro)),
                "某米公司的小米笔记本Pro",
                1,
                "1",
                System.currentTimeMillis());
        AddProduct(
                "小米圈铁Pro",
                "36",
                149,
                ProcessPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.miearphone)),
                "某米公司的小米圈铁Pro",
                1,
                "1",
                System.currentTimeMillis());
        return true;
    }

    //顶类添加
    public void ProductTopCategory(String[] categorys){
        //循环添加到数据库
        for (int i = 0; i < categorys.length; i++) {
            ProductCategory category = new ProductCategory();
            category.setCatName(categorys[i]);
            category.setParentId(null);
            category.setSubCategory(true);
            //保存数据到数据库之前进行条件判定
            category.saveIfNotExist("catname = ?",categorys[i]);
        }
    }

    //子类添加
    public void ProductSubCategory(String[] categorys,String parent){
        //获取父分类数据的实体
        ProductCategory pc = DataSupport.where("catname = ?",parent).findFirst(ProductCategory.class);
        //循环添加到数据库
        for (int i = 0; i < categorys.length; i++) {
            ProductCategory category = new ProductCategory();
            category.setCatName(categorys[i]);
            category.setParentId(pc);
            category.setSubCategory(false);
            category.saveIfNotExist("catname = ?",categorys[i]);
        }
    }

    //添加用户
    public void AddUser(String username,String password,String realname,String sex,String cardid,String phone,long time,int reputation,double money,String address){
        Users users = new Users();
        users.setUserName(username);
        users.setPassword(password);
        users.setRealName(realname);
        users.setSex(sex);
        users.setCardId(cardid);
        users.setPhone(phone);
        users.setTime(time);
        users.setReputation(reputation);
        users.setMoney(money);
        users.setAddress(address);
        users.saveIfNotExist("phone = ?",phone);
    }

    //添加商品
    public void AddProduct(String name,String cat,double price,byte[] picture,String productcount,int status,String shopid,long time){
        ProductCategory pc = DataSupport.where("id = ?",cat).find(ProductCategory.class).get(0);
        Users user = DataSupport.where("id = ?",shopid).findFirst(Users.class);
        ProductInfo info = new ProductInfo();
        info.setProductName(name);
        info.setCatId(pc);
        info.setOldPrice(price);
        info.setPicture(picture);
        info.setProductCount(productcount);
        info.setStatus(status);
        info.setShopId(user);
        info.setTime(time);
        info.save();
    }
    //图片转换为byte数组
    public byte[] ProcessPicture(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
        return baos.toByteArray();
    }
    //时间戳转商品时间
    public String ProcessTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(new Date(time));
    }
    //用户金额交易
    public static Boolean Transaction(int userId,double money,boolean action){
        Users user = DataSupport.where("id = ?",String.valueOf(userId)).findFirst(Users.class);
        double UserMoney = user.getMoney();
        //入账
        if (action){
            UserMoney += money;
            user.setMoney(UserMoney);
        }else {
            //消费
            if (UserMoney < money)
                return false;
            UserMoney = UserMoney - money;
            user.setMoney(UserMoney);
        }
        return true;
    }
}
