/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eleanalysis;

import java.io.Serializable;
import java.util.Comparator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 * The Element object represents an Element and it's concentration. The concentration
 * can take on two forms. Weight % and Atomic %. One can be calculated from the other only
 * when the entire sample is known. Because of this, that calculation is performed in the 
 * { @link Sample#calcWeightorAtomic() method. }
 * @author Yan
 * @version  1.1
 */
public class Element implements  Serializable {
    private final static long serialVersionUID = 8912410941248L;
    private final String elementName; 
    private double concWt; // The concentration as a weight %
    private double concAtomic; // The concentration as an atomic %
    private boolean oxide; // Not used currently. Determines if the value is of an oxide
    private final boolean isWP; // Data is read in as concWt or concAtomic. The other is calculated

   
    //Below parameters are to allow this class to work with TableView Object. 
    private final transient SimpleStringProperty elementTableName;
    private final transient SimpleDoubleProperty elementTableWtConc;
    private final transient SimpleDoubleProperty elementTableAtomicConc;
    private final transient SimpleStringProperty StringWtConc;
    private final transient SimpleStringProperty StringAtomicConc;
    
    /**
     * Creates a new Element object 
     * @param elementName the name of the Element
     * @param concentration the concentration of the Element. This can represent Wt& or At%
     * @param isWP tells the Element which subset concentration belongs to
     */
    
    Element(String elementName, double concentration, boolean isWP){
        this.elementName = elementName;
        this.isWP = isWP;
        if (isWP){
            this.concWt = concentration;
            this.concAtomic = -1; // Will be set after all elements are read
            this.StringWtConc = new SimpleStringProperty(((Double)this.concWt).toString());
            this.StringAtomicConc = new SimpleStringProperty("NA");
            
        }else{
            this.concAtomic = concentration;
            this.concWt = -1; // Will be set after all elements are read in
            this.StringWtConc = new SimpleStringProperty("NA");
            this.StringAtomicConc = new SimpleStringProperty(((Double)this.concAtomic).toString());
        }
        
        this.elementTableName = new SimpleStringProperty(elementName);
        this.elementTableWtConc = new SimpleDoubleProperty(this.concWt);
        this.elementTableAtomicConc = new SimpleDoubleProperty(this.concAtomic);
        this.oxide = this.elementName.contains("O");
    }
 
    /**
     * Constructs Element object that is a deep copy of parameter element
     * @param el the Element object to be copied
     */
    
   Element(Element el) { 
       this(new String(el.getName()),el.getConc(),el.isWP);
   }

   /**
    * This array is used to check if entries that are read in from files or Tableview
    * are elements or oxides that the system has data on
    */
   
    public static String[] elementSymbols = {"Li", "Be", "B","C","CO2","N","NO","NO2"
    ,"F","Ne","Na","Na2O","Mg","MgO","Al","Al2O3","Si","SiO2","P","P2O5","P204","P2OX",
    "S","SO2","SO3","Cl","Cl2O3","K","K2O","Ca","CaO","Sc","Sc2O3","Ti","TiO2","V","V2O5",
    "Cr","Cr2O3","Mn","MnO","Fe","Fe2O3","Co","Co2O3","Co2O4","Co3O4","Ni","NiO","Cu","CuO",
    "Zn","ZnO","Ga","Ga2O3","Ge","GeO2","As","As2O3","Se","SeO2","Br","BrO2","Rb",
    "Rb2O","Sr","SrO","Y","Y2O3","Zr","ZrO2","Nb","NbO","Nb2O5","Mo","MoO2", "Tc",
    "Tc2O7", "Ru","Ru2O","Rh","Rh2O3","Pd","PdO","Ag","Ag2O","Cd","CdO","In","In2O3",
    "Sn","SnO","Sb","Sb2O3","Te","TeO2","I","Cs","Cs2O","Ba","BaO","La","La2O3",
    "Ce","CeO2","Pr","Pr2O3","Nd","Nd2O3","Pm","Pm2O3","Sm","Sm2O3","Eu","Eu2O3",
    "Gd","Gd2O3","Tb","Tb4O7","Dy","Dy2O3","Ho","Ho2O3","Er","Er2O3","Tm","Tm2O3",
    "Yb","Yb2O3","Lu","Lu2O3","Hf","HfO2","Ta","Ta2O5","W","WO2","WO3","Re","ReO2","Os",
    "OsO4","Ir","IrO2","Pt","PtO2","Au","Au2O3","Hg","HgO","Tl","Tl2O3","Pb","PbO",
    "Bi","Bi2O3","Po","PoO2","At","At2O","Fr","FrO2","Ra","RaO","Ac","Ac2O3","Th",
    "ThO2","Pa","PaO2","Pa2O5","U","U3O8","Np","NpO2","Pu","PuO2"};
    
    /**
     * @return the name of the Element as a String
     */
    
    public String getName(){
        return elementName;
    }
    
    /**
     * @return Wt% concentration of sample. If it's not calculated, -1 is returned
     */
    
    public double getWtConc(){
        return concWt;
    }
    
    /**
     * @return Atomic% concentration of sample. If it's not calculated, -1 is returned
     */
    public double getAtomicConc(){
        return concAtomic;
    }
    
    /**
     * @return the inputted concentration
     */
    public double getConc(){ 
        return isWP ? concWt : concAtomic;
    }
    
    /**
     * @return the calculated concentration
     */
    
    public double getOtherConc(){ 
        return isWP ? concAtomic : concWt;
    }
    
    /**
     * @return whether the Element name is an oxide
     */
    
    public boolean isOxide(){
        return oxide;
    }
    
    /**
     * @return was isWP true or false when Element was created
     */
    
    public boolean isWP(){
        return isWP;
    }
   
    //used to work with tableview
    public String getElementName() {
            return elementTableName.get();
    }

     //used to work with tableview   
    public String getConcWeight() {
            return StringWtConc.get();
    }
    
    //used to work with tableview
   public String getAtomicWeight() {
                return StringAtomicConc.get(); 
        }
 
   //used to work with tableview
   public void setWtConc(String c) {
            
            StringWtConc.set(c);
            concWt = Double.parseDouble(c);
    }
   
   /**
    * This function is used to set values during @link Sample#calcWeightorAtomic
    * @param c This string will be be used to change Element object fields
    */
   
   public void setAtomicConc(String c) {
            
            StringAtomicConc.set(c);
            concAtomic = Double.parseDouble(c);
   }
   /**
    * Dissects the Element name and returns base element
    * For example Fe2O3 = Fe, ZnO = Zn, F = F
    * @return base element
    */
   public  String getBaseElement(){
       String str = "";
       int i = 0;
       if (elementName.substring(0, 1).equals("Co") || elementName.substring(0, 1).equals("Mo") 
               ||elementName.substring(0, 1).equals("Os") || elementName.substring(0, 1).equals("No"))
           return elementName.substring(0,1);
       while(Character.isAlphabetic(elementName.charAt(i)) 
               && elementName.charAt(i)!='O'
               && i < elementName.length()){
           str += elementName.charAt(i);
           i++;
       }
       return str;
   }
   
   
  /*  @Override
    // This will sort objects in descending order by concentration
    public int compareTo(Element e) { 
        if(this.getConc() == e.getConc())
            return 0;
        else if(this.getConc() < e.getConc())
            return 1;
        else 
            return -1;       
    } */
   
   /**
    * Comparator for sorting elements into descending order based on default
    * concentration
    */
    public static  Comparator<Element> elementConcDescComparator = new Comparator<Element>(){

        @Override
        public int compare(Element e1, Element e2) {
            if(e1.getConc() == e2.getConc())
                return 0;
            else if (e1.getConc() < e2.getConc())
                return 1;
            else 
                return -1;
        }
        
    };
    /**
     * Comparator if you want to sort in Elements in ascending order by 
     * default concentration
     */
    public static  Comparator<Element> elementConcAscComparator = new Comparator<Element>(){

        @Override
        public int compare(Element e1, Element e2) {
            if(e1.getConc() == e2.getConc())
                return 0;
            else if (e1.getConc() < e2.getConc())
                return -1;
            else 
                return 1;
        }
        
    };
    /**
     * Comparator for sorting Elements in by name
    */
    public static  Comparator<Element> elementNameComparator = new Comparator<Element>(){

        @Override
        public int compare(Element e1, Element e2) {
            return (e1.getName().toUpperCase().compareTo(e2.getName().toUpperCase()));
        }    
    };
}
