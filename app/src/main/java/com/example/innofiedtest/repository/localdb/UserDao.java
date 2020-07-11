package com.example.innofiedtest.repository.localdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.innofiedtest.model.User;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User LIMIT :limitCount OFFSET:offset")
    public Observable<List<User>> getUsersPaging(int offset,int limitCount);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertUsers(List<User> users);
}
