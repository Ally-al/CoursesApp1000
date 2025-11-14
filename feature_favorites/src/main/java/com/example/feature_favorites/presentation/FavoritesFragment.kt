package com.example.feature_favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.presentation.CoursesAdapter
import com.example.feature_favorites.databinding.FragmentFavoritesBinding
import com.example.feature_favorites.di.DaggerFavoritesComponent
import com.example.feature_favorites.di.FavoritesDependencies
import com.example.feature_favorites.di.FavoritesModule
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var coursesAdapter: CoursesAdapter

    @Inject
    lateinit var favoritesViewModelFactory: FavoritesViewModelFactory

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deps: FavoritesDependencies =
            requireActivity().application as? FavoritesDependencies
                ?: throw IllegalStateException(
                    "Application must implement FavoritesDependencies"
                )

        val component = DaggerFavoritesComponent.factory().create(
            deps = deps,
            module = FavoritesModule(deps)
        )

        component.inject(this)

        viewModel = ViewModelProvider(this, favoritesViewModelFactory)[FavoritesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coursesAdapter = CoursesAdapter(
            onCourseClick = {},
            onFavoriteClick = { courseId ->
                viewModel.toggleFavorite(courseId)
            }
        )

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = coursesAdapter
        }

        viewModel.favoriteCourses.observe(viewLifecycleOwner) { favorites ->
            coursesAdapter.submitList(favorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
