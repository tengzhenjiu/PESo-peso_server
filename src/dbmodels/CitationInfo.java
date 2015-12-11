package dbmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import util.mongo.MongodbObjectSerializer;

import com.mongodb.DBObject;

public class CitationInfo implements util.mongo.BasicMongoModel, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1444973148193272399L;

	String raw;
	String sid;
	Integer seq;
	String id;
	String doi;

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		if (raw != null && raw.trim().length() > 0) {
			this.raw = raw.replace('\u00A0', ' ').trim();
		}
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		if (sid != null && sid.trim().length() > 0) {
			this.sid = sid.replace('\u00A0', ' ').trim();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null && id.trim().length() > 0) {
			this.id = id.replace('\u00A0', ' ').trim();
		}
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		if (doi != null && doi.trim().length() > 0) {
			this.doi = doi.replace('\u00A0', ' ').trim();
		}
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public static CitationInfo fromMongoDB(Object iobj) {
		DBObject obj = (DBObject) iobj;
		CitationInfo ci = new CitationInfo();
		if (obj.containsField("_id")) {
			ci.setId(obj.get("_id").toString());
		}
		if (obj.containsField("raw")) {
			ci.setRaw(obj.get("raw").toString());
		}
		if (obj.containsField("sid")) {
			ci.setSid(obj.get("sid").toString());
		}
		if (obj.containsField("doi")) {
			ci.setDoi(obj.get("doi").toString());
		}
		if (obj.containsField("seq")) {
			ci.setSeq((Integer) obj.get("seq"));
		}
		return ci;
	}

	@SuppressWarnings("unchecked")
	public static List<CitationInfo> fromMongoDBL(Object objl) {
		List<CitationInfo> cil = new ArrayList<CitationInfo>();
		for (Object obj : (List<Object>) objl) {
			cil.add(fromMongoDB(obj));
		}
		return cil;
	}

	@Override
	public DBObject toMongodbObject() {
		return MongodbObjectSerializer.toMongodbObject(this);
	}
}