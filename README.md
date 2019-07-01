# zonkynewloans

## Zadání:
- Zkuste naprogramovat kód, který bude každých 5 minut kontrolovat nové půjčky na Zonky tržišti a vypíše je. 
- Programové API Zonky tržiště je dostupné na adresehttps://api.zonky.cz/loans/marketplace, dokumentace na http://docs.zonky.apiary.io/#. 
- Výběr technologie necháme na Vás, podmínkou je však Java. 
- Při hodnocení úkolu budeme přihlížet k dobré testovatelnosti a čistotě kódu. Předem přiznáváme, že nemusíme over engineered řešení. 

## Technologie
Spring-Boot, REST

## Kompilace
```
mvn clean package
```

## Spuštění
```
mvn spring-boot:run
```
nebo
```
java -jar zonky-new-loans-1.0.0-SNAPSHOT.jar
```

## Popis aplikace
- **Application.java** - Hlavní spouštěcí třída pro spring-boot
- **TasksController.java** - Spring komponenta s taskem pro kontrolu nových půjček. Volá službu pro získání nových půjček a vypisuje je do console a do /logs/zonky-loans-${datum a čas spuštění aplikace}*.log. Při spuštění aplikace vypíše půjčky za posledních 24 hodin a dále sleduje jen nově zadané půjčky.
- **package com.github.zonky.service** - Vrstva služeb. Nabízí jen jednu službu pro získání nových půjček. V této vrstvě jsou použity a předávány dál entitní objekty, jelikož v této chvíli je asi zbytečné vytvářet modelové objekty (DTO) pro servisní vrstvu, jelikož by byly naprosto stejné. Pro další rozvoj bych doporučil vytvářet DTO objekty, aby struktura aplikace mohla být více flexibilní :) Pro mapování entitních objektů na DTO objekty bude vhodné použít framework pro object mapping.
- **package com.github.zonky.backend** - Backendová vrsta - Volání REST API zonky a definice entitních objektů.
- **application.properties** - Pouze jediná konfigurace - interval spouštění kontroly (nastaveno na 5min = 300 000ms)

