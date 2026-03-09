package com.packt.cardatabase;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest   // Uses H2, auto-rolls back each test, loads only JPA beans
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository repository;

    @Test
    @DisplayName("Save owner — should be findable by first name")
    void saveOwner() {
        // Arrange: save a new Owner
        repository.save(new Owner("Lucy", "Smith"));

        // Act + Assert: find by first name → should be present
        assertThat(repository.findByFirstname("Lucy").isPresent()).isTrue();
    }

    @Test
    @DisplayName("Delete all owners — count should be zero")
    void deleteOwners() {
        // Arrange: save an owner
        repository.save(new Owner("Lisa", "Morrison"));

        // Act: delete everything
        repository.deleteAll();

        // Assert: nothing left
        assertThat(repository.count()).isEqualTo(0);
    }
}

