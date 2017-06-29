package pl.nataliana.mysporthabit;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.R;

import pl.nataliana.mysporthabit.Data.MySportContract.SportEntry;
import pl.nataliana.mysporthabit.Data.MySportDbHelper;

public class AddActivity extends AppCompatActivity {

    /**
     * EditText field to enter the habit's type
     */
    private EditText mTypeEditText;

    /**
     * EditText field to enter the duration of the sport
     */
    private EditText mDurationEditText;

    /**
     * EditText field to enter the sport's day
     */
    private Spinner mDaySpinner;

    /**
     * Day of the sport The possible values are:
     * 0 for monday, 1 for tuesday, 2 for wednesday, 3 for thursday, 4 for friday, 5 for saturday, 6 for sunday.
     */
    private int mDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Find all relevant views that we will need to read user input from
        mTypeEditText = (EditText) findViewById(R.id.edit_habit_type);
        mDurationEditText = (EditText) findViewById(R.id.edit_habit_duration);
        mDaySpinner = (Spinner) findViewById(R.id.spinner_day);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the day of the sport.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_day_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDaySpinner.setAdapter(daySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.day_monday))) {
                        mDay = SportEntry.DAY_MONDAY; // Monday
                    } else if (selection.equals(getString(R.string.day_tuesday))) {
                        mDay = SportEntry.DAY_TUESDAY; // Tuesday
                    } else if (selection.equals(getString(R.string.day_wednesday))){
                        mDay = SportEntry.DAY_WEDNESDAY; // Wednesday
                    } else if (selection.equals(getString(R.string.day_thursday))){
                        mDay = SportEntry.DAY_THURSDAY; // Thursday
                    } else if (selection.equals(getString(R.string.day_friday))){
                        mDay = SportEntry.DAY_FRIDAY; // Friday
                    } else if (selection.equals(getString(R.string.day_saturday))){
                        mDay = SportEntry.DAY_SATURDAY; // Saturday
                    } else if (selection.equals(getString(R.string.day_sunday))){
                        mDay = SportEntry.DAY_SUNDAY; // Sunday
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDay = 0; // Unknown
            }
        });
    }

    /**
     * Get user input from editor and save new habit into database.
     */
    private void insertHabit() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String typeString = mTypeEditText.getText().toString().trim();
        String durationString = mDurationEditText.getText().toString().trim();
        int duration = Integer.parseInt(durationString);

        // Create database helper
        MySportDbHelper mDbHelper = new MySportDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and sport attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(SportEntry.COLUMN_SPORT_TYPE, typeString);
        values.put(SportEntry.COLUMN_SPORT_DAY, mDay);
        values.put(SportEntry.COLUMN_SPORT_DURATION, durationString);

        // Insert a new row for habit in the database, returning the ID of that new row.
        long newRowId = db.insert(SportEntry.TABLE_NAME, null, values);

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