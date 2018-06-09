package com.orchidaceae.taotransaction.Tools;

//RecyclerView的List内容实体类
public class SelectList {
    private byte[] image;
    private String title;
    private String user;
    private String price;
    private String time;
    private int id;

    public SelectList(byte[] image, String title, String user, String price, String time,int id) {
        this.image = image;
        this.title = title;
        this.user = user;
        this.price = price;
        this.time = time;
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }
}
