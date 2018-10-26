package javasign.net.FinAlly.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.api.RetrofitClientLogged;
import javasign.net.FinAlly.fragments.OneFragment;
import javasign.net.FinAlly.models.PromoModels;
import javasign.net.FinAlly.models.PromoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KategoriPromo extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_promo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        loadkategoripromo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadkategoripromo() {

        Intent intent = getIntent();
        int vendor_id = intent.getIntExtra("data", 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        Call<PromoResponse> call = RetrofitClientLogged
                .getInstance(this)
                .getApi()
                .getPromo(vendor_id);

        call.enqueue(new Callback<PromoResponse>() {
            @Override
            public void onResponse(Call<PromoResponse> call, Response<PromoResponse> response) {

                if (response.code() == 200) {
                    progressDialog.dismiss();
                    final PromoResponse pm = response.body();
                    Toast.makeText(KategoriPromo.this, pm.getStatus(),Toast.LENGTH_LONG).show();
                    HashMap<String, ArrayList<PromoModels>> map = new HashMap<>();
                    for (PromoModels promoModels : pm.getPromoModels()) {
                        if (map.get(promoModels.category) != null) {
                            ArrayList<PromoModels> set = map.get(promoModels.category);
                            set.add(promoModels);
                            map.put(promoModels.category, set);
                        } else {
                            ArrayList<PromoModels> p = new ArrayList<>();
                            p.add(promoModels);
                            map.put(promoModels.category, p);
                        }
                    }
                    setupViewPager(viewPager,pm.getPromoModels(),map);
                }
            }

            @Override
            public void onFailure(Call<PromoResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager, List<PromoModels> all, HashMap<String, ArrayList<PromoModels>> map) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(all), "All Promo");
        for (Map.Entry<String, ArrayList<PromoModels>> entry : map.entrySet()) {
            adapter.addFrag(new OneFragment(entry.getValue()), entry.getKey());
        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
