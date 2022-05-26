package com.example.firebasesqldemo.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String tacGia;
    private int soTrang;

    public Book() {
    }

    public Book(String id, String name, String tacGia, int soTrang) {
        this.id = id;
        this.name = name;
        this.tacGia = tacGia;
        this.soTrang = soTrang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tacGia='" + tacGia + '\'' +
                ", soTrang=" + soTrang +
                '}';
    }
}
