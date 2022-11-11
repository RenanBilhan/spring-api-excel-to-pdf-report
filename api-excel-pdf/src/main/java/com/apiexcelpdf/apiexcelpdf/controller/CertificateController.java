package com.apiexcelpdf.apiexcelpdf.controller;

import com.apiexcelpdf.apiexcelpdf.dto.CertificateDto;
import com.apiexcelpdf.apiexcelpdf.model.CertificateModel;
import com.apiexcelpdf.apiexcelpdf.service.CertificateService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/certificate")

public class CertificateController {

    final CertificateService certificateService;

    public CertificateController (CertificateService certificateService){
        this.certificateService = certificateService;
    }


    @PostMapping
    public ResponseEntity<Object> saveCertificate(@RequestParam @Valid MultipartFile excel) throws IOException, JRException {
        List<CertificateDto>certificateDtoList = certificateService.create( excel);
        for(int i = 0; i<certificateDtoList.size();i++ ) {
            var certificateModel = new CertificateModel();
            var certificateDto = certificateDtoList.get(i);
            BeanUtils.copyProperties(certificateDto, certificateModel);
            certificateModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
            certificateService.save(certificateModel);
            System.out.println(certificateModel.getName()+" Save successful!");

        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Success!");
    }




    @PostMapping("/pdf")
    public String generatePdf(@RequestParam @Valid MultipartFile excel) throws IOException, JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(certificateService.create(excel));
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/Certificate_Sample_Blank.jrxml"));
        HashMap<String, Object> map = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JasperExportManager.exportReportToPdfFile(report, "certificate.pdf");
        return "Sucesso!";

    }
        

    @GetMapping
    public ResponseEntity<List<CertificateModel>> getAllCertificateModel(){
        return ResponseEntity.status(HttpStatus.OK).body(certificateService.findAll());
    }



    
}
