package com.bank.service;

import com.bank.domain.ExcelData;
import com.bank.domain.ExcelDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExcelDataService {
    private final ExcelDataRepository excelDataRepository;
    public int save(ExcelData excelData) {
        return excelDataRepository.save(excelData).getYear();
    }

    public List<ExcelData> saveAll(List<ExcelData> excelData) {
        return excelDataRepository.saveAll(excelData);
    }

    @Bean
    public String readExcel()
            throws IOException { // 2

        List<ExcelData> dataList = new ArrayList<>();

        Workbook workbook = null;
        FileInputStream file1 = new FileInputStream("D:\\code\\sirjeenee\\src\\main\\resources\\test_data.xlsx");

        workbook = new XSSFWorkbook(file1);

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            ExcelData data = new ExcelData();

            data.setYear((int) row.getCell(0).getNumericCellValue());
            data.setUtilizationRate(row.getCell(1).getNumericCellValue());
            data.setSmartPhone(row.getCell(2).getNumericCellValue());
            data.setDesktop(row.getCell(3).getNumericCellValue());
            data.setNotebook(row.getCell(4).getNumericCellValue());
            data.setEtc(row.getCell(5).getNumericCellValue());
            if(row.getCell(6) == null) {
                log.info("널값");
            }
            else {
                data.setSmartPad(row.getCell(6).getNumericCellValue());
            }
            dataList.add(data);
        }
        saveAll(dataList);
        return "excelList";
    }
    public List<ExcelData> findAll(){
        return excelDataRepository.findAll();
    }
}
