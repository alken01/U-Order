package be.kuleuven.mainactivity.ModelClasses;

public class Category {

    String emoji, category;

    public Category(String emoji, String category) {
        this.emoji = emoji;
        this.category = category;
    }

    public String getImageCategory() {
        return emoji;
    }
    public void setImageCategory(String imageCategory) {
        this.emoji = imageCategory;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
