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

import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterListTransaksimore;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.CashFlowsModels;
import javasign.net.FinAlly.models.CashFlowsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTransaksi extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;

    private AdapterListTransaksimore adapterListTransaksimore;
    private List<CashFlowsModels> cashFlows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_transaksi);

        LinearLayoutManager linearlayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearlayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();

        Call<CashFlowsResponse> call = RetrofitClientLogged
                .getInstance(this)
                .getApi()
                .getCashflows(intent.getIntExtra("data", 0));

        call.enqueue(new Callback<CashFlowsResponse>() {
            @Override
            public void onResponse(Call<CashFlowsResponse> call, Response<CashFlowsResponse> response) {
                String res = response.toString();
                Log.e("onResponse", res);
                final CashFlowsResponse cf = response.body();
                cashFlows = cf.getCashFlowsModels();
                adapterListTransaksimore = new AdapterListTransaksimore(cashFlows, ListTransaksi.this);
                recyclerView.setAdapter(adapterListTransaksimore);
            }

            @Override
            public void onFailure(Call<CashFlowsResponse> call, Throwable t) {
                Log.e("Tag","onResponse onFailure" );
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

}
