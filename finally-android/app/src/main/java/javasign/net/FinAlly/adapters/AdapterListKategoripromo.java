package javasign.net.FinAlly.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javasign.net.FinAlly.R;
import javasign.net.FinAlly.activities.TempatPromo;
import javasign.net.FinAlly.models.PromoModels;

public class AdapterListKategoripromo extends RecyclerView.Adapter<AdapterListKategoripromo.MyViewHolder>{

    private Context mContext;
    private List<PromoModels> promoList;


    public AdapterListKategoripromo(List<PromoModels> promoList) {
        this.promoList = promoList;
        notifyDataSetChanged();
    }

    public void setPromoList(List<PromoModels> promoList) {
        this.promoList = promoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kategoripromo, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PromoModels promo = promoList.get(position);
        holder.category.setText(promo.getCategory());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TempatPromo.class);
                intent.putExtra("data", promo.getPromo_details());
                mContext.startActivity(intent);
            }
        });

        Picasso.get().load(promo.getImage()).into(holder.img_category);
    }

    @Override
    public int getItemCount() {
        int count = promoList.size();
        return count;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_category;
        TextView category;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.tv_kategori);
            img_category = itemView.findViewById(R.id.img_logo);
            cardView = itemView.findViewById(R.id.cardview_id);
        }
    }
}
