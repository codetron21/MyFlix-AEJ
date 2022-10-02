package com.catnip.profile.di

import com.catnip.core.base.FeatureModules
import com.catnip.profile.data.network.datasources.UpdateProfileDataSource
import com.catnip.profile.data.network.datasources.UpdateProfileDataSourceImpl
import com.catnip.profile.data.network.service.ProfileFeatureService
import com.catnip.profile.data.repository.UpdateProfileRepository
import com.catnip.profile.data.repository.UpdateProfileRepositoryImpl
import com.catnip.profile.domain.CheckProfileFieldUseCase
import com.catnip.profile.domain.LogoutUserUseCase
import com.catnip.profile.domain.SaveProfileDataUseCase
import com.catnip.profile.domain.UpdateProfileUseCase
import com.catnip.profile.presentation.ui.profile.ProfileViewModel
import com.catnip.shared.data.remote.NetworkClient
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object ProfileModules : FeatureModules {
    override fun getModules(): List<Module> =
        listOf(repositories, viewModels, dataSources, useCases, network)

    override val repositories: Module = module {
        single<UpdateProfileRepository> { UpdateProfileRepositoryImpl(get()) }
    }

    override val viewModels: Module = module {
        viewModelOf(::ProfileViewModel)
    }

    override val dataSources: Module = module {
        single<UpdateProfileDataSource> { UpdateProfileDataSourceImpl(get()) }
    }

    override val useCases: Module = module {
        single { CheckProfileFieldUseCase(Dispatchers.IO) }
        single { SaveProfileDataUseCase(get(), Dispatchers.IO) }
        single { UpdateProfileUseCase(get(), get(), get(), Dispatchers.IO) }
        single { LogoutUserUseCase(get(), Dispatchers.IO) }
    }

    override val network: Module = module {
        single<ProfileFeatureService> { get<NetworkClient>().create() }
    }
}