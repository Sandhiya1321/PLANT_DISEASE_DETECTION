package plantLeafDetection.finalYearProject;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.*;

public class LeafSegmentation {

    public static Mat segmentLeaf(Mat image) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

        Scalar lower = new Scalar(20, 40, 40);
        Scalar upper = new Scalar(90, 255, 255);

        Mat mask = new Mat();
        Core.inRange(hsv, lower, upper, mask);

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, kernel);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        if (contours.isEmpty()) return image;

        double maxArea = 0;
        MatOfPoint largest = contours.get(0);
        for (MatOfPoint c : contours) {
            double area = Imgproc.contourArea(c);
            if (area > maxArea) { maxArea = area; largest = c; }
        }

        Rect bbox = Imgproc.boundingRect(largest);
        return new Mat(image, bbox);
    }
}
