package com.chan.cart

import com.chan.auth.domain.usecase.CheckSessionUseCase
import com.chan.cart.domain.usecase.AddProductUseCase
import com.chan.cart.domain.usecase.CartItemUseCase
import com.chan.cart.domain.usecase.CartUseCases
import com.chan.cart.domain.usecase.DecreaseProductUseCase
import com.chan.cart.domain.usecase.IncreaseProductUseCase
import com.chan.cart.domain.usecase.ProductInfoUseCase
import com.chan.cart.domain.usecase.RemoveProductUseCase
import com.chan.cart.domain.usecase.UpdateAllProductSelectedUseCase
import com.chan.cart.domain.usecase.UpdateProductSelectedUseCase
import com.chan.cart.proto.CartItem
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CartViewModelTest {
    //공통 환경 셋팅
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var cartRepository: TestCartRepository
    private lateinit var authRepository: TestAuthRepository
    private lateinit var sessionUseCase: CheckSessionUseCase
    private lateinit var cartUseCases: CartUseCases
    private lateinit var viewModel: CartViewModel

    @Before
    fun setup() {
        cartRepository = TestCartRepository()
        authRepository = TestAuthRepository()
        sessionUseCase = CheckSessionUseCase(authRepository)
        cartUseCases = CartUseCases(
            productInfoUseCase = ProductInfoUseCase(cartRepository),
            cartItemUseCase = CartItemUseCase(cartRepository),
            addProductUseCase = AddProductUseCase(cartRepository),
            removeProductUseCase = RemoveProductUseCase(cartRepository),
            updateProductSelectedUseCase = UpdateProductSelectedUseCase(cartRepository),
            updateAllProductSelectedUseCase = UpdateAllProductSelectedUseCase(cartRepository),
            increaseProductUseCase = IncreaseProductUseCase(cartRepository),
            decreaseProductUseCase = DecreaseProductUseCase(cartRepository)
        )
        viewModel = CartViewModel(
            checkSessionUseCase = sessionUseCase,
            cartUseCases = cartUseCases
        )
    }

    @Test
    fun `장바구니 로드 시 합계 정보 계산`() = runTest {
        val testItem1 = CartItem.newBuilder()
            .setProductId("product1")
            .setQuantity(2)
            .setDiscountedPrice(4500)
            .setIsSelected(true)
            .build()
        val testItem2 = CartItem.newBuilder()
            .setProductId("product2")
            .setQuantity(3)
            .setDiscountedPrice(3000)
            .setIsSelected(true)
            .build()

        cartRepository.setCartItem(listOf(testItem1, testItem2))

        viewModel.setEvent(CartContract.Event.LoadCartProducts)
        advanceUntilIdle()

        assertEquals(5, viewModel.viewState.value.totalProductsCount)
        assertEquals(18000, viewModel.viewState.value.totalPrice)
        assertEquals(true, viewModel.viewState.value.allSelected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `장바구니 아이템 삭제`() = runTest {
        //Arrange
        val testItem1 = CartItem.newBuilder()
            .setProductId("product1")
            .setQuantity(2)
            .setDiscountedPrice(4500)
            .setIsSelected(true)
            .build()
        val testItem2 = CartItem.newBuilder()
            .setProductId("product2")
            .setQuantity(3)
            .setDiscountedPrice(3000)
            .setIsSelected(true)
            .build()
        cartRepository.setCartItem(listOf(testItem1, testItem2))
        //Act
        viewModel.setEvent(CartContract.Event.DeleteProduct("product1"))
        advanceUntilIdle()
        //Assert
        val remainingItems = cartRepository.getCartItems(0).first()
        assertFalse(remainingItems.any { it.productId == "product1" })
        assertEquals(1, remainingItems.size)
    }

    @Test
    fun `수량이 2개 이상일 때 1개 감소`() = runTest {
        //Arrange
        cartRepository.setCartItem(
            listOf(
                CartItem.newBuilder()
                    .setProductId("product1")
                    .setQuantity(2)
                    .setDiscountedPrice(4500)
                    .setIsSelected(true)
                    .build()
            )
        )
        advanceUntilIdle()
        //Act
        viewModel.setEvent(
            CartContract.Event.UpdateProductQuantity(
                productId = "product1",
                isAdd = false
            )
        )
        advanceUntilIdle()
        //Assert
        val state = viewModel.viewState.value

        //상품 갯수
        assertEquals(1, state.cartInProducts.size)
        //상품 수량
        assertEquals(1, state.cartInProducts.first().quantity)
        //장바구니 총 수량
        assertEquals(1, state.totalProductsCount)
    }

    @Test
    fun `수량이 1개 일 때 감소되어 제거`() = runTest {
        //Arrange
        cartRepository.setCartItem(
            listOf(
                CartItem.newBuilder()
                    .setProductId("product1")
                    .setQuantity(1)
                    .setDiscountedPrice(4500)
                    .setIsSelected(true)
                    .build()
            )
        )
        advanceUntilIdle()
        //Act
        viewModel.setEvent(
            CartContract.Event.UpdateProductQuantity(
                productId = "product1",
                isAdd = false
            )
        )
        advanceUntilIdle()
        //Assert
        val state = viewModel.viewState.value
        assertEquals(0, state.cartInProducts.size)
        assertEquals(0, state.totalProductsCount)
        assertEquals(0, state.totalPrice)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) = Dispatchers.setMain(testDispatcher)
    override fun finished(description: Description) = Dispatchers.resetMain()
}
