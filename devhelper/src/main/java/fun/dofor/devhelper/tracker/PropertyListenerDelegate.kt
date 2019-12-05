package `fun`.dofor.devhelper.tracker

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class PropertyListenerDelegate<T>(defaultValue: T) : ReadWriteProperty<Any, T> {

    private var value: T = defaultValue

    private val onChangeListeners = mutableListOf<OnChangeListener<T>>()

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return this.value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val old = this.value
        this.value = value
        onChangeListeners.forEach {
            it.onChange(value, old)
        }
    }

    internal fun addOnChangeListener(listener: OnChangeListener<T>) {
        onChangeListeners.add(listener)
    }

    internal fun removeOnChangeListener(listener: OnChangeListener<T>) {
        onChangeListeners.remove(listener)
    }

    internal interface OnChangeListener<Property> {
        fun onChange(next: Property, old: Property)
    }
}