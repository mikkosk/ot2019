## Shakki - OT2019

Sovelluksessa kaksi pelaajaa pystyvät pelaamaan shakkia vastakkain.
Tällä hetkellä peli ei kuitenkaan tunne vielä sääntöjä, vaan nappuloita voi
siirtää aivan miten haluaa.

Viikko 3: Etätyöpöydältä puuttui ilmeisesti, jokin tiedosto, joka tarvitaan sovelluksen
pyörittämiseen. Koska en voinut tätä itse asentaa, en ole aivan varma sen
toiminnasta Linuxilla, vaikka se sen NetBeansillä kyllä pyörii.
Muilla käyttöjärjestelmillä komentoriviltä käynnistyskin onnistuu.

### Dokumentaatio

[Vaatimusmäärittely](https://github.com/mikkosk/ot2019/tree/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/mikkosk/ot2019/tree/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/mikkosk/ot2019/tree/master/dokumentaatio/arkkitehtuuri.md)

### Komentorivitoiminnot

#### Käynnistys

Sovelluksen suoritus

```
mvn compile exec:java -Dexec.mainClass=com.mycompany.shakki.Main
```

#### Testaus

Testien suoritus

```
mvn test
```

Testikattavuusraportin luonti

```
mvn jacoco:report
```


#### Jarin generointi

```
mvn package
```


#### Checkstyle

```
mvn jxr:jxr checkstyle:checkstyle
```



Tekijä:

*Mikko Kivistö* 2019
