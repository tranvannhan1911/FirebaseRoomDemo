package com.example.firebasesqldemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasesqldemo.Dialog;
import com.example.firebasesqldemo.R;
import com.example.firebasesqldemo.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final Context context;
    private List<Book> books;
    
    public RecyclerAdapter(Context context){
        this.context = context;
        books = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.txtTenSach.setText(book.getName());
        holder.txtTacGia.setText(book.getTacGia());
        holder.txtSoTrang.setText(""+book.getSoTrang());

        holder.itemView.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context, book);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTenSach;
        private TextView txtTacGia;
        private TextView txtSoTrang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenSach = itemView.findViewById(R.id.txt_tensach);
            txtTacGia = itemView.findViewById(R.id.txt_tacgia);
            txtSoTrang = itemView.findViewById(R.id.txt_sotrang);
        }
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }
}
