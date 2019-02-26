package com.xekera.Ecommerce.di.module

import com.xekera.Ecommerce.data.rest.XekeraAPI
import com.xekera.Ecommerce.data.room.AppDatabase
import com.xekera.Ecommerce.ui.search_all_products.SearchAllProductsMVP
import com.xekera.Ecommerce.ui.search_all_products.SearchAllProductsModel
import com.xekera.Ecommerce.ui.search_all_products.SearchAllProductsPresenter
import com.xekera.Ecommerce.util.SessionManager
import com.xekera.Ecommerce.util.Utils
import dagger.Module
import dagger.Provides


@Module
class SearchAllProductsModule {

    @Provides
    fun provideSearchAllProductsPresenter(
        model: SearchAllProductsMVP.Model,
        sessionManager: SessionManager,
        utils: Utils
    ): SearchAllProductsMVP.Presenter {
        return SearchAllProductsPresenter(model, sessionManager, utils)
    }

    @Provides
    fun provideSearchAllProductsModel(
        xekeraAPI: XekeraAPI,
        appDatabase: AppDatabase,
        utils: Utils
    ): SearchAllProductsMVP.Model {
        return SearchAllProductsModel(xekeraAPI, appDatabase, utils)
    }
}