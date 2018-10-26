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

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.TempatPromoDetails;
import javasign.net.FinAlly.models.PromoDetailModels;

import static android.support.constraint.Constraints.TAG;

public class AdapterTempatPromo extends RecyclerView.Adapter<AdapterTempatPromo.MyViewHolder> {

    private Context mContext;
    private ArrayList<PromoDetailModels> promoDetailModels = new ArrayList<>();

    public AdapterTempatPromo(ArrayList<PromoDetailModels> promoDetailModels) {
        this.promoDetailModels = promoDetailModels;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tempatpromo, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setPromoDetailModels(ArrayList<PromoDetailModels> promoDetailModels) {
        this.promoDetailModels = promoDetailModels;
        notifyDataSetChanged();
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PromoDetailModels models = promoDetailModels.get(position);
        holder.tv_name.setText(models.name);
        holder.tv_deskripsi.setText(models.description);
        String date2 = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", models.due_date);
        holder.tv_due_date.setText(date2);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TempatPromoDetails.class);
                intent.putExtra("data", models);
                mContext.startActivity(intent);
            }
        });

        String image = models.logo;
        if (image.isEmpty()) {
            holder.img_logo.setImageResource(R.drawable.logo_promo);
        } else{
            Picasso.get().load(image).into(holder.img_logo);
        }
    }

    @Override
    public int getItemCount() {
        return promoDetailModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_logo;
        TextView tv_name, tv_deskripsi, tv_due_date;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.nickname1);
            tv_deskripsi = itemView.findViewById(R.id.name);
            tv_due_date = itemView.findViewById(R.id.due_date);
            img_logo = itemView.findViewById(R.id.img_logo);
            cardView = itemView.findViewById(R.id.cardview_tempatpromo);
        }
    }
}
