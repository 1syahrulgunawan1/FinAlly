package javasign.net.FinAlly.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.MainActivity;
import javasign.net.FinAlly.adapters.AdapterVendorpromo;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.VendorModels;
import javasign.net.FinAlly.models.VendorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TwoFragment extends Fragment {

    public TwoFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerview;
    AdapterVendorpromo adapterVendorpromo;
    private List<VendorModels> vendorModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_two, container, false);

        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        loadvendor();

        return rootView;

    }

    private void loadvendor() {

        Call<VendorResponse> call = RetrofitClientLogged
                .getInstance(this.getActivity())
                .getApi()
                .getVendor();

        call.enqueue(new Callback<VendorResponse>() {
            @Override
            public void onResponse(Call<VendorResponse> call, Response<VendorResponse> response) {
                VendorResponse vr = response.body();
                vendorModels = vr.getItemvendor();
                adapterVendorpromo = new AdapterVendorpromo(vendorModels, TwoFragment.this);
                recyclerview.setAdapter(adapterVendorpromo);
            }

            @Override
            public void onFailure(Call<VendorResponse> call, Throwable t) {

            }
        });

    }

}
