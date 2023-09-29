package hr.algebra.StreamingPlatformApplicationWEB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan("hr.algebra.dal.entity")
@EnableJpaRepositories("hr.algebra.dal.dao")
@ComponentScan(basePackages = {"hr.algebra.StreamingPlatformApplicationWEB", "hr.algebra.utils", "hr.algebra.dal","hr.algebra.bll"})
public class StreamingPlatformApplicationWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamingPlatformApplicationWebApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
