package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.data.entity.Solicitation;
import com.example.data.entity.SolicitationFile;
import com.example.data.model.UploadedFileModel;
import com.example.repository.SolicitationFileRepository;
//import com.springcourse.service.s3.S3Service;

@Service
public class SolicitationFileService {
	
//	@Autowired
//	private SolicitationFileRepository fileRepository;
//	
//	@Autowired
	//private S3Service s3Service;

//	public List<SolicitationFile> upload(Long solicitationId, MultipartFile[] files) {
//		 List<UploadedFileModel> uploadedFiles = s3Service.upload(files);
//		 List<SolicitationFile> solicitationFiles = new ArrayList<SolicitationFile>();
//		 
//		 uploadedFiles.forEach(uploadedFile -> {
//			 SolicitationFile file = new SolicitationFile();
//			 file.setName(uploadedFile.getName());
//			 file.setLocation(uploadedFile.getLocation());
//			 
//			 Solicitation solicitation = new Solicitation();
//			 solicitation.setId(solicitationId);
//			 
//			 file.setSolicitation(solicitation);
//			 
//			 solicitationFiles.add(file);
//		 });
//		 
//		 return fileRepository.saveAll(solicitationFiles);
//	}
//	
//	public PageModel<SolicitationFile> listAllBySolicitationId(Long solicitationId, PageRequestModel prm) {
//		Pageable pageable = prm.toSpringPageSolicitation();
//		Page<SolicitationFile> page = fileRepository.findAllBySolicitationId(solicitationId, pageable);
//		
//		PageModel<SolicitationFile> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
//
//		return pm;
//	}
}
