package com.example.todolist

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.todolist.database.AppDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val database = AppDB.getInstance(context).toDoDao
        CoroutineScope(Dispatchers.IO).launch {
            val pendingIntent = PendingIntent.getActivity(context, 0, context.packageManager.getLaunchIntentForPackage(context.packageName),0)
            val list = database.loadTodoByDateTime(LocalDate.now(), LocalTime.of(LocalTime.now().hour, LocalTime.now().minute))
            if (list.isNotEmpty()) {
                for (todo in list) {
                    val notification = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id)).apply {
                        setSmallIcon(R.drawable.ic_to_do)
                        setContentTitle(todo.toDoTitle)
                        setContentIntent(pendingIntent)
                        setAutoCancel(true)
                    }.build()

                    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    manager.notify(0, notification)
                }
            }
        }

    }
}
