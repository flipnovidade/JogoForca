package com.game.forca.game_forca.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first

class FirebaseRegisterLoginRepository : RegisterLoginRepository {
    private val usersReference = Firebase.database.reference("users")

    override suspend fun findByEmailPassword(
        email: String,
        password: String
    ): RegisterUserItem? {
        val snapshot = usersReference.valueEvents.first()
        val matched = snapshot.children.firstOrNull { child ->
            val user = child.value<RegisterUserItem>()
            user.email.trim() == email && user.password == password
        }

        val found = matched?.value<RegisterUserItem>() ?: return null
        return found.copy(idFirebase = matched.key ?: "")
    }

    override suspend fun updateUser(registerUserItem: RegisterUserItem) {
        Firebase.database.reference("users")
            .child(registerUserItem.idFirebase)
            .setValue(registerUserItem)
    }
}
