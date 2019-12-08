## Käyttöohje


Lataa githubista itsellesi sovelluksen jar-tiedosto eli Shakki.jar.

Ohjelmaa voi pyörittää myös lataamalla koko reposition ja käynnistämällä sen

komentoriviltä, mutta tässä käsittelen vain .jarin avauksen.


### Konfigurointi

Ohjelma tallentaa tietonsa tiedostoihin

```
chessDatbase.mv

chessDatabase.trace

testChessDatabase.mv

testChessDatabase.trace
```

näiden pitäisi kuitenkin syntyä automaattisesti samaan hakemistoon, kuin 

.jarin, joten lähtökohtaisesti kaiken pitäisi toimia ilman konfigurointia.


### Käynnistäminen

Ohjelman saa käyntiin seuraavalla komennolla komentoriviltä

```
java -jar Shakki.jar
```


### Uuden pelin aloittaminen

Uuden pelin saat käyntiin kirjoittamalla haluamiesi pelaajien nimet tekstikenttiin

ja sitten painamalla Let's play! -nappulaa.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/menu.PNG">


### Vanhan pelin jatkaminen

Vanhaa peliä voit jatkaa painamlla Continue -näppäintä. Tämä jatkaa viimeisintä peliä

kohdasta, johon se jäi. Jos kuitenkin viimeisin peli pelattiin loppuun tai

sellaista ei ole olemasa, ei Continue tee mitään.



### Siirron tekeminen

Pelin oikeassa reunassa näkyy kumpi pelaajista on vuorossa. Tämä pelaaja voi tehdä 

minkä vain laillisen siirron (paitsi tornituksen, joka toteutuksesta jäi puuttumaan)

klikkaamalla ensin nappulaa ja sitten haluttua uutta sijaintia. Jos siirto on,

laillinen ohjelma päivittää pöydän tilanteen. Jos tuntuu, että siirto on laillinen,

mutta ohjelma ei toteuta siirtoa, kannattaa kokeilla tuplaklikata nappulaa, jotta

et ole mahdollisesti valinnut jo aikaisempaa nappulaa, jonka siirtoa ohjelma vielä odottaa.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/peli.PNG">


### Luovuttaminen

Pelin voi lopettaa luovuttamalla painamalla Forfeit-nappulaa. Tämän jälkeen

peli ilmoittaa vastapelurin voittaneen ja pääset palaamaan menuun painamalla Back to

menu -nappulaa.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/loppu.PNG">

### Pelin luonnollinen loppuminen

Peli loppuu luonnollisesti, jos siirron jälkeen havaitaan matti tai patti. 

Tämän jälkeen menuun pääsee palaaman takaisin. (Kt. luovuttaminen)


### Pelin tallentaminen ja lopettaminen

Pelin voi lopettaa myös sitä lopettamatta painamalla Quit-näppäintä. Tällöin 

peliin pääsee palaamaan menusta Continue-näppäimellä


### Tulosten katsominen

Pelaajien voittojen määrää pääsee tarkastelemaan painamalla Leaderboard-näppäintä.

Takaisin menuun pääse Back to menu -näppäimellä.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/leaderboard.PNG">