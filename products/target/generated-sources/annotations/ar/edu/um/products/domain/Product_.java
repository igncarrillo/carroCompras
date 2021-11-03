package ar.edu.um.products.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, Float> price;
	public static volatile SingularAttribute<Product, Boolean> isEnabled;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, Distributor> distributor;
	public static volatile SingularAttribute<Product, Integer> soldQuantity;

	public static final String PRICE = "price";
	public static final String IS_ENABLED = "isEnabled";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String DISTRIBUTOR = "distributor";
	public static final String SOLD_QUANTITY = "soldQuantity";

}

