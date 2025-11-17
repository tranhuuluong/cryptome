package com.luongtran.cryptome.core.common.manager

import java.io.InputStream

interface AssetManager {
    fun loadAsset(fileName: String): InputStream
}