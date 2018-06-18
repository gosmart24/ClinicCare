package com.cybertech.cliniccare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class TimeLines extends AppCompatActivity {

    RecyclerView timelinerecyclerView;
    TinyDB tinyDB;
    List<TimelineModel> modelList;
    StudentModel studentModel;
    TimelineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_lines);

        timelinerecyclerView = (RecyclerView) findViewById(R.id.timelineRecyclerview);
        timelinerecyclerView.setHasFixedSize(true);
        timelinerecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tinyDB = new TinyDB(this);
        studentModel = tinyDB.getObject("studentmodel", StudentModel.class);
        modelList = studentModel.getTimelineModels();

        adapter = new TimelineAdapter(this, modelList);

        timelinerecyclerView.setAdapter(adapter);
    }
}
