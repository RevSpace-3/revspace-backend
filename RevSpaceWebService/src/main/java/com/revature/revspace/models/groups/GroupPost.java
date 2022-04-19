package com.revature.revspace.models.groups;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.revspace.models.User;

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
	private String image;
	private String datePosted;
	
	private int numOfLikes;
	
	private boolean comment;
	
	@OneToOne
	@JoinColumn(name="userId")
	private User owner;
	
	// Forward post
	@OneToOne
	@JoinColumn(name="parentId", referencedColumnName="postId")//, insert="false", update="false")
	private GroupPost parent;

	public GroupPost(String body, String image, String date, int numLikes, boolean comFlag, User owner, GroupPost parent)//, List<Like> list)
	{
		this(0, body, image, date, numLikes, comFlag, owner, parent);
	}
}
