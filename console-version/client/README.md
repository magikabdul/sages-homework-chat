W celu łatwego korzystania z wielu instancji klienta została przygotowana odpowiednia konfiguracja `pom.xml`

### Zbudowanie paczki `jar`

``mvn package -DskipTests=true``

### Uruchomienie klienta

Przy założeniu, że znajdujemy się w głównym katalogu klienta, aplikację uruchamiamy

``java -jar target\client-1.0-SNAPSHOT.jar``

Dla potrzeb prac nad tą aplikacją klient posiada domyślną konfigurację w przypadku braku argumentów startowych:
- serwer - `localhost`
- port - `9500`
