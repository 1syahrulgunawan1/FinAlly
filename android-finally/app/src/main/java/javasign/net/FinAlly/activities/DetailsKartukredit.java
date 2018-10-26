package javasign.net.FinAlly.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.models.AccountModelsProducts;

public class DetailsKartukredit extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_bank, tv_credit, tv_cardholder, tv_kredit_tersedia, tv_jumlah_belanja;
    private TextView tv_cardnumber, tv_statement_date, tv_due_date, tv_last_payment, tv_creditlimit;
    private ImageView iv_iconkredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_kartukredit);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_bank = (TextView) findViewById(R.id.tv_name_bank);
        tv_credit = (TextView) findViewById(R.id.tv_credit);
        tv_cardholder = (TextView) findViewById(R.id.tv_cardname);
        iv_iconkredit = (ImageView) findViewById(R.id.iv_iconkredit);
        tv_kredit_tersedia = (TextView) findViewById(R.id.tv_available_balance);
        tv_jumlah_belanja = (TextView) findViewById(R.id.tv_last_payment);

        tv_cardnumber = (TextView) findViewById(R.id.tv_cardnumber);
        tv_statement_date = (TextView) findViewById(R.id.tv_statment_date);
        tv_due_date = (TextView) findViewById(R.id.tv_due_date);
        tv_last_payment = (TextView) findViewById(R.id.tv_lastpayment);
        tv_creditlimit = (TextView) findViewById(R.id.tv_creditlimit);

        Intent intent = getIntent();
        final AccountModelsProducts accountModelsProducts = intent.getParcelableExtra("data");
        tv_bank.setText(accountModelsProducts.nickname);
        tv_credit.setText(accountModelsProducts.vendorModels.name);
        tv_cardholder.setText(accountModelsProducts.getProperties().cardholder);
        tv_kredit_tersedia.setText(toRupiah(accountModelsProducts.getProperties().available_balance));
        tv_jumlah_belanja.setText(toRupiah(accountModelsProducts.getProperties().minimum_payment));
        tv_cardnumber.setText(accountModelsProducts.number);
        tv_statement_date.setText(accountModelsProducts.getProperties().statementDate);
        tv_due_date.setText(accountModelsProducts.properties.due_date);
        tv_last_payment.setText(toRupiah(accountModelsProducts.properties.last_payment));
        tv_creditlimit.setText(toRupiah(accountModelsProducts.properties.credit_limit));

        switch (accountModelsProducts.nickname) {
            case "MasterCard":
                iv_iconkredit.setImageResource(R.drawable.ic_master_card);
                break;
            case "Visa Card":
                iv_iconkredit.setImageResource(R.drawable.ic_visa);
                break;
        }

    }

    private String toRupiah(double nominal){
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

    private String toRupiah(String nominal){
        return toRupiah(Double.valueOf(nominal.replace("(","").replace(")","").replace(" ","")));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
