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

import com.example.data.entity.User;
import com.example.data.enums.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	@Order(1)
	public void should_find_no_user_if_repository_is_empty() {
		List<User> users = userRepository.findAll();

		assertThat(users).isEmpty();
	}

	@Test
	@Order(2)
	public void should_store_a_user() {
		User user = userRepository.save(USER_1);

		assertThat(user).hasNoNullFieldsOrPropertiesExcept("solicitations", "stages");
		assertThat(user).hasFieldOrPropertyWithValue("name", USER_1.getName());
		assertThat(user).hasFieldOrPropertyWithValue("email", USER_1.getEmail());
		assertThat(user).hasFieldOrPropertyWithValue("password", USER_1.getPassword());
		assertThat(user).hasFieldOrPropertyWithValue("role", USER_1.getRole());
	}

	@Test
	@Order(3)
	public void should_throw_exception_with_invalid_user() {
		assertThatThrownBy(() -> userRepository.save(EMPTY_USER)).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> userRepository.save(INVALID_USER)).isInstanceOf(RuntimeException.class);
	}

	@Test
	@Order(4)
	public void should_throw_exception_with_existing_email() {
		User user = testEntityManager.persist(USER_2);
		testEntityManager.detach(user);
		user.setId(null);

		assertThatThrownBy(() -> userRepository.save(user)).isInstanceOf(RuntimeException.class);
	}

	@Test
	@Order(5)
	public void should_find_all_users() {
		testEntityManager.persist(USER_3);

		testEntityManager.persist(USER_4);

		testEntityManager.persist(USER_5);

		List<User> users = userRepository.findAll();

		assertThat(users).hasSize(3).contains(USER_3, USER_4, USER_5);
	}

	@Test
	@Order(6)
	public void should_find_user_by_id() {
		testEntityManager.persist(USER_6);

		testEntityManager.persist(USER_7);

		User foundUser = userRepository.findById(USER_7.getId()).get();

		assertThat(foundUser).isEqualTo(USER_7);
	}

	@Test
	@Order(7)
	public void should_login() {
		testEntityManager.persist(USER_8);

		User foundUser = userRepository.login(USER_8.getEmail(), USER_8.getPassword()).get();

		assertThat(foundUser).isEqualTo(USER_8);
		assertThat(foundUser).hasFieldOrPropertyWithValue("name", USER_8.getName());
		assertThat(foundUser).hasFieldOrPropertyWithValue("email", USER_8.getEmail());
		assertThat(foundUser).hasFieldOrPropertyWithValue("password", USER_8.getPassword());
		assertThat(foundUser).hasFieldOrPropertyWithValue("role", USER_8.getRole());
	}

	@Test
	@Order(8)
	public void should_find_by_email() {
		testEntityManager.persist(USER_9);

		User foundUser = userRepository.findByEmail(USER_9.getEmail()).get();

		assertThat(foundUser).isEqualTo(USER_9);
		assertThat(foundUser).hasFieldOrPropertyWithValue("name", USER_9.getName());
		assertThat(foundUser).hasFieldOrPropertyWithValue("email", USER_9.getEmail());
		assertThat(foundUser).hasFieldOrPropertyWithValue("password", USER_9.getPassword());
		assertThat(foundUser).hasFieldOrPropertyWithValue("role", USER_9.getRole());
	}

	@Test
	@Order(9)
	public void should_update_role() {
		testEntityManager.persist(USER_10);

		User foundUser = testEntityManager.find(User.class, USER_10.getId());

		int affectedRows = userRepository.updateRole(foundUser.getId(), Role.ADMINISTRATOR);
		testEntityManager.detach(foundUser);
		User updatedUser = testEntityManager.find(User.class, foundUser.getId());

		assertThat(affectedRows).isEqualTo(1);
		assertThat(updatedUser.getRole()).isEqualTo(Role.ADMINISTRATOR);
	}

	@Test
	@Order(10)
	public void should_update_user_by_id() {
		testEntityManager.persist(USER_11);

		User foundUser = testEntityManager.find(User.class, USER_11.getId());
		foundUser.setName(UPDATED_USER_11.getName());
		foundUser.setEmail(UPDATED_USER_11.getEmail());
		foundUser.setPassword(UPDATED_USER_11.getPassword());
		foundUser.setRole(UPDATED_USER_11.getRole());
		userRepository.save(foundUser);

		User checkUser = testEntityManager.find(User.class, USER_11.getId());

		assertThat(checkUser.getId()).isEqualTo(USER_11.getId());
		assertThat(checkUser.getName()).isEqualTo(UPDATED_USER_11.getName());
		assertThat(checkUser.getEmail()).isEqualTo(UPDATED_USER_11.getEmail());
		assertThat(checkUser.getPassword()).isEqualTo(UPDATED_USER_11.getPassword());
		assertThat(checkUser.getRole()).isEqualTo(UPDATED_USER_11.getRole());
	}

	@Test
	@Order(11)
	public void should_delete_user_by_id() {
		testEntityManager.persist(USER_12);

		testEntityManager.persist(USER_13);

		testEntityManager.persist(USER_14);

		userRepository.deleteById(USER_13.getId());

		User notFoundUser = testEntityManager.find(User.class, USER_13.getId());

		List<User> users = userRepository.findAll();

		assertThat(notFoundUser).isNull();
		assertThat(users).hasSize(2).contains(USER_12, USER_14);
	}
	
	@Test
	@Order(12)
	public void should_throw_exception_when_delete_with_invalid_user_id() {

		assertThatThrownBy( () -> userRepository.deleteById(INVALID_USER.getId())).isInstanceOf(RuntimeException.class);
	}

	@Test
	@Order(13)
	public void should_delete_all_users() {
		testEntityManager.persist(USER_15);

		testEntityManager.persist(USER_16);

		testEntityManager.persist(USER_17);

		userRepository.deleteAll();

		assertThat(userRepository.findAll()).isEmpty();
	}

}
