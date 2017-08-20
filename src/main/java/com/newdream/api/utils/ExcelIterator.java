package com.newdream.api.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by qiuwei on 2017/7/20.
 */
public class ExcelIterator implements Iterator<Object[]> {

    private int curRowNum = 0;          //当前行
    private int rowNum = 0;             //测试用例数
    private Sheet sheet = null;         //工作表
    private int colNum = 0;             //参数个数
    private String[] colNames = null;   //参数名数组

    /**
     * TODO 将Excel数据读取,提供TestNG的@DataProvider使用
     * 
     * @author qiuwei
     * @dateTime 2017/7/20 22:41
     * @param  filePath excel文件路径
     *
    */
    public ExcelIterator(String filePath) throws IOException {
        readExcel(filePath);
    }

    /**
     * TODO 读取Excel文件的标题行
     * 
     * @author qiuwei
     * @dateTime 2017/7/20 22:49
     * @param  filePath excel文件路径
     * @return 
    */
    private void readExcel(String filePath) throws IOException {
        //读取Excel文件
        FileInputStream fileInputStream = new FileInputStream(filePath + ".xls");

        //读取工作空间以及工作表(工作表默认为Excel的第一个sheet)
        Workbook workbook = new HSSFWorkbook(fileInputStream);
        sheet = workbook.getSheetAt(0);

        //获取标题行
        Row titles = sheet.getRow(0);

        //获取测试用例个数
        rowNum = sheet.getPhysicalNumberOfRows();

        //获取参数个数
        colNum = titles.getPhysicalNumberOfCells();

        //将标题行的参数赋值给参数名数组
        colNames = new String[colNum];
        Iterator<Cell> itr = titles.cellIterator();
        int count = 0;
        while (itr.hasNext()) {
            Cell cell = itr.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String value = cell.getStringCellValue();
            colNames[count] = value;
            count++;
        }
        //读取标题行后,当前行+1
        this.curRowNum++;
    }


    /**
     * TODO 重写迭代器hasNext方法,用于判断是否还有下一条测试用例
     * 
     * @author qiuwei
     * @dateTime 2017/7/20 22:51
     * @return  boolean
    */
    public boolean hasNext() {
        //rowNum == 0 表示Excel为空
        //rowNum >= rowNum 表示Excel数据已读取完
        return !(rowNum == 0 || curRowNum >= rowNum);
    }

    /**
     * TODO 重写迭代器next方法,用于读取Excel下一行测试用例
     * 
     * @author qiuwei
     * @dateTime 2017/7/20 22:54
     * @return  Object[]
    */
    public Object[] next() {
        //声明Map,用于存放Excel中的数据 [标题-值] 的形式
        Map<String, String> map = new HashMap<String, String>();

        //获取当前行
        Row row = sheet.getRow(curRowNum);

        //遍历该行的每个单元格,并将标题-值存放在map中
        for (int i = 0; i < colNum; i++) {
            Cell cell = row.getCell(i);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String value = cell.getStringCellValue();
            map.put(colNames[i], value);
        }
        //调用一次next方法,当前行+1
        this.curRowNum++;

        //将map存放于object[],提供TestNG的@DataProvider使用
        Object[] objects = new Object[1];
        objects[0] = map;
        return objects;
    }

    public void remove() {

    }
}

