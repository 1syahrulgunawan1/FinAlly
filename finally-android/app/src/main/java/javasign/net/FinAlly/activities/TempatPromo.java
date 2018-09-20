package javasign.net.FinAlly.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterTempatPromo;
import javasign.net.FinAlly.models.PromoDetailModels;

public class TempatPromo extends AppCompatActivity {

    RecyclerView recyclerview;
    AdapterTempatPromo adapterTempatPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_promo);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview_tempatpromo);
        LinearLayoutManager linearlayout = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearlayout);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapterTempatPromo = new AdapterTempatPromo();
        recyclerview.setAdapter(adapterTempatPromo);
        ArrayList<PromoDetailModels> promoDetails = getIntent().getParcelableArrayListExtra("data");
        if(promoDetails != null){
            adapterTempatPromo.setPromoDetailModels(promoDetails);
        }
    }
}
