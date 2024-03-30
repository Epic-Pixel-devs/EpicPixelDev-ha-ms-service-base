package epicpixel.dev.ha.ms.service.base.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("users")
data class UserDocument(
        @Id
        val id: ObjectId = ObjectId(),
        val email: String,
        val password: String,
        val name: String,
        val roles: List<String>,
        val createdDate: LocalDateTime = LocalDateTime.now(),
        val modifiedDate: LocalDateTime = LocalDateTime.now()
)