package com.smartdroidesign.contentproviderimplementation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Declared a field for the linear layout and the DB helper
    LinearLayout linearLayout;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout
        setContentView(R.layout.activity_main);

        // Populate DB helper
        dbHelper = new DatabaseHelper(this);

        // set view variables
        Button addButton = findViewById(R.id.addButton);
        final EditText editText = findViewById(R.id.nameEditText);
        linearLayout = findViewById(R.id.linearLayout);

        /*
         * Set onClick listener for the button
         * Grab the text from the editText (if there is any)
         * Add that text to our 'vics' table
         * Update the list
         * set the editText back to empty
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                if (name.length() > 0) {
                    dbHelper.addContact(name);
                    updateList();
                    editText.setText("");
                }
            }
        });
        // Calling updateList() here to handle the list initialization
        updateList();
    }

    public void updateList() {
        // Removing all the views attached to our list
        linearLayout.removeAllViews();
        // Get a list array of all the rows we need to show
        Row[] rows = dbHelper.getContacts();
        // for each row, get a new TextView, add that TextView to the LinearLayout
        for (Row row : rows) {
            TextView newEntry = getNewTextView(row.id, row.name);
            linearLayout.addView(newEntry);
        }
    }

    private TextView getNewTextView(String id, String name) {
        TextView textView = new TextView(this);
        textView.setText(id + "  " + name);
        textView.setTextSize(24f);
        return textView;
    }
}
