CREATE DATABASE ManagementEducational;
USE ManagementEducational;

--Tabele--

CREATE TABLE Users (
   idUser INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
   userName VARCHAR(50) NOT NULL,
   passwordUser VARCHAR(50) NOT NULL,
   imagine VARCHAR(255) NOT NULL,
   nume VARCHAR(50) NOT NULL,
   prenume VARCHAR(50) NOT NULL,
   email VARCHAR(100) NOT NULL,
   DOB date NOT NULL
);


CREATE TABLE Elevi(
   idElev INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
   numeElev VARCHAR(50) NOT NULL,
   prenumeElev VARCHAR(50) NOT NULL,
   patronimicElev VARCHAR(50),
   DataNasterii Date NOT NULL,
   IDNP VARCHAR(13) NOT NULL,
   email VARCHAR(100) NOT NULL,
   telefon VARCHAR(9) NOT NULL
);

CREATE TABLE Disciplini(
   idDisciplina int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
   disciplina VARCHAR(70)NOT NULL
);

CREATE TABLE Note(
   idNote int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
   nota int NOT NULL,
   idElev int NOT NULL,
   idDisciplina int NOT NULL,
   FOREIGN KEY (idElev) REFERENCES Elevi(idElev),
   FOREIGN KEY (idDisciplina) REFERENCES Disciplini(idDisciplina)
);

