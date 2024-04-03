package epicpixel.dev.ha.ms.service.base.controller

import epicpixel.dev.ha.ms.service.base.document.LogEntry
import epicpixel.dev.ha.ms.service.base.service.LogEntryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/logs")
class LogEntryController(private val logEntryService: LogEntryService) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun listLogs() = logEntryService.getAllLogs()

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun listLogsId(@PathVariable id: String) = logEntryService.getLogByID(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createLog(@RequestBody logEntry: LogEntry): LogEntry {
        val logCriado = logEntryService.createLog(logEntry)
        return logCriado
    }

}