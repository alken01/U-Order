package be.kuleuven.mainactivity.model;

public class Restaurant {

    String name;
    int tokens;

    public Restaurant(String name, int tokens) {
        this.name = name;
        this.tokens = tokens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
