package ru.mail.techotrack.lection9;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends Activity {

    private EditText nameBox;
    private EditText emailBox;
    private Button updateButton;
    private long id;


    final Uri CONTACT_URI = Uri
            .parse("content://ru.mail.techotrack.lection9.AdressBook/contacts");

    final String CONTACT_NAME = "name";
    final String CONTACT_EMAIL = "email";
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id");
        }

        nameBox = (EditText) findViewById(R.id.name);
        emailBox = (EditText) findViewById(R.id.email);
        updateButton = (Button) findViewById(R.id.saveButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put(CONTACT_NAME, nameBox.getText().toString());
                cv.put(CONTACT_EMAIL, emailBox.getText().toString());
                Uri uri = ContentUris.withAppendedId(CONTACT_URI, id);
                int cnt = getBaseContext().getContentResolver().update(uri, cv,null,null);
                Log.d(LOG_TAG, "update, count = " + 1);
                goBack();
            }
        });

    }

    public void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
