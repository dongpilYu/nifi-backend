package com.hyperdata.nifi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_TEMPLATE")
public class Template extends BaseEntity {

    @Column(name = "templateId", nullable = false)
    private String templateId;

    @Column(name = "templateUrl", nullable = false)
    private String templateUrl;

    @Column(name = "templateName", nullable = false)
    private String templateName;

    @Builder
    public Template(String id, String templateId, String templateUrl, String templateName) {
        super(id);
        this.templateId = templateId;
        this.templateUrl = templateUrl;
        this.templateName = templateName;
    }

    @Builder
    public Template(String templateId, String templateUrl, String templateName) {
        this.templateId = templateId;
        this.templateUrl = templateUrl;
        this.templateName = templateName;
    }

    public static Template of(String templateId, String templateUrl, String templateName) {
        return new Template(templateId, templateUrl, templateName);
    }

}
