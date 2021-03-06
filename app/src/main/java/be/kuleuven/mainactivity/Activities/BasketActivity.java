package be.kuleuven.mainactivity.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.mainactivity.Adapters.CartAdapter;
import be.kuleuven.mainactivity.ModelClasses.Item;
import be.kuleuven.mainactivity.ModelClasses.Users;
import be.kuleuven.mainactivity.R;
import be.kuleuven.mainactivity.Database.DatabaseCart;

public class BasketActivity extends AppCompatActivity {

    ImageView image_home;
    RecyclerView orderRecycler;
    CartAdapter orderAdapter;
    TextView txtTokensLeft2, txtTokensLeftADD, textViewCart;
    Button btnOrder;
    List<Item> order = new ArrayList<>();
    String URLSubmit = "https://studev.groept.be/api/a20sd710/orderItems/";

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtTokensLeft2 = (TextView) findViewById(R.id.txtTokensLeft2);

        txtTokensLeftADD = (TextView) findViewById(R.id.txtTokensLeftADD);

        String tokens = String.valueOf(Users.tokens);
        if(tokens == null) { tokens="0"; }
        txtTokensLeftADD.setText( tokens + " \uD83E\uDE99 ");

        txtTokensLeftADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openTokens(); }});

        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitOrder();
                    Toast.makeText(BasketActivity.this, "Submitting Order!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }});

        image_home = (ImageView) findViewById(R.id.image_home23);
        image_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });


        DatabaseCart databaseCart = new DatabaseCart(this);
        order = databaseCart.getAllItems();
        orderRecycler(order);

        txtTokensLeft2.setText(tokenCounter(order) + " \uD83E\uDE99");

        textViewCart = (TextView) findViewById(R.id.textViewCart);
        textViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseCart.emptyAll();
                Toast.makeText(BasketActivity.this, "Cart Emptied", Toast.LENGTH_SHORT).show();
                openMain();
            }
        });
    }


    public void openTokens() {
        Intent intent = new Intent(this, AddTokens.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private String tokenCounter(List<Item> order) {
        int totalTokens = 0;
        for (int i = 0; i<order.size(); i++){
            totalTokens += Integer.parseInt(order.get(i).getToken()) * order.get(i).getOrder();
        }
        return String.valueOf(totalTokens);
    }

    public void submitOrder() throws JSONException {

        Intent intent = new Intent(this, MainActivity.class);

        int price = Integer.parseInt(tokenCounter(order));
        if(order.size()==0){
            Toast.makeText(BasketActivity.this, "No Items in the Cart", Toast.LENGTH_SHORT).show();
        }
        else if (price<=Users.tokens)
        {
            DatabaseCart databaseCart = new DatabaseCart(this);
            Users.tokens = Users.tokens - price;
            sendOrder();
            databaseCart.emptyAll();
            Toast.makeText(BasketActivity.this, "Submitting Order!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }
        else{
            Toast.makeText(BasketActivity.this, "Not enough tokens :(", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendOrder()
    {
        String userTest=URLSubmit+Users.username;
        RequestQueue sendOrder = Volley.newRequestQueue( this );
        JsonArrayRequest orderRequest = new JsonArrayRequest(Request.Method.GET, userTest, null,
                response -> {
            for(int i=0;i<order.size();i++)
            {
                String newUser = URLSubmit + Users.username+"/"+order.get(i).getName()+"/"+order.get(i).getOrder();
                RequestQueue userCreator = Volley.newRequestQueue( this );
                JsonArrayRequest newUserRequest = new JsonArrayRequest(Request.Method.GET, newUser, null,
                        response1 -> {
                        },
                        error -> Toast.makeText(BasketActivity.this,"Connection Failed",Toast.LENGTH_LONG).show());
                userCreator.add(newUserRequest);
            }
        },
        error -> Log.d("Error","Error"));

        sendOrder.add(orderRequest );

    }


    @SuppressLint("NotifyDataSetChanged")
    private void orderRecycler(List<Item> order) {
        orderRecycler = findViewById(R.id.order_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        orderRecycler.setLayoutManager(layoutManager);
        orderAdapter = new CartAdapter(this, order);
        orderRecycler.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }


}