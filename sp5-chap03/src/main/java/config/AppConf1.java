package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.MemberDAO;
import spring.MemberPrinter;

@Configuration
public class AppConf1 {
	
	@Bean
	public MemberDAO memberDao() {
		return new MemberDAO();
	}
	
	@Bean
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}
	
}
