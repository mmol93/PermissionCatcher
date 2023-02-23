package com.easyo.permissioncatcherlibrary.extension

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.easyo.permissioncatcherlibrary.DENIED
import com.easyo.permissioncatcherlibrary.EXPLAINED


fun Fragment.setPermissionCatcherResultLauncher(
    allGranted: () -> Unit,
    denied: () -> Unit,
    explained: () -> Unit
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val deniedPermissionList = permissions.filter { !it.value }.map { it.key }
        when {
            deniedPermissionList.isNotEmpty() -> {
                val map = deniedPermissionList.groupBy { permission ->
                    if (shouldShowRequestPermissionRationale(permission)) DENIED else EXPLAINED
                }
                map[DENIED]?.let {
                    // 단순히 권한이 거부 되었을 때
                    denied()
                }
                map[EXPLAINED]?.let {
                    // 권한 요청이 완전히 막혔을 때(주로 앱 상세 창 열기)
                    explained()
                }
            }
            else -> {
                // 모든 권한이 허가 되었을 때
                allGranted()
            }
        }
    }
}