package javasign.net.FinAlly.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.models.CashFlowsModels;
import javasign.net.FinAlly.models.CashFlowsModelsDate;

public class TransaksiDetails extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_amount, tv_category, tv_description, tv_day, tv_original;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_category = (TextView) findViewById(R.id.tv_bills);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_original = (TextView) findViewById(R.id.tv_ori);
        Intent intent = getIntent();
        CashFlowsModelsDate date = intent.getParcelableExtra("data4");
        tv_amount.setText(toRupiah(intent.getDoubleExtra("data1", 0)));
        tv_category.setText(intent.getStringExtra("data2"));
        tv_description.setText(intent.getStringExtra("data3"));
        tv_day.setText(date.day);
        tv_original.setText(date.original);

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
