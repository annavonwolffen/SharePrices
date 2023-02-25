package com.annevonwolffen.shareprices.domain

import com.annevonwolffen.shareprices.data.RawDataHelper
import com.annevonwolffen.shareprices.data.StocksSharedPrefHelper
import com.annevonwolffen.shareprices.models.presentation.StockPresentationModel
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author Terekhova Anna
 */
class StocksInteractorImpl(
    private val repository: StocksRepository,
    private val converter: DomainToPresentationModelConverter,
    private val sharedPrefHelper: StocksSharedPrefHelper,
    private val rawDataHelper: RawDataHelper
) : StocksInteractor {

    override fun getPopularStocksData(startPosition: Int, loadSize: Int): Single<List<StockPresentationModel>> =
        repository.getPopularStocksData(startPosition, loadSize)
            .map { list ->
                list.map {
                    converter.convertToPresentationModel(
                        it,
                        sharedPrefHelper.isFavorite(it.ticker)
                    )
                }
            }

    override fun getFavoriteStocksData(startPosition: Int, loadSize: Int): Single<List<StockPresentationModel>> =
        repository.getFavoriteStocksData(startPosition, loadSize, sharedPrefHelper.getFavorites().toList())
            .map { list -> list.map { converter.convertToPresentationModel(it) } }

    override fun searchSymbol(query: String): Single<List<StockPresentationModel>> = repository.getStocksSearch(query)
        .map { list -> list.map { converter.convertToPresentationModel(it, sharedPrefHelper.isFavorite(it.ticker)) } }

    override fun setFavorite(ticker: String): Boolean = sharedPrefHelper.addFavorite(ticker)

    override fun getRecentSearch(): Single<List<String>> =
        Single.fromCallable { sharedPrefHelper.getRecentSearched().toList() }

    override fun addToRecentSearch(ticker: String) = sharedPrefHelper.addToRecentSearched(ticker)

    override val stocksFlow: Flow<List<StockPresentationModel>>
        get() {
            var tickers: List<String> = mutableListOf<String>().apply {
                addAll(rawDataHelper.popularTickers.map { it.symbol })
            }
            return flow {
                while (tickers.isNotEmpty()) {
                    emit(repository.getStocks(tickers.take(10).also { tickers = tickers.drop(10) }).map {
                        converter.convertToPresentationModel(it)
                    })
                    delay(5000)
                }
            }.flowOn(Dispatchers.IO)
        }
}