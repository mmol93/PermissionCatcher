package com.easyo.permissioncatcher

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.easyo.permissioncatcher.databinding.ActivityMainBinding
import com.easyo.permissioncatcherlibrary.*
import com.easyo.permissioncatcherlibrary.TestLibrary.makeToast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val permissionCatcher = PermissionCatcher(this)
        .setPermissions(
            arrayOf(
                PERMISSION_CAMERA,
                PERMISSION_LOCATION_COARSE,
                PERMISSION_STORAGE_READ,
                PERMISSION_STORAGE_WRITE,
                PERMISSION_PHONE_CALL,
                PERMISSION_PHONE_READ_LOG,
                PERMISSION_SMS_READ,
                PERMISSION_CALENDAR_READ
            )
        )
        .setAllPermissionGranted {
            makeToast(this, "모든 권한 통과1")
            Log.d("my", "모든 권한 통과1")
        }
        .setPermissionDenied {
            makeToast(this, "거부된 권한: $it")
            Log.d("my", "거부된 권한: $it")

        }
        .setPermissionExplained {
            makeToast(this, "완전 거부된 권한: $it")
            Log.d("my", "완전 거부된 권한: $it")
        }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        Log.d("my", "onCreate")
        binding.checkPermission.setOnClickListener {
            permissionCatcher.requestPermission {
                makeToast(this, "모든 권한 통과2")
                Log.d("my", "모든 권한 통과2")
            }
        }


    }
}
