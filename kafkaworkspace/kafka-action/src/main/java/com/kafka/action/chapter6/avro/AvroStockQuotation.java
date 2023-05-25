package com.kafka.action.chapter6.avro;

/**
 * Created by 17081290 on 2021/7/2.
 */
import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class AvroStockQuotation extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    private static final long serialVersionUID = 1089240866939643773L;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AvroStockQuotation\",\"namespace\":\"com.kafka.action.chapter7.avro\",\"fields\":[{\"name\":\"stockCode\",\"type\":\"string\"},{\"name\":\"stockName\",\"type\":\"string\"},{\"name\":\"tradeTime\",\"type\":\"long\"},{\"name\":\"preClosePrice\",\"type\":\"float\"},{\"name\":\"openPrice\",\"type\":\"float\"},{\"name\":\"currentPrice\",\"type\":\"float\"},{\"name\":\"highPrice\",\"type\":\"float\"},{\"name\":\"lowPrice\",\"type\":\"float\"}]}");
    public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
    @Deprecated public java.lang.CharSequence stockCode;
    @Deprecated public java.lang.CharSequence stockName;
    @Deprecated public long tradeTime;
    @Deprecated public float preClosePrice;
    @Deprecated public float openPrice;
    @Deprecated public float currentPrice;
    @Deprecated public float highPrice;
    @Deprecated public float lowPrice;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public AvroStockQuotation() {}

    /**
     * All-args constructor.
     * @param stockCode The new value for stockCode
     * @param stockName The new value for stockName
     * @param tradeTime The new value for tradeTime
     * @param preClosePrice The new value for preClosePrice
     * @param openPrice The new value for openPrice
     * @param currentPrice The new value for currentPrice
     * @param highPrice The new value for highPrice
     * @param lowPrice The new value for lowPrice
     */
    public AvroStockQuotation(java.lang.CharSequence stockCode, java.lang.CharSequence stockName, java.lang.Long tradeTime, java.lang.Float preClosePrice, java.lang.Float openPrice, java.lang.Float currentPrice, java.lang.Float highPrice, java.lang.Float lowPrice) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.tradeTime = tradeTime;
        this.preClosePrice = preClosePrice;
        this.openPrice = openPrice;
        this.currentPrice = currentPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public org.apache.avro.Schema getSchema() { return SCHEMA$; }
    // Used by DatumWriter.  Applications should not call.
    public java.lang.Object get(int field$) {
        switch (field$) {
            case 0: return stockCode;
            case 1: return stockName;
            case 2: return tradeTime;
            case 3: return preClosePrice;
            case 4: return openPrice;
            case 5: return currentPrice;
            case 6: return highPrice;
            case 7: return lowPrice;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int field$, java.lang.Object value$) {
        switch (field$) {
            case 0: stockCode = (java.lang.CharSequence)value$; break;
            case 1: stockName = (java.lang.CharSequence)value$; break;
            case 2: tradeTime = (java.lang.Long)value$; break;
            case 3: preClosePrice = (java.lang.Float)value$; break;
            case 4: openPrice = (java.lang.Float)value$; break;
            case 5: currentPrice = (java.lang.Float)value$; break;
            case 6: highPrice = (java.lang.Float)value$; break;
            case 7: lowPrice = (java.lang.Float)value$; break;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    /**
     * Gets the value of the 'stockCode' field.
     * @return The value of the 'stockCode' field.
     */
    public java.lang.CharSequence getStockCode() {
        return stockCode;
    }

    /**
     * Sets the value of the 'stockCode' field.
     * @param value the value to set.
     */
    public void setStockCode(java.lang.CharSequence value) {
        this.stockCode = value;
    }

    /**
     * Gets the value of the 'stockName' field.
     * @return The value of the 'stockName' field.
     */
    public java.lang.CharSequence getStockName() {
        return stockName;
    }

    /**
     * Sets the value of the 'stockName' field.
     * @param value the value to set.
     */
    public void setStockName(java.lang.CharSequence value) {
        this.stockName = value;
    }

    /**
     * Gets the value of the 'tradeTime' field.
     * @return The value of the 'tradeTime' field.
     */
    public java.lang.Long getTradeTime() {
        return tradeTime;
    }

    /**
     * Sets the value of the 'tradeTime' field.
     * @param value the value to set.
     */
    public void setTradeTime(java.lang.Long value) {
        this.tradeTime = value;
    }

    /**
     * Gets the value of the 'preClosePrice' field.
     * @return The value of the 'preClosePrice' field.
     */
    public java.lang.Float getPreClosePrice() {
        return preClosePrice;
    }

    /**
     * Sets the value of the 'preClosePrice' field.
     * @param value the value to set.
     */
    public void setPreClosePrice(java.lang.Float value) {
        this.preClosePrice = value;
    }

    /**
     * Gets the value of the 'openPrice' field.
     * @return The value of the 'openPrice' field.
     */
    public java.lang.Float getOpenPrice() {
        return openPrice;
    }

    /**
     * Sets the value of the 'openPrice' field.
     * @param value the value to set.
     */
    public void setOpenPrice(java.lang.Float value) {
        this.openPrice = value;
    }

    /**
     * Gets the value of the 'currentPrice' field.
     * @return The value of the 'currentPrice' field.
     */
    public java.lang.Float getCurrentPrice() {
        return currentPrice;
    }

    /**
     * Sets the value of the 'currentPrice' field.
     * @param value the value to set.
     */
    public void setCurrentPrice(java.lang.Float value) {
        this.currentPrice = value;
    }

    /**
     * Gets the value of the 'highPrice' field.
     * @return The value of the 'highPrice' field.
     */
    public java.lang.Float getHighPrice() {
        return highPrice;
    }

    /**
     * Sets the value of the 'highPrice' field.
     * @param value the value to set.
     */
    public void setHighPrice(java.lang.Float value) {
        this.highPrice = value;
    }

    /**
     * Gets the value of the 'lowPrice' field.
     * @return The value of the 'lowPrice' field.
     */
    public java.lang.Float getLowPrice() {
        return lowPrice;
    }

    /**
     * Sets the value of the 'lowPrice' field.
     * @param value the value to set.
     */
    public void setLowPrice(java.lang.Float value) {
        this.lowPrice = value;
    }

    /**
     * Creates a new AvroStockQuotation RecordBuilder.
     * @return A new AvroStockQuotation RecordBuilder
     */
    public static com.kafka.action.chapter6.avro.AvroStockQuotation.Builder newBuilder() {
        return new com.kafka.action.chapter6.avro.AvroStockQuotation.Builder();
    }

    /**
     * Creates a new AvroStockQuotation RecordBuilder by copying an existing Builder.
     * @param other The existing builder to copy.
     * @return A new AvroStockQuotation RecordBuilder
     */
    public static com.kafka.action.chapter6.avro.AvroStockQuotation.Builder newBuilder(com.kafka.action.chapter6.avro.AvroStockQuotation.Builder other) {
        return new com.kafka.action.chapter6.avro.AvroStockQuotation.Builder(other);
    }

    /**
     * Creates a new AvroStockQuotation RecordBuilder by copying an existing AvroStockQuotation instance.
     * @param other The existing instance to copy.
     * @return A new AvroStockQuotation RecordBuilder
     */
    public static com.kafka.action.chapter6.avro.AvroStockQuotation.Builder newBuilder(com.kafka.action.chapter6.avro.AvroStockQuotation other) {
        return new com.kafka.action.chapter6.avro.AvroStockQuotation.Builder(other);
    }

    /**
     * RecordBuilder for AvroStockQuotation instances.
     */
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AvroStockQuotation>
            implements org.apache.avro.data.RecordBuilder<AvroStockQuotation> {

        private java.lang.CharSequence stockCode;
        private java.lang.CharSequence stockName;
        private long tradeTime;
        private float preClosePrice;
        private float openPrice;
        private float currentPrice;
        private float highPrice;
        private float lowPrice;

        /** Creates a new Builder */
        private Builder() {
            super(SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         * @param other The existing Builder to copy.
         */
        private Builder(com.kafka.action.chapter6.avro.AvroStockQuotation.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.stockCode)) {
                this.stockCode = data().deepCopy(fields()[0].schema(), other.stockCode);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.stockName)) {
                this.stockName = data().deepCopy(fields()[1].schema(), other.stockName);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.tradeTime)) {
                this.tradeTime = data().deepCopy(fields()[2].schema(), other.tradeTime);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.preClosePrice)) {
                this.preClosePrice = data().deepCopy(fields()[3].schema(), other.preClosePrice);
                fieldSetFlags()[3] = true;
            }
            if (isValidValue(fields()[4], other.openPrice)) {
                this.openPrice = data().deepCopy(fields()[4].schema(), other.openPrice);
                fieldSetFlags()[4] = true;
            }
            if (isValidValue(fields()[5], other.currentPrice)) {
                this.currentPrice = data().deepCopy(fields()[5].schema(), other.currentPrice);
                fieldSetFlags()[5] = true;
            }
            if (isValidValue(fields()[6], other.highPrice)) {
                this.highPrice = data().deepCopy(fields()[6].schema(), other.highPrice);
                fieldSetFlags()[6] = true;
            }
            if (isValidValue(fields()[7], other.lowPrice)) {
                this.lowPrice = data().deepCopy(fields()[7].schema(), other.lowPrice);
                fieldSetFlags()[7] = true;
            }
        }

        /**
         * Creates a Builder by copying an existing AvroStockQuotation instance
         * @param other The existing instance to copy.
         */
        private Builder(com.kafka.action.chapter6.avro.AvroStockQuotation other) {
            super(SCHEMA$);
            if (isValidValue(fields()[0], other.stockCode)) {
                this.stockCode = data().deepCopy(fields()[0].schema(), other.stockCode);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.stockName)) {
                this.stockName = data().deepCopy(fields()[1].schema(), other.stockName);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.tradeTime)) {
                this.tradeTime = data().deepCopy(fields()[2].schema(), other.tradeTime);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.preClosePrice)) {
                this.preClosePrice = data().deepCopy(fields()[3].schema(), other.preClosePrice);
                fieldSetFlags()[3] = true;
            }
            if (isValidValue(fields()[4], other.openPrice)) {
                this.openPrice = data().deepCopy(fields()[4].schema(), other.openPrice);
                fieldSetFlags()[4] = true;
            }
            if (isValidValue(fields()[5], other.currentPrice)) {
                this.currentPrice = data().deepCopy(fields()[5].schema(), other.currentPrice);
                fieldSetFlags()[5] = true;
            }
            if (isValidValue(fields()[6], other.highPrice)) {
                this.highPrice = data().deepCopy(fields()[6].schema(), other.highPrice);
                fieldSetFlags()[6] = true;
            }
            if (isValidValue(fields()[7], other.lowPrice)) {
                this.lowPrice = data().deepCopy(fields()[7].schema(), other.lowPrice);
                fieldSetFlags()[7] = true;
            }
        }

        /**
         * Gets the value of the 'stockCode' field.
         * @return The value.
         */
        public java.lang.CharSequence getStockCode() {
            return stockCode;
        }

        /**
         * Sets the value of the 'stockCode' field.
         * @param value The value of 'stockCode'.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder setStockCode(java.lang.CharSequence value) {
            validate(fields()[0], value);
            this.stockCode = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'stockCode' field has been set.
         * @return True if the 'stockCode' field has been set, false otherwise.
         */
        public boolean hasStockCode() {
            return fieldSetFlags()[0];
        }


        /**
         * Clears the value of the 'stockCode' field.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder clearStockCode() {
            stockCode = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'stockName' field.
         * @return The value.
         */
        public java.lang.CharSequence getStockName() {
            return stockName;
        }

        /**
         * Sets the value of the 'stockName' field.
         * @param value The value of 'stockName'.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder setStockName(java.lang.CharSequence value) {
            validate(fields()[1], value);
            this.stockName = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'stockName' field has been set.
         * @return True if the 'stockName' field has been set, false otherwise.
         */
        public boolean hasStockName() {
            return fieldSetFlags()[1];
        }


        /**
         * Clears the value of the 'stockName' field.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder clearStockName() {
            stockName = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        /**
         * Gets the value of the 'tradeTime' field.
         * @return The value.
         */
        public java.lang.Long getTradeTime() {
            return tradeTime;
        }

        /**
         * Sets the value of the 'tradeTime' field.
         * @param value The value of 'tradeTime'.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder setTradeTime(long value) {
            validate(fields()[2], value);
            this.tradeTime = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        /**
         * Checks whether the 'tradeTime' field has been set.
         * @return True if the 'tradeTime' field has been set, false otherwise.
         */
        public boolean hasTradeTime() {
            return fieldSetFlags()[2];
        }


        /**
         * Clears the value of the 'tradeTime' field.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder clearTradeTime() {
            fieldSetFlags()[2] = false;
            return this;
        }

        /**
         * Gets the value of the 'preClosePrice' field.
         * @return The value.
         */
        public java.lang.Float getPreClosePrice() {
            return preClosePrice;
        }

        /**
         * Sets the value of the 'preClosePrice' field.
         * @param value The value of 'preClosePrice'.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder setPreClosePrice(float value) {
            validate(fields()[3], value);
            this.preClosePrice = value;
            fieldSetFlags()[3] = true;
            return this;
        }

        /**
         * Checks whether the 'preClosePrice' field has been set.
         * @return True if the 'preClosePrice' field has been set, false otherwise.
         */
        public boolean hasPreClosePrice() {
            return fieldSetFlags()[3];
        }


        /**
         * Clears the value of the 'preClosePrice' field.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder clearPreClosePrice() {
            fieldSetFlags()[3] = false;
            return this;
        }

        /**
         * Gets the value of the 'openPrice' field.
         * @return The value.
         */
        public java.lang.Float getOpenPrice() {
            return openPrice;
        }

        /**
         * Sets the value of the 'openPrice' field.
         * @param value The value of 'openPrice'.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder setOpenPrice(float value) {
            validate(fields()[4], value);
            this.openPrice = value;
            fieldSetFlags()[4] = true;
            return this;
        }

        /**
         * Checks whether the 'openPrice' field has been set.
         * @return True if the 'openPrice' field has been set, false otherwise.
         */
        public boolean hasOpenPrice() {
            return fieldSetFlags()[4];
        }


        /**
         * Clears the value of the 'openPrice' field.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder clearOpenPrice() {
            fieldSetFlags()[4] = false;
            return this;
        }

        /**
         * Gets the value of the 'currentPrice' field.
         * @return The value.
         */
        public java.lang.Float getCurrentPrice() {
            return currentPrice;
        }

        /**
         * Sets the value of the 'currentPrice' field.
         * @param value The value of 'currentPrice'.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder setCurrentPrice(float value) {
            validate(fields()[5], value);
            this.currentPrice = value;
            fieldSetFlags()[5] = true;
            return this;
        }

        /**
         * Checks whether the 'currentPrice' field has been set.
         * @return True if the 'currentPrice' field has been set, false otherwise.
         */
        public boolean hasCurrentPrice() {
            return fieldSetFlags()[5];
        }


        /**
         * Clears the value of the 'currentPrice' field.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder clearCurrentPrice() {
            fieldSetFlags()[5] = false;
            return this;
        }

        /**
         * Gets the value of the 'highPrice' field.
         * @return The value.
         */
        public java.lang.Float getHighPrice() {
            return highPrice;
        }

        /**
         * Sets the value of the 'highPrice' field.
         * @param value The value of 'highPrice'.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder setHighPrice(float value) {
            validate(fields()[6], value);
            this.highPrice = value;
            fieldSetFlags()[6] = true;
            return this;
        }

        /**
         * Checks whether the 'highPrice' field has been set.
         * @return True if the 'highPrice' field has been set, false otherwise.
         */
        public boolean hasHighPrice() {
            return fieldSetFlags()[6];
        }


        /**
         * Clears the value of the 'highPrice' field.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder clearHighPrice() {
            fieldSetFlags()[6] = false;
            return this;
        }

        /**
         * Gets the value of the 'lowPrice' field.
         * @return The value.
         */
        public java.lang.Float getLowPrice() {
            return lowPrice;
        }

        /**
         * Sets the value of the 'lowPrice' field.
         * @param value The value of 'lowPrice'.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder setLowPrice(float value) {
            validate(fields()[7], value);
            this.lowPrice = value;
            fieldSetFlags()[7] = true;
            return this;
        }

        /**
         * Checks whether the 'lowPrice' field has been set.
         * @return True if the 'lowPrice' field has been set, false otherwise.
         */
        public boolean hasLowPrice() {
            return fieldSetFlags()[7];
        }


        /**
         * Clears the value of the 'lowPrice' field.
         * @return This builder.
         */
        public com.kafka.action.chapter6.avro.AvroStockQuotation.Builder clearLowPrice() {
            fieldSetFlags()[7] = false;
            return this;
        }

        @Override
        public AvroStockQuotation build() {
            try {
                AvroStockQuotation record = new AvroStockQuotation();
                record.stockCode = fieldSetFlags()[0] ? this.stockCode : (java.lang.CharSequence) defaultValue(fields()[0]);
                record.stockName = fieldSetFlags()[1] ? this.stockName : (java.lang.CharSequence) defaultValue(fields()[1]);
                record.tradeTime = fieldSetFlags()[2] ? this.tradeTime : (java.lang.Long) defaultValue(fields()[2]);
                record.preClosePrice = fieldSetFlags()[3] ? this.preClosePrice : (java.lang.Float) defaultValue(fields()[3]);
                record.openPrice = fieldSetFlags()[4] ? this.openPrice : (java.lang.Float) defaultValue(fields()[4]);
                record.currentPrice = fieldSetFlags()[5] ? this.currentPrice : (java.lang.Float) defaultValue(fields()[5]);
                record.highPrice = fieldSetFlags()[6] ? this.highPrice : (java.lang.Float) defaultValue(fields()[6]);
                record.lowPrice = fieldSetFlags()[7] ? this.lowPrice : (java.lang.Float) defaultValue(fields()[7]);
                return record;
            } catch (Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }

    private static final org.apache.avro.io.DatumWriter
            WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

    @Override public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        WRITER$.write(this, SpecificData.getEncoder(out));
    }

    private static final org.apache.avro.io.DatumReader
            READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

    @Override public void readExternal(java.io.ObjectInput in)
            throws java.io.IOException {
        READER$.read(this, SpecificData.getDecoder(in));
    }

}
