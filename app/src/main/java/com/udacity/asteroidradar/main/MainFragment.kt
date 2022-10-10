package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        val factory = MainViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = AsteroidAdapter()
        binding.asteroidRecycler.adapter = adapter


        viewModel.asteroids?.let { asteroids ->
            asteroids.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    viewModel.fetchIfDBEmpty()
                }
                adapter.submitList(it)
            }
        }


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_today_asteroids_menu -> {
                viewModel.showTodayAsteroids()
            }
            R.id.show_week_menu_asteroids ->{
                viewModel.showWeekAsteroids()
            }
            R.id.show_saved_asteroids_menu ->{
                viewModel.showSavedAsteroids()
            }
        }
        return true
    }
}
