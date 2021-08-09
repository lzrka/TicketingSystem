# Jegykezelő rendszer

### 1. Leírás

Az applikáció egy céges félautómata jegykezelő rendszert testesít meg. A dolgozók feladhatnak különböző kérelmeket(request) és
hibabejelentéseket(incident) is. Amennyiben validálására kerül, hogy a munkavállaló tényleg szerepel a céges adatbázisban,
a probléma megadásával egy jegy(ticket) generálódik a rendszerben. A jegyek kezelése már manuálisan történik. A description mezőben
megadott probléma alapján kerül a jegy eszkalálásra. Ezt követően a jegyből egy incidens vagy request jegy nyitható amely
már tartalmazza a probléma kategóriáját és a megfelelő megoldócsoporthoz soroja azt. A ticket státusz mezőjének változása
befolyásolja, hogy miről és hogyan kap értesítést a feladó.

### 2. Entitások

![img_2.png](img_2.png)

### 3. Jegyfeladás menete:

A user companyNumber és név megadásával kerül validálásra, majd meg kell tennie a bejelentést a title és description menübe.
Amennyiben a probléma érinti a felhasználó céges eszközeit, meg tudja jelölni pontosan melyik készülékét érinti a probléma.

### 4. A jegyek kezelése:

A jegyeket az Agentek kezelik. Amennyiben a probléma már ticket szinten megoldásra kerül, a jegyet kezelő Agent
a nevére veszi azt(assigne) és a státuszt resolvedra kell, hogy rakja. Amennyiben nem megoldható a bejelentés
eszkalálni kell a jegyet. Ehhez ki kell tölteni a category fieldet és egy megoldócsoporthoz sorolni azt.

### 5. Értesítések és Nyomonkövetés

A feladók e-mailben kapnak értesítést amennyiben a feladott jegyet elküldték, hogy tudjanak a jegyszámra hivatkozni.
Ezt követően, már csak a probléma megoldásakor kapnak értesítést.
Az acitivites fieldbe lehet hozzáadni kommenteket a jegyhez. Ez arra szolgál, hogy a különböző változtatásokat
az Agentek jobban át tudják látni.

A jegyek szűrhetőek lesznek státusz, priorítás és típus alapján(ticket, request, incidens)


### 6. Technológiák

- Git
- Github
- Java
- H2 database
- Spring Data JPA
- Gradle
- RestApi
- Spring boot
- Flyway
- JUnit 5
- Lombok
- Mockito
- Swagger
- Docker
