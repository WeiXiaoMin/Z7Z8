package `fun`.dofor.devhelper.accessbility.model

import android.view.accessibility.AccessibilityNodeInfo

class NodeInfoIterator(private val nodeInfo: AccessibilityNodeInfo?) : Iterator<NodeInfo?> {
    private var index = if (check(nodeInfo)) -1 else 0
    private val count = nodeInfo?.childCount ?: 0
    private var childs: NodeInfoIterator? = null
    private val recycleSet = mutableSetOf<NodeInfoIterator>()

    override fun hasNext(): Boolean {
        return index < count || childs?.hasNext() == true
    }

    override fun next(): NodeInfo? {
        val lastIndex = this.index
        if (lastIndex == -1) {
            this.index = 0
            return nodeInfo?.mapNodeInfo()
        }
        var cs = childs
        if (cs != null && cs.hasNext()) {
            return cs.next()
        }
        cs = NodeInfoIterator(nodeInfo?.getChild(lastIndex))
        this.index++
        this.childs = cs
        recycleSet.add(cs)
        return cs.next()
    }

    private fun check(nodeInfo: AccessibilityNodeInfo?): Boolean {
        return nodeInfo?.childCount == 0
    }

    fun recycle() {
        recycleSet.forEach { it.recycle() }
        nodeInfo?.recycle()
    }
}