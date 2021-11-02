# extract-stats
This repository aims to process XML data in order to extract and compute targeted statistics from a football match. 

## Short Description
A 4-tiers architecture has been used to seperate the different layers of the application: 
- Presentation 
- Parser 
- Transformer 
- Model 

The user can only put 2 arguments in the command line: the path to the XML file to be extracted and the targeted statistic. The class Validator will handle all these prerequisites. If the arguments are not compatible, the application will automatically exit providing the reason of the failure.    

To be able to print the list of the errors I made use of ValidatedNel a pre-build [Validated type](https://typelevel.org/cats/datatypes/validated.html) from [cats](https://typelevel.org/cats/) that uses NonEmptyList as its error type. I handled the potential errors with ValidatedNel (from wrong arguments to xml extraction) and isolated the side effects using [IO data type](https://typelevel.org/cats-effect/docs/2.x/datatypes/io)  from [cats-effect](https://typelevel.org/cats-effect/).   

The architecture of the app has been made this way to enable future development easily: depending on the XML to extract new Parsers could be implemented, using other kind of Transformer and models.

## Prerequesites
The program has been implemented using the following versions:
* sbt 1.5.5
* Java 11.0.12
* Scala 2.13.6.

## Tests
Potential errors coming from a defected XML file or incorrect command-line arguments have been isolated and tested through the ValidatorSpec test class. This covers Presentation and Parser layers. Unitary tests could also be added to ensure the correctness of the Transformer and Model layers. 
To run the tests, run the command 
```sh 
sbt test
``` 
from the root directory of this program.

## How to use
1. Get the absolute path where is located your XML file
2. From the root directory of the project, run the following command: 
```sh
sbt "run [xml_path] [stat_name]"
```  

where, xml_path is replaced by your xml absolute file path (from root directory) and the stat_name is the targeted statistic. 

CAUTIOUS: The "" are necessary in the former command.


As a running example, some text files have been placed in the directory src/main/resources/, you can run the program for this directory by running 
```sh 
sbt "run src/main/resources/matchState.xml successful_open_play_pass"
```   

You can also run a wrong command and see how the errors are handled in the console, as an example: 
```sh 
sbt "run src/main/resources/matchState.xml successful_open_play_pass 35"
```  
