package javasign.net.FinAlly.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.PilihVendorTambahkartukredit;
import javasign.net.FinAlly.activities.TambahKartuKredit;
import javasign.net.FinAlly.models.VendorModels;

public class AdapterPilihvendortambahkartukredit extends RecyclerView.Adapter<AdapterPilihvendortambahkartukredit.MyViewHolder> {

    private Context mContext;
    private List<VendorModels> vendorModels;
    OnClickAdapterVendor onAdapterVendor;

    public interface OnClickAdapterVendor{
        void OnClick(VendorModels vendorModels, View view);
    }

    public AdapterPilihvendortambahkartukredit(List<VendorModels> vendorModels, PilihVendorTambahkartukredit pilihVendor) {
        this.vendorModels = vendorModels;
        notifyDataSetChanged();
    }

    public void setClickOnAdapterVendor(OnClickAdapterVendor onAdapterVendor) {
        this.onAdapterVendor = onAdapterVendor;
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
        holder.tv_name_bank.setText(vendormodels.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAdapterVendor != null){
                    onAdapterVendor.OnClick(vendormodels, v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name_bank;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name_bank = (TextView) itemView.findViewById(R.id.tv_bank);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
