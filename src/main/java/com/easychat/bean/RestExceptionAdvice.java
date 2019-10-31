/*
 * @(#)RestControllerAdvice.java 2018年9月25日
 * Copyright (c), 2018 深圳业拓讯通信科技有限公司（Shenzhen Yetelcom Communication Tech. Co.,Ltd.）,  
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.easychat.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@RestControllerAdvice(basePackages={"com.easychat.controller", "com.easychat.interceptor"})
@Slf4j
public class RestExceptionAdvice {
	@ExceptionHandler(ServiceException.class)
	public Result<?> handlerServiceException(ServiceException e){
		
		if (ResultCode.SYSTEM_ERROR.getCode().equals(e.getCode()) || ResultCode.DB_EXCEPTION.getCode().equals(e.getCode())) {
			log.warn("ServiceException found", e);
		} else {
			if (e.getCode() != null) {
				log.warn("ServiceException found, resultCode = {}, resultDesc = {}"
						, e.getCode(), e.getCode());
			}
			
			log.debug("ServiceException found", e);
		}
		return Result.fail(e.getCode(), e.getDesc());
	}
	
	@ExceptionHandler({DataAccessException.class, SQLException.class})
	public Result<?> handlerDataAccessException(DataAccessException e){
		
		log.warn("DataAccessException found", e);

		return Result.fail(ResultCode.DB_EXCEPTION);
	}
	
	@ExceptionHandler(Exception.class)
	public Result<?> handlerException(Exception e){
		
		log.warn("Exception found", e);
		
		return Result.fail(ResultCode.SYSTEM_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result<?> handler(MethodArgumentNotValidException e) {
		
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		List<String> fieldList = new ArrayList<>();
		
		if (log.isDebugEnabled()) {
			errors.forEach(f -> {
				fieldList.add(f.getField());
				log.debug("field format error: " + toString());
			});
		}

		return Result.fail(ResultCode.PARAM_INVALID, fieldList);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public Result<?> handler(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		List<String> fieldList = new ArrayList<>();
		if (log.isDebugEnabled()) {
			constraintViolations.forEach(f -> {
				fieldList.add(f.getPropertyPath().toString());
				log.debug("field format error: " + toString());
			});
		}
		return Result.fail(ResultCode.PARAM_INVALID, fieldList);
	}
	
	@ExceptionHandler(BindException.class)
	public Result<?> handler(BindException e) {
		
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		List<String> fieldList = new ArrayList<>();
		
		if (log.isDebugEnabled()) {
			errors.forEach(f -> {
				fieldList.add(f.getField());
				log.debug("field format error: " + toString());
			});
		}

		return Result.fail(ResultCode.PARAM_INVALID, fieldList);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Result<?> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e){
		
		log.debug("Exception found", e);
		
		return Result.fail(ResultCode.PARAM_INVALID);
	}

}
