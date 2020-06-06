package `fun`.dofor.common.view.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RVAdapterWrapper<T : RecyclerView.ViewHolder>(override var inner: RecyclerView.Adapter<RecyclerView.ViewHolder>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), IRVAdapterWrapper<T> {

    override fun getItemViewType(position: Int): Int {
        val pos = mapSectionItemPosition(position)
        if (pos >= 0) {
            return getSectionItemViewType(pos)
        }
        return inner?.getItemViewType(position) ?: super.getItemViewType(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (isSectionItemViewType(viewType)) {
            return onSectionCreateViewHolder(parent, viewType)
        }
        return inner?.onCreateViewHolder(parent, viewType) ?: EmptyViewHolder(parent.context)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val pos = mapSectionItemPosition(position)
        if (pos >= 0) {
            onSectionBindViewHolder(holder as T, pos)
        } else {
            inner?.onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
       return (inner?.itemCount ?: 0) + sectionItemCount
    }

    override fun mapSectionItemPosition(globalItemPosition: Int): Int {
        val innerItemCount = inner?.itemCount ?: 0
        if (globalItemPosition < innerItemCount) {
            return -1
        }
        val sectionItemPosition = globalItemPosition - innerItemCount
        return if (sectionItemPosition >= sectionItemCount) {
            // TODO: 2018/9/21 这里或许应该抛异常
            -2
        } else sectionItemPosition
    }

}