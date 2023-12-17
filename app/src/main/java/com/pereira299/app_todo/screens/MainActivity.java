package com.pereira299.app_todo.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pereira299.app_todo.R;
import com.pereira299.app_todo.adapters.CompromissoAdapter;
import com.pereira299.app_todo.dao.CompromissoDao;
import com.pereira299.app_todo.database.AppDatabase;
import com.pereira299.app_todo.database.DatabaseProvider;
import com.pereira299.app_todo.entities.Compromisso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button addBtn;
    private CompromissoAdapter compromissoAdapter;
    private AppDatabase db;
    private List<Compromisso> compromissoList;
    private CompromissoDao dao;
    private CompromissoObserver listObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewCompromissos);
        addBtn = findViewById(R.id.buttonAddCompromisso);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        compromissoList = new ArrayList<>();
        listObserver = new CompromissoObserver();
        db = DatabaseProvider.getDatabase(this);
        dao = db.compromissoDao();
        dao.getAll().observe(this, listObserver);

        compromissoAdapter = new CompromissoAdapter(this, compromissoList, dao);
        recyclerView.setAdapter(compromissoAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddCompromissoActivity.class);
                startActivity(in);
            }
        });
    }

    class CompromissoObserver implements Observer<List<Compromisso>> {

        @Override
        public void onChanged(List<Compromisso> compromissoEntities) {
            compromissoList.clear();
            compromissoList.addAll(compromissoEntities);
            compromissoAdapter.notifyDataSetChanged();
        }
    }
}