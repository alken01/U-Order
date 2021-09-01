package be.kuleuven.mainactivity.ModelClasses;

import android.graphics.Bitmap;

public class Item {

    Bitmap image;
    String name,token, quantity, category, description;
    int order, featured;

    public Item(Bitmap image, String name, String token, String quantity,
                String category, String description, int featured, int order)
    {
        this.image = image;
        this.name = name;
        this.token = token;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
        this.order = order;
        this.featured = featured;
    }

    public Item(Bitmap image, String name, String token, String quantity, String description, int order)
    {
        this.image = image;
        this.name = name;
        this.token = token;
        this.quantity = quantity;
        this.description = description;
        this.order = order;
    }

    public Item(){}

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getFeatured() {
        return featured;
    }
    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}