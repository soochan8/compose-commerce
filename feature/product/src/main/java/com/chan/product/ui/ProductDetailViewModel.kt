package com.chan.product.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.product.domain.repository.ProductDetailRepository
import com.chan.product.ui.mapper.toProductDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailRepository: ProductDetailRepository
) :
    BaseViewModel<ProductDetailContract.Event, ProductDetailContract.State, ProductDetailContract.Effect>() {
    override fun setInitialState()= ProductDetailContract.State()

    override fun handleEvent(event: ProductDetailContract.Event) {
        when (event) {
            ProductDetailContract.Event.ProductDetailLoad -> getProductDetailInfo()
        }
    }

    private fun getProductDetailInfo() {
        handleRepositoryCall(
            call = {
                //현재 json 데이터가 P001001이라 고정
                productDetailRepository.getProductDetail("P001001").toProductDetailModel()
            },
            onSuccess = { productDetails ->
                copy(
                    productDetailInfo = productDetails
                )
            }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: ProductDetailContract.State.(T) -> ProductDetailContract.State,
        onFinally: suspend (T) -> Unit = {}
    ) {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }
            try {
                val result = call()
                setState { onSuccess(result).copy(loadingState = LoadingState.Success) }
                onFinally(result)
            } catch (e: Exception) {
                setState { copy(loadingState = LoadingState.Error) }
                setEffect { ProductDetailContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}