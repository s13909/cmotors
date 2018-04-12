#!/bin/bash


cd $(dirname $0)

if [ ! -f hsqldb.jar ]; then
    wget http://puzniakowski.pl/repo/libs/hsqldb.jar
fi

cd ..

#in memory database
java -cp scripts/hsqldb.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb &

#in file database
#java -cp lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:/tmp/mydb --dbname.0 workdb
