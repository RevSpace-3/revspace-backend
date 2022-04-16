package com.revature.revspace.services.httpmessageservices;

import java.util.List;

import com.revature.revspace.models.GroupPost;
import com.revature.revspace.models.httpmsgmodel.GroupPostMsg;

public interface GroupPostMsgService 
{
	List<GroupPostMsg> convertAllGroupPosts(List<GroupPost> list);
}
