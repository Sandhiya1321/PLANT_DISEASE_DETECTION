package plantLeafDetection.finalYearProject;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.*;

public class ShadowRemoval {
    public static Mat removeShadow(Mat image) {
        Mat lab = new Mat();
        Imgproc.cvtColor(image, lab, Imgproc.COLOR_BGR2Lab);

        List<Mat> channels = new ArrayList<>();
        Core.split(lab, channels);

        Imgproc.createCLAHE(2.0, new Size(8, 8)).apply(channels.get(0), channels.get(0));
        Core.merge(channels, lab);

        Mat result = new Mat();
        Imgproc.cvtColor(lab, result, Imgproc.COLOR_Lab2BGR);
        return result;
    }
}
