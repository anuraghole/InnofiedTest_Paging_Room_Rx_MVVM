package com.example.innofiedtest.repository.localdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.innofiedtest.model.User;

@Database(version = 1 , exportSchema = false , entities = {User.class})
public abstract  class InnofiedDB extends RoomDatabase {

    public static final String DB_NAME = "innofied_db";

    public abstract UserDao getContentDao();

}
