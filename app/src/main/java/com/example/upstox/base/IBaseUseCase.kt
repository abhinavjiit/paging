package com.example.upstox.base

import kotlinx.coroutines.flow.Flow


interface IBaseUseCase<out T : Any> {
    suspend operator fun invoke(): Flow<T>
}
