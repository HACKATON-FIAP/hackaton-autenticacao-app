package br.com.fiap.usuarios.service;

import br.com.fiap.usuarios.api.model.UsuarioDto;
import br.com.fiap.usuarios.config.MessageConfig;
import br.com.fiap.usuarios.dados.ClienteDados;
import br.com.fiap.usuarios.domain.exception.UsuarioNaoEncontradoException;
import br.com.fiap.usuarios.domain.model.Usuario;
import br.com.fiap.usuarios.domain.repository.UsuarioRepository;
import br.com.fiap.usuarios.domain.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClienteServiceTest extends ClienteDados {

    private AutoCloseable closeable;

    @Mock
    private UsuarioRepository clienteRepository;

    @InjectMocks
    private UsuarioService clienteService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MessageConfig messageConfig;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Nested
    class buscarClientePorNome {

        @Test
        void deveBuscarClientePorNome_ComSucesso() {
            var nome = "Bruno";
            var cliente1 = criarCliente1();
            var cliente2 = criarCliente2();
            var clienteDto1 = criarClienteDto1();
            var clienteDto2 = criarClienteDto2();
            var clienteList = Arrays.asList(cliente1, cliente2);

            when(clienteRepository.findByNomeIgnoreCaseContaining(nome)).thenReturn(clienteList);
            when(modelMapper.map(cliente1, UsuarioDto.class)).thenReturn(clienteDto1);
            when(modelMapper.map(cliente2, UsuarioDto.class)).thenReturn(clienteDto2);

            var result = clienteService.buscarClientePorNome(nome);

            assertEquals(2, result.size());
            assertEquals(clienteDto1, result.get(0));
            assertEquals(clienteDto2, result.get(1));

            verify(clienteRepository, times(1)).findByNomeIgnoreCaseContaining(nome);
            verify(modelMapper, times(1)).map(cliente1, UsuarioDto.class);
            verify(modelMapper, times(1)).map(cliente2, UsuarioDto.class);
        }

        @Test
        void deveBuscarClientePorNome_SemResultados() {
            var nome = "";
            List<Usuario> clienteList = List.of();

            when(clienteRepository.findByNomeIgnoreCaseContaining(nome)).thenReturn(clienteList);

            var result = clienteService.buscarClientePorNome(nome);

            assertEquals(0, result.size());

            verify(clienteRepository, times(1)).findByNomeIgnoreCaseContaining(nome);
            verifyNoInteractions(modelMapper);
        }
    }

    @Nested
    class adicionarCliente {

        @Test
        void deveAdicionarCliente_ComSucesso() {
            var cliente = criarCliente1();
            var clienteDto = criarClienteDto1();

            when(modelMapper.map(clienteDto, Usuario.class)).thenReturn(cliente);

            clienteService.add(clienteDto);

            verify(clienteRepository, times(1)).save(cliente);
            verify(modelMapper, times(1)).map(clienteDto, Usuario.class);
        }
    }

    @Nested
    class atualizarCliente {

        @Test
        void deveAtualizarERetornarCliente_ComSucesso() {
            Long id = 1L;
            UsuarioDto clienteDto = criarClienteDto1();
            Usuario cliente = criarCliente1();

            when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
            when(modelMapper.map(clienteDto, Usuario.class)).thenReturn(cliente);
            when(clienteRepository.save(any(Usuario.class))).thenReturn(cliente);
            when(modelMapper.map(cliente, UsuarioDto.class)).thenReturn(clienteDto);

            UsuarioDto updatedClienteDto = clienteService.update(clienteDto, id);

            assertEquals(clienteDto, updatedClienteDto);
            verify(clienteRepository, times(1)).findById(id);
            verify(modelMapper, times(1)).map(clienteDto, cliente);
            verify(clienteRepository, times(1)).save(cliente);
        }

        @Test
        void atualizarClienteQueNaoExiste_DeveLancarThrowException() {
            Long id = 1L;
            UsuarioDto clienteDto = new UsuarioDto();

            when(clienteRepository.findById(id)).thenReturn(Optional.empty());
            when(messageConfig.getClienteNaoEncontrado()).thenReturn("Cliente não encontrado");

            assertThrows(UsuarioNaoEncontradoException.class, () -> clienteService.update(clienteDto, id));
            verify(clienteRepository, times(1)).findById(id);
            verifyNoMoreInteractions(modelMapper, clienteRepository);
        }
    }

    @Nested
    class deletarCliente {
        @Test
        void deveDeletarCliente_ComSucesso() {
            Long id = 1L;
            when(clienteRepository.findById(id)).thenReturn(Optional.of(new Usuario()));

            clienteService.delete(id);

            verify(clienteRepository, times(1)).findById(id);
            verify(clienteRepository, times(1)).deleteById(id);
        }

        @Test
        void deletarClienteQueNaoExiste_DeveLancarThrowException() {
            Long id = 1L;
            when(clienteRepository.findById(id)).thenReturn(Optional.empty());
            when(messageConfig.getClienteNaoEncontrado()).thenReturn("Cliente não encontrado");

            assertThrows(UsuarioNaoEncontradoException.class, () -> clienteService.delete(id));
            verify(clienteRepository, times(1)).findById(id);
            verifyNoMoreInteractions(clienteRepository);
        }
    }

    @Nested
    class buscarClientePorId {
        @Test
        void deveBuscarClientePorId_ComSucesso() {
            Long id = 1L;
            Usuario cliente = criarCliente1();
            UsuarioDto clienteDto = criarClienteDto1();

            when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
            when(modelMapper.map(cliente, UsuarioDto.class)).thenReturn(clienteDto);

            UsuarioDto resultDto = clienteService.getClienteById(id);

            assertEquals(clienteDto, resultDto);
            verify(clienteRepository, times(1)).findById(id);
            verify(modelMapper, times(1)).map(cliente, UsuarioDto.class);
        }

        @Test
        void aoBuscarClienteQueNaoExiste_DeveLancarThrowException() {
            Long id = 1L;

            when(clienteRepository.findById(id)).thenReturn(Optional.empty());
            when(messageConfig.getClienteNaoEncontrado()).thenReturn("Cliente não encontrado");

            assertThrows(UsuarioNaoEncontradoException.class, () -> clienteService.getClienteById(id));
            verify(clienteRepository, times(1)).findById(id);
            verifyNoMoreInteractions(modelMapper);
        }
    }

    @Nested
    class buscarTodosClientes {
        @Test
        void deveBuscarTodosClientes_ComSucesso() {
            Usuario cliente1 = criarCliente1();
            Usuario cliente2 = criarCliente2();
            List<Usuario> clientes = Arrays.asList(cliente1, cliente2);

            UsuarioDto clienteDto1 = criarClienteDto1();
            UsuarioDto clienteDto2 = criarClienteDto2();
            List<UsuarioDto> expectedDtos = Arrays.asList(clienteDto1, clienteDto2);

            when(clienteRepository.findAll()).thenReturn(clientes);
            when(modelMapper.map(cliente1, UsuarioDto.class)).thenReturn(clienteDto1);
            when(modelMapper.map(cliente2, UsuarioDto.class)).thenReturn(clienteDto2);

            List<UsuarioDto> resultDtos = clienteService.findAll();

            // Assert
            assertEquals(expectedDtos.size(), resultDtos.size());
            for (int i = 0; i < expectedDtos.size(); i++) {
                assertEquals(expectedDtos.get(i), resultDtos.get(i));
            }
            verify(clienteRepository, times(1)).findAll();
            verify(modelMapper, times(1)).map(cliente1, UsuarioDto.class);
            verify(modelMapper, times(1)).map(cliente2, UsuarioDto.class);
        }
    }
}
