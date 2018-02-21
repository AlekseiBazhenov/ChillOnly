package ru.modernsoft.chillonly.business.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.modernsoft.chillonly.ui.ChillNotification

class ChillOnlyMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val text = remoteMessage.notification?.body
        ChillNotification(this).createNotificationPush(text)
    }


}
