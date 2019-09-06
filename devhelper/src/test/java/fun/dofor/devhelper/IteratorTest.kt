package `fun`.dofor.devhelper

import org.junit.Test


class IteratorTest {
    @Test
    fun test() {
        val node = genNode()
        val iterable = Iterable { NodeInfoIterator(node) }
        iterable.forEach {
            println(it)
        }
    }

    @Test
    fun test1() {
        val node = null
        val iterable = Iterable { NodeInfoIterator(node) }
        iterable.forEach {
            println(it)
        }
    }


    @Test
    fun test2() {
        val node = AccessibilityNodeInfo(emptyList(), "parent")
        val iterable = Iterable { NodeInfoIterator(node) }
        iterable.forEach {
            println(it)
        }
    }

    private fun genNode(): AccessibilityNodeInfo {
        return AccessibilityNodeInfo(
            listOf(
                AccessibilityNodeInfo(
                    listOf(
                        AccessibilityNodeInfo(emptyList(), "child1_1"),
                        AccessibilityNodeInfo(emptyList(), "child1_2")
                    ), "child1"
                ),
                AccessibilityNodeInfo(
                    listOf(
                        AccessibilityNodeInfo(emptyList(), "child2_1"),
                        AccessibilityNodeInfo(
                            listOfNotNull(
                                AccessibilityNodeInfo(emptyList(), "child2_2_1")
                            ), "child2_2"
                        )
                    ), "child2"
                ),
                AccessibilityNodeInfo(
                    listOf(
                        AccessibilityNodeInfo(emptyList(), "child3_1")
                    ), "child3"
                ),
                AccessibilityNodeInfo(emptyList(), "child4")
            ), "parent"
        )
    }

    private class NodeInfoIterator(private val nodeInfo: AccessibilityNodeInfo?) : Iterator<AccessibilityNodeInfo?> {
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
        }
    }

    private data class AccessibilityNodeInfo(val childs: List<AccessibilityNodeInfo>, val tag: String) {
        val childCount = childs.size
        fun getChild(index: Int): AccessibilityNodeInfo? {
            if (index < childCount) {
                return childs[index]
            }
            return null
        }
    }
}