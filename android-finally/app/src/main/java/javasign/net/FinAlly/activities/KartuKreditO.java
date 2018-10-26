package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javasign.net.FinAlly.Card;
import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterListTransaksio;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.AccountModels;
import javasign.net.FinAlly.models.AccountModelsProducts;
import javasign.net.FinAlly.models.AccountResponse;
import javasign.net.FinAlly.models.CashFlowsModels;
import javasign.net.FinAlly.models.CashFlowsResponse;
import javasign.net.FinAlly.models.SycnResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KartuKreditO extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_toolbar_title, tv_bank, tv_credit, tv_cardholder, tv_kredit_tersedia, tv_jumlah_belanja;
    private TextView tv_timer;
    private ImageView iv_iconkredit, iv_sycn;
    private Button btn_more;
    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    CardView cv_card;

    private AdapterListTransaksio adapterListTransaksi;
    private List<CashFlowsModels> cashFlows;
    private int product_id, account_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_kredito);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        cv_card = (CardView) findViewById(R.id.cardview_detailscard);
        tv_timer = (TextView) findViewById(R.id.timer);
        tv_bank = (TextView) findViewById(R.id.tv_name_bank);
        tv_credit = (TextView) findViewById(R.id.tv_credit);
        tv_cardholder = (TextView) findViewById(R.id.tv_cardname);
        tv_kredit_tersedia = (TextView) findViewById(R.id.tv_available_balance);
        tv_jumlah_belanja = (TextView) findViewById(R.id.tv_last_payment);
        iv_iconkredit = (ImageView) findViewById(R.id.iv_iconkredit);
        iv_sycn = (ImageView) findViewById(R.id.iv_sycn);
        btn_more = (Button) findViewById(R.id.btn_more);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_transaksi);

        Intent intent = getIntent();
        final AccountModelsProducts accountModelsProducts = intent.getParcelableExtra("data1");
        product_id = accountModelsProducts.id;
        account_id = accountModelsProducts.idAccountModels;
        tv_toolbar_title.setText(accountModelsProducts.nickname + " " + accountModelsProducts.vendorModels.name);
        tv_bank.setText(accountModelsProducts.nickname);
        tv_credit.setText(accountModelsProducts.vendorModels.name);
        tv_cardholder.setText(accountModelsProducts.getProperties().cardholder);
        tv_kredit_tersedia.setText(toRupiah(accountModelsProducts.getProperties().available_balance));
        tv_jumlah_belanja.setText(toRupiah(accountModelsProducts.getProperties().minimum_payment));

        switch (accountModelsProducts.nickname) {
            case "MasterCard":
                iv_iconkredit.setImageResource(R.drawable.ic_master_card);
                break;
            case "Visa Card":
                iv_iconkredit.setImageResource(R.drawable.ic_visa);
                break;
        }

        cv_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KartuKreditO.this, DetailsKartukredit.class);
                intent.putExtra("data", accountModelsProducts);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearlayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearlayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        iv_sycn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinkron();
            }
        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KartuKreditO.this, ListTransaksi.class);
                intent.putExtra("data", accountModelsProducts.id);
                startActivity(intent);
                finish();
            }
        });

        listtransaksi();
    }

    public void listtransaksi() {
        Call<CashFlowsResponse> call = RetrofitClientLogged
                .getInstance(this)
                .getApi()
                .getCashflows(product_id);

        call.enqueue(new Callback<CashFlowsResponse>() {
            @Override
            public void onResponse(Call<CashFlowsResponse> call, Response<CashFlowsResponse> response) {
                String res = response.toString();
                Log.e("onResponse", res);
                final CashFlowsResponse cf = response.body();

                if (response.isSuccessful()) {
                    cashFlows = cf.getCashFlowsModels();
                    adapterListTransaksi = new AdapterListTransaksio(cashFlows, KartuKreditO.this);
                    if (cashFlows == null) {
                        btn_more.setVisibility(View.GONE);
                    } else if (cashFlows.size() < 5) {
                        btn_more.setVisibility(View.GONE);
                    } else {
                        btn_more.setVisibility(View.VISIBLE);
                    }

                    switch (cf.getStatus()) {
                        case "fail":
                            Toast.makeText(KartuKreditO.this, "Transactions are not found", Toast.LENGTH_LONG).show();
                            break;
                    }
                    recyclerView.setAdapter(adapterListTransaksi);
                }
            }

            @Override
            public void onFailure(Call<CashFlowsResponse> call, Throwable t) {
                Log.e("Tag", "onResponse onFailure");
            }
        });
    }

    public void sinkron() {
        progressDialog = new ProgressDialog(KartuKreditO.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<SycnResponse> call = RetrofitClientLogged
                .getInstance(this)
                .getApi()
                .getSycn(account_id);
        call.enqueue(new Callback<SycnResponse>() {
            @Override
            public void onResponse(Call<SycnResponse> call, Response<SycnResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    SycnResponse sr = response.body();
                    Toast.makeText(KartuKreditO.this, "Sync Account Success", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(KartuKreditO.this, "Sync Account Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SycnResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Tag", "onResponse onFailure");
            }
        });
    }

    private String toRupiah(double nominal) {
        String hasil = " ";

        DecimalFormat toRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatAngka = toRupiah.getDecimalFormatSymbols();
        formatAngka.setCurrencySymbol("Rp. ");
        formatAngka.setMonetaryDecimalSeparator(',');
        formatAngka.setGroupingSeparator('.');
        toRupiah.setDecimalFormatSymbols(formatAngka);

        hasil = toRupiah.format(Double.valueOf(nominal));

        return hasil;
    }

    private String toRupiah(String nominal) {
        return toRupiah(Double.valueOf(nominal.replace("(", "").replace(")", "").replace(" ", "")));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
