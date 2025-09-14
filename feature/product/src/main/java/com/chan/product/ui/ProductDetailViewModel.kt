package com.chan.product.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.product.domain.repository.ProductDetailRepository
import com.chan.product.ui.ProductDetailContract.Effect.Navigation.ToCartPopupRoute
import com.chan.product.ui.ProductDetailContract.Effect.Navigation.ToCartRoute
import com.chan.product.ui.mapper.toProductDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailRepository: ProductDetailRepository
) :
    BaseViewModel<ProductDetailContract.Event, ProductDetailContract.State, ProductDetailContract.Effect>() {

    init {
        getProductDetailInfo()
    }

    override fun setInitialState() = ProductDetailContract.State()

    override fun handleEvent(event: ProductDetailContract.Event) {
        when (event) {
            is ProductDetailContract.Event.OnCouponDownloadClick -> downloadCoupon(event.couponId)
            is ProductDetailContract.Event.OnCartPopupClick -> setEffect { ToCartPopupRoute(event.productId) }
            is ProductDetailContract.Event.OnCartClick -> setEffect { ToCartRoute(event.productId) }
        }
    }

    private fun getProductDetailInfo() {
        handleRepositoryCall(
            call = {
                //현재 json 데이터가 p1이라 고정
                productDetailRepository.getProductDetail("p1").toProductDetailModel()
            },
            onSuccess = { productDetails ->
                copy(
                    productDetailInfo = productDetails
                )
            }
        )
    }

    private fun downloadCoupon(couponId: String) {
        viewModelScope.launch {
            try {
                setEffect { ProductDetailContract.Effect.ShowToast("쿠폰 발급 요청 중...") }

                productDetailRepository.downloadCoupon(couponId)

                setEffect { ProductDetailContract.Effect.ShowToast("쿠폰 발급 완료!") }
                setEffect { ProductDetailContract.Effect.UpdateWebView(couponId) }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "알 수 없는 오류가 발생했습니다."
                setEffect { ProductDetailContract.Effect.ShowToast(errorMessage) }
                setEffect { ProductDetailContract.Effect.RevertWebViewButton(couponId) }
            }
        }
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