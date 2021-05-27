package com.example.order.service;

import com.example.order.pojo.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order.pojo.vo.PayAsyncVo;
import com.example.order.pojo.vo.PayVo;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-16
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 支付
     * @param payVo
     * @return
     */
    String toAlipay(PayVo payVo);

    /**
     * 异步通知 接收支付成功的信息
     * @param payAsyncVo
     * @return
     */
    String handlePayResult(PayAsyncVo payAsyncVo);
}
