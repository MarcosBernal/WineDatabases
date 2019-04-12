#! /bin/bash

DIR_PATH=$(dirname "$(realpath "$0")")
PASSWD=secret
C_NAME=mysql_server
DDBB=cataVino

if ! docker ps | grep -q ${C_NAME} ; then
    docker run -d --rm -e MYSQL_ROOT_PASSWORD=$PASSWD -p 3306:3306 -v ${DIR_PATH}/src:/tmp/src:ro --name=${C_NAME} mysql
    sleep 25
    echo "Mysql up and running!"
else
    mysql -h localhost --protocol=tcp -u root -p${PASSWD} -e "drop database ${DDBB}" > /dev/null
    echo "Mysql already up!"
fi

mysql -h localhost --protocol=tcp -u root -p${PASSWD} < src/exercise1/create_tables.sql
echo "Created Tables!!"


mysql -h localhost --protocol=tcp -u root -p${PASSWD} ${DDBB} < src/exercise2/country.sql
mysql -h localhost --protocol=tcp -u root -p${PASSWD} ${DDBB} < src/exercise2/grape_variety.sql
mysql -h localhost --protocol=tcp -u root -p${PASSWD} ${DDBB} < src/exercise2/region.sql
mysql -h localhost --protocol=tcp -u root -p${PASSWD} ${DDBB} < src/exercise2/taster.sql
mysql -h localhost --protocol=tcp -u root -p${PASSWD} ${DDBB} < src/exercise2/user.sql
mysql -h localhost --protocol=tcp -u root -p${PASSWD} ${DDBB} < src/exercise2/winery.sql
mysql -h localhost --protocol=tcp -u root -p${PASSWD} ${DDBB} < src/exercise2/wine.sql

echo "Filled tables"
