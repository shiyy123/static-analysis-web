DIR=`cd \`dirname ${BASH_SOURCE[0]}\`/.. && pwd`

rm -rf ${DIR}/android-analysis-common/src/main/java/cn/iselab/android/analysis/thrift/
thrift --gen java:beans -out ${DIR}/android-analysis-common/src/main/java ${DIR}/android-analysis-common/src/main/thrift/AndroidAnalysis.thrift