# Blog API CI/CD Grupparbete


## Render

- Dev: https://grp1-ci-cd-dev.onrender.com/api/blogs

- Main: https://grp1-ci-cd-main.onrender.com/api/blogs

 

## 1. Projektets mål

Vi ska bygga ett enkelt REST API med Java Spring Boot för att hantera blogginlägg.


Projektet ska visa CI/CD flöde med:

- Java Spring Boot
- REST API
- Mockad/in-memory-data (Vi använder ingen extern databas)
- Automatiserade tester
- Docker
- GitHub Actions
- GitHub Repository secrets
- Render DEV
- Render MAIN/PRODUCTION
- Git branches och Pull Requests


---


# 2. Applikationsarkitektur


## Controller

Ansvarar för:

- REST endpoints (CRUD operationes) 
- HTTP requests
- HTTP responses
- HTTP status codes


---

## Service

Ansvarar för:

- Business logic
- Validering
- Kontrollera att en post finns
- Skapa posts
- Uppdatera posts
- Ta bort posts

Controller ska inte innehålla all business logic.

---

## Repository

Vi använder ett enkelt in-memory repository. (Godkännt från läraren)

Repository ansvarar för:

- Lagra posts
- Hämta posts
- Hitta post via ID
- Uppdatera post
- Ta bort post


---

# 3. Projektstruktur

```text
blog-api/
│
├── .github/
│   └── workflows/
│       ├── test.yml
│       ├── deploy-dev.yml
│       └── deploy-main.yml
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/blogapi/
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   └── Controller.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   └── Service.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   └── Repository.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   └── Blog.java
│   │   │       │
│   │   │       └── BlogApiApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/example/blogapi/
│               │
│               ├── controller/
│               │   └── ControllerTest.java
│               │
│               └── service/
│                   └── ServiceTest.java
│
├── Dockerfile
├── pom.xml
├── README.md
└── .gitignore
```

---

# 4. Git Branch regler att komma ihåg

1. Ingen push direkt till `main`.
2. Ingen push direkt till `dev`.
3. All ny utveckling sker på `feature/` branches.
4. Feature branches skapas från `dev`.
5. Feature branches mergas till `dev` genom Pull Request.
6. `dev` mergas till `main` genom Pull Request.
7. CI måste vara godkänd innan merge.
8. Ändringar ska testas på DEV innan de skickas till MAIN.
9. Glöm inte att köra `pull` innan man ändra något (för att undvika merge-conflict)

---


# 5. Docker

Vi använder en Dockerfile för både DEV och MAIN, och två olika Docker images för DEV & MAIN.

---

# 6. Render

Vi använder två separata Render services.

- Render DEV

- Render MAIN

---

  

# 7. Arbetsfördelning – 6 personer

## Person 1 – Project Setup & GitHub

Ansvar: Jonas

- Skapa Spring Boot-projekt
- Skapa GitHub repository
- Skapa `main`
- Skapa `dev`
- Konfigurera branch protection och permissions, ge alla collaborators full åtkomest till repository 
- Hjälpa gruppen med Git och Pull Requests
- Lägg till alla mapper enligt artiktekturen 


---

## Person 2 – Model & Mock Repository

Ansvar: Caroline

- Skapa `Blog` /model
- Skapa `Repository`
- Implementera `List<Post>`
- Lägga till exempeldata "Mockad data"
- Implementera CRUD-operationer i repository


Branch:

```text
feature/model
```

---

## Person 3 – REST Controller

Ansvar: Ludvig

- Skapa `Controller`
- Implementera REST endpoints
- GET
- POST
- PUT
- DELETE
- HTTP status codes


Branch:

```text
feature/controller
```

---

## Person 4 – Service & Business Logic

Ansvar: Martina

- Skapa `Service`
- Implementera business logic
- Validering
- Kontrollera om post finns
- Kommunikation mellan Controller och Repository

Branch:

```text
feature/service
```

---

## Person 5 – Testing

Ansvar: Hanad

- Skriva unit tests
- Testa `Service`
- Testa `Controller`
- Testa olika scenarier
- Testa felhantering
- Kontrollera test coverage


Branch:

```text
feature/tests
```

Alla utvecklare ska samtidigt skriva minst en test som tester det viktigeste för sin egen kod. Person 5 ansvarar för teststrategin och hjälper gruppen med testerna.

---

## Person 6 – Docker & CI/CD

Ansvar: Moody 

- Skapa Dockerfile
- Skapa `test.yml`
- Skapa `deploy-dev.yml`
- Skapa `deploy-main.yml`
- Konfigurera docker images
- Konfigurera Render DEV
- Konfigurera Render MAIN
- Testa Docker deployment
- Testa hela CI/CD-flödet



Branches 

```text
feature/docker
feature/ci-cd
```

Person 6 samarbetar med Person 1 kring GitHub branch protection och permissions.

---

# 8. Gemensamma regler

Alla sex personer ska:
- Göra en ny `pull` på repot innan man göra ändringar
- Arbeta på `feature/*` branches.
- Skapa Pull Requests.
- Göra code review.
- Skriva tester för sin kod.
- Inte pusha direkt till `dev`.
- Inte pusha direkt till `main`.
- Testa funktioner på Render DEV.
- Endast skicka testad kod från `dev` till `main`.

---

# 9. Definition of Done

En funktion räknas som klar när:

```text
[x] Kod är implementerad
[x] Tester är skrivna
[x] Tester passerar
[x] Pull Request är skapad
[x] Code review är gjord
[x] CI är godkänd
[x] PR är mergad till dev
[x] Docker image för DEV är byggd
[x] Funktionen är deployad till Render DEV
[x] Funktionen är testad på DEV
[x] PR från dev till main är skapad
[x] CI är godkänd igen
[x] PR är mergad till main
[x] Docker image för MAIN är byggd
[x] Funktionen är deployad till Render MAIN
```

---

Lycka till! :)) 
