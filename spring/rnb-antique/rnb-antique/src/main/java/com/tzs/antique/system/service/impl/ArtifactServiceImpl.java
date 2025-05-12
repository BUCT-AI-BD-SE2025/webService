//package com.tzs.antique.system.service.impl;
//
//import com.tzs.antique.system.entity.Artifact;
//import com.tzs.antique.system.mapper.ArtifactMapper;
//import com.tzs.antique.system.service.ArtifactService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ArtifactServiceImpl implements ArtifactService {
//
//    @Autowired
//    private ArtifactMapper artifactMapper;
//
//    @Override
//    public List<Artifact> findAll(String search, String type, String period, String museum, String sortBy, String order) {
//        return artifactMapper.findAll(search, type, period, museum, sortBy, order);
//    }
//}
