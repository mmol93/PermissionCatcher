package com.easyo.permissioncatcherlibrary

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

class PermissionCatcher(activityRef: WeakReference<AppCompatActivity>) {
    private val activity = activityRef.get()

    companion object {
        const val PERMISSION_CAMERA = Manifest.permission.CAMERA
        const val PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    private var permissions: Array<String>? = null
    private var allPermissionGranted: ((String) -> Unit)? = null

    private val permissionRequestLauncher =
        activity?.let {
            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val deniedPermissionList = permissions.filter { !it.value }.map { it.key }
                when {
                    deniedPermissionList.isNotEmpty() -> {
                        val map = deniedPermissionList.groupBy { permission ->
                            if (shouldShowRequestPermissionRationale(
                                    activity,
                                    permission
                                )
                            ) DENIED else EXPLAINED
                        }
                        map[DENIED]?.let {
                            println("my DENIED: ${map[DENIED]}")
                            // 단순히 권한이 거부 되었을 때
                        }
                        map[EXPLAINED]?.let {
                            println("my EXPLAINED: ${map[EXPLAINED]}")
                            // 권한 요청이 완전히 막혔을 때(주로 앱 상세 창 열기)
                        }
                    }
                    else -> {
                        // 모든 권한이 허가 되었을 때
                    }
                }
            }
        }


    private var onPermissionResult: (Boolean) -> Unit = {}

    fun setPermissionArray(permissionArray: Array<String>): PermissionCatcher {
        permissions = permissionArray
        return this
    }

    fun setAllPermissionGranted(allPermissionGranted: (String) -> Unit): PermissionCatcher {
        this.allPermissionGranted = allPermissionGranted
        return this
    }

    fun requestPermission(permission: String, onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult

        activity?.let {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted
                onPermissionResult(true)
            } else {
                // Permission is not granted, request it
                permissionRequestLauncher?.launch(permissions)
            }
        } ?: run { Log.e(this@PermissionCatcher.javaClass.simpleName, "activity WeakReference is null") }
    }
}

