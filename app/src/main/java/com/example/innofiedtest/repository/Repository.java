package com.example.innofiedtest.repository;

import androidx.room.Room;

import com.example.innofiedtest.MyApplication;
import com.example.innofiedtest.model.ResponseModel;
import com.example.innofiedtest.model.User;
import com.example.innofiedtest.repository.localdb.InnofiedDB;
import com.example.innofiedtest.repository.network.ApiService;
import com.example.innofiedtest.repository.network.ApiClient;

import java.util.List;

import io.reactivex.Observable;

public class Repository {
    private ApiService apiService;
    private InnofiedDB innofiedDB;

    public Repository() {
        this.apiService = ApiClient.getClient().create(ApiService.class);
        this.innofiedDB= Room.databaseBuilder(MyApplication.getAppContext(),InnofiedDB.class,InnofiedDB.DB_NAME).build();
    }

    public Observable<ResponseModel> getResponse(int page,int per_page) {
        return  apiService.getResponse(page, per_page);
    }

    public Observable<List<User>> getResponseFromDB(int page, int per_page) {
        return  innofiedDB.getContentDao().getUsersPaging(page*per_page, per_page);
    }
    public void insertUsersInDB(List<User> users) {
        innofiedDB.getContentDao().insertUsers(users);
    }
}
