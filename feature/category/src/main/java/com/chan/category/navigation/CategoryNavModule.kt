package com.chan.category.navigation

import com.chan.navigation.NavDestinationProvider
import com.chan.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject

class CategoryDestinationProvider @Inject constructor() : NavDestinationProvider {
    override fun getDestination() = listOf(CategoryDestination)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryNavModule {
    @Binds
    @IntoSet
    abstract fun bindCategoryNavGraph(graph: CategoryNavGraph): NavGraphProvider

    @Binds
    @IntoSet
    abstract fun bindCategoryDestinationProvider(provider: CategoryDestinationProvider): NavDestinationProvider
}