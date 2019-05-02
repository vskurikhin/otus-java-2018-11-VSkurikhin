@ECHO OFF
SET CS=encoding=UTF-8
SET CP=-cp "target\classes;target\dependency\*;target\*"
SET JAVA_OPTS=%CP% -Dfile.%CS% -Dsun.stdout.%CS% -Dsun.err.%CS%
CHCP 65001
java %JAVA_OPTS% -jar ./target/homework15-15.0-0.jar < fake_books.csv
