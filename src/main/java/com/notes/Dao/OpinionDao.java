package com.notes.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.notes.Entity.Opinion;

//组件
@Component
//事务管理器
@Repository //据访问层
@Transactional
public class OpinionDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	
	//注册
	public void save(Opinion opinion){
		getSession().save(opinion);
	}
}
