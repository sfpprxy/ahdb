#!/usr/bin/env bash
echo pwd
echo ${pwd}
pwd

echo '> env keys'
echo GVAR is ${GVAR}
echo JVAR is ${JVAR}

echo V1
echo ${V1}
echo "$V1"
echo V2
echo $V2

V3=ASDASD
V4=${V3}+BBB
echo ${V4}
V5=${V1}+BBB=${V2}
echo ${V5}

if [ ${DB} == "-Dquarkus.http.port" ]; then
  echo "11=="
else
  echo "11!="
fi

if [ "${DB}" == "-Dquarkus.http.port" ]; then
  echo "22=="
else
  echo "22!="
fi

echo '> package'
mvn package -Pnative

echo '> start'
