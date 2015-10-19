/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eleanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Encapsulate a set of Items that represent a Sample.
 * This class is also responsible for reading data from files
 * @author Yan
 * @version 1.2
 */
public class Sample  implements Serializable {
    private final static long serialVersionUID = 2342348713709L;
    private transient String fileName; // Source file if there is one
    private transient List<Element> array;  // Group of elements that belong to the Sample
    private final Map<String,Double> map;
    private String sampleName; 
    private transient boolean dataFound; // if no data is found, calculations are not performed
    private transient boolean isWeightPercent; // Determines whether data is weight percent or atomic %
    private double totalWeightConc, totalAtomicConc;
    private transient HSSFWorkbook hssfwb; 
    private transient HSSFSheet sheet;
   // private transient Row row;
    //private transient Cell cell;
    private transient File src;   
    //private transient String topElement, secondElement; // for search algorithm
    
    // The minimum concentration for a complete sample
    final static double MIN_CONC = 80;
    
     /** 
      * Constructor when file is sent as a parameter. 
      * @param file the file from which data will be read from
      * @param isWP this boolean tells the Sample if it's in weight percent(true)
      * or atomic percent(false)
      */
    
    Sample(File file, boolean isWP){ 
        dataFound = false; // is flagged true if data is found
        src = file;
        this.sampleName = file.getName().substring(0, file.getName().indexOf(".")); // generates name from file name
        this.isWeightPercent = isWP;
        readFileToXLS();
       // array = new ArrayList<>();
        map = new HashMap<>();
        buildArrayFromXLS();
        if (dataFound){
            finalizeSample();
            buildMapFromArray();
        }
        else 
            Alert.loadMessage("No data was found in " + file.toString());
    }
    
    /**
     * For constructing Sample that has no elements
     * @param samName is name of sample provided by client
     * @param isWP this boolean tells the Sample if it's in weight percent(true)
     * or atomic percent(false)
     */
    
   Sample(String samName, boolean isWP){ 
        //array = new ArrayList<>();
        map = new HashMap<>();
        this.sampleName = samName;
        this.isWeightPercent = isWP;
   }
   
   /** 
    * This constructor is often used to generate an instance of Sample from information contained
    * in TableView. This way the dataview can be encapsulated by Sample and archived or put in report
    * @param array an array of elements 
    * @param samName is name of sample provided by client
    */
   
   Sample(List<Element> array, String samName ,boolean isWP){ 
       this.array = array;
       this.array = new ArrayList<>(array.size());
       this.map = new HashMap<>(array.size());
       array.stream().forEach((Element e)->this.array.add(new Element(e)));
       this.sampleName = samName;
       this.isWeightPercent = isWP;
       calcWeightorAtomic();
       buildMapFromArray();
   }

   /**Copy Constructor Deep Copy
    * @param s the source sample that will be deep copied by constructor
    */
   
   Sample(Sample s){
       this(s.getArrayCopy(),new String(s.getName()),s.isWeightPercent);
   }
    
   /**  
    *  This method populates the Sample array with data from a File.
    *  After it's finished, it sorts the array using @link #sortArray()
    * The method only looks for data in the first visible page.
    */
   
    private void buildArrayFromXLS(){ 
  
       int r = 0;
       while(r < 50){ // searches first 50 rows for data
            while(sheet.getRow(r)==null && r < 50){ // looks for a row that has data
                r++;
            }
            for(int c = 0; c < 15; c++){ //searches first 15 columns for data
                if(!getElementName(r,c).equals("-1") && getData(r,c+1)!=-1){
                    
                    array.add(new Element(getElementName(r,c),getData(r,c+1),isWeightPercent));
                    c = 40; //Stop reading through c's once the data set is found
                }
            }
            r++;
            
       }
       sortArray();    
    }
    
    /**
     *  This method sorts the array if there is one.
     */
    
    private void sortArray(){
      if(array.size() > 0){
           Collections.sort(array, Element.elementConcDescComparator);
           dataFound = true;
       }            
    }
    
    /**
     *  This method builds a Map using data in a populated array
     */ 
    
    private void buildMapFromArray(){
        this.map.clear();
        if(!array.isEmpty())
            array.stream().forEach((Element e)->this.map.put(e.getName().trim(), e.getConc()));  
    }
    
