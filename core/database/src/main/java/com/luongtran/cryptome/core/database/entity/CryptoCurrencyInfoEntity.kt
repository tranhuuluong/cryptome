package com.luongtran.cryptome.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = CryptoCurrencyInfoEntity.TABLE_NAME,
    indices = [
        Index(value = [CryptoCurrencyInfoEntity.COLUMN_SYMBOL]),
        Index(value = [CryptoCurrencyInfoEntity.COLUMN_NAME]),
        Index(value = [CryptoCurrencyInfoEntity.COLUMN_TRADABLE]),
        Index(value = [CryptoCurrencyInfoEntity.COLUMN_RANK]),
    ]
)
data class CryptoCurrencyInfoEntity(
    @PrimaryKey
    @ColumnInfo(COLUMN_ID)
    val id: String,
    @ColumnInfo(COLUMN_NAME)
    val name: String? = null,
    @ColumnInfo(COLUMN_SYMBOL)
    val symbol: String? = null,
    @ColumnInfo(COLUMN_PRICE_USD)
    val priceUsd: String? = null,
    @ColumnInfo(COLUMN_CHANGE_PERCENT_24HR)
    val changePercent24Hr: String? = null,
    @ColumnInfo(COLUMN_MARKET_CAP_USD)
    val marketCapUsd: String? = null,
    @ColumnInfo(COLUMN_TRADABLE)
    val tradable: Boolean? = null,
    @ColumnInfo(COLUMN_RANK)
    val rank: Int? = null,
) {
    companion object {
        const val TABLE_NAME = "crypto_currency_info"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_SYMBOL = "symbol"
        const val COLUMN_PRICE_USD = "price_usd"
        const val COLUMN_CHANGE_PERCENT_24HR = "change_percent_24hr"
        const val COLUMN_MARKET_CAP_USD = "market_cap_usd"
        const val COLUMN_TRADABLE = "tradable"
        const val COLUMN_RANK = "rank"
    }
}