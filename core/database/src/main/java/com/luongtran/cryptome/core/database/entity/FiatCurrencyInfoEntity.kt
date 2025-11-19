package com.luongtran.cryptome.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = FiatCurrencyInfoEntity.TABLE_NAME,
    indices = [
        Index(value = [FiatCurrencyInfoEntity.COLUMN_SYMBOL]),
        Index(value = [FiatCurrencyInfoEntity.COLUMN_NAME]),
        Index(value = [FiatCurrencyInfoEntity.COLUMN_TRADABLE]),
    ]
)
data class FiatCurrencyInfoEntity(
    @PrimaryKey
    @ColumnInfo(COLUMN_ID)
    val id: String,
    @ColumnInfo(COLUMN_NAME)
    val name: String? = null,
    @ColumnInfo(COLUMN_SYMBOL)
    val symbol: String? = null,
    @ColumnInfo(COLUMN_CODE)
    val code: String? = null,
    @ColumnInfo(COLUMN_PRICE_USD)
    val priceUsd: String? = null,
    @ColumnInfo(COLUMN_TRADABLE)
    val tradable: Boolean? = null
) {
    companion object {
        const val TABLE_NAME = "fiat_currency_info"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_SYMBOL = "symbol"
        const val COLUMN_PRICE_USD = "price_usd"
        const val COLUMN_CODE = "code"
        const val COLUMN_TRADABLE = "tradable"
    }
}