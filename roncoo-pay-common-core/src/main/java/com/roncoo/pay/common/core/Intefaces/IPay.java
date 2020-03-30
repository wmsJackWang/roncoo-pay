package com.roncoo.pay.common.core.Intefaces;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface IPay {
	
	public abstract String pay(Model model, HttpServletRequest httpServletRequest);

	public abstract String pay(Model model, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse);

	public abstract String pay(Map<String,String> paramMap);
	
	public abstract String pay();

}
