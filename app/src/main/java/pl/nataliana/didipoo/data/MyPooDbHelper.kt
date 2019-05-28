package pl.nataliana.didipoo.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import pl.nataliana.didipoo.data.MyPooReportContract.PooEntry

class MyPooDbHelper
/**
 * Constructs a new instance of [MyPooDbHelper].
 *
 * @param context of the app
 */
(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * This is called when the database is created for the first time.
     */

    override fun onCreate(db: SQLiteDatabase) {
        // Create a String that contains the SQL statement to create the HABITS table
        val SQL_CREATE_POO_TABLE = ("CREATE TABLE " + PooEntry.TABLE_NAME + " ("
                + PooEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PooEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + PooEntry.COLUMN_HOUR + " TEXT NOT NULL, "
                + PooEntry.COLUMN_DESCRIPTION + " TEXT );")

        Log.v(LOG_TAG, SQL_CREATE_POO_TABLE)

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_POO_TABLE)
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO handle change of database
    }

    companion object {

        val LOG_TAG: String = MyPooDbHelper::class.java.simpleName

        //** Name of the database file **//
        private const val DATABASE_NAME = "mypoo.db"

        /**
         * Database version. If you change the database schema, you must increment the database version.
         */
        private const val DATABASE_VERSION = 2
    }
}
