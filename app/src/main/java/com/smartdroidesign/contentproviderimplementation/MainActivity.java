package com.smartdroidesign.contentproviderimplementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Declared a field for the linear layout and the DB helper
    LinearLayout linearLayout;
    // OLD CODE PREVIOUS CUSTOM CONTENT PROVIDER
    // DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout
        setContentView(R.layout.activity_main);

        // Populate DB helper
        // OLD CODE PREVIOUS CUSTOM CONTENT PROVIDER
        //dbHelper = new DatabaseHelper(this);

        // set view variables
        Button addButton = findViewById(R.id.addButton);
        final EditText editText = findViewById(R.id.nameEditText);
        linearLayout = findViewById(R.id.linearLayout);

        /*
         * a - Set onClick listener for the button
         * b - Grab the text from the editText (if there is any)
         * c - Add that text to our 'vics' table
         * d - Update the list
         * f - Set the editText back to empty
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                if (name.length() > 0) {
                    addContact(name);
                    updateList();
                    editText.setText("");
                }
            }
        });
        // Calling updateList() here to handle the list initialization
        updateList();
    }

    /** NEW addContact() method to use the custom content provider
     * a - Create a ContentValues object: this is needed because you're calling insert() -
     * - from MyContentProvider, and the method parameter requires a ContentValues
     * b - call put() on values, and pass the column name from DataBaseHelper, and the name from addContact
     * c - call the insert method on ContentProvider. To do that, you need to use a ContentResolver -
     *  - call getContentResolver, chain to insert(), pass the CONTENT_URI from MyContentProvider
     *  - and pass values as second parameter
     *  d - To make sure insert() returns a URI, save getContentResolver to a Uri variable
     *  e - Check if it's null, if not, Toast back the URI of the new inserted row
     * @param name
     */
    private void addContact(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
        if (uri != null) {
            Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();

        }

    }

    /** We are going to use ContentProvider's query method to get a cursor -
     *  - then, we loop through that cursor to update the list.
     *
     * a - Remove all the views attached to our list
     * b - Create a cursor variable
     * c - Set the variable equal to getContentResolver.query()
     * d - query() takes a URI of the data source, and a bunch of parameters for each part of the SQL query.
     * e - Since we want to SELECT *, all the parameters for the query can be null
     * f - Make sure the Cursor exists, and is not empty
     *      - cursor.moveToFirst() > returns false if there is not a first row to move to
     *  g - Keep looping until there is another row to move to > do-while is perfect for the case
     *  h - Inside the loop, you need to get the ID and name for our contacts -
     *      - use those to create a textView for the LinearLayout
     *  i - Add the view to the LinearLayout
     *  l - Close the cursor
     */
    public void updateList() {
        linearLayout.removeAllViews();
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                TextView textView = getNewTextView(id, name);
                LinearLayout linearLayout = findViewById(R.id.linearLayout);
                linearLayout.addView(textView);
            } while (cursor.moveToNext());
            cursor.close();
        }
        // OLD CODE PREVIOUS CUSTOM CONTENT PROVIDER
//        // Get a list array of all the rows we need to show
//        Row[] rows = dbHelper.getContacts();
//        // for each row, get a new TextView, add that TextView to the LinearLayout
//        for (Row row : rows) {
//            TextView newEntry = getNewTextView(row.id, row.name);
//            linearLayout.addView(newEntry);
//        }
    }


    private TextView getNewTextView(String id, String name) {
        TextView textView = new TextView(this);
        textView.setText(String.format("%s  %s", id, name));
        textView.setTextSize(24f);
        return textView;
    }
}
