package com.annevonwolffen.shareprices.domain

import com.annevonwolffen.shareprices.models.presentation.StockPresentationModel
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

/**
 * @author Terekhova Anna
 */
interface StocksInteractor {

    fun getPopularStocksData(startPosition: Int, loadSize: Int): Single<List<StockPresentationModel>>

    fun getFavoriteStocksData(startPosition: Int, loadSize: Int): Single<List<StockPresentationModel>>

    fun searchSymbol(query: String): Single<List<StockPresentationModel>>

    fun setFavorite(ticker: String): Boolean

    fun getRecentSearch(): Single<List<String>>

    fun addToRecentSearch(ticker: String)

    val stocksFlow: Flow<List<StockPresentationModel>>
}