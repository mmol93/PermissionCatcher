package com.easyo.permissioncatcher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.easyo.permissioncatcher.databinding.ActivityMainBinding
import com.easyo.permissioncatcherlibrary.*

const val LOG_TAG = "TEST"

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
            Log.d(LOG_TAG, "all permission is granted")
        }
        .setPermissionDenied {
            Log.d(LOG_TAG, "denied permissions: $it")
        }
        .setPermissionExplained {
            Log.d(LOG_TAG, "explained permissions: $it")
        }
        .setSomePermissionGranted {
            Log.d(LOG_TAG, "granted permission: $it")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        Log.d(LOG_TAG, "onCreate")
        binding.checkPermission.setOnClickListener {
            permissionCatcher.requestPermission {
                Log.d(LOG_TAG, "all permission is granted2")
            }
        }

        binding.openFragment.setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }
    }
}
