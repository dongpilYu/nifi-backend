package com.hyperdata.nifi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hyperdata.nifi.domain.NiFi;

@Repository
public interface NiFiRepository extends JpaRepository<NiFi, String> {
}
