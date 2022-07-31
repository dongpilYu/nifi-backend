package com.hyperdata.nifi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hyperdata.nifi.domain.FlowFile;

@Repository
public interface FlowFileRepository extends JpaRepository<FlowFile, String> {
    FlowFile findByMinifiID(String minifiID);

}
