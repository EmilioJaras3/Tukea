package org.devquality.trukea.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "do4nedzix",
                "api_key", "386477793112854",
                "api_secret", "KporUuSxHWKFRqBCw-7FVZw63oA"
        ));
    }

    // Método para subir imagen desde archivo
    public String subirImagen(File archivo) throws IOException {
        Map resultado = cloudinary.uploader().upload(archivo, ObjectUtils.emptyMap());
        return resultado.get("secure_url").toString();
    }

    // Método para subir imagen desde InputStream (usado en el controller)
    public String subirImagen(InputStream inputStream) throws IOException {
        Map resultado = cloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap());
        return resultado.get("secure_url").toString();
    }
}
