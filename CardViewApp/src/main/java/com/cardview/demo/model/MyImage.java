package com.cardview.demo.model;

import java.io.Serializable;

public class MyImage implements Serializable {

    private byte[] imageAsBytes;
    private String imageName;

    public MyImage(String imageName, byte[] imageAsBytes){
        this.imageName = imageName;
        this.imageAsBytes = imageAsBytes;
    }

    public byte[] getImageAsBytes() {
        return imageAsBytes;
    }

    public void setImageAsBytes(byte[] imageAsBytes) {
        this.imageAsBytes = imageAsBytes;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
