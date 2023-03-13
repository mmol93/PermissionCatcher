# PermissionCatcher - Simple tool to get permission

[![](https://jitpack.io/v/mmol93/PermissionCatcher.svg)](https://jitpack.io/#mmol93/PermissionCatcher)

## How to install

### 1. set maven in your settings.grdle
``` kotlin 
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```


### 2. add dependency in your module gradle
``` kotlin
dependencies {
    implementation 'com.github.mmol93:PermissionCatcher:x.x.x'
}
```
you should set version instead of x.x.x
for example,

`implementation 'com.github.mmol93:PermissionCatcher:2.0.4'`

---------

## Usage
### 1. set `PermissionCatcher` instance
* set instance in Activity or Fragment global variable area

``` kotlin
private val permissionCatcher = PermissionCatcher(this)
    // set permissions
    // All permission list is already set.
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
    // when All permission is granted
    .setAllPermissionGranted {
        Log.d(LOG_TAG, "all permission is granted1")
    }
    // when some permissions are denied
    .setPermissionDenied {
        // show denied permissions
        Log.d(LOG_TAG, "denied permissions: $it")

    }
    // when some permissions are explained
    .setPermissionExplained {
        // show explained permissions
        Log.d(LOG_TAG, "explained permissions: $it")
    }
        
```

### 2. request permission
* you can request permission when lifecycle status is `onResume`
* for example
   * onCreate in Activity
   * onViewCreated in Fragment

> Activity
``` kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    setContentView(binding.root)
    Log.d(LOG_TAG, "onCreate")
    binding.checkPermission.setOnClickListener {
    
        // request Permission
        permissionCatcher.requestPermission {
            // you can set action when all permissions are granted
            // this action has higher priority than setAllPermissionGranted function.
            
        }
    }
}
```

> Fragment
``` kotlin
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
    
        // or you can skip action when all permissions are granted
        permissionCatcher.requestPermission()
    }
}
```
