package com.chan.home.navigation

import com.chan.navigation.NavDestinationProvider
import com.chan.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject

class HomeDestinationProvider @Inject constructor() : NavDestinationProvider {
    override fun getDestination() = listOf(HomeDestination)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeNavModule {
    @Binds
    @IntoSet
    abstract fun bindHomeNavGraph(graph: HomeNavGraph): NavGraphProvider

    @Binds
    @IntoSet
    abstract fun bindHomeDestinationProvider(provider: HomeDestinationProvider): NavDestinationProvider
}