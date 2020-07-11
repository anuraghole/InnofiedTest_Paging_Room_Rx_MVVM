package com.example.innofiedtest.repository.network;

import com.example.innofiedtest.model.ResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Observable<ResponseModel> getResponse(@Query("page") int page, @Query("per_page") int page_size);

}