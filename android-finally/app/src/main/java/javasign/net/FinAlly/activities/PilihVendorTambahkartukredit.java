package javasign.net.FinAlly.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterPilihvendortambahkartukredit;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.VendorModels;
import javasign.net.FinAlly.models.VendorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihVendorTambahkartukredit extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerview;
    private AdapterPilihvendortambahkartukredit adapterPilihvendor;
    private List<VendorModels> vendorModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_vendor_tambahkartukredit);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());


        loadvendor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadvendor() {

        Call<VendorResponse> call = RetrofitClientLogged
                .getInstance(this)
                .getApi()
                .getVendor();

        call.enqueue(new Callback<VendorResponse>() {
            @Override
            public void onResponse(Call<VendorResponse> call, Response<VendorResponse> response) {

                if (response.code() == 200) {
                    VendorResponse vr = response.body();
                    vendorModels = vr.getItemvendor();
                    adapterPilihvendor = new AdapterPilihvendortambahkartukredit(vendorModels, PilihVendorTambahkartukredit.this);
                    adapterPilihvendor.setClickOnAdapterVendor(new AdapterPilihvendortambahkartukredit.OnClickAdapterVendor() {
                        @Override
                        public void OnClick(VendorModels vendorModels, View view) {
                            Intent intent = new Intent(PilihVendorTambahkartukredit.this, TambahKartuKredit.class);
                            intent.putExtra("data1", vendorModels.getId());
                            intent.putExtra("data2", vendorModels.getName());
                            PilihVendorTambahkartukredit.this.setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    recyclerview.setHasFixedSize(true);
                    recyclerview.setAdapter(adapterPilihvendor);
                } else if (response.code() == 502) {
                    Toast.makeText(PilihVendorTambahkartukredit.this, "Time out", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VendorResponse> call, Throwable t) {
                Log.e("Tag", "onResponse onFailure");
            }
        });
    }
}
