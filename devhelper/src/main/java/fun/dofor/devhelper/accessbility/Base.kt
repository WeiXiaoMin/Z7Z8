package `fun`.dofor.devhelper.accessbility

import `fun`.dofor.devhelper.IMvpView
import `fun`.dofor.devhelper.IPresenter
import `fun`.dofor.devhelper.accessbility.model.EventInfo
import `fun`.dofor.devhelper.accessbility.model.NodeInfoIterator
import `fun`.dofor.devhelper.accessbility.model.PageInfo

interface TrackerView : IMvpView<TrackerPresenter> {
    fun show()
    fun hide()
    fun showPageInfo(info: PageInfo)
    fun showEventInfo(info: EventInfo)
    fun showNodeInfo(iterator: NodeInfoIterator)
}

interface TrackerPresenter : IPresenter<TrackerView> {
    fun loadNodeInfo()
}