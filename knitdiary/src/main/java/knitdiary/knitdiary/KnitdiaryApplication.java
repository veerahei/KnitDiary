package knitdiary.knitdiary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import knitdiary.knitdiary.domain.AppUser;
import knitdiary.knitdiary.domain.AppUserRepository;
import knitdiary.knitdiary.domain.Category;
import knitdiary.knitdiary.domain.CategoryRepository;
import knitdiary.knitdiary.domain.Pattern;
import knitdiary.knitdiary.domain.PatternRepository;
import knitdiary.knitdiary.domain.Project;
import knitdiary.knitdiary.domain.ProjectRepository;
import knitdiary.knitdiary.domain.Yarn;
import knitdiary.knitdiary.domain.YarnRepository;

@SpringBootApplication
public class KnitdiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnitdiaryApplication.class, args);
	}

	// Add test data
	@Bean
	public CommandLineRunner knitDemo(ProjectRepository pRepository, CategoryRepository cRepository,
			PatternRepository paRepository, YarnRepository yRepository, AppUserRepository auRepository) {
		return (args) -> {

			Category category1 = new Category("Sweater");
			Category category2 = new Category("Scarf");

			cRepository.save(category1);
			cRepository.save(category2);

			Pattern pattern1 = new Pattern("Poppy sweater", "Kayla Knitter");
			Pattern pattern2 = new Pattern("No pattern used", "No pattern used");

			paRepository.save(pattern1);
			paRepository.save(pattern2);

			// Weight weight1 = new Weight("Lace");
			// wRepository.save(weight1);

			Yarn yarn1 = new Yarn("Mohair", "Sandnes garn", "Light blue");
			Yarn yarn2 = new Yarn("Novita Wool", "Novita", "Red");

			yRepository.save(yarn1);
			yRepository.save(yarn2);

			List<Yarn> yarns = new ArrayList<>();
			yarns.add(yarn1);

			// Create users
			AppUser user1 = new AppUser("user", "$2a$10$Qe6osubeJbMgVFkdDkBYnOcnZheUKq7eCwvQhLQIZXcOIARB2fZra", "USER");
			AppUser user2 = new AppUser("admin", "$2a$10$qV.EUjP6EVgzYx3lHCGD0.Ots.RjbOCaSF.fHjbiv2bm82dAe6iV6",
					"ADMIN");

			auRepository.save(user1);
			auRepository.save(user2);

			pRepository.save(new Project("My first sweater", user1, pattern1, category1, yarns, "36", "4mm 80 cm", null,
					"I've just started my first sweater"));

			pRepository.save(
					new Project("White socks", user2, pattern2, category2, yarns, "39", "3mm", null, "Basic socks"));

		};
	}

}
