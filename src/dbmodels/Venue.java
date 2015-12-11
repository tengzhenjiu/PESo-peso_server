package dbmodels;

import java.io.Serializable;
import java.util.Date;

import util.mongo.MongodbObjectSerializer;

import com.mongodb.DBObject;

public class Venue implements util.mongo.BasicMongoModel, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2708430512202516590L;
	private String sid; // format: src_type:
	private String id;
	private String shortName;
	private String url;
	private String name;
	private String nameZh;
	private Integer type; // journal : 0
				// conference : 1
				// ...
	private Double _if;

	private String publisher; //
	private String publisherUrl;

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
		this.name = name;
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
	 * @return the publiserStr
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisherName
	 *                the publiserStr to set
	 */
	public void setPublisher(String publisherName) {
		this.publisher = publisherName;
	}

	/**
	 * @return the impactFactor
	 */
	public Double getIf() {
		return _if;
	}

	/**
	 * @param impactFactor
	 *                the impactFactor to set
	 */
	public void setIf(Double impactFactor) {
		this._if = impactFactor;
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
		this.nameZh = nameZh;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPublisherUrl() {
		return publisherUrl;
	}

	public void setPublisherUrl(String publisherUrl) {
		this.publisherUrl = publisherUrl;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private Date ts;

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public void setTs() {
		this.ts = new Date();
	}

	@Override
	public DBObject toMongodbObject() {
		return MongodbObjectSerializer.toMongodbObject(this);
	}

	@Override
	public String toString() {
		return "Venue [sid=" + sid + ", id=" + id + ", shortName="
				+ shortName + ", url=" + url + ", name=" + name
				+ ", nameZh=" + nameZh + ", type=" + type
				+ ", _if=" + _if + ", publisher=" + publisher
				+ ", publisherUrl=" + publisherUrl + "]";
	}

}
