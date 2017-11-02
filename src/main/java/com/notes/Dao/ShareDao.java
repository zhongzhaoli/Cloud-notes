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
import com.notes.Util.ShareSplit;
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
	
	User users = new User();
	UTF8 utf8 = new UTF8();
	Note notes = new Note();
	Share share = new Share();
	ShareSplit sharesplit = new ShareSplit();
	
	
	//删除分享的人
	public String dele_share_user(String id,String userid){
		Note note = (Note) notedao.findNote_id(id).get(0);
		Share find_Share = (Share) share_find_id(id,userid,true).get(0);
		BeanUtils.copyProperties(find_Share, share);
		delete_share(share);
		BeanUtils.copyProperties(note,notes);
		String share_list = note.getSharelist();
		if(share_list!=null){
			String[] qg_sha_3 = sharesplit.split_qg(share_list);
			List share_list_new = new ArrayList();
			String want_del_user = userdao.findUser_id(userid).getAccount();
			for(int i=0;i<qg_sha_3.length;i++){
				String arr_user_name = qg_sha_3[i].replace(" ", ""); 
				if(arr_user_name.equals(want_del_user)){
					continue;
				}
				else{
					share_list_new.add(arr_user_name);
				}
			}
			notes.setSharelist(share_list_new.toString());
			notedao.updataNote2(notes);
			return "success";
			
		}
		else{
			return null;
		}
	}
	//查找分享的人
	public List findStaredao(String id){
		Note note = (Note) notedao.findNote_id(id).get(0);
		//切割
			String share_list = note.getSharelist();
			if(share_list!=null&&!share_list.equals("[]")&&share_list!=""){
				String[] qg_sha_3 = sharesplit.split_qg(share_list);
				List share_list_new = new ArrayList();
				for(int i=0;i<qg_sha_3.length;i++){
					User user_name = userdao.findUser(qg_sha_3[i].replace(" ", ""));
					String user_name_tos =(String) user_name.getAccount(); 
					share_list_new.add(user_name);
				}
				return share_list_new;
			}
			else{
				return null;
			}
	}
	//添加分享的人
	public String add_share_user(String id,String userid,HttpServletRequest req){
		if(req.getSession().getAttribute("user_id").equals(userid)){
			throw new ServiceException("share_is_userself");
		}
		Note note = (Note) notedao.findNote_id(id).get(0);
		User user = (User) userdao.findUser_id(userid);
		BeanUtils.copyProperties(note,notes);
		if(notes.getSharelist()==null||notes.getSharelist()==""||notes.getSharelist().equals("[]")){
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
			String[] qg_sha_3 = sharesplit.split_qg(share_list);
			//List 用for循环 add进去 再toString
			 List share_list_new = new ArrayList();
			 share_list_new.add(user.getAccount());
			for(int i=0;i<qg_sha_3.length;i++){
				if(qg_sha_3[i].replace(" ","").equals(user.getAccount())){
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
		share.setId(UUID.randomUUID().toString().replace("-", ""));
		share.setNode(id);
		share.setUsername(user.getAccount());
		share.setEditable("false");
		save_share(share);
		return "success";
	}
	//查找出 share表的 noteid
	public List share_find_noteid(String user_name){
		 DetachedCriteria dc = DetachedCriteria.forClass(Share.class); //离线查询
		 dc.add(Property.forName("username").eq(user_name));
		 Criteria criteria = dc.getExecutableCriteria(getSession());
		 List list = criteria.list();
		 if(list != null && list.size() > 0){
			 return (List) list;
		 }
		 else{
			 return null;
		 }
	}
	//查找出 share表的 数据  准确
	public List share_find_id(String note,String user_id,boolean accuracy){
		 DetachedCriteria dc = DetachedCriteria.forClass(Share.class); //离线查询
		 if(accuracy){
			 dc.add(Property.forName("username").eq(userdao.findUser_id(user_id).getAccount()));
		 }
		 dc.add(Property.forName("node").eq(note));
		 Criteria criteria = dc.getExecutableCriteria(getSession());
		 List list = criteria.list();
		 if(list != null && list.size() > 0){
			 return (List) list;
		 }
		 else{
			 return null;
		 }
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
