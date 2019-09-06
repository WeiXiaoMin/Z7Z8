package `fun`.dofor.z7z8

import `fun`.dofor.common.util.isOverlayPermissionGranted
import `fun`.dofor.common.util.requestOverlayPermission
import `fun`.dofor.devhelper.DevHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val CODE_START_TRACKER = 1
    }

    private val mTracker by lazy { DevHelper.createTracker(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isOverlayPermissionGranted()) {
            requestOverlayPermission(CODE_START_TRACKER)
        }
        setContentView(R.layout.activity_main)
        startTracker.setOnClickListener {
            mTracker.start(CODE_START_TRACKER)
        }
    }
}
