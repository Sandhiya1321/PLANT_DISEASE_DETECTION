package plantLeafDetection.finalYearProject;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class NoiseRemoval {
    public static Mat removeNoise(Mat image) {
        Mat output = new Mat();
        Imgproc.GaussianBlur(image, output, new Size(5, 5), 0);
        return output;
    }
}
