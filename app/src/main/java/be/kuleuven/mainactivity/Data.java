package be.kuleuven.mainactivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.mainactivity.adapter.CategoryAdapter;
import be.kuleuven.mainactivity.adapter.FeaturedAdapter;
import be.kuleuven.mainactivity.adapter.MenuAdapter;
import be.kuleuven.mainactivity.model.Category;
import be.kuleuven.mainactivity.model.Item;

public class Data extends AppCompatActivity {

    RequestQueue requestQueue;

    List<Item> menuList = new ArrayList<>();
    List<Item> featuredList = new ArrayList<>();
    List<Category> categoryList = new ArrayList<>();


    private static Bitmap base64ToBitmap(String dataToDecode)
    {
        byte[] dataDecoded = Base64.decode(dataToDecode, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( dataDecoded, 0, dataDecoded.length );
        return bitmap;
    }



    public void getMenu() {
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://studev.groept.be/api/a20sd710/getMenu";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley", "response : " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String name = object.getString("name");
                                String token = object.getString("tokens");
                                String quantity = object.getString("quantity");
                                String category = object.getString("Category");
                                String description = object.getString("description");
                                int featured = object.getInt("featured");
//                                Bitmap bitmap = base64ToBitmap(object.getString("image"));

                                Item food = new Item(R.drawable.sushi, name, token, quantity, category, description, featured, 0);
                                if (food.getFeatured() == 1) { featuredList.add(food); }
                                else { menuList.add(food); }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void getCategory() {
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://studev.groept.be/api/a20sd710/getCategory";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley","response : " + response.toString());
                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String Emoji = jsonObject.getString("Emoji");
                                String Category = jsonObject.getString("Category");
                                be.kuleuven.mainactivity.model.Category category = new Category(Emoji, Category);
                                categoryList.add(category);
                            } catch (JSONException e) { e.printStackTrace(); }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

//    public void callIt(){
//        String url = "https://studev.groept.be/api/a20sd710/getMenu";
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d("volley","response : " + response.toString());
//
//                        MenuAdapter adapter = new MenuAdapter(MainActivity.this , menuList);
//                        menuRecycler.setAdapter(adapter);
//
//                        FeaturedAdapter adapter1 = new FeaturedAdapter(MainActivity.this , featuredList);
//                        popularRecycler.setAdapter(adapter1);
//
//                        CategoryAdapter adapter2 = new CategoryAdapter(MainActivity.this , categoryList);
//                        categoryRecycler.setAdapter(adapter2);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//        setCategoryRecycler(categoryList);
//        setPopularRecycler(featuredList);
//        setMenuRecycler(menuList);
//    }


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

}
