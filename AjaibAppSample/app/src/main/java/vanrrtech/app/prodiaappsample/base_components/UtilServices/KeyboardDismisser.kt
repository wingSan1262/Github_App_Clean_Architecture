package vanrrtech.app.prodiaappsample.base_components.UtilServices

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity


class KeyboardDismisser(val application: Application, val activity: AppCompatActivity) {

    fun dismissSoftKey(){
        val imm : InputMethodManager = application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity.currentFocus.let {
            imm.hideSoftInputFromWindow(it?.windowToken, 0)
        }
    }
}