package com.tbondarenko.foodadd.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.tbondarenko.foodadd.Application
import com.tbondarenko.foodadd.R
import com.tbondarenko.foodadd.data.FoodAdd
import com.tbondarenko.foodadd.databinding.FragmentDetailFoodAddBinding
import com.tbondarenko.foodadd.viewmodel.FoodAddViewModel
import com.tbondarenko.foodadd.viewmodel.FoodAddViewModelFactory

class DetailFoodAddFragment : BaseFragment<FragmentDetailFoodAddBinding>() {

    private val viewModel: FoodAddViewModel by activityViewModels {
        FoodAddViewModelFactory((requireActivity().application as Application).repository)
    }
    private val navigationArgs: DetailFoodAddFragmentArgs by navArgs()

    override fun getViewBinding() =
        FragmentDetailFoodAddBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id

        viewModel.retrieveFoodAdd(id).observe(this.viewLifecycleOwner) { selectedFoodAdd ->
            with(binding) {
                foodAddNumberText.text = getString(
                    R.string.e_number,
                    selectedFoodAdd.number.toString()
                )
                foodAddNameText.text = selectedFoodAdd.name
                foodAddOriginText.text = getString(
                    R.string.e_origin,
                    selectedFoodAdd.origin.removeSuffix(getString(R.string.trim_origin)).lowercase()
                )
                foodAddTypeText.text = getString(R.string.e_type, selectedFoodAdd.type.lowercase())
                foodAddDangerText.text = getString(R.string.e_danger, selectedFoodAdd.danger)
                foodAddDescriptionText.text = selectedFoodAdd.description
                foodAddBenefitText.text = selectedFoodAdd.benefitToHealth
                foodAddHarmText.text = selectedFoodAdd.harmToHealth
                foodAddUseText.text = selectedFoodAdd.useCountry
                foodAddFoodButton.setOnClickListener { showFoodDialog(selectedFoodAdd.food) }
                setFab(selectedFoodAdd)
            }
        }
    }

    private fun showFoodDialog(food: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setMessage(food)
            .setPositiveButton(getString(R.string.dialog_button_ok)) { dialog, _ ->
                dialog.cancel()}
            .show()
    }

    private fun setFab(foodAdd: FoodAdd) {
        when (foodAdd.favorites) {
            FoodAddViewModel.FAVORITES_DELETE -> {
                binding.favoritesFab.setImageResource(R.drawable.ic_favorite_36)
                binding.favoritesFab.setOnClickListener { view ->
                    viewModel.addFavorites(foodAdd)
                    val message = getString(R.string.snackBar_add, foodAdd.number.toString())
                    showSnack(view, message)
                }
            }
            else -> {
                binding.favoritesFab.setImageResource(R.drawable.ic_delete_36)
                binding.favoritesFab.setOnClickListener { view ->
                    viewModel.deleteFavorites(foodAdd)
                    val message = getString(R.string.snackBar_delete, foodAdd.number.toString())
                    showSnack(view, message)
                }
            }
        }
    }

    private fun showSnack(view: View, message: String) {
        Snackbar.make( view, message, Snackbar.LENGTH_SHORT).show()
    }
}