mvn versions:set -DnewVersion=$@
mvn -N versions:update-child-modules
mvn versions:commit
