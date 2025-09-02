package com.chan.cart

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.cart.domain.CartRepository
import com.chan.cart.ui.mapper.toCartInProductsModel
import com.chan.cart.ui.mapper.toPopupProductInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
) : BaseViewModel<CartContract.Event, CartContract.State, CartContract.Effect>() {
    override fun setInitialState() = CartContract.State()

    override fun handleEvent(event: CartContract.Event) {
        when (event) {
            is CartContract.Event.LoadPopupProductInfo -> loadPopupProductInfo(event.productId)
            is CartContract.Event.LoadCartProducts -> loadCartInProducts()
            is CartContract.Event.AddToProduct -> addToCart(event.productId)
            is CartContract.Event.UpdateProductSelected -> updateProductSelected(
                event.productId,
                event.isSelected
            )

            is CartContract.Event.UpdateProductQuantity -> updateProductQuantity(
                event.productId,
                event.isAdd
            )

            CartContract.Event.OnAllSelected -> updateAllSelected()
            is CartContract.Event.DeleteProduct -> deleteProduct(event.productId)
        }
    }

    private fun deleteProduct(productId: String) {
        handleRepositoryCall(
            call = { cartRepository.deleteCartByProductId(productId) },
            onSuccess = { this }
        )
    }

    private fun updateAllSelected() {
        val allSelected = !viewState.value.allSelected
        handleRepositoryCall(
            call = { cartRepository.updateAllProductSelected(allSelected) },
            onSuccess = {
                copy(allSelected = allSelected)
            }
        )
    }

    private fun updateProductQuantity(productId: String, isAdd: Boolean) {
        if (isAdd) {
            handleRepositoryCall(
                call = { cartRepository.increaseProductQuantity(productId = "p2") },
                onSuccess = { result ->
                    this
                }
            )
        } else {
            handleRepositoryCall(
                call = { cartRepository.decreaseProductQuantity(productId = "p2") },
                onSuccess = { result ->
                    this
                }
            )
        }
    }

    private fun updateProductSelected(productId: String, isSelected: Boolean) {
        handleRepositoryCall(
            call = {
                cartRepository.updateProductSelected(
                    productId = "p2",
                    isSelected = isSelected
                )
            },
            onSuccess = { result ->
                this
            }
        )
    }

    private fun addToCart(productId: String) {
        handleRepositoryCall(
            call = { cartRepository.addToCart(productId = "p2") },
            onSuccess = { result ->
                setEffect { CartContract.Effect.ShowToast(R.string.success_cart_in) }
                setEffect { CartContract.Effect.DismissCartPopup }
                this
            }
        )
    }

    private fun loadCartInProducts() {
        viewModelScope.launch {
            cartRepository.getInCartProducts()
                .map { list -> list.map { it.toCartInProductsModel() } }
                .collect { products ->
                    val isAllSelected = products.isNotEmpty() && products.all { it.isSelected }
                    setState {
                        copy(
                            cartInProducts = products,
                            totalProductsCount = products.sumOf { it.quantity },
                            totalPrice = products.sumOf { it.quantity * it.discountPrice },
                            allSelected = isAllSelected
                        )
                    }
                }
        }
    }

    //장바구니 팝업 정보 조회
    private fun loadPopupProductInfo(productId: String) {
        handleRepositoryCall(
            call = { cartRepository.getProductInfo(productId = "p2") },
            onSuccess = { productInfo ->
                copy(popupProductInfo = productInfo.toPopupProductInfoModel())
            }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: CartContract.State.(T) -> CartContract.State,
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
                setEffect { CartContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}