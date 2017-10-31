package com.notes.Service;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.notes.Util.BasePhoto;

@Component
@Transactional
public class PhotoService {

	public boolean savephoto(String base_url,String Folder,String file_name){
		String file_url = "src/main/resources/static/images/"+Folder+"/"+file_name+".jpg";
	
		boolean c = BasePhoto.GenerateImage(base_url,file_url);
		return c;
	}
}
