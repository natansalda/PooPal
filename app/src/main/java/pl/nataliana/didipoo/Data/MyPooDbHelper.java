package pl.nataliana.didipoo.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import pl.nataliana.didipoo.Data.MyPooReportContract.PooEntry;

public class MyPooDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = MyPooDbHelper.class.getSimpleName();

    //** Name of the database file **//
    private static final String DATABASE_NAME = "mypoo.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 2;

    /**
     * Constructs a new instance of {@link MyPooDbHelper}.
     *
     * @param context of the app
     */

    public MyPooDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the HABITS table
        String SQL_CREATE_POO_TABLE =  "CREATE TABLE " + PooEntry.TABLE_NAME + " ("
                + PooEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PooEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + PooEntry.COLUMN_HOUR + " TEXT NOT NULL, "
                + PooEntry.COLUMN_DESCRIPTION + " TEXT );";

        Log.v(LOG_TAG,SQL_CREATE_POO_TABLE);

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_POO_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO handle change of database
    }
}
