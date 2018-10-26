package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterListTransaksio;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.CashFlowsModels;
import javasign.net.FinAlly.models.CashFlowsModelsDate;
import javasign.net.FinAlly.models.CashFlowsResponse;
import javasign.net.FinAlly.models.CashFlowsUpdates;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static javasign.net.FinAlly.adapters.AdapterListTransaksio.formateDateFromstring;

public class TransaksiDetails extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_amount, tv_category, tv_description, tv_tanggal;
    private Button btn_paid;
    private ProgressDialog progressDialog;
    private AdapterListTransaksio adapterListTransaksi;
    private List<CashFlowsModels> cashFlows;

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
        tv_tanggal = (TextView) findViewById(R.id.tv_tanggal);
        btn_paid = (Button) findViewById(R.id.btn_paid);
        Intent intent = getIntent();
        CashFlowsModelsDate date = intent.getParcelableExtra("data4");
        tv_amount.setText(toRupiah(intent.getDoubleExtra("data1", 0)));
        tv_category.setText(intent.getStringExtra("data2"));
        tv_description.setText(intent.getStringExtra("data3"));
        String tanggal = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", date.original);
        tv_tanggal.setText(date.day + ", " + tanggal);

        switch (intent.getStringExtra("data6")) {
            case "paid":
                btn_paid.setBackgroundColor(getResources().getColor(R.color.green));
                btn_paid.setTextColor(Color.WHITE);
                btn_paid.setText("Paid");
                btn_paid.setEnabled(false);
                break;
            case "unpaid":
                btn_paid.setBackgroundColor(getResources().getColor(R.color.yellow));
                btn_paid.setTextColor(Color.BLACK);
                btn_paid.setText("Mark as paid");
                btn_paid.setEnabled(true);
        }

        btn_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paid();
            }
        });

        switch (intent.getStringExtra("data5")) {
            case "DB":
                tv_amount.setTextColor(Color.parseColor("#2E7D32"));
                break;
            case "CR":
                tv_amount.setTextColor(Color.RED);
                break;
        }

    }

    public void paid() {
        Intent intent = getIntent();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        int cashflow_id = intent.getIntExtra("data", 0);

        Call<CashFlowsUpdates> call = RetrofitClientLogged
                .getInstance(this)
                .getApi()
                .updatepaid(cashflow_id, "paid");

        call.enqueue(new Callback<CashFlowsUpdates>() {
            @Override
            public void onResponse(Call<CashFlowsUpdates> call, Response<CashFlowsUpdates> response) {
                progressDialog.dismiss();
                String res = response.toString();
                Log.e("onResponse", res);
                CashFlowsUpdates cfu = response.body();
                btn_paid.setBackgroundColor(getResources().getColor(R.color.green));
                btn_paid.setTextColor(Color.WHITE);
                btn_paid.setText("Paid");
                btn_paid.setEnabled(false);
            }

            @Override
            public void onFailure(Call<CashFlowsUpdates> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Tag", String.valueOf(t));
            }
        });


    }

    private String toRupiah(double nominal) {
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
            onBackPressed();;
        }
        return super.onOptionsItemSelected(item);
    }
}
