## Arkkitehtuuri

### Rakenne

Ohjelma koostuu kolmesta taosta, joiden pakkausrakenne näyttää seuraavalta. 

UI on myös yhteydessä LeaderboardDao:on suoraan, joka ei toki ole optimaalista.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/rakenne1.PNG">

UI hallitsee graafista käyttöliittymää (JavaFX), domain sovelluslogiikkaa ja dao

tietokantoja.


### Käyttöliittymä

Sovelluksessa on neljä näkymää:

	- Menu

	- Peli

	- Pelin loppu

	- Leaderboard

Jokaisella näkymällä on oma scene, joka voidaan asettaa stagelle, saaden se näin

näkyviin.

Käyttöliittymä muodostuu kolmesta luokasta ChessUI, PiecesUI ja TileUI. 

ChessUI vastaa suurimmilta osin pelin näkymän luonnista ja PiecesUI ja TilesUI,

luovat graafiset esitykset nappuloista ja ruuduista pelissä.

Käyttöliittymä kommunikoi sovelluslogiikan kanssa ChessServicen läpi.


Käyttöliittymän tilanne muuttuu, kun painetaan jotain (scenen vaihtavaa) nappulaa

tai tehdään pelissä siirto (DrawBoard()).



### Sovelluslogiikka

Pelin logiiikka toimii pääosin viidestä luokasta (Chess, Player, Board, Tile ja Piece):

Piece luokka toimii pohjana kaikille eri nappulatyypeillä, eikä sitä siis käytetä

itsessään pelissä vaan luokkia (Pawn, Knight, Bishop, Rook, Queen, King).

Käyttöliittymä on yhteydessä sovelluslogiikkaan luokan ChessService kautta, joka pystyy

muun muassa seuraaviin toimintoihin:

	- void newGame(String playerOne, String playerTwo)

	- boolean turn(int clickedX, int clickedY, int x, int y)

	- String endGameAndReturnResult()

	- void quitAndSave()

	- boolean continueGame()

	- void addGameToLeaderboard()

ChessService säilyttää yksityisissä muuttujissaan tietoa tämän hetkisestä pelistä,

tämän hetkisestä laudasta ja edellisen pelin id:stä. Tietokannan kanssa ChessService

kommunikoi DAO;jen avulla.


Käyttöliittymään ChessService luodaan init() -käskyllä.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/rakenne2.PNG">



### Tietojen pysyväistalletus

Ohjelma hallinoi tietoja tietokannan avulla, johon se käyttää apunaan H2-kirjastoa.

Tiedot tallennetaan ja luetaan sovelluksen olessa käynnissä ChessDatabase-tiedostosta

ja testeissä TestChessDatabase-tiedostosta.

SQL-tietokannassa on neljä tietokanta taulua, jotka pitävät kirjaa edellisistä

peleistä, nappuloista ja pelaajista.


CREATE TABLE Chess (id INTEGER PRIMARY KEY, whitesTurn BOOLEAN, playerOne VARCHAR(255), playerTwo VARCHAR(255)

CREATE TABLE Pieces (id INTEGER AUTO_INCREMENT PRIMARY KEY, x INTEGER, y INTEGER, type VARCHAR(255), white BOOLEAN, hasMoved BOOLEAN, chessId INTEGER, FOREIGN KEY(chessId) REFERENCES Chess(id))

CREATE TABLE LatestGame (id INTEGER AUTO_INCREMENT PRIMARY KEY, latestGame INTEGER)

CREATE TABLE Players (name VARCHAR(255) PRIMARY KEY, wins INTEGER, losses INTEGER


### Päätoiminnallisuudet

##### Uuuden pelin aloitus

Pelaajaa täyttää nimikentät haluamillaan nimillä ja painaa pelin

aloitusta. ChessService luo uuden shakin kysesillä nimillä ja 

uudella pöydällä nappulat standardi sijainneillaan. Tämän jälkeen

pelaajat lisätään tietokantaan. Lopuksi pöytä piirretään ja scene vaihtuu.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/rakenne3.PNG">


##### Siirron tekeminen

Pelaaja klikkaa nappulaa ja sitten haluamaansa ruutua. ChessService

tarkastaa siirron laillisuuden ja sen ollessa oikein vaihtaa vuoroa.

Ennen vuoron vaihtoa tarkistetaan myös mahdollinen pelin loppuminen.

Lopuksi pöytä päivitetään CHessServicessä ja käyttöliittymässä.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/rakenne4.PNG">


##### Lopetus

Peli saavuttaa luonnolisen lopun siirron jälkeen tai luovutuksen seurauksena.

Scene vaihtuu ja tämän jälkeen ChessServicen kautta DAO poistaa pelin tietokannasta.

ChessService merkkaa, että viimeisintä peliä ei enää ole ja päivittää

DAO:n kautta Leaderboardin.

<img src="https://raw.githubusercontent.com/mikkosk/ot2019/master/kuvat/rakenne5.PNG">


##### Muut

Pääsääntöisesti kaikki toiminta siirtoa lukuunottamatta, tapahtuu

niin, että pelaaja klikkaa nappia ja näin päivittää näkymän.

Samalla ChessServie ja sen välityksellä DAO päivittävät tietoja

tarpeen tullen ja välittävät niitä käyttöliittymälle.


### Rakenteen ongelmat

Graafinen käyttöliittymä on sekava. Sen koodi on pitkää ja tulisi

optimi tilanteessa jakaa selvemmin. Luultavasti taitavampi JavaFX 

ihminen toteuttaisi sen myös tehokkaammin. Se kommunikoi myös suoraan

DAO:n kanssa, joka ei ole optimaalista. Graafinen käyttöliitttymä

ei ole myöskään järin kaunis.



Domainiin jäi taas vähän isoja luokkia, hieman toisteista koodia ja myös

yksittäisiä huonoja nimeämisiä ja montaa asiaa tekeviä luokkia.

Luultavasti shakin kokonaisuutena ja yksittäisen pelin olisi voinut erottaa ja 

ja luoda tulostaulun tähän suurempaan kokonaisuuteen.

Nappuloiden luokkien toteutukseen olisi luultavasti ollut myös elegantimpi''

keino.


DAO ei noudata CRUD logiikkaa, eikä ole muutenkaan toiminnaltaa järin intuitiivinen.

En myöskään tiedä oliko tapani eriyttää testaaminen ja varsinainen

tietokanta hyvä.

Myös toisteista koodia löytyy.


