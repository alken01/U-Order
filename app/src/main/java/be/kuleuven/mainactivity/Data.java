package be.kuleuven.mainactivity;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.kuleuven.mainactivity.model.Category;
import be.kuleuven.mainactivity.model.Item;

public class Data {


    private Data dataInstance;
    RequestQueue requestQueue;

    List < Item > menuList = new ArrayList < > ();
    List < Item > featuredList = new ArrayList < > ();
    List < Category > categoryList = new ArrayList < > ();
    String nameAccount;
    int tokensAccount;


    private Data(List<Item> menuList, List<Item> featuredList, List<Category> categoryList, String nameAccount, int tokensAccount) {

//        getMenu();
        getCategory();
        menuList = getMenuList();
        featuredList = getFeaturedList();
        categoryList = getCategoryList();




    }


//    public void getMenu() {
//        requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
//        String url = "https://studev.groept.be/api/a20sd710/getMenu";
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener < JSONArray > () {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d("volley", "response : " + response.toString());
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject object = response.getJSONObject(i);
//                                String name = object.getString("name");
//                                String token = object.getString("tokens");
//                                String quantity = object.getString("quantity");
//                                String category = object.getString("Category");
//                                String description = object.getString("description");
//                                int featured = object.getInt("featured");
//
//                                //                                String img = object.getString("image");
//                                //                                byte[] image = Base64.decode(img, Base64.DEFAULT);
//                                //                                Bitmap bitmap = BitmapFactory.decodeByteArray( image, 0, image.length );
//
//                                Item food = new Item(R.drawable.sushi, name, token, quantity, category, description, featured, 0);
//
//                                if (food.getFeatured() == 1) { featuredList.add(food); }
//                                else { menuList.add(food); }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {}
//        });
//        requestQueue.add(jsonArrayRequest);
//    }


    public List<Category> getCategory() {
        requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        String url = "https://studev.groept.be/api/a20sd710/getCategory";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener < JSONArray > () {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley", "response : " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String Emoji = jsonObject.getString("Emoji");
                                String Category = jsonObject.getString("Category");
                                be.kuleuven.mainactivity.model.Category category = new Category(Emoji, Category);
                                categoryList.add(category);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue.add(jsonArrayRequest);
        return categoryList;
    }


    public List<Item> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Item> menuList) {
        this.menuList = menuList;
    }

    public List<Item> getFeaturedList() {
        return featuredList;
    }

    public void setFeaturedList(List<Item> featuredList) {
        this.featuredList = featuredList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public int getTokensAccount() {
        return tokensAccount;
    }

    public void setTokensAccount(int tokensAccount) {
        this.tokensAccount = tokensAccount;
    }
}