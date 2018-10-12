package com.honeywelltest;

public class Item {

    public int id;
    private String fName,lName;
    private String avatar;
    private String description;

    public Item() {
    }

    public Item(int id, String fName, String lName, String avatar, String description) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.avatar = avatar;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
