package com.example.firebasesqldemo.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.firebasesqldemo.entity.Book;

@Dao
public abstract class BookDAO {
    @Insert
    public abstract void insert(Book book);

    @Update
    public abstract void update(Book book);

    @Delete
    public abstract void delete(Book book);

    @Query("SELECT * FROM books WHERE id = :id")
    public abstract Book findById(String id);

    public boolean checkExits(String id){
        return findById(id) != null;
    }
}
