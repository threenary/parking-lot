#!/bin/sh
arg1=$1
##directory where jar file is located
dir=build/libs
##jar file name
jar_name=ParkingLot-1.0-SNAPSHOT.jar

./gradlew clean
./gradlew build

if [ -z "$1" ] ; then
        java -jar $dir/$jar_name
        exit 1

else
	java -jar $dir/$jar_name $arg1

fi
