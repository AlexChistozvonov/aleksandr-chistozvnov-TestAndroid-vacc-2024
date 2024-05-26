package com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.di

import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.locations.LocationsRepositoryImpl
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.login.LoginRepositoryImpl
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.menu.MenuRepositoryImpl
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.data.registration.RegistrationRepositoryImpl
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.locations.LocationsRepository
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.login.LoginRepository
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.menu.MenuRepository
import com.alexchistozvonov.aleksandr_chistozvnov_testandroid_vacc_2024.domain.registration.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun registrationRepository(
        registrationRepository: RegistrationRepositoryImpl
    ): RegistrationRepository

    @Singleton
    @Binds
    abstract fun loginRepository(
        loginRepository: LoginRepositoryImpl
    ): LoginRepository

    @Singleton
    @Binds
    abstract fun locationsRepository(
        locationsRepository: LocationsRepositoryImpl
    ): LocationsRepository

    @Singleton
    @Binds
    abstract fun menuRepository(
        menuRepository: MenuRepositoryImpl
    ): MenuRepository
}
