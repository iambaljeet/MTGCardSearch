package com.babblingbrook.mtgcardsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.babblingbrook.mtgcardsearch.di.ViewModelKey
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import com.babblingbrook.mtgcardsearch.ui.detail.DetailFragment
import com.babblingbrook.mtgcardsearch.ui.detail.DetailViewModel
import com.babblingbrook.mtgcardsearch.ui.favorites.FavoritesFragment
import com.babblingbrook.mtgcardsearch.ui.favorites.FavoritesViewModel
import com.babblingbrook.mtgcardsearch.ui.search.SearchViewModel
import com.babblingbrook.mtgcardsearch.ui.search.SearchFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module(
    includes = [UiModule.ProvideViewModel::class,
        UiModule.ProvideViewModelFactory::class]
)
abstract class UiModule {

    @ContributesAndroidInjector(modules = [InjectIntoFragment::class])
    abstract fun bindSearchFragment(): SearchFragment

    @ContributesAndroidInjector(modules = [InjectIntoFragment::class])
    abstract fun bindDetailFragment(): DetailFragment

    @ContributesAndroidInjector(modules = [InjectIntoFragment::class])
    abstract fun bindFavoritesFragment(): FavoritesFragment

    @Module
    class ProvideViewModelFactory {
        @Provides
        fun provideViewModelFactory(
            providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory =
            ViewModelFactory(providers)
    }

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(SearchViewModel::class)
        fun provideSearchViewModel(scryfallRepository: ScryfallRepository): ViewModel =
            SearchViewModel(
                scryfallRepository
            )

        @Provides
        @IntoMap
        @ViewModelKey(DetailViewModel::class)
        fun provideDetailViewModel(scryfallRepository: ScryfallRepository): ViewModel =
            DetailViewModel(scryfallRepository)

        @Provides
        @IntoMap
        @ViewModelKey(FavoritesViewModel::class)
        fun provideFavoritesViewModel(scryfallRepository: ScryfallRepository): ViewModel =
            FavoritesViewModel(scryfallRepository)
    }

    @Module
    class InjectIntoFragment {
        @Provides
        fun provideSearchViewModel(
            factory: ViewModelProvider.Factory,
            target: SearchFragment
        ): SearchViewModel =
            ViewModelProvider(target, factory).get(SearchViewModel::class.java)

        @Provides
        fun provideDetailViewModel(
            factory: ViewModelProvider.Factory,
            target: DetailFragment
        ): DetailViewModel =
            ViewModelProvider(target, factory).get(DetailViewModel::class.java)

        @Provides
        fun provideFavoritesViewModel(
            factory: ViewModelProvider.Factory,
            target: FavoritesFragment
        ): FavoritesViewModel =
            ViewModelProvider(target, factory).get(FavoritesViewModel::class.java)
    }
}