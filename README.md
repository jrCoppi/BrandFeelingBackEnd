# BrandFeelingBackEnd

Representation of the back end system on Brand Feeling old version, this was the core of the system, it is responsible for making the data analysis of the text data retrieved from social networks and retrieve this data to the redundacy unit

The part works in the way that several* threads are started for the Text mining (leitura), text filtering (filtragem) and finally data analysis (analise).

They all work together at the same time using synchronized functions to maximize the system's performance.

The system used to recieve the call to start the process from the RMI control unit.

Some parts were changed, and some libraries are not imported to maintain the system's privacy.

* the number of treads for each part is estimated based on the period (each day corresponds to one more tread).


Packages:
 - Dados: Contains the system base class, some parts and class were ommited.\\
 - RMI: Control part of the system, recive the call to start the process, and controled the execution throught the process.\\
 - Leitura: Text mine the social network data, and pass the rought data to the next part of the process\\
 - Filtragem: Filter the data, separating the actual text the users sent from the HTML, also apply some technics to clean noises on the text\\
 - Analise :  Makes the sentimental analysis based on the text filtered previously, defines the text value based on some algorithm
 
Each of the backend instances is called by the reddundancy system (https://github.com/jrCoppi/BrandFeelingRedundancy) and also recieves the user input from the front end system (https://github.com/jrCoppi/BrandFeelingFrontEnd).
