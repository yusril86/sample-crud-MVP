package com.pareandroid.note_mvppattern;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
//    @Headers("Content-Type:application/json")
    @FormUrlEncoded
    @POST("save.php")
    Call<Notes> savenote(
         @Field("tittle") String tittle,
         @Field("note") String note,
         @Field("color") int color


    );

    @GET("notes.php")
        Call<List<Notes>> getNotes();

    @FormUrlEncoded
    @POST("update.php")
    Call<Notes> UpdateNotes(
                    @Field("id") int id,
                    @Field("tittle") String tittle,
                    @Field("note") String note,
                    @Field("color") int color
            );

    @FormUrlEncoded
    @POST("delete.php")
    Call<Notes> deleteNote(
            @Field("id") int id

    );

}
