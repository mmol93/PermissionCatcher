package com.easyo.permissioncatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easyo.permissioncatcherlibrary.TestLibrary

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestLibrary.makeToast(this, "라이브러리 테스트입니다.")
    }
}