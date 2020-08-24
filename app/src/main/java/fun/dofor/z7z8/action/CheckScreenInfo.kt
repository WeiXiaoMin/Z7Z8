package `fun`.dofor.z7z8.action

import `fun`.dofor.common.util.getScreenHeight
import `fun`.dofor.common.util.getScreenWidth
import `fun`.dofor.common.util.px2dp
import android.app.Activity
import android.app.AlertDialog

class CheckScreenInfo(
    val context: Activity
) : Runnable {

    override fun run() {

        val w = context.getScreenWidth()
        val h = context.getScreenHeight()

        val wDp = context.px2dp(w.toFloat())
        val hDp = context.px2dp(h.toFloat())

        AlertDialog.Builder(context)
            .setMessage("""
                |屏幕宽度（px）：$w
                |屏幕高度（px）：$h
                |屏幕宽度（dp）：$wDp
                |屏幕高度（dp）：$hDp
            """.trimMargin())
            .show()
    }
}