package com.tbondarenko.foodadd.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tbondarenko.foodadd.Application
import com.tbondarenko.foodadd.R
import com.tbondarenko.foodadd.adapter.FoodAddAdapter
import com.tbondarenko.foodadd.databinding.FragmentListFoodAddBinding
import com.tbondarenko.foodadd.viewmodel.FoodAddViewModel
import com.tbondarenko.foodadd.viewmodel.FoodAddViewModelFactory

class ListFoodAddFragment : BaseFragment<FragmentListFoodAddBinding>(),
    SearchView.OnQueryTextListener {

        private val viewModel: FoodAddViewModel by activityViewModels {
        FoodAddViewModelFactory(
            (requireActivity().application as Application).repository)}

    private lateinit var recyclerView: RecyclerView
    private lateinit var foodAddAdapter: FoodAddAdapter
    private var isLinearLayout = true

    override var isOptionsMenu = true // setOptionsMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLinearLayout = savedInstanceState?.getBoolean(LAYOUT_MANAGER_KEY, true) ?: true
    }

    override fun getViewBinding() =
        FragmentListFoodAddBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView(view)
    }

    private fun setRecyclerView(view: View) {

        recyclerView = binding.listRecyclerView
        chooseLayout()
        foodAddAdapter = FoodAddAdapter {
            val action = ListFoodAddFragmentDirections
                .actionListFoodAddFragmentToDetailFoodAddFragment(id = it.id)
            view.findNavController().navigate(action)
        }
        recyclerView.adapter = foodAddAdapter
        viewModel.allFoodAdd.observe(this.viewLifecycleOwner) {
            foodAddAdapter.addData(it)
        }
    }

    private fun chooseLayout() {
        recyclerView.layoutManager = if (isLinearLayout) LinearLayoutManager(requireContext())
        else GridLayoutManager(requireContext(), 2)
    }

    private fun setIconLayout (menuItem: MenuItem?) {
        if (menuItem == null)
            return
        menuItem.icon = if (isLinearLayout) {
            ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_recycler_view)
        } else {
            ContextCompat.getDrawable(this.requireContext(),R.drawable.ic_list_recycler_view)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_fragment, menu)
        // create search view
        val itemSearch = menu.findItem(R.id.menu_search)
        val searchView = itemSearch.actionView as SearchView
        searchView.queryHint= getString(R.string.search)
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_switch_layout -> {
                isLinearLayout = !isLinearLayout
                chooseLayout()
                setIconLayout(item)}
            R.id.menu_show_all -> {viewModel.setDangerNumber(-1)}
            R.id.menu_danger_0 -> {viewModel.setDangerNumber(0)}
            R.id.menu_danger_1 -> {viewModel.setDangerNumber(1)}
            R.id.menu_danger_2 -> {viewModel.setDangerNumber(2)}
            R.id.menu_danger_3 -> {viewModel.setDangerNumber(3)}
            R.id.menu_danger_4 -> {viewModel.setDangerNumber(4)}
            R.id.menu_danger_5 -> {viewModel.setDangerNumber(5)}
        }
        return super.onOptionsItemSelected(item)
    }

    // implement setOnQueryTextListener
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
    override fun onQueryTextChange(query: String?): Boolean {
        foodAddAdapter.filter.filter(query)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(LAYOUT_MANAGER_KEY, isLinearLayout)
    }

    companion object {
        private const val LAYOUT_MANAGER_KEY = "isLinearLayout"
    }
}