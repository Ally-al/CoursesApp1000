package com.example.feature_home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.presentation.CoursesAdapter
import com.example.feature_home.databinding.FragmentHomeBinding
import com.example.feature_home.di.DaggerHomeComponent
import com.example.feature_home.di.HomeDependencies
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var coursesAdapter: CoursesAdapter

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deps = requireActivity().application as? HomeDependencies
            ?: throw IllegalStateException("Application must implement HomeDependencies")

        DaggerHomeComponent.factory()
            .create(deps)
            .inject(this)

        viewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coursesAdapter = CoursesAdapter(
            onCourseClick = {},
            onFavoriteClick = { courseId -> viewModel.toggleFavorite(courseId) }
        )

        binding.rvCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = coursesAdapter
        }

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            coursesAdapter.submitList(courses)
        }

        binding.tvSort.setOnClickListener { viewModel.toggleSort() }
        binding.ivSort.setOnClickListener { viewModel.toggleSort() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
