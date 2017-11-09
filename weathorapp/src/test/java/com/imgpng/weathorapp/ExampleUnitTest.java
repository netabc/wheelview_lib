package com.imgpng.weathorapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test3(){
//        l = n（圆心角）× π（圆周率）× r（半径）/180=α(圆心角弧度数)× r（半径）
        System.out.println(""+getSize(30.0,180.0));
        System.out.println(""+getSize(60.0,180.0));
        System.out.println(""+getSize(90.0,180.0));
        System.out.println(""+getSize(180.0,90));
    }
    public double gL(double size,double r){
        return size*Math.PI*r/180;
    }
    public double getSize(double l,double r){
        return l*180/(Math.PI*r);
    }
}