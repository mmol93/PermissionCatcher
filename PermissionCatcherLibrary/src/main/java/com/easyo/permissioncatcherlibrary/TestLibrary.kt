package com.easyo.permissioncatcherlibrary

import android.content.Context
import android.widget.Toast

object TestLibrary {
    fun makeToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}