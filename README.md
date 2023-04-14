# FOTOzabawa
Repozytorium dla zadania projektowego na przedmiocie Aplikacje Mobilne Systemu Android, Wydział Matematyki Stosowanej, Politechnika Śląska 2022/2023.

## Autors
- [Patryk Typek]()
- [Dawid Wydra](https://github.com/ejsiik)

## About
This project aims to create a mobile application for Android devices that automatically takes a series of photos at specified intervals and a server application that creates a set of photos ready for printing on an A4-sized pdf document. The mobile app continuously displays a camera preview and allows the user to initiate a new photo series with a large button. Before taking each photo, the app signals the user with a configurable sound, and after taking the photo, it signals the user with another distinct sound. At the end of the entire photo series, a different sound signal indicates the end of the program's operation. Each photo is sent to the server application, which operates as a REST API within a local network. Once the entire photo series is completed, the server application creates an output document containing the series of photos, ready for printing.

To run the mobile application, simply install it on an Android device and launch it. The server application should be run on a machine within the local network. To configure the sound signals and time intervals, refer to the application settings. For any issues or inquiries, please refer to the project's issue tracker or contact the developers.

![1fotozabawa](https://user-images.githubusercontent.com/72543889/232085209-86f79c03-436e-4197-bf5e-a8c315708195.png)

Contributions and feedback are welcome and appreciated.
