# FOTOzabawa
Repozytorium dla zadania projektowego na przedmiocie Aplikacje Mobilne Systemu Android, Wydział Matematyki Stosowanej, Politechnika Śląska 2022/2023.

## Autorzy
- [Artur Pabjan]()
- [Patryk Typek]()
- [Przemysław Żebrowski]()
- [Dawid Wydra](https://github.com/ejsiik)

## O Projekcie 
Celem projektu jest stworzenie aplikacji mobilnej dla systemu Android wykonującej automatycznie serię zadanej liczby
zdjęć w określonych przez użytkownika odstępach czasu oraz aplikacji serwerowej służącej do kreacji zestawienia zdjęć
w jedno konfigurowalne przez użytkownika zestawienie zdjęć gotowych do wydruku o rozmiarze A4 w formacie pdf.
Aplikacja mobilna po uruchomieniu powinna w sposób ciągły wyświetlać podgląd kamery. Powinna umożliwić
użytkownikowi rozpoczęcie wykonania nowej serii zdjęć (duży przycisk pośrodku ekranu a w tle podgląd kamery).
Zakładamy, że ostatnie kilka sekund (również konfigurowalne) przed wykonaniem każdego zdjęcia aplikacja powinna
sygnalizować charakterystycznym dźwiękiem. Po wykonaniu zdjęcia powinien pojawić się inny charakterystyczny dla
tylko tego etapu sygnał dźwiękowy. Wykonanie całej serii zdjęć powinno być również oznajmiane charakterystycznym
tylko dla tego etapu działania programu sygnałem dźwiękowym. Po wykonaniu każdego ze zdjęć powinno ono zostać
przesłane do aplikacji serwerowej.
Aplikacja serwerowa powinna działać w obrębie sieci lokalnej i działać jako REST API, do którego aplikacja mobilna
wysyła kolejne zdjęcia a po wykonaniu pełnej serii zleca stworzenie dokumentu wyjściowego zbudowanego z serii
wysłanych zdjęć, gotowego do wydruku.
