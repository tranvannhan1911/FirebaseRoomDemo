package com.example.firebasesqldemo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.firebasesqldemo.dao.BookDAO;
import com.example.firebasesqldemo.database.DatabaseHandler;
import com.example.firebasesqldemo.entity.Book;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SyncDatabaseService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.firebasesqldemo.action.FOO";
    private static final String ACTION_BAZ = "com.example.firebasesqldemo.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.firebasesqldemo.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.firebasesqldemo.extra.PARAM2";
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public SyncDatabaseService() {
        super("SyncDatabaseService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncDatabaseService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SyncDatabaseService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DatabaseHandler sqlDatabase = Room.databaseBuilder(this, DatabaseHandler.class, "mydb")
                .allowMainThreadQueries()
                .build();
        BookDAO bookDAO = sqlDatabase.getBookDAO();

        database = FirebaseDatabase.getInstance("https://fir-sqldemo-c8697-default-rtdb.asia-southeast1.firebasedatabase.app");
        myRef = database.getReference("books");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                if(bookDAO.checkExits(book.getId()) == false)
                    bookDAO.insert(book);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                bookDAO.update(book);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Book book = snapshot.getValue(Book.class);
                bookDAO.delete(book);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}