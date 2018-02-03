package com.gtrack.projectmanagementportal.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Team.class)
public abstract class Team_ {

	public static volatile SingularAttribute<Team, Boolean> processExternalTask;
	public static volatile SingularAttribute<Team, Boolean> processOrder;
	public static volatile SingularAttribute<Team, String> code;
	public static volatile SingularAttribute<Team, Instant> createdDate;
	public static volatile SingularAttribute<Team, String> imageUrl;
	public static volatile SingularAttribute<Team, String> name;
	public static volatile SingularAttribute<Team, Boolean> active;
	public static volatile SingularAttribute<Team, Long> id;
	public static volatile SingularAttribute<Team, UserInfo> teamHead;

}

