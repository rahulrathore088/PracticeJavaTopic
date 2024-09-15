package com.lp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lp.model.Actor;
import com.lp.validator.UserValidator;

@Component
@Aspect
public class ValidatePasswordDelegate {

	@Autowired
	private UserValidator userValidator;

	@Around("@annotation(com.lp.util.ValidatePassword)")
	public Object validatePassword(ProceedingJoinPoint joinPoint) throws Throwable {
		Actor actor = (Actor) joinPoint.getArgs()[0];
		System.out.println(actor.getPassword());
		boolean isPasswordValid = userValidator.validatePassword(actor.getPassword());
		return isPasswordValid ? joinPoint.proceed() : Long.valueOf(-1);
	}

}
