package com.kafka.monitor.model;

import org.jsoup.helper.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 18061263
 * @create 2019-03-29
 * @since v1.0.0
 */
public class ConsumerDetailInfo extends TopicInfo {

    private List<PartitionDetailInfo> partitionDetailInfoList;

    private TroubleType troubleType;

    private Map<String, String> troubleWorkerMap;

    private long totalLag;

    public ConsumerDetailInfo(TopicInfo indexInfo) {
        super(indexInfo.getDetailUrl(), indexInfo.getIndexId(), indexInfo.getGroupId());
        this.troubleWorkerMap = new HashMap<String, String>();
    }

    public ConsumerDetailInfo(String detailUrl, int id, String groupId) {
        super(detailUrl, id, groupId);
        this.troubleWorkerMap = new HashMap<String, String>();
    }

    public List<PartitionDetailInfo> getPartitionDetailInfoList() {
        return partitionDetailInfoList;
    }

    public void setPartitionDetailInfoList(List<PartitionDetailInfo> partitionDetailInfoList) {
        this.partitionDetailInfoList = partitionDetailInfoList;
    }

    public TroubleType getTroubleType() {
        return troubleType;
    }

    public void setTroubleType(TroubleType troubleType) {
        this.troubleType = troubleType;
    }

    public void addTroubleWorker(String ip) {
        troubleWorkerMap.put(ip, ip);
    }

    public void clear() {
        troubleWorkerMap.clear();
    }

    public void caculate() {
        long vTotalLag = 0;
        for (PartitionDetailInfo info : partitionDetailInfoList) {
            vTotalLag += info.getLag();
        }
        totalLag = vTotalLag;
    }

    public Long getTotalLag() {
        return totalLag;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        String text = "ConsumerDetailInfo{" +
                "troubleType=" + troubleType +
                ", indexId=" + indexId +
                ", groupId=" + groupId +
                ", totalLag='" + getTotalLag() + '\'' +
                '}';
        if (!troubleWorkerMap.isEmpty()) {
            Set<String> keySet = troubleWorkerMap.keySet();
            for (String ip : keySet) {
                buffer.append(ip + ",");
            }
        }
        if (!StringUtil.isBlank(buffer.toString())) {
            text = text + "[" + buffer.toString() + "]";
        }
        return text;
    }
}
