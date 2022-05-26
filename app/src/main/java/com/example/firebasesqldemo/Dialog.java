package com.example.firebasesqldemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firebasesqldemo.entity.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dialog extends android.app.Dialog {
    private Book book;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText edtTenSach;
    private EditText edtTacGia;
    private EditText edtSoTrang;

    public Dialog(@NonNull Context context) {
        super(context);
        this.context = context;
        show();
    }

    public Dialog(@NonNull Context context, Book book) {
        super(context);
        this.context = context;
        this.book = book;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);


        database = FirebaseDatabase.getInstance("https://fir-sqldemo-c8697-default-rtdb.asia-southeast1.firebasedatabase.app");
        myRef = database.getReference("books");

        System.out.println(myRef);

        edtTenSach = findViewById(R.id.edt_tensach);
        edtTacGia = findViewById(R.id.edt_tacgia);
        edtSoTrang = findViewById(R.id.edt_sotrang);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnEdit = findViewById(R.id.btn_edit);
        Button btnDelete = findViewById(R.id.btn_delete);

        if(book != null){
            edtTenSach.setText(book.getName());
            edtTacGia.setText(book.getTacGia());
            edtSoTrang.setText(""+book.getSoTrang());
        }else{
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        }


        btnAdd.setOnClickListener(v -> {
            if(!check())return;
            String tenSach = edtTenSach.getText().toString();
            String tacGia = edtTacGia.getText().toString();
            int soTrang = Integer.parseInt(edtSoTrang.getText().toString());

            Book book = new Book(myRef.push().getKey(), tenSach, tacGia, soTrang);
            myRef.child(book.getId()).setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnEdit.setOnClickListener(v -> {
            if(book == null){
                Toast.makeText(context, "Chưa chọn sách để sửa", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!check())return;
            String tenSach = edtTenSach.getText().toString();
            String tacGia = edtTacGia.getText().toString();
            int soTrang = Integer.parseInt(edtSoTrang.getText().toString());

            book.setName(tenSach);
            book.setTacGia(tacGia);
            book.setSoTrang(soTrang);

            myRef.child(book.getId()).setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnDelete.setOnClickListener(v -> {
            if(book == null){
                Toast.makeText(context, "Chưa chọn sách để xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            myRef.child(book.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean check(){

        String tenSach = edtTenSach.getText().toString();
        String tacGia = edtTacGia.getText().toString();

        if(tenSach.equals("") || tacGia.equals("")){
            Toast.makeText(context, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            int soTrang = Integer.parseInt(edtSoTrang.getText().toString());
        }catch (Exception e){
            Toast.makeText(context, "Số trang không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
