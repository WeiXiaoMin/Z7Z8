package `fun`.dofor.devhelper.tracker

/**
 * 关闭类名过滤
 */
internal object DisableClassFilterDelegate : PropertyListenerDelegate<Boolean>(false)

/**
 * 显示事件信息
 */
internal object ShowEventInfoDelegate : PropertyListenerDelegate<Boolean>(false)