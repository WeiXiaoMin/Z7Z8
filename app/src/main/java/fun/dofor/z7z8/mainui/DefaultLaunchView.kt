package `fun`.dofor.z7z8.mainui

import android.content.Context
import android.util.AttributeSet

class DefaultLaunchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
)  : LauncherView(context, attrs, defStyleAttr) {

    fun setData(data: DefaultLauncherData) {
        vTitle.text = data.title
        vDesc.text = data.desc

        data.createOnClickListener()?.let {
            super.setOnClickListener(it)
        }
    }
}