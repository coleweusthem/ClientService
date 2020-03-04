package com.techme.mainscreenactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.MainViewHolder> {
    private List<MailProps> mailingList = new ArrayList<>();
    private onclickListener listener;

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgDelete, imgArchive;
        private TextView txtName, txtSubject;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtSubject = itemView.findViewById(R.id.txt_subject);
            imgDelete = itemView.findViewById(R.id.img_delete);
            imgArchive = itemView.findViewById(R.id.img_archive);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick();
                    }
                }
            });

            imgArchive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onArchiveClick();
                    }
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onDeleteClick();
                    }
                }
            });


        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        MailProps mail = getMail(holder.getAdapterPosition());
        holder.txtName.setText(mail.getSenderName());
        holder.txtSubject.setText(mail.getSubject());
    }

    @Override
    public int getItemCount() {
        return mailingList.size();
    }

    public MailProps getMail(int position){
        return mailingList.get(position);
    }

    public void setMail(MailProps mail){
        int position = 0;
        mailingList.add(position,mail);
        notifyItemInserted(position);
    }

    public void delete(int position){
        mailingList.remove(position);
        notifyItemRemoved(position);
    }

    public interface onclickListener{
        void onItemClick();
        void onDeleteClick();
        void onArchiveClick();
    }

    public void setOnclickListener(onclickListener listener){
        this.listener = listener;
    }


}
