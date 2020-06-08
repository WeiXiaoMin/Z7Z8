package `fun`.dofor.z7z8.mainui

import `fun`.dofor.common.util.getActivity
import `fun`.dofor.devhelper.DevHelper
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import java.lang.RuntimeException

class UriToolsLauncherView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LauncherView(context, attrs, defStyleAttr) {

    private val activity: Activity =
        context.getActivity() ?: throw RuntimeException("获取不到 Activity")

    init {
        title = "URI Tools"
        desc = "URI 解析等工具。"
        super.setOnClickListener {
            DevHelper.startUriToolsActivity(activity)
        }
    }
}