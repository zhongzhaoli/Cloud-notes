package com.notes.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.Controller.NoteForm;
import com.notes.Dao.NoteDao;
import com.notes.Dao.ShareDao;
import com.notes.Entity.Note;
import com.notes.Entity.Share;
import com.notes.Util.ServiceException;
import com.notes.Util.Time;

@Service
public class NoteService {

	@Autowired
	private NoteDao noteDao;
	@Autowired
	private ShareDao sharedao;
	@Autowired
	private NoteDao notedao;
	
	Note note = new Note();
	Time time = new Time();
	NoteForm notesForm = new NoteForm();
	
	public Note create(NoteForm noteForm, HttpServletRequest req){
		if(noteForm.getTitle().equals(null)||noteForm.getTitle().equals("")){
			note.setTitle("无标题笔记");
		}else{
			note.setTitle(noteForm.getTitle());
		}
		note.setId(UUID.randomUUID().toString().replace("-", ""));
		note.setContent(noteForm.getContent());
		note.setUsername((String) req.getSession().getAttribute("user_name"));
		note.setUserid((String) req.getSession().getAttribute("user_id"));
		note.setTime(time.timestamp());
		noteDao.createNote(note);
		return note;
	}
	public Note create_node(String title, String content, HttpServletRequest req){
		note.setId(UUID.randomUUID().toString().replace("-", ""));
		note.setTitle(title);
		note.setContent(content);
		note.setTime(time.timestamp());
		note.setUsername((String) req.getSession().getAttribute("user_name"));
		note.setUserid((String) req.getSession().getAttribute("user_id"));
		noteDao.createNote(note);
		return note;
	}
	
	//获取 用户的所有笔记 如果没有笔记顺便增加一条
	public List findNode_create(String a,HttpServletRequest req){
		List note = noteDao.findAllNotes(a);
		String text = "<p align='center'></p><p align='left' style='text-align: center;'><font size='5'>关于</font></p><p align='left' style='text-align: left;'><font size='3'>随记是一款在线的网络笔记本，您可以随时随地记录身边的点滴，重要的事情，或者笔记。使用免费，快捷便利，操作简单。只用在编辑区输入你想记录的事情，保存即可。</font></p><p align='left' style='text-align: left;'><font size='3'>为了更好的用户体验，让用户不再是“单机”。</font><span style='font-size: medium;'>存成笔记之后，只要有讲师的用户名就能与之分享。并且我们设置了一个功能，这个功能可以得益于讲解题目之时。</span><span style='font-size: medium;'>学生将自己不会的题目写入笔记中，保存之后分享给讲师。讲师可以在笔记中解答，完毕之后学生就可以直接看到结果。这样就可以实现网上一对一辅导教学。</span></p><p align='left' style='text-align: left;'><font size='3'>如果笔记已成过去事，可点击删除，永久删除笔记。</font></p><p align='left' style='text-align: left;'><span style='font-size: medium;'><br></span></p><p align='left' style='text-align: center;'><font size='5'>优点</font></p><p align='left' style='text-align: justify;'><img src='http://note.youdao.com/yws/res/4/F49B2C75339840CFAB805368E8E4406E' data-ke-src='http://note.youdao.com/yws/res/38576/F49B2C75339840CFAB805368E8E4406E' alt='icon-1.png' data-media-type='image' style='font-size: 17.5px; cursor: default; text-align: center; background-color: rgb(245, 250, 255); display: inline-block; margin: auto 35px; height: 55px; color: rgb(0, 0, 0); font-family: &quot;Microsoft YaHei&quot;, STXihei; width: 60px; max-width: 800px !important;'><span style='font-size: 17.5px; text-align: center;'>&nbsp;<b>实时保存</b>&nbsp; &nbsp; &nbsp;Ctrl+S，编辑框失焦，保存按钮，三种方法帮你实时保存自己的笔记。</span></p><p align='left' style='text-align: justify;'><img src='http://note.youdao.com/yws/res/2/BC6E60AD2F254333A856F7045279BBF3' data-ke-src='http://note.youdao.com/yws/res/38581/BC6E60AD2F254333A856F7045279BBF3' alt='icon-4.png' data-media-type='image' style='font-size: 17.5px; cursor: default; text-align: center; background-color: rgb(245, 250, 255); display: inline-block; margin: auto 35px; height: 55px; color: rgb(0, 0, 0); font-family: &quot;Microsoft YaHei&quot;, STXihei; width: 60px; max-width: 800px !important;'><span style='font-size: 17.5px; text-align: center;'>&nbsp;<b>协作共享</b>&nbsp; &nbsp; &nbsp;笔记可通过输入用户名，分享给他人，协作完成笔记。</span></p><p align='left' style='text-align: justify;'><img src='http://note.youdao.com/yws/res/5/57436F8839814351ABFCCD43722E756A' data-ke-src='http://note.youdao.com/yws/res/38580/57436F8839814351ABFCCD43722E756A' alt='icon-3.png' data-media-type='image' style='font-size: 17.5px; cursor: default; text-align: center; background-color: rgb(245, 250, 255); display: inline-block; margin: auto 35px; height: 55px; color: rgb(0, 0, 0); font-family: &quot;Microsoft YaHei&quot;, STXihei; width: 60px; max-width: 800px !important;'><span style='font-size: 17.5px; text-align: center;'><b>&nbsp;自适应&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</b>网页可根据分辨率大小自动调整，所有手机端，电脑端都可以正常使用。</span></p>";
		if(note == null){
			Note notelp =create_node("关于随记", text,req);
		}
		List notes = noteDao.findAllNotes(a);
		return notes;
	}
	//更新note
	public void update(String id, String title, String content){
		if(title.length() > 1){
			note.setId(id);
			note.setTitle(title);
			note.setContent(content);
			noteDao.updateNote(note, title, content);
		}else{
			throw new ServiceException("note.title","title.length.short");
		}
	}
	//查找受分享的note
	public List findShare(String user_name){
		List note_id_list = sharedao.share_find_noteid(user_name);
		if(note_id_list!=null){
			List note = new ArrayList();
			for(int i=0;i<note_id_list.size();i++){
				String node_id = ((Share) note_id_list.get(i)).getNode();
				Note node = (Note) (notedao.findNote_id(node_id).get(0));
				node.setQx(((Share) note_id_list.get(i)).getEditable());
				note.add(node);
			}
			return note;
		}
		else{
			return null;
		}
	}
}
