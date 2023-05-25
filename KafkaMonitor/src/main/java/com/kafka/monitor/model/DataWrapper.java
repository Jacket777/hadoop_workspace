package com.kafka.monitor.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 18061263
 * @create 2019-04-16
 * @since v1.0.0
 */
public class DataWrapper implements Serializable {
    private List<OffsetSizeInfo> offsetList;
    private List<LogSizeInfo> logSizeList;
    private long partition;

    public List<OffsetSizeInfo> getOffsetList() {
        return offsetList;
    }

    public void setOffsetList(List<OffsetSizeInfo> offsetList) {
        this.offsetList = offsetList;
    }

    public List<LogSizeInfo> getLogSizeList() {
        return logSizeList;
    }

    public void setLogSizeList(List<LogSizeInfo> logSizeList) {
        this.logSizeList = logSizeList;
    }

    public long getPartition() {
        return partition;
    }

    public void setPartition(long partition) {
        this.partition = partition;
    }

    @Override
    public String toString() {
        return "DataWrapper{" +
                "offsetList=" + offsetList +
                ", partition=" + partition +
                '}';
    }
}
