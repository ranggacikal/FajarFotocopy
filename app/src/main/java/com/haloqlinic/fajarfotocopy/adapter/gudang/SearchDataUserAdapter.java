package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.dataUserByNama.DataUserByNamaItem;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchDataUserAdapter extends RecyclerView.Adapter<SearchDataUserAdapter.SearchDataUserViewHolder> {
    Context context;
    List <DataUserByNamaItem> dataUserByNamaItems;

    public SearchDataUserAdapter(Context context, List<DataUserByNamaItem> dataUserByNamaItems) {
        this.context = context;
        this.dataUserByNamaItems = dataUserByNamaItems;
    }

    @NonNull
    @NotNull
    @Override
    public SearchDataUserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_user, parent, false);
        return new SearchDataUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchDataUserViewHolder holder, int position) {
        String urlImage = dataUserByNamaItems.get(position).getFoto();

        holder.txtNama.setText(dataUserByNamaItems.get(position).getNamaLengkap());
        holder.txtLevel.setText(dataUserByNamaItems.get(position).getLevel());

        Glide.with(context).load(urlImage).into(holder.imgDataUser);



    }

    @Override
    public int getItemCount() {
        return dataUserByNamaItems.size();
    }

    public class SearchDataUserViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDataUser;
        TextView txtNama, txtLevel;


        public SearchDataUserViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgDataUser = itemView.findViewById(R.id.img_data_user_gudang);
            txtNama = itemView.findViewById(R.id.text_item_nama_data_user);
            txtLevel = itemView.findViewById(R.id.text_item_level_data_user);
        }
    }
}
