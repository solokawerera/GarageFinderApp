package com.example.myrescueapp;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


class ServicesAdapter extends
        RecyclerView.Adapter<ServicesAdapter.ViewHolder>{
    private GarageService[] garageServices1;
    private List<GarageService> garageServices;
    private Context mContext;
    private Listener listener;

    public ServicesAdapter(Context mContext, List<GarageService> garageServices) {
        this.garageServices = garageServices;
        this.mContext = mContext;
    }

    interface Listener {
        void onClick(View view,int position, GarageService garageService);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public ServicesAdapter(List<GarageService> garageServices){
        this.garageServices=garageServices;
    }

    @Override
    public int getItemCount(){
        return garageServices.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_service_image, parent, false);
        return new ViewHolder(cv);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        final GarageService garageServiceCurrent = garageServices.get(position);
        CardView cardView = holder.cardView;
        TextView textView =cardView.findViewById(R.id.info_text);
        textView.setText(garageServices.get(position).getName());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(v, position,garageServiceCurrent);
                }

            }
        });

    }
}
