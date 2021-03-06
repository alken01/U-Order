package be.kuleuven.mainactivity.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import be.kuleuven.mainactivity.Database.DatabaseCart;
import be.kuleuven.mainactivity.ModelClasses.Users;
import be.kuleuven.mainactivity.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText name;
    private EditText username;
    private EditText password;
    private RequestQueue usernameChecker;
    private static final String UserCreator= "https://studev.groept.be/api/a20sd710/addUser/";
    private static final String usernameURL = "https://studev.groept.be/api/a20sd710/getLogin/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name= findViewById(R.id.txtName);
        username= findViewById(R.id.txtUsername);
        password=findViewById(R.id.txtPassword23);
        DatabaseCart db = new DatabaseCart(this);
        db.emptyAll();
    }

    public void openMainFromSignUp(View view)
    {
            Intent intent = new Intent(this, AddTokens.class);
        if(String.valueOf(username.getText()).equals("") || String.valueOf(password.getText()).equals("") || String.valueOf(name.getText()).equals("")) {
            if (String.valueOf(username.getText()).equals("")){
                Toast.makeText(SignUpActivity.this,"Insert the Username!",Toast.LENGTH_LONG).show();
            }
            if (String.valueOf(password.getText()).equals("")){
                Toast.makeText(SignUpActivity.this,"Insert the Password!",Toast.LENGTH_LONG).show();
            }
            if (String.valueOf(name.getText()).equals("")){
                Toast.makeText(SignUpActivity.this,"Insert your Name",Toast.LENGTH_LONG).show();
            }
        }
        else {
            String userTest=usernameURL+username.getText();
            usernameChecker = Volley.newRequestQueue( this );
            JsonArrayRequest usernamesRequest = new JsonArrayRequest(Request.Method.GET, userTest, null,
                    response -> {
                        if(response.length()==0)
                        {
                            String newUser = UserCreator + username.getText()+"/"+password.getText()+"/"+name.getText();
                            RequestQueue userCreator = Volley.newRequestQueue( this );
                            JsonArrayRequest newUserRequest = new JsonArrayRequest(Request.Method.GET, newUser, null,
                                    response1 -> {
                                        Users.tokens = 0;
                                        Users.nameUser = name.getText().toString();
                                        Users.username = username.getText().toString();
                                        Users.password = password.getText().toString();



                                        Toast.makeText(SignUpActivity.this,"Account Created Successfully",Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        finish();
                                    },
                                    error -> Toast.makeText(SignUpActivity.this,"Connection Failed",Toast.LENGTH_LONG).show());
                            userCreator.add(newUserRequest);
                        }
                        else{
                            Toast.makeText(SignUpActivity.this,"Username already exists",Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Log.d("Error","Error"));
            usernameChecker.add(usernamesRequest);
        }
    }

    public void openLogIn(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}