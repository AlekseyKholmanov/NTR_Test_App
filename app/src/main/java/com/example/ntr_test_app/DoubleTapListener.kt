package com.example.ntr_test_app

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class DoubleTapListener(
    val context: Context,
    val doOnDoubleTap: () -> Unit,
    val doOnSingleTap: () -> Unit
) : View.OnTouchListener {

    private val listener =
        object : GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                Log.d("M_APP", "double tap")
                doOnDoubleTap.invoke()
                return super.onDoubleTap(e)
            }

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                Log.d("M_APP", "single confirmed tap")
                doOnSingleTap.invoke()
                return super.onSingleTapConfirmed(e)
            }
        }) {}

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        listener.onTouchEvent(p1)
        return true
    }
}