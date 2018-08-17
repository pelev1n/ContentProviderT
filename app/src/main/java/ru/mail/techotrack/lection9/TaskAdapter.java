package ru.mail.techotrack.lection9;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private Context context;
    private Cursor cursor;

    final Uri CONTACT_URI = Uri
            .parse("content://ru.mail.techotrack.lection9.AdressBook/contacts");

    final String CONTACT_NAME = "name";
    final String CONTACT_EMAIL = "email";
    final String LOG_TAG = "myLogs";
    final String CONTACT_ID = "_id";

    public TaskAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (cursor != null) {
            cursor.moveToPosition(position);
        }
        final String name = cursor.getString(cursor.getColumnIndex(CONTACT_NAME));
        final String email = cursor.getString(cursor.getColumnIndex(CONTACT_EMAIL));
        final long id = cursor.getLong(cursor.getColumnIndex(CONTACT_ID));

        holder.text_name.setText(name);
        holder.text_email.setText(email);

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ContactActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
                update();

            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    private void remove(long id) {
        Uri uri = ContentUris.withAppendedId(CONTACT_URI, id);
        int cnt = context.getContentResolver().delete(uri, null, null);
        Log.d(LOG_TAG, "delete, count = " + cnt);
        update();
    }

    public void update() {
        this.notifyDataSetChanged();
    }

    public void getNewCursor() {
        DBHelper db = new DBHelper(context);
        this.cursor = db.getAllData();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button updateButton, deleteButton;
        private TextView text_name;
        private TextView text_email;

        ViewHolder(View view){
            super(view);
            updateButton = (Button) view.findViewById(R.id.updateButton);
            deleteButton = (Button) view.findViewById(R.id.deleteButton);
            text_name = (TextView) view.findViewById(R.id.text_name);
            text_email = (TextView) view.findViewById(R.id.text_email);
        }
    }
}

