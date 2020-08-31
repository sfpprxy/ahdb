#!/usr/bin/env bash
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

echo '> package'

echo '> start'
