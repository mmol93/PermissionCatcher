package com.easyo.permissioncatcherlibrary

const val DENIED = "denied"
const val EXPLAINED = "explained"

// https://developer.android.com/reference/kotlin/android/Manifest.permission_group
const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA

const val PERMISSION_CALENDAR_READ = android.Manifest.permission.READ_CALENDAR
const val PERMISSION_CALENDAR_WRITE = android.Manifest.permission.WRITE_CALENDAR

const val PERMISSION_CONTACTS_READ = android.Manifest.permission.READ_CONTACTS
const val PERMISSION_CONTACTS_WRITE = android.Manifest.permission.WRITE_CONTACTS

const val PERMISSION_LOCATION_FINE = android.Manifest.permission.ACCESS_FINE_LOCATION
const val PERMISSION_LOCATION_COARSE = android.Manifest.permission.ACCESS_COARSE_LOCATION

/** to use phone record */
const val PERMISSION_MIROPHONE = android.Manifest.permission.RECORD_AUDIO

const val PERMISSION_PHONE_READ_STATE = android.Manifest.permission.READ_PHONE_STATE
const val PERMISSION_PHONE_CALL = android.Manifest.permission.CALL_PHONE
const val PERMISSION_PHONE_READ_LOG = android.Manifest.permission.READ_CALL_LOG
const val PERMISSION_PHONE_WRITE_LOG = android.Manifest.permission.WRITE_CALL_LOG

const val PERMISSION_BODY_SENSOR = android.Manifest.permission.BODY_SENSORS

const val PERMISSION_SMS_SEND = android.Manifest.permission.SEND_SMS
const val PERMISSION_SMS_RECEIVE = android.Manifest.permission.RECEIVE_SMS
const val PERMISSION_SMS_READ = android.Manifest.permission.READ_SMS
const val PERMISSION_SMS_RECEIVE_WAP_PUSH = android.Manifest.permission.RECEIVE_WAP_PUSH
const val PERMISSION_SMS_RECEIVE_MMS = android.Manifest.permission.RECEIVE_MMS

const val PERMISSION_STORAGE_READ = android.Manifest.permission.READ_EXTERNAL_STORAGE
const val PERMISSION_STORAGE_WRITE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE