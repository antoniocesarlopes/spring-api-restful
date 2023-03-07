package com.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.data.model.UploadedFileModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final AmazonS3 amazonS3;
	private final String awsRegion;
	private final String awsS3Bucket;
	
	public List<UploadedFileModel> upload(MultipartFile[] files) {
		
		List<UploadedFileModel> uploadedFiles = new ArrayList<UploadedFileModel>();
		
		for (MultipartFile file : files) {
			String originalName = file.getOriginalFilename();
			String s3FileName = getUniqueFileName(originalName);
			
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			
			try {
				PutObjectRequest request = new PutObjectRequest(awsS3Bucket, s3FileName, file.getInputStream(), metadata)
													.withCannedAcl(CannedAccessControlList.PublicRead);
				
				amazonS3.putObject(request);
				
				String location = getFileLocation(s3FileName);
				
				UploadedFileModel uploadedFileModel = new UploadedFileModel(originalName, location);
				uploadedFiles.add(uploadedFileModel);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return uploadedFiles;
	}
	
	private String getFileLocation(String fileName) {
		return "https://" + awsS3Bucket + ".s3." + awsRegion + ".amazonaws.com/" + fileName;
	}
	
	private String getUniqueFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}
}
