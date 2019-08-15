package com.xyz.caofancpu.trackingtime.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by caofanCPU on 2018/6/29.
 *
 * @author caofanCPU
 * 附件
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Attachment implements Serializable {

    private String name;

    /**
     * 附件所属的业务ID
     */
    private String busId;

    private String type;

    private Integer sortId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    private String accessUrl;
}
