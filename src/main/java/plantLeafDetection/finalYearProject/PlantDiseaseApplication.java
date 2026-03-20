package plantLeafDetection.finalYearProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("plantLeafDetection.finalYearProject")
@EnableJpaRepositories("plantLeafDetection.finalYearProject.repo")

public class PlantDiseaseApplication {

    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLocally();
        SpringApplication.run(PlantDiseaseApplication.class, args);
    }
}
