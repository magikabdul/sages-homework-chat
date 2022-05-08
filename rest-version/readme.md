# INSTRUKCJA

Główny element aplikacji to aplikacja serwerowa, którą należy wgrać na serwer aplikacyjny. Aplikacja była uruchamiana
na serwerze JBoss v. 26.1.0.Final. 
Aplikacja korzysta z bazy danych, której sterownik oraz konfiguracja znajdują się na serwerze aplikacyjnym.

Root aplikacji - `chat`

## REJESTRACJA
W celu korzystania z aplikacji konieczne jest zarejestrowanie użytkownika. 
Nałożone są minimalne wymagania:
- login minimalna długość 3 znaki, maksymalna 50
- hasło minimalna długość 5 znaków

### `POST` `http://localhost:8080/chat/users/register`

![img.png](screens/register.png)

Przy próbie utworzenia konta, dla którego nick już istnieje jest rzucany wyjątek i otrzymujemy błąd `User already exists`

![img.png](screens/register-error.png)

Poprawnie założone konto zwraca w nagłówku `Location` i kod odpowiedzi `201`

![img.png](screens/register-success.png)

## LOGOWANIE

### `POST` `http://localhost:8080/chat/users/login`

![img.png](screens/login.png)

Podanie błędnych danych logowania zwraca kod odpowiedzi `404`

![img.png](screens/login-error.png)

Poprawne logowanie zwraca kod `200` oraz `token`, który jest używany do autoryzacji i jest wysyłany w nagłówku

![img.png](screens/login-success.png)

## KANAŁY CHATU

Domyślanie nowo zalogowany użytkownik jest przypisany do kanału `GENERAL`
### PRZEŁĄCZENIE UŻYTKOWNIKA NA INNY KANAŁ
Wymagane jest podanie tokena w nagłówku.

### `GET` `http://localhost:8080/chat/channels/change/<nazwa kanału>`

![img_1.png](screens/channel-change.png)

Jeżeli kanał istnieje dostajemy kod `202`

![img.png](screens/channel-change-success.png)

Jeżeli kanał nie istnieje dostajemy kod `400`

![img.png](screens/channel-change-fail.png)

### UTWORZENIE NOWEGO KANAŁU
Wymagane jest podanie tokena w nagłówku.

### `POST` `http://localhost:8080/chat/channels/`

![img.png](screens/channel-create.png)

Jeżeli kanał istnieje, to dostajemy kod `400`

![img.png](screens/channel-create-fail.png)

Jeżeli kanał nie istniał, to dostajemy kod `201`

![img.png](screens/channel-create-success.png)

### PUBLIKOWANIE WIADOMOŚCI
Wymagane jest podanie tokena w nagłówku.
Wiadomość jest przyporządkowana do kanału, na którym znajduje się użytkownik

### `POST` `http://localhost:8080/chat/channels/messages`

![img.png](screens/message-publish.png)

W odpowiedzi otrzymujemy kod `200`

![img.png](screens/message-publish-success.png)

### POBIERANIE HISTORII KANAŁU
Wymagane jest podanie tokena w nagłówku.
Historia jest wyświetlana tylko dla kanału, na którym obecnie znajduje się użytkownik.
Pobranie historii innego kanału wymaga przełączenia się na ten kanał.

### `GET` `http://localhost:8080/chat/channels/history`

![img.png](screens/history.png)

W odpowiedzi dostajemy kod `200` oraz tablicę obiektów lub pustą tablicę, kiedy nie było żadnych rozmów na kanale.

![img.png](screens/history-success.png)

### WYSZUKIWANIE WIADOMOŚCI PO ID
Wymagane jest podanie tokena w nagłówku.

### `GET` `http://localhost:8080/chat/channels/messages/<id>`

![img.png](screens/message-find.png)

Jeżeli wiadomość istnieje dostajemy kod `200`

![img.png](screens/message-find-success.png)

Jeżeli wiadomość nie została znaleziona dostajemy kod `404`

![img.png](screens/message-find-fail.png)

### WYSZUKIWANIE OSTATNIEJ WIADOMOŚCI NA KANALE
Wymagane jest podanie tokena w nagłówku.

_Obecnie nie działa mi rozgłaszanie wiadomości z wykorzystanie JMS, dlatego dla potrzeby uruchomienia klienta, została w nim zaimplementowana funkacja odpytywania serwera o ostatnią wiadomość na kanale_

### `GET` `http://localhost:8080/chat/channels/messages`

![img.png](screens/message-last.png)

W odpowiedzi otrzymujemy kod `200`

![img.png](screens/message-last-get.png)

### WYSYŁANIE PLIKU NA SERWER

### `POST` `http://localhost:8080/chat/channels/files/upload`

Wymagane jest podanie tokena w nagłówku.
Korzystamy z `form-data`, gdzie konieczne jest podanie dwóch parametrów:
- `file` - plik do wgrania
- `fileName` - nazwa, pod którą zostanie zapisany na serwerze

![img.png](screens/file-upload.png)

W odpowiedzi otrzymujemy kod `200`

![img.png](screens/file-upload-success.png)

Domyślnie plik jest zapisywany w głównym katalogu na dysku `D`

### POBIERANIE PLIKU Z SERWERA
Wymagane jest podanie tokena w nagłówku.
Nazwę pliku podajemy jako parametr w ścieżce w zapytaniu pod kluczem `fileName`

### `GET` `http://localhost:8080/chat/channels/files/download?fileName=<nazwa-pliku>`

![img.png](screens/file-download.png)

W odpowiedzi otrzymujemy kod `200`. Informacje o pliku znajduję się w nagłówku `Content-Disposition`

![img.png](screens/file-download-success.png)

# APLIKACJA KLIENCKA

Do współpracy z aplikacją serwerową została napisana aplikacja kliencka pracująca w konsoli.

`java -jar target/rest-client-1.0-SNAPSHOT.jar`

Po uruchomieniu pojawia się następujący widok

![img.png](screens/client.png)

### TWORZENIE KONTA

![img.png](screens/client-register.png)

### LOGOWANIE

![img.png](screens/client-login.png)

Po pomyślnym zalogowaniu wyświetla się prompt z nazwą kanału i loginem użytkownika

### WYŚWIETLANIE HISTORII

![img.png](screens/client-history.png)

### PUBLIKOWANIE WIADOMOŚCI

Konsola użytkownika `qwe`

![img.png](screens/client-qwe.png)

Konsola użytkownika `asd`

![img.png](screens/client-asd.png)


### ZAKOŃCZENIE PRACY

![img.png](screens/client-end-work.png)
