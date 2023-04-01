package com.example.neighbour.service.aws;

public interface S3Service {

    /**
     * Uploads file to S3 bucket
     *
     * @param keyName    - name of the file
     * @param base64File - file in base64 format
     */
    void uploadFile(String keyName, String base64File);

    /**
     * Downloads file from S3 bucket
     *
     * @param keyName - name of the file
     */
    String generatePreSignedUrl(String keyName);

    boolean checkIfFileExists(String keyName);


}
