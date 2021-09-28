package com.emedinaa.kotlinapp.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.emedinaa.kotlinapp.core.utils.dialog.UtilsAlertDialog
import com.google.android.material.snackbar.Snackbar

abstract class BaseBindingFragment<VB : ViewDataBinding>(@LayoutRes protected val layoutRes: Int) :
    Fragment() {

    protected lateinit var binding: VB

    private val dialog: AlertDialog by lazy {
        UtilsAlertDialog.setProgressDialog(requireContext(), "Loading..")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModel()
    }

    abstract fun init()

    abstract fun initViewModel()

    fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    // Optional
    fun showAlertProgressLoading(show: Boolean) {
        if(show) dialog.show() else dialog.hide()
    }

    private fun showAlertProgress() {
        dialog.show()
    }

    private fun hideAlertProgress() {
        dialog.hide()
    }

    fun showProgressBarLoading(show: Boolean, view: ProgressBar) {
        view.visibility = if(show) View.VISIBLE else View.GONE
    }

    // Observers
    @MainThread
    protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T?) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            onChanged.invoke(value)
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNull(crossinline onChanged: (T) -> Unit): Observer<T?> {
        val wrappedObserver = Observer<T?> { value ->
            if (value != null) {
                onChanged.invoke(value)
            }
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

}