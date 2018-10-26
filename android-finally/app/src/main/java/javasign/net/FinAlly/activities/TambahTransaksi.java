package javasign.net.FinAlly.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nmaltais.calcdialog.CalcDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javasign.net.FinAlly.CashFlow;
import javasign.net.FinAlly.InputFilterMinMax;
import javasign.net.FinAlly.R;
import javasign.net.FinAlly.storage.SharedPrefManager;

public class TambahTransaksi extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, CalcDialog.CalcDialogCallback {

    private Toolbar toolbar;
    private EditText ed_biaya, ed_tanggal, ed_deskripsi, ed_akun, ed_cicilan, ed_tags, ed_note;
    private TextView tv_cicilan, tv_alamat, ed_lokasi, tv_kategori;
    private Button btn_tambah;
    private CheckBox cb_cicilan;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private static final int DIALOG_REQUEST_CODE = 0;
    private Spinner spinner_kategori, spinner_invoice;
    private ProgressDialog progressDialog;

    private int mYear, mMonth, mDay, id_vendor;
    private String value_addres, value_placename, lat, lang, value_amount;
    private @Nullable
    BigDecimal value;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "TambahTaransaksi";

    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_biaya = (EditText) findViewById(R.id.ed_biaya);
        ed_tanggal = (EditText) findViewById(R.id.ed_tanggal);
        ed_deskripsi = (EditText) findViewById(R.id.ed_deskripsi);
        ed_akun = (EditText) findViewById(R.id.ed_akun);
        ed_lokasi = (TextView) findViewById(R.id.ed_lokasi);
        ed_tags = (EditText) findViewById(R.id.tagsEditText);
        ed_note = (EditText) findViewById(R.id.ed_note);
        tv_alamat = (TextView) findViewById(R.id.tv_alamat);
        cb_cicilan = (CheckBox) findViewById(R.id.cb_cicilan);
        ed_cicilan = (EditText) findViewById(R.id.ed_cicilan);
        tv_cicilan = (TextView) findViewById(R.id.tv_hasil_cicilan);
        btn_tambah = (Button) findViewById(R.id.btn_tambah);
        spinner_kategori = (Spinner) findViewById(R.id.spinner_kategori);
        spinner_invoice = (Spinner) findViewById(R.id.spinner_invoice);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        ed_akun.setText(intent.getStringExtra("cardname"));
        id_vendor = intent.getIntExtra("vendor", 0);

        ed_biaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CalcDialog calcDialog = CalcDialog.newInstance(DIALOG_REQUEST_CODE);

                calcDialog.setShowSignButton(false);

                FragmentManager fm = getSupportFragmentManager();
                if (fm.findFragmentByTag("calc_dialog") == null) {
                    calcDialog.show(fm, "calc_dialog");
                }
            }
        });

        ed_biaya.setText(value_amount);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(" dd-MM-yyyy ");
        String strDate = mdformat.format(calendar.getTime());

        ed_tanggal.setText(strDate);
        ed_tanggal.setTextColor(ContextCompat.getColor(TambahTransaksi.this, R.color.colorAccent));

        ed_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahTransaksi.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ed_tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        SimpleDateFormat parseFormat = new SimpleDateFormat("EEEE");
                        Date date = new Date(year, monthOfYear, dayOfMonth);
                        ed_tanggal.setTextColor(ContextCompat.getColor(TambahTransaksi.this, R.color.colorAccent));

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        FirebaseFirestore.getInstance().collection("Kategori").document("Expenses").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                List<Kategori> kategori = (List<Kategori>) document.get("1");
                ArrayAdapter<Kategori> adapter = new ArrayAdapter<Kategori>(TambahTransaksi.this,android.R.layout.simple_spinner_dropdown_item, kategori);
                spinner_kategori.setAdapter(adapter);
            }
        });

        String duedate = "Invoice: " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", intent.getStringExtra("duedate"));

        List<String> date = new ArrayList<>();
        date.add(duedate);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahTransaksi.this, android.R.layout.simple_spinner_item, date);
        spinner_invoice.setAdapter(adapter);

        ed_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(TambahTransaksi.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        cb_cicilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_cicilan.isChecked()) {
                    ed_cicilan.setVisibility(View.VISIBLE);
                    tv_cicilan.setVisibility(View.VISIBLE);
                } else {
                    ed_cicilan.setVisibility(View.GONE);
                    tv_cicilan.setVisibility(View.GONE);
                }
            }
        });

        ed_cicilan.setFilters(new InputFilter[]{new InputFilterMinMax("1", "99")});
        String value_cicilan1 = ed_cicilan.getText().toString();
        ed_cicilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (!value_cicilan1.isEmpty()) {
            int value_biaya = Integer.parseInt(ed_biaya.getText().toString());
            int value_cicilan2 = Integer.parseInt(value_cicilan1);
            int hasil = value_biaya / value_cicilan2;
            tv_cicilan.setText(String.valueOf(hasil));
            Log.d("tes12", String.valueOf(hasil));
        }

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtransaksi();
            }
        });
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0;
    }


    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }

    public void sendtransaksi() {
        int amount = Integer.parseInt(value_amount);
        String amoun = ed_biaya.getText().toString();
        String value_tanggal = formateDateFromstring("dd-MM-yyyy", "yyyy-MM-dd", ed_tanggal.getText().toString());
        String value_day = formateDateFromstring("dd-MM-yyyy", "EEEE", ed_tanggal.getText().toString());
        String description = ed_deskripsi.getText().toString();
        String category = "Clothing";
        String tags = ed_tags.getText().toString();
        String note = ed_note.getText().toString();

        if (amoun.isEmpty()) {
            ed_biaya.setError("is required");
            ed_biaya.requestFocus();
            return;
        }

        CashFlow.Date date = new CashFlow.Date(value_tanggal, value_day);
        CashFlow.Location location = new CashFlow.Location(value_placename, value_addres, lat, lang);
        CashFlow cashFlow = new CashFlow(SharedPrefManager.getInstance(this).getUser().getId(), id_vendor, category, description, "CR", "Unpaid", tags, note, amount, date, location);
        cashFlow.account_key = key;
        progressDialog = new ProgressDialog(TambahTransaksi.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        db.collection("Users").document(String.valueOf(SharedPrefManager.getInstance(this).getUser().getId())).collection("cashflow").add(cashFlow)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(TambahTransaksi.this, "Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(TambahTransaksi.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TambahTransaksi.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(ed_lokasi, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append("Alamat: ");
                stBuilder.append(address);
                value_placename = placename;
                value_addres = address;
                lat = latitude;
                lang = longitude;
                ed_lokasi.setText(placename);
                tv_alamat.setVisibility(View.VISIBLE);
                tv_alamat.setText(stBuilder.toString());
            }
        }
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
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        if (value != null) {
            state.putString("value", value.toString());
        }
    }

    @Override
    public void onValueEntered(int requestCode, BigDecimal value) {
        // if (requestCode == DIALOG_REQUEST_CODE) {}  <-- If there's many dialogs
        this.value = value;
        ed_biaya.setText(toRupiah(value.toPlainString()));
        value_amount = value.toPlainString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
