package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.api.RetrofitClient;
import javasign.net.FinAlly.models.PasswordResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPassword extends AppCompatActivity {

    private EditText editTextEmail, editTextNpassword, editTextCpassword;
    private ProgressDialog progressDialog;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +      //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNpassword = (EditText) findViewById(R.id.editTextPassword);
        editTextCpassword = (EditText) findViewById(R.id.editTextPassword2);

        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void resetPassword() {

        String email = editTextEmail.getText().toString().trim();
        String npassword = editTextNpassword.getText().toString().trim();
        String cpassword = editTextCpassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (npassword.isEmpty()) {
            editTextNpassword.setError("Password required");
            editTextNpassword.requestFocus();
            return;
        }

        if (!PASSWORD_PATTERN.matcher(npassword).matches()) {
            editTextNpassword.setError("Password too weak");
            return;
        }

        if (!npassword.equals(cpassword)) {
            editTextCpassword.setError("Password Not matching");
        }

        else {

            progressDialog = new ProgressDialog(LupaPassword.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            Call<PasswordResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .updatePassword(email, npassword, cpassword);

            call.enqueue(new Callback<PasswordResponse>() {
                @Override
                public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {

                    progressDialog.dismiss();

                    if (response.code() == 200) {
                        String res = response.toString();
                        Log.e("onResponse", res);
                        Toast.makeText(LupaPassword.this, "Check your Email", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LupaPassword.this, Login.class));
                    } else if (response.code() == 400) {
                        Toast.makeText(LupaPassword.this, "The selected email hash is invalid", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PasswordResponse> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }

    }
}
