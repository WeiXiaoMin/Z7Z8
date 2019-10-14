package `fun`.dofor.z7z8

import `fun`.dofor.common.util.isOverlayPermissionGranted
import `fun`.dofor.common.util.requestOverlayPermission
import `fun`.dofor.devhelper.DevHelper
import `fun`.dofor.z7z8.mainui.ItemDecorationSpace
import `fun`.dofor.z7z8.mainui.LauncherData
import `fun`.dofor.z7z8.mainui.LauncherListAdapter
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = LauncherListAdapter(this).also {
            it.launcherData = LauncherData(mTracker.isClassNameFilterEnable())
            it.onTrackerLauncherClick = View.OnClickListener {
                mTracker.start(CODE_START_TRACKER)
            }
            it.onClassNameFilterClick = View.OnClickListener { v ->
                val btn = v as CompoundButton
                mTracker.setClassNameFilterEnable(btn.isChecked)
            }
            it.onClassNameFilterHelpClick = View.OnClickListener {
                AlertDialog.Builder(this)
                    .setTitle("说明")
                    .setMessage("不显示Activity、Fragment、Dialog以外的组件名称。")
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        }
        recyclerView.addItemDecoration(ItemDecorationSpace())
    }
}
