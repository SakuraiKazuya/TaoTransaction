package com.orchidaceae.taotransaction;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orchidaceae.taotransaction.Tools.LoadData;
import com.orchidaceae.taotransaction.Tools.SelectList;
import com.orchidaceae.taotransaction.Tools.SelectListAdapter;
import com.orchidaceae.taotransaction.db.ProductInfo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private List<SelectList> selectLists = new ArrayList<>();
    private SelectListAdapter adapter;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //获取传递的搜索内容
        Intent SeaContent = getIntent();
        content = SeaContent.getStringExtra("content");
        //获取界面控件实例
        Button back = findViewById(R.id.back);
        TextView search = findViewById(R.id.search_text);
        RecyclerView recyclerView = findViewById(R.id.search_list);
        //填充控件
        search.setText("搜索：" + content);
        back.setOnClickListener(this);
        if (!init(content,0)) {
            Toast.makeText(this,"没有相关商品", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        final LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new SelectListAdapter(selectLists);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断当前位置是否在列表最底
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()){
                    //执行数据查询并传入当前列表总数判断数据库查询偏移量
                    init(content,adapter.getItemCount());
                    //如果当前位置和添加数据后的总数不一致时就刷新数据
                    if (lastVisibleItem + 1 != selectLists.size()){
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(SearchActivity.this,"没有更多了！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            //RecyclerView的滚动位置改变时响应事件
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取当前页面显示的末尾标识
                lastVisibleItem = linearLayout.findLastVisibleItemPosition();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
    private Boolean init(String content,int post){
        List<ProductInfo> pd = DataSupport.where("productName like ? and status = ?","%%"+content+"%%","1").order("time desc").offset(post).limit(10).find(ProductInfo.class,true);
        if (pd.size() == 0){
            return false;
        }
        for (ProductInfo pis : pd){
            String name = pis.getShopId().getUserName();
            String time = new LoadData().ProcessTime(pis.getTime());
            //将商品信息添加到存放内容的数组
            selectLists.add(new SelectList(pis.getPicture(),pis.getProductName(),name,"¥"+String.valueOf(pis.getOldPrice()),time,pis.getId()));
        }
        return true;
    }
}
