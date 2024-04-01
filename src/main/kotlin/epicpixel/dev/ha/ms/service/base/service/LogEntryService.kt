package epicpixel.dev.ha.ms.service.base.service

import epicpixel.dev.ha.ms.service.base.document.LogEntry
import epicpixel.dev.ha.ms.service.base.document.LogLevel
import epicpixel.dev.ha.ms.service.base.document.UserDocument
import epicpixel.dev.ha.ms.service.base.repository.LogEntryRepository
import epicpixel.dev.ha.ms.service.base.repository.UserRepository
import org.bson.types.ObjectId
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

class LogEntryService(
    private val logEntryRepository: LogEntryRepository,
    private val userRepository: UserRepository,
) {
    fun createLog(
        user: UserDocument?,
        details: String,
        level: LogLevel,
        createdAt: Instant
    ): LogEntry {
        val now = Instant.now()

        if (createdAt.isAfter(now)) {
            throw IllegalArgumentException("A data fornecida está no futuro.")
        }

        if (details.isBlank()) {
            throw IllegalArgumentException("Detalhes em branco não são permitidos.")
        }

        if (user != null) {
            if (!userRepository.existsByEmailIgnoreCase(user.email)) {
                throw IllegalArgumentException("Usuário não encontrado.")
            }
        }

        val logEntry = LogEntry(user = user, details = details, level = level, createdAt = createdAt)
        return logEntryRepository.save(logEntry)

    }

    fun getLogByaID(id: ObjectId): LogEntry? {
        return logEntryRepository.findById(id).getOrNull()
    }

    fun getAllLogs(): List<LogEntry> {
        return logEntryRepository.findAll()
    }
}
