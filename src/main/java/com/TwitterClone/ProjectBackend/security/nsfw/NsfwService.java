package com.TwitterClone.ProjectBackend.security.nsfw;

public interface NsfwService {
    float getPrediction(byte[] imgBytes);
}