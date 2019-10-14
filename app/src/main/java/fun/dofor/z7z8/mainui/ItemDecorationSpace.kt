package `fun`.dofor.z7z8.mainui

import `fun`.dofor.z7z8.R
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationSpace : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val space = parent.context.resources.getDimensionPixelSize(R.dimen.smallSpace)
        outRect.set(space, space, space, space)
    }
}