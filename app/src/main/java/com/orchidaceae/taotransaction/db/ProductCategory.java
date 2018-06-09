package com.orchidaceae.taotransaction.db;

import org.litepal.crud.DataSupport;

import java.util.List;

//商品类别表实体类
public class ProductCategory extends DataSupport {

    //类别编号
    private int id;

    //类别名称
    private String catName;

    //父类编号
    private ProductCategory parentId;

    //是否有子类
    private boolean subCategory;

    //商品信息表分类外键约束
    private List<ProductInfo> productInfos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public ProductCategory getParentId() {
        return parentId;
    }

    public void setParentId(ProductCategory parentId) {
        this.parentId = parentId;
    }

    public boolean isSubCategory() {
        return subCategory;
    }

    public void setSubCategory(boolean subCategory) {
        this.subCategory = subCategory;
    }


    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }
}
