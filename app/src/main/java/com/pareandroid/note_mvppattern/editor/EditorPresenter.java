package com.pareandroid.note_mvppattern.editor;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.pareandroid.note_mvppattern.ApiClient;
import com.pareandroid.note_mvppattern.ApiInterface;
import com.pareandroid.note_mvppattern.MainView;
import com.pareandroid.note_mvppattern.Notes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {

    private EditorView view;

    public EditorPresenter(EditorView view){
        this.view = view;
    }

    void SaveNote(final String tittle, final String note, final int color) {
        view.showProgress();
        //Request ke Server

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Notes> call = apiInterface.savenote(tittle,note,color);

        call.enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(@NonNull Call<Notes> call, @NonNull Response<Notes> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null){

                    Boolean success = response.body().getSuccess();
                    if (success){
                        view.onRequestSuccess(response.body().getMessage());


//                        finish();
                    }
                    else{
                      view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Notes> call, @NonNull Throwable t) {
                view.hideProgress();
               view.onRequestSuccess(t.getLocalizedMessage());
            }
        });
    }

    void UpdateNotes(int id , String title,String note,int color){
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Notes> call = apiInterface.UpdateNotes(id,title,note,color);

        call.enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(@NonNull Call<Notes> call, @NonNull Response<Notes> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        view.onRequestSuccess(response.body().getMessage());
                    }else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Notes> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());

            }
        });
    }

    void deleteNote(int id ){
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Notes> call = apiInterface.deleteNote(id);
        call.enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(@NonNull Call<Notes> call, @NonNull Response<Notes> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        view.onRequestSuccess(response.body().getMessage());
                    }else {
                        view.onRequestError(response.body().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Notes> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());

            }
        });
    }
}
