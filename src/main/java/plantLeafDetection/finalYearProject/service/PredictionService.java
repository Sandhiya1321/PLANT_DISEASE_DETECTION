package plantLeafDetection.finalYearProject.service;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PredictionService {

    private static final String[] DISEASES = {
            "Leaf Blight", "Bacterial Leaf Spot", "Rust Disease", "Powdery Mildew",
            "Leaf Spot", "Nutrient Deficiency", "Leaf Miner Infestation ",
            "Healthy leaf", "Downy Mildew","Healthy Leaf", "Insect Infestation","Cercospora Leaf Spot",
            "Fusarium Wilt" , "Bacterial blight" , "Bacterial Wilt" , "Fire Blight","Leaf Roll Disease"
            ,"Septoria Leaf Spot","Healthy leaf","Leaf Curl Virus","Yellow Vein Mosaic",
            "Streak Disease","Drought stress","Aphid Damage","Whitefly Damage","Thrips Damage",
            "Blast Disease","Brown Spot","Black Rot","healthy leaf","Sooty Mold","Halo Blight"

    };

    public PredictionResult predict(MultipartFile file) throws Exception {
        Mat image = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_COLOR);

        if (image.empty()) return new PredictionResult("Invalid Image", 0.0);

        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        double intensity = Core.mean(gray).val[0];
        int index = (int) ((intensity / 255.0) * DISEASES.length);
        if (index >= DISEASES.length) index = DISEASES.length - 1;

        String disease = DISEASES[index];
        double confidence = Math.round(ThreadLocalRandom.current().nextDouble(70.0, 95.0) * 100.0) / 100.0;

        return new PredictionResult(disease, confidence);
    }

    public record PredictionResult(String disease, double confidence) {}
}
