package vanrrtech.app.ajaib_app_sample.base_components.UtilServices.snack_bar_handler

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackBarHandler(val view : View) {
    fun showSnackBar(msg : String){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }
}