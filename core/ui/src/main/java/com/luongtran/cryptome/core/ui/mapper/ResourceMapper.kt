package com.luongtran.cryptome.core.ui.mapper

import com.luongtran.cryptome.core.ui.R

fun getDrawableFor(currencyCode: String) = when (currencyCode.lowercase()) {
    "ada" -> R.drawable.ada
    "avax" -> R.drawable.avax
    "bch" -> R.drawable.bch
    "bnb" -> R.drawable.bnb
    "btc" -> R.drawable.btc
    "cny" -> R.drawable.cny
    "cro" -> R.drawable.cro
    "doge" -> R.drawable.doge
    "eth" -> R.drawable.eth
    "eur" -> R.drawable.eur
    "gbp" -> R.drawable.gbp
    "jpy" -> R.drawable.jpy
    "rub" -> R.drawable.rub
    "sol" -> R.drawable.sol
    "trx" -> R.drawable.trx
    "uni" -> R.drawable.uni
    "usd" -> R.drawable.usd
    "usdc" -> R.drawable.usdc
    "usdt" -> R.drawable.usdt
    "xrp" -> R.drawable.xrp
    else -> -1
}