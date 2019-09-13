package `fun`.dofor.common.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import android.content.pm.PackageManager
import java.util.*


fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

/**
 * 开启无障碍功能
 */
fun Context.requestAccessibility() {
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    startActivity(intent)
}

fun Activity.requestAccessibility(requestCode: Int) {
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    startActivityForResult(intent, requestCode)
}

/**
 * 无障碍功能是否已经开启
 */
fun Context.isAccessibilityEnable(): Boolean {
    try {
        val enable = Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        if (enable == 1) {
            val services = Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            return services?.toLowerCase(Locale.getDefault())?.contains(packageName.toLowerCase(Locale.getDefault())) ?: false
        }
    } catch (e: Settings.SettingNotFoundException) {
        e.printStackTrace()
    }
    return false
}

/**
 * 有无悬浮窗（覆盖层）权限
 * AppOpsManager.MODE_ALLOWED —— 表示授予了权限并且重新打开了应用程序
 * AppOpsManager.MODE_IGNORED —— 表示授予权限并返回应用程序
 * AppOpsManager.MODE_ERRORED —— 表示当前应用没有此权限
 * AppOpsManager.MODE_DEFAULT —— 表示默认值，有些手机在没有开启权限时，mode的值就是这个
 */
fun Context.isOverlayPermissionGranted(): Boolean {
    // 感觉没必要
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val aom = getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager
//        if (aom == null) {
//            return false
//        }
//        val mode = aom.checkOpNoThrow(android.Manifest.permission.SYSTEM_ALERT_WINDOW, android.os.Process.myUid(), packageName)
//        return Settings.canDrawOverlays(this) || mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED
//    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return Settings.canDrawOverlays(this)
    }
    return true
}

/**
 * 请求悬浮窗（覆盖层）权限
 */
fun Activity.requestOverlayPermission(requestCode: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        startActivityForResult(intent, requestCode)
    }
}

/**
 * 获取应用名称
 */
fun Context.getAppName(): String? {
    try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val applicationInfo = packageInfo.applicationInfo
        val labelRes = applicationInfo.labelRes
        return resources.getString(labelRes)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return null
}