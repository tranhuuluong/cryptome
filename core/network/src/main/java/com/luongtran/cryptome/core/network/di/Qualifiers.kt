package com.luongtran.cryptome.core.network.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

object NetworkJsonQualifier: Qualifier {
    override val value: QualifierValue = "NetworkJson"
}