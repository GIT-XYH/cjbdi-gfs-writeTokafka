package com.cjbdi.hbinsert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: XYH
 * @Date: 2021/12/9 12:47 下午
 * @Description: TODO
 */
@Data
@ToString
@AllArgsConstructor
public class WsWithBase64File {
    private Ws wsBean;
    private String base64File;
}
