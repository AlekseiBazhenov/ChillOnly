package ru.modernsoft.chillonly.business.use_cases

interface UseCaseNoParams<Result> {
    suspend fun doWork(): Result
}