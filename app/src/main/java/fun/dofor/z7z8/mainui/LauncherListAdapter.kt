package `fun`.dofor.z7z8.mainui

import `fun`.dofor.z7z8.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class LauncherListAdapter(private val context: Context) : RecyclerView.Adapter<LauncherViewHolder>() {
    companion object{
        const val VIEW_TYPE_TRACKER = 0
    }

    var onTrackerLauncherClick: View.OnClickListener? = null
    var onClassNameFilterClick: View.OnClickListener? = null
    var onClassNameFilterHelpClick: View.OnClickListener? = null
    var launcherData: LauncherData = LauncherData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LauncherViewHolder {

        val view = LauncherView(context)
        view.layoutParams = StaggeredGridLayoutManager.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT)
        view.viewStub.layoutResource = R.layout.part_tracker_option
        view.viewStub.inflate()

        view.title.text = "Tracker"
        view.desc.text = "开发辅助工具。获取当前Activity的全类名，列举当前页面控件id等。"
        view.setOnClickListener(onTrackerLauncherClick)

        view.findViewById<CompoundButton>(R.id.classNameFilterCompoundButton)
            .setOnClickListener(onClassNameFilterClick)
        view.findViewById<ImageButton>(R.id.filterHelpButton)
            .setOnClickListener(onClassNameFilterHelpClick)
        return LauncherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> VIEW_TYPE_TRACKER
            else -> super.getItemViewType(position)
        }
    }

    override fun onBindViewHolder(holder: LauncherViewHolder, position: Int) {
        when(getItemViewType(position)) {
            VIEW_TYPE_TRACKER -> holder.itemView
                .findViewById<CompoundButton>(R.id.classNameFilterCompoundButton)
                .isChecked = launcherData.classNameFilter
        }
    }
}