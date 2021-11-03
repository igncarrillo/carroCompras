package ar.edu.um.products.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Distributor.class)
public abstract class Distributor_ {

	public static volatile SingularAttribute<Distributor, Boolean> isEnabled;
	public static volatile SingularAttribute<Distributor, String> name;
	public static volatile SingularAttribute<Distributor, String> description;
	public static volatile SingularAttribute<Distributor, Long> id;
	public static volatile SetAttribute<Distributor, Product> products;

	public static final String IS_ENABLED = "isEnabled";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String PRODUCTS = "products";

}

