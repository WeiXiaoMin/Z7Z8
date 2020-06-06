package `fun`.dofor.common.view.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface IRVAdapterWrapper<T : RecyclerView.ViewHolder> {
    val sectionItemCount: Int
    var inner: RecyclerView.Adapter<RecyclerView.ViewHolder>?
    fun mapSectionItemPosition(globalItemPosition: Int): Int
    fun mapGlobalItemPosition(sectionPosition: Int): Int
    fun onSectionBindViewHolder(t: T, position: Int)
    fun getSectionItemViewType(position: Int): Int
    fun onSectionCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    fun isSectionItemViewType(viewType: Int): Boolean
}