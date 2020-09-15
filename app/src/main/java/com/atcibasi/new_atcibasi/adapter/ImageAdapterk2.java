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
import com.atcibasi.new_atcibasi.Uploadk2;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterk2 extends RecyclerView.Adapter<ImageAdapterk2.ImageViewHolder> {

    private Context mContext;
    private List<Uploadk2> mk2Uploads;
    public ImageAdapterk2(Context context, List<Uploadk2> uploads){
        mContext=context;
        mk2Uploads=uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item_k2,parent,false);
        return  new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uploadk2 uploadk2Current=mk2Uploads.get(position);
        holder.textViewName.setText(uploadk2Current.getmName());
        Picasso.with(mContext).load(uploadk2Current.getmImageUrl())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerInside()
                .into(holder.imageView);

    }

    public int getItemCount() {
        return mk2Uploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.k2_text_view_name);
            imageView=itemView.findViewById(R.id.k2_image_view_upload);
        }
    }
}
