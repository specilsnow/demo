package com.cdutcm.core.excel;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cdutcm.core.PathConstant;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel 工具类
 *
 * @author FJL
 */
@SuppressWarnings({"unchecked", "deprecation"})
public final class ExcelUtil {

    // 测试
    // public static void main(String[] args) {
    // try {
    // List<PfRecipelItem> list = readExcel("d:\\示例模板.xlsx",
    // PfRecipelItem.class);
    // System.out.println(list.get(0).getHisId());
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    /**
     * 读取 Excel 并生成对应实体
     *
     * @param filePath 文件路径
     * @param clzss    需要生成的实体
     * @return List<T>
     * @throws Exception 可能出现的异常
     */
    public static <T> List<T> readExcel(String filePath, Class<?> clzss)
            throws Exception {
        List<T> result = new ArrayList<T>();
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        Workbook workBook = WorkbookFactory.create(inputStream);
        // Excel 中页签的个数
        int number = workBook.getNumberOfSheets();
        // 循环读取页签的内容
        for (int i = 0; i < number; i++) {
            Sheet sheet = workBook.getSheetAt(i);
            String className = sheet.getSheetName().replaceAll("[^a-zA-Z].*$",
                    "");
            // Excel 中总行数，从 0 开始
            int rowNum = sheet.getLastRowNum();
            // Excel 中总列数，从 1 开始
            int cellNum = sheet.getRow(0).getLastCellNum();
            // 当前页签的名称跟实体名称相对应，开始实例化数据
            if (clzss.getSimpleName().toLowerCase()
                    .equals(className.trim().toLowerCase())
                    || className.trim().toLowerCase()
                    .contains(clzss.getSimpleName().toLowerCase())) {
                // 循环读取每行记录
                for (int rowNumber = 0; rowNumber <= rowNum; rowNumber++) {
                    T t = (T) clzss.newInstance();
                    // 循环读取每列记录
                    for (int cellNumber = 0; cellNumber < cellNum; cellNumber++) {
                        // 获取属性
                        String property = getProperty(sheet, 0, cellNumber)
                                .replaceAll("[^a-zA-Z].*$", "");
                        if (rowNumber > 0) {
                            // 获取属性值
                            String value = getProperty(sheet, rowNumber,
                                    cellNumber);
                            ClassUtil.methodInvoke(t, property, value);
                        }
                    }
                    if (rowNumber > 0) {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }

    private static String getProperty(Sheet sheet, int rowNumber, int cellNumber) {
        String result = null;
        Cell cell = sheet.getRow(rowNumber).getCell(cellNumber);
        if (rowNumber > 0) {
            // System.out.println(cell);
            if (cell != null) {
                CellType type = cell.getCellTypeEnum();
                if (type == CellType.NUMERIC) {
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        result = sdf.format(HSSFDateUtil.getJavaDate(cell
                                .getNumericCellValue()));
                    } else {
                        result = new DecimalFormat("#").format(cell
                                .getNumericCellValue());
                    }
                } else {
                    result = cell.getStringCellValue();
                }
            } else {
                result = null;
            }
        } else {
            result = cell.getStringCellValue();
        }
        return result;
    }

    private static String saveExcel(List<T> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(String.valueOf(list.getClass()));

        HSSFRow row = sheet.createRow(0);

        for (T t : list) {

        }
        final HSSFCell cell = row.createCell(0);

        return "ok";
    }


}
