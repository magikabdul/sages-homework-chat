# INSTRUKCJA

W pierwszej kolejności należy uruchomić [Server](https://github.com/magikabdul/sages-homework-chat/tree/main/console-version/server)
domyślnie nasłuchuje na porcie `9500`. 
Jeżeli chcemy wybrać inny port, to jako parametr `port:<numer portu>`

Na potrzeby sprawdzenia działania aplikacji, możliwości konwersacji grupowej, jak i w poszczególnych kanałach
jest wczytywana domyślna konfiguracja.

Oprócz kanału głównego mamy dwa kanały prywatne:
- games
- movies

"Logowanie" w aplikacji polega na wpisaniu z konsoli dowolnego nick-a. 

![logowanie](client/screens/00-logowanie.png)

Następnie użytkownik może rozmawiać w głównym kanale.

![prompt](client/screens/01-prompt.png)

Przełączenie do odpowiedniego kanału następuje po podaniu odpowiedniego polecenia `\c<nazwa kanału>` np. `\cgames`.
Można tylko przełączać się na istniejące kanały (games, movies). 

![no-channel](client/screens/02-no-channel.png)

![no-permissions](client/screens/03-no-permissions.png)

![switch-success](client/screens/04_channel-succes.png)

Powrót to głównego kanału następuje po wpisaniu `\c`

Do kanału może się zalogować tylko użytkownik, który ma do niego uprawnienia. Na potrzeby testów do poszczególnych kanałów
zostali przypisani użytkownicy według poniższej listy.



### games
- kris
- tom
- alice

### movies
- kris
- ed
- thomas
- liv

Serwer na bieżąco zapisuje historię rozmów. Użytkownik może pobrać historię danego kanału za pomocą polecenia `\d`.
Historię można pobrać tylko dla kanału, na którym znajduje się użytkownik. Nie ma zatem możliwości pobranie historii kanału,
do którego użytkownik nie może się zalogować.

![history](client/screens/05-history.png)

Zakończenie pracy następuje po wydaniu polecenia `\q`. Działa na dowolnym poziomie chat-u.

![logout](client/screens/06-logout.png)


Przykład działania chatu, różnych osób na różnych kanałach

![chat](client/screens/chat.png)
