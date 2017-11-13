package com.notes.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.notes.Entity.Note;
import com.notes.Entity.Share;
import com.notes.Entity.User;
import com.notes.Util.ServiceException;
import com.notes.Util.UTF8;

//组件
@Component
//事务管理器
@Repository //据访问层
@Transactional
public class ShareDao {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	@Autowired
	private NoteDao notedao;
	@Autowired
	private UserDao userdao;
	@Autowired
	private BaseDao basedao;
	
	User users = new User();
	UTF8 utf8 = new UTF8();
	Note notes = new Note();
	Share share = new Share();
	
	
	//删除分享的人
	public String dele_share_user(String id,String userid){
		Share find_Share = (Share) share_find_id(id,userid,true).get(0);
		BeanUtils.copyProperties(find_Share, share);
		delete_share(share);
		return "success";

	}
	//查找分享的人
	public List findStaredao(String node_id){
	    DetachedCriteria dc = DetachedCriteria.forClass(Share.class); //离线查询
		dc.add(Property.forName("node").eq(node_id));
		List share_list_new = new ArrayList();
		List item = basedao.for_Share(dc);
		for(int i=0;i<item.size();i++){
			Share c = (Share) item.get(i);
			User d = (User) userdao.findUser(c.getUsername());
			//password 替换权限
			d.setQx(c.getEditable());
			share_list_new.add(i,d);
		}
		return share_list_new;
	}
	//添加分享的人
	public String add_share_user(String id,String userid,HttpServletRequest req){
		//如果是自己
		if(req.getSession().getAttribute("user_id").equals(userid)){
			throw new ServiceException("share_is_userself");
		}
		User user = (User) userdao.findUser_id(userid);
	    DetachedCriteria dc = DetachedCriteria.forClass(Share.class); //离线查询
		dc.add(Property.forName("username").eq(user.getAccount()));
		dc.add(Property.forName("node").eq(id));
		List list = basedao.for_Share(dc);
		if(list.size() <= 0||list == null){
			share.setId(UUID.randomUUID().toString().replace("-", ""));
			share.setNode(id);
			share.setUsername(user.getAccount());
			share.setEditable("false");
			save_share(share);
			return "success";
		}
		else{
			throw new ServiceException("share_is_in");
		}
	}
	//查找出 share表的 noteid
	public List share_find_noteid(String user_name){
		 DetachedCriteria dc = DetachedCriteria.forClass(Share.class); //离线查询
		 dc.add(Property.forName("username").eq(user_name));
		 List list = basedao.for_Share(dc);
		 return basedao.get_list_all(list);
	}
	//查找出 share表的 数据  准确
	public List share_find_id(String note,String user_id,boolean accuracy){
		 DetachedCriteria dc = DetachedCriteria.forClass(Share.class); //离线查询
		 if(accuracy){
			 dc.add(Property.forName("username").eq(userdao.findUser_id(user_id).getAccount()));
		 }
		 dc.add(Property.forName("node").eq(note));
		 List list = basedao.for_Share(dc);
		 return basedao.get_list_all(list);
	}
	//存储 share表数据
	public void save_share(Share share){
		getSession().save(share);
	}
	//删除 share表数据
	public void delete_share(Share share){
		getSession().remove(getSession().merge(share));
	}
	//修改share表数据
	public void updata_share(Share share){
		getSession().merge(share);
	}
}
