package epicpixel.dev.ha.ms.service.base.repository

import epicpixel.dev.ha.ms.service.base.document.UserDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserDocument, String> {
    fun existsByEmailIgnoreCase(email: String): Boolean
}
