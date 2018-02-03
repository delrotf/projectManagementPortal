package com.gtrack.projectmanagementportal.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfo.class)
public abstract class UserInfo_ {

	public static volatile SingularAttribute<UserInfo, String> firstName;
	public static volatile SingularAttribute<UserInfo, String> lastName;
	public static volatile SingularAttribute<UserInfo, String> phone;
	public static volatile SingularAttribute<UserInfo, String> imageUrl;
	public static volatile SingularAttribute<UserInfo, String> callingName;
	public static volatile SingularAttribute<UserInfo, Long> id;
	public static volatile SingularAttribute<UserInfo, Designation> designation;
	public static volatile SingularAttribute<UserInfo, User> user;
	public static volatile SingularAttribute<UserInfo, UserInfo> supervisor;

}

