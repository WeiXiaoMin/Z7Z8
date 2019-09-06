package `fun`.dofor.devhelper.accessbility

import `fun`.dofor.devhelper.accessbility.view.TrackerViewImpl
import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class TrackerService : AccessibilityService(), TrackerPresenter {
    companion object {
        //        const val TAG = "DEV_HELPER"
        const val KEY_TYPE = "key_type"
        var filterClassName = false
    }

    private var type = 0

    override val view: TrackerView by lazy {
        TrackerViewImpl(this, this)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        type = intent?.getIntExtra(KEY_TYPE, Type.CLOSE.code) ?: Type.CLOSE.code
        if (type == Type.OPEN.code) {
            this.view.show()
        } else {
            this.view.hide()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> updatePageInfo(event)
            AccessibilityEvent.TYPE_VIEW_CLICKED, AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> updateActionInfo(event)
        }
    }

    enum class Type(val code: Int) {
        OPEN(1), CLOSE(0)
    }

    override fun loadNodeInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 获取当前窗口的控件id
            rootInActiveWindow?.let {
                val nodeInfos = Iterable { NodeInfoIterator(it) }
                this.view.showNodeInfo(nodeInfos)
                it.recycle()
            }
        }
    }

    private fun updatePageInfo(event: AccessibilityEvent) {
        // 浮窗显示信息
        val className = event.className?.toString() ?: ""
        if (filterClassName) {
            if (!TextUtils.isEmpty(className)
                && (className.contains("Activity")
                        || className.contains("Fragment")
                        || className.contains("Dialog"))
            ) {
                this.view.showPageInfo(className)
            }
        } else {
            this.view.showPageInfo(className)
        }
    }

    private fun updateActionInfo(event: AccessibilityEvent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val source = event.source
            val sb = StringBuilder()
            val idName = source?.viewIdResourceName ?: ""
            if (TextUtils.isEmpty(idName)) {
                val childCount = source?.childCount ?: 0
                if (childCount > 0) {
                    var index = 0
                    do {
                        val child = source?.getChild(index)
                        if (child != null) {
                            val name = child.viewIdResourceName
                            if (!TextUtils.isEmpty(name)) {
                                sb.append("child:")
                                sb.append(child.className)
                                sb.append("\n")
                                sb.append("viewId:")
                                sb.append(name)
                                break
                            }
                        }
                        index++
                    } while (index < childCount)
                }
            } else {
                sb.append("source:")
                sb.append(source?.className ?: "")
                sb.append("\n")
                sb.append("viewId:")
                sb.append(idName)
            }
            if (TextUtils.isEmpty(sb.toString())) {
                sb.append("source:")
                sb.append(source?.className ?: "")
            }
            this.view.showActionInfo(sb.toString())
            source.recycle()
        }
    }

    class NodeInfoIterator(private val nodeInfo: AccessibilityNodeInfo?) : Iterator<AccessibilityNodeInfo?> {
        private var index = if (check(nodeInfo)) -1 else 0
        private val count = nodeInfo?.childCount ?: 0
        private var childs: NodeInfoIterator? = null

        override fun hasNext(): Boolean {
            return index < count || childs?.hasNext() == true
        }

        override fun next(): AccessibilityNodeInfo? {
            val lastIndex = this.index
            if (lastIndex == -1) {
                this.index = 0
                return nodeInfo
            }
            var cs = childs
            if (cs != null && cs.hasNext()) {
                return cs.next()
            }
            cs = NodeInfoIterator(nodeInfo?.getChild(lastIndex))
            this.index++
            this.childs = cs
            return cs.next()
        }

        private fun check(nodeInfo: AccessibilityNodeInfo?): Boolean {
            return nodeInfo?.childCount == 0
//            return nodeInfo != null
        }
    }
}