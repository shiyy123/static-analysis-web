DIR=`cd \`dirname ${BASH_SOURCE[0]}\`/.. && pwd`

cd android-analysis-server

mvn package

profile='dev'

while getopts "p:" OPT; do
    case $OPT in
        p)
            profile=$OPTARG
            ;;
    esac
done

screen java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$profile target/android-analysis-server.jar