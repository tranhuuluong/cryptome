package com.luongtran.cryptome.core.ui.mapper

import com.luongtran.cryptome.core.ui.R
import org.junit.Assert.assertEquals
import org.junit.Test

class ResourceMapperTest {
    private val mappings = listOf(
        "ada" to R.drawable.ada,
        "ADA" to R.drawable.ada,
        "apt" to R.drawable.apt,
        "arb" to R.drawable.arb,
        "atom" to R.drawable.atom,
        "avax" to R.drawable.avax,
        "bch" to R.drawable.bch,
        "bnb" to R.drawable.bnb,
        "btc" to R.drawable.btc,
        "BTC" to R.drawable.btc,
        "cny" to R.drawable.cny,
        "cro" to R.drawable.cro,
        "dai" to R.drawable.dai,
        "dash" to R.drawable.dash,
        "doge" to R.drawable.doge,
        "dot" to R.drawable.dot,
        "ena" to R.drawable.ena,
        "etc" to R.drawable.etc,
        "eth" to R.drawable.eth,
        "eur" to R.drawable.eur,
        "fil" to R.drawable.fil,
        "gbp" to R.drawable.gbp,
        "hbar" to R.drawable.hbar,
        "icp" to R.drawable.icp,
        "jpy" to R.drawable.jpy,
        "link" to R.drawable.link,
        "ltc" to R.drawable.ltc,
        "near" to R.drawable.near,
        "pepe" to R.drawable.pepe,
        "rub" to R.drawable.rub,
        "shib" to R.drawable.shib,
        "sol" to R.drawable.sol,
        "strk" to R.drawable.strk,
        "sui" to R.drawable.sui,
        "tao" to R.drawable.tao,
        "ton" to R.drawable.ton,
        "trx" to R.drawable.trx,
        "uni" to R.drawable.uni,
        "usd" to R.drawable.usd,
        "usdc" to R.drawable.usdc,
        "usde" to R.drawable.usde,
        "usdt" to R.drawable.usdt,
        "xlm" to R.drawable.xlm,
        "xrp" to R.drawable.xrp,
        "zec" to R.drawable.zec,
        "xxx" to R.drawable.unknown,
        "" to R.drawable.unknown
    )

    @Test
    fun `maps currency codes to correct drawables`() {
        mappings.forEach { (code, expected) ->
            assertEquals("Mismatch for $code", expected, getDrawableFor(code))
        }
    }
}