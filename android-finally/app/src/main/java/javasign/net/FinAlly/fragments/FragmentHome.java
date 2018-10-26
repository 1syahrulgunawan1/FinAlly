package javasign.net.FinAlly.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import javasign.net.FinAlly.Card;
import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.Login;
import javasign.net.FinAlly.activities.PilihVendorLoginInternetbangking;
import javasign.net.FinAlly.activities.TambahKartuKredit;
import javasign.net.FinAlly.adapters.AdapterListKartukredit;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.AccountModels;
import javasign.net.FinAlly.models.AccountModelsProducts;
import javasign.net.FinAlly.models.AccountResponse;
import javasign.net.FinAlly.storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


public class FragmentHome extends Fragment {

    public FragmentHome() {
        // Required empty public constructor
    }


    private ProgressDialog progressDialog;
    private RecyclerView recyclerview;
    private FloatingActionMenu fabmenu;
    private TextView tv_number;
    private AdapterListKartukredit adapterListKartuKredit;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        tv_number = (TextView) rootView.findViewById(R.id.tv_number);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview_kartu_kredit);
        LinearLayoutManager linearlayout = new LinearLayoutManager(this.getActivity());
        recyclerview.setLayoutManager(linearlayout);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        fabmenu = (FloatingActionMenu) rootView.findViewById(R.id.fb_menu);

        FloatingActionButton itemone = new FloatingActionButton(getActivity());
        itemone.setButtonSize(FloatingActionButton.SIZE_MINI);
        itemone.setLabelText("Connect Now");
        itemone.setImageResource(R.drawable.ic_add);

        itemone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PilihVendorLoginInternetbangking.class);
                startActivity(intent);
            }
        });

        fabmenu.addMenuButton(itemone);

        FloatingActionButton itemtwo = new FloatingActionButton(getActivity());
        itemtwo.setButtonSize(FloatingActionButton.SIZE_MINI);
        itemtwo.setLabelText("Create Now");
        itemtwo.setImageResource(R.drawable.ic_add);

        itemtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TambahKartuKredit.class);
                startActivity(intent);
            }
        });

        fabmenu.addMenuButton(itemtwo);

        fabmenu.setClosedOnTouchOutside(true);

        loadaccounts();

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 ||dy<0 && fabmenu.isShown())
                {
                    fabmenu.hideMenuButton(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    fabmenu.showMenuButton(true);
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return rootView;
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


    private void loadaccounts() {

        progressDialog = new ProgressDialog(FragmentHome.this.getActivity());
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        Call<AccountResponse> call = RetrofitClientLogged
                .getInstance(this.getActivity())
                .getApi()
                .getAccounts();
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(final Call<AccountResponse> call, Response<AccountResponse> response) {

                if (response.code() == 200) {
                    progressDialog.dismiss();
                    String res = response.toString();
                    Log.e("onResponse", res);

                    AccountResponse ar = response.body();

                    switch (ar.getStatus()) {
                        case "error":
                            SharedPrefManager.getInstance(getActivity()).clear();
                            Intent intent = new Intent(getActivity(), Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            break;
                    }

                    final ArrayList<AccountModelsProducts> products = new ArrayList<>();
                    if (ar.getAccount() != null) {
                        double hasil = 0;
                        for (AccountModels ac : ar.getAccount()) {
                            for (AccountModelsProducts pro : ac.getProducts()) {
                                pro.setVendorModels(ac.getVendor());
                                pro.idAccountModels = ac.id;
                                hasil += pro.balance;
                                products.add(pro);
                            }
                        }
                        tv_number.setText(toRupiah(hasil));
                        adapterListKartuKredit = new AdapterListKartukredit(products);
                        recyclerview.setAdapter(adapterListKartuKredit);
                    }

                    String document_id = String.valueOf(SharedPrefManager.getInstance(getActivity()).getUser().getId());
                    db.collection("Users").document(document_id).collection("Data").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        ArrayList<Card> cards = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Card card = document.toObject(Card.class);
                                            card.key = document.getId();
                                            cards.add(card);
                                            adapterListKartuKredit.setCards(cards);
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                } else if (response.code() == 502) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Time out", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.e("Tag", "onResponse onFailure");
            }
        });

    }
}
