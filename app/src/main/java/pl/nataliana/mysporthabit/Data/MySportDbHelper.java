package pl.nataliana.mysporthabit.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import pl.nataliana.mysporthabit.Data.MySportContract.SportEntry;

public class MySportDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = MySportDbHelper.class.getSimpleName();

    //** Name of the database file **//
    private static final String DATABASE_NAME = "mysport.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link MySportDbHelper}.
     *
     * @param context of the app
     */

    public MySportDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the HABITS table
        String SQL_CREATE_HABIT_TABLE =  "CREATE TABLE " + SportEntry.TABLE_NAME + " ("
                + SportEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SportEntry.COLUMN_SPORT_TYPE + " TEXT NOT NULL, "
                + SportEntry.COLUMN_SPORT_DAY + " INTEGER NOT NULL, "
                + SportEntry.COLUMN_SPORT_DURATION + " INTEGER NOT NULL DEFAULT 0);"; //duration of sport in minutes

        Log.v(LOG_TAG,SQL_CREATE_SPORT_TABLE);

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_SPORT_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
