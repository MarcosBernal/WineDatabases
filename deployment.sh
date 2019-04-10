#! /bin/bash

DIR_PATH=$(dirname $(realpath $0))
PASSWD=secret

docker run -d --rm -e MYSQL_ROOT_PASSWORD=$PASSWD -p 3306:3306 -v src:/tmp/src:ro --name=mysql_server mysql:5.7.25
echo "Mysql up and running!"

$(mysql -h localhost --protocol=tcp -u root -psecret < src/exercise1/create_tables.sql)
echo "Created Tables!!"

$(mysql -h localhost --protocol=tcp -u root -p${PASSWD} cataVino < ${DIR_PATH}/src/exercise2/country.sql)
$(mysql -h localhost --protocol=tcp -u root -p${PASSWD} cataVino < ${DIR_PATH}/src/exercise2/grape_variety.sql)
$(mysql -h localhost --protocol=tcp -u root -p${PASSWD} cataVino < ${DIR_PATH}/src/exercise2/region.sql)
$(mysql -h localhost --protocol=tcp -u root -p${PASSWD} cataVino < ${DIR_PATH}/src/exercise2/taster.sql)
$(mysql -h localhost --protocol=tcp -u root -p${PASSWD} cataVino < ${DIR_PATH}/src/exercise2/user.sql)
$(mysql -h localhost --protocol=tcp -u root -p${PASSWD} cataVino < ${DIR_PATH}/src/exercise2/wine.sql)
$(mysql -h localhost --protocol=tcp -u root -p${PASSWD} cataVino < ${DIR_PATH}/src/exercise2/winery.sql)
echo "Filled tables"
