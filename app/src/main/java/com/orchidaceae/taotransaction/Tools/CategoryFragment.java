package com.orchidaceae.taotransaction.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orchidaceae.taotransaction.R;
import com.orchidaceae.taotransaction.db.ProductCategory;
import com.orchidaceae.taotransaction.db.ProductInfo;
import com.orchidaceae.taotransaction.db.Users;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

//碎片内容
public class CategoryFragment extends Fragment {

    private TabLayout tabLayout;
    private List<SelectList> selectLists = new ArrayList<>();
    private SelectListAdapter selectListAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipe;
    private Context context;
    private String onSelect;

    public static CategoryFragment newInstance(String tag){
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag",tag);
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Nullable
    @Override//View创建时
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载对应的布局文件
        View view = inflater.inflate(R.layout.tab_fragment,null);
        tabLayout = view.findViewById(R.id.sub_category_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        swipe = view.findViewById(R.id.swipe);
        gridLayoutManager = new GridLayoutManager(view.getContext(),1);
        context = view.getContext();
        return view;
    }

    @Override//活动创建完成时
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //为活动填充内容
        Bundle arguments = getArguments();
        //拿到上层布局传下来的父分类id
        String msg = arguments.getString("tag");
        //通过id获取子分类并动态添加TabLayout
        List<ProductCategory> pc = DataSupport.where("productcategory_id = ?",msg).find(ProductCategory.class);
        for (ProductCategory pcs : pc){
            tabLayout.addTab(tabLayout.newTab().setText(pcs.getCatName()));
        }
        //填充List内容、传入分类名称
        onSelect = pc.get(0).getCatName();
        initList(onSelect,0);
        //RecyclerView绑定适配器
        recyclerView.setLayoutManager(gridLayoutManager);
        selectListAdapter = new SelectListAdapter(selectLists);
        recyclerView.setAdapter(selectListAdapter);
        //TabLayout选中响应事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //将当前选中传入String
                onSelect = tab.getText().toString();
                //将Title传入List内容填充类
                initList(onSelect,0);
                //刷新适配器的数据
                selectListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //设置上拉刷新视图颜色
        swipe.setColorSchemeResources(R.color.accent);
        //设置上拉刷新响应事件
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新数据
                initList(onSelect,0);
                selectListAdapter.notifyDataSetChanged();
                //将上拉控件重新隐藏
                swipe.setRefreshing(false);
            }
        });
        //使用RecyclerView的滚动监听事件实现下拉惰性加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断当前位置是否在列表最底
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == selectListAdapter.getItemCount()){
                    //执行数据查询并传入当前列表总数判断数据库查询偏移量
                    initList(onSelect,selectListAdapter.getItemCount());
                    //如果当前位置和添加数据后的总数不一致时就刷新数据
                    if (lastVisibleItem + 1 != selectLists.size()){
                        selectListAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(context,"没有更多了！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            //RecyclerView的滚动位置改变时响应事件
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取当前页面显示的末尾标识
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    //列表数据填充
    private void initList(String categoryName,int post){
        //如果偏移量为0则不是下拉加载更多，清空数据
        if (post == 0){
            //清空存放内容的数组
            selectLists.clear();
        }
        //通过参数查询数据库获取商品信息
        int id = DataSupport.where("catname = ?",categoryName).findFirst(ProductCategory.class).getId();
        List<ProductInfo> pi = DataSupport.where("productcategory_id = ? and status = ?",String.valueOf(id),"1").order("time desc").offset(post).limit(10).find(ProductInfo.class,true);
        //遍历输出商品信息
        for (ProductInfo pis : pi){
            String name = pis.getShopId().getUserName();
            String time = new LoadData().ProcessTime(pis.getTime());
            //将商品信息添加到存放内容的数组
            selectLists.add(new SelectList(pis.getPicture(),pis.getProductName(),name,"¥"+String.valueOf(pis.getOldPrice()),time,pis.getId()));
        }
    }
}
