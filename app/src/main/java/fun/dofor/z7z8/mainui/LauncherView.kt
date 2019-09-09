package `fun`.dofor.z7z8.mainui

import `fun`.dofor.z7z8.R
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.TextView

class LauncherView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val title: TextView
    val desc: TextView
    val viewStub: ViewStub

    init {
        View.inflate(context, R.layout.item_launcher, this)
        title = findViewById(R.id.title)
        desc = findViewById(R.id.desc)
        viewStub = findViewById(R.id.viewStub)
    }
}