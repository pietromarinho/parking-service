package br.com.parking.service.beans;

import br.com.parking.core.component.Mapper;
import br.com.parking.core.dao.BaseDAO;
import br.com.parking.core.dao.DAOInterface;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class Beans {
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public DAOInterface getDAOInstance() {
		return new BaseDAO();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public Mapper getMapperInstance() {
		return new Mapper();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource 	source		= new UrlBasedCorsConfigurationSource();
		CorsConfiguration					config		= new CorsConfiguration();

		config.addAllowedOrigin(CorsConfiguration.ALL);
		config.addAllowedMethod(HttpMethod.OPTIONS.name());
		config.addAllowedMethod(HttpMethod.GET.name());
		config.addAllowedMethod(HttpMethod.POST.name());
		config.addAllowedMethod(HttpMethod.PUT.name());
		config.addAllowedMethod(HttpMethod.DELETE.name());
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.addExposedHeader(HttpHeaders.AUTHORIZATION);
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}