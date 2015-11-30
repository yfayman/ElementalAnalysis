# ElementalAnalysis

This project allows a user to build libraries and query against them. This is an old project
and there are plans to build a web based version of it on the Spring MVC platform. This current
version runs on the Java FX framework. You can manually enter information regarding a sample
or import XLS files(you can import multiple at a time). The XLS reader can read a variety of
different schemas. It searches for a String with a neighboring number. Data can be entered
as weight percent or atomic percent. Once your table is complete, you can calculate atomic percent
from weight percent or weight percent from atomic percent.

#![Home Screen](/../screenshots/screenshots/ea2.png?raw=true "Home")

Once data is loaded into the program, it can be saved to a pesistence layer using the File -> Save to Main Library
Data that is saved to the main library can be queried against. To query against the main library, hit the search button. This will bring up a list of sample names and how similar they are to your sample. Clicking on any specific sample name will bring up information regarding that sample. My screenshot below is a bit bare. This is due to the fact that my previous library belonged to a former employer. As per the rules, I wiped that data when I left the company.

#![Home Screen](/../screenshots/screenshots/ea3.png?raw=true "Home")

#The Future of This Project
As mentioned above, I would love to turn this into a web application. It can be adapted to save and compare all kinds of data. My plan is to allow users to store their data privately(associated to the account) or publicly and execute queries against private or public libraries. This can be a useful tool for identifying minerals and alloy metals. Another cool possibility is identifying the source of said mineral. Each source has a fingerprint of trace elements that can be used to figure out where a sample came from.
