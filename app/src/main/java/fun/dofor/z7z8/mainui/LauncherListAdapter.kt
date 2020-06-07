package `fun`.dofor.z7z8.mainui

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class LauncherListAdapter(private val context: Context) :
    RecyclerView.Adapter<LauncherViewHolder>() {

    companion object {
        const val TAG = "LauncherListAdapter"
    }

    var data = createLauncherData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LauncherViewHolder {
        Log.d(TAG, "viewType = $viewType")
        val view = when (viewType) {
            VIEW_TYPE_TRACKER -> TrackerLauncherView(parent.context)
            VIEW_TYPE_URI_TOOLS -> UriToolsLauncherView(parent.context)
            else -> FrameLayout(parent.context)
        }
        return LauncherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].viewType
    }

    override fun onBindViewHolder(holder: LauncherViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_TRACKER -> (holder.itemView as TrackerLauncherView).data =
                data[position] as TrackerLauncherData
        }
    }
}