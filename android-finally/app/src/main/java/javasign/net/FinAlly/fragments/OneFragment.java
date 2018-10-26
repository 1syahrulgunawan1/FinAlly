package javasign.net.FinAlly.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.KategoriPromo;
import javasign.net.FinAlly.adapters.AdapterListKartukredit;
import javasign.net.FinAlly.adapters.AdapterListKategoripromo;
import javasign.net.FinAlly.adapters.AdapterTempatPromo;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.AccountModelsProducts;
import javasign.net.FinAlly.models.PromoDetailModels;
import javasign.net.FinAlly.models.PromoModels;
import javasign.net.FinAlly.models.PromoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OneFragment extends Fragment {

    public OneFragment(){

    }

    List<PromoModels> promoModels;

    @SuppressLint("ValidFragment")
    public OneFragment(List<PromoModels> promoModels) {
        // Required empty public constructor
        this.promoModels = promoModels;
    }

    RecyclerView recyclerview;
    AdapterTempatPromo adapterTempatPromo;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview_tempatpromo);
        LinearLayoutManager linearlayout = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearlayout);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        final ArrayList<PromoDetailModels> promoDetailModels = new ArrayList<>();
        for (PromoModels promoModels : promoModels) {
            for (PromoDetailModels pdm : promoModels.getPromo_details()) {
                promoDetailModels.add(pdm);
            }
        }

        adapterTempatPromo = new AdapterTempatPromo(promoDetailModels);
        recyclerview.setAdapter(adapterTempatPromo);

        return rootView;
    }

}
