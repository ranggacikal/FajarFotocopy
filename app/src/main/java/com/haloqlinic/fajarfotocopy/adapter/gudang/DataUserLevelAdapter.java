package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.dataUserByLevel.DataUserByLevelItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataUserLevelAdapter extends RecyclerView.Adapter<DataUserLevelAdapter.DataUserLevelViewHolder> {

    Context context;
    List<DataUserByLevelItem> dataUser;

    public DataUserLevelAdapter(Context context, List<DataUserByLevelItem> dataUser) {
        this.context = context;
        this.dataUser = dataUser;
    }

    @NonNull
    @NotNull
    @Override
    public DataUserLevelViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_user, parent, false);
        return new DataUserLevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataUserLevelViewHolder holder, int position) {
        String urlImage = dataUser.get(position).getFoto();

        holder.txtNama.setText(dataUser.get(position).getNamaLengkap());
        holder.txtLevel.setText(dataUser.get(position).getLevel());

        Glide.with(context).load(urlImage).into(holder.imgDataUser);
    }

    @Override
    public int getItemCount() {
        return dataUser.size();
    }

    public class DataUserLevelViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDataUser;
        TextView txtNama, txtLevel;

        public DataUserLevelViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgDataUser = itemView.findViewById(R.id.img_data_user_gudang);
            txtNama = itemView.findViewById(R.id.text_item_nama_data_user);
            txtLevel = itemView.findViewById(R.id.text_item_level_data_user);
        }
    }
}
