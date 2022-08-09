package vanrrtech.app.ajaib_app_sample.base_components.extensions

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import vanrrtech.app.ajaib_app_sample.base_components.entities.Event


fun Fragment.findNullableNavController(): NavController? {
    var navController : NavController? = null
    try {
        navController = (this.view?.let { Navigation.findNavController(it) } ?: null)
    } catch (e : Throwable){navController = null; Log.d("app log", "nav controller heckled up")}
    return navController
}

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
}

fun View.setVisibility(boolean: Boolean){
    this.visibility = if(boolean) View.VISIBLE else View.GONE
}
fun View.setVisibilityInvisible(boolean: Boolean){
    this.visibility = if(boolean) View.VISIBLE else View.INVISIBLE
}

fun ImageView.loadImage(imageLink : String){
        Glide.with(this.context)
            .load(imageLink)
            .error(vanrrtech.app.ajaib_app_sample.R.drawable.no_connection_error)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    android.util.Log.i("Glide", "fail")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(this)
}

fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s).isSuccess        //TODO: Replace offer with trySend when we update kotlin coroutine version
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun <K> Fragment.observeEvent(
    data: LiveData<Event<K>>,
    callback : (K) -> Unit
){
    data.observe(viewLifecycleOwner){ it ->
        it.contentIfNotHandled?.let {
            callback(it)
        }
    }
}
