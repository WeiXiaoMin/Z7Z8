package `fun`.dofor.z7z8.mainui

import `fun`.dofor.z7z8.R
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog


abstract class LauncherView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "LauncherView"
    }

    protected val vTitle: TextView
    protected val vDesc: TextView
    protected val vViewStub: ViewStub
    protected var title: String = ""
        set(value) {
            field = value
            vTitle.text = value
        }
    protected var desc: String = ""
        set(value) {
            field = value
            vDesc.text = desc
        }

    init {
        View.inflate(context, R.layout.view_launcher, this)
        vTitle = findViewById(R.id.title)
        vDesc = findViewById(R.id.desc)
        vViewStub = findViewById(R.id.viewStub)
        isClickable = true
    }

    protected fun setViewStubLayoutResource(@LayoutRes res: Int) {
        vViewStub.layoutResource = res
        vViewStub.inflate()
    }

    fun showInfoDialog(info: String) {
        AlertDialog.Builder(context)
            .setTitle("说明")
            .setMessage(info)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}