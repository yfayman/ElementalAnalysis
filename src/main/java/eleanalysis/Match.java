/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eleanalysis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class will store the information for matches when a 
 * search is executed on a Sample Library
 * @author Yan
 */
public class Match{

    private final String samName; // name of Sample from Sample object
    private final double matchPercent; // this is calculated by static calculateMatchPercent method
    private final List <Element> array; // holds data to be displayed on TableView
    //Below parameters are to conform with TableView
    private final SimpleStringProperty matchName; 
    private final SimpleStringProperty matchPercentString;
    private final SimpleDoubleProperty matchPercentDouble;
    /**
     * Object to encaps all data related to a Match item. The SimpeStringProperty
     * and SimpleDoubleProperty fields are to allow compatibility with the JavaFX 
     * Tableview
     * @param name the name of the Sample
     * @param matchPercent the % match with the search Sample 
     * @param sam Sample object associated with the match
     */    
    Match(String name, double matchPercent, Sample sam){
        this.samName = name;
        this.matchName = new SimpleStringProperty(name);
        this.matchPercentString = new SimpleStringProperty(((Double)matchPercent).toString());
        this.matchPercentDouble = new SimpleDoubleProperty(matchPercent);
        this.matchPercent = matchPercent;
        this.array = sam.getArrayCopy();
        
    }
    /**
     * Return match name
     * @return
     */
    public String getSamName(){
        return samName;
    }
    /**
     * 
     * @return the match percent(0-100% 
     */
    public double getMatchPercent(){
        return matchPercent;
    }
    /**
     * This getter is necessary for javafx tableview compatibiity
     * @return 
     */
    public String getName(){
        return matchName.get();
    }
   /**
    * This getter is necessary for javaFX tablieview
    * @return 
    */
    public String getPercent(){
        return matchPercentString.get();
    }
    /**
     * This setter is necessary for javaFX tableview
     * @param s 
     */
    public void setPercent(String s){
        matchPercentString.set(s);
    }
    /**
     * This getter is necessary for java FX tableview
      * @return 
     */
    public Double getmatchPercentDouble(){
        return matchPercentDouble.get();
    }
    /**
     * this setter is neccesary for javaFX tableview
     * @param d 
     */
   public void setMatchPercentDouble(double d){
       matchPercentDouble.set(d);
   }
   
   /**
    * Returns a deep copy of the array associated with the match
    * @return 
    */
   public List<Element> getArray(){
       List<Element> arrayCopy = new ArrayList<>();
       array.stream().map((e)->e).forEach((e)->arrayCopy.add(new Element(e)));
       return arrayCopy;
   }
    
   /**
    * Calculates how similar two Sample Items are. This function uses a weighted
    * percent difference algorithm. The percent difference of each element is weighted
    * by the concentration of that element in the source sample
    * @param searchedSample Primary Sample
    * @param librarySample Sample being compared to Primary Sample(most likely from a library)
    * @return a double value between 0 and 100 
    */
    static double calculateMatchPercent(Sample searchedSample, Sample librarySample){
        double matchPercent = 0;
        double weightedPercentSim, leftVal, rightVal, percentSim;

        for(int i = 0; i < searchedSample.getArraySize(); i ++){
            weightedPercentSim = 0;
            leftVal = searchedSample.getFromArray(i).getWtConc();
            String s1Name = searchedSample.getFromArray(i).getName().trim();
            if(librarySample.getFromMap(s1Name)!=-1) { // -1 is returned if there is no result 
                rightVal = librarySample.getFromMap(s1Name);
                percentSim = 1- Math.abs(rightVal - leftVal)
                                /Math.max(rightVal,leftVal);
                        weightedPercentSim = percentSim * leftVal;
            }
            matchPercent += weightedPercentSim;
        }
        return matchPercent;
    }
    
    /**
     * Comparator for sorting Match objects in ascending order by their matchPercent
     */
    public static Comparator<Match> ascendingMatchComparator = new Comparator<Match>(){

        @Override
        public int compare(Match o1, Match o2) {
            if(o1.matchPercent < o2.matchPercent)
                return -1;
            else if(o1.matchPercent > o2.matchPercent)
                return 1;
            else 
                return 0;
        }
            
    };
    
    /**
     * Comparator for sorting Match objects in descending order according to matchPercent
     */
    public static Comparator<Match> descendingMatchComparator = new Comparator<Match>(){

        @Override
        public int compare(Match o1, Match o2) {
            if(o1.matchPercent < o2.matchPercent)
                return 1;
            else if(o1.matchPercent > o2.matchPercent)
                return -1;
            else 
                return 0;
        }
            
    };
}