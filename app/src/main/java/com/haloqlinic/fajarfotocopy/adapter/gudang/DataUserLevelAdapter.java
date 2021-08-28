package com.haloqlinic.fajarfotocopy.adapter.gudang;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.gudang.usergudang.DetailDataUserGudangActivity;
import com.haloqlinic.fajarfotocopy.model.dataUserByLevel.DataUserByLevelItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

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

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailDataUserGudangActivity.class);
                        intent.putExtra("id_user", dataUser.get(position).getIdUser());
                        intent.putExtra("nama_user", dataUser.get(position).getNamaLengkap());
                        intent.putExtra("username", dataUser.get(position).getUsername());
                        intent.putExtra("password", dataUser.get(position).getPassword());
                        intent.putExtra("level", dataUser.get(position).getLevel());
                        intent.putExtra("id_outlet", dataUser.get(position).getIdOutlet());
                        intent.putExtra("nama_outlet", dataUser.get(position).getNamaOutlet());
                        intent.putExtra("foto", dataUser.get(position).getFoto());
                        context.startActivity(intent);
                    }
                });
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
