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
@Table(name = "TB_FLOWFILE")
public class FlowFile extends BaseEntity {

    @Column(name = "minifi_id", nullable = false)
    private String minifiID;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_download_uri", nullable = false)
    private String fileDownloadUri;

    @Builder
    public FlowFile(String id, String minifiID, String fileName, String fileDownloadUri) {
        super(id);
        this.minifiID = minifiID;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
    }

    @Builder
    public FlowFile(String minifiID, String fileName, String fileDownloadUri) {
        this.minifiID = minifiID;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
    }

    public static FlowFile of(String minifiID, String fileName, String fileDownloadUri) {

        return new FlowFile(minifiID, fileName, fileDownloadUri);
    }

}
