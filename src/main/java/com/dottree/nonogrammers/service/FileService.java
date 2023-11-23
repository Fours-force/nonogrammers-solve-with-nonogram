package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.FileDTO;
import com.dottree.nonogrammers.entity.File;
import com.dottree.nonogrammers.entity.Post;
import com.dottree.nonogrammers.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
    @Transactional
    public File InsertFile(FileDTO fileModel, Post post) {
        File file = fileModel.toEntity(post);
        return fileRepository.save(file);
    }

    public List<FileDTO> ListImage(int postId){
        List<File> files= fileRepository.findByPost_PostId(postId);
        List<FileDTO> fileDTOList=new ArrayList<>();
        for(File file : files){
            fileDTOList.add(file.toDto());
        }
        return fileDTOList;
    }
}
