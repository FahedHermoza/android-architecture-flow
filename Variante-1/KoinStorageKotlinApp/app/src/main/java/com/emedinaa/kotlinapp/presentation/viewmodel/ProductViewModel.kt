package com.emedinaa.kotlinapp.presentation.viewmodel

import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.core.base.BaseViewModel
import com.emedinaa.kotlinapp.domain.model.Product
import com.emedinaa.kotlinapp.domain.usecase.AddProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.ClearProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.FetchProductUseCase
import com.emedinaa.kotlinapp.domain.usecase.UpdateProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/****
 * https://medium.com/androiddevelopers/migrating-from-livedata-to-kotlins-flow-379292f419fb
 * https://stackoverflow.com/questions/69042012/livedata-vs-stateflow-should-we-switch-from-live-data-to-state-flow
 * https://developer.android.com/kotlin/flow/stateflow-and-sharedflow?hl=es-419
 */
//Changed used ViewState and Event
class ProductViewModel(
    private val fetchProductUseCase: FetchProductUseCase,
    private val clearProductUseCase: ClearProductUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : BaseViewModel() {
    // Used StateFlow/MutableStateFlow instead of LiveData/MutableLiveData
    private val _state = MutableStateFlow(ProductViewState())
    val state: StateFlow<ProductViewState> get()= _state.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() = launch {
        fetchProductUseCase.invoke().collect {
            _state.value = ProductViewState(loading = false, products = it)
        }
    }

    fun loadProductsTest(productViewState: ProductViewState) = launch {
        _state.emit(productViewState)
    }

    fun onEvent(event: ProductEvent) {
        when(event) {
            is ProductEvent.ActionAddNewProduct -> addNewProduct(event.title, event.cost, event.description)
            is ProductEvent.ActionEditProduct -> editProduct(event.title, event.cost, event.product)
            is ProductEvent.ActionDeleteAllProducts -> deleteAllProducts()
        }

    }

    fun addNewProduct(title: String, cost: Double, description: String) = launch {
        var product = Product(0, title, cost, description, R.mipmap.ic_funko)
        val params = AddProductUseCase.Params(product)
        addProductUseCase.invoke(params)
    }

    fun editProduct(title: String, cost: Double, product: Product) = launch {
        product.name = title
        product.cost = cost
        val params = UpdateProductUseCase.Params(product)
        updateProductUseCase.invoke(params)
    }

    fun deleteAllProducts() = launch {
        clearProductUseCase.invoke()
    }
}