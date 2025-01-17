package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import com.oficinadobrito.api.utils.GenerateBigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest()
class GenericsServicesTests {
    @Mock
    private IGenericRepository<Anuncio> repository;

    @InjectMocks
    private GenericService<Anuncio> genericService;

    private Anuncio resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource = new Anuncio();
        resource.setAnuncioId(GenerateBigInteger.generateCustomRandom());
        resource.setDescricao("Uma descrição do terreno");
        resource.setImagemPropaganda("http://hegehg.png");
        resource.setTipoPagamento("A VISTA");
        resource.setPreco(45.7);
    }

    @DisplayName("Should save the resource successfully")
    @Test
    void testSaveSuccess() {
        when(this.genericService.save(resource)).thenReturn(resource);

        Anuncio result = genericService.save(resource);

        assertNotNull(result, "The saved resource must not be null");
        assertEquals(resource, result, "The returned resource must be the same as the one that was saved");
    }

    @DisplayName("Should throw ResourceNotFoundException when searching for a non-existent resource")
    @Test
    void testFindByIdNotFound() {
        BigInteger id = resource.getAnuncioId();
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {genericService.findById(id);});

        assertEquals("The searched resource containing this id does not exist.", thrown.getMessage());
    }

    @DisplayName("Should return all resources")
    @Test
    void testFindAll() {
        when(this.genericService.findAll()).thenReturn(List.of(resource));

        Iterable<Anuncio> result = genericService.findAll();

        assertNotNull(result, "The list of resources must not be null");
        assertTrue(result.iterator().hasNext(), "There should be at least one resource in the list");
    }

    @DisplayName("Should throw ResourceNotFoundException when deleting a non-existent resource")
    @Test
    void testDeleteNotFound() {
        BigInteger id = resource.getAnuncioId();
        when(repository.findById(id)).thenReturn(Optional.of(resource));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            genericService.delete(id);
        });

        assertEquals("The searched resource containing this id does not exist.", thrown.getMessage());
    }
}
