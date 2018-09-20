package javasign.net.FinAlly.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.models.PromoDetailModels;

public class TempatPromoDetails extends AppCompatActivity {

    private ImageView iv_logo;
    private TextView txt_duedate,txt_deskripsi,txt_ketentuan,txt_lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_promo_details);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        iv_logo = (ImageView) findViewById(R.id.image);
        txt_duedate = (TextView) findViewById(R.id.due_date);
        txt_deskripsi = (TextView) findViewById(R.id.name);
        txt_ketentuan = (TextView) findViewById(R.id.ketentuan);
        txt_lokasi = (TextView) findViewById(R.id.lokasi);

        Intent inten = getIntent();
        PromoDetailModels models  = inten.getParcelableExtra("data");
        Picasso.get().load(models.logo).into(iv_logo);
        collapsingToolbar.setTitle(models.name);
        txt_duedate.setText(models.due_date);
        txt_deskripsi.setText(models.description);
        txt_ketentuan.setText(models.ketentuan);
        txt_lokasi.setText(models.lokasi);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
