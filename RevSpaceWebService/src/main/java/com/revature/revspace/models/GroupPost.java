package com.revature.revspace.models;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "grouppost")
public class GroupPost 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	
	private String body;
	private String datePosted;
	
	private int numOfLikes = 0;
	
	@ColumnDefault("false")
	private boolean isComment;
	
	@OneToOne
	@JoinColumn(name="userId")
	private User owner;
	
	// Forward post
	@OneToOne
	@JoinColumn(name="postId")
	private GroupPost parent;
	
	// Back Post
	@OneToOne
	@JoinColumn(name="postId")
	private GroupPost child;
	
	@ManyToMany
	@JoinTable(name = "grouppostlikes", joinColumns = @JoinColumn(name = "like_Id"), inverseJoinColumns = @JoinColumn(name = "post_Id"))
	private List<Like> likes;

}
