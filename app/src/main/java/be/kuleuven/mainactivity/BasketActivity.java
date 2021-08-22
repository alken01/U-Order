package be.kuleuven.mainactivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.mainactivity.adapter.MenuAdapter;
import be.kuleuven.mainactivity.model.Item;

public class BasketActivity extends AppCompatActivity {

    ImageView image_home;
    RecyclerView orderRecycler;
    MenuAdapter orderAdapter;
    TextView txtTokensLeft2, txtTokensLeft3;
    Button btnOrder;

    public List<Item> getOrder() {
        return order;
    }

    public void setOrder(List<Item> order) {
        this.order = order;
    }

    List<Item> order = new ArrayList<>();

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

//                String TokensLeft2 = txtTokensLeft2.getText().toString().replace("\uD83E\uDE99","");
//                String TokensLeft3 = txtTokensLeft3.getText().toString().replace("\uD83E\uDE99","");
//                if(Integer.parseInt(TokensLeft2)>Integer.parseInt(TokensLeft3)){//}
//                if(Integer.parseInt(TokensLeft2)<=Integer.parseInt(TokensLeft3)){//}
                Toast.makeText(BasketActivity.this, "Submitting Order!", Toast.LENGTH_LONG).show();
            }
        });

        image_home = (ImageView) findViewById(R.id.image_home);
        image_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openMain(); }
        });

//        basketListAdder();
        orderRecycler(order);

       txtTokensLeft2.setText(tokenCounter(order) + "\uD83E\uDE99");
    }


    private String tokenCounter(List<Item> order) {
        int counter = 0;
        for(int i=0;i<=order.size();i++)
        { counter = counter + 1; }
        return String.valueOf(counter);
    }

    private void orderRecycler(List<Item> order) {
        orderRecycler = findViewById(R.id.order_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        orderRecycler.setLayoutManager(layoutManager);
        orderAdapter = new MenuAdapter(this, order);
        orderRecycler.setAdapter(orderAdapter);
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