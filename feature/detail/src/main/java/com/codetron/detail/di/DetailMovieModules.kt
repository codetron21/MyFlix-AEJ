package com.codetron.detail.di

import com.catnip.core.base.FeatureModules
import org.koin.core.module.Module

object DetailMovieModules :FeatureModules{

    override fun getModules(): List<Module> {
        return listOf()
    }

    override val repositories: Module
        get() = TODO("Not yet implemented")

    override val viewModels: Module
        get() = TODO("Not yet implemented")

    override val dataSources: Module
        get() = TODO("Not yet implemented")

    override val useCases: Module
        get() = TODO("Not yet implemented")

    override val network: Module
        get() = TODO("Not yet implemented")
}