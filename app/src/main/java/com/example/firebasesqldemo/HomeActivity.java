package com.example.firebasesqldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.firebasesqldemo.adapter.RecyclerAdapter;
import com.example.firebasesqldemo.entity.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recycler = findViewById(R.id.recycler);
        RecyclerAdapter recyclerAdapter
                = new RecyclerAdapter(this);
        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        ImageButton imgBtnAdd = findViewById(R.id.btn_add);
        imgBtnAdd.setOnClickListener(v -> {
            Dialog dialog = new Dialog(HomeActivity.this);
        });

        database = FirebaseDatabase.getInstance("https://fir-sqldemo-c8697-default-rtdb.asia-southeast1.firebasedatabase.app");
        myRef = database.getReference("books");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Book> lst = new ArrayList<>();

                for(DataSnapshot child : snapshot.getChildren()){
                    Log.d("key", child.getKey());

                    lst.add(child.getValue(Book.class));
                }

                recyclerAdapter.setBooks(lst);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = new Intent(this, SyncDatabaseService.class);
        startService(intent);
    }
}