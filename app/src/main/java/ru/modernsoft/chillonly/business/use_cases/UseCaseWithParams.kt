package ru.modernsoft.chillonly.business.use_cases

interface UseCaseWithParams<Params, Result> {
    suspend fun doWork(params: Params): Result
}