package com.halfroom.distribution.config.web;
/**************************************************************************
 *
 *
 * @author along
 * @date   Nov 27, 2018 7:41:47 PM
 *************************************************************************/

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
public class AMQPConfig {

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
