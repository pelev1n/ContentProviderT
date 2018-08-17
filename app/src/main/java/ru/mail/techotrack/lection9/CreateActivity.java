package ru.mail.techotrack.lection9;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends Activity {
    private EditText nameBox;
    private EditText emailBox;
    private Button createButton;

    final Uri CONTACT_URI = Uri
            .parse("content://ru.mail.techotrack.lection9.AdressBook/contacts");

    final String CONTACT_NAME = "name";
    final String CONTACT_EMAIL = "email";
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        nameBox = (EditText) findViewById(R.id.name);
        emailBox = (EditText) findViewById(R.id.email);
        createButton = (Button) findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put(CONTACT_NAME, nameBox.getText().toString());
                cv.put(CONTACT_EMAIL, emailBox.getText().toString());
                Uri newUri = getBaseContext().getContentResolver().insert(CONTACT_URI, cv);
                Log.d(LOG_TAG, "created contact, result Uri : " + newUri.toString());
                goBack();
            }
        });

    }

    public void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
