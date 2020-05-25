This course assumes that you are familiar with databases in general, SQL databases in particular, and the SQL language used to interact with them.

This page is a refresher and quick reference.

SQL databases

SQL databases store data in tables of rows and columns:

The intersection of a row and column is called a field.
Fields contain data, references to other fields, or references to other tables.
Each row contains one entity identified by a unique ID, which is usually used as its primary key.
Each column is identified by a name that is unique per table.
SQLite

SQLite implements an SQL database engine that has the following characteristics:

Self-contained (requires no other components)
Serverless (requires no server backend)
Zero-configuration (does not need to be configured for your app)
Transactional (changes within a single transaction in SQLite either occur completely or not at all)
SQLite is the most widely deployed database engine in the world. The source code for SQLite is in the public domain. For details of the SQLite database, see the SQLite website.

Example table

A database named DATABASE_NAME
A table named WORD_LIST_TABLE
Columns for _id, word, and definition
After inserting the words alpha and beta, where alpha has two definitions, the table might look like this:
DATABASE_NAME > WORD_LIST_TABLE

_id	Word	Definition
1	"alpha"	"first letter"
2	"beta"	"second letter"
3	"alpha"	"particle"
You can find what's in a specific row using the _id, or you can retrieve rows by formulating queries that select rows from the table by specifying constraints.

Query language

You use the SQL query language to interact with the database. Queries can be very complex, but there are four basic operations:

Inserting rows
Deleting rows
Updating values in rows
Retrieving rows that meet given criteria
On Android, the data access object (DAO) provides convenience methods for inserting, deleting, and updating the database.

Full description of the query language

Query structure

An SQL query is highly structured and here is an example:

SELECT word, definition FROM WORD_LIST_TABLE WHERE word="alpha"

Here is the generic version of the sample query:

SELECT columns FROM table WHERE column="value"

Parts of a query:

SELECT columns: Select the columns to return. Use * to return all columns.
FROM table: Specify the table from which to get results.
WHERE: Optional keyword that precedes conditions that have to be met, for example column="value". Common operators are =, *, LIKE, <, and >. To connect multiple conditions, use AND or OR.
Also:

ORDER BY: Optional key phrase for ordering results by the specified column. Specify ASC for ascending and DESC for descending. If you don't specify an order, you will get the default order, which may be unordered.
LIMIT: Keyword to specify a limited number of results.
Sample queries and results

The following queries use the previously defined table WORD_LIST_TABLE.

Query	Result
SELECT * FROM WORD_LIST_TABLE	Gets all the rows in WORD_LIST_TABLE table.
SELECT word, definition FROM WORD_LIST_TABLE WHERE _id > 2	Selects the word and definition columns of all items with an id greater than 2. Returns[["alpha", "particle"]]
SELECT _id FROM WORD_LIST_TABLE WHERE word="alpha" AND definition LIKE "%art%"	Returns the id of the word alpha with the substring art in the definition. [["3"]]
SELECT definition FROM WORD_LIST_TABLE ORDER BY word DESC LIMIT 1	Selects all definitions. Sorts in reverse and gets the first row after the list is sorted. Sorting is by the column specified which is word. Note that we can sort by a column that we don't return! [["second letter"]]
SELECT * FROM WORD_LIST_TABLE LIMIT 2,1	Returns 1 item starting at position 2. Position counting starts at 1 (not zero!). Returns [["2", "beta", "second letter"]]