package `fun`.dofor.z7z8.mainui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.item_launcher.view.*

class LaucherListAdapter(private val context: Context) : RecyclerView.Adapter<LauncherViewHolder>() {

    var onTrackerLaunch: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LauncherViewHolder {
        val view = LauncherView(context)
        view.layoutParams = StaggeredGridLayoutManager.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT)
        return LauncherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: LauncherViewHolder, position: Int) {
        when(position) {
            0 -> {
                holder.itemView.title.text = "Tracker"
                holder.itemView.desc.text = "开发辅助工具。获取当前Activity的全类名，列举当前页面控件id等。"
                holder.itemView.setOnClickListener(onTrackerLaunch)
            }
        }
    }
}