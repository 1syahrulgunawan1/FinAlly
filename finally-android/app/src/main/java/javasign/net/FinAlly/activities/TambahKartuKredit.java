package javasign.net.FinAlly.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import javasign.net.FinAlly.Card;
import javasign.net.FinAlly.R;

public class TambahKartuKredit extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText ed_namakartu, ed_limitkredit, ed_bunga, ed_minimumpayment;
    private TextView tv_namabank, tv_cetaktagihan, tv_jatuhtempo;
    private ImageView iv_pilihbank;
    private Button btn_tambah;
    private ProgressDialog progressDialog;
    private int mYear, mMonth, mDay;
    private Spinner spinner;

    String[] ClipcodesText;
    Integer[] ClipcodesImage;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference data = db.collection("Data");


    private static final String TAG = "TambahKartuKredit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kartu_kredit);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_namakartu = (EditText) findViewById(R.id.ed_namakartu);
        ed_limitkredit = (EditText) findViewById(R.id.ed_limitkredit);
        ed_bunga = (EditText) findViewById(R.id.ed_bunga);
        ed_minimumpayment = (EditText) findViewById(R.id.ed_minimumpayment);
        tv_namabank = (TextView) findViewById(R.id.tv_namabank);
        tv_cetaktagihan = (TextView) findViewById(R.id.tv_cetaktagihan);
        tv_jatuhtempo = (TextView) findViewById(R.id.tv_jatuhtempo);
        iv_pilihbank = (ImageView) findViewById(R.id.iv_color);
        btn_tambah = (Button) findViewById(R.id.btn_tambah);
        spinner = (Spinner)findViewById(R.id.spinner);

        ClipcodesText = new String[]{"MasterCard", "Visa Card", "Visa Platinum", "American Express"};
        ClipcodesImage = new Integer[]{R.drawable.ic_master_card, R.drawable.ic_visa, R.drawable.ic_visa_platinum, R.drawable.ic_american_express};

        MyAdapterSpinner adapter = new MyAdapterSpinner(getApplicationContext(), R.layout.list_spinner, ClipcodesText, ClipcodesImage);

        spinner.setAdapter(adapter);


        iv_pilihbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahKartuKredit.this, PilihVendorTambahkartukredit.class);
                startActivityForResult(intent, 1);
            }
        });

        tv_cetaktagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahKartuKredit.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_cetaktagihan.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tv_cetaktagihan.setTextColor(ContextCompat.getColor(TambahKartuKredit.this, R.color.colorAccent));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        tv_jatuhtempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TambahKartuKredit.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_jatuhtempo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tv_jatuhtempo.setTextColor(ContextCompat.getColor(TambahKartuKredit.this, R.color.colorAccent));

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        btn_tambah.setOnClickListener(this);

    }

    protected class MyAdapterSpinner extends ArrayAdapter {

        Integer[] Image;
        String[] Text;

        public MyAdapterSpinner(Context context, int resource, String[] text, Integer[] image) {
            super(context, resource, text);
            Image = image;
            Text = text;
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.list_spinner, parent, false);

            //Set Custom View
            TextView tv = (TextView) view.findViewById(R.id.textView);
            ImageView img = (ImageView) view.findViewById(R.id.imageView);

            tv.setText(Text[position]);
            img.setImageResource(Image[position]);

            return view;
        }

        @Override
        public View getDropDownView(int position,View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == PilihVendorTambahkartukredit.RESULT_OK){
                String Name = data.getStringExtra("data");
                tv_namabank.setText(Name);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_tambah) {
            savecard();
        }
    }

    public void savecard() {

        String cardname = ed_namakartu.getText().toString();
        String limit = ed_limitkredit.getText().toString();
        String vendor = tv_namabank.getText().toString();
        String bill = tv_cetaktagihan.getText().toString();
        String due_date = tv_jatuhtempo.getText().toString();
        String interest = ed_bunga.getText().toString();
        String minimum_payment = ed_minimumpayment.getText().toString();
        String type_card = spinner.getSelectedItem().toString();

        if (cardname.isEmpty()) {
            ed_namakartu.setError("Cardname is required");
            ed_namakartu.requestFocus();
            return;
        }

        if (limit.isEmpty()) {
            ed_limitkredit.setError("is required");
            ed_limitkredit.requestFocus();
            return;
        }

        if (vendor.isEmpty()) {
            tv_namabank.setError("is required");
            tv_namabank.requestFocus();
            return;
        }

        if (bill.isEmpty()) {
            tv_cetaktagihan.setError("is required");
            tv_cetaktagihan.requestFocus();
            return;
        }

        if (due_date.isEmpty()) {
            tv_jatuhtempo.setError("is required");
            tv_jatuhtempo.requestFocus();
            return;
        }

        if (interest.isEmpty()) {
            ed_bunga.setError("is required");
            ed_bunga.requestFocus();
            return;
        }

        if (minimum_payment.isEmpty()) {
            ed_minimumpayment.setError("is required");
            ed_minimumpayment.requestFocus();
            return;
        }

        else {
            progressDialog = new ProgressDialog(TambahKartuKredit.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            Card card = new Card(cardname, limit, vendor, bill, due_date, interest, minimum_payment, type_card);
            data.add(card)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.dismiss();
                            Toast.makeText(TambahKartuKredit.this, "Sucessfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(TambahKartuKredit.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(TambahKartuKredit.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
        }

    }
}
