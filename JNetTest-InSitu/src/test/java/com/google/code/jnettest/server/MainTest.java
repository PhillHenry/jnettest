package com.google.code.jnettest.server;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainTest {
    
    public static Main main; 
    
    @BeforeClass
    public static void setUp() {
        main = new Main();
    }
    
    @AfterClass
    public static void tearDown() {
        main.shutDown();
    }

    @Test
    public void springAnnotations() {
        Assert.assertNotNull(main.getServer());
    }

}
