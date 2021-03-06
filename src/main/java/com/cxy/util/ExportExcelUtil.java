package com.cxy.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcelUtil {

    //需序列化的方法调用
    public static byte[] serializeExport(String sheetName, String[] titleName, String[][] data) throws Exception {
        if (data.length > 65535 ) {
            data = new String[1][1];
            titleName = new String[1];
            titleName[0] = "error";
            data[0][0] = "数据超过excel 2003处理量（65536），请分次导出";
        }

        HSSFWorkbook wb = new HSSFWorkbook(); // new 一个HSSFWorkbook实例
        HSSFCellStyle setBorder = wb.createCellStyle();

        setBorder.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
        setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 边框的大小
        setBorder.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
        setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);// 边框的大小
        setBorder.setBottomBorderColor(HSSFColor.BLACK.index);
        setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        setBorder.setTopBorderColor(HSSFColor.BLACK.index);
        setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);

        HSSFSheet sheet = wb.createSheet(sheetName); // 创建一个sheet脚本
        sheet.setDefaultColumnWidth(40);
        // 创建 title
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleName.length; i++) {
            HSSFCell cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString(titleName[i]));
            HSSFFont f = wb.createFont();
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
            HSSFCellStyle style = wb.createCellStyle();
            sheet.setColumnWidth(i, 6000);
            style.setFont(f);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 边框的大小
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 边框的大小
            cell.setCellStyle(style);
        }
        if (data != null && data.length > 0) {
            for (int j = 1; j <= data.length; j++) {
                row = sheet.createRow(j);
                HSSFCell cell = null;
                for (int i = 0; i < titleName.length; i++) {
                    cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(new HSSFRichTextString(data[j - 1][i]));
                    cell.setCellStyle(setBorder);
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);

        return os.toByteArray();
    }
    //无需序列化的方法调用
    public static InputStream export(String sheetName, String[] titleName, String[][] data) throws Exception {

        byte[] bt = serializeExport(sheetName, titleName, data);

        InputStream is = new ByteArrayInputStream(bt);
        return is;
    }

}
