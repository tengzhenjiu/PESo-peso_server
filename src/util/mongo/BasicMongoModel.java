package util.mongo;

import com.mongodb.DBObject;

public interface BasicMongoModel {
	public abstract DBObject toMongodbObject();
}
