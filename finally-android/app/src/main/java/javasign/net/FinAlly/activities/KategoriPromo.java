package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterListKategoripromo;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.PromoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriPromo extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerview;
    private ProgressDialog progressDialog;
    private AdapterListKategoripromo adapterListKategoripromo;

    private int vendor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_promo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_kategoripromo);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        vendor_id = intent.getIntExtra("data", 0);

        loadkategoripromo();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadkategoripromo() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        Call<PromoResponse> call = RetrofitClientLogged
                .getInstance(this)
                .getApi()
                .getPromo(vendor_id);

        call.enqueue(new Callback<PromoResponse>() {
            @Override
            public void onResponse(Call<PromoResponse> call, Response<PromoResponse> response) {
                progressDialog.dismiss();
                final PromoResponse pm = response.body();

                if (response.code() == 200) {
                    adapterListKategoripromo = new AdapterListKategoripromo(pm.getPromoModels());
                     Toast.makeText(KategoriPromo.this, "sukses", Toast.LENGTH_LONG).show();
                    recyclerview.setAdapter(adapterListKategoripromo);
                }
            }
            @Override
            public void onFailure(Call<PromoResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
