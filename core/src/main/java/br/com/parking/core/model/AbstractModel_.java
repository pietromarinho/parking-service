package br.com.parking.core.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.OffsetDateTime;

@StaticMetamodel(AbstractModel.class)
public abstract class AbstractModel_ {
	public		static		volatile		SingularAttribute<AbstractModel, Long>				sequence;
	public		static		volatile		SingularAttribute<AbstractModel, String>			identifier;
	public		static		volatile		SingularAttribute<AbstractModel, OffsetDateTime>	createdDate;
	public		static		volatile		SingularAttribute<AbstractModel, OffsetDateTime>	updatedDate;
	public		static		volatile		SingularAttribute<AbstractModel, Long>				createdByID;
	public		static		volatile		SingularAttribute<AbstractModel, Long>				modifiedByID;
}
