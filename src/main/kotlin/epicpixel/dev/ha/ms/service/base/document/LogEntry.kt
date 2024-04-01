package epicpixel.dev.ha.ms.service.base.document

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document("logs")
data class LogEntry(
    @Id val id: ObjectId = ObjectId(),
    @DBRef val user: UserDocument? = null,
    val createdAt: Instant = Instant.now(),
    @Field("type") val level: LogLevel? = LogLevel.INFO,
    var details: String,
)

enum class LogLevel {
    INFO,
    WARNING,
    ERROR,
    DEBUG,
    SUCCESS,
}