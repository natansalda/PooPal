package pl.nataliana.mysporthabit.Data;

import android.provider.BaseColumns;

public class MySportContract {

    public static abstract class SportEntry implements BaseColumns {

        public static final String TABLE_NAME = "MySport";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SPORT_TYPE = "type";
        public static final String COLUMN_SPORT_DAY = "day";
        public static final String COLUMN_SPORT_DURATION = "duration";

        /**
         * Possible values for the day when you did sport.
         */
        public static final int DAY_MONDAY = 0;
        public static final int DAY_TUESDAY = 1;
        public static final int DAY_WEDNESDAY = 2;
        public static final int DAY_THURSDAY = 3;
        public static final int DAY_FRIDAY = 4;
        public static final int DAY_SATURDAY = 5;
        public static final int DAY_SUNDAY = 6;

    }
}