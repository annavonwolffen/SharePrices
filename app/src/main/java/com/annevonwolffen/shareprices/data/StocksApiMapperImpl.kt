package com.annevonwolffen.shareprices.data

import com.annevonwolffen.shareprices.models.data.CompanyProfileResponse
import com.annevonwolffen.shareprices.models.data.QuoteResponse
import com.annevonwolffen.shareprices.models.data.SymbolSearchResponse
import io.reactivex.Maybe

/**
 * @author Terekhova Anna
 */
class StocksApiMapperImpl(private val stocksService: StocksDataService) : StocksApiMapper {

    override fun getQuoteForTicker(ticker: String): Maybe<QuoteResponse> {
        return stocksService.getQuoteForTicker(ticker)
    }

    override fun getCompanyProfile(ticker: String): Maybe<CompanyProfileResponse> {
        return stocksService.getCompanyProfile(ticker)
    }

    override fun getQuoteForTicker2(ticker: String): QuoteResponse {
        return stocksService.getQuoteForTicker2(ticker)
    }

    override fun getCompanyProfile2(ticker: String): CompanyProfileResponse {
        return stocksService.getCompanyProfile2(ticker)
    }

    override fun searchSymbol(query: String): Maybe<SymbolSearchResponse> {
        return stocksService.searchSymbol(query)
    }
}