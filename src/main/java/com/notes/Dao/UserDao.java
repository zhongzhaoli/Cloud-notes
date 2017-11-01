package com.notes.Dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.notes.Entity.Note;
import com.notes.Entity.User;
import com.notes.Util.ServiceException;
import com.notes.Util.UTF8;

//组件
@Component
//事务管理器
@Repository //据访问层
@Transactional
public class UserDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	@Autowired
	private NoteDao notedao;
	
	User users = new User();
	UTF8 utf8 = new UTF8();
	Note notes = new Note();
	
	//找个人信息(account)
	public User findUser(String account){
		if(account != null){
			 DetachedCriteria dc = DetachedCriteria.forClass(User.class); //离线查询
			 dc.add(Property.forName("account").eq(account));
			 Criteria criteria = dc.getExecutableCriteria(getSession());
			 List list = criteria.list();
			 if(list != null && list.size() > 0){
				 return (User)list.get(0);
			 }
			 else{
				 return null;
			 }
		}
		else{
			throw new ServiceException("account","not.get.account");
		}
	}
	//找个人信息(id)
	public User findUser_id(String id){
		if(id != null){
			 DetachedCriteria dc = DetachedCriteria.forClass(User.class); //离线查询
			 dc.add(Property.forName("id").eq(id));
			 Criteria criteria = dc.getExecutableCriteria(getSession());
			 List list = criteria.list();
			 if(list != null && list.size() > 0){
				 return (User)list.get(0);
			 }
			 else{
				 return null;
			 }
		}
		else{
			throw new ServiceException("account","not.get.account");
		}
	}
	//找人(模糊查找)
	public List findUser_vague(String keyword){
		DetachedCriteria dc = DetachedCriteria.forClass(User.class); //离线查询
		//ANYWHERE 查出所有
		dc.add(Property.forName("account").like(keyword,MatchMode.ANYWHERE));
		 Criteria criteria = dc.getExecutableCriteria(getSession());
		 List list = criteria.list();
		 if(list != null && list.size() > 0){
			 return list;
		 }
		 else{
			 return null;
		 }
	}
	//添加分享的人
	public String add_share_user(String id,String userid){
		Note note = (Note) notedao.findNote_id(id).get(0);
		User user = (User) findUser_id(userid);
		BeanUtils.copyProperties(note,notes);
		if(notes.getSharelist()==null||notes.getSharelist()==""){
			 //存进 List  然后toString
			 List share_list = new ArrayList();
			 share_list.add(user.getAccount());
			notes.setSharelist(share_list.toString());
			//存进数据库
			notedao.updataNote2(notes);
		}
		else{
			//先获取 sharelist 里的所有数据
			String share_list = note.getSharelist();
			//切割
			String qg_sha = share_list.split("\\[")[1];
			String qg_sha_2 = qg_sha.split("\\]")[0];
			//获取到一个数组吧
			String[] qg_sha_3 = qg_sha_2.split(",");
			int le = qg_sha_3.length;
			//List 用for循环 add进去 再toString
			 List share_list_new = new ArrayList();
			 share_list_new.add(user.getAccount());
			for(int i=0;i<le;i++){
				if(qg_sha_3[i].equals(user.getAccount())){
					throw new ServiceException("share_is_in");
				}
				else{
					//清除空格
					String qg_sha_4 = qg_sha_3[i].replace(" ", "");
					share_list_new.add(qg_sha_4);
				}
			}
			notes.setSharelist(share_list_new.toString());
			notedao.updataNote2(notes);
		}
		return "success";
	}
	//查找出所有信息 并 进行覆盖
	public void copyUser(User user){
		User getuser = findUser(user.getAccount());
		BeanUtils.copyProperties(getuser, users);
	}
	//修改头像
	public void changePhoto(String photo_url,User user){
		copyUser(user);
		users.setPhoto(photo_url);
		getSession().merge(users);
	}
	//修改信息
	public void changeMessage(String user_nickname,String user_province,String user_city,String user_mood,User user) throws UnsupportedEncodingException{
		copyUser(user);
		users.setNickname(user_nickname);
		users.setProvince(user_province);
		users.setCity(user_city);
		users.setMood(user_mood);
		getSession().merge(users);
	}
	//查找分享的人
	public List findStaredao(String id){
		Note note = (Note) notedao.findNote_id(id).get(0);
		//切割
			String share_list = note.getSharelist();
			if(share_list!=null){
				String qg_sha = share_list.split("\\[")[1];
				String qg_sha_2 = qg_sha.split("\\]")[0];
				//获取到一个数组吧
				String[] qg_sha_3 = qg_sha_2.split(",");
				List share_list_new = new ArrayList();
				int le = qg_sha_3.length;
				for(int i=0;i<le;i++){
					User user_name = findUser(qg_sha_3[i].replace(" ", ""));
					String user_name_tos =(String) user_name.getAccount(); 
					share_list_new.add(user_name);
				}
				return share_list_new;
			}
			else{
				return null;
			}
	}
}
