package com.revature.revspace.services;


import com.revature.revspace.models.Post;
import com.revature.revspace.models.User;
import com.revature.revspace.repositories.PostRepo;

import java.util.List;
import java.util.Set;

public interface PostService extends CrudService<Post, Integer, PostRepo>{

    public List<List<Post>> pullPostsList(int lastPostIdOnThePage, User currentUser);
    List<List<Post>> pullPostsListFollowing(User currentUser);
    public List<Post> selectedRelatedComments (Post parentsPost, List<Post> allComments);
    
    public List<Post> getPostsByGroupId(int id);
    public List<Post> getCommentsByGroupId(int id);

}