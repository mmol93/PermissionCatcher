package com.easyo.permissioncatcherlibrary

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

class PermissionCatcher(private val activity: AppCompatActivity) {
    private var permissionArray: Array<String>? = null
    private var allPermissionGranted: (() -> Unit)? = null
    private var permissionDenied: ((List<String>) -> Unit)? = null
    private var permissionExplained: ((List<String>) -> Unit)? = null

    private val permissionRequestLauncher =
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

    fun requestPermission(allPermissionGranted: (() -> Unit)? = null) {
        this.allPermissionGranted = allPermissionGranted
        Log.d("my", "requested permissions: ${permissionArray?.size}")

        permissionArray?.filter {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_DENIED
        }?.toTypedArray().also { permissionRequestLauncher.launch(it) }
    }
}

