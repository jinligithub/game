package edu.xpu.game.service.pay.impl;

import com.alipay.api.AlipayApiException;
import edu.xpu.game.bean.AlipayBean;
import edu.xpu.game.service.pay.PayService;
import edu.xpu.game.util.AlipayUtil;
import org.springframework.stereotype.Service;

/**
 * @author tim
 * @version 1.0
 * @className PayServiceImpl
 * @description
 * @date 2019-05-31 08:16
 */
@Service
public class PayServiceImpl implements PayService {
    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return AlipayUtil.connect(alipayBean);
    }
}
