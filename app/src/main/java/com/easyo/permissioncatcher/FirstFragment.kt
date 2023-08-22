package com.easyo.permissioncatcher

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.easyo.permissioncatcher.databinding.FragmentFirstBinding
import com.easyo.permissioncatcherlibrary.*

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private val permissionCatcher = PermissionCatcher(this)
        .setPermissions(
            arrayOf(
                PERMISSION_CAMERA,
                PERMISSION_LOCATION_COARSE,
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkPermission.setOnClickListener {
            permissionCatcher.requestPermission()
        }
    }
}
