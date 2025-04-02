package com.rahul;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class AbstractTestContainer {

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
	
	@Test
	void canCommunicate() {
		assertThat(mysqlContainer.isCreated()).isTrue();
		assertThat(mysqlContainer.isRunning()).isTrue();
	}

}
