package com.example.naran.showinmap.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naran.showinmap.Models.AmbulanceListModel;
import com.example.naran.showinmap.R;

import java.util.List;

/**
 * Created by NaRan on 5/21/17.
 */


public class AmbulanceListAdapter extends RecyclerView.Adapter<AmbulanceListAdapter.ViewHolder> {

    private Context context;
    private List<AmbulanceListModel> ambulanceList;

    public AmbulanceListAdapter(Context context, List<AmbulanceListModel> ambulanceList) {
        this.context = context;
        this.ambulanceList = ambulanceList;
    }


    @Override
    public AmbulanceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulance_detail_adapter,parent,false);

        return new AmbulanceListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AmbulanceListAdapter.ViewHolder holder, int position) {

        final AmbulanceListModel ambuList = ambulanceList.get(position);

        holder.name.setText(ambuList.getName());
        holder.location.setText(ambuList.getLocation());
        holder.contactNumber.setText(ambuList.getContact_number());
        holder.noPlate.setText(ambuList.getNo_plate());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String phoneNumber = "tel:" + ambuList.getContact_number();
                intent.setData(Uri.parse(phoneNumber));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ambulanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView location;
        private TextView contactNumber;
        private TextView noPlate;

        private ImageView call;


        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            location = (TextView) itemView.findViewById(R.id.location);
            contactNumber = (TextView) itemView.findViewById(R.id.contact_number);
            noPlate = (TextView) itemView.findViewById(R.id.no_plate);

            call = (ImageView) itemView.findViewById(R.id.call);
        }
    }
}
