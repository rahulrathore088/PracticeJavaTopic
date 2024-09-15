package com.lp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Service;

import com.lp.util.ValidPassword;

@Service
public class UserValidator implements ConstraintValidator<ValidPassword, String> {

	public boolean validatePassword(String password) {
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(regex);
		if (password == null) {
			return false;
		}
		Matcher m = p.matcher(password);
		return m.matches();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return validatePassword(value);
	}

}
