package epicpixel.dev.ha.ms.service.base.service

import epicpixel.dev.ha.ms.service.base.document.LogEntry
import epicpixel.dev.ha.ms.service.base.document.LogLevel
import epicpixel.dev.ha.ms.service.base.document.UserDocument
import epicpixel.dev.ha.ms.service.base.repository.LogEntryRepository
import epicpixel.dev.ha.ms.service.base.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

@Service
class LogEntryService(
    private val logEntryRepository: LogEntryRepository,
    private val userRepository: UserRepository,
) {
    fun createLog(logEntry: LogEntry): LogEntry {
        val now = Instant.now()

        if (logEntry.createdAt.isAfter(now)) {
            throw IllegalArgumentException("A data fornecida está no futuro.")
        }

        if (logEntry.details.isBlank()) {
            throw IllegalArgumentException("Detalhes em branco não são permitidos.")
        }

        if (logEntry.user != null) {
            if (!userRepository.existsByEmailIgnoreCase(logEntry.user.email)) {
                throw IllegalArgumentException("Usuário não encontrado.")
            }
        }

        val logEntry = LogEntry(
            user = logEntry.user,
            details = logEntry.details,
            level = logEntry.level,
            createdAt = logEntry.createdAt
        )
        return logEntryRepository.save(logEntry)

    }


    fun getLogByID(id: String): LogEntry? {
        return logEntryRepository.findById(id).getOrNull()
    }

    fun getAllLogs(): List<LogEntry> {
        return logEntryRepository.findAll()
    }


}
