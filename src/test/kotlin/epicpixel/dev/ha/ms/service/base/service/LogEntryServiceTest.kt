package epicpixel.dev.ha.ms.service.base.service

import epicpixel.dev.ha.ms.service.base.document.LogEntry
import epicpixel.dev.ha.ms.service.base.document.LogLevel
import epicpixel.dev.ha.ms.service.base.document.UserDocument
import epicpixel.dev.ha.ms.service.base.repository.LogEntryRepository
import epicpixel.dev.ha.ms.service.base.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class LogEntryServiceTest {

    @Mock
    private lateinit var logEntryRepository: LogEntryRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var logEntryService: LogEntryService


    //Este teste está verificando se o método createLog da classe LogEntryService lança uma exceção
    //quando tentamos criar um log com uma data futura
    @Test
    fun `test create log with future date`() {
        // Cria uma data que está 1 hora no futuro
        val futureDate = Instant.now().plusSeconds(3600)

        // Cria uma nova instância de LogEntry com a data futura
        val logEntry = LogEntry(details = "Test log", createdAt = futureDate)

        // Espera que uma exceção seja lançada quando tentamos criar um log com uma data futura
        val exception = assertThrows(IllegalArgumentException::class.java) {
            // Tenta criar um log com a data futura
            logEntryService.createLog(logEntry)
        }

        // Verifica se a mensagem da exceção é a esperada
        assertEquals("A data fornecida está no futuro.", exception.message)
    }

    //Este teste está verificando se o método createLog da classe LogEntryService lança uma exceção
    // quando tentamos criar um log com detalhes em branco
    @Test
    fun `test create log with blank details`() {
        // Cria uma nova instância em branco
        val logEntry = LogEntry(details = "", level = LogLevel.INFO)

        // Espera que uma exceção seja lançada quando tentamos criar um log com detalhes em branco
        val exception = assertThrows(IllegalArgumentException::class.java) {
            logEntryService.createLog(logEntry)
        }
        // Verifica se a mensagem da exceção é a esperada
        assertEquals("Detalhes em branco não são permitidos.", exception.message)
    }

    /*Esse teste foi projetado para falhar. Ele está testando um cenário em que você tenta criar
    um log com um usuário que não existe no repositório. O método createLog na classe LogEntryService
    deve lançar uma exceção
    */
    @Test
    fun `test create log with non-existing user`() {
        // Cria uma nova instância de UserDocument para um usuário que não existe
        val nonExistingUser = UserDocument(
            email = "nonexisting@example.com", password = "password",
            name = "Non Existing", roles = emptyList()
        )
        // Cria uma nova instância de LogEntry com o usuário não existente
        val logEntry = LogEntry(user = nonExistingUser, details = "Test log")

        // Configura o mock do userRepository para retornar false quando o método existsByEmailIgnoreCase for chamado
        `when`(userRepository.existsByEmailIgnoreCase(anyString())).thenReturn(false)

        // Espera que uma exceção seja lançada quando tentamos criar um log com um usuário não existente
        val exception = assertThrows(IllegalArgumentException::class.java) {
            logEntryService.createLog(logEntry)
        }
        // Verifica se a mensagem da exceção é a esperada
        assertEquals("Usuário não encontrado.", exception.message)
    }
}
