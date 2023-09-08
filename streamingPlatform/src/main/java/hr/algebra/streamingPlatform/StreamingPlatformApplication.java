package hr.algebra.streamingPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
Enable JPA Repositories: Use the @EnableJpaRepositories annotation to specify the package where your repositories are located.
@EnableJpaRepositories("hr.algebra.dal.dao")

Need to make sure that JPA entities are being scanned. I can customize this with the @EntityScan annotation
@EntityScan("hr.algebra.dal.entity")
*/

@SpringBootApplication
@EntityScan("hr.algebra.dal.entity")
@EnableJpaRepositories("hr.algebra.dal.dao")
@ComponentScan(basePackages = {"hr.algebra.streamingPlatform", "hr.algebra.utils", "hr.algebra.dal","hr.algebra.bll"})
public class StreamingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingPlatformApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
