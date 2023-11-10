package dev.vengateshm.artbookappandroidtestingudemy.drag_drop

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dev.vengateshm.artbookappandroidtestingudemy.databinding.ActivityDragDropBinding

class DragDropActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivityDragDropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDragDropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTopRecyclerView()
        setBottomRecyclerView()

        binding.tvEmptyListTop.visibility = View.GONE
        binding.tvEmptyListBottom.visibility = View.GONE
    }

    private fun setTopRecyclerView() {
        binding.rvTop.setHasFixedSize(true)
        binding.rvTop.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val topList: MutableList<String> = ArrayList()
        topList.add("A")
        topList.add("B")

        val topListAdapter = DragDropAdapter(topList, this)
        binding.rvTop.adapter = topListAdapter
        binding.tvEmptyListTop.setOnDragListener(topListAdapter.dragInstance)
        binding.rvTop.setOnDragListener(topListAdapter.dragInstance)
    }

    private fun setBottomRecyclerView() {
        binding.rvBottom.setHasFixedSize(true)
        binding.rvBottom.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val bottomList: MutableList<String> = ArrayList()
        bottomList.add("C")
        bottomList.add("D")

        val bottomListAdapter = DragDropAdapter(bottomList, this)
        binding.rvBottom.adapter = bottomListAdapter
        binding.tvEmptyListBottom.setOnDragListener(bottomListAdapter.dragInstance)
        binding.rvBottom.setOnDragListener(bottomListAdapter.dragInstance)
    }

    override fun setEmptyListTop(visibility: Boolean) {
        binding.tvEmptyListTop.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.rvTop.visibility = if (visibility) View.GONE else View.VISIBLE
    }

    override fun setEmptyListBottom(visibility: Boolean) {
        binding.tvEmptyListBottom.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.rvBottom.visibility = if (visibility) View.GONE else View.VISIBLE
    }
}