    /**
     *  This method builds an Array using data in a populated map. This is used
     *  when a sample file is loaded from serialization(array is transient)
     */
    
    private void buildArrayFromMap(){
        array.clear();
        if(!map.isEmpty()){
           List <String> eleName = new ArrayList<String>(map.keySet());
           List <Double> eleConc = new ArrayList<Double>(map.values());
           for(int i = 0; i < eleName.size(); i++){
               array.add(new Element(eleName.get(i),eleConc.get(i).doubleValue(),this.isWeightPercent));
           }
        }
    }   

    /**
     * Returns a File object pointing to source of file if there is one. Otherwise
     * throws a NullPointerException
     * @return source file
     */
    
    public  File getFile(){ 
        if (src == null)
            throw new NullPointerException();
        return src;
       
    }
    
     /**
    *  @param row the row at which to attempt data access
    *  @param cell the column at which to attempt data access
    * @return returns a String if the position is not null and contains a string. Otherwise returns "-1"
    */
    
    private String getElementName(int row, int cell){
        if(sheet.getRow(row)==null || sheet.getRow(row).getCell(cell)==null) { // Checks to see if the data exists
           return "-1";
        }else if (sheet.getRow(row).getCell(cell).getCellType() == Cell.CELL_TYPE_STRING)
            return sheet.getRow(row).getCell(cell).getStringCellValue();
        else 
            return "-1";
    }
   
   /**
    * @param row the row at which to attempt data access
    * @param cell the column at which to attempt data access
    * @return double value if the position is not null and contains a number. Returns -1 otherwise.
    */
    
      private double getData(int row, int cell){
       if(sheet.getRow(row)==null || sheet.getRow(row).getCell(cell)==null) { // Checks to see if the data exists
           return -1;
       } else if (sheet.getRow(row).getCell(cell).getCellType() == Cell.CELL_TYPE_NUMERIC) //Checks to see if cell has a number
           return sheet.getRow(row).getCell(cell).getNumericCellValue();
       else
           return -1;
       
   }
    // Returns the sample name

    /**
     * Returns the Sample name
     * @return name of Sample
     */
   public String getName(){
       return sampleName;
   }

    /**
     * Adds an element to the array
     * @param element adds a deep copy of this to the array
     */
   
   public void addToArray(Element element){
       array.add(new Element(element));
   }

    /**
     * @param index the index at which data will be retrieved from
     * @return a deep copy of the element in array[index]
     */
   
   public Element getFromArray(int index){
       return new Element(array.get(index));
   }

    /**
     * 
     * @return the size of the array.
     */
   
    public int getArraySize(){
       return array.size();
   }

    /**
     * Returns a deep copy of the Sample array
     * @return a deep copy copy of the array object 
     */
    
       public List<Element> getArrayCopy(){
       List<Element> temp = new ArrayList<>();
       if(array==null){ // if array is not constructed
           if(!map.isEmpty()){
               array = new ArrayList<>(map.size());
               buildArrayFromMap();
           }
       }
       array.stream().forEach((Element el)-> temp.add(el));
       return temp;
   }

    /**
     * Adds a new sample to the map
     * @param element a deep copy of this element is added to the map
     */
       public void addToMap(Element element){
       Element e = new Element(element);
       map.put(e.getName(),e.getConc());
   }

    /**
     * Looks for the date in the map
     * @param elName the name of the Element that is used as key to the Map
     * @return the concentration value in the map if there is one. -1 if the key doesn't exist
     */
       public double getFromMap(String elName){
       if (map.containsKey(elName))
            return map.get(elName);
       return -1;
   }


    // Checks the array to see if there are any oxides

    /**
     * Returns whether any Element in the array is an oxide
     * @return the @link Element#isOxide() applied to array
     */
       
    public boolean containsOxide(){
        boolean hasOxide = false;
        if (array.stream().anyMatch((e) -> (e.isOxide()))) {
            return true;
        }
        return hasOxide;
    }

