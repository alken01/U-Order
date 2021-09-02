package be.kuleuven.mainactivity.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import be.kuleuven.mainactivity.ModelClasses.Users;
import be.kuleuven.mainactivity.R;

public class AddTokens extends AppCompatActivity {

    String urlToken = "https://studev.groept.be/api/a20sd710/getCode/";
    String updateToken = "https://studev.groept.be/api/a20sd710/updateTokens/";
    private EditText txtPassword23token;
    private RequestQueue requestQueue, addTokens;
    private TextView txtTokensLeftADD;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tokens);

        txtPassword23token= findViewById(R.id.txtPassword23token);

        txtTokensLeftADD = (TextView) findViewById(R.id.txtTokensLeftADD);
        int tokensTotal = Users.tokens;
        String tokens = String.valueOf(tokensTotal);
        if(tokens == null) { tokens="0"; }
        txtTokensLeftADD.setText( tokens + " \uD83E\uDE99 ");



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

                                Users.tokens = Users.tokens + tokensToAdd;
                                Toast.makeText(AddTokens.this, "Tokens received!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                updateToken(Users.tokens, Users.username);
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

    public void updateToken(int tokens,String username)
    {

        String userTest = updateToken + tokens + "/" + username;

        addTokens = Volley.newRequestQueue( this );
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, userTest, null,
                response -> { },
                error -> Log.d("Error","Error"));
        addTokens.add(jsonArrayRequest);

    }


    public void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}