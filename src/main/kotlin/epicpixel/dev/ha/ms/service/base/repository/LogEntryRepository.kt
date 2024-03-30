package epicpixel.dev.ha.ms.service.base.repository

import epicpixel.dev.ha.ms.service.base.document.LogEntry
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LogEntryRepository : MongoRepository<LogEntry, String> {
    fun findByDetails(details: String): LogEntry?
}