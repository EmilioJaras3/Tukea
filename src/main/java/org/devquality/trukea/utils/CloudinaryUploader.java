package org.devquality.trukea.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryUploader {

    private final Cloudinary cloudinary;

    public CloudinaryUploader() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "do4nedzix",
                "api_key", "386477793112854",
                "api_secret", "KporUuSxHWKFRqBCw-7FVZw63oA"
        ));
    }

    public String uploadFile(File file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return uploadResult.get("secure_url").toString();
    }
}
