package model;

public class Friend {

    private int friendID;
    private int categoryId;
    private String categoryDesc;
    private String name;
    private String phone;
    private int age;

    public Friend() {
    }
    
    public Friend(int friendID) {
        this.friendID = friendID;
    }

    public Friend(String phone) {
        this.phone = phone;
    }

    public Friend(String phone, String name, int age) {
        this.phone = phone;
        this.name = name;
        this.age = age;
    }
    
    public Friend(String phone, String name, int age, int categoryId) {
        this.phone = phone;
        this.name = name;
        this.age = age;
        this.categoryId = categoryId;
    }
    
    public Friend(int friendID, String phone, String name, int age, int categoryId) {
        this.friendID = friendID;
        this.phone = phone;
        this.name = name;
        this.age = age;
        this.categoryId = categoryId;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Override
    public String toString() {
        return "<br />Friend{" + "phone=" + phone + ", name=" + name + ", age=" + age + '}';
    }
}
