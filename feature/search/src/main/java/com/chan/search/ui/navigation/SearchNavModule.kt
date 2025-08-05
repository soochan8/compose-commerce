package com.chan.search.ui.navigation

import com.chan.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchNavModule {

    @Binds
    @IntoSet
    abstract fun bindSearchNavGraph(graph: SearchNavGraph) : NavGraphProvider
}