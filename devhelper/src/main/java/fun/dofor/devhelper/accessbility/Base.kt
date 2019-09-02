package `fun`.dofor.devhelper.accessbility

import `fun`.dofor.devhelper.IMvpView
import `fun`.dofor.devhelper.IPresenter
import android.view.accessibility.AccessibilityNodeInfo

interface TrackerView : IMvpView<TrackerPresenter> {
    fun show()
    fun hide()
    fun showPageInfo(info: String)
    fun showNodeInfo(nodeInfo: Iterable<AccessibilityNodeInfo>)
}

interface TrackerPresenter : IPresenter<TrackerView> {
    fun loadNodeInfo()
}