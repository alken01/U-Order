package be.kuleuven.mainactivity.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.mainactivity.R;

public class AddTokens extends AppCompatActivity {

    String urlToken = "https://studev.groept.be/api/a20sd710/getCode/";
    private EditText txtPassword23token;
    private RequestQueue requestQueue;
    private TextView txtTokensLeftMAIN;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tokens);

        txtPassword23token= findViewById(R.id.txtPassword23token);

        txtTokensLeftMAIN = (TextView) findViewById(R.id.txtTokensLeftMAIN);

        ImageView home = (ImageView) findViewById(R.id.image_home23token);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });


        
        TextView textViewSkip = (TextView) findViewById(R.id.textViewSkip);
        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    public void submit(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        if(String.valueOf(txtPassword23token.getText()).equals("")) {
                Toast.makeText(AddTokens.this,"No Code Inserted",Toast.LENGTH_SHORT).show();
            }
        else {
            String userTest = urlToken + txtPassword23token.getText();
            requestQueue = Volley.newRequestQueue( this );
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, userTest, null,
                    response -> {

                        if(response.length()==0) {
                            Toast.makeText(AddTokens.this,"Code is Incorrect",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                int tokensToAdd = response.getJSONObject(0).getInt("Tokens");

                                intent.putExtra("TOKENS", tokensToAdd);
                                Toast.makeText(AddTokens.this, "Tokens received!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                finish();

                            } catch (JSONException e) {
                                Log.e("Database", e.getMessage(), e);
                            }
                        }
                    },
                    error -> Log.d("Error","Error"));
            requestQueue.add(jsonArrayRequest);
        }
    }


    public void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}