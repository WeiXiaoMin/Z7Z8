package `fun`.dofor.z7z8.mainui

import `fun`.dofor.z7z8.R
import `fun`.dofor.z7z8.uritools.UriToolsActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class LauncherListAdapter(private val context: Context) :
    RecyclerView.Adapter<LauncherViewHolder>() {
    companion object {
        const val VIEW_TYPE_TRACKER = 0
        const val VIEW_TYPE_URI_TOOLS = 1
    }

    var onTrackerLauncherClick: View.OnClickListener? = null
    var onClassNameFilterClick: View.OnClickListener? = null
    var onClassNameFilterHelpClick: View.OnClickListener? = null
    var onShowEventInfoClick: View.OnClickListener? = null
    var onShowEventInfoHelpClick: View.OnClickListener? = null

    var launcherData: LauncherData = LauncherData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LauncherViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_launcher, parent, false) as LauncherView
        view.layoutParams = StaggeredGridLayoutManager.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        when(viewType) {
            VIEW_TYPE_TRACKER -> {
                view.viewStub.layoutResource = R.layout.part_tracker_option
                view.viewStub.inflate()

                view.title.text = "Tracker"
                view.desc.text = "开发辅助工具。获取当前Activity的全类名，列举当前页面控件id等。"
                view.setOnClickListener(onTrackerLauncherClick)

                // TODO: CompoundButton赋初始值
                view.findViewById<CompoundButton>(R.id.classNameFilterCompoundButton)
                    .setOnClickListener(onClassNameFilterClick)
                view.findViewById<ImageButton>(R.id.filterHelpButton)
                    .setOnClickListener(onClassNameFilterHelpClick)

                view.findViewById<CompoundButton>(R.id.showEventInfoCompoundButton)
                    .setOnClickListener(onShowEventInfoClick)
                view.findViewById<ImageButton>(R.id.showEventInfoHelpButton)
                    .setOnClickListener(onShowEventInfoHelpClick)
            }
            VIEW_TYPE_URI_TOOLS -> {
                view.title.text = "URI Tools"
                view.desc.text = "URI 解析等工具。"
                view.setOnClickListener {
                    if (context is Activity) {
                        context.startActivity(Intent(context, UriToolsActivity::class.java))
                    }
                }
            }
        }

        return LauncherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_TRACKER
            1 -> VIEW_TYPE_URI_TOOLS
            else -> super.getItemViewType(position)
        }
    }

    override fun onBindViewHolder(holder: LauncherViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_TRACKER -> holder.itemView
                .findViewById<CompoundButton>(R.id.classNameFilterCompoundButton)
                .isChecked = launcherData.disableClassFilter
        }
    }
}