package com.example.ntr_test_app.extension

import android.content.res.Resources
import android.view.View

val Int.dp
    get() = this * Resources.getSystem().displayMetrics.density


fun View.toggleVisibility(visible: Boolean) {
    if (this.visibility == View.VISIBLE && visible) return
    if (this.visibility == View.INVISIBLE && !visible) return
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}fun View.toggleGone(visible: Boolean) {
    if (this.visibility == View.VISIBLE && visible) return
    if (this.visibility == View.GONE && !visible) return
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}