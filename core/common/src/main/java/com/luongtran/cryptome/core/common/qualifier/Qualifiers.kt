package com.luongtran.cryptome.core.common.qualifier

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

object IoDispatcherQualifier : Qualifier {
    override val value: QualifierValue = "IoDispatcher"
}

object DefaultDispatcherQualifier : Qualifier {
    override val value: QualifierValue = "DefaultDispatcher"
}