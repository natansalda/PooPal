package pl.nataliana.didipoo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pl.nataliana.didipoo.Data.MyPooDbHelper;
import pl.nataliana.didipoo.Data.MyPooReportContract.PooEntry;

public class HabitActivity extends AppCompatActivity {

    private MyPooDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new MyPooDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private Cursor read() {


        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        String[] projection = {
                PooEntry._ID,
                PooEntry.COLUMN_DATE,
                PooEntry.COLUMN_HOUR,
                PooEntry.COLUMN_DESCRIPTION
        };

        // Perform a query on the habits table
        Cursor cursor = db.query(
                PooEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);


        TextView displayView = (TextView) findViewById(R.id.text_view_sport);

        try {
            displayView.setText("The sport table contains " + cursor.getCount() + " trainings.\n\n");
            displayView.append(PooEntry._ID + " - " +
                    PooEntry.COLUMN_DATE + " - " +
                    PooEntry.COLUMN_HOUR + " - " +
                    PooEntry.COLUMN_DESCRIPTION + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PooEntry._ID);
            int typeColumnIndex = cursor.getColumnIndex(PooEntry.COLUMN_DATE);
            int dayColumnIndex = cursor.getColumnIndex(PooEntry.COLUMN_HOUR);
            int durColumnIndex = cursor.getColumnIndex(PooEntry.COLUMN_DESCRIPTION);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentType = cursor.getString(typeColumnIndex);
                int currentDay = cursor.getInt(dayColumnIndex);
                int currentDuration = cursor.getInt(durColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentType + " - " +
                        currentDay + " - " +
                        currentDuration));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

        return cursor;
    }


    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the exercises database.
     */
    private void displayDatabaseInfo() {
        Cursor cursor = read();
    }

    private void insertHabit() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PooEntry.COLUMN_DATE, "2019-05-28");
        values.put(PooEntry.COLUMN_HOUR, "12:34");
        values.put(PooEntry.COLUMN_DESCRIPTION, "just normal poo");

        long newRowId = db.insert(PooEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New Row ID " + newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
