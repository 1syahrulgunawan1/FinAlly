package javasign.net.FinAlly.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javasign.net.FinAlly.Card;
import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.KartuKreditM;
import javasign.net.FinAlly.activities.KartuKreditO;
import javasign.net.FinAlly.models.AccountModelsProducts;

import static android.support.constraint.Constraints.TAG;


public class AdapterListKartukredit extends RecyclerView.Adapter<AdapterListKartukredit.MyViewHolder> {

    private Context mContext;
    private List<AccountModelsProducts> accountModelsProducts;
    private ArrayList<Card> cards = new ArrayList<>();

    public AdapterListKartukredit(List<AccountModelsProducts> accountModelsProducts) {
        this.accountModelsProducts = accountModelsProducts;
        notifyDataSetChanged();
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public void setAccountModels(List<AccountModelsProducts> accountModelsProducts) {
        this.accountModelsProducts = accountModelsProducts;
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
    public AdapterListKartukredit.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kartu_kredit, parent, false);
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
    public void onBindViewHolder(@NonNull AdapterListKartukredit.MyViewHolder holder, final int position) {
        if (position < accountModelsProducts.size()) {
            final AccountModelsProducts product = accountModelsProducts.get(position);
            switch (product.vendorModels.name.toUpperCase()) {
                case "MANDIRI":
                    holder.iv_iconbank.setImageResource(R.drawable.logo_mandiri);
                    break;
                case "BCA":
                    holder.iv_iconbank.setImageResource(R.drawable.logo_bca);
                    break;
                case "BRI":
                    holder.iv_iconbank.setImageResource(R.drawable.logo_bri);
                    break;
                case "CIMB NIAGA":
                    holder.iv_iconbank.setImageResource(R.drawable.logo_cimb);
                    break;
                default:
                    holder.iv_iconbank.setImageResource(R.drawable.ic_bank);
                    break;
            }
            holder.tv_bank.setText(product.nickname + " " + product.vendorModels.name);
            holder.tv_balance.setText(toRupiah(product.balance));
            String date1 = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", product.properties.due_date);
            holder.tv_invoice.setText("Invoice :" + " " + date1);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, KartuKreditO.class);
                    intent.putExtra("data1", product);
                    mContext.startActivity(intent);
                }
            });
        } else {
            int pos = position - accountModelsProducts.size();
            final Card card = cards.get(pos);

            if (card != null) {
                switch (card.getVendor().name.toUpperCase()) {
                    case "MANDIRI":
                        holder.iv_iconbank.setImageResource(R.drawable.logo_mandiri);
                        break;
                    case "BCA":
                        holder.iv_iconbank.setImageResource(R.drawable.logo_bca);
                        break;
                    case "BRI":
                        holder.iv_iconbank.setImageResource(R.drawable.logo_bri);
                        break;
                    case "CIMB NIAGA":
                        holder.iv_iconbank.setImageResource(R.drawable.logo_cimb);
                        break;
                }
                holder.tv_bank.setText(card.getProducts().nickname + " " + card.getVendor().name);
                holder.tv_balance.setText(toRupiah(card.getProducts().balance));
                String date2 = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", card.getProducts().getProperties().due_date);
                holder.tv_invoice.setText("Invoice :" + " " + date2);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, KartuKreditM.class);
                        intent.putExtra("key", card.key);
                        intent.putExtra("fb1", card.getVendor().name);
                        intent.putExtra("fb2", card.getProducts().getNickname());
                        intent.putExtra("fb3", card.getProducts().getProperties().cardholder);
                        intent.putExtra("fb4", card.getProducts().getProperties().available_balance);
                        intent.putExtra("fb5", card.getProducts().getProperties().minimum_payment);
                        intent.putExtra("fb6", card.getVendor().getId());
                        intent.putExtra("fb7", card.getProducts().getProperties().getDue_date());
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (accountModelsProducts == null || cards == null) {
            return 0;
        } else {
            return accountModelsProducts.size() + cards.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_bank, tv_balance, tv_invoice;
        ImageView iv_iconbank;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_iconbank = (ImageView) itemView.findViewById(R.id.iv_bank);
            tv_invoice = (TextView) itemView.findViewById(R.id.tv_invoice);
            tv_bank = (TextView) itemView.findViewById(R.id.tv_bank);
            tv_balance = (TextView) itemView.findViewById(R.id.tv_balance);
            cardView = (CardView) itemView.findViewById(R.id.cardview_kartu_kredit);
        }
    }
}
