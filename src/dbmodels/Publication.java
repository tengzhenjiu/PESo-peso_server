package dbmodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.mongo.MongodbObjectSerializer;

import com.mongodb.DBObject;

public class Publication implements util.mongo.BasicMongoModel, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 823468759849995522L;

	private String id;
	private String title; //
	// private String titleZh;
	private String hash;
	// abstract
	private String _abstract; // abstract
	// private String _abstractZh; // abstract_zh

	private String keywords;
	// private String keywordsZh; // keywordsZh

	// private List<String> _keywords;
	// private List<String> _keywordsZh;

	private String pageStart; // page_start
	private String pageEnd;// page_end
	private String pageStr;
	private String volume;

	private String issue;

	private String authorStr; // authors string
	private List<AuthorInPub> authors;
	private String isbn;
	private String issn;
	private String doi;
	private String src;

	private String lang;
	private List<String> pdf_src;
	private String date; // publish date
	private Integer year;
	private Date ts;

	private VenueInPub venue;
	private List<CitationInfo> reference;
	private List<String> url;
	private String sid;

	/*******************************************************************************************
	 * 
	 * Setters & Getters Section
	 * 
	 *******************************************************************************************/

	@Override
	public DBObject toMongodbObject() {
		return MongodbObjectSerializer.toMongodbObject(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title != null && title.trim().length() > 0) {
			this.title = title.replace('\u00A0', ' ').trim();
		}
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String get_abstract() {
		return _abstract;
	}

	public void set_abstract(String _abstract) {
		if (_abstract != null && _abstract.trim().length() > 0) {
			this._abstract = _abstract.replace('\u00A0', ' ')
					.trim();
		}
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		if (keywords != null && keywords.trim().length() > 0) {
			this.keywords = keywords.replace('\u00A0', ' ').trim();
		}
	}

	public String getPageStart() {
		return pageStart;
	}

	public void setPageStart(String pageStart) {
		if (pageStart != null && pageStart.trim().length() > 0) {
			this.pageStart = pageStart.replace('\u00A0', ' ')
					.trim();
		}
	}

	public String getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(String pageEnd) {
		if (pageEnd != null && pageEnd.trim().length() > 0) {
			this.pageEnd = pageEnd.replace('\u00A0', ' ').trim();
		}
	}

	public String getPageStr() {
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		if (pageStr != null && pageStr.trim().length() > 0) {
			this.pageStr = pageStr.replace('\u00A0', ' ').trim();
		}
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		if (date != null && date.trim().length() > 0) {
			this.date = date.replace('\u00A0', ' ').trim();
		}
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		if (volume != null && volume.trim().length() > 0) {
			this.volume = volume.replace('\u00A0', ' ').trim();
		}
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		if (issue != null && issue.trim().length() > 0) {
			this.issue = issue.replace('\u00A0', ' ').trim();
		}
	}

	public String getAuthorStr() {
		return authorStr;
	}

	public void setAuthorStr(String authorStr) {
		if (authorStr != null && authorStr.trim().length() > 0) {
			this.authorStr = authorStr.replace('\u00A0', ' ')
					.trim();
		}
	}

	public List<AuthorInPub> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorInPub> authors) {
		this.authors = authors;
	}

	public VenueInPub getVenue() {
		return venue;
	}

	public void setVenue(VenueInPub venue) {
		this.venue = venue;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		if (isbn != null && isbn.trim().length() > 0) {
			this.isbn = isbn.replace('\u00A0', ' ').trim();
		}
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		if (issn != null && issn.trim().length() > 0) {
			this.issn = issn.replace('\u00A0', ' ').trim();
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

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String srcId) {
		if (srcId != null && srcId.trim().length() > 0) {
			this.sid = srcId.replace('\u00A0', ' ').trim();
		}
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang
	 *                the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	public List<String> getPdf_src() {
		return pdf_src;
	}

	public void setPdf_src(List<String> pdf_src) {
		this.pdf_src = pdf_src;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public void setTs() {
		this.ts = new Date();
	}

	public List<CitationInfo> getReference() {
		return reference;
	}

	public void setReference(List<CitationInfo> reference) {
		this.reference = reference;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Publication fromMongdbObject(DBObject obj) {
		Publication pub = new Publication();
		if (obj.containsField("_id")) {
			pub.setId(obj.get("_id").toString());
		}
		// private String id;
		// private String title; //
		if (obj.containsField("title")) {
			pub.setTitle(obj.get("title").toString());
		}
		// private String hash;
		if (obj.containsField("hash")) {
			pub.setHash(obj.get("hash").toString());
		}

		// private String _abstract; // abstract
		if (obj.containsField("abstract")) {
			pub.set_abstract(obj.get("abstract").toString());
		}

		// private String pageStart; // page_start
		if (obj.containsField("page_start")) {
			pub.setPageStart(obj.get("page_start").toString());
		}
		// private String pageEnd;// page_end
		if (obj.containsField("page_end")) {
			pub.setPageEnd(obj.get("page_end").toString());
		}
		// private String pageStr;
		if (obj.containsField("page_str")) {
			pub.setPageStr(obj.get("page_str").toString());
		}

		// private Integer year;
		if (obj.containsField("year")) {
			pub.setYear((Integer) obj.get("year"));
		}
		// private String date; // publish date
		if (obj.containsField("date")) {
			pub.setDate(obj.get("date").toString());
		}
		// private String volume;
		if (obj.containsField("volume")) {
			pub.setVolume(obj.get("volume").toString());
		}
		// private String issue;
		if (obj.containsField("issue")) {
			pub.setIssue(obj.get("issue").toString());
		}

		//
		// // private String editor; // some book
		// private List<AuthorInPub> editor;
		// private String authorStr; // authors string
		if (obj.containsField("author_str")) {
			pub.setAuthorStr(obj.get("author_str").toString());
		}
		// private String authorStrZh;
		// private List<AuthorInPub> authors;
		if (obj.containsField("authors")) {
			pub.setAuthors(AuthorInPub.fromMongoDBL(obj
					.get("authors")));
		}

		// private VenueInPub venue;
		if (obj.containsField("venue")) {
			pub.setVenue(VenueInPub.fromMongoDB(obj.get("venue")));
		}
		// private String isbn;
		if (obj.containsField("isbn")) {
			pub.setIsbn(obj.get("isbn").toString());
		}
		// private String issn;
		if (obj.containsField("issn")) {
			pub.setIssn(obj.get("issn").toString());
		}
		// private String doi;
		if (obj.containsField("doi")) {
			pub.setDoi(obj.get("doi").toString());
		}
		// private List<String> url;
		if (obj.containsField("url")) {
			List<String> urlList = new ArrayList<String>();
			for (Object urlItem : (List<Object>) obj.get("url")) {
				urlList.add(urlItem.toString());
			}
			pub.setUrl(urlList);
		}

		// // cite/citation
		// // cited times
		// private Integer nCitation; // n_citation a known
		//
		// // src:type [dblp,acm,ms,scopus,ieee,el,cnki]
		// private String src;
		if (obj.containsField("src")) {
			pub.setSrc(obj.get("src").toString());
		}
		// private String sid;
		if (obj.containsField("sid")) {
			pub.setSid(obj.get("sid").toString());
		}
		// private String lang;
		if (obj.containsField("lang")) {
			pub.setLang(obj.get("lang").toString());
		}
		if (obj.containsField("ts")) {
			pub.setTs((Date) obj.get("ts"));
		}
		if (obj.containsField("pdf_src")) {
			pub.setPdf_src((List) (obj.get("pdf_src")));
		}

		return pub;
	}

	@Override
	public String toString() {
		return "Publication [id=" + id + ", title=" + title
				+ ",  hash=" + hash + ", _abstract="
				+ _abstract + ", " + "keywords=" + keywords
				+ ", pageStart=" + pageStart + ", pageEnd="
				+ pageEnd + ", " + "pageStr=" + pageStr
				+ ", year=" + year + ", date=" + date
				+ ", volume=" + volume + ", issue=" + issue
				+ ",  authorStr=" + authorStr + ",  authors="
				+ authors + ", venue=" + venue + ", isbn="
				+ isbn + ", issn=" + issn + ", doi=" + doi
				+ ", url=" + url + ",]";
	}
}
