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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.KartuKredit;
import javasign.net.FinAlly.models.AccountModelsProducts;


public class AdapterListKartukredit extends RecyclerView.Adapter<AdapterListKartukredit.MyViewHolder> {

    private Context mContext;
    private List<AccountModelsProducts> accountModelsProducts;

    public AdapterListKartukredit(List<AccountModelsProducts> accountModelsProducts) {
        this.accountModelsProducts = accountModelsProducts;
        notifyDataSetChanged();
    }

    public void setAccountModels(List<AccountModelsProducts> accountModelsProducts) {
        this.accountModelsProducts = accountModelsProducts;
        notifyDataSetChanged();
    }

    private String toRupiah(double nominal){
        String hasil = "";

        DecimalFormat toRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatAngka = toRupiah.getDecimalFormatSymbols();
        formatAngka.setCurrencySymbol("Rp. ");
        formatAngka.setMonetaryDecimalSeparator(',');
        formatAngka.setGroupingSeparator('.');
        toRupiah.setDecimalFormatSymbols(formatAngka);

        hasil = toRupiah.format(Double.valueOf(nominal));

        return hasil;
    }

    @NonNull
    @Override
    public AdapterListKartukredit.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kartu_kredit, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListKartukredit.MyViewHolder holder, int position) {
        final AccountModelsProducts product = accountModelsProducts.get(position);
        holder.tv_bank.setText(product.vendorModels.name);
        holder.tv_balance.setText(toRupiah(product.balance));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, KartuKredit.class);
                intent.putExtra("data", product);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountModelsProducts == null ? 0 : accountModelsProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_bank, tv_balance;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_bank = (TextView) itemView.findViewById(R.id.tv_bank);
            tv_balance = (TextView) itemView.findViewById(R.id.tv_balance);
            cardView = (CardView) itemView.findViewById(R.id.cardview_kartu_kredit);
        }
    }
}
