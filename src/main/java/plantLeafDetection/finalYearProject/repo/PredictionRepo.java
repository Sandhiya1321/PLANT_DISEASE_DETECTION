package plantLeafDetection.finalYearProject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plantLeafDetection.finalYearProject.model.Prediction;

@Repository
public interface PredictionRepo extends JpaRepository<Prediction, Long> {}
