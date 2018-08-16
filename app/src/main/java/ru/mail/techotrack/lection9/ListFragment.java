package ru.mail.techotrack.lection9;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vlad on 14/04/16.
 */
public class ListFragment extends Fragment {

    final String LOG_TAG = "myLogs";

    final Uri CONTACT_URI = Uri
            .parse("content://ru.mail.techotrack.lection9.AdressBook/contacts");

    final String CONTACT_NAME = "name";
    final String CONTACT_EMAIL = "email";
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*View root = inflater.inflate(R.layout.list_fragment, container, false);*/
        final View root = inflater.inflate(R.layout.recycler_activity, container, false);
        if (null == root) return null;

        final Cursor cursor = getActivity().getContentResolver().query(CONTACT_URI, null, null,
                null, null);
        getActivity().startManagingCursor(cursor);

        /*String from[] = { "name", "email" };
        int to[] = { android.R.id.text1, android.R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_2, cursor, from, to);*/

        String from[] = { "name", "email" };
        int to[] = { R.id.text_name, R.id.text_email};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.list_item, cursor, from, to);


        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final TaskAdapter taskAdapter = new TaskAdapter(getContext(),cursor);
        recyclerView.setAdapter(taskAdapter);

/*
        ListView lvContact = (ListView)root.findViewById(R.id.lvContact);
        lvContact.setAdapter(adapter);
*/

        Button insert = (Button)root.findViewById(R.id.btnInsert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(CONTACT_NAME, "Default Name");
                cv.put(CONTACT_EMAIL, "Default Email");
                Uri newUri = getActivity().getContentResolver().insert(CONTACT_URI, cv);
                Log.d(LOG_TAG, "insert, result Uri : " + newUri.toString());
                taskAdapter.update();
            }
        });

        Button create = (Button)root.findViewById(R.id.btnCreate);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText contact = (EditText)root.findViewById(R.id.text_create);
                String newContact = contact.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put(CONTACT_NAME, newContact);
                cv.put(CONTACT_EMAIL, newContact + " Email");
                Uri newUri = getActivity().getContentResolver().insert(CONTACT_URI, cv);
                Log.d(LOG_TAG, "created contact, result Uri : " + newUri.toString());
                taskAdapter.update();
            }
        });

/*        Button delete = (Button)root.findViewById(R.id.btnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* Uri uri = ContentUris.withAppendedId(CONTACT_URI, 3);
                int cnt = getActivity().getContentResolver().delete(uri, null, null);
                Log.d(LOG_TAG, "delete, count = " + cnt);*//*
            }
        });

        Button error = (Button)root.findViewById(R.id.btnError);
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://ru.mail.techotrack.lection9.AdressBook/phones");
                try {
                    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                } catch (Exception ex) {
                    Log.d(LOG_TAG, "Error: " + ex.getClass() + ", " + ex.getMessage());
                }
            }
        });*/
        return root;

    }
}
