package com.luongtran.cryptome.core.ui.mapper

import com.luongtran.cryptome.core.ui.R

fun getDrawableFor(currencyCode: String) = when (currencyCode.lowercase()) {
    "ada" -> R.drawable.ada
    "apt" -> R.drawable.apt
    "arb" -> R.drawable.arb
    "atom" -> R.drawable.atom
    "avax" -> R.drawable.avax
    "bch" -> R.drawable.bch
    "bnb" -> R.drawable.bnb
    "btc" -> R.drawable.btc
    "cny" -> R.drawable.cny
    "cro" -> R.drawable.cro
    "dai" -> R.drawable.dai
    "dash" -> R.drawable.dash
    "doge" -> R.drawable.doge
    "dot" -> R.drawable.dot
    "ena" -> R.drawable.ena
    "etc" -> R.drawable.etc
    "eth" -> R.drawable.eth
    "eur" -> R.drawable.eur
    "fil" -> R.drawable.fil
    "gbp" -> R.drawable.gbp
    "hbar" -> R.drawable.hbar
    "icp" -> R.drawable.icp
    "jpy" -> R.drawable.jpy
    "link" -> R.drawable.link
    "ltc" -> R.drawable.ltc
    "near" -> R.drawable.near
    "pepe" -> R.drawable.pepe
    "rub" -> R.drawable.rub
    "shib" -> R.drawable.shib
    "sol" -> R.drawable.sol
    "strk" -> R.drawable.strk
    "sui" -> R.drawable.sui
    "tao" -> R.drawable.tao
    "ton" -> R.drawable.ton
    "trx" -> R.drawable.trx
    "uni" -> R.drawable.uni
    "usd" -> R.drawable.usd
    "usdc" -> R.drawable.usdc
    "usde" -> R.drawable.usde
    "usdt" -> R.drawable.usdt
    "xlm" -> R.drawable.xlm
    "xrp" -> R.drawable.xrp
    "zec" -> R.drawable.zec
    else -> R.drawable.unknown
}