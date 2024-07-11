USE ops;

SELECT * FROM customer;

SELECT Dname, Speciality FROM doctor;

SELECT * FROM employee WHERE Salary = '50000';

SELECT * FROM customer JOIN invoice ON customer.CID = invoice.invoiceID;

SELECT * FROM medicine ORDER BY medID DESC;

SELECT * FROM medicine LIMIT 10;

UPDATE medicine SET Quantity = 55 WHERE Quantity = 49;

INSERT INTO doctor(docID, Dname, Gender, Contact, Speciality, Address) 
VALUES 
(101, 'Himanshu Chauhan', 'Male', 9654393760, 'Neuro-Surgeon', 'New Delhi');

DELETE FROM doctor WHERE Dname = 'Himanshu Chauhan';

SELECT role, COUNT(*) FROM login GROUP BY role;

SELECT customer.CID, invoice.Quantity, invoice.Amount 
FROM customer 
JOIN invoice ON customer.CID = invoice.CID;

SELECT Cname, COUNT(invoiceId) as num_invoice
FROM customer 
LEFT JOIN invoice ON customer.CID = invoice.CID
GROUP BY customer.CID LIMIT 10;

SELECT Cname, AVG(invoice.Amount) as total
FROM customer
LEFT JOIN invoice ON customer.CID = invoice.CID
GROUP BY customer.CID
ORDER BY total DESC
LIMIT 10;

SELECT DISTINCT pharName
FROM pharmacy
WHERE State IS NULL;

SELECT CID, Cname, Contact 
FROM customer 
WHERE CID IN (SELECT invoice.CID FROM invoice WHERE invoice.CID < 50)
UNION 
SELECT docID, Dname, Contact 
FROM doctor 
WHERE docID IN (SELECT invoice.docID FROM invoice WHERE invoice.docID >= 50);

SELECT Cname, 'customer' AS type FROM customer 
UNION 
SELECT Ename, 'employee' AS type FROM employee
UNION 
SELECT Dname, 'doctor' AS type FROM doctor;

SELECT customer.CID, Cname, invoice.CID FROM customer, invoice 
WHERE customer.CID = invoice.CID
AND
invoice.CID > 50; 

SELECT CID, Cname 
FROM customer 
WHERE CID NOT IN (SELECT DISTINCT CID FROM invoice);

SELECT pharName, CONCAT(Address, ', ', City, ', ', Location, ', ', Pincode) AS address 
FROM pharmacy;

SELECT Cname AS CustomerName, Dname AS DoctorName 
FROM customer 
JOIN invoice ON customer.CID = invoice.CID 
JOIN doctor ON invoice.docID = doctor.docID;

SELECT Cname AS CustomerName, Mname AS MedicineName 
FROM customer 
JOIN invoice ON customer.CID = invoice.CID 
JOIN medicine ON invoice.CID = medicine.medID 
GROUP BY customer.CID
HAVING SUM(medicine.Quantity * medicine.Price) > 1000;

SELECT CID FROM customer
INTERSECT
SELECT CID FROM invoice;

SELECT medID FROM invoice
INTERSECT
SELECT medID FROM medicine;