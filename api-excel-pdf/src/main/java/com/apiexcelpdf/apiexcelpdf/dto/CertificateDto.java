package com.apiexcelpdf.apiexcelpdf.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public class CertificateDto {

    public CertificateDto(){
        super();
    }

    public CertificateDto(String name, String workLoad, String course) {
        this.name = name;
        this.workLoad = workLoad;
        this.course = course;
    }

    @NotBlank
    private String name;

    @NotBlank
    private String workLoad;

    @NotBlank
    private String course;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkLoad() {
        return workLoad;
    }

    public void setWorkLoad(String workLoad) {
        this.workLoad = workLoad;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
