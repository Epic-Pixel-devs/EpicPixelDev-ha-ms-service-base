package dev.epicpixel.ha.ms.service.base.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant


@Document("RegisterUser")
data class RegisterUser(
    @Id val id: ObjectId = ObjectId(),
    var name: String,
    var email: String,
    val gender: Char,
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate val updatedAt: Instant = Instant.now(),
    @DBRef val address: AdressDocument? = null,
    val cpf: String,
    var phone: String
)


