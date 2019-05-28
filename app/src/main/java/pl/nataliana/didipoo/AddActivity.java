package pl.nataliana.didipoo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import pl.nataliana.didipoo.Data.MyPooDbHelper;
import pl.nataliana.didipoo.Data.MyPooReportContract.PooEntry;

public class AddActivity extends AppCompatActivity {

    private DatePicker datePicker;

    private TimePicker hourPicker;

    private EditText details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Find all relevant views that we will need to read user input from
        datePicker = findViewById(R.id.edit_date);
        hourPicker = findViewById(R.id.edit_time);
        details = findViewById(R.id.edit_details);
    }

    private void insertHabit() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String dateString = datePicker.toString().trim();
        String timeString = hourPicker.toString().trim();
        String detailsString = details.getText().toString().trim();

        // Create database helper
        MyPooDbHelper mDbHelper = new MyPooDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and sport attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(PooEntry.COLUMN_DATE, dateString);
        values.put(PooEntry.COLUMN_HOUR, timeString);
        values.put(PooEntry.COLUMN_DESCRIPTION, detailsString);

        // Insert a new row for habit in the database, returning the ID of that new row.
        long newRowId = db.insert(PooEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving sport", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Sport saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save habit to the database
                insertHabit();
                //Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}