package com.bank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bank.domain.ExcelData;
import com.bank.service.ExcelDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ExcelController {
  private final ExcelDataService excelDataService;
  @GetMapping("/excel")
  public String main() { // 1
    return "excel";
  }


  @PostMapping("/excel/read")
  public String readExcel(@RequestParam("file") MultipartFile file, Model model)
      throws IOException { // 2

    List<ExcelData> dataList = new ArrayList<>();

    String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

    if (!extension.equals("xlsx") && !extension.equals("xls")) {
      throw new IOException("엑셀파일만 업로드 해주세요.");
    }

    Workbook workbook = null;

    if (extension.equals("xlsx")) {
      workbook = new XSSFWorkbook(file.getInputStream());
    } else {
      // xls 일 경우
      workbook = new HSSFWorkbook(file.getInputStream());
    }

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

    model.addAttribute("datas", dataList); // 5
    excelDataService.saveAll(dataList);
    return "excelList";

  }
}