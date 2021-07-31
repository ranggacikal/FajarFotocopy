package com.haloqlinic.fajarfotocopy.adapter.gudang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.model.dataUser.DataUserItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataUserAdapter extends RecyclerView.Adapter<DataUserAdapter.DataUserViewHolder> {

    Context context;
    List<DataUserItem> dataUserItem;

    public DataUserAdapter(Context context, List<DataUserItem> dataUserItems) {
        this.context = context;
        this.dataUserItem = dataUserItems;
    }



    @NonNull
    @NotNull
    @Override
    public DataUserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_user, parent, false);
        return new DataUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataUserViewHolder holder, int position) {
        holder.txtNamaUser.setText(dataUserItem.get(position).getUsername());
        holder.txtLevelUser.setText(dataUserItem.get(position).getLevel());
    }

    @Override
    public int getItemCount() {
        return dataUserItem.size();    }

    public class DataUserViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaUser, txtLevelUser;

        public DataUserViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNamaUser = itemView.findViewById(R.id.text_item_nama_data_user);
            txtLevelUser = itemView.findViewById(R.id.text_item_level_data_user);

        }
    }
}
