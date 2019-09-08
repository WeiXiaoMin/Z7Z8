package `fun`.dofor.devhelper.tracker.view

import `fun`.dofor.common.util.toast
import `fun`.dofor.devhelper.tracker.TrackerPresenter
import `fun`.dofor.devhelper.tracker.TrackerView
import `fun`.dofor.devhelper.tracker.model.EventInfo
import `fun`.dofor.devhelper.tracker.model.NodeInfo
import `fun`.dofor.devhelper.tracker.model.NodeInfoIterator
import `fun`.dofor.devhelper.tracker.model.PageInfo
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams

internal class TrackerViewImpl(
    private val context: Context,
    override val presenter: TrackerPresenter
) : TrackerView {

    companion object {
        var filterClassName = false
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
                    presenter.loadNodeInfo()
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

    override fun showPageInfo(info: PageInfo) {
        val className = info.className
        if (filterClassName) {
            if (!TextUtils.isEmpty(className)
                && (className.contains("Activity")
                        || className.contains("Fragment")
                        || className.contains("Dialog"))
            ) {
                this.floatView?.updateText1(className)
            }
        } else {
            this.floatView?.updateText1(className)
        }
    }

    override fun showEventInfo(info: EventInfo) {
        info.source?.run {
            val sb = StringBuilder()
            sb.append("source:")
            sb.append(className)
            if (!TextUtils.isEmpty(viewIdResourceName)) {
                sb.append("\n")
                sb.append("viewId:")
                sb.append(viewIdResourceName)
            }
            this@TrackerViewImpl.floatView?.updateText2(sb.toString())
        }
    }

    override fun showNodeInfo(iterator: NodeInfoIterator) {
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
            ntv.clear()
            ntv.visibility = View.VISIBLE
            this.state = State.NODE_INFO

            while (iterator.hasNext()) {
                val node = iterator.next()?.let { mapNode(it) }
                if (node != null) {
                    ntv.addNode(node)
                }
            }
            ntv.notifyDataSetChange()
            iterator.recycle()
        } else {
            context.toast("WindowManager为空")
        }
    }

    private fun mapNode(nodeInfo: NodeInfo): NodeTextView.Node? {
        val rect = nodeInfo.boundsInScreen
        val idName = nodeInfo.viewIdResourceName
        if (!TextUtils.isEmpty(idName) && "null" != idName) {
            val split = idName.split(":")
            return NodeTextView.Node(
                split[split.size - 1],
                rect.left.toFloat(),
                rect.top.toFloat()
            )
        }
        return null
    }

    enum class State {
        SIMPLE, NODE_INFO, NONE
    }
}