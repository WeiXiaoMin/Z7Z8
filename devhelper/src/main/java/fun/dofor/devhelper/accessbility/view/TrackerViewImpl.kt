package `fun`.dofor.devhelper.accessbility.view

import `fun`.dofor.common.util.toast
import `fun`.dofor.devhelper.accessbility.TrackerPresenter
import `fun`.dofor.devhelper.accessbility.TrackerView
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Rect
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi

internal class TrackerViewImpl(
    private val context: Context,
    override val presenter: TrackerPresenter
) : TrackerView {

    companion object {
        val LAYOUTPARAMS = LayoutParams().apply {
            x = 0
            y = 0
            width = LayoutParams.WRAP_CONTENT
            height = LayoutParams.WRAP_CONTENT
            gravity = Gravity.START or Gravity.TOP
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = LayoutParams.FLAG_NOT_TOUCH_MODAL or LayoutParams.FLAG_NOT_FOCUSABLE
        }
        val FULL_LAYOUT_PARAMS = LayoutParams().apply {
            x = 0
            y = 0
            width = LayoutParams.MATCH_PARENT
            height = LayoutParams.MATCH_PARENT
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = LayoutParams.FLAG_NOT_TOUCH_MODAL or LayoutParams.FLAG_NOT_FOCUSABLE
        }
    }

    private val windowManager by lazy { context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager }

    private var state = State.NONE
    private var floatView: TrackerFloatView? = null
    private var nodeTextView: NodeTextView? = null

    override fun show() {
        val wm = this@TrackerViewImpl.windowManager
        if (wm != null) {
            val floatView = this.floatView ?: TrackerFloatView(context).apply {
                wm.addView(this, LAYOUTPARAMS)
                this@TrackerViewImpl.floatView = this
                setOnClickListener {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        presenter.loadNodeInfo()
                    }
                }
            }
            floatView.visibility = View.VISIBLE
            this.state = State.SIMPLE
        } else {
            context.toast("WindowManager为空")
        }
    }

    override fun hide() {
        val wm = this@TrackerViewImpl.windowManager
        if (wm != null) {
            this.floatView?.let {
                wm.removeView(it)
                this@TrackerViewImpl.floatView = null
            }
            this.nodeTextView?.let {
                wm.removeView(it)
                this@TrackerViewImpl.nodeTextView = null
            }
            this.state = State.NONE
        }
    }

    override fun showPageInfo(info: String) {
        this.floatView?.updateText1(info)
    }

    override fun showActionInfo(info: String) {
        this.floatView?.updateText2(info)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun showNodeInfo(nodeInfos: Iterable<AccessibilityNodeInfo?>) {
        val wm = this@TrackerViewImpl.windowManager
        if (wm != null) {
            this.floatView?.visibility = View.GONE
            val ntv = this.nodeTextView ?: NodeTextView(context).apply {
                wm.addView(this, FULL_LAYOUT_PARAMS)
                this@TrackerViewImpl.nodeTextView = this
                setBackgroundColor(Color.parseColor("#80000000"))
                setOnClickListener {
                    visibility = View.GONE
                    show()
                }
            }
            ntv.visibility = View.VISIBLE
            this.state = State.NODE_INFO
            nodeInfos.forEach {
                Log.d("TRACKER", it?.toString() ?: "null")
                if (it != null) {
                    addNodeInfo(ntv, it)
                }
            }
        } else {
            context.toast("WindowManager为空")
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun addNodeInfo(ntv: NodeTextView, nodeInfo: AccessibilityNodeInfo) {
        val rect = Rect()
        nodeInfo.getBoundsInScreen(rect)
        val idName: String = try {
            nodeInfo.viewIdResourceName // Kotlin 将其转换成非空变量时抛异常
        } catch (e: IllegalStateException) {
            ""
        }
        if (!TextUtils.isEmpty(idName) && "null" != idName) {
            val split = idName.split(":")
            val node = NodeTextView.Node(
                split[split.size - 1],
                rect.left.toFloat(),
                rect.top.toFloat()
            )
            ntv.addNode(node)
        }
    }

    enum class State {
        SIMPLE, NODE_INFO, NONE
    }
}