    public boolean isWP(){
        return isWeightPercent;
    }
    /**
     * This method sorts the array in descending order by concentration
     * @param low lower index of array
     * @param high higher index of array
     */
    private void quickSort(int low, int high){ // Sort for TableView
        int i = low;
        int j = high;
        Element tempVal;
        double p = array.get(i+((j-i)/2)).getConc();
        while(i<=j){
            while((array.get(i).getConc()) > p){
                i++;
            }
            while((array.get(j).getConc()) < p){
                j--;
            }
            if(i<=j){
                tempVal = array.get(i);
                array.set(i,new Element(array.get(j).getElementName(),array.get(j).getConc(),isWeightPercent));
                array.set(j,new Element(tempVal.getElementName(),tempVal.getConc(),isWeightPercent));
                 i++;
                 j--;
            }
            if(low<j){
                quickSort(low,j);
            }
            if(i<high){
                quickSort(i,high);
            }   
        }      
    }
    
    /**
     * Returns total moles in a simulated sample to calculate relative moles.
     * This is used to calculate Atomic %
     * @return total moles in a sample assuming a set weight(usually 100 grams) 
     */
    
    private double totalMoles(){ 

        if(isWeightPercent){
           
            return array.stream().mapToDouble((e)->e.getWtConc()/ElementUtils.getMolarMass(e.getName())).sum();
           
        }
        return -1;
    }
    
    /**
     * Returns total moles in a simulated sample to calculate relative moles.
     * This is used to calculate Weight %
     * @return total mass in a sample assuming a set amount of moles(usually 1 mole)
     */
    
    private double totalMass(){
        if(!isWeightPercent){
            
            return array.stream().map((el)->(el.getAtomicConc() * ElementUtils.getMolarMass(el.getName())))
                    .reduce(0.0, (x,y)->x+y);
        }
        return -1;
    }
    
    /**
     * This method calculates the value that was not inputted by the client(Wt % or At%) 
     * The value is populated by changing the Elements in the array
     */
    
    private void calcWeightorAtomic(){
        double total;
        double temp;
        DecimalFormat df = new DecimalFormat("##.##");
        if(isWeightPercent){ // if WP calculate Atomic % and adjust Element Array
            total = totalMoles();
            for(Element el: array){
                temp = (el.getConc()/ElementUtils.getMolarMass(el.getName())/total)*100;
                el.setAtomicConc(df.format(temp));
            }
        }else{ // if not WP, calculate weightpercent and adjust Element Array
            total = totalMass();
            for(Element el: array){
                temp = (el.getConc()*ElementUtils.getMolarMass(el.getName()))/total*100;
                el.setWtConc(df.format(temp));
            }            
        } 
    }
   
 
    /**
     * This method is useful for troubleshooting. It prints the Element at a specific array index
     * @param index index at which Element information is printed
     */
    public void printElement(int index){ 
          
        System.out.println(array.get(index).getName() + " " + array.get(index).getConc()+ " " +
                    array.get(index).getOtherConc());   
    }
    
    public String getFirstElementName(){
        return array.get(0).getName();
    }
    public String getSecondElementName(){
        return array.get(1).getName();
    }
    /*Sorts the arraylist if it has more than 1 element
     * Calculates concWt or AtomicWt
     * This is used when a sample isn't read in from a file or TableView   
    */
    
    // Tells you if the sample makes sense

    /**
     * Checks to see if the aggregate concentration of the sample is between MIN_CONC 
     * and 100
     * @return returns true if sample is between MIN_CONC and 100(inclusive), else false
     */
    public boolean isComplete(){
        double totalConc = array.stream().mapToDouble((e)->e.getConc()).sum();
        return (totalConc >= MIN_CONC && totalConc <= 100);
    }

    /**
     * Sorts the array and calculates weight % or atomic percent depending on
     * what information is known
     */
        
    private void finalizeSample(){
        sortArray();
        calcWeightorAtomic();
    }
    
    /**
     * Reads the file and creates a new HSSFWorkbook object with it
     */
    
    private void readFileToXLS(){
        try {
            hssfwb =  new HSSFWorkbook(new FileInputStream(src)); // Stores XLS file
            this.sheet = hssfwb.getSheetAt(0); // Selects the first sheet in the workbook
        } catch (IOException ex) {
            System.out.println("Error: "+ ex);
        }
        
    }
    

}
