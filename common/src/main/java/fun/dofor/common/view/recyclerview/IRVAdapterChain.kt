package `fun`.dofor.common.view.recyclerview

import androidx.recyclerview.widget.RecyclerView

interface IRVAdapterChain<T : RecyclerView.ViewHolder> : IRVAdapterWrapper<T> {
    val sectionAdapter: RecyclerView.Adapter<T>
}