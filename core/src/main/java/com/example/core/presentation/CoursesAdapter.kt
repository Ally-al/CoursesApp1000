package com.example.core.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import kotlin.collections.mutableListOf

class CoursesAdapter(
    onCourseClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Course>()
    private val delegatesManager = AdapterDelegatesManager<List<Course>>()
        .addDelegate(CourseAdapterDelegates(onCourseClick, onFavoriteClick))

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int) =
        delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(items, position, holder, mutableListOf<Any>())
    }

    fun submitList(courses: List<Course>) {
        items.clear()
        items.addAll(courses)
        notifyDataSetChanged()
    }
}
