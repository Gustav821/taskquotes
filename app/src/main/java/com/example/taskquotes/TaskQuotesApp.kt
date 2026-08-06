package com.example.taskquotes

import android.app.Application
import com.google.firebase.FirebaseApp

class TaskQuotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Se inicializa Firebase solo si existe una configuración válida
        // (google-services.json). Si no existe, la app sigue funcionando
        // en modo local (Room) sin sincronización en la nube.
        runCatching { FirebaseApp.initializeApp(this) }
    }
}
