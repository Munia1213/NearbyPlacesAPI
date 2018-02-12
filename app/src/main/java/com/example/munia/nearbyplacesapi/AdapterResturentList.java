package com.example.munia.nearbyplacesapi;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by munia on 1/27/2018.
 */

public class AdapterResturentList extends RecyclerView.Adapter<AdapterResturentList.MyViewHolder> {

    private List<AllPlaceList> allPlaceLists;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTV, addressTV;
        public ImageView delete,edit;

        public MyViewHolder(View view) {
            super(view);
            nameTV = (TextView) view.findViewById(R.id.nameTextViewId);
            addressTV = (TextView) view.findViewById(R.id.addressTextViewId);


            nameTV.setOnClickListener(this);
            addressTV.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == nameTV.getId()||view.getId() == addressTV.getId()) {
                String latLng =allPlaceLists.get(getAdapterPosition()).placeLatLng;

                //Toast.makeText(view.getContext(), latLng,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(view.getContext(), ShowMap.class);
                intent.putExtra("latlng",latLng);
                view.getContext().startActivity(intent);


            }
        }



    }


    public AdapterResturentList(List<AllPlaceList> allPlaceLists) {
        this.allPlaceLists = allPlaceLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AllPlaceList allPlaceList  = allPlaceLists.get(position);
        holder.nameTV.setText(allPlaceList.getPlaceName());
        holder.addressTV.setText(allPlaceList.getPlaceAddress());
    }

    @Override
    public int getItemCount() {
        return allPlaceLists.size();
    }
}
