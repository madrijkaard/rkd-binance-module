package com.rkd.binance.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;

/**
 * Class responsible for market information on a chart period.
 */
@Table("kline")
public class KlineModel {
        @PrimaryKeyColumn(name = "open_time", ordinal = 0, type = PARTITIONED)
        private Instant openTime;
        @PrimaryKeyColumn(name = "close_time", ordinal = 1, type = PARTITIONED)
        private Instant closeTime;
        @PrimaryKeyColumn(name = "sequence", ordinal = 2, type = CLUSTERED, ordering = DESCENDING)
        private int sequence;
        @Column("open_price")
        private String openPrice;
        @Column("high_price")
        private String highPrice;
        @Column("low_price")
        private String lowPrice;
        @Column("close_price")
        private String closePrice;
        @Column("volume")
        private String volume;
        @Column("quote_asset_volume")
        private String quoteAssetVolume;
        @Column("number_of_trade")
        private int numberOfTrade;
        @Column("taker_buy_base_asset_volume")
        private String takerBuyBaseAssetVolume;
        @Column("taker_buy_quote_asset_volume")
        private String takerBuyQuoteAssetVolume;

        public KlineModel() {
        }

        public KlineModel(Instant openTime, Instant closeTime, int sequence, String openPrice, String highPrice,
                          String lowPrice, String closePrice, String volume, String quoteAssetVolume, int numberOfTrade,
                          String takerBuyBaseAssetVolume, String takerBuyQuoteAssetVolume) {
                this.openTime = openTime;
                this.closeTime = closeTime;
                this.sequence = sequence;
                this.openPrice = openPrice;
                this.highPrice = highPrice;
                this.lowPrice = lowPrice;
                this.closePrice = closePrice;
                this.volume = volume;
                this.quoteAssetVolume = quoteAssetVolume;
                this.numberOfTrade = numberOfTrade;
                this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
                this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
        }

        public Instant getOpenTime() {
                return openTime;
        }

        public void setOpenTime(Instant openTime) {
                this.openTime = openTime;
        }

        public Instant getCloseTime() {
                return closeTime;
        }

        public void setCloseTime(Instant closeTime) {
                this.closeTime = closeTime;
        }

        public int getSequence() {
                return sequence;
        }

        public void setSequence(int sequence) {
                this.sequence = sequence;
        }

        public String getOpenPrice() {
                return openPrice;
        }

        public void setOpenPrice(String openPrice) {
                this.openPrice = openPrice;
        }

        public String getHighPrice() {
                return highPrice;
        }

        public void setHighPrice(String highPrice) {
                this.highPrice = highPrice;
        }

        public String getLowPrice() {
                return lowPrice;
        }

        public void setLowPrice(String lowPrice) {
                this.lowPrice = lowPrice;
        }

        public String getClosePrice() {
                return closePrice;
        }

        public void setClosePrice(String closePrice) {
                this.closePrice = closePrice;
        }

        public String getVolume() {
                return volume;
        }

        public void setVolume(String volume) {
                this.volume = volume;
        }

        public String getQuoteAssetVolume() {
                return quoteAssetVolume;
        }

        public void setQuoteAssetVolume(String quoteAssetVolume) {
                this.quoteAssetVolume = quoteAssetVolume;
        }

        public int getNumberOfTrade() {
                return numberOfTrade;
        }

        public void setNumberOfTrade(int numberOfTrade) {
                this.numberOfTrade = numberOfTrade;
        }

        public String getTakerBuyBaseAssetVolume() {
                return takerBuyBaseAssetVolume;
        }

        public void setTakerBuyBaseAssetVolume(String takerBuyBaseAssetVolume) {
                this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
        }

        public String getTakerBuyQuoteAssetVolume() {
                return takerBuyQuoteAssetVolume;
        }

        public void setTakerBuyQuoteAssetVolume(String takerBuyQuoteAssetVolume) {
                this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
        }
}
