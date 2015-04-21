ALTER TABLE APP.OFFICES ALTER COLUMN OFFICECODE NOT NULL;
ALTER TABLE APP.OFFICES ADD PRIMARY KEY (OFFICECODE);

ALTER TABLE APP.EMPLOYEES ALTER COLUMN EMPLOYEENUMBER NOT NULL;
ALTER TABLE APP.EMPLOYEES ADD PRIMARY KEY (EMPLOYEENUMBER);

ALTER TABLE APP.CUSTOMERS ALTER COLUMN CUSTOMERNUMBER NOT NULL;
ALTER TABLE APP.CUSTOMERS ADD PRIMARY KEY (CUSTOMERNUMBER);

ALTER TABLE APP.ORDERS ALTER COLUMN ORDERNUMBER NOT NULL;
ALTER TABLE APP.ORDERS ADD PRIMARY KEY (ORDERNUMBER);

ALTER TABLE APP.PRODUCTS ALTER COLUMN PRODUCTCODE NOT NULL;
ALTER TABLE APP.PRODUCTS ADD PRIMARY KEY (PRODUCTCODE);

ALTER TABLE APP.PAYMENTS ALTER COLUMN CUSTOMERNUMBER NOT NULL;
ALTER TABLE APP.PAYMENTS ALTER COLUMN CHECKNUMBER NOT NULL;
ALTER TABLE APP.PAYMENTS ADD PRIMARY KEY (CUSTOMERNUMBER, CHECKNUMBER);

ALTER TABLE APP.ORDERDETAILS ALTER COLUMN ORDERNUMBER NOT NULL;
ALTER TABLE APP.ORDERDETAILS ADD PRIMARY KEY (ORDERNUMBER);

ALTER TABLE APP.EMPLOYEES
ADD CONSTRAINT FK_EMP_OFF
  FOREIGN KEY (OFFICECODE) REFERENCES OFFICES (OFFICECODE);


ALTER TABLE APP.ORDERDETAILS
ADD CONSTRAINT FK_ORDDET_PROD
FOREIGN KEY (PRODUCTCODE) REFERENCES PRODUCTS (PRODUCTCODE);

ALTER TABLE APP.CUSTOMERS
ADD FOREIGN KEY (salesrepemployeenumber) REFERENCES EMPLOYEES (EMPLOYEENUMBER);

ALTER TABLE APP.ORDERS
ADD FOREIGN KEY (CUSTOMERNUMBER) REFERENCES CUSTOMERS (CUSTOMERNUMBER);


ALTER TABLE APP.PRODUCTLINES ALTER COLUMN PRODUCTLINE NOT NULL;
ALTER TABLE APP.PRODUCTLINES ADD PRIMARY KEY (PRODUCTLINE);

ALTER TABLE APP.PRODUCTS
ADD constraint fk_prod_prodline FOREIGN KEY (PRODUCTLINE) REFERENCES PRODUCTLINES (PRODUCTLINE);

ALTER TABLE APP.ORDERDETAILS
ADD FOREIGN KEY (ORDERNUMBER) REFERENCES ORDERS (ORDERNUMBER);