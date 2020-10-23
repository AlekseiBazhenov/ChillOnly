package ru.modernsoft.chillonly.business.events

enum class EventType { // TODO: divide

    PLAYER_START,
    PLAYER_BUFFERING,
    PLAYER_ERROR,
    PLAYER_CONNECTING,
    PLAYER_STOP,
    PLAYER_PREPARED,

    START_TRACK_UPDATER,

    TRACK_CHANGED
}