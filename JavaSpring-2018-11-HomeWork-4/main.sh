#!/bin/sh
export LANG=ru_RU.UTF-8
CS="encoding=UTF-8"  
CP="-cp ./target/classes:./target/dependency/*:./target/*"
JAVA_OPTS="$CP -Dfile.$CS -Dsun.stdin.$CS -Dsun.stdout.$CS -Dsun.$CS"
java ${JAVA_OPTS} -jar ./target/homework4-4.0-23.jar
