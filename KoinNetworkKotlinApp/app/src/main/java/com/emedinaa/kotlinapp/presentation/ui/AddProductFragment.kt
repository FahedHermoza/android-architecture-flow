package com.emedinaa.kotlinapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.databinding.FragmentAddProductBinding
import com.emedinaa.kotlinapp.presentation.UtilsAlertDialog
import com.emedinaa.kotlinapp.presentation.viewmodel.AddProductViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel


class AddProductFragment : Fragment() {
    private val viewModel: AddProductViewModel by viewModel()

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val dialog: AlertDialog by lazy {
        UtilsAlertDialog.setProgressDialog(requireContext(), "Loading..")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()

        binding.btnAddProduct.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var cost = binding.etCost.text.toString().toDoubleOrNull()?:0.0

            if(isValidate(title, cost)) {
                showAlertProgress()
                viewModel.addProduct(title, cost)
            }
        }
    }

    private fun setObservers() {
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            hideAlertProgress()
            it?.let {
                showMessage(it)
            }
        })

        viewModel.onSuccess.observe(viewLifecycleOwner, Observer {
            hideAlertProgress()
            it?.let {
                findNavController().popBackStack()
            }
        })
    }

    private fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    private fun isValidate(title: String, cost: Double): Boolean {
        if(title.isNullOrEmpty())
            return false

        if(cost<0.0)
            return false

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAlertProgress(){
        dialog.show()
    }

    private fun hideAlertProgress(){
        dialog.hide()
    }
}