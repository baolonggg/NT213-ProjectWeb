package com.TwitterClone.ProjectBackend.nsfw;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.nio.file.Files;
import java.nio.file.Paths;

public class NSFWDetector {

    private byte[] graphDef;

    public NSFWDetector(String modelPath) throws Exception {
        this.graphDef = Files.readAllBytes(Paths.get(modelPath));
    }

    public float detectNSFW(byte[] imageBytes) {
        try (Graph graph = new Graph()) {
            graph.importGraphDef(graphDef);
            try (Session session = new Session(graph);
                 Tensor<String> inputTensor = Tensor.create(imageBytes, String.class);
                 Tensor<Float> result = session.runner()
                         .feed("input", inputTensor)
                         .fetch("output")
                         .run()
                         .get(0)
                         .expect(Float.class)) {

                float[][] res = new float[1][1];
                result.copyTo(res);
                return res[0][0];
            }
        }
    }
}
