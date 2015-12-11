package dbmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import util.mongo.MongodbObjectSerializer;

import com.mongodb.DBObject;

public class AuthorInPub implements util.mongo.BasicMongoModel, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5421616090380149216L;
	String name;
	String nameZh;
	String email;
	String org;
	String orgZh;
	String id;
	String sid;
	// String bib; // bibtex
	Integer pos;

	/**
	 * set special id :
	 */
	public static AuthorInPub fromMongoDB(DBObject obj) {
		AuthorInPub aip = new AuthorInPub();
		if (obj.containsField("_id")) {
			aip.setId(obj.get("_id").toString());
		}
		if (obj.containsField("sid")) {
			aip.setSid(obj.get("sid").toString());
		}
		if (obj.containsField("name")) {
			aip.setName(obj.get("name").toString());
		}
		if (obj.containsField("name_zh")) {
			aip.setNameZh(obj.get("name_zh").toString());
		}
		if (obj.containsField("email")) {
			aip.setEmail(obj.get("email").toString());
		}
		if (obj.containsField("org")) {
			aip.setOrg(obj.get("org").toString());
		}
		if (obj.containsField("org_zh")) {
			aip.setOrgZh(obj.get("org_zh").toString());
		}
		return aip;
	}

	@SuppressWarnings("unchecked")
	public static List<AuthorInPub> fromMongoDBL(Object aiplObj) {
		List<DBObject> objs = (List<DBObject>) aiplObj;
		List<AuthorInPub> aips = new ArrayList<AuthorInPub>();
		for (DBObject obj : objs) {
			aips.add(fromMongoDB(obj));
		}
		return aips;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *                the name to set
	 */
	public void setName(String name) {
		if (name != null && name.trim().length() > 0) {
			this.name = name.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *                the id to set
	 */
	public void setId(String id) {
		if (id != null && id.trim().length() > 0) {
			this.id = id.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *                the sid to set
	 */
	public void setSid(String sid) {
		if (sid != null && sid.trim().length() > 0) {
			this.sid = sid.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *                the email to set
	 */
	public void setEmail(String email) {
		if (email != null && email.trim().length() > 0) {
			this.email = email.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the org
	 */
	public String getOrg() {
		return org;
	}

	/**
	 * @param org
	 *                the org to set
	 */
	public void setOrg(String org) {
		if (org != null && org.trim().length() > 0) {
			this.org = org.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the nameZh
	 */
	public String getNameZh() {
		return nameZh;
	}

	/**
	 * @param nameZh
	 *                the nameZh to set
	 */
	public void setNameZh(String nameZh) {
		if (nameZh != null && nameZh.trim().length() > 0) {
			this.nameZh = nameZh.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the orgZh
	 */
	public String getOrgZh() {
		return orgZh;
	}

	/**
	 * @param orgZh
	 *                the orgZh to set
	 */
	public void setOrgZh(String orgZh) {
		if (orgZh != null && orgZh.trim().length() > 0) {
			this.orgZh = orgZh.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the pos
	 */
	public Integer getPos() {
		return pos;
	}

	/**
	 * @param pos
	 *                the pos to set
	 */
	public void setPos(Integer pos) {
		this.pos = pos;
	}

	// public String getBib() {
	// return bib;
	// }
	// public void setBib(String bib) {
	// this.bib = bib;
	// }
	@Override
	public DBObject toMongodbObject() {
		return MongodbObjectSerializer.toMongodbObject(this);
	}

	@Override
	public String toString() {
		return "AuthorInPub [name=" + name + ", nameZh=" + nameZh
				+ ", email=" + email + ", org=" + org
				+ ", orgZh=" + orgZh + ", id=" + id + ", sid="
				+ sid + ", pos=" + pos + "]";
	}

}
