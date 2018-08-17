package ru.mail.techotrack.lection9;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

        String from[] = { "name", "email" };
        int to[] = { R.id.text_name, R.id.text_email};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.list_item, cursor, from, to);


        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final TaskAdapter taskAdapter = new TaskAdapter(getContext(),cursor);
        recyclerView.setAdapter(taskAdapter);

        Button create = (Button)root.findViewById(R.id.btnCreate);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CreateActivity.class);
                context.startActivity(intent);
                taskAdapter.update();
            }
        });
        return root;

    }
}
