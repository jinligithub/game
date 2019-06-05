package edu.xpu.game.service.msg;

import edu.xpu.game.entity.MsgQueue;

import java.util.List;

public interface MsgQueueService {
    Boolean sendMsg(String fromUserId, String toUserId,
                   String msg);

    List<MsgQueue> acceptMsg(String userId);
}
