package com.atcibasi.new_atcibasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atcibasi.new_atcibasi.R;
import com.atcibasi.new_atcibasi.Uploadk1;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterk1 extends RecyclerView.Adapter<ImageAdapterk1.ImageViewHolder> {

    private Context mContext;
    private List<Uploadk1> mk1Uploads;
    public ImageAdapterk1(Context context, List<Uploadk1> uploads){
        mContext=context;
        mk1Uploads=uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item_k1,parent,false);
        return  new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uploadk1 uploadk1Current=mk1Uploads.get(position);
        holder.textViewName.setText(uploadk1Current.getmName());
        Picasso.with(mContext).load(uploadk1Current.getmImageUrl())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerInside()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mk1Uploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.k1_text_view_name);
            imageView=itemView.findViewById(R.id.k1_image_view_upload);
        }
    }
}
