/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eleanalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.Workbook;
/**
 * Packages a group of Sample files
 * @author Yan
 */

public class SampleLibrary implements Serializable {
    private final static long serialVersionUID = 23423579015L;
    
   //private File file= new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\library\\main.dat");;
    private List<Sample> array;
    /*public static String reportList[] = {"Na2O", "MgO", "Al2O3", "SiO2", "P2O4", "S", "Cl", 
        "K2O", "CaO", "TiO2", "V2O5", "Cr2O3", "MnO", "Fe2O3", "Co3O4", "Ni2O3", "CuO", "ZnO",
        "As2O3", "Br", "SrO", "Y2O3", "ZrO2", "Nb2O5", "MoO3", "Rh2O3", "PdO", "Ag2O", "CdO",
        "SnO", "BaO", "WO3", "PtO2", "Au2O3", "PbO", "Bi2O3", "F"};
    */
    /*SampleLibrary(File file) throws IOException, ClassNotFoundException{ // reads from a library
        this.file = file;
        array = new ArrayList();
       
    }*/
    /**
     * Constructs an empty Sample Library
     */
    SampleLibrary(){  
        array = new ArrayList();
    }
    /**
     * Constructs a copy of a sample library. The array of samples is deep copied
     * @param sl Sample library which will be copied into new object
     */
    SampleLibrary(SampleLibrary sl){
        array = new ArrayList<>(sl.getArrayList().size());
        this.array = sl.getArrayList().stream()
                .map(s->new Sample(s)).collect(Collectors.toList());
        
    }
    /**
     * Takes 2 sample libraries as parameters and returns a single sample library
     * that is the combination of the 2 parameters
     * @param s1 first Sample Library to be merged
     * @param s2 second Sample Library to be merged
     * @return 
     */
    public static SampleLibrary mergeLibraries(SampleLibrary s1, SampleLibrary s2){
        SampleLibrary combinedLibrary = new SampleLibrary();
        s1.getArrayList().stream().forEach((s) -> {
            combinedLibrary.addSample(s);
        });
        s2.getArrayList().stream().forEach((s) -> {
            combinedLibrary.addSample(s);
        });
        return combinedLibrary;
    }
    /**
     * Due to the sheer size of the data structure, a copy of the arraylist is
     * not practical
     * @return a reference to the array list
     */
    public List<Sample> getArrayList(){
       return array;
    }
   
    /**
     * @return the size of the Sample LibraryArray
     */
    public int getLength() {
        return array.size();
    }
    
    /**
     * Adds a sample to the Sample Library
     * @param s sample to be added to library
     */
    public void addSample(Sample s){
        array.add(s);
    }
    
