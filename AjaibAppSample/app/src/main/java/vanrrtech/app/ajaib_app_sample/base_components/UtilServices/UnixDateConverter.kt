package vanrrtech.app.ajaib_app_sample.base_components.UtilServices

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class  UnixDateConverter {

    @SuppressLint("SimpleDateFormat")
    fun convertUnixTimeToDateString(unixDate: Double): String {
        val date = Date((unixDate.toLong()) * 1000);
        val inputFormatter = SimpleDateFormat ("d MMM ''yy")
        return date.toString()
    }
}