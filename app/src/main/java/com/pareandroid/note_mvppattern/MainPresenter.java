package com.pareandroid.note_mvppattern;

import android.support.annotation.NonNull;
import android.telecom.Call;


import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private MainView view;


    public MainPresenter(MainView view){
        this.view = view;
    }

    void getData(){
        view.showLoading();
        //Request ke Server

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        retrofit2.Call<List<Notes>> call = apiInterface.getNotes();

        call.enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<List<Notes>> call, @NonNull Response<List<Notes>> response) {
                view.hideloading();
                if (response.isSuccessful() && response.body() !=null) {
                    view.onGetResult(response.body());
                }


            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<List<Notes>> call, @NonNull Throwable t) {
                view.hideloading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }
}
