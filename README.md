# NetworkOntologyGenerator
Network Ontology Generator 

This project is for generating a network ontology for Internet network or IoT network

## How to run?

1. Clone this project and build with maven

2. Edit configuration in Config.java

3. Run GeneratorApp.java

4. OutputFile is in ./src/main/resources/ (as default)

## Output format:

- From 1st line to the line before the last line:
NodeID [NeigbhorID1, NeigbhorsID2, ..., NeigbhorsIDn]

- Last line is the backbone nodes of the network.
