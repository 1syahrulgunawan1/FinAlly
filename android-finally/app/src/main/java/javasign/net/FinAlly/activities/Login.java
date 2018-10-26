package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.api.RetrofitClient;
import javasign.net.FinAlly.models.LoginResponse;
import javasign.net.FinAlly.storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword;
    private ImageView logo;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = (ImageView) findViewById(R.id.logo);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);
        findViewById(R.id.buttonForgot).setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                userLogin();
                break;
            case R.id.textViewRegister:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.buttonForgot:
                startActivity(new Intent(this, LupaPassword.class));
                break;
        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        email = email.toLowerCase();


        if (email.isEmpty()) {
            editTextEmail.setError("Username or Email is required");
            editTextEmail.requestFocus();
            return;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        else {
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setMessage("Logging in...");
            progressDialog.show();
            Call<LoginResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .userLogin(email, email, password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    String res = response.toString();
                    Log.e("onResponse", res);

                    progressDialog.dismiss();

                    if (response.code() == 200) {
                        SharedPrefManager.getInstance(Login.this)
                                .saveUser(loginResponse.getUser());
                        LoginResponse lg = response.body();
                        Toast.makeText(Login.this, "Welcome " + lg.getUser().getUsername(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (response.code() == 502) {
                        Toast.makeText(Login.this, "Bad Gateway", Toast.LENGTH_LONG).show();
                    }

                    else {
                        Toast.makeText(Login.this, "Email or password is incorrect", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("Tag","onResponse onFailure" );
                }
            });
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
