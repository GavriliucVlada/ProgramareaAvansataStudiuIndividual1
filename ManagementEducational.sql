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

select * from Users

CREATE VIEW vwUserCount AS
SELECT COUNT(*) AS TotalUsers
FROM Users;

select * from vwUserCount



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


INSERT INTO Elevi (numeElev, prenumeElev, patronimicElev, DataNasterii, IDNP, email, telefon) VALUES
('Popescu', 'Ion', 'Dumitru', '2006-08-13', '2600813000012', 'ion.popescu@mail.md', '079123456'),
('Gavriliuc', 'Vlada', 'Alexandru', '2006-08-13', '2060813000021', 'vlada.gavriliuc@mail.md', '079123457'),
('Ciobanu', 'Maria', 'Petru', '2007-05-22', '2070522000038', 'maria.ciobanu@mail.md', '079123458'),
('Rusu', 'Andrei', 'Ion', '2005-11-03', '2051103000045', 'andrei.rusu@mail.md', '079123459'),
('Savu', 'Elena', 'Mihai', '2008-02-17', '2080217000052', 'elena.savu@mail.md', '079123460'),
('Moraru', 'Alex', 'Dumitru', '2006-09-25', '2060925000069', 'alex.moraru@mail.md', '079123461'),
('Ivanov', 'Ana', 'Petru', '2007-07-10', '2070710000076', 'ana.ivanov@mail.md', '079123462'),
('Lungu', 'Diana', 'Andrei', '2005-12-30', '2051230000083', 'diana.lungu@mail.md', '079123463'),
('Cojocaru', 'Victor', 'Ion', '2006-03-05', '2060305000090', 'victor.cojocaru@mail.md', '079123464'),
('Matei', 'Sofia', 'Mihai', '2008-06-18', '2080618000107', 'sofia.matei@mail.md', '079123465'),
('Chirila', 'Ion', 'Alexandru', '2007-09-09', '2070909000114', 'ion.chirila@mail.md', '079123466'),
('Balan', 'Roxana', 'Dumitru', '2005-04-27', '2050427000121', 'roxana.balan@mail.md', '079123467'),
('Ciobanu', 'Cristian', 'Petru', '2006-01-15', '2060115000138', 'cristian.ciobanu@mail.md', '079123468'),
('Platon', 'Adela', 'Ion', '2007-08-03', '2070803000145', 'adela.platon@mail.md', '079123469'),
('Sandu', 'Andreea', 'Mihai', '2005-06-11', '2050611000152', 'andreea.sandu@mail.md', '079123470'),
('Rotaru', 'Alexandru', 'Diana', '2006-10-22', '2061022000169', 'alexandru.rotaru@mail.md', '079123471'),
('Munteanu', 'Larisa', 'Ion', '2007-03-29', '2070329000176', 'larisa.munteanu@mail.md', '079123472'),
('Savca', 'Vlad', 'Dumitru', '2005-09-14', '2050914000183', 'vlad.savca@mail.md', '079123473'),
('Gavril', 'Elena', 'Petru', '2006-12-08', '2061208000190', 'elena.gavril@mail.md', '079123474'),
('Morari', 'Andrei', 'Mihai', '2007-11-21', '2071121000207', 'andrei.morari@mail.md', '079123475'),
('Petrov', 'Maria', 'Alexandru', '2005-05-16', '2050516000214', 'maria.petrov@mail.md', '079123476'),
('Dodon', 'Ion', 'Dumitru', '2006-07-19', '2060719000221', 'ion.dodon@mail.md', '079123477'),
('Toma', 'Ana', 'Petru', '2007-10-30', '2071030000238', 'ana.toma@mail.md', '079123478'),
('Ceban', 'Vlad', 'Mihai', '2005-01-05', '2050105000245', 'vlad.ceban@mail.md', '079123479'),
('Filip', 'Roxana', 'Alexandru', '2006-04-14', '2060414000252', 'roxana.filip@mail.md', '079123480'),
('Andronache', 'Cristian', 'Ion', '2007-06-07', '2070607000269', 'cristian.andronache@mail.md', '079123481'),
('Caraman', 'Larisa', 'Dumitru', '2005-02-28', '2050228000276', 'larisa.caraman@mail.md', '079123482'),
('Babii', 'Elena', 'Mihai', '2006-09-12', '2060912000283', 'elena.babii@mail.md', '079123483'),
('Tanas', 'Andrei', 'Petru', '2007-12-19', '2071219000290', 'andrei.tanas@mail.md', '079123484'),
('Savciuc', 'Maria', 'Alexandru', '2005-03-23', '2050323000307', 'maria.savciuc@mail.md', '079123485'),
('Apostol', 'Gheorghe', 'Ion', '2001-09-20', '2004599872166', 'aposrol.gheorghe@mail.md', '079337880'),
('Oboroc', 'Giulia', 'Ruslan', '2007-02-02', '2050323444307', 'giulia.oboroc@mail.md', '033323485'),
('Celpan', 'Alexandra', 'Petru', '2003-11-30', '2044323123307', 'celpan.alexandra@mail.md', '079121234');




CREATE VIEW vwElevCount AS
SELECT COUNT(*) AS TotalElevi
FROM Elevi;

select * from vwElevCount




select * from Elevi


CREATE TABLE Disciplini(
   idDisciplina int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
   disciplina VARCHAR(70)NOT NULL
);

CREATE TABLE Disciplini(
   idDisciplina INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
   disciplina VARCHAR(70) NOT NULL
);

INSERT INTO Disciplini (disciplina) VALUES
('Mathematics'),
('Romanian Language and Literature'),
('English Language'),
('Computer Science'),
('Physics'),
('Chemistry'),
('Biology'),
('Geography'),
('History'),
('Physical Education');

