package com.example.core.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import kotlin.collections.mutableListOf

class CoursesAdapter(
    onCourseClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit
) : ListAdapter<Course, RecyclerView.ViewHolder>(CourseDiffCallback()) {

    private val delegatesManager = AdapterDelegatesManager<List<Course>>()
        .addDelegate(CourseAdapterDelegates(onCourseClick, onFavoriteClick))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(currentList, position, holder, mutableListOf<Any>())
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(currentList, position)
    }

    class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
            oldItem == newItem
    }
}
