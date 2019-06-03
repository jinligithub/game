package edu.xpu.game.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import edu.xpu.game.bean.AlipayBean;
import edu.xpu.game.entity.OrderMaster;
import edu.xpu.game.enums.PayStatusEnum;
import edu.xpu.game.service.order.impl.OrderServiceImpl;
import edu.xpu.game.service.pay.impl.PayServiceImpl;
import edu.xpu.game.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author tim
 * @version 1.0
 * @className PayController
 * @description
 * @date 2019-05-31 08:18
 */


@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {
    private final OrderServiceImpl orderService;

    private final PayServiceImpl payService;

    @Autowired
    public PayController(OrderServiceImpl orderService, PayServiceImpl payService) {
        this.orderService = orderService;
        this.payService = payService;
    }

    @ResponseBody
    @RequestMapping("/affirmPay")
    public String payOrder(@RequestParam("masterOrderId") String masterOrderId){
        ResultVO<Boolean> ret = new ResultVO<>();
        Optional<OrderMaster> byId = orderService.findOrderById(masterOrderId);

        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(masterOrderId);
        alipayBean.setSubject("农家商城购物");

        if(byId.isPresent()){
            ret.setCode(0);
            ret.setMsg("查询到此订单");
            alipayBean.setTotal_amount(new StringBuffer().append(byId.get().getOrderAmount()));
            try {
                String aliPay = payService.aliPay(alipayBean);
                log.info("[pay.affirmPay] aliPay = {}", aliPay);
                return aliPay;
            } catch (AlipayApiException e) {
                e.printStackTrace();
                ret.setCode(2);
                ret.setMsg("支付过程出错");
                return JSONObject.toJSONString(ret, true);
            }
        }else{
            ret.setCode(1);
            ret.setMsg("不存在此订单");
            return JSONObject.toJSONString(ret, true);
        }
    }


    /**
     * 支付成功的回调
     * @param trade_status 交易状态
     * @param out_trade_no 商户订单号
     */
    @RequestMapping("/payFinish")
    public void payFinishCallback(String trade_status, String out_trade_no){
        ResultVO<String> ret = new ResultVO<>();

        log.info("支付回调：trade_status = "+trade_status);
        if("TRADE_SUCCESS".equals(trade_status)){
            //支付成功
            Optional<OrderMaster> orderById = orderService.findOrderById(out_trade_no);
            ret.setCode(0);
            ret.setMsg("支付成功");
            if(orderById.isPresent()){
                OrderMaster orderMaster = orderById.get();
                //修改支付状态
                orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                orderService.change(orderMaster);
            }
        }else{
            //支付失败
            Optional<OrderMaster> orderById = orderService.findOrderById(out_trade_no);
            ret.setCode(1);
            ret.setMsg("等待支付");
            if(orderById.isPresent()){
                OrderMaster orderMaster = orderById.get();
                //修改支付状态
                orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
                orderService.change(orderMaster);
            }
        }
    }


    @RequestMapping("/paySyncFinish")
    public String paySyncFinishCallback(){
        return "redirect:http://9t9wb2.natappfree.cc/#/pay";
    }
}
