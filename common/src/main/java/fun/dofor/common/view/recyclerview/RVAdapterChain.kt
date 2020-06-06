package `fun`.dofor.common.view.recyclerview

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver

/**
 * @param sectionAdapter          当前片段的Adapter
 * @param itemViewTypeSeed 为了避免ItemViewType冲突，建议按一定的步长递增，该步长要比任一ItemViewType都要大
 */
class RVAdapterChain<T : RecyclerView.ViewHolder>(
    override val sectionAdapter: RecyclerView.Adapter<T>,
    private val itemViewTypeSeed: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IRVAdapterChain<T> {

    companion object {
        private const val TAG = "RVAdapterChain"
    }

    // 核心（上面的片段）
    override var inner: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    override fun getItemViewType(position: Int): Int {
        val pos = mapSectionItemPosition(position)
        if (pos >= 0) {
            return getSectionItemViewType(pos) + itemViewTypeSeed
        }
        val inner = inner
        return inner?.getItemViewType(position) ?: super.getItemViewType(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (isSectionItemViewType(viewType)) {
            return onSectionCreateViewHolder(parent, viewType - itemViewTypeSeed)
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
            val inner = inner
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
            -2
        } else sectionItemPosition
    }

    override fun mapGlobalItemPosition(sectionPosition: Int): Int {
        return (inner?.itemCount ?: 0) + sectionPosition
    }

    override val sectionItemCount: Int
        get() = sectionAdapter.itemCount

    override fun onSectionBindViewHolder(t: T, position: Int) {
        sectionAdapter.onBindViewHolder(t, position)
    }

    override fun getSectionItemViewType(position: Int): Int {
        return sectionAdapter.getItemViewType(position)
    }

    override fun onSectionCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return sectionAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun isSectionItemViewType(viewType: Int): Boolean {
        return viewType - itemViewTypeSeed >= 0
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        sectionAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                notifyDataSetChanged()
                Log.d(TAG, String.format("section:%s onChanged:", sectionAdapter.toString()))
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                this@RVAdapterChain.notifyItemRangeChanged(
                    mapGlobalItemPosition(positionStart), itemCount)
                Log.d(TAG, String.format("section:%s onItemRangeChanged: start=%d, count=%d",
                        sectionAdapter.toString(), positionStart, itemCount))
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                this@RVAdapterChain.notifyItemRangeChanged(
                    mapGlobalItemPosition(positionStart), itemCount, payload)
                Log.d(TAG, String.format("section:%s onItemRangeChanged: start=%d, count=%d, payload=%s",
                        sectionAdapter.toString(), positionStart, itemCount, payload.toString()))
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(
                    mapGlobalItemPosition(positionStart), itemCount)
                Log.d(TAG, String.format("section:%s onItemRangeInserted: start=%d, count=%d",
                        sectionAdapter.toString(), positionStart, itemCount))
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(mapGlobalItemPosition(positionStart), itemCount)
                Log.d(TAG, String.format("section:%s onItemRangeRemoved: start=%d, count=%d",
                        sectionAdapter.toString(), positionStart, itemCount))
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                val gf = mapGlobalItemPosition(fromPosition)
                val gt = mapGlobalItemPosition(toPosition)
                for (i in 0 until itemCount) {
                    notifyItemMoved(gf, gt)
                    Log.d(TAG, String.format("section:%s onItemRangeMoved: f=%d, t=%d, gf=%d, gt=%d",
                            sectionAdapter.toString(), fromPosition, toPosition, gf, gt))
                }
            }
        })
        val inner = this.inner
        if (inner != null) {
            inner.registerAdapterDataObserver(object : AdapterDataObserver() {
                override fun onChanged() {
                    notifyDataSetChanged()
                    Log.d(TAG, String.format("inner:%s onChanged:", inner.toString()))
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    this@RVAdapterChain.notifyItemRangeChanged(positionStart, itemCount)
                    Log.d(TAG, String.format("inner:%s onItemRangeChanged: start=%d, count=%d",
                            inner.toString(), positionStart, itemCount))
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                    this@RVAdapterChain.notifyItemRangeChanged(positionStart, itemCount, payload)
                    Log.d(TAG, String.format("inner:%s onItemRangeChanged: start=%d, count=%d, payload=%s",
                            inner.toString(), positionStart, itemCount, payload.toString()))
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    notifyItemRangeInserted(positionStart, itemCount)
                    Log.d(TAG, String.format("inner:%s onItemRangeInserted: start=%d, count=%d",
                            inner.toString(), positionStart, itemCount))
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    notifyItemRangeRemoved(positionStart, itemCount)
                    Log.d(TAG, String.format("inner:%s onItemRangeRemoved: start=%d, count=%d",
                            inner.toString(), positionStart, itemCount))
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    for (i in 0 until itemCount) {
                        notifyItemMoved(fromPosition, toPosition)
                        Log.d(TAG, String.format("inner:%s onItemRangeMoved: f=%d, t=%d",
                                inner.toString(), fromPosition, toPosition))
                    }
                }
            })
            inner.onAttachedToRecyclerView(recyclerView)
        }
    }
}