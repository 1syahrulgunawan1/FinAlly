package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.LoginInternetbangkingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInternetBanking extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_alias_name, tv_bank_name, tv_loginurl, tv_instruction;
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
        tv_bank_name = (TextView) findViewById(R.id.tv_bank);
        tv_loginurl = (TextView) findViewById(R.id.tv_urllogin);
        tv_instruction = (TextView) findViewById(R.id.tv_instruction);

        ed_userid = (EditText) findViewById(R.id.ed_userid);
        ed_pin = (EditText) findViewById(R.id.ed_pin);

        btn_login = (Button) findViewById(R.id.btn_login);

        Intent intent = getIntent();

        tv_alias_name.setText(intent.getStringExtra("data1"));
        tv_bank_name.setText(intent.getStringExtra("data2"));
        tv_loginurl.setText(intent.getStringExtra("data3"));
        tv_instruction.setText(intent.getStringExtra("data4"));
        ed_userid.setHint(intent.getStringExtra("data5"));
        ed_pin.setHint(intent.getStringExtra("data6"));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginInternetbangking();

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


    private void LoginInternetbangking() {
        String username = ed_userid.getText().toString().trim();
        String pin = ed_pin.getText().toString().trim();

        Intent intent = getIntent();

        if (username.isEmpty()) {
            ed_userid.setError(intent.getStringExtra("data5"));
            ed_userid.requestFocus();
            return;
        }

        if (pin.isEmpty()) {
            ed_pin.setError(intent.getStringExtra("data6"));
            ed_pin.requestFocus();
            return;
        }

        if (pin.length() < 6) {
            ed_pin.setError("PIN should be atleast 6 number long");
            ed_pin.requestFocus();
            return;
        } else {
            progressDialog = new ProgressDialog(LoginInternetBanking.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            Call<LoginInternetbangkingResponse> call = RetrofitClientLogged
                    .getInstance(this)
                    .getApi()
                    .createAccount(intent.getIntExtra("data", 0),username, pin);

            call.enqueue(new Callback<LoginInternetbangkingResponse>() {
                @Override
                public void onResponse(Call<LoginInternetbangkingResponse> call, Response<LoginInternetbangkingResponse> response) {
                    String res = response.toString();
                    Log.e("onResponse", res);
                    progressDialog.dismiss();
                    LoginInternetbangkingResponse Lib = response.body();
                    Toast.makeText(LoginInternetBanking.this, "Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginInternetBanking.this, MainActivity.class));
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
