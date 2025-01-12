package com.rafaelhosaka.rhv.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CloudinaryService {
    @Autowired
    private Environment environment;

    private Cloudinary getCloudinary(){
        return new Cloudinary(environment.getProperty("CLOUDINARY_URL"));
    }

    public String upload (byte[] fileBytes, String folderName, String fileName, String resourceType, String type) throws IOException {
        var cloudinary = getCloudinary();
        var map = ObjectUtils.asMap(
                "folder", "rhv/"+folderName,
                "resource_type", resourceType,
                "public_id", fileName,
                "type" , type
        );
        var result = cloudinary.uploader().upload(fileBytes, map);
        return (String) result.get("url");
    }

    public String makeAuthenticated ( String fileName, String resourceType) throws Exception {
        var cloudinary = getCloudinary();
        var map = ObjectUtils.asMap(
                "type", "upload",
                "to_type", "authenticated",
                "resource_type" , resourceType,
                "invalidate" , true
        );
        var result = cloudinary.uploader().rename("rhv/"+fileName, fileName, map);
        return (String) result.get("url");
    }

    public String makePublic ( String fileName, String resourceType) throws Exception {
        var cloudinary = getCloudinary();
        var map = ObjectUtils.asMap(
                "type", "authenticated",
                "resource_type" , resourceType,
                "to_type", "upload"
        );
        var result = cloudinary.uploader().rename("rhv/"+fileName, fileName, map);
        return (String) result.get("url");
    }

    public void delete ( String fileName, String resourceType, String type) throws IOException {
        var cloudinary = getCloudinary();
        var map = ObjectUtils.asMap(
                "resource_type" , resourceType,
                "type" , type,
                "invalidate", true
        );
        cloudinary.uploader().destroy("rhv/"+fileName,map);
    }

    public void deleteFolder ( String folderName) throws Exception {
        var cloudinary = getCloudinary();

        cloudinary.api().deleteFolder("rhv/"+folderName, ObjectUtils.emptyMap());
    }
}
