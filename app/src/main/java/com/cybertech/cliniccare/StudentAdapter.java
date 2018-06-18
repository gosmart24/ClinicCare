package com.cybertech.cliniccare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class StudentAdapter extends FirebaseRecyclerAdapter<StudentModel, StudentAdapter.Holder> {


    private final Context ctx;
    private final TinyDB tinyDB;


    public StudentAdapter(Context context, Query ref) {
        super(StudentModel.class, R.layout.student_row_card, StudentAdapter.Holder.class, ref);
        ctx = context;
        tinyDB = new TinyDB(context);

    }

    @Override
    protected void populateViewHolder(Holder viewHolder, final StudentModel model, int position) {
        viewHolder.setStudentDetails(model);

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent st_detail = new Intent(ctx, StudentDetails.class);
                st_detail.putExtra("model", model.toString());
                ctx.startActivity(st_detail);
            }
        });

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_row_card, parent, false);
        return new StudentAdapter.Holder(itemView);
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView name_TV, matric_TV, level_TV;
        View view;
        LinearLayout card;
        ImageView st_Icon;

        public Holder(View itemView) {
            super(itemView);
            view = itemView;
            name_TV = (TextView) view.findViewById(R.id.name_search);
            matric_TV = (TextView) view.findViewById(R.id.matric_search);
            level_TV = (TextView) view.findViewById(R.id.level_search);
            card = view.findViewById(R.id.student_card);

            st_Icon = view.findViewById(R.id.image_search);
        }

        public void setStudentDetails(StudentModel model) {
            name_TV.setText(model.getName());
            matric_TV.setText(model.getMatric());
            level_TV.setText(model.getLevel());


        }

    }
}
