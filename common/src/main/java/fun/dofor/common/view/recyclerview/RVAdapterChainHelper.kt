package `fun`.dofor.common.view.recyclerview

import androidx.recyclerview.widget.RecyclerView

class RVAdapterChainHelper(private val itemViewTypeSeed: Int) {
    private var count = 0
    var chain: RVAdapterChain<RecyclerView.ViewHolder>? = null
        private set

    fun append(section: RecyclerView.Adapter<RecyclerView.ViewHolder>): RVAdapterChainHelper {
        wrap(section)
        return this
    }

    fun wrap(section: RecyclerView.Adapter<RecyclerView.ViewHolder>): RVAdapterChain<RecyclerView.ViewHolder> {
        val chain = RVAdapterChain(section, count * itemViewTypeSeed)
        count += 1
        if (this.chain != null) {
            chain.inner = this.chain
        }
        this.chain = chain
        return chain
    }

}