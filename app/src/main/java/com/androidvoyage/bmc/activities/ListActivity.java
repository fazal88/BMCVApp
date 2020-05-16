package com.androidvoyage.bmc.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidvoyage.bmc.adapters.MyRecyclerAdapter;
import com.androidvoyage.bmc.R;

public class ListActivity extends AppCompatActivity {

    RecyclerView rcvList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context = this;
        rcvList = findViewById(R.id.rcv_list);
        rcvList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvList.setAdapter(new MyRecyclerAdapter(context));

    }
}
