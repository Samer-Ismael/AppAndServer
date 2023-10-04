# Dokumentation: Samer Ismael

## Ditt namn
Samer Ismael

## Beskrivning av projektet

Projektet är en Kafka-serverapplikation som är integrerad med en MySQL-databas och har även en användarapplikation.

Jag valde att utveckla en chattapplikation där användare kan ange sina namn och börja kommunicera med andra aktiva användare i chatten. Användarapplikationen har en enkel grafisk användargränssnitt (GUI).

Alla tester körs automatiskt via GitHub Actions. 

## Vad har du gjort

För att implementera funktionaliteten i min applikation använde jag en enkel och effektiv metod. Jag skapade en "producer" som är ansvarig för att skapa och skicka Message-objekt till en Kafka-topic. Samtidigt lagras dessa meddelanden i en MySQL-databas för senare åtkomst och hantering.

För att säkerställa att Kafka-miljön är korrekt konfigurerad och startad när applikationen körs, skapade jag en Kafka-startklass. Denna startklass tar en filväg till en specifik Kafka-mapp och automatiserar processen att starta Zookeeper och Kafka-servern. Detta är viktigt eftersom en korrekt fungerande Kafka-miljö är avgörande för att våra meddelanden ska kunna skickas och tas emot.

I den andra delen av applikationen, som är tillgänglig för användarna, implementerade jag möjligheten för användare att ange sina namn. När användaren anger sitt namn, initieras en "producer" och en "consumer".

För att kunna hämta informationen från min topic som ett objekt så behöver man använda JsonDeserializer, men ibland kan den inte hantera den klassen som jag har som Entity-klass i det scenariot som jag vill ha den för den specifika appen som jag försöker skapa. Eftersom jag inte vill ändra i min Entity-klass och inte heller vill ändra i min app, behövde jag skapa en anpassad deserializer som fungerar med den klassen jag arbetar med för att lösa problemet.

- "Producer" är ansvarig för att skapa och skicka meddelanden till Kafka-topic. Varje användare har sin egen "producer" för att möjliggöra att de kan skicka meddelanden till andra användare.

- "Consumer" är ansvarig för att lyssna på Kafka-topic och ta emot meddelanden. Eftersom det inte är möjligt för två "consumers" som tillhör samma grupp att lyssna på samma topic, utvecklade jag en specialfunktion som genererar en unik och slumpmässig grupp-id för varje ny användare. Detta säkerställer att alla användare kan kommunicera och se de meddelanden som andra skickar. För varje ny consumer skickas ett meddelande från en annan consumer som kallas "System" till samma topic för att informera alla om att en person har anslutit sig till chatten.

För att spara och hantera meddelandena i applikationen använder jag en MySQL-databas. Denna databas innehåller en särskild tabell där varje meddelande är lagrat som en rad. Tabellen har flera kolumner, inklusive ett automatiskt ökande ID för varje meddelande, mottagarens namn, avsändarens namn och själva meddelandet. Genom att använda en strukturerad databas kan vi effektivt organisera och hämta meddelanden baserat på olika kriterier.

Även om möjligheten att hämta information från databasen via GET-requests är skriven i koden, är detta för närvarande inte integrerat i GUI-applikationen. Detta ger dock en möjlighet att implementera denna funktion i framtiden och låta användarna hämta meddelanden baserat på avsändarnamn eller mottagarnamn, vilket ger dem flexibilitet att söka efter och hitta de meddelanden de är intresserade av.

För att stödja privata meddelanden eller konversationer mellan två användare skulle jag lägga till ytterligare tabeller i MySQL-databasen. Dessa tabeller skulle användas för att lagra meddelanden som är specifika för två användare och skapa en relation som länkar avsändare och mottagare för varje meddelande. På så sätt skulle jag kunna utveckla API-rutter som tillåter användare att skicka privata meddelanden och hämta hela konversationer mellan två användare från databasen när jag väl implementerar den här funktionen i framtiden.

## Vad som varit utmanande

Den mest utmanande delen av projektet var att arbeta med GUI, eftersom det var första gången jag gjorde det. Testningen av interaktionen med databasen var också en utmaning som krävde noggrannhet och användning av inspelade lektioner samt forskning.

## Beskriv olika lösningar du implementerat

För att starta servern användes en "Server start"-funktion som automatiserar processen att starta Zookeeper och Kafka-servern genom att använda en ProcessBuilder som exekverar en CMD-kommando i bakgrunden.

## Beskriv något som var besvärligt att få till

Testningen av applikationen och interaktionen med databasen var den mest utmanande delen av projektet. Det krävde användning av externa resurser och noggrannhet för att säkerställa att allt fungerade som det skulle.
