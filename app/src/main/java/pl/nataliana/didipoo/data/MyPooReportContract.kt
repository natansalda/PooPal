package pl.nataliana.didipoo.data

import android.provider.BaseColumns

class MyPooReportContract {

    abstract class PooEntry : BaseColumns {
        companion object {

            const val TABLE_NAME = "MyPooHistory"

            const val _ID = BaseColumns._ID
            const val COLUMN_DATE = "date"
            const val COLUMN_HOUR = "hour"
            const val COLUMN_DESCRIPTION = "description"
        }
    }
}