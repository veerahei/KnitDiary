package knitdiary.knitdiary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import knitdiary.knitdiary.domain.ProjectRepository;

@SpringBootTest
class KnitdiaryApplicationTests {

	@Autowired
	private ProjectRepository pRepository;

	@Test
	void contextLoads() {
	}

	//Test db connection
	@Test
	public void testDBConnection() {
		assertThat(pRepository).isNotNull(); // Test repository injection
		assertThat(pRepository.count()).isNotNull();
	}

}
