# Cele projektu

## Projekt 1a
### Założenia ogólne
1. Projekt realizowany samodzielnie przez wszystkich uczestników kursu
2. Nieprzekraczalny termin oddania projektu to 10.04.2022
3. Stworzony kod powinien być opublikowany na repozytorium git np. GitHub
4. Realizując projekt używamy wyłącznie standardowego SDK Java 11 tzn. nie korzystamy z frameworków, ani zewnętrznych bibliotek
5. Ze względu na kolejność realizowanych zajęć testy jednostkowe nie są wymagane, ale mile widziane

[CLEAN JAVA](https://github.com/magikabdul/sages-homework-chat/tree/main/console-version)

## Projekt 1b
### Założenia ogólne
1. Projekt realizowany samodzielnie przez wszystkich uczestników kursu
2. Nieprzekraczalny termin oddania projektu to 8.05.2022
3. Stworzony kod powinien być opublikowany na repozytorium git np. GitHub
4. Realizując projekt używamy standardowego SDK Java 11 oraz technologii poznanych w dalszej części kursu tj. CDI, JPA/Hibernate, elementy Jakarta EE
5. Ze względu na kolejność realizowanych zajęć testy jednostkowe nie są wymagane, ale mile widziane

[REST API](https://github.com/magikabdul/sages-homework-chat/tree/main/rest-version)

# Opis aplikacji
**Stwórz czat tekstowy/aplikację klient-server wykorzystując Java Sockets.**

**Aplikacja powinna umożliwiać:**
- rozmowę wielu osób na kanale grupowym
- rozmowę 2 lub więcej osób na kanale prywatnym
- przesyłanie plików między osobami na danym kanale
- zapamiętywanie historii rozmów po stronie serwera w bazie opartej o plik płaski
- możliwość przeglądania historii rozmów z poziomu klienta (jeśli uczestniczył on w rozmowie/był na kanale)
  Obsługa aplikacji powinna odbywać się z terminala/linii komend (interfejs tekstowy) dla 1a oraz REST API w przypadku 1b.
  Uwaga! Należy zwrócić szczególną uwagę na aspekty związane z wielowątkowością - zapewnić zarówno bezpieczeństwo jak wydajność całego rozwiązania.
