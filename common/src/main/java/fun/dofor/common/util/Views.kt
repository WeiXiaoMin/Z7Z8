package `fun`.dofor.common.util

import android.app.Activity
import android.content.ContextWrapper
import android.view.View

fun View.getActivity(): Activity? {
    if (context is Activity) {
        return context as Activity
    }

    if (context is ContextWrapper) {
        val base = (context as ContextWrapper).baseContext
        if (base is Activity) {
            return base as Activity
        }
    }

    return null
}