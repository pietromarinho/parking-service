package br.com.parking.core.component;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;

public class Mapper implements FactoryBean<MapperFactory> {
	@Override
	public MapperFactory getObject() {
		return new DefaultMapperFactory.Builder().mapNulls(false).build();
	}

	@Override
	public Class<?> getObjectType() {
		return MapperFactory.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}