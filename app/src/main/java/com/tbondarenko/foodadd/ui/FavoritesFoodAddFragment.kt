package com.tbondarenko.foodadd.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tbondarenko.foodadd.Application
import com.tbondarenko.foodadd.R
import com.tbondarenko.foodadd.adapter.FavoritesFoodAddAdapter
import com.tbondarenko.foodadd.databinding.FragmentFavoritesFoodAddBinding
import com.tbondarenko.foodadd.viewmodel.FoodAddViewModel
import com.tbondarenko.foodadd.viewmodel.FoodAddViewModelFactory

class FavoritesFoodAddFragment : BaseFragment<FragmentFavoritesFoodAddBinding>() {

    private val viewModel: FoodAddViewModel by activityViewModels {
        FoodAddViewModelFactory(
            (requireActivity().application as Application).repository)}

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var favoritesFoodAddAdapter: FavoritesFoodAddAdapter

    override fun getViewBinding() =
        FragmentFavoritesFoodAddBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView(view)
    }

    private fun setRecyclerView(view: View) {
        favoritesRecyclerView = binding.favoritesRecyclerView
        favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoritesFoodAddAdapter = FavoritesFoodAddAdapter({
            val action = FavoritesFoodAddFragmentDirections
                .actionFavoritesFoodAddFragmentToDetailFoodAddFragment(id = it.id)
            view.findNavController().navigate(action)
        }, {
            viewModel.deleteFavorites(it)
            val message = getString(R.string.snackBar_delete, it.number.toString())
            showSnack(view, message)
        })
        favoritesRecyclerView.adapter = favoritesFoodAddAdapter
        viewModel.favoritesFoodAdd.observe(this.viewLifecycleOwner) {
            favoritesFoodAddAdapter.addData(it)
        }
    }

    private fun showSnack(view: View, message: String) {
        Snackbar.make( view, message, Snackbar.LENGTH_SHORT).show()
    }
 }