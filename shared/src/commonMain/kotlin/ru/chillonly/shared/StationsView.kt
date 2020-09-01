package ru.chillonly.shared

data class StationsState(val data: String)

interface StationsView {
    fun showState(state: StationsState)
}