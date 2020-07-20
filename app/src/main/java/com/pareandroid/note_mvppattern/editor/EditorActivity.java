package com.pareandroid.note_mvppattern.editor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.pareandroid.note_mvppattern.ApiClient;
import com.pareandroid.note_mvppattern.ApiInterface;
import com.pareandroid.note_mvppattern.MainPresenter;
import com.pareandroid.note_mvppattern.MainView;
import com.pareandroid.note_mvppattern.Notes;
import com.pareandroid.note_mvppattern.R;
import com.thebluealliance.spectrum.SpectrumPalette;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity implements EditorView {

    EditText et_tittle,et_note;
    SpectrumPalette palette;
    ProgressDialog progressDialog;
    ApiInterface apiInterface;

    EditorPresenter presenter;

    int color,id;
    String title,note;

    Menu actionMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        et_tittle = findViewById(R.id.tittle);
        et_note = findViewById(R.id.note);
        palette = findViewById(R.id.palette);

        palette.setOnColorSelectedListener(
                clr ->color = clr
        );

//        //Default Color
//        palette.setSelectedColor(getResources().getColor(R.color.white));
//        color = getResources().getColor(R.color.white);

        progressDialog  = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");

        presenter = new EditorPresenter(this);

        Intent intent = getIntent();
        id  = intent.getIntExtra("id",0);
        title = intent.getStringExtra("title");
        note = intent.getStringExtra("note");
        color = intent.getIntExtra("color",0);

        setDataFromIntentExtra();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor,menu);
        actionMenu = menu;

        if (id != 0 ){
            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
        }else{
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);
        }

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String tittle = et_tittle.getText().toString().trim();
        String note = et_note.getText().toString().trim();
        int color = this.color;

        switch (item.getItemId()){
            case R.id.save:
                //Save
                if (tittle.isEmpty()){
                    et_tittle.setError("Mohon Isi kan Judul");
                }else if (note.isEmpty()) {
                    et_note.setError("Masukkan Catatatan..");
                }else{
                    presenter.SaveNote(tittle,note,color);
                }
//                Toast.makeText(this,"tes" + tittle +note,Toast.LENGTH_SHORT).show();
                return true;

            case R.id.edit:
                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);
                return  true;

            case R.id.update:
                //Update
                if (tittle.isEmpty()){
                    et_tittle.setError("Mohon Isi kan Judul");
                }else if (note.isEmpty()) {
                    et_note.setError("Masukkan Catatatan..");
                }else{
                    presenter.UpdateNotes(id,title,note,color);
                }

                return  true;
            case R.id.delete:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Apakah Kamu Yakin Ingin Mengapus?");
                alertDialog.setNegativeButton("yes", (dialog, which) -> {
                    dialog.dismiss();
                    presenter.deleteNote(id);
                });
                alertDialog.setPositiveButton("Batal",
                        (dialog, which)-> dialog.dismiss());
                alertDialog.show();

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }



    private void setDataFromIntentExtra() {

        if ( id != 0 ) {
            et_tittle.setText(title);
            et_note.setText(note);
            palette.setSelectedColor(color);

            getSupportActionBar().setTitle("Update Note");
            readMode();
        }else{
            //Default Color
            palette.setSelectedColor(getResources().getColor(R.color.white));
            color = getResources().getColor(R.color.white);
            editMode();
        }

    }

    private void editMode() {
        et_tittle.setFocusableInTouchMode(true);
        et_note.setFocusableInTouchMode(true);
        palette.setFocusableInTouchMode(true);
    }

    private void readMode() {
        et_tittle.setFocusableInTouchMode(false);
        et_note.setFocusableInTouchMode(false);
        et_tittle.setFocusable(false);
        et_note.setFocusable(false);
        palette.setEnabled(false);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();

    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();

    }


}