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

			Category category1 = new Category("Sweaters");
			Category category2 = new Category("Scarves");
			Category category3 = new Category("Socks");

			cRepository.save(category1);
			cRepository.save(category2);
			cRepository.save(category3);

			Pattern pattern1 = new Pattern("Poppy sweater", "Kayla Knitter");
			Pattern pattern2 = new Pattern("Sophie Scarf", "Petite knit");
			Pattern pattern3 = new Pattern("No pattern used", "No pattern used");

			paRepository.save(pattern1);
			paRepository.save(pattern2);
			paRepository.save(pattern3);

			// Weight weight1 = new Weight("Lace");
			// wRepository.save(weight1);

			Yarn yarn1 = new Yarn("Mohair", "Sandnes garn", "Light blue");
			Yarn yarn2 = new Yarn("Merinoull", "Sandnes garn", "Grey");
			Yarn yarn3 = new Yarn("Novita Wool", "Novita", "Red");
			Yarn yarn4 = new Yarn("Novita Wool", "Novita", "Blue");
			Yarn yarn5 = new Yarn("Novita Wool", "Novita", "Green");

			yRepository.save(yarn1);
			yRepository.save(yarn2);
			yRepository.save(yarn3);
			yRepository.save(yarn4);
			yRepository.save(yarn5);

			List<Yarn> yarns1 = new ArrayList<>();
			yarns1.add(yarn1);

			List<Yarn> yarns2 = new ArrayList<>();
			yarns2.add(yarn3);
			yarns2.add(yarn4);
			yarns2.add(yarn5);

			// Create users
			AppUser user1 = new AppUser("user", "$2a$10$Qe6osubeJbMgVFkdDkBYnOcnZheUKq7eCwvQhLQIZXcOIARB2fZra", "USER");
			AppUser user2 = new AppUser("admin", "$2a$10$qV.EUjP6EVgzYx3lHCGD0.Ots.RjbOCaSF.fHjbiv2bm82dAe6iV6",
					"ADMIN");

			auRepository.save(user1);
			auRepository.save(user2);

			pRepository
					.save(new Project("My first sweater", user1, pattern1, category1, yarns1, "36", "4mm 80 cm", null,
							"I've just started my first sweater"));

			pRepository.save(
					new Project("Colorful socks", user2, pattern3, category3, yarns2, "39", "3mm", null,
							"Basic socks"));

		};
	}

}
