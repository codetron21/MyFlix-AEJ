package com.codetron.movieinfo.di

import com.catnip.core.base.BaseModules
import com.codetron.movieinfo.presentation.ui.MovieInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object MovieInfoModules : BaseModules {

    override fun getModules(): List<Module> {
        return listOf(viewModels)
    }

    private val viewModels: Module = module {
        viewModelOf(::MovieInfoViewModel)
    }

}