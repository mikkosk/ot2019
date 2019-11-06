package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void toStringToimiiOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void positiivinenLatausLisaaSaldoa() {
        kortti.lataaRahaa(10);
        assertEquals("saldo: 10.10", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeJosRahaaOn() { 
        assertTrue(kortti.otaRahaa(1000));
    }
    
    @Test
    public void saldoEiVaheneJosRahaaEiOle() {
        assertFalse(kortti.otaRahaa(11000));
    }
}
