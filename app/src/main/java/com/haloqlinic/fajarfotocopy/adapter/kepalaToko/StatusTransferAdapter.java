package com.haloqlinic.fajarfotocopy.adapter.kepalaToko;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.fajarfotocopy.R;
import com.haloqlinic.fajarfotocopy.databinding.ItemListTransferKetoBinding;
import com.haloqlinic.fajarfotocopy.kepalatoko.listtransferketo.DetailListTransferKetoActivity;
import com.haloqlinic.fajarfotocopy.model.listStatusTransfer.ListStatusTransferItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class StatusTransferAdapter extends RecyclerView.Adapter<StatusTransferAdapter.StatusTransferViewHolder> {

    Context context;
    List<ListStatusTransferItem> dataStatus;

    public StatusTransferAdapter(Context context, List<ListStatusTransferItem> dataStatus) {
        this.context = context;
        this.dataStatus = dataStatus;
    }

    @NonNull
    @Override
    public StatusTransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatusTransferViewHolder(ItemListTransferKetoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull StatusTransferViewHolder holder, int position) {
        holder.binding.textTanggalListTransferKeto.setText(dataStatus.get(position).getTanggalTransfer());
        holder.binding.textTokoListTransferKeto.setText(dataStatus.get(position).getOutletPenerima());

        PushDownAnim.setPushDownAnimTo(holder.binding.btnDetailListTransferKeto)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailListTransferKetoActivity.class);
                        intent.putExtra("id_status_transfer", dataStatus.get(position).getIdStatusTransfer());
                        intent.putExtra("tanggal_transfer", dataStatus.get(position).getTanggalTransfer());
                        intent.putExtra("id_outlet", dataStatus.get(position).getIdOutletPenerima());
                        context.startActivity(intent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataStatus.size();
    }

    public class StatusTransferViewHolder extends RecyclerView.ViewHolder {
        private ItemListTransferKetoBinding binding;
        public StatusTransferViewHolder(@NonNull ItemListTransferKetoBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
