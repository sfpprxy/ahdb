echo '> stash'
git stash

echo '> pull'
git pull

echo '> kill old'
kill $(cat ./AHDBPID)

echo '> start'

cd ./apps-living-server/
nohup mvn clean spring-boot:run -Dprofile=test -Dserver.port=9999./server.log &
