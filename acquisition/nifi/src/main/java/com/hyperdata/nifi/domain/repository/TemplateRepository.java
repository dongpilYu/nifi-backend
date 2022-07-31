package com.hyperdata.nifi.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hyperdata.nifi.domain.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, String> {
    Optional<Template> findByTemplateName(String templateName);
}
