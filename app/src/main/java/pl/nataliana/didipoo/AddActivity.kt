package pl.nataliana.didipoo

import android.content.ContentValues
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import pl.nataliana.didipoo.data.MyPooDbHelper
import pl.nataliana.didipoo.data.MyPooReportContract.PooEntry

class AddActivity : AppCompatActivity() {

    private var datePicker: DatePicker? = null

    private var hourPicker: TimePicker? = null

    private var details: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // Find all relevant views that we will need to read user input from
        datePicker = findViewById(R.id.edit_date)
        hourPicker = findViewById(R.id.edit_time)
        details = findViewById(R.id.edit_details)
    }

    private fun insertHabit() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        val dateString = datePicker!!.toString().trim { it <= ' ' }
        val timeString = hourPicker!!.toString().trim { it <= ' ' }
        val detailsString = details!!.text.toString().trim { it <= ' ' }

        // Create database helper
        val mDbHelper = MyPooDbHelper(this)

        // Gets the database in write mode
        val db = mDbHelper.writableDatabase

        // Create a ContentValues object where column names are the keys,
        // and sport attributes from the editor are the values.
        val values = ContentValues()
        values.put(PooEntry.COLUMN_DATE, dateString)
        values.put(PooEntry.COLUMN_HOUR, timeString)
        values.put(PooEntry.COLUMN_DESCRIPTION, detailsString)

        // Insert a new row for habit in the database, returning the ID of that new row.
        val newRowId = db.insert(PooEntry.TABLE_NAME, null, values)

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1L) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving sport", Toast.LENGTH_SHORT).show()
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Sport saved with row id: $newRowId", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // User clicked on a menu option in the app bar overflow menu
        when (item.itemId) {
            // Respond to a click on the "Save" menu option
            R.id.action_save -> {
                // Save habit to the database
                insertHabit()
                //Exit activity
                finish()
                return true
            }
            // Respond to a click on the "Delete" menu option
            R.id.action_delete ->
                // Do nothing for now
                return true
            // Respond to a click on the "Up" arrow button in the app bar
            android.R.id.home -> {
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}