package com.chan.login.navigation

import com.chan.navigation.NavDestinationProvider
import com.chan.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject

class LoginNavModuleProvider @Inject constructor() : NavDestinationProvider {
    override fun getDestination() = listOf(LoginDestination)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeNavModule {
    @Binds
    @IntoSet
    abstract fun bindHomeNavGraph(graph: LoginNavGraph): NavGraphProvider

    @Binds
    @IntoSet
    abstract fun bindHomeDestinationProvider(provider: LoginNavModuleProvider): NavDestinationProvider
}