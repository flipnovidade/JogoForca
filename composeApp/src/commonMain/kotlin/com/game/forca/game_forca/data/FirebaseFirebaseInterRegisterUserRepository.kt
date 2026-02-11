package com.game.forca.game_forca.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first

class FirebaseFirebaseInterRegisterUserRepository : FirebaseInterRegisterUserRepository {
    private val usersReference = Firebase.database.reference("users")

    override suspend fun fetchFirstUser(): RegisterUserItem? {
        val snapshot = usersReference.valueEvents.first()
        val firstChild = snapshot.children.firstOrNull() ?: return null
        return firstChild.value<RegisterUserItem>()
    }

    override suspend fun saveUser(registerUserItem: RegisterUserItem): String {
        val newRef = usersReference.push()
        newRef.setValue(registerUserItem)
        return newRef.key.orEmpty()
    }
}
