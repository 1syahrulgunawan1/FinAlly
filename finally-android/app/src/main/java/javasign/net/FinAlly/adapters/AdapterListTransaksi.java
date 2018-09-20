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
import javasign.net.FinAlly.activities.TransaksiDetails;
import javasign.net.FinAlly.models.CashFlowsModels;


public class AdapterListTransaksi extends RecyclerView.Adapter<AdapterListTransaksi.MyViewHolder> {

    private Context mContext;
    private List<CashFlowsModels> cashFlowsModels;

    public AdapterListTransaksi(List<CashFlowsModels> cashFlowsModels, KartuKredit kartuKredit) {
        this.cashFlowsModels = cashFlowsModels;
        notifyDataSetChanged();
    }

    public void setCashFlowsModels(List<CashFlowsModels> cashFlowsModels) {
        this.cashFlowsModels = cashFlowsModels;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksi, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final CashFlowsModels cashflow = cashFlowsModels.get(position);
        holder.tv_description.setText(cashflow.getDescription());
        holder.tv_amount.setText(toRupiah(cashflow.getAmount()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TransaksiDetails.class);
                intent.putExtra("data1", cashflow.getAmount());
                intent.putExtra("data2", cashflow.getCategory());
                intent.putExtra("data3", cashflow.getDescription());
                intent.putExtra("data4", cashflow.getDate());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cashFlowsModels == null ? 0  : cashFlowsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_description, tv_amount;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            cardView = (CardView) itemView.findViewById(R.id.cardview_kartu_kredit);

        }
    }
}
