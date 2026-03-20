package plantLeafDetection.finalYearProject;

import org.opencv.core.Mat;


public class ImagePreprocessor {
    public Mat preprocess(Mat inputImage) {
        Mat noiseFree = NoiseRemoval.removeNoise(inputImage);
        Mat shadowFree = ShadowRemoval.removeShadow(noiseFree);
        return LeafSegmentation.segmentLeaf(shadowFree);
    }
}
