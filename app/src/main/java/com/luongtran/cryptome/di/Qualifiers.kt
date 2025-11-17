package com.luongtran.cryptome.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

object IoDispatcherQualifier : Qualifier {
    override val value: QualifierValue = "IoDispatcher"
}

object DefaultDispatcherQualifier : Qualifier {
    override val value: QualifierValue = "DefaultDispatcher"
}