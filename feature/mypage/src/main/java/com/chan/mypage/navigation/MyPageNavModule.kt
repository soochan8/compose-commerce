package com.chan.mypage.navigation

import com.chan.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class MyPageNavModule {
    @Binds
    @IntoSet
    abstract fun bindMyPageNavGraph(graph: MyPageNavGraph): NavGraphProvider
}