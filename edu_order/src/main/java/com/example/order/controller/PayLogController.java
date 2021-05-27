package com.example.order.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.order.config.AlipayTemplate;
import com.example.order.pojo.Order;
import com.example.order.pojo.PayLog;
import com.example.order.pojo.vo.PayAsyncVo;
import com.example.order.pojo.vo.PayVo;
import com.example.order.result.Result;
import com.example.order.service.OrderService;
import com.example.order.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author jiangsanjin
 * @since 2021-04-16
 */
@CrossOrigin
@RestController
@RequestMapping("/pay")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;



    @Autowired
    private AlipayTemplate alipayTemplate;

    @ApiOperation(value = "支付")
    @PostMapping("toalipay")
    public Result toAlipay(@RequestBody PayVo payVo) {
        String s = payLogService.toAlipay(payVo);
        System.out.println(s);
        return Result.ok().data("s", s);

    }


    @ApiOperation(value = "异步通知 接收支付成功的信息")
    @PostMapping(value = "/payed/notify")
    public String handleAlipayed(PayAsyncVo payAsyncVo, HttpServletRequest request) throws AlipayApiException {
        System.out.println("异步通知 接收支付成功的信息");
        // 只要收到支付宝的异步通知，返回 success 支付宝便不再通知
        // 获取支付宝POST过来反馈信息
        //TODO 需要验签
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(),
                alipayTemplate.getCharset(), alipayTemplate.getSign_type()); //调用SDK验证签名

        if (signVerified) {
            System.out.println("签名验证成功...");

            //修改  支付日志表
            String result = payLogService.handlePayResult(payAsyncVo);
            return result;
        } else {
            System.out.println("签名验证失败...");
            return "error";
        }
    }


}
