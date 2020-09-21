package kr.re.kari.portal.board.config;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class H2ServerConfiguration {

	@Bean
	@Profile("!test")
	@ConfigurationProperties("spring.datasource.hikari")
	public DataSource dataSource() throws SQLException {
		Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8089").start();
		return new HikariDataSource();
	}
}
