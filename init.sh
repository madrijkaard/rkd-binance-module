#!/bin/bash

docker exec -it cassandra cqlsh -e "CREATE KEYSPACE rkd_binance_module WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor':1};"

docker exec -it cassandra cqlsh -e "USE rkd_binance_module; CREATE TABLE kline (open_time TIMESTAMP, close_time TIMESTAMP, sequence INT, interval TEXT, open_price TEXT, high_price TEXT, low_price TEXT, close_price TEXT, volume TEXT, quote_asset_volume TEXT, number_of_trade INT, taker_buy_base_asset_volume TEXT, taker_buy_quote_asset_volume TEXT, PRIMARY KEY ((open_time, close_time), sequence)) WITH CLUSTERING ORDER BY (sequence DESC);"