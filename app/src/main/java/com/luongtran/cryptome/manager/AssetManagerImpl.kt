package com.luongtran.cryptome.manager

import android.content.Context
import com.luongtran.cryptome.core.network.AssetManager
import java.io.InputStream

class AssetManagerImpl(
    private val applicationContext: Context,
) : AssetManager {
    override fun loadAsset(fileName: String): InputStream {
        return applicationContext.assets.open(fileName)
    }
}