package com.notes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.notes.Util.ServiceException;


@ControllerAdvice
public class GlobalAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAdvice.class);

	@Autowired
	private MessageSource messageSource; //提供了消息处理的功能
	
	@ResponseStatus(HttpStatus.NOT_FOUND) //修饰目标方法
	public String notFoundView() { //没有找到错误
		return "error";
	}
	//转字符串
	@ResponseBody
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> businessExceptionHandler(ServiceException be) {
		LOGGER.error(be.getMessage(), be);

		final Locale locale = LocaleContextHolder.getLocale();
		final String message = messageSource.getMessage(be.getMsg(), be.getParams(), locale);
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = -2147774529887359231L;
			{
				put(be.getField(), message);
			}
		};
	}
}
