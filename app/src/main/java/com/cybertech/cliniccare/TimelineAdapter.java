package com.cybertech.cliniccare;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.Holder> {
    List<TimelineModel> storageList;
    Context ctx;

    public TimelineAdapter(Context context, List<TimelineModel> storageList) {
        this.storageList = storageList;
        ctx = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_row, parent, false);
        return new TimelineAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.setTimeLineViews(storageList.get(position));

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetails(storageList.get(position));
                //Toast.makeText(ctx, storageList.get(position).getComplain(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return storageList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        View view;
        TextView complain_TV, visite_dateTV;
        CardView cardLayout;

        public Holder(View itemView) {
            super(itemView);
            view = itemView;

            complain_TV = (TextView) view.findViewById(R.id.complain_row);
            visite_dateTV = (TextView) view.findViewById(R.id.visite_row_date);
            cardLayout = (CardView) view.findViewById(R.id.timelineRowCard);
        }

        public void setTimeLineViews(TimelineModel model) {
            complain_TV.setText(model.getComplain());
            visite_dateTV.setText(model.getVisitdate());

        }
    }

    public void showDetails(TimelineModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Medical Timeline: " + model.getVisitdate());
        builder.setMessage("Complains: " + model.getComplain() + "\n Prescriptions: " + model.getPriscriptions() + "\n Personal: " + model.getPersonal());
        builder.setCancelable(true);
        builder.setNegativeButton("Close", null);
        builder.show();
    }
}
