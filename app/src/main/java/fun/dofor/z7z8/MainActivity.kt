package `fun`.dofor.z7z8

import `fun`.dofor.common.util.isOverlayPermissionGranted
import `fun`.dofor.common.util.requestOverlayPermission
import `fun`.dofor.devhelper.DevHelper
import `fun`.dofor.z7z8.mainui.ItemDecorationSpace
import `fun`.dofor.z7z8.mainui.LauncherData
import `fun`.dofor.z7z8.mainui.LauncherListAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val CODE_START_TRACKER = 1
    }

    private val adapter: LauncherListAdapter by lazy {LauncherListAdapter(this)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isOverlayPermissionGranted()) {
            requestOverlayPermission(CODE_START_TRACKER)
        }
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        savedInstanceState?.let {
            val data = it.getSerializable("data")
            if (data != null) {
                adapter.data = data as Array<LauncherData>
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("data", adapter.data)
    }
}
