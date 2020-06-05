package `fun`.dofor.z7z8.mainui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface LauncherItem {
    val viewType: Int
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
}