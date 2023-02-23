package com.easyo.permissioncatcherlibrary.extension

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.easyo.permissioncatcherlibrary.DENIED
import com.easyo.permissioncatcherlibrary.EXPLAINED

fun AppCompatActivity.setPermissionCatcherResultLauncher(
    allGranted: (() -> Unit)? = null,
    denied: (() -> Unit)? = null,
    explained: (() -> Unit)? = null
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val deniedPermissionList = permissions.filter { !it.value }.map { it.key }
        when {
            deniedPermissionList.isNotEmpty() -> {
                val map = deniedPermissionList.groupBy { permission ->
                    if (shouldShowRequestPermissionRationale(permission)) DENIED else EXPLAINED
                }
                map[DENIED]?.let {
                    println("my DENIED: ${map[DENIED]}")
                    // 단순히 권한이 거부 되었을 때
                    denied?.invoke()
                }
                map[EXPLAINED]?.let {
                    println("my EXPLAINED: ${map[EXPLAINED]}")
                    // 권한 요청이 완전히 막혔을 때(주로 앱 상세 창 열기)
                    explained?.invoke()
                }
            }
            else -> {
                // 모든 권한이 허가 되었을 때
                allGranted?.invoke()
            }
        }
    }
}

fun AppCompatActivity.setPermissions(vararg permissions: String) {
    setPermissionCatcherResultLauncher(
        allGranted = {}
    )
}
