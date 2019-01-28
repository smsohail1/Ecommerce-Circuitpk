package com.xekera.Ecommerce.ui.continue_shopping.model;

public class DashboardItem {
    private int nameResId;
    private int imgResId;

    public DashboardItem(int nameResId, int imgResId) {
        this.nameResId = nameResId;
        this.imgResId = imgResId;
    }

    public int getNameResId() {
        return nameResId;
    }

    public void setNameResId(int nameResId) {
        this.nameResId = nameResId;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
