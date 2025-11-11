package com.example.feature_home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.CourseAdapter
import com.example.feature_home.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var rvCourses: RecyclerView
    private lateinit var adapter: ListDelegationAdapter<List<com.example.domain.model.Course>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvCourses = view.findViewById(R.id.rvCourses)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListDelegationAdapter(
            CourseAdapter(
                onCourseClick = { /* ничего не делаем */ },
                onFavoriteClick = { /* можно обработать лайк */ }
            )
        )

        rvCourses.layoutManager = LinearLayoutManager(requireContext())
        rvCourses.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.courses.collectLatest { courses ->
                adapter.items = courses
                adapter.notifyDataSetChanged()
            }
        }
    }
}
