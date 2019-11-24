# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla kaksi pelaajaa voivat pelata pelin shakkia.

## Käyttäjät

Lähtökohtaisesti sovelluksella ei ole useampia käyttäjärooleja vaan kaikki
pelaajat ovat **normaali käyttäjiä**

## Käyttöliittymäluonnos

Sovellus koostuu kahdesta näkymästä. 

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/vaatimusmaarittely.png">

Ensimmäisessä näkymässä pelaajat voivat valita nimensä ja puolet. Toisessa 
näkymässä toimii itse shakkipeli. Tähän siirrytään, kun pelaajat ovat
valinneet puolet ja siitä siirrytään takaisin ensimmäiseen näkymään,
kun peli on ohitse.

## Perusversion tarjoama toiminnallisuus

### Ennen pelin alkua

- Pelaajat voivat syöttää nimensä ja valita puolensa "tehty osittain, pelaajan nimi ei vielä näy"

### Pelin aikana

- Pelaaja pystyy näkemään pelin tämän hetkisen tilanteen "tehty"

- Pelaaja pystyy siirtämään nappuloita "tehty"

	- Ainoastaan omia nappuloita voidaan siirtää "tehty"

	- Siirtojen tulee olla laillisia "tehty"

- Peli loppuu shakkimattiin "tehty"

- Pelaaja lopettaa pelin luovuttumalla

## Jatkokehitysideoita

Jos aikaa jää toteutetaan joitan seuraavista ominaisuuksista:

- Peli tallettaa kunkin pelaajan tuloshistorian

- Peli voidaan keskeyttää luovuttamatta ja jatkaa sitä myöhemmin

- Peli näyttää jo syödyt nappulat

- Peliä voi pelata niin, että aika on rajoitettu

- Peliä voi pelata muutenkin kuin klassisella nappula kokoonpanolla
	
	- Pelille syötetään pisteraja ja nappuoloille pisteet

	- Pelaajat valitsevat ennen peliä haluamansa nappulat

	- Pelaajat valitsevat nappuloiden paikat ennen pelin alkua
