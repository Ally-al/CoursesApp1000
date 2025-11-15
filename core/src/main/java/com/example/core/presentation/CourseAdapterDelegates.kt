package com.example.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemCourseBinding
import com.example.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.example.core.utils.formatDate

class CourseAdapterDelegates(
    private val onCourseClick: (Int) -> Unit,
    private val onFavoriteClick: (Int) -> Unit
) : AdapterDelegate<List<Course>>() {

    override fun isForViewType(items: List<Course>, position: Int): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<Course>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as CourseViewHolder).bind(items[position], position)
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course, position: Int) {
            val covers = listOf(R.drawable.cover1, R.drawable.cover2, R.drawable.cover3)
            val coverResId = covers[position % covers.size]

            Glide.with(binding.ivCourseImage.context)
                .load(coverResId)
                .centerCrop()
                .into(binding.ivCourseImage)

            binding.ivCourseImage.postDelayed({
                val density = binding.root.context.resources.displayMetrics.density

                binding.blurFavorite.cornerRadius = 14f * density
                binding.blurFavorite.setSourceImageView(binding.ivCourseImage)

                binding.blurRating.cornerRadius = 100f
                binding.blurRating.setSourceImageView(binding.ivCourseImage)

                binding.blurDate.cornerRadius = 100f
                binding.blurDate.setSourceImageView(binding.ivCourseImage)
            }, 100)

            binding.tvCourseTitle.text = course.title
            binding.tvCourseDescription.text = course.text
            binding.tvPrice.text = "${course.price} ₽"
            binding.tvRating.text = course.rate
            binding.tvDate.text = formatDate(course.startDate)

            binding.btnFavorite.setImageResource(
                if (course.hasLike) R.drawable.ic_bookmark_fill
                else R.drawable.ic_bookmark
            )

            binding.root.setOnClickListener { onCourseClick(course.id) }
            binding.btnDetailsContainer.setOnClickListener { onCourseClick(course.id) }
            binding.btnFavorite.setOnClickListener { onFavoriteClick(course.id) }
        }
    }
}
