package be.kuleuven.mainactivity.Activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import be.kuleuven.mainactivity.Adapters.CategoryAdapter;
import be.kuleuven.mainactivity.Adapters.MenuAdapter;
import be.kuleuven.mainactivity.Adapters.FeaturedAdapter;
import be.kuleuven.mainactivity.ModelClasses.Category;
import be.kuleuven.mainactivity.ModelClasses.Item;
import be.kuleuven.mainactivity.R;

public class MainActivity extends AppCompatActivity {

    RecyclerView popularRecycler, menuRecycler, categoryRecycler;
    FeaturedAdapter featuredAdapter;
    MenuAdapter menuAdapter;
    CategoryAdapter categoryAdapter;
    ImageView image_order;
    TextView txtTokensLeftMAIN;

    RequestQueue requestQueue;

    List < Item > menuList = new ArrayList < > ();
    List < Item > featuredList = new ArrayList < > ();
    List < Category > categoryList = new ArrayList < > ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_order = (ImageView) findViewById(R.id.image_home23);
        image_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBasket();
            }
        });

        txtTokensLeftMAIN = (TextView) findViewById(R.id.txtTokensLeftMAIN);
        txtTokensLeftMAIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openTokens();}
        });



        getMenu();
        getCategory();
        callIt();
    }

    private void setPopularRecycler(List < Item > popularFoodList) {
        popularRecycler = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        featuredAdapter = new FeaturedAdapter(this, popularFoodList);
        popularRecycler.setAdapter(featuredAdapter);
    }

    private void setMenuRecycler(List < Item > menuList) {
        menuRecycler = findViewById(R.id.menu_recycler2);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        menuRecycler.setLayoutManager(layoutManager2);
        menuAdapter = new MenuAdapter(this, menuList);
        menuRecycler.setAdapter(menuAdapter);
    }

    private void setCategoryRecycler(List < Category > categoryList) {
        categoryRecycler = findViewById(R.id.category_recycler);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        categoryRecycler.setLayoutManager(layoutManager2);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setAdapter(categoryAdapter);
    }

    public void openBasket() {
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openTokens() {
        Intent intent = new Intent(this, AddTokens.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void getMenu() {
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://studev.groept.be/api/a20sd710/getMenu";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener < JSONArray > () {
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

                                byte[] imageBytes = Base64.decode(object.getString("image"), Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                                Item food = new Item(decodedImage, name, token, quantity, category, description, featured, 0);

                                if (food.getFeatured() == 1) { featuredList.add(food); }
                                else { menuList.add(food); }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            MenuAdapter adapter = new MenuAdapter(MainActivity.this, menuList);
                            menuRecycler.setAdapter(adapter);
                            FeaturedAdapter adapter1 = new FeaturedAdapter(MainActivity.this, featuredList);
                            popularRecycler.setAdapter(adapter1);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void getCategory() {
        requestQueue = Volley.newRequestQueue(this);
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
                                Category category = new Category(Emoji, Category);
                                categoryList.add(category);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            CategoryAdapter adapter2 = new CategoryAdapter(MainActivity.this, categoryList);
                            categoryRecycler.setAdapter(adapter2);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void callIt() {
        setCategoryRecycler(categoryList);
        setPopularRecycler(featuredList);
        setMenuRecycler(menuList);
    }


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}