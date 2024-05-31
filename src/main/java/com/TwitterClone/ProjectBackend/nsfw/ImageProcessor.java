package com.TwitterClone.ProjectBackend.nsfw;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageProcessor {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public byte[] blurImage(byte[] imageBytes) {
        Mat src = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_UNCHANGED);
        Mat dst = new Mat(src.size(), src.type());
        Imgproc.GaussianBlur(src, dst, new Size(45, 45), 0);
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", dst, matOfByte);
        return matOfByte.toArray();
    }
}
