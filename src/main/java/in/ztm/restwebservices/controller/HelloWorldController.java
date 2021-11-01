package in.ztm.restwebservices.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/hello-world")
	public String helloWorld(){
		return "Hello World";
	}
	
	/*@GetMapping(path="/hello-world-i18n")
	public String helloWorldI18n(
			@RequestHeader(name="Accept-Language", required=false) Locale locale
			){
		System.out.println(locale);
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
	}*/
	
	@GetMapping(path="/hello-world-i18n")
	public String helloWorldI18n(
			//@RequestHeader(name="Accept-Language", required=false) Locale locale
			){
		return messageSource.getMessage("good.morning.message", null, "Default Message", LocaleContextHolder.getLocale());
	}

}
