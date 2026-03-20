package plantLeafDetection.finalYearProject.controller;

import plantLeafDetection.finalYearProject.service.PredictionService;
import plantLeafDetection.finalYearProject.model.Prediction;
import plantLeafDetection.finalYearProject.repo.PredictionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DiseaseController {

    @Autowired
    private PredictionService mlService;

    @Autowired
    private PredictionRepo repository;

    @PostMapping("/analyze")
    public Map<String, Object> analyze(@RequestParam("plantName") String plantName,
                                       @RequestParam("image") MultipartFile image) throws Exception {

        PredictionService.PredictionResult result = mlService.predict(image);

        Prediction p = new Prediction();
        p.setPlantName(plantName);
        p.setDisease(result.disease());
        p.setConfidence(result.confidence());

        repository.save(p);

        Map<String, Object> response = new HashMap<>();
        response.put("plant", plantName);
        response.put("disease", result.disease());
        response.put("confidence", result.confidence());
        return response;
    }
}
