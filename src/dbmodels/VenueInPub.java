package dbmodels;

import java.io.Serializable;

import util.mongo.MongodbObjectSerializer;

import com.mongodb.DBObject;

public class VenueInPub implements util.mongo.BasicMongoModel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3397857059530374368L;
	private String id; // joural id
	private String raw;
	private String rawZh;
	private Integer type; // journal , conference

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
		this.id = id;
	}

	/**
	 * @return the rawString
	 */
	public String getRaw() {
		return raw;
	}

	/**
	 * @param raw
	 *                the rawString to set
	 */
	public void setRaw(String raw) {
		if (raw != null && raw.trim().length() > 0) {
			this.raw = raw.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type
	 *                the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the rawZh
	 */
	public String getRawZh() {
		return rawZh;
	}

	/**
	 * @param rawZh
	 *                the rawZh to set
	 */
	public void setRawZh(String rawZh) {
		if (rawZh != null && rawZh.trim().length() > 0) {
			this.rawZh = rawZh.replace('\u00A0', ' ').trim();
		}
	}

	@Override
	public DBObject toMongodbObject() {
		return MongodbObjectSerializer.toMongodbObject(this);
	}

	public static VenueInPub fromMongoDB(Object iobj) {
		DBObject obj = (DBObject) iobj;
		VenueInPub vip = new VenueInPub();
		if (obj.containsField("_id")) {
			vip.setId(obj.get("_id").toString());
		}
		if (obj.containsField("raw")) {
			vip.setRaw(obj.get("raw").toString());
		}
		if (obj.containsField("raw_zh")) {
			vip.setRawZh(obj.get("raw_zh").toString());
		}
		if (obj.containsField("type")) {
			vip.setType((Integer) obj.get("type"));
		}
		return vip;
	}

	@Override
	public String toString() {
		return "VenueInPub [id=" + id + ", raw=" + raw + ", rawZh="
				+ rawZh + ", type=" + type + "]";
	}

	public static void main(String[] args) {
		VenueInPub vip = new VenueInPub();
		vip.setId("5390877920f70186a0d2ca7e");
		vip.setRaw("__raw__");
		vip.setRawZh("__RAW_ZH__");
		vip.setType(1125);
		System.out.println(VenueInPub.fromMongoDB(vip.toMongodbObject()));
	}

}
