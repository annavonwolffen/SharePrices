package com.annevonwolffen.shareprices.data

import com.annevonwolffen.shareprices.models.data.CompanyProfileResponse
import com.annevonwolffen.shareprices.models.data.QuoteResponse
import com.annevonwolffen.shareprices.models.data.SymbolSearchResponse
import io.reactivex.Maybe

/**
 * @author Terekhova Anna
 */
interface StocksApiMapper {
    fun getQuoteForTicker(ticker: String): Maybe<QuoteResponse>

    fun getCompanyProfile(ticker: String): Maybe<CompanyProfileResponse>

    fun getQuoteForTicker2(ticker: String): QuoteResponse

    fun getCompanyProfile2(ticker: String): CompanyProfileResponse

    fun searchSymbol(query: String): Maybe<SymbolSearchResponse>
}