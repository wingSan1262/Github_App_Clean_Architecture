package vanrrtech.app.ajaib_app_sample.base_components.UtilServices

import android.annotation.SuppressLint
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import org.jetbrains.annotations.TestOnly
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class  UnixDateConverter {


    // for test purpose -_-, DO NOT USE IN PRODUCTION
    private var testDate : Date? = null
    @RestrictTo(RestrictTo.Scope.TESTS)
    fun setTestDate(date : String,) {
        try{
            val newDate = date.replace("T"," ")
            val calcTime: Date =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(newDate)
            testDate=calcTime
        }catch (e : Throwable){}
    }



    fun getDaysBetweenWithCurrent(
        date : String,
    ): String? {
        return try {
            val newDate = date.replace("T"," ")
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val calcTime: Date = sdf.parse(newDate)
            val currentTime : Date = testDate ?: Calendar.getInstance().time
            val difference : Long = (currentTime.getTime() - calcTime.getTime()) / 1000;
            val days = difference / (24 * 3600) // Calculating Hours
            days.toString()
        } catch (e : Throwable) {
            null
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getHourBetweenWithCurrent(
        date : String
    ): String? {
        return try {
            val newDate = date.replace("T"," ")
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val calcTime: Date = sdf.parse(newDate)
            val currentTime : Date = testDate ?: Calendar.getInstance().time
            val difference : Long = (currentTime.getTime() - calcTime.getTime()) / 1000;
            val hours = (difference % (24 * 3600)) / 3600 // Calculating Hours
            hours.toString()
        } catch (e : Throwable){
            null
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getMinutesBetweenWithCurrent(
        date : String
    ): String? {
        return try {
            val newDate = date.replace("T"," ")
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val calcTime: Date = sdf.parse(newDate)
            val currentTime : Date = testDate ?: Calendar.getInstance().time
            val difference : Long = (currentTime.getTime() - calcTime.getTime()) / 1000;
            val minute =
                difference % 3600 / 60 // Calculating minutes if there is any minutes difference
            minute.toString()
        } catch (e : Throwable){
            null
        }
    }
}