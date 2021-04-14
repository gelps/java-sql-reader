# Java SQL Queries

## Intro
This program allows a user to load a table and query the table using limited SQL features. Run the Main class in the
'program' package to start the UI.

## Loading a Table
A table can be loaded using the UI by placing the file inside the 'tablefiles' folder and specifying the name of the file.

### Example - Load the 'Canadian Cities' Table
![Loading Table GUI Image](https://drive.google.com/uc?id=1Fm3ZkNCXPTPbtWr3PU0q2gprpn4oyj5B)

A table is specified in a text file and loaded by the TableLoader. Each table should be entered as a text file in the
following format:
- Line 1 : Table Name
- Line 2 : Column Names
- Line 3 : Column Types (String or Int only)
- Lines 4 & 5 : leave empty
- Line 6 onwards : individual rows

Refer to the example tables in the folder ['tablefiles'](https://github.com/gelps/java-sql-reader/tree/main/src/tablefiles)

## Queries
Each query must be entered as follows. Use exactly 1 space to separate between each element in the query except for lists
where a comma should be used without any spaces. The queries are parsed by the QueryParser which converts an SQL-like statement
into the internal representation.

At this time, the queries only support SELECT, FROM, and WHERE.

##### FROM

- Currently, only 1 table can be queried at a time; however a potential way to add the ability to query multiple tables is
to generate a new, temporary table by taking the cartesian product of the multiple tables

##### WHERE

- Currently, columns can not be compared with other columns; they can only be compared with a given value.
- This feature can be added after the ability the query multiple tables is added by slightly changing the way a SingleCondition
is evaluated.

### Example Query on the 'Canadian Cities' Table
    SELECT * FROM Canadian Cities WHERE (AND (IN province BC,AB,ON,QC) (OR (GEQ airport YV) (EQ airport YEG)))

![Sample Query Results Image 1](https://drive.google.com/uc?id=1Ii0wlNwvmoNNQTPsvZUHMYjhp3lNOu2L)

### Example Query on the 'Northwest Manufacturing School' Table
    SELECT name FROM Northwest Manufacturing School WHERE (AND (EQ program Helicopter Repair Shop Sales Specialist) (LT year 3))

![Sample Query Results Image 2](https://drive.google.com/uc?id=16cQ2nWKlMdouRdiuEYepNBl4eY-UtfVo)
