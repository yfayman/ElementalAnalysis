/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.eletablebuilder;

/**
 *
 * @author yan
 */
public class EleInfo {
    private int atomicNumber;
    private String symbol;
    private String atomicWeight;

    public EleInfo(int atomicNumber, String symbol, String atomicWeight) {
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.atomicWeight = atomicWeight;
    }
    
    
    
    public int getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAtomicWeight() {
        return atomicWeight;
    }

    public void setAtomicWeight(String atomicWeight) {
        this.atomicWeight = atomicWeight;
    }

    
    
}
