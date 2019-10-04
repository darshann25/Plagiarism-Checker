# PlagiarismChecker
Basic command-line program that performs plagiarism detection using a N-tuple comparison algorithm allowing for synonyms in the text.
PlagiarismChecker is a command-line program that performs a plagiarism check using N-tuple comparison algorithm for the synonyms in the text, and returns a plagiarism score between 0 to 100.

<h3>Sample Usage using Command Line</h3>

Run with no arguments
```
$ java -jar execute.jar
PRODUCT:
	Plagiarism Checker
EXECUTE:
	java -jar execute.java synonymsFile inputFile1 inputFile2 [tupleSize]
EXPLANATION:
	This project is used to compute the degree of plagiarism between two text files using synonym matching and fixed tuple length
	synonymsFile:	Groups of synonyms contained on separate lines (*.txt)
	inputFile1:	Textfile that will be checked for plagiarism (*.txt)
	inputFile2:	Textfile that will be checked for plagiarism (*.txt)
	tupleSize:	Size of the tuple used for comparison (Optional)

```

Run with arguments
```
arg1 = Groups of synonyms contained on separate lines (*.txt)
arg2 = Textfile that will be checked for plagiarism (*.txt)
arg3 = Textfile that will be checked for plagiarism (*.txt)
arg4 = Size of the tuple used for comparison (Optional)
```

Example
```
$ java -jar execute.jar synonym.txt file1.txt file2.txt

Begin execution...
synonym.txt:
run walk jog

file1.txt:
go for a jog

file2.txt:
go for a run

Number of tuples in file2.txt : 2
Number of tuples in file1.txt : 2
Number of matching tuples : 2

Plagiarism Score = 100.00%
Execution Completed!
```
or
```
$ java -jar execute.jar synonym.txt file1.txt file2.txt 3

Begin execution...
synonym.txt:
run walk jog

file1.txt:
go for a jog

file2.txt:
went for a run

Number of tuples in file2.txt : 2
Number of tuples in file1.txt : 2
Number of matching tuples : 1

Plagiarism Score = 50.00%
Execution Completed!
```
or
```
$ java -jar execute.jar synonym.txt file1.txt file2.txt 4

Begin execution...
synonym.txt:
run walk jog

file1.txt:
go for a jog

file2.txt:
go for a run

Number of tuples in file2.txt : 1
Number of tuples in file1.txt : 1
Number of matching tuples : 1

Plagiarism Score = 100.00%
Execution Completed!
```

<h3>Sample Usage using Eclipse IDE</h3>
```
Open Eclipse IDE -> File -> Import -> PlagiarismChecker.zip
```
Run with no arguments
```
PRODUCT:
	Plagiarism Checker
EXECUTE:
	java -jar execute.java synonymsFile inputFile1 inputFile2 [tupleSize]
EXPLANATION:
	This project is used to compute the degree of plagiarism between two text files using synonym matching and fixed tuple length
	synonymsFile:	Groups of synonyms contained on separate lines (*.txt)
	inputFile1:	Textfile that will be checked for plagiarism (*.txt)
	inputFile2:	Textfile that will be checked for plagiarism (*.txt)
	tupleSize:	Size of the tuple used for comparison (Optional)
```
Run with arguments
```
arg1 = Groups of synonyms contained on separate lines (*.txt)
arg2 = Textfile that will be checked for plagiarism (*.txt)
arg3 = Textfile that will be checked for plagiarism (*.txt)
arg4 = Size of the tuple used for comparison (Optional)

Eclipse IDE -> Run -> Run Configurations -> Arguments -> Program Arguments
```
Example
```

Begin execution...
synonym.txt:
run walk jog

file1.txt:
go for a jog

file2.txt:
go for a run

Number of tuples in file2.txt : 2
Number of tuples in file1.txt : 2
Number of matching tuples : 2

Plagiarism Score = 100.00%
Execution Completed!
```
