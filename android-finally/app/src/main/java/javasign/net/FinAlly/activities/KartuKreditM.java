package javasign.net.FinAlly.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import javasign.net.FinAlly.CashFlow;
import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterListTransaksim;
import javasign.net.FinAlly.storage.SharedPrefManager;

public class KartuKreditM extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_bank, tv_credit, tv_cardholder, tv_toolbar_title, tv_kredit_tersedia, tv_jumlah_belanja;
    private ImageView iv_iconkredit;
    RecyclerView recyclerView;

    private AdapterListTransaksim adapterListTransaksi;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionMenu fab_menu;

    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_kreditm);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        tv_bank = (TextView) findViewById(R.id.tv_name_bank);
        tv_credit = (TextView) findViewById(R.id.tv_credit);
        tv_cardholder = (TextView) findViewById(R.id.tv_cardname);
        tv_kredit_tersedia = (TextView) findViewById(R.id.tv_available_balance);
        tv_jumlah_belanja = (TextView) findViewById(R.id.tv_last_payment);
        iv_iconkredit = (ImageView) findViewById(R.id.iv_iconkredit);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_transaksi);

        LinearLayoutManager linearlayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearlayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 ||dy<0 && fab_menu.isShown())
                {
                    fab_menu.hideMenuButton(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    fab_menu.showMenuButton(true);
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        String typecard = intent.getStringExtra("fb2");
        String bankname = intent.getStringExtra("fb1");
        final String duedate = intent.getStringExtra("fb7");
        final String cardholder = intent.getStringExtra("fb3");
        final int vendor = intent.getIntExtra("fb6", 0);
        switch (typecard) {
            case "MasterCard":
                iv_iconkredit.setImageResource(R.drawable.ic_master_card);
                break;
            case "Visa Card":
                iv_iconkredit.setImageResource(R.drawable.ic_visa);
                break;
        }
        tv_toolbar_title.setText(typecard + " " + bankname);
        tv_bank.setText(intent.getStringExtra("fb2"));
        tv_credit.setText(intent.getStringExtra("fb1"));
        tv_cardholder.setText(cardholder);
        tv_kredit_tersedia.setText(toRupiah(intent.getDoubleExtra("fb4", 0)));
        tv_jumlah_belanja.setText(toRupiah(intent.getDoubleExtra("fb5", 0)));


        fab_menu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fab_menu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KartuKreditM.this, TambahTransaksi.class);
                intent.putExtra("key", key);
                intent.putExtra("cardname", cardholder);
                intent.putExtra("vendor", vendor);
                intent.putExtra("duedate", duedate);
                startActivity(intent);
            }
        });

        String document_id = String.valueOf(SharedPrefManager.getInstance(this).getUser().getId());
        db.collection("Users").document(document_id).collection("cashflow").whereEqualTo("account_key", key).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<CashFlow> cashFlows = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CashFlow c = document.toObject(CashFlow.class);
                        cashFlows.add(c);
                        Log.d("tes", document.getId() + " => " + document.getData());
                    }
                    adapterListTransaksi = new AdapterListTransaksim(cashFlows);
                    recyclerView.setAdapter(adapterListTransaksi);
                } else {
                    Log.d("tes", "Error getting documents: ", task.getException());
                }
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
