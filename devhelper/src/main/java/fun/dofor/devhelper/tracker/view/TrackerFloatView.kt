package `fun`.dofor.devhelper.tracker.view

import `fun`.dofor.devhelper.R
import `fun`.dofor.devhelper.tracker.TrackerService
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

internal class TrackerFloatView(context: Context) : LinearLayout(context) {
    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val textView1: TextView
    private val textView2: TextView
    private var downPoint: Point? = null

    init {
        View.inflate(context, R.layout.tracker_float_window, this)
        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        findViewById<View>(R.id.close).setOnClickListener {
            Toast.makeText(context, "关闭悬浮窗", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, TrackerService::class.java)
            intent.putExtra(TrackerService.KEY_TYPE, TrackerService.Type.CLOSE.code)
            context.startService(intent)
        }
    }

    fun updateDisplay(text1: String, text2: String) {
        textView1.text = text1
        textView2.text = text2
    }

    fun updateText1(text1: String) {
        textView1.text = text1
    }

    fun updateText2(text2: String) {
        textView2.text = text2
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> this.downPoint = Point(event.rawX.toInt(), event.rawY.toInt())

            MotionEvent.ACTION_MOVE -> {
                val movePoint = Point(event.rawX.toInt(), event.rawY.toInt())
                val dx = movePoint.x - (this.downPoint?.x ?: 0)
                val dy = movePoint.y - (this.downPoint?.y ?: 0)
                val layoutParams = this.layoutParams as WindowManager.LayoutParams
                layoutParams.x += dx
                layoutParams.y += dy
                this.windowManager.updateViewLayout(this, layoutParams)
                this.downPoint = movePoint

            }
        }
        return false
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        textView1.setOnClickListener(listener)
    }
}