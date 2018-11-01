package org.sopt.server.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by ds on 2018-10-24.
 */

@Component
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public S3Service(final AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    //S3에 파일을 업로드한다.
    public void uploadOnS3(String fileName, File file) {

        TransferManager transferManager = new TransferManager(this.amazonS3Client);
        PutObjectRequest request = new PutObjectRequest(bucket, fileName, file);
        Upload upload = transferManager.upload(request);

        try {
            upload.waitForCompletion();
        } catch (AmazonClientException amazonClientException) {
            amazonClientException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
