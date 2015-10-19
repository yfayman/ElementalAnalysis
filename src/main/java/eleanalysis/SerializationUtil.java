/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eleanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This call is used to help serialize/de-serialize the SampleLibrary object.
 * This class only contains static methods. It will not be instanced
 * @author Yan
 */
public final class SerializationUtil {
    
    private SerializationUtil(){} // Class should not be instanced
    
    public static Object deserialize(File file) {

        Object obj; 
        try (ObjectInputStream ob = new ObjectInputStream(
                new FileInputStream(file))){

            obj = ob.readObject();
            
            return obj;
        }catch(IOException | ClassNotFoundException  e){
            System.out.println("Exception: " + e);
            return null;
        }
              
        
        
            
    }
    // This method isn't used at the moment, but may be useable if a class to read/write multiple serialized objects
    // is written
    public static void serialize(Object obj, File file) {
        try (ObjectOutputStream ob = new ObjectOutputStream(
            new FileOutputStream(file))){
            
            ob.writeObject(obj);
        }catch(IOException e){
            System.out.println("Exception " + e);
        }
        
    }
    //This is used when the contents of the file must be erased prior to serialization. It is most commonly used
    public static void serializeOverwrite(Object obj,File file) {
        //file.delete(); This is not necessary because 
        try(ObjectOutputStream ob = new ObjectOutputStream(
                new FileOutputStream(file,false))){
            
        
            ob.writeObject(obj);
        }catch(IOException e){
            System.out.println("Exception " + e);
        }
    }
}
