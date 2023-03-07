package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.data.entity.Solicitation;
import com.example.data.entity.SolicitationFile;
import com.example.data.model.UploadedFileModel;
import com.example.repository.SolicitationFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitationFileService {
	
	private final SolicitationFileRepository fileRepository;
	
	private final S3Service s3Service;

	public List<SolicitationFile> upload(Long solicitationId, MultipartFile[] files) {
		 List<UploadedFileModel> uploadedFiles = s3Service.upload(files);
		 List<SolicitationFile> solicitationFiles = new ArrayList<SolicitationFile>();
		 
		 uploadedFiles.forEach(uploadedFile -> {
			 SolicitationFile file = new SolicitationFile();
			 file.setName(uploadedFile.getName());
			 file.setLocation(uploadedFile.getLocation());
			 
			 Solicitation solicitation = new Solicitation();
			 solicitation.setId(solicitationId);
			 
			 file.setSolicitation(solicitation);
			 
			 solicitationFiles.add(file);
		 });
		 
		 return fileRepository.saveAll(solicitationFiles);
	}
	
	public Page<SolicitationFile> listAllBySolicitationId(Long solicitationId, Pageable pageable) {
		return fileRepository.findAllBySolicitationId(solicitationId, pageable);
	}
}
