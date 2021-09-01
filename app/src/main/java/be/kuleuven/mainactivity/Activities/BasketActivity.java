package be.kuleuven.mainactivity.Activities;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.mainactivity.Adapters.CartAdapter;
import be.kuleuven.mainactivity.Adapters.MenuAdapter;
import be.kuleuven.mainactivity.ModelClasses.Item;
import be.kuleuven.mainactivity.R;
import be.kuleuven.mainactivity.database.DatabaseCart;

public class BasketActivity extends AppCompatActivity {

    ImageView image_home;
    RecyclerView orderRecycler;
    CartAdapter orderAdapter;
    TextView txtTokensLeft2, txtTokensLeft3;
    Button btnOrder;

    List<Item> order = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtTokensLeft2 = (TextView) findViewById(R.id.txtTokensLeft2);
        txtTokensLeft3 = (TextView) findViewById(R.id.txtTokensLeft3);

        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BasketActivity.this, "Submitting Order!", Toast.LENGTH_LONG).show(); }});

        image_home = (ImageView) findViewById(R.id.image_home);
        image_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openMain(); }
        });
        DatabaseCart databaseCart = new DatabaseCart(this);
        order = databaseCart.getAllItems();
        orderRecycler(order);

        txtTokensLeft2.setText(tokenCounter(order) + " \uD83E\uDE99");
    }


    private String tokenCounter(List<Item> order) {
        int totalTokens = 0;
        for (int i = 0; i<order.size(); i++){
            totalTokens += Integer.parseInt(order.get(i).getToken()) * Integer.parseInt(order.get(i).getQuantity());
        }
        return String.valueOf(totalTokens);
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