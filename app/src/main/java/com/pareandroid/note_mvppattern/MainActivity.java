package com.pareandroid.note_mvppattern;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.pareandroid.note_mvppattern.editor.EditorActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView{


    private static final int INTENT_ADD = 100;
    private static final int INTENT_EDIT = 200;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;

    MainPresenter presenter;
    MainAdapter adapter;
    MainAdapter.ItemClickListener itemClickListener;
    List<Notes> note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab =findViewById(R.id.add);
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefresh = findViewById(R.id.swipe_refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(view->
        startActivityForResult(new Intent(this, EditorActivity.class),
                INTENT_ADD)
        );

        presenter = new MainPresenter(this );
        presenter.getData();

        swipeRefresh.setOnRefreshListener(
                () -> presenter.getData()
        );
        itemClickListener = ((view, position) -> {
            int id = note.get(position).getId();
            String title = note.get(position).getTittle();
            String notes = note.get(position).getNote();
            int color = note.get(position).getColor();

            Intent intent = new Intent(this,EditorActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("title",title);
            intent.putExtra("note",notes);
            intent.putExtra("color",color);
            startActivityForResult(intent, INTENT_EDIT);


//        String tittle = note.get(position).getTittle();
//        Toast.makeText(this,tittle,Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_ADD && resultCode == RESULT_OK){
            presenter.getData(); // Untuk Reload Data
        }else if (requestCode == INTENT_EDIT && resultCode == RESULT_OK){
            presenter.getData(); // Untuk Reload Data
        }
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideloading() {
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void onGetResult(List<Notes> notes) {
        adapter = new MainAdapter(this,notes,itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        note = notes;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }
}
