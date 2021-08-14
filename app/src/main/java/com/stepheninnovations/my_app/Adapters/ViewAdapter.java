package com.stepheninnovations.my_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stepheninnovations.my_app.R;
import com.stepheninnovations.my_app.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<User> hitsList;
    private onItemClickListner mlistner;

    public ViewAdapter(Context mContext, ArrayList<User> hitsList) {
        this.mContext = mContext;
        this.hitsList = hitsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_user,parent,false);

        return new ViewHolder(v,mlistner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter.ViewHolder holder, int position) {

        User data = hitsList.get(position);

        holder.username.setText(""+data.getUsername());
        holder.email.setText(""+data.getEmail());

    }

    @Override
    public int getItemCount() {
        return hitsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView username,email;
         CardView cardview_user;
         ImageButton option;

        public ViewHolder(@NonNull View itemView,onItemClickListner clickListener) {
            super(itemView);

            username = itemView.findViewById(R.id.u_name);
            email = itemView.findViewById(R.id.e_name);
            cardview_user = itemView.findViewById(R.id.cardview_user);
            option = itemView.findViewById(R.id.option);

            cardview_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mlistner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mlistner.onCardClick(position);

                        }
                    }
                }
            });

        }
    }

    public interface onItemClickListner{
        void onCardClick(int position);
    }
    public void setOnItemClickListner(onItemClickListner listner){
        mlistner = listner;
    }
}
