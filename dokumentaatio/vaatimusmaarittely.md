# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla kaksi pelaajaa voivat pelata pelin shakkia.

## Käyttäjät

Sovelluksella ei ole useampia käyttäjärooleja vaan kaikki
pelaajat ovat **normaali käyttäjiä**

## Käyttöliittymäluonnos

Sovellus koostuu neljästä näkymästä. 

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/kuvat/maarittely.PNG">

1. Menu

Pelaaja voi aloittaa uuden peli, jatkaa vanhaa tai tarkastella tuloksia.


2. Peli

Pelaaja voi pelata shakkia


3. Pelin loppu

Sovellus kertoo pelin voittajan.


4. Tulokset

Pelaaja näkyy pelaajien voitot ja tappiot.



## Sovelluksen tarjoama toiminnallisuus

### Ennen pelin alkua

- Pelaajat voivat syöttää nimensä ja valita puolensa

- Pelaaja voi tarkastella vanhoja tuloksia

- Pelaaja voi aloittaa uuden tai jatkaa vanhaa peliä

### Pelin aikana

- Pelaaja pystyy näkemään pelin tämän hetkisen tilanteen

- Pelaaja pystyy siirtämään nappuloita

	- Ainoastaan omia nappuloita voidaan siirtää

	- Siirtojen tulee olla laillisia 

	- ** TORNITUSTA EI LISÄTTY PELIN LOPULLISEEN VERSIOON) **

- Peli loppuu shakkimattiin tai pattiin

- Pelaaja lopettaa pelin luovuttumalla

- Peli tallettaa kunkin pelaajan tuloshistorian

- Peli voidaan keskeyttää luovuttamatta ja jatkaa sitä myöhemmin

## Jatkokehitysideoita

Seuraavat kohdat jäivät toteuttamatta:

- Peli näyttää jo syödyt nappulat

- Peliä voi pelata niin, että aika on rajoitettu

- Peliä voi pelata muutenkin kuin klassisella nappula kokoonpanolla
	
	- Pelille syötetään pisteraja ja nappuoloille pisteet

	- Pelaajat valitsevat ennen peliä haluamansa nappulat

	- Pelaajat valitsevat nappuloiden paikat ennen pelin alkua
