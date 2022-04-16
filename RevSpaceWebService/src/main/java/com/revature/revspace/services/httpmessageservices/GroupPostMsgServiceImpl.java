package com.revature.revspace.services.httpmessageservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.revspace.models.GroupPost;
import com.revature.revspace.models.httpmsgmodel.GroupPostMsg;

@Service
public class GroupPostMsgServiceImpl implements GroupPostMsgService 
{

	@Override
	public List<GroupPostMsg> convertAllGroupPosts(List<GroupPost> list) 
	{
		List<GroupPostMsg> resultList = null;
		
		try
		{
			if(list.isEmpty())
				throw new Exception("GroupPost list is empty");
				
			resultList = new ArrayList<GroupPostMsg>();
			
			for(GroupPost p : list)
			{
				resultList.add(new GroupPostMsg(p));
			}
			
		}catch(Exception e)
		{
			
		}
		
		return resultList;
	}

}
