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

    private var permissionArray: Array<String>? = null
    private var allPermissionGranted: (() -> Unit)? = null
    private var permissionDenied: ((List<String>) -> Unit)? = null
    private var permissionExplained: ((List<String>) -> Unit)? = null

    private val permissionRequestLauncher =
        activity?.let {
            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val deniedPermissionList = permissions.filter { !it.value }.map { it.key }
                when {
                    deniedPermissionList.isNotEmpty() -> {
                        val map = deniedPermissionList.groupBy { permission ->
                            if (shouldShowRequestPermissionRationale(activity, permission)) DENIED else EXPLAINED
                        }
                        map[DENIED]?.let {
                            println("my DENIED: ${map[DENIED]}")
                            // 단순히 권한이 거부 되었을 때
                            permissionDenied?.invoke(it)
                        }
                        map[EXPLAINED]?.let {
                            println("my EXPLAINED: ${map[EXPLAINED]}")
                            // 권한 요청이 완전히 막혔을 때(주로 앱 상세 창 열기)
                            permissionExplained?.invoke(it)
                        }
                    }
                    else -> {
                        // 모든 권한이 허가 되었을 때
                        allPermissionGranted?.invoke()
                    }
                }
            }
        }


    private var onPermissionResult: (Boolean) -> Unit = {}

    fun setPermissions(permissionArray: Array<String>): PermissionCatcher {
        this.permissionArray = permissionArray
        return this
    }

    fun setAllPermissionGranted(allPermissionGranted: () -> Unit): PermissionCatcher {
        this.allPermissionGranted = allPermissionGranted
        return this
    }

    fun setPermissionDenied(permissionDenied: (List<String>) -> Unit): PermissionCatcher {
        this.permissionDenied = permissionDenied
        return this
    }

    fun setPermissionExplained(permissionExplained: (List<String>) -> Unit): PermissionCatcher {
        this.permissionExplained = permissionExplained
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
                permissionRequestLauncher?.launch(permissionArray)
            }
        } ?: run { Log.e(this@PermissionCatcher.javaClass.simpleName, "activity WeakReference value is null") }
    }
}

