package epicpixel.dev.ha.ms.service.base.service

import epicpixel.dev.ha.ms.service.base.document.LogEntry
import epicpixel.dev.ha.ms.service.base.repository.LogEntryRepository
import org.springframework.stereotype.Service

@Service
class LogEntryService(private val logEntryRepository: LogEntryRepository) {

    fun alterarLog(log: LogEntry, logId: String) {
        val existingLog = logEntryRepository.findById(logId)
        if (existingLog.isPresent) {
            val logToUpdate = existingLog.get()
            if (validarDetalhesLog(log)) {
                logToUpdate.details = log.details
                logEntryRepository.save(logToUpdate)
                println("Log alterado com sucesso.")
            } else {
                throw IllegalArgumentException("Detalhes do log inválidos.")
            }
        } else {
            throw IllegalArgumentException("Log não encontrado com o ID fornecido.")
        }
    }

    fun criarLog(log: LogEntry) {
        if (logEntryRepository.count() >= 100) {
            throw IllegalStateException("Não é possível adicionar o log porque excederia o limite de logs permitidos.")
        } else {
            val existingLog = logEntryRepository.findByDetails(log.details)
            if (existingLog == null) {
                logEntryRepository.save(log)
                println("Log adicionado com sucesso.")
            } else {
                throw IllegalArgumentException("Log já existe.")
            }
        }
    }

    fun excluirLog(logId: String) {
        val existingLog = logEntryRepository.findById(logId)
        if (existingLog.isPresent) {
            logEntryRepository.deleteById(logId)
            println("Log excluído com sucesso.")
        } else {
            throw IllegalArgumentException("Log não encontrado com o ID fornecido.")
        }
    }

    fun validarLog(log: LogEntry) {
        if (log.user == null || log.level == null || log.details.isEmpty()) {
            throw IllegalArgumentException("Campos obrigatórios não preenchidos: user, createdAt, level, details")
        } else {
            println("Validação bem-sucedida.")
        }
    }

    private fun validarDetalhesLog(log: LogEntry): Boolean {
        return log.details.isNotBlank()
    }
}