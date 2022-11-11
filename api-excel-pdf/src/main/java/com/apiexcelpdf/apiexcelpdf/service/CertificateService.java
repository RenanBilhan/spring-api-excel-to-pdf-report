package com.apiexcelpdf.apiexcelpdf.service;

import com.apiexcelpdf.apiexcelpdf.dto.CertificateDto;
import com.apiexcelpdf.apiexcelpdf.model.CertificateModel;
import com.apiexcelpdf.apiexcelpdf.repository.CertificateRepository;
import lombok.Cleanup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections4.IteratorUtils.toList;

@Service
public class CertificateService {
    final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository){
        this.certificateRepository = certificateRepository;
    }

    @Transactional
    public CertificateModel save(CertificateModel certificateModel) {
        return certificateRepository.save(certificateModel);
    }

    public List<CertificateModel> findAll() {
        return certificateRepository.findAll();
    }

    public List<CertificateDto> create(MultipartFile excel) throws IOException {
        List<CertificateDto> certificateDtoList = new ArrayList<>();
        @Cleanup FileInputStream file = (FileInputStream) excel.getInputStream();
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<Row> rowList = toList(sheet.rowIterator());
        rowList.remove(0);

        rowList.forEach(row->{
            List<Cell> cellList = toList(row.cellIterator());

            CertificateDto certificateDto = CertificateDto.builder()
                    .name(cellList.get(0).toString())
                    .workLoad(Double.toString(cellList.get(1).getNumericCellValue()))
                    .course(cellList.get(2).toString())
                    .build();

            certificateDtoList.add(certificateDto);

        });
        return certificateDtoList;
    }
}
