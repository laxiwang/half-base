package com.halfroom.distribution.rabbitmq.Listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.halfroom.distribution.rabbitmq.MessageBean.DistributionMessageBean;
import com.halfroom.distribution.service.IBranchsalerAccountChangeService;
import com.rabbitmq.client.Channel;

@Component
public class DistributionListener {
//	
	@Autowired
	IBranchsalerAccountChangeService iBranchsalerAccountChangeService;

//	@Transactional
	@RabbitListener(queues = "distribution")
	public void DistributionListenerService(DistributionMessageBean distributionMessageBean, Channel channel,
			Message message) throws IOException {
		try {
			if (distributionMessageBean.getType().equals("0")) {
				Distribution(distributionMessageBean);// 分润
			} else {
				SettleAccounts(distributionMessageBean);// 提现结算
			}
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 手动确认确认消息成功消费
		} catch (Exception e) {
			try {
				System.out.println("分润处理异常");
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
				// 通知队列丢弃非法消息后，记录非法消息待后续处理。。。。。。
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// 分润
	public void Distribution(DistributionMessageBean distributionMessageBean) {
		iBranchsalerAccountChangeService.branchsalerDistribution(distributionMessageBean);
	}

	// 提现结算
	public void SettleAccounts(DistributionMessageBean distributionMessageBean) {
		iBranchsalerAccountChangeService.insertAccountsChange(distributionMessageBean);
	}
}
