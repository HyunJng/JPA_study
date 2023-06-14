package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing // Auditing 적용할 때 필수
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	/**
	 * Auditor의 수정자, 생성자에 들어가는 값 설정
	 * - 실제는 Session이나 Security에서  session정보를 가져와서 사용자 정보를 꺼내와야 함
	 * */
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
