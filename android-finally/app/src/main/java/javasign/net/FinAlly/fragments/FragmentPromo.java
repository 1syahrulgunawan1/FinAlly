package javasign.net.FinAlly.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.adapters.AdapterVendorpromo;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.models.VendorModels;
import javasign.net.FinAlly.models.VendorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPromo extends Fragment {

    public FragmentPromo() {
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
        View rootView = inflater.inflate(R.layout.fragment_promo, container, false);

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

                if (response.code() == 200) {
                    VendorResponse vr = response.body();
                    vendorModels = vr.getItemvendor();
                    adapterVendorpromo = new AdapterVendorpromo(vendorModels, FragmentPromo.this);
                    recyclerview.setAdapter(adapterVendorpromo);
                } else if (response.code() == 502) {
                    Toast.makeText(getActivity(), "Time out", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VendorResponse> call, Throwable t) {

            }
        });

    }

}
