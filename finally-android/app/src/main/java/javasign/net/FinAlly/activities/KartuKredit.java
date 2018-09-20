package javasign.net.FinAlly.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterListTransaksi;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.AccountModelsProducts;
import javasign.net.FinAlly.models.CashFlowsModels;
import javasign.net.FinAlly.models.CashFlowsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KartuKredit extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_bank, tv_credit, tv_kredit_tersedia, tv_jumlah_belanja;
    private ImageView iv_iconkredit;
    RecyclerView recyclerView;

    private AdapterListTransaksi adapterListTransaksi;
    private List<CashFlowsModels> cashFlows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_kredit);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_bank = (TextView) findViewById(R.id.tv_name_bank);
        tv_credit = (TextView) findViewById(R.id.tv_credit);
        tv_kredit_tersedia = (TextView) findViewById(R.id.tv_available_balance);
        tv_jumlah_belanja = (TextView) findViewById(R.id.tv_last_payment);
        iv_iconkredit = (ImageView) findViewById(R.id.iv_iconkredit);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_transaksi);

        Intent intent = getIntent();
        AccountModelsProducts accountModelsProducts = intent.getParcelableExtra("data");
        tv_bank.setText(accountModelsProducts.vendorModels.name);
        tv_credit.setText(accountModelsProducts.nickname);
        tv_kredit_tersedia.setText(toRupiah(accountModelsProducts.getProperties().available_balance));
        tv_jumlah_belanja.setText(toRupiah(accountModelsProducts.getProperties().last_payment));

        LinearLayoutManager linearlayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearlayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Call<CashFlowsResponse> call = RetrofitClientLogged
                .getInstance(this)
                .getApi()
                .getCashflows(accountModelsProducts.id);

        call.enqueue(new Callback<CashFlowsResponse>() {
            @Override
            public void onResponse(Call<CashFlowsResponse> call, Response<CashFlowsResponse> response) {
                final CashFlowsResponse cf = response.body();
                cashFlows = cf.getCashFlowsModels();
                adapterListTransaksi = new AdapterListTransaksi(cashFlows, KartuKredit.this);
                recyclerView.setAdapter(adapterListTransaksi);
            }

            @Override
            public void onFailure(Call<CashFlowsResponse> call, Throwable t) {
                Log.e("Tag","onResponse onFailure" );
            }
        });
    }

    private String toRupiah(double nominal){
        String hasil = "";

        DecimalFormat toRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatAngka = toRupiah.getDecimalFormatSymbols();
        formatAngka.setCurrencySymbol("Rp. ");
        formatAngka.setMonetaryDecimalSeparator(',');
        formatAngka.setGroupingSeparator('.');
        toRupiah.setDecimalFormatSymbols(formatAngka);

        hasil = toRupiah.format(Double.valueOf(nominal));

        return hasil;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
