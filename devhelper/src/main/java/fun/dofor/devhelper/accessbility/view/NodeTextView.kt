package `fun`.dofor.devhelper.accessbility.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

internal class NodeTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    private val mTextPaint: Paint
    private val mNodeList: MutableList<Node>
    private val mLocation = intArrayOf(0, 0)
    private val mTextSize: Int

    init {
        mNodeList = ArrayList()
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.color = Color.GREEN
        mTextSize = dp2px(context, 11f)
        mTextPaint.textSize = mTextSize.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        getLocationOnScreen(mLocation)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (node in mNodeList) {
            canvas.drawText(
                node.text,
                node.xOnScreen - mLocation[0],
                node.yOnScreen + mTextSize - mLocation[1],
                mTextPaint
            )
            canvas.drawCircle(node.xOnScreen - mLocation[0], node.yOnScreen - mLocation[1], 4f, mTextPaint)
            //            Log.i("onDraw", "onDraw: node = " + node + "\nmLocation = " + Arrays.toString(mLocation));
        }
    }

    fun setNodeList(list: List<Node>) {
        mNodeList.clear()
        mNodeList.addAll(list)
    }

    fun addNode(node: Node) {
        mNodeList.add(node)
    }

    fun notifyDataSetChange() {
        invalidate()
    }

    fun clear() {
        mNodeList.clear()
    }

    data class Node(val text: String, val xOnScreen: Float, val yOnScreen: Float)

    companion object {

        fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics)
                .toInt()
        }
    }
}