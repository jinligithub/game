package edu.xpu.game.service.pay;

import com.alipay.api.AlipayApiException;
import edu.xpu.game.bean.AlipayBean;

public interface PayService {
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;
}
