package com.easyo.permissioncatcher

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.easyo.permissioncatcherlibrary.PermissionCatcher
import com.easyo.permissioncatcherlibrary.TestLibrary.makeToast
import com.easyo.permissioncatcherlibrary.extension.setPermissionCatcherResultLauncher
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private val permissionRequest = setPermissionCatcherResultLauncher(
        allGranted = {
            // 모든 권한이 확인되어 있을 때
            makeToast(this, "모든 권한 얻어져 있음")
        },
        denied = {
            // 1개라도 허락되지 않은 권한이 있을 때
            makeToast(this, "허락되지 않은 권한 있음")
        },
        explained = {
            // 완전히 거부된 권한이 있을 경우
            makeToast(this, "완전 거부된 권한 있음")
        }
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionCatcher = PermissionCatcher(WeakReference(this))
        .setPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS))
        .setAllPermissionGranted {

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkEssentialPermission()
    }

    private fun checkEssentialPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionRequest.launch(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS))
        }
    }
}
