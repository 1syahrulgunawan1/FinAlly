package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.LoginInternetbangkingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInternetBanking extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_alias_name, tv_loginurl;
    private EditText ed_userid, ed_pin;
    private Button btn_login;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_internet_banking);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_alias_name = (TextView) findViewById(R.id.tv_alias_name);
        tv_loginurl = (TextView) findViewById(R.id.tv_urllogin);

        ed_userid = (EditText) findViewById(R.id.ed_userid);
        ed_pin = (EditText) findViewById(R.id.ed_pin);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setEnabled(false);
        Intent intent = getIntent();

        tv_alias_name.setText(intent.getStringExtra("data1"));
        tv_loginurl.setText(intent.getStringExtra("data3"));
        ed_userid.setHint(intent.getStringExtra("data5"));
        ed_pin.setHint(intent.getStringExtra("data6"));

        ed_userid.addTextChangedListener(loginTextWatcher);
        ed_pin.addTextChangedListener(loginTextWatcher);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInternetbangking();

            }
        });

    }


    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            btn_login.setEnabled(false);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = ed_userid.getText().toString().trim();
            String passwordInput = ed_pin.getText().toString().trim();

            btn_login.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
            if (btn_login.isEnabled() == true) {
                btn_login.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                btn_login.setTextColor(Color.WHITE);
            } else if (btn_login.isEnabled() == false){
                btn_login.setBackgroundColor(getResources().getColor(R.color.grey));
                btn_login.setTextColor(Color.GRAY);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void LoginInternetbangking() {
        String username = ed_userid.getText().toString().trim();
        String pin = ed_pin.getText().toString().trim();

        Intent intent = getIntent();

        if (username.length() < 5) {
            ed_userid.setError("at least 5 characters");
            ed_userid.requestFocus();
            return;
        }

        if (pin.length() < 6) {
            ed_pin.setError("PIN or password should be atleast 6 number long");
            ed_pin.requestFocus();
            return;
        } else {
            progressDialog = new ProgressDialog(LoginInternetBanking.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            Call<LoginInternetbangkingResponse> call = RetrofitClientLogged
                    .getInstance(this)
                    .getApi()
                    .createAccount(intent.getIntExtra("data", 0), username, pin);

            call.enqueue(new Callback<LoginInternetbangkingResponse>() {
                @Override
                public void onResponse(Call<LoginInternetbangkingResponse> call, Response<LoginInternetbangkingResponse> response) {
                    String res = response.toString();
                    Log.e("onResponse", res);
                    progressDialog.dismiss();
                    LoginInternetbangkingResponse Lib = response.body();
                    if (response.code() == 200) {
                        Toast.makeText(LoginInternetBanking.this, "Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginInternetBanking.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginInternetBanking.this, "Failed", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginInternetBanking.this, MainActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<LoginInternetbangkingResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("ERROR", t.toString());
                }
            });
        }
    }
}
