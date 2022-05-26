package com.example.firebasesqldemo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.firebasesqldemo.dao.BookDAO;
import com.example.firebasesqldemo.entity.Book;

@Database(entities = {Book.class}, version = 1)
public abstract class DatabaseHandler extends RoomDatabase {
    public abstract BookDAO getBookDAO();
}
