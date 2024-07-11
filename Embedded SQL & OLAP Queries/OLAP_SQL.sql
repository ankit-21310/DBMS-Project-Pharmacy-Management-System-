show databases;
use ops;
show tables;

-- OLAP Queries..
SELECT invoice.medID, SUM(Amount) as Total
FROM invoice
JOIN medicine ON medicine.medID = invoice.medID
JOIN customer ON invoice.CID = customer.CID
GROUP BY medID;

SELECT customer.Cname, SUM(Amount) as Total
FROM invoice
JOIN customer ON invoice.CID = customer.CID
GROUP BY invoice.CID
ORDER BY Total DESC
LIMIT 10;

SELECT COUNT(DISTINCT invoice.medID) / COUNT(DISTINCT medicine.medID) * 100 as Percentage
FROM medicine
JOIN invoice ON medicine.medID = invoice.medID
JOIN customer ON invoice.CID = customer.CID
WHERE medicine.Mname = 'Quinine Sulfate';

SELECT medicine.Mname, SUM(Amount) as Total
FROM invoice
JOIN medicine ON invoice.medID = medicine.medID
JOIN customer ON invoice.CID = customer.CID
GROUP BY Mname;

-- TRIGGER Queries...

CREATE TRIGGER tQuery01
AFTER INSERT ON invoice
FOR EACH ROW
BEGIN
    UPDATE medicine
    SET Quantity = Quantity - NEW.Quantity
    WHERE medID = NEW.medID;
END;

CREATE TRIGGER tQuery02
AFTER INSERT ON employee
FOR EACH ROW
BEGIN
  UPDATE login
  SET Role = Role + 1
  WHERE EID = NEW.EID;
END;
