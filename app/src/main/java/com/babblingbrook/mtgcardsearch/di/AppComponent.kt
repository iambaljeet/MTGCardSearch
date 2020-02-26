package com.babblingbrook.mtgcardsearch.di

import android.content.Context
import com.babblingbrook.mtgcardsearch.MTGCardSearchApp
import com.babblingbrook.mtgcardsearch.data.DataModule
import com.babblingbrook.mtgcardsearch.ui.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        DataModule::class,
        UiModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(app: MTGCardSearchApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: MTGCardSearchApp): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
