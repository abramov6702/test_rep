package ru.abramon;

import org.apache.commons.io.FilenameUtils;
import javax.imageio.ImageIO;
import javax.management.InstanceAlreadyExistsException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.json.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.lang3.*;
import java.util.*;
import java.util.logging.Logger;

public class Main {
        Logger lgr = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, JSONException {
        Path path = Paths.get("C:\\temp\\1111.jpg");
        imageAddHigh(Files.readAllBytes(path), 200, "jpg");
        //InputStream is = new FileInputStream(path.toString());


        //Date date = strToDate("2023-04-20 14:52:36.35");
//        String dateStr = "2023-04-20 14:52:36.100";
//        System.out.println("seze = "+getSizeMillis(dateStr));
//        Date date = strToDate(dateStr);
//        System.out.println("print = "+date);
//        System.out.println("!!!dateToStr = "+dateToStr(date));
//        Path path = Path.of("C:\\temp\\NRT.xlsx");
//        readExcelXSS(path);
//        Path path2 = Path.of("C:\\temp\\NRT_old.xls");
//        readExcelHSS(path2);
    }

    private static String readExcelHSS(Path path) throws FileNotFoundException, JSONException {
        InputStream is = new FileInputStream(path.toString());
        JSONArray jsnItems = new JSONArray();
        try {
            HSSFWorkbook wb = new HSSFWorkbook(is);
            int columnsCount;
            int sheetNum;
            if (wb.getNumberOfSheets() > 1) {
                columnsCount = 2;
                sheetNum = 1;
            } else {
                columnsCount = 3;
                sheetNum = 0;
            }
            HSSFSheet sh = wb.getSheetAt(sheetNum);
            Iterator<Row> iterator = sh.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                JSONObject jsnRow = new JSONObject();
                for (int j = 0; j < columnsCount; j++) {
                    HSSFCell cell = (HSSFCell) row.getCell(j);
                    String f = "";
                    if (cell != null) {
                        CellType cellType = cell.getCellType();
                        f = getCellValueAsString(cell, cellType);
                    }
                    jsnRow.put("f" + (j + 1), f);
                }
                jsnItems.put(jsnRow);
            }
            System.out.println("!!!jsn hss = "+jsnItems);
        } catch (IOException e) {
            System.out.println("!!!Error = "+ e.getMessage());
            e.printStackTrace();
        }
        return jsnItems.toString();
    }


    private static String readExcelXSS(Path path) throws FileNotFoundException, JSONException {
        InputStream is = new FileInputStream(path.toString());
        JSONArray jsnItems = new JSONArray();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(is);
            int columnsCount;
            int sheetNum;
            if (wb.getNumberOfSheets() > 1) {
                columnsCount = 2;
                sheetNum = 1;
            } else {
                columnsCount = 3;
                sheetNum = 0;
            }
            XSSFSheet sh = wb.getSheetAt(sheetNum);
            Iterator<Row> iterator = sh.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                JSONObject jsnRow = new JSONObject();
                for (int j = 0; j < columnsCount; j++) {
                    XSSFCell cell = (XSSFCell) row.getCell(j);
                    String f = "";
                    if (cell != null) {
                        CellType cellType = cell.getCellType();
                        f = getCellValueAsString(cell, cellType);
                    }
                    jsnRow.put("f" + (j + 1), f);
                }
                jsnItems.put(jsnRow);
            }
            System.out.println("!!!jsn xss = "+jsnItems);
        } catch (IOException e) {
            System.out.println("!!!Error = "+ e.getMessage());
            e.printStackTrace();
        }
        return jsnItems.toString();
    }

    private static String getCellValueAsString(Cell cell, CellType cellType) {
        switch (cellType) {
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return String.valueOf(cell.getCachedFormulaResultType());
            case STRING:
                return cell.getStringCellValue();
            default:
                return StringUtils.EMPTY;
        }
    }


    private static Date strToDate(String dateStr) throws ParseException {
        Instant inst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateStr).toInstant();
        System.out.println("!!!inst = "+inst.toString());
        return Date.from(inst) ;//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateStr);
    }

    private static String dateToStr(Date date) {
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.").format(date);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z").format(date);
    }

    private static int getSizeMillis(String dateStr) {
        int i = dateStr.indexOf(".");
        if (i > -1) {
            if (dateStr.length() > i + 1) {
                return dateStr.substring(i + 1).length();
            } else {
                return -1;
            }
        }
        return 0;
    }

    private static byte[] imageAddHigh(byte[] originalBytes, int addHigh, String ext) throws IOException {
        BufferedImage ORIGINAL;
        BufferedImage ALTERED;
//        try {
        System.out.println("!!!"+originalBytes.length);
            InputStream is = new ByteArrayInputStream(originalBytes);
            ORIGINAL = ImageIO.read(is);
        //System.out.println("!!!"+ORIGINAL.);
            ALTERED = createImage(
                    ORIGINAL.getWidth(),
                    ORIGINAL.getHeight() +
                            addHigh, Color.WHITE);
            Graphics2D g2 = ALTERED.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, ALTERED.getWidth(), addHigh);
            g2.drawImage(ORIGINAL, 0, addHigh, null);
            g2.dispose();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(ALTERED, ext, baos);
            return baos.toByteArray();
//        } catch(Exception ignore) { //из-за heic временно
//            return null;
//        }
    }

    private static BufferedImage createImage(int width, int height, Color color) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = image.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.dispose();
        return image;
    }

//    private BufferedImage convertJPGToPng(byte[] originalImage) {
//        BufferedImage newImage = new BufferedImage( pngImage.getWidth(), pngImage.getHeight(), BufferedImage.TYPE_INT_RGB);
//        newImage.createGraphics().drawImage( pngImage, 0, 0, Color.WHITE, null);
//        return image;
//    }

    private static String turnFIO(String fio) {
        if (fio == null) return "";
        if (fio.trim().isEmpty()) return "";
        String resultFIO = "";
        String clearFIO = clearFIO(fio);
        String[] fioArr = clearFIO.split(" ");
        if (fioArr.length >= 3)  resultFIO = fioArr[2] + " " + fioArr[0] + " " + fioArr[1];
        if (fioArr.length == 2)  resultFIO = fioArr[1] + " " + fioArr[0];
        if (fioArr.length == 1)  resultFIO = fioArr[0];
        return resultFIO;
    }

    private static String clearFIO(String fio) {
        Assist.replaceAllByRegex(fio, "\\p{Cntrl}");
        String clearFIO = fio.replaceAll("  ", " ");
        while (clearFIO.contains("  ")) {
            clearFIO = clearFIO.replaceAll("  ", " ");
        }
        return clearFIO.trim();
    }


}
