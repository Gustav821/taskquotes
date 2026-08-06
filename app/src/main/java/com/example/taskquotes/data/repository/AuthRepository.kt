package com.example.taskquotes.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

/**
 * Envuelve Firebase Authentication. Si el proyecto todavía no tiene un
 * google-services.json válido, `auth` será null y los métodos devolverán
 * un error controlado en lugar de bloquear la app.
 */
class AuthRepository {

    private val auth: FirebaseAuth? = runCatching { FirebaseAuth.getInstance() }.getOrNull()

    val isFirebaseAvailable: Boolean get() = auth != null

    val currentUser: FirebaseUser?
        get() = auth?.currentUser

    suspend fun signIn(email: String, password: String): Result<FirebaseUser> = runCatching {
        val firebaseAuth = auth ?: error("Firebase no está configurado (falta google-services.json)")
        firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            ?: error("No se pudo iniciar sesión")
    }

    suspend fun signUp(email: String, password: String): Result<FirebaseUser> = runCatching {
        val firebaseAuth = auth ?: error("Firebase no está configurado (falta google-services.json)")
        firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
            ?: error("No se pudo registrar el usuario")
    }

    fun signOut() {
        auth?.signOut()
    }
}
