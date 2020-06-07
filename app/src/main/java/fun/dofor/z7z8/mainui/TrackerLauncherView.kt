package `fun`.dofor.z7z8.mainui

import `fun`.dofor.common.util.getActivity
import `fun`.dofor.devhelper.DevHelper
import `fun`.dofor.devhelper.tracker.Tracker
import `fun`.dofor.z7z8.R
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ViewStub
import android.widget.CompoundButton
import android.widget.ImageButton
import java.lang.RuntimeException

class TrackerLauncherView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LauncherView(context, attrs, defStyleAttr) {

    private val activity: Activity =
        context.getActivity() ?: throw RuntimeException("获取不到 Activity")
    private val tracker: Tracker = DevHelper.createTracker(activity)
    private var vClassNameFilter: CompoundButton
    private var vShowEventInfo: CompoundButton
    private var vFilterHelp: ImageButton
    private var vShowEventInfoHelp: ImageButton

    var data: TrackerLauncherData = TrackerLauncherData()
        set(value) {
            field = value
            tracker.disableClassFilter = value.disableClassFilter
            tracker.showEventInfo = value.showEventInfo
            vClassNameFilter.isChecked = value.disableClassFilter
            vShowEventInfo.isChecked = value.showEventInfo
        }

    var requestCode: Int = 0

    init {
        title = "Tracker"
        desc = "开发辅助工具。获取当前Activity的全类名，列举当前页面控件id等。"
        setViewStubLayoutResource(R.layout.part_tracker_option)
        vClassNameFilter = findViewById(R.id.classNameFilterCompoundButton)
        vShowEventInfo = findViewById(R.id.showEventInfoCompoundButton)
        vFilterHelp = findViewById(R.id.filterHelpButton)
        vShowEventInfoHelp = findViewById(R.id.showEventInfoHelpButton)

        super.setOnClickListener {
            tracker.start(requestCode)
        }

        vClassNameFilter.setOnClickListener { v ->
            val btn = v as CompoundButton
            tracker.disableClassFilter = btn.isChecked
            data.disableClassFilter = btn.isChecked
        }

        vShowEventInfo.setOnClickListener { v ->
            val btn = v as CompoundButton
            tracker.showEventInfo = btn.isChecked
            data.showEventInfo = btn.isChecked
        }

        vFilterHelp.setOnClickListener {
            showInfoDialog("不显示Activity、Fragment、Dialog以外的组件名称。")
        }

        vShowEventInfoHelp.setOnClickListener {
            showInfoDialog("显示点击等事件信息")
        }
    }
}