SELECT * FROM Disciplini


CREATE VIEW vwDisciplinaCount AS
SELECT COUNT(*) AS TotalDiscipline
FROM Disciplini;

select * from vwDisciplinaCount


CREATE TABLE Note(
   idNote int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
   nota int NOT NULL,
   idElev int NOT NULL,
   idDisciplina int NOT NULL,
   FOREIGN KEY (idElev) REFERENCES Elevi(idElev),
   FOREIGN KEY (idDisciplina) REFERENCES Disciplini(idDisciplina)
);

CREATE VIEW vwNoteCount AS
SELECT COUNT(*) AS TotalNotes
FROM Note;

select * from vwNoteCount

INSERT INTO Note (nota, idElev, idDisciplina) VALUES
(8, 4, 1),
(9, 4, 2),
(7, 4, 3),
(10, 4, 4),
(6, 4, 5),
(9, 5, 1),
(8, 5, 2),
(7, 5, 3),
(6, 5, 4),
(10, 5, 5),
(8, 6, 1),
(9, 6, 2),
(7, 6, 3),
(8, 6, 4),
(9, 6, 5),
(10, 7, 1),
(6, 7, 2),
(8, 7, 3),
(9, 7, 4),
(7, 7, 5),
(8, 8, 1),
(9, 8, 2),
(10, 8, 3),
(6, 8, 4),
(7, 8, 5),
(9, 9, 1),
(8, 9, 2),
(7, 9, 3),
(10, 9, 4),
(6, 9, 5),
(8, 10, 1),
(9, 10, 2),
(7, 10, 3),
(8, 10, 4),
(9, 10, 5),
(10, 11, 1),
(6, 11, 2),
(7, 11, 3),
(8, 11, 4),
(9, 11, 5),
(8, 12, 1),
(7, 12, 2),
(9, 12, 3),
(10, 12, 4),
(6, 12, 5),
(7, 13, 1),
(8, 13, 2),
(9, 13, 3),
(10, 13, 4),
(6, 13, 5),
(7, 14, 1),
(8, 14, 2),
(9, 14, 3),
(10, 14, 4),
(6, 14, 5),
(8, 15, 1),
(9, 15, 2),
(7, 15, 3),
(10, 15, 4),
(6, 15, 5),
(9, 16, 1),
(8, 16, 2),
(7, 16, 3),
(6, 16, 4),
(10, 16, 5),
(8, 17, 1),
(9, 17, 2),
(10, 17, 3),
(6, 17, 4),
(7, 17, 5),
(9, 18, 1),
(8, 18, 2),
(7, 18, 3),
(10, 18, 4),
(6, 18, 5),
(7, 19, 1),
(8, 19, 2),
(9, 19, 3),
(10, 19, 4),
(6, 19, 5),
(8, 20, 1),
(9, 20, 2),
(7, 20, 3),
(10, 20, 4),
(6, 20, 5),
(7, 21, 1),
(8, 21, 2),
(9, 21, 3),
(10, 21, 4),
(6, 21, 5),
(8, 22, 1),
(9, 22, 2),
(7, 22, 3),
(10, 22, 4),
(6, 22, 5),
(7, 23, 1),
(8, 23, 2),
(9, 23, 3),
(10, 23, 4),
(6, 23, 5),
(8, 24, 1),
(9, 24, 2),
(7, 24, 3),
(10, 24, 4),
(6, 24, 5),
(7, 25, 1),
(8, 25, 2),
(9, 25, 3),
(10, 25, 4),
(6, 25, 5),
(8, 26, 1),
(9, 26, 2),
(7, 26, 3),
(10, 26, 4),
(6, 26, 5),
(7, 27, 1),
(8, 27, 2),
(9, 27, 3),
(10, 27, 4),
(6, 27, 5),
(8, 28, 1),
(9, 28, 2),
(7, 28, 3),
(10, 28, 4),
(6, 28, 5),
(7, 29, 1),
(8, 29, 2),
(9, 29, 3),
(10, 29, 4),
(6, 29, 5),
(8, 30, 1),
(9, 30, 2),
(7, 30, 3),
(10, 30, 4),
(6, 30, 5),
(7, 31, 1),
(8, 31, 2),
(9, 31, 3),
(10, 31, 4),
(6, 31, 5),
(8, 32, 1),
(9, 32, 2),
(7, 32, 3),
(10, 32, 4),
(6, 32, 5),
(7, 33, 1),
(8, 33, 2),
(9, 33, 3),
(10, 33, 4),
(6, 33, 5);


CREATE VIEW VwEleviNote AS
SELECT 
    e.numeElev,
    e.prenumeElev,
    e.patronimicElev,
    d.disciplina,
    n.nota
FROM Note n
JOIN Elevi e ON n.idElev = e.idElev
JOIN Disciplini d ON n.idDisciplina = d.idDisciplina;

select * from VwEleviNote


CREATE VIEW VwTop10Elevi AS
SELECT TOP 10
    e.numeElev,
    e.prenumeElev,
    e.patronimicElev,
    CAST(AVG(n.nota * 1.0) AS DECIMAL(4,2)) AS medie
FROM Note n
JOIN Elevi e ON n.idElev = e.idElev
GROUP BY e.numeElev, e.prenumeElev, e.patronimicElev
ORDER BY medie DESC;

select * from VwTop10Elevi

CREATE TABLE Prezente (
    idPrezenta INT IDENTITY(1,1) PRIMARY KEY,
    idElev INT NOT NULL,
    idDisciplina INT NOT NULL,
    data DATE NOT NULL,
    prezent BIT NOT NULL,
    FOREIGN KEY (idElev) REFERENCES Elevi(idElev),
    FOREIGN KEY (idDisciplina) REFERENCES Disciplini(idDisciplina)
);

select*from Prezente;



