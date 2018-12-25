package com.xyz.caofancpu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileName: AbstractLogger
 * Author:   caofanCPU
 * Date:     2018/12/24 16:11
 */

public abstract class AbstractLogger {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
