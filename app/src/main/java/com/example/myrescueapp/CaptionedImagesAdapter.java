package com.example.myrescueapp;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.List;

class CaptionedImagesAdapter extends
        RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>{

    private List<Garage> mGarages;
    private Context mContext;

    private Listener listener;


    interface Listener {
        void onClick(View v, int position, Garage garage);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public CaptionedImagesAdapter(Context mContext, List<Garage> mGarages){
        this.mContext = mContext;
        this.mGarages = mGarages;
    }

    @Override
    public int getItemCount(){
        return mGarages.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }


    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlist_captioned_image, parent, false);


        return new ViewHolder(cv);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        final Garage garageCurrent = mGarages.get(position);

        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);

        //imageView.setImageDrawable(drawable);
       Uri mImageUri = Uri.parse(garageCurrent.getImageResourceId());
       String url1 = "https://firebasestorage.googleapis.com/v0/b/myrescueapp-789e1.appspot.com/o/Garages%2F1569328649278.jpg?alt=media&token=9ef214f2-9453-4af2-bab1-88cf0e583251";
       String url2 ="https://firebasestorage.googleapis.com/v0/b/myrescueapp-789e1.appspot.com/o/IMG_20180108_150930.jpg?alt=media&token=23c29258-476c-47aa-b62c-5e6cee244d46";
       String url3database = "https://firebasestorage.googleapis.com/v0/b/myrescueapp-789e1.appspot.com/o?name=Garages%2F1569328853812.jpg&uploadType=resumable&upload_id=AEnB2UqNM91ZczjcD0FS_ZXdu_MpnFNylxm9PGkaG1GBnuw2tW059DRRAB2stdy_PNLlbt6CmqI8EFn1afeopKWv-h02Dufc3A&upload_protocol=resumable";
        String url3storage ="https://firebasestorage.googleapis.com/v0/b/myrescueapp-789e1.appspot.com/o/Garages%2F1569328853812.jpg?alt=media&token=862a7a45-5930-45af-947f-a609686b5e58";

        Glide.with(mContext)
                .load(mImageUri)
                .centerCrop()
                .into(imageView);


        imageView.setContentDescription(garageCurrent.getName());

        TextView textViewName = (TextView)cardView.findViewById(R.id.name_text);
        textViewName.setText(garageCurrent.getName());

        TextView textViewLocation = (TextView)cardView.findViewById(R.id.location_text);
        textViewLocation.setText(garageCurrent.getLocation());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position, garageCurrent );
                }
            }
        });
    }
}
