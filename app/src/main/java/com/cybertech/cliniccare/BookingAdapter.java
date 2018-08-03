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

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.Holder> {

    List<BookingModel> storageList;
    Context ctx;

    public BookingAdapter(Context context, List<BookingModel> storageList) {
        this.storageList = storageList;
        ctx = context;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_row, parent, false);
        return new BookingAdapter.Holder(itemView);
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
    public int getItemCount() {
        return 0;
    }


    public class Holder extends RecyclerView.ViewHolder {
        View view;
        TextView name_TV, complain_TV, _dateTV;
        CardView cardLayout;

        public Holder(View itemView) {
            super(itemView);

            complain_TV = (TextView) view.findViewById(R.id.booking_complain_row);
            name_TV = (TextView) view.findViewById(R.id.book_name_row);
            _dateTV = (TextView) view.findViewById(R.id.booking_row_date);
            cardLayout = (CardView) view.findViewById(R.id.bookingCard);
        }

        public void setTimeLineViews(BookingModel model) {
            complain_TV.setText(model.getMessage());
            name_TV.setText(model.getStudent());
            _dateTV.setText(model.getTime());
        }
    }

    public void showDetails(BookingModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Appointment by: " + model.getStudent());
        builder.setMessage("Message: " + model.getMessage());
        builder.setCancelable(true);
        builder.setNegativeButton("Close", null);
        builder.show();
    }
}
