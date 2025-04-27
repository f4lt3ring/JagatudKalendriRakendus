# JagatudKalendriRakendus
Rühmaliikmed: Artur (Lugerhelm), Kaur Huko (KaurHuko), Robert (f4lt3ring)

Meie projekt on veebiserveris põhinev kalender, mis põhineb .ical failil, kuhu saavad kasutajad programmi terminali kasutades muudatusi teha. Kliendi poolne programm saadab veebiserverile info küsimist, info edastamist, lisaks veel sisselogimise päringuid. Sisselogimisinfo tuleb muust failist, mis oleks veebiserverile kättesaadav. .ical fail töötab lõpuks ka kõigi kalendri rakendustega, mis toetavad seda ning faili on võimalik jagada ka sõbraga, et teine saaks seda muuta.

### Teine etapp

Teises etapi hakkasime rohkem vaeva nägema andmebaasi kasutamisega ning frontendi loomisega. Andmebaas hakkab meil hoidma kasutajaid ning erinevaid kalendreid, ning sellega me saame paremini kontrollida, millisel kliendil on õigus mingit kalendrit kasutada ja vaadata. Lisaks iseloomustab seda etapi plaanide ümber tegemine, sest tegime otsuseid milliseid tööriistu kasutada ja et meil siiski ei ole terminalis kasutatav frontend vaid tavaline application. Teise etapi lõpuks ei suutnud me oma eesmärki täita aga oleme sellele väga lähedal. Kolmanda etapi eesmärgiks on luua valmis ja esitletav application, mis täidab meie seadud eesmärke.

### Esimene etapp

Esimese etappi põhifookus oli mõista ja õppida kasutama *springbooti*.

Esimesed kaks nädalat igaüks võttis iseseisvalt läbi vajaliku materjali põhitõdede kohta. Õppeperioodi lõpuks moodustasime plaani, kuidas meie süsteem võiks töötada ning mille kallal keegi töötab. Etapi lõpuks oli eesmärk saada valmis andmebaas kuhu erinevaid kalendreid saaks salvestada. Lisaks esmane funktsionaalsus kalendri klassi - võimalus luua kalendrit, lisada üritusi ning saada programmis kätte muudetud *.ics fail.
Teise etapi eesmärk on valmis saada vähima vajaliku funktsionaalsusega programm, mida saaks klient kasutada.

## Kasutusjuhend: \
* docker-compose down -v (vajalikud andmebaasi andmed uuesti seadistada (vajadusel))
* docker-compose up --build (konteinerite käivitamine)
* docker ps (konteinerite kontrollimine)
* docker exec -it jagatudkalendrirakendus-db-1 psql -U admin -d registration (andmebaasi kontrollimine)
* kasulikud andmebaasi käsud: \dt, SELECT * FROM app_user;, SELECT * FROM confirmation_token;
* päringu saatmiseks aadressil http://localhost:8080/api/v1/registration lisades päiste alla Content-Type:application/json ja Accept:application/json: 
  {
    "email": "...",
    "username": "...",
    "password": "...",
    }
