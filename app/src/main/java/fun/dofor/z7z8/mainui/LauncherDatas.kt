package `fun`.dofor.z7z8.mainui

import `fun`.dofor.common.util.getActivity
import `fun`.dofor.z7z8.action.CheckScreenInfo
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import java.io.Serializable

const val VIEW_TYPE_DEFAULT = 0
const val VIEW_TYPE_TRACKER = 1
const val VIEW_TYPE_URI_TOOLS = 2

const val LAUNCH_ID_SCREEN_INFO = 1

fun createLauncherData(): Array<LauncherData> = arrayOf(
    TrackerLauncherData(),
    UriToolsLauncherData(),
    DefaultLauncherData(LAUNCH_ID_SCREEN_INFO, "查看屏幕信息", "点击查看")
)

interface LauncherData : Serializable, Parcelable {
    val viewType: Int
}

fun DefaultLauncherData.createOnClickListener(): View.OnClickListener? {
    return when(this.id) {
        LAUNCH_ID_SCREEN_INFO -> View.OnClickListener {v ->
            v.getActivity()?.let { CheckScreenInfo(it).run() }
        }
        else -> null
    }
}

data class DefaultLauncherData(
    val id: Int,
    val title: String,
    val desc: String = ""
) : LauncherData, Serializable, Parcelable {

    override val viewType: Int
        get() = VIEW_TYPE_DEFAULT

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DefaultLauncherData> {
        override fun createFromParcel(parcel: Parcel): DefaultLauncherData {
            return DefaultLauncherData(parcel)
        }

        override fun newArray(size: Int): Array<DefaultLauncherData?> {
            return arrayOfNulls(size)
        }
    }
}

data class UriToolsLauncherData(val title: String = "URI Tools") : LauncherData, Serializable,
    Parcelable {
    override val viewType: Int = VIEW_TYPE_URI_TOOLS

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UriToolsLauncherData> {
        override fun createFromParcel(parcel: Parcel): UriToolsLauncherData {
            return UriToolsLauncherData(parcel)
        }

        override fun newArray(size: Int): Array<UriToolsLauncherData?> {
            return arrayOfNulls(size)
        }
    }
}

data class TrackerLauncherData(
    var disableClassFilter: Boolean = false,
    var showEventInfo: Boolean = false
) : LauncherData, Serializable, Parcelable {
    override val viewType: Int = VIEW_TYPE_TRACKER

    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (disableClassFilter) 1 else 0)
        parcel.writeByte(if (showEventInfo) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrackerLauncherData> {
        override fun createFromParcel(parcel: Parcel): TrackerLauncherData {
            return TrackerLauncherData(parcel)
        }

        override fun newArray(size: Int): Array<TrackerLauncherData?> {
            return arrayOfNulls(size)
        }
    }


}

