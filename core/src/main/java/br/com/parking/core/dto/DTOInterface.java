package br.com.parking.core.dto;

import br.com.parking.core.model.AbstractModel;

public interface DTOInterface<T extends AbstractModel> {
	T getModel();
}