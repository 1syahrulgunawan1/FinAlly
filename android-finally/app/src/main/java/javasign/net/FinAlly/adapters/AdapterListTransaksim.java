package javasign.net.FinAlly.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javasign.net.FinAlly.CashFlow;
import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.EditTransaksi;

import static android.support.constraint.Constraints.TAG;

public class AdapterListTransaksim extends RecyclerView.Adapter<AdapterListTransaksim.MyViewHolder> {

    private Context mContext;
    private ArrayList<CashFlow> cashFlows;

    public AdapterListTransaksim(ArrayList<CashFlow> cashFlows) {
        this.cashFlows = cashFlows;
        notifyDataSetChanged();
    }

    public void setTransaksi(ArrayList<CashFlow> cashFlows) {
        this.cashFlows = cashFlows;
        notifyDataSetChanged();
    }

    private String toRupiah(double nominal) {
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
    public AdapterListTransaksim.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksi, parent, false);
        return new MyViewHolder(itemView);
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListTransaksim.MyViewHolder holder, int position) {
        final CashFlow transaksi = cashFlows.get(position);

        String paymentflag = transaksi.getPayment_flag().toLowerCase();
        switch (paymentflag) {
            case "paid":
                holder.tv_payment_flag.setTextColor(Color.parseColor("#2E7D32"));
                break;
            case "unpaid":
                holder.tv_payment_flag.setTextColor(Color.parseColor("#E65100"));
                break;
        }

        if (transaksi != null) {

            holder.tv_description.setText(transaksi.category);
            String day = transaksi.getDate().day;
            String tanggal = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", transaksi.getDate().getOriginal());
            holder.tv_payment_flag.setText(transaksi.getPayment_flag().toUpperCase());
            holder.tv_tanggal.setText(day + ", " + tanggal);
            holder.tv_amount.setText(toRupiah(transaksi.amount));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditTransaksi.class);
                intent.putExtra("key", transaksi.account_key);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cashFlows == null ? 0 : cashFlows.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_description, tv_amount, tv_tanggal, tv_payment_flag;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_tanggal = (TextView) itemView.findViewById(R.id.tv_tanggal);
            tv_payment_flag = (TextView) itemView.findViewById(R.id.tv_payment_flag);
            cardView = (CardView) itemView.findViewById(R.id.cardview_kartu_kredit);

        }
    }
}

