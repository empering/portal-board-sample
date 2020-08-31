package kr.re.kari.portal.board;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@Configuration
public class TestConfig {

	@Bean
	public RestDocsMockMvcConfigurationCustomizer customizer() {
		return configurer -> {
			configurer.operationPreprocessors()
					.withRequestDefaults(prettyPrint())
					.withResponseDefaults(prettyPrint());

			configurer.snippets().withEncoding("UTF-8");
		};
	}
}
