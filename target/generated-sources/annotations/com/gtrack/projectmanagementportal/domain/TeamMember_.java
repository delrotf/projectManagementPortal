package com.gtrack.projectmanagementportal.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TeamMember.class)
public abstract class TeamMember_ {

	public static volatile SingularAttribute<TeamMember, Instant> updatedTime;
	public static volatile SingularAttribute<TeamMember, UserInfo> userInfo;
	public static volatile SingularAttribute<TeamMember, Long> id;
	public static volatile SingularAttribute<TeamMember, Team> team;

}

