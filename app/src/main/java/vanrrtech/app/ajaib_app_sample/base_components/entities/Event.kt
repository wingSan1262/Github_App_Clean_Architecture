package vanrrtech.app.ajaib_app_sample.base_components.entities

import androidx.annotation.RestrictTo

class Event<T>(content: T?) {
    private val mContent: T
    private var hasBeenHandled = false

    val contentIfNotHandled: T?
        get() = if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }

    @RestrictTo(RestrictTo.Scope.TESTS)
    fun getContentForTest(): T {return mContent}

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }

    init {
        requireNotNull(content) { "null values in Event are not allowed." }
        mContent = content
    }
}