package pl.nataliana.didipoo

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import pl.nataliana.didipoo.data.MyPooDbHelper
import pl.nataliana.didipoo.data.MyPooReportContract.PooEntry

class HabitActivity : AppCompatActivity() {

    private var mDbHelper: MyPooDbHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit)

        // Setup FAB to open EditorActivity
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this@HabitActivity, AddActivity::class.java)
            startActivity(intent)
        }

        mDbHelper = MyPooDbHelper(this)

    }

    override fun onStart() {
        super.onStart()
        displayDatabaseInfo()
    }

    private fun read(): Cursor? {

        // Create and/or open a database to read from it
        val db = mDbHelper?.readableDatabase

        val projection = arrayOf(PooEntry._ID, PooEntry.COLUMN_DATE, PooEntry.COLUMN_HOUR, PooEntry.COLUMN_DESCRIPTION)

        // Perform a query on the habits table
        val cursor = db?.query(
                PooEntry.TABLE_NAME,
                projection, null, null, null, null, null)


        val displayView = findViewById<View>(R.id.text_view_sport) as TextView

        cursor.use { cursor ->
            if (cursor != null) {
                displayView.text = "The sport table contains " + cursor.count + " trainings.\n\n"
            }
            displayView.append(PooEntry._ID + " - " +
                    PooEntry.COLUMN_DATE + " - " +
                    PooEntry.COLUMN_HOUR + " - " +
                    PooEntry.COLUMN_DESCRIPTION + "\n")

            // Figure out the index of each column
            val idColumnIndex = cursor?.getColumnIndex(PooEntry._ID)
            val typeColumnIndex = cursor?.getColumnIndex(PooEntry.COLUMN_DATE)
            val dayColumnIndex = cursor?.getColumnIndex(PooEntry.COLUMN_HOUR)
            val durColumnIndex = cursor?.getColumnIndex(PooEntry.COLUMN_DESCRIPTION)

            // Iterate through all the returned rows in the cursor
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    val currentID = idColumnIndex?.let { cursor.getInt(it) }
                    val currentType = typeColumnIndex?.let { cursor.getString(it) }
                    val currentDay = dayColumnIndex?.let { cursor.getInt(it) }
                    val currentDuration = durColumnIndex?.let { cursor.getInt(it) }
                    // Display the values from each column of the current row in the cursor in the TextView
                    displayView.append("\n" + currentID + " - " +
                            currentType + " - " +
                            currentDay + " - " +
                            currentDuration)
                }
            }
        }

        return cursor
    }


    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the exercises database.
     */
    private fun displayDatabaseInfo() {
        val cursor = read()
    }

    private fun insertHabit() {
        // Gets the database in write mode
        val db = mDbHelper!!.writableDatabase

        val values = ContentValues()
        values.put(PooEntry.COLUMN_DATE, "2019-05-28")
        values.put(PooEntry.COLUMN_HOUR, "12:34")
        values.put(PooEntry.COLUMN_DESCRIPTION, "just normal poo")

        val newRowId = db.insert(PooEntry.TABLE_NAME, null, values)

        Log.v("CatalogActivity", "New Row ID $newRowId")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        menuInflater.inflate(R.menu.menu_habit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // User clicked on a menu option in the app bar overflow menu
        when (item.itemId) {
            // Respond to a click on the "Insert dummy data" menu option
            R.id.action_insert_dummy_data -> {
                insertHabit()
                displayDatabaseInfo()

                return true
            }
            // Respond to a click on the "Delete all entries" menu option
            R.id.action_delete_all_entries ->
                // Do nothing for now
                return true
        }
        return super.onOptionsItemSelected(item)
    }
}
