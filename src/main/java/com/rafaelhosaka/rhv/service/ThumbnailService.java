package com.rafaelhosaka.rhv.service;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ThumbnailService {
    public File saveLocalFile(MultipartFile file) throws IOException {
        // Define the directory where the file will be saved
        String workingDirectory = System.getProperty("user.dir");

        // Create a relative directory for videos
        File directory = new File(workingDirectory, "videos/");

        // Ensure the directory exists, create it if it doesn't
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Failed to create directory: " + directory.getAbsolutePath());
            }
        }

        // Create the destination file
        File destFile = new File(directory, file.getOriginalFilename());

        // Save the uploaded file to the destination
        file.transferTo(destFile);

        return destFile;
    }

    public byte[] extractFrameFromVideo(MultipartFile multipartfile, String timestamp) throws IOException {
        File file = saveLocalFile(multipartfile);
        String videoFilePath = file.getAbsolutePath();

        try (FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videoFilePath)) {
            frameGrabber.start();

            // Convert the timestamp to seconds
            double seconds = convertTimestampToSeconds(timestamp);

            // Seek to the specified timestamp (in seconds)
            frameGrabber.setFrameNumber((int) (seconds * frameGrabber.getFrameRate()));

            // Grab the frame at the timestamp
            Frame frame = frameGrabber.grabImage();
            if (frame != null) {
                // Convert the frame to a BufferedImage
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bufferedImage = converter.convert(frame);

                // Write the image to a ByteArrayOutputStream
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(bufferedImage, "jpg", baos);
                    return baos.toByteArray();
                }
            }

            frameGrabber.stop();
        } catch (Exception e) {
            throw new IOException("Error while extracting frame: " + e.getMessage());
        } finally {
            // Ensure the file is deleted
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.err.println("Failed to delete temporary file: " + videoFilePath);
                }
            }
        }

        return null; // Return null if the operation fails
    }


    // Convert timestamp from HH:MM:SS format to seconds
    private double convertTimestampToSeconds(String timestamp) {
        String[] parts = timestamp.split(":");
        double seconds = 0;
        if (parts.length == 3) {
            seconds += Integer.parseInt(parts[0]) * 3600; // Hours
            seconds += Integer.parseInt(parts[1]) * 60;   // Minutes
            seconds += Integer.parseInt(parts[2]);        // Seconds
        }
        return seconds;
    }
}
