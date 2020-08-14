package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import spring.MemberDAO;
import spring.MemberPrinter;

@Configuration
@Import(AppConf2.class)
public class AppConfImport {
	
	@Bean
	public MemberDAO memberDao() {
		return new MemberDAO();
	}
	
	@Bean
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}
	
}
