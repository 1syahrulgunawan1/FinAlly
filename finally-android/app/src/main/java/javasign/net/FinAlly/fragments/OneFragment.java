package javasign.net.FinAlly.fragments;

import android.content.Intent;
import android.os.Bundle;
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

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.MainActivity;
import javasign.net.FinAlly.activities.PilihVendorLoginInternetbangking;
import javasign.net.FinAlly.activities.TambahKartuKredit;
import javasign.net.FinAlly.adapters.AdapterListKartukredit;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.AccountModels;
import javasign.net.FinAlly.models.AccountModelsProducts;
import javasign.net.FinAlly.models.AccountResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OneFragment extends Fragment {

    public OneFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerview;
    private FloatingActionMenu fabmenu;
    private AdapterListKartukredit adapterListKartuKredit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);


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

        return rootView;
    }

    private void loadaccounts() {

        Call<AccountResponse> call = RetrofitClientLogged
                .getInstance(this.getActivity())
                .getApi()
                .getAccounts();
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {

                        AccountResponse ar = response.body();
                        ArrayList<AccountModelsProducts> products = new ArrayList<>();
                        if(ar.getAccount() != null) {
                            for (AccountModels ac : ar.getAccount()) {
                                for (AccountModelsProducts pro : ac.getProducts()) {
                                    pro.setVendorModels(ac.getVendor());
                                    products.add(pro);
                                }
                            }
                            adapterListKartuKredit = new AdapterListKartukredit(products);
                            recyclerview.setAdapter(adapterListKartuKredit);
                        }


            }
            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.e("Tag","onResponse onFailure" );
            }
        });

    }
}
