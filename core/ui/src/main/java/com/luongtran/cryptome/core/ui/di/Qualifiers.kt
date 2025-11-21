package com.luongtran.cryptome.core.ui.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

object PriceFormatterQualifier : Qualifier {
    override val value: QualifierValue = "PriceFormatterQualifier"
}

object PercentFormatterQualifier : Qualifier {
    override val value: QualifierValue = "PercentFormatterQualifier"
}