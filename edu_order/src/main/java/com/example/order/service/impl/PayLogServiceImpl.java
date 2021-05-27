package com.example.order.service.impl;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.order.config.AlipayTemplate;
import com.example.order.pojo.Order;
import com.example.order.pojo.PayLog;
import com.example.order.mapper.PayLogMapper;
import com.example.order.pojo.vo.PayAsyncVo;
import com.example.order.pojo.vo.PayVo;
import com.example.order.service.OrderService;
import com.example.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-16
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    AlipayTemplate alipayTemplatea;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayLogMapper payLogMapper;
    /**
     * 支付
     * @param payVo
     * @return
     */
    @Override
    public String toAlipay(PayVo payVo) {

        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayTemplatea.getGatewayUrl(),//支付宝网关
                alipayTemplatea.getApp_id(),//appid
                alipayTemplatea.getMerchant_private_key(),//商户私钥
                alipayTemplatea.getFormate(),
                alipayTemplatea.getCharset(),//字符编码格式
                alipayTemplatea.getAlipay_public_key(),//支付宝公钥
                alipayTemplatea.getSign_type());//签名方式

        //2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(alipayTemplatea.getReturn_url());
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(alipayTemplatea.getNotify_url());

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = payVo.getOut_trade_no();
        //付款金额，必填
        String total_amount = payVo.getTotal_amount();
        //订单名称，必填
        String subject = payVo.getSubject();
        //商品描述，可空
        String body = payVo.getBody();
        // 超时时间
        String timeout = payVo.getTimeout();
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+timeout+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = "";
        try {
            //3、请求支付宝进行付款，并获取支付结果
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        //返回付款信息
        return result;
    }

    /**
     *  异步通知 接收支付成功的信息
     * @param payAsyncVo
     * @return
     */
    @Override
    public String handlePayResult(PayAsyncVo payAsyncVo) {

        //保存交易流水信息
        // System.out.println(payAsyncVo);
        PayLog payLog = new PayLog();
        payLog.setOrder_no(payAsyncVo.getOut_trade_no());
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(payAsyncVo.getGmt_payment());
            payLog.setPay_time(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        payLog.setTotal_fee(payAsyncVo.getTotal_amount());
        payLog.setTransaction_id(payAsyncVo.getTrade_no());
        payLog.setTrade_state(payAsyncVo.getTrade_status());
        payLog.setPay_type(1);
        payLogMapper.insert(payLog);


        String tradeStatus = payAsyncVo.getTrade_status();

        if (tradeStatus.equals("TRADE_SUCCESS")) {
            //支付成功状态
            // 修改order表的购买状态
            UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
            wrapper.eq("order_no",payAsyncVo.getOut_trade_no()).set("status", 1);
            orderService.update(wrapper);

        }

        return "success";
    }
}
