/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mikko
 */
public class KassapaateTest {
    Kassapaate paate;
    Maksukortti kortti;
    
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(10000);
    }

    @Test 
    public void oikeaRahamaaraAlussa() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test 
    public void oikeaEdullistenMyytyjenMaaraAlussa() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test 
    public void oikeaMaukkaidenMyytyjenMaaraAlussa() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void riittavaMaksuEdullinenKasvattaaKassaa() {
        paate.syoEdullisesti(1000);
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void riittavaMaksuMaukasKasvattaaKassaa() {
        paate.syoMaukkaasti(1000);
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void edullinenPalauttaaOikeanVaihtorahan() {
        assertEquals(260, paate.syoEdullisesti(500));
    }
    
    @Test
    public void maukasPalauttaaOikeanVaihtorahan() {
        assertEquals(100, paate.syoMaukkaasti(500));
    }
    
    @Test 
    public void onnistunutEdullinenKasvattaaMyytyjenMaaraa() {
        paate.syoEdullisesti(1000);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test 
    public void onnistunutMaukasKasvattaaMyytyjenMaaraa() {
        paate.syoMaukkaasti(1000);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void riittamatonMaksuEdullinenEiKasvataKassaa() {
        paate.syoEdullisesti(10);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void riittaatonMaksuMaukasEiKasvataKassaa() {
        paate.syoMaukkaasti(10);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void riittamatonEdullinenPalauttaaKaiken() {
        assertEquals(100, paate.syoEdullisesti(100));
    }
    
    @Test
    public void riittamatonMaukasPalauttaaKaiken() {
        assertEquals(100, paate.syoEdullisesti(100));
    }
    
    @Test 
    public void riittamatonEdullinenEiKasvataMyytyjenMaaraa() {
        paate.syoEdullisesti(100);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test 
    public void riittamatonMaukasEiKasvataMyytyjenMaaraa() {
        paate.syoMaukkaasti(100);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void tarpeeksiRahaaKortillaEdullisenOstoVeloittaaSummanKortilta() {
        assertTrue(paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void tarpeeksiRahaaKortillaMaukkaanOstoVeloittaaSummanKortilta() {
        assertTrue(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void tarpeeksiRahaaKortillaEdullisenOstoKasvattaaMyytyjenMaaraa() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void tarpeeksiRahaaKortillaMaukkaanOstoKasvattaaMyytyjenMaaraa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    
    @Test
    public void eiTarpeeksiRahaaKortillaEdullisenOstoVeloittaaSummanKortilta() {
        kortti = new Maksukortti(0);
        assertFalse(paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void eiTarpeeksiRahaaKortillaMaukkaanOstoVeloittaaSummanKortilta() {
        kortti = new Maksukortti(0);
        assertFalse(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void eiTarpeeksiRahaaKortillaEdullisenOstoEiKasvataMyytyjenMaaraa() {
        kortti = new Maksukortti(0);
        paate.syoEdullisesti(kortti);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void eiTarpeeksiRahaaKortillaMaukkaanOstoEiKasvataMyytyjenMaaraa() {
        kortti = new Maksukortti(0);
        paate.syoMaukkaasti(kortti);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortillaEdullisenOstoEiKasvataKassanSaldoa() {
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortillaMaukkaanOstoEiKasvataKassanSaldoa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test 
    public void kassanRahamaaraKasvaaKorttiaLadattaessa() {
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, paate.kassassaRahaa());
    }
    
    @Test
    public void negatiivitaSummaaEiLadataKassasta() {
        paate.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    
    
    
    
    
    
}
