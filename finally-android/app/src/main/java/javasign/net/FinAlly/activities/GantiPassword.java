package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.api.RetrofitClient;
import javasign.net.FinAlly.models.PasswordResponse;
import javasign.net.FinAlly.models.User;
import javasign.net.FinAlly.storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiPassword extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView newpassword, confirmpassword;
    private Button btn_gantipassword;

    private ProgressDialog progressDialog;

    private Pattern PASSWORD_PATTERN =
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
        setContentView(R.layout.activity_ganti_password);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        newpassword = (TextView) findViewById(R.id.ed_newpassword);
        confirmpassword = (TextView) findViewById(R.id.ed_confirmpassword);
        btn_gantipassword = (Button) findViewById(R.id.btn_gantipassword);

        btn_gantipassword.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gantipassword:
                gantipassword();
                break;
        }
    }

    private void gantipassword() {


        String npassword = newpassword.getText().toString().trim();
        String cpassword = confirmpassword.getText().toString().trim();

        if (npassword.isEmpty()) {
            newpassword.setError("Password required");
            newpassword.requestFocus();
            return;
        }

        if (!PASSWORD_PATTERN.matcher(npassword).matches()) {
            newpassword.setError("Password too weak");
            newpassword.requestFocus();
            return;
        }

        if (cpassword.isEmpty()) {
            confirmpassword.setError("Confirm Password required");
            confirmpassword.requestFocus();
            return;
        }

        if (!npassword.equals(cpassword)) {
            confirmpassword.setError("Password Not matching");
            confirmpassword.requestFocus();
        }

        else {
            User user = SharedPrefManager.getInstance(this).getUser();

            Call<PasswordResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .updatePassword(user.getEmail(), npassword, cpassword);

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            call.enqueue(new Callback<PasswordResponse>() {
                @Override
                public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                    String res = response.toString();
                    Log.e("onResponse", res);
                    progressDialog.dismiss();
                    Toast.makeText(GantiPassword.this, "Password changed successfully Check your Email", Toast.LENGTH_LONG).show();
                    SharedPrefManager.getInstance(GantiPassword.this).clear();
                    Intent intent = new Intent(GantiPassword.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<PasswordResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("ERROR", t.toString());
                }
            });
        }
    }
}
