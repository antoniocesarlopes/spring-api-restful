package com.example.repository;

import static com.example.commons.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import com.example.data.Owner;
import com.example.data.enums.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
@TestMethodOrder(OrderAnnotation.class)
public class OwnerRepositoryTest {

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	@Order(1)
	public void should_find_no_owner_if_repository_is_empty() {
		List<Owner> owners = ownerRepository.findAll();

		assertThat(owners).isEmpty();
	}

	@Test
	@Order(2)
	public void should_store_a_owner() {
		Owner owner = ownerRepository.save(OWNER_1);

		assertThat(owner).hasNoNullFieldsOrPropertiesExcept("solicitations", "stages");
		assertThat(owner).hasFieldOrPropertyWithValue("name", OWNER_1.getName());
		assertThat(owner).hasFieldOrPropertyWithValue("email", OWNER_1.getEmail());
		assertThat(owner).hasFieldOrPropertyWithValue("password", OWNER_1.getPassword());
		assertThat(owner).hasFieldOrPropertyWithValue("role", OWNER_1.getRole());
	}

	@Test
	@Order(3)
	public void should_throw_exception_with_invalid_owner() {
		assertThatThrownBy(() -> ownerRepository.save(EMPTY_OWNER)).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> ownerRepository.save(INVALID_OWNER)).isInstanceOf(RuntimeException.class);
	}

	@Test
	@Order(4)
	public void should_throw_exception_with_existing_email() {
		Owner owner = testEntityManager.persist(OWNER_2);
		testEntityManager.detach(owner);
		owner.setId(null);

		assertThatThrownBy(() -> ownerRepository.save(owner)).isInstanceOf(RuntimeException.class);
	}

	@Test
	@Order(5)
	public void should_find_all_owners() {
		testEntityManager.persist(OWNER_3);

		testEntityManager.persist(OWNER_4);

		testEntityManager.persist(OWNER_5);

		List<Owner> owners = ownerRepository.findAll();

		assertThat(owners).hasSize(3).contains(OWNER_3, OWNER_4, OWNER_5);
	}

	@Test
	@Order(6)
	public void should_find_owner_by_id() {
		testEntityManager.persist(OWNER_6);

		testEntityManager.persist(OWNER_7);

		Owner foundOwner = ownerRepository.findById(OWNER_7.getId()).get();

		assertThat(foundOwner).isEqualTo(OWNER_7);
	}

	@Test
	@Order(7)
	public void should_login() {
		testEntityManager.persist(OWNER_8);

		Owner foundOwner = ownerRepository.login(OWNER_8.getEmail(), OWNER_8.getPassword()).get();

		assertThat(foundOwner).isEqualTo(OWNER_8);
		assertThat(foundOwner).hasFieldOrPropertyWithValue("name", OWNER_8.getName());
		assertThat(foundOwner).hasFieldOrPropertyWithValue("email", OWNER_8.getEmail());
		assertThat(foundOwner).hasFieldOrPropertyWithValue("password", OWNER_8.getPassword());
		assertThat(foundOwner).hasFieldOrPropertyWithValue("role", OWNER_8.getRole());
	}

	@Test
	@Order(8)
	public void should_find_by_email() {
		testEntityManager.persist(OWNER_9);

		Owner foundOwner = ownerRepository.findByEmail(OWNER_9.getEmail()).get();

		assertThat(foundOwner).isEqualTo(OWNER_9);
		assertThat(foundOwner).hasFieldOrPropertyWithValue("name", OWNER_9.getName());
		assertThat(foundOwner).hasFieldOrPropertyWithValue("email", OWNER_9.getEmail());
		assertThat(foundOwner).hasFieldOrPropertyWithValue("password", OWNER_9.getPassword());
		assertThat(foundOwner).hasFieldOrPropertyWithValue("role", OWNER_9.getRole());
	}

	@Test
	@Order(9)
	public void should_update_role() {
		testEntityManager.persist(OWNER_10);

		Owner foundOwner = testEntityManager.find(Owner.class, OWNER_10.getId());

		int affectedRows = ownerRepository.updateRole(foundOwner.getId(), Role.ADMINISTRATOR);
		testEntityManager.detach(foundOwner);
		Owner updatedOwner = testEntityManager.find(Owner.class, foundOwner.getId());

		assertThat(affectedRows).isEqualTo(1);
		assertThat(updatedOwner.getRole()).isEqualTo(Role.ADMINISTRATOR);
	}

	@Test
	@Order(10)
	public void should_update_owner_by_id() {
		testEntityManager.persist(OWNER_11);

		Owner foundOwner = testEntityManager.find(Owner.class, OWNER_11.getId());
		foundOwner.setName(UPDATED_OWNER_11.getName());
		foundOwner.setEmail(UPDATED_OWNER_11.getEmail());
		foundOwner.setPassword(UPDATED_OWNER_11.getPassword());
		foundOwner.setRole(UPDATED_OWNER_11.getRole());
		ownerRepository.save(foundOwner);

		Owner checkOwner = testEntityManager.find(Owner.class, OWNER_11.getId());

		assertThat(checkOwner.getId()).isEqualTo(OWNER_11.getId());
		assertThat(checkOwner.getName()).isEqualTo(UPDATED_OWNER_11.getName());
		assertThat(checkOwner.getEmail()).isEqualTo(UPDATED_OWNER_11.getEmail());
		assertThat(checkOwner.getPassword()).isEqualTo(UPDATED_OWNER_11.getPassword());
		assertThat(checkOwner.getRole()).isEqualTo(UPDATED_OWNER_11.getRole());
	}

	@Test
	@Order(11)
	public void should_delete_owner_by_id() {
		testEntityManager.persist(OWNER_12);

		testEntityManager.persist(OWNER_13);

		testEntityManager.persist(OWNER_14);

		ownerRepository.deleteById(OWNER_13.getId());

		Owner notFoundOwner = testEntityManager.find(Owner.class, OWNER_13.getId());

		List<Owner> owners = ownerRepository.findAll();

		assertThat(notFoundOwner).isNull();
		assertThat(owners).hasSize(2).contains(OWNER_12, OWNER_14);
	}
	
	@Test
	@Order(12)
	public void should_throw_exception_when_delete_with_invalid_owner_id() {

		assertThatThrownBy( () -> ownerRepository.deleteById(INVALID_OWNER.getId())).isInstanceOf(RuntimeException.class);
	}

	@Test
	@Order(13)
	public void should_delete_all_owners() {
		testEntityManager.persist(OWNER_15);

		testEntityManager.persist(OWNER_16);

		testEntityManager.persist(OWNER_17);

		ownerRepository.deleteAll();

		assertThat(ownerRepository.findAll()).isEmpty();
	}

}
