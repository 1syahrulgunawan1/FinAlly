package javasign.net.FinAlly.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.KategoriPromo;
import javasign.net.FinAlly.activities.PilihVendorLoginInternetbangking;
import javasign.net.FinAlly.fragments.FragmentPromo;
import javasign.net.FinAlly.models.VendorModels;

public class AdapterVendorpromo extends RecyclerView.Adapter<AdapterVendorpromo.MyViewHolder> {

    private Context mContext;
    private List<VendorModels> vendorModels;

    public AdapterVendorpromo(List<VendorModels> vendorModels, PilihVendorLoginInternetbangking pilihVendor) {
        this.vendorModels = vendorModels;
        notifyDataSetChanged();
    }

    public AdapterVendorpromo(List<VendorModels> vendorModels, FragmentPromo twoFragment) {
        this.vendorModels = vendorModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_vendorpromo, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final VendorModels vendormodels = vendorModels.get(position);

        switch (vendormodels.getMode()) {
            case "api":
                switch (vendormodels.getName()) {
                    case "Mandiri":
                        holder.relativeLayout.setBackgroundResource(R.drawable.logo_mandiri);
                        break;
                    case "BCA":
                        holder.relativeLayout.setBackgroundResource(R.drawable.logo_bca);
                        break;
                    case "BRI":
                        holder.relativeLayout.setBackgroundResource(R.drawable.logo_bri);
                        break;
                    case "CIMB Niaga":
                        holder.relativeLayout.setBackgroundResource(R.drawable.logo_cimb);
                        break;
                }
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialStyledDialog materialStyledDialog;
                        switch (vendormodels.getName()) {
                            case "Mandiri":
                                         materialStyledDialog = new MaterialStyledDialog.Builder(mContext)
                                        .setHeaderDrawable(R.drawable.logo_mandiri)
                                        .setHeaderColor(R.color.white)
                                        .setTitle(vendormodels.getName())
                                        .setDescription(vendormodels.getDescription())
                                        .setCancelable(true)
                                        .setPositiveText("CHOOSE")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                Intent intent = new Intent(mContext, KategoriPromo.class);
                                                intent.putExtra("data", vendormodels.getId());
                                                mContext.startActivity(intent);
                                            }
                                        })
                                        .build();
                                materialStyledDialog.show();
                                break;
                            case "BCA":
                                        materialStyledDialog = new MaterialStyledDialog.Builder(mContext)
                                        .setHeaderDrawable(R.drawable.logo_bca)
                                        .setHeaderColor(R.color.white)
                                        .setTitle(vendormodels.getName())
                                        .setDescription(vendormodels.getDescription())
                                        .setCancelable(true)
                                        .setPositiveText("CHOOSE")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                Intent intent = new Intent(mContext, KategoriPromo.class);
                                                intent.putExtra("data", vendormodels.getId());
                                                mContext.startActivity(intent);
                                            }
                                        })
                                        .build();
                                materialStyledDialog.show();
                                break;
                            case "BRI":
                                        materialStyledDialog = new MaterialStyledDialog.Builder(mContext)
                                        .setHeaderDrawable(R.drawable.logo_bri)
                                        .setHeaderColor(R.color.white)
                                        .setTitle(vendormodels.getName())
                                        .setDescription(vendormodels.getDescription())
                                        .setCancelable(true)
                                        .setPositiveText("CHOOSE")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                Intent intent = new Intent(mContext, KategoriPromo.class);
                                                intent.putExtra("data", vendormodels.getId());
                                                mContext.startActivity(intent);
                                            }
                                        })
                                        .build();
                                materialStyledDialog.show();
                                break;
                            case "CIMB Niaga":
                                        materialStyledDialog = new MaterialStyledDialog.Builder(mContext)
                                        .setHeaderDrawable(R.drawable.logo_cimb)
                                        .setHeaderColor(R.color.white)
                                        .setTitle(vendormodels.getName())
                                        .setDescription(vendormodels.getDescription())
                                        .setCancelable(true)
                                        .setPositiveText("CHOOSE")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                Intent intent = new Intent(mContext, KategoriPromo.class);
                                                intent.putExtra("data", vendormodels.getId());
                                                mContext.startActivity(intent);
                                            }
                                        })
                                        .build();
                                materialStyledDialog.show();
                                break;
                        }

                    }
                });
                holder.btn_promo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, KategoriPromo.class);
                        intent.putExtra("data", vendormodels.getId());
                        mContext.startActivity(intent);
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return vendorModels == null ? 0 : vendorModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name_bank;
        CardView cardView;
        Button btn_promo;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name_bank = (TextView) itemView.findViewById(R.id.tv_bank);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            btn_promo = (Button) itemView.findViewById(R.id.btn_promo);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.one);
        }
    }
}
