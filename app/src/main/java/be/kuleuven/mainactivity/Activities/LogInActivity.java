package be.kuleuven.mainactivity.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.mainactivity.R;

public class LogInActivity extends AppCompatActivity {
    private EditText user;
    private EditText password;
    private RequestQueue usernameChecker;
    private RequestQueue passwordChecker;
    private static final String usernameURL = "https://studev.groept.be/api/a20sd710/getLogin/";
    private static final String passwordUrl = "https://studev.groept.be/api/a20sd710/getPassword/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        user = findViewById(R.id.txtUsername2);
        password = findViewById(R.id.txtPassword2);

    }

    public void openSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


    public void openMainFromLogIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        if (String.valueOf(user.getText()).equals("") || String.valueOf(password.getText()).equals("")) {
            if (String.valueOf(user.getText()).equals("")) {
                Toast.makeText(LogInActivity.this, "Insert the Username!", Toast.LENGTH_LONG).show();
            }
            if (String.valueOf(password.getText()).equals("")) {
                Toast.makeText(LogInActivity.this, "Insert the Password!", Toast.LENGTH_LONG).show();
            }
        } else {
            String userTest = usernameURL + user.getText();
            usernameChecker = Volley.newRequestQueue(this);
            JsonArrayRequest usernamesRequest = new JsonArrayRequest(Request.Method.GET, userTest, null,
                    response -> {
                        if (response.length() == 1) {
                            String passChecker = passwordUrl + user.getText();
                            passwordChecker = Volley.newRequestQueue(this);
                            JsonArrayRequest passCheck = new JsonArrayRequest(Request.Method.GET, passChecker, null,
                                    response1 -> {
                                        try {
                                            JSONObject j = response.getJSONObject(0);

                                            if (j.getString("Password").equals(String.valueOf(password.getText()))) {
                                                Toast.makeText(LogInActivity.this, "Log In Successful", Toast.LENGTH_LONG).show();
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                finish();
                                            } else {
                                                Toast.makeText(LogInActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            Log.e("Database", e.getMessage(), e);
                                        }
                                    },
                                    error -> Toast.makeText(LogInActivity.this, "Something went wrong", Toast.LENGTH_LONG).show());
                            passwordChecker.add(passCheck);

                        } else {
                            Toast.makeText(LogInActivity.this, "Account does not exist", Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Log.d("Error", "Something went wrong"));
            usernameChecker.add(usernamesRequest);
        }
    }

    public void openGuest(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


}