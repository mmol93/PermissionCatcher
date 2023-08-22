package com.easyo.permissioncatcherlibrary

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionCatcher {
    private var permissionArray: Array<String>? = null
    private var allPermissionGranted: (() -> Unit)? = null
    private var permissionDenied: ((List<String>) -> Unit)? = null
    private var permissionExplained: ((List<String>) -> Unit)? = null
    private var somePermissionGranted: ((List<String>) -> Unit)? = null
    private lateinit var activity: AppCompatActivity
    private lateinit var fragment: Fragment
    private val permissionRequestLauncher: ActivityResultLauncher<Array<String>>

    constructor(activity: AppCompatActivity) {
        this.activity = activity
        permissionRequestLauncher =
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
                            permissionDenied?.invoke(it)
                        }
                        map[EXPLAINED]?.let {
                            permissionExplained?.invoke(it)
                        }
                    }

                    else -> {
                        allPermissionGranted?.invoke()
                    }
                }
            }
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
        permissionRequestLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val deniedPermissionList = permissions.filter { !it.value }.map { it.key }
                when {
                    deniedPermissionList.isNotEmpty() -> {
                        val map = deniedPermissionList.groupBy { permission ->
                            if (shouldShowRequestPermissionRationale(
                                    fragment.requireActivity(),
                                    permission
                                )
                            ) DENIED else EXPLAINED
                        }
                        map[DENIED]?.let {
                            permissionDenied?.invoke(it)
                        }
                        map[EXPLAINED]?.let {
                            permissionExplained?.invoke(it)
                        }
                    }

                    else -> {
                        allPermissionGranted?.invoke()
                    }
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

    fun setSomePermissionGranted(somePermissionGranted: (List<String>) -> Unit): PermissionCatcher {
        this.somePermissionGranted = somePermissionGranted
        return this
    }

    fun requestPermission(allPermissionGranted: (() -> Unit)? = null) {
        this.allPermissionGranted = allPermissionGranted

        permissionArray?.filter {
            ContextCompat.checkSelfPermission(
                if (::activity.isInitialized) activity else fragment.requireContext(),
                it
            ) == PackageManager.PERMISSION_DENIED
        }?.toTypedArray().also { permissionRequestLauncher.launch(it) }

        val grantedPermissions = permissionArray?.filter {
            ContextCompat.checkSelfPermission(
                if (::activity.isInitialized) activity else fragment.requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }?.toList()

        if (!grantedPermissions.isNullOrEmpty()) {
            somePermissionGranted?.invoke(grantedPermissions)
        }
    }
}

