package pl.nataliana.didipoo.Data;

import android.provider.BaseColumns;

public class MyPooReportContract {

    public static abstract class PooEntry implements BaseColumns {

        public static final String TABLE_NAME = "MyPooHistory";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_DESCRIPTION = "description";
    }
}