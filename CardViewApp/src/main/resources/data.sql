INSERT INTO 
	TBL_EMPLOYEES (first_name, last_name, date_of_birth, gender, country, expired, attachments)
VALUES
  	('Lokesh', 'Gupta', '2002-01-01', 'Male', 'India', true, null),
  	('Neha', 'Patel', '2002-02-11', 'Female', 'Australia', false , null),
  	('John', 'Doe', '2002-04-22', 'Male', 'Australia', false , null),
  	('Manish', 'Kumar', '2002-03-05', 'Male', 'India', false , null),
    ('Amar', 'Singh', '2001-02-06', 'Male', 'South Africa', false , null),
    ('Bobby', 'Sinha', '2002-01-01', 'Male', 'India', false , null),
    ('Pranav', 'Kumar', '19922-11-12', 'Male', 'Nigeria', true , null),
    ('Priya', 'Lal', '1986-02-18', 'Female', 'Australia', false , null),
  	('Julia', 'Mary', '2021-08-27', 'Female', 'Nigeria', false, null);

INSERT INTO
	TBL_EXPENSES (exp_type, cust_type, purpose, vend_name, inv_date, inv_no, inv_amt, sanc_amt, clm_amt, remarks, attachments)
VALUES
  	('Entertainment', 'Internal', 'Team Lunch', 'Eden Park', '2021-05-04', '1008', '3000.00', '4000.00', '3000.00', '6 members lunch', null),
  	('Entertainment', 'External', 'Customer Entertainment', 'Eden Park', '2021-05-04', '1008', '5000.00', '5000.00', '5000.00', '5 members lunch', null),
  	('Official', 'Internal', 'Vendor Meet', 'Company', '2021-05-04', '1008', '3000.00', '4000.00', '3000.00', 'Vendor meet', null),
    ('Entertainment', 'External', 'Annual Day', 'Company', '2021-05-04', '1008', '5000.00', '5000.00', '5000.00', 'Annual day arrangements', null),
    ('Travel', 'Internal', 'Client Visit', 'IOB', '2021-05-04', '1008', '3000.00', '4000.00', '3000.00', 'On-site Visit', null),
    ('Miscellaneous', 'External', 'Bill Payment', 'BESCOM', '2021-05-04', '1008', '5000.00', '5000.00', '5000.00', 'Bill Payment', null);
