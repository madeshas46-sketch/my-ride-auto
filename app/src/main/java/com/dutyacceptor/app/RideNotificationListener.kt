
package com.dutyacceptor.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.os.Build

class RideNotificationListener : NotificationListenerService() {

    companion object {
        private const val CHANNEL_ID = "duty_acceptor_alerts"
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName.lowercase()

        val extras = sbn.notification.extras
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString() ?: ""
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: ""

        val message = "$title $text $bigText"
        val lowerMessage = message.lowercase()

        val isRideApp =
            packageName.contains("rapido") ||
            packageName.contains("uber") ||
            packageName.contains("ola") ||
            lowerMessage.contains("rapido") ||
            lowerMessage.contains("uber") ||
            lowerMessage.contains("ola")

        if (!isRideApp) return

        val farePattern = Regex(
            """(?i)(?:₹|rs\.?|inr)\s*([0-9,]+(?:\.\d{1,2})?)|([0-9,]+(?:\.\d{1,2})?)\s*(?:₹|rs\.?|inr)"""
        )

        val match = farePattern.find(message) ?: return
        val fareText = match.groupValues.drop(1).firstOrNull { it.isNotEmpty() } ?: return
        val fare = fareText.replace(",", "").toDoubleOrNull() ?: return

        if (fare <= 100.0) return

        showAlert(fare)
    }

    private fun showAlert(fare: Double) {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Qualifying ride alerts",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val alert = if (fare % 1.0 == 0.0) {
            "₹${fare.toInt()} ride detected"
        } else {
            "₹$fare ride detected"
        }

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("DUTY ACCEPTOR")
                .setContentText(alert)
                .setAutoCancel(true)
                .build()
        } else {
            Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("DUTY ACCEPTOR")
                .setContentText(alert)
                .setAutoCancel(true)
                .build()
        }

        try {
            manager.notify(
                (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
                notification
            )
        } catch (_: SecurityException) {
            // Notification permission may not be enabled.
        }
    }
}
