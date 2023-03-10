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
                PERMISSION_STORAGE_READ,
                PERMISSION_STORAGE_WRITE,
                PERMISSION_PHONE_CALL,
                PERMISSION_PHONE_READ_LOG,
                PERMISSION_SMS_READ,
                PERMISSION_CALENDAR_READ
            )
        )
        .setAllPermissionGranted {
            TestLibrary.makeToast(requireContext(), "모든 권한 통과1")
            Log.d("my", "모든 권한 통과1")
        }
        .setPermissionDenied {
            TestLibrary.makeToast(requireContext(), "거부된 권한: $it")
            Log.d("my", "거부된 권한: $it")

        }
        .setPermissionExplained {
            TestLibrary.makeToast(requireContext(), "완전 거부된 권한: $it")
            Log.d("my", "완전 거부된 권한: $it")
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