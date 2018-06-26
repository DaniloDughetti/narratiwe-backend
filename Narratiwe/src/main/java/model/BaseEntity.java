package model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;

public abstract class BaseEntity {

	  @Id
	  protected ObjectId id;
	  protected Date creationDate;
	  protected Date lastChange;

	  public BaseEntity() {
	    super();
	  }

	  public ObjectId getId() {
	    return id;
	  }

	  public Date getCreationDate() {
	    return creationDate;
	  }

	  public Date getLastChange() {
	    return lastChange;
	  }

	  @PrePersist
	  public void prePersist() {
	    this.creationDate = (creationDate == null) ? new Date() : creationDate;
	    this.lastChange = (lastChange == null) ? creationDate : new Date();
	  }

	  public abstract String toString();

	}