    /**
     * Removes sample from Sample Library. Uses the Collections implementation of
     * remove(T t)
     * @param s The item to be removed
     */
    public void removeSample(Sample s){
        array.remove(s);
    }
    // writes a report in the form of an excel spreadsheet that is exported.
    // Stage variable is for using a filechooser to pick where to save file.
    /**
     * writes a report in the form of an excel spreadsheet that is exported.
     * Stage variable is for using a filechooser to pick where to save file.
     * @param myStage Stage is for saving file using FileChooser
     * @throws FileNotFoundException 
     */
    public void writeReport(Stage myStage) throws FileNotFoundException {
        FileChooser pickFile = new FileChooser();
        pickFile.setInitialDirectory(new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\"));
        pickFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
        File writeFile = pickFile.showSaveDialog(myStage);
        FileOutputStream fileOut;
        fileOut = new FileOutputStream(writeFile);
       
        
        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("WDXRF");
        sheet1.setDefaultColumnWidth(15);
        // Create a cell space
        for(int i = 0; i < ElementUtils.skf.length+5; i++){
            Row tempR = sheet1.createRow(i);
            for(int j = 0; j <= array.size(); j++){
                Cell tempC = tempR.createCell(j);
            }
        }
        CellStyle csCenter = wb.createCellStyle();
        csCenter.setAlignment(CellStyle.ALIGN_CENTER);
        csCenter.setBorderTop(CellStyle.BORDER_THIN);
        csCenter.setBorderLeft(CellStyle.BORDER_THIN);
        csCenter.setBorderRight(CellStyle.BORDER_THIN);
        csCenter.setBorderBottom(CellStyle.BORDER_THIN);
        CellStyle csRight = wb.createCellStyle();
        csRight.setAlignment(CellStyle.ALIGN_RIGHT);
        csRight.setBorderTop(CellStyle.BORDER_THIN);
        csRight.setBorderLeft(CellStyle.BORDER_THIN);
        csRight.setBorderRight(CellStyle.BORDER_THIN);
        csRight.setBorderBottom(CellStyle.BORDER_THIN);
        CellStyle csLeft = wb.createCellStyle();
        csLeft.setAlignment(CellStyle.ALIGN_LEFT);
        csLeft.setBorderTop(CellStyle.BORDER_THIN);
        csLeft.setBorderLeft(CellStyle.BORDER_THIN);
        csLeft.setBorderRight(CellStyle.BORDER_THIN);
        csLeft.setBorderBottom(CellStyle.BORDER_THIN);
        
        //Top Row
        sheet1.getRow(0).setHeightInPoints(25);
        sheet1.addMergedRegion(new CellRangeAddress(0,0,0, array.size()));
        sheet1.getRow(0).getCell(0).setCellValue("WDXRF Analysis");
        sheet1.getRow(0).getCell(0).setCellStyle(csCenter);
        sheet1.addMergedRegion(new CellRangeAddress(1,1, 0, array.size()));
        
        //Second Row
        sheet1.getRow(1).getCell(0).setCellValue("Conc as Wt%");
        sheet1.getRow(1).getCell(0).setCellStyle(csCenter);
        
        // Third Row
        sheet1.getRow(2).setHeightInPoints(35);
        sheet1.getRow(2).getCell(0).setCellValue("Common Oxides/Oxication States");
        sheet1.getRow(2).getCell(0).setCellStyle(csLeft);
        for(int j = 1; j <= array.size(); j++){
            sheet1.getRow(2).getCell(j).setCellStyle(csLeft);
            sheet1.getRow(2).getCell(j).setCellValue(array.get(j-1).getName());
        }
        
        //Fourth Row
        sheet1.getRow(3).getCell(0).setCellValue("% Detectable");
        sheet1.getRow(3).getCell(0).setCellStyle(csLeft);
        for(int j = 1; j <= array.size(); j++){
            sheet1.getRow(3).getCell(j).setCellValue("0.00");
            sheet1.getRow(3).getCell(j).setCellStyle(csLeft);
        }
        
        //Fifth Row
        sheet1.addMergedRegion(new CellRangeAddress(4,4,0,array.size()));
        sheet1.getRow(4).getCell(0).setCellValue("Results Normalized with Respect to Detectable Concentration");
        sheet1.getRow(4).getCell(0).setCellStyle(csCenter);
        
        //Rows 6 and beyond. Prints element list and defaults values to 0
        for(int i = 5; i < ElementUtils.skf.length+5; i++){
            sheet1.getRow(i).getCell(0).setCellValue(ElementUtils.skf[i-5]);
            sheet1.getRow(i).getCell(0).setCellStyle(csLeft);
            for(int j = 1; j <= array.size(); j++){
                   sheet1.getRow(i).getCell(j).setCellValue("0.0");
                   sheet1.getRow(i).getCell(j).setCellStyle(csRight);
            }
        }    
        
        // Copies values in SampleLibrary array into report
        for(int i = 0; i < array.size(); i++){
            List<Element> eleArray = array.get(i).getArrayCopy();
            for(int j = 0; j < ElementUtils.skf.length; j++){
                for(int k = 0; k < eleArray.size(); k++){
                    
                    if(ElementUtils.skf[j].contains(eleArray.get(k).getBaseElement()))
                        sheet1.getRow(j+5).getCell(i+1).setCellValue(eleArray.get(k).getConcWeight());
                }
                    
            }
        }
        try {
            wb.write(fileOut);
            wb.close();
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(SampleLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
