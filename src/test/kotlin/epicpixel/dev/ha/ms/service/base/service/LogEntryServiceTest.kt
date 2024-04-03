//package epicpixel.dev.ha.ms.service.base.service
//
//import epicpixel.dev.ha.ms.service.base.document.LogEntry
//import epicpixel.dev.ha.ms.service.base.document.LogLevel
//import epicpixel.dev.ha.ms.service.base.repository.LogEntryRepository
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.ArgumentCaptor
//import org.mockito.Captor
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.MockitoAnnotations
//import java.time.Instant
//
//class LogEntryServiceTest {
//
//    @Mock private lateinit var logEntryRepository: LogEntryRepository
//    private lateinit var logEntryService: LogEntryService
//    @Captor private lateinit var logEntryCaptor: ArgumentCaptor<LogEntry>
//
//    @BeforeEach
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        logEntryService = LogEntryService(logEntryRepository)
//    }
//
//    @Test
//    fun `test alterarLog`() {
//        val logId = "logId"
//        val logEntry = LogEntry(details = "Updated details")
//        `when`(logEntryRepository.findById(logId)).thenReturn(java.util.Optional.of(logEntry))
//        logEntryService.alterarLog(logEntry, logId)
//
//        assertEquals("Updated details", logEntry.details)
//        verify(logEntryRepository, times(1)).findById(logId)
//        verify(logEntryRepository, times(1)).save(logEntryCaptor.capture())
//        assertEquals(logEntry, logEntryCaptor.value)
//    }
//
//    @Test
//    fun `test criarLog`() {
//        val logEntry = LogEntry(details = "New log details")
//        `when`(logEntryRepository.count()).thenReturn(99L)
//        `when`(logEntryRepository.findByDetails(logEntry.details)).thenReturn(null)
//        logEntryService.criarLog(logEntry)
//        verify(logEntryRepository, times(1)).save(logEntryCaptor.capture())
//        assertEquals(logEntry, logEntryCaptor.value)
//
//    }
//
//    @Test
//    fun `test excluirLog`() {
//        val logId = "logId"
//        `when`(logEntryRepository.findById(logId)).thenReturn(java.util.Optional.of(createDummyLogEntry()))
//        logEntryService.excluirLog(logId)
//        verify(logEntryRepository, times(1)).deleteById(logId)
//    }
//
//    @Test
//    fun `test validarLog`() {
//        val logEntry = LogEntry(user = null, createdAt = Instant.now(), level = null, details = "")
//        val exception = assertThrows(IllegalArgumentException::class.java) {
//            logEntryService.validarLog(logEntry)
//        }
//        assertTrue(exception.message?.contains("Campos obrigatórios não preenchidos: user, createdAt, level, details") ?: false)
//    }
//
//    private fun createDummyLogEntry(): LogEntry {
//        return LogEntry(
//            user = null,
//            createdAt = Instant.now(),
//            level = LogLevel.INFO,
//            details = "Dummy log details"
//        )
//    }
//
//}