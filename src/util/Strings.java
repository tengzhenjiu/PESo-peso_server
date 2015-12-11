package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Strings {

	public static String getterNameTransfer(String name) {
		if (name == null || !name.startsWith("get")
				|| name.trim().length() < 4)
			return null;
		char[] narr = name.substring(3).toCharArray();
		StringBuffer sb = new StringBuffer();
		boolean start_res_flag = true;
		for (char c : narr) {
			if (start_res_flag) {
				if (c == '_' || c == '$') {
					continue;
				} else {
					sb.append(c >= 'A' && c <= 'Z' ? (char) (c + 32)
							: c);
					start_res_flag = false;
				}
			} else {
				if (c >= 'A' && c <= 'Z') {
					sb.append('_').append((char) (c + 32));
				} else {
					sb.append(c);
				}
			}
		}

		return sb.toString();
	}

	public static String setterNameTransfer(String name) {
		if (name == null || !name.startsWith("set")
				|| name.trim().length() < 4)
			return null;
		char[] narr = name.substring(3).toCharArray();
		StringBuffer sb = new StringBuffer();
		boolean start_res_flag = true;
		for (char c : narr) {
			if (start_res_flag) {
				if (c == '_' || c == '$') {
					continue;
				} else {
					sb.append(c >= 'A' && c <= 'Z' ? (char) (c + 32)
							: c);
					start_res_flag = false;
				}
			} else {
				if (c >= 'A' && c <= 'Z') {
					sb.append('_').append((char) (c + 32));
				} else {
					sb.append(c);
				}
			}
		}

		return sb.toString();
	}

	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isPureEnglish(String str) {
		if (str == null)
			return false;
		return str.length() == str.getBytes().length;
	}

	/**
	 * check if it is Chinese Word
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasChineseWord(String str) {
		if (str == null)
			return false;
		int length = str.length();
		String cnReg = ("[\u4E00-\u9fa5]");
		for (int i = 0; i < length; i++)
			if (str.substring(i, i + 1).matches(cnReg))
				return true;
		return false;
	}

	static Set<String> complexLastNameSet = new HashSet<String>();

	public static void initComplexLastNameSet() {
		String line;
		FileIO f = new FileIO("transfer/complexLastName.txt",
				FileIO.RW.READ);
		while ((line = f.readLine()) != null) {
			if (Strings.isNotEmpty(line)) {
				complexLastNameSet.add(line.trim());
			}
		}
		f.close();
		// f = new FileIO("transfer/japaneseLastName.txt",
		// FileIO.RW.READ);
		// f.close();
	}

	public static boolean isNumberCharacter(byte t) {
		return t >= '0' && t <= '9';
	}

	private static int byte2int(byte t) {
		return t - '0';
	}

	public static int checkYear(byte[] t) {
		try {

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int findYearFromString(String str) {
		byte[] bStr = str.getBytes();
		int len = bStr.length - 3;
		for (int offset = 0; offset < len; offset++) {
			try {
				if ((bStr[offset] == '1' && bStr[offset + 1] == '9')
						|| (bStr[offset] == '2' && bStr[offset + 1] == '0')) {
					if (isNumberCharacter(bStr[offset + 2])
							&& isNumberCharacter(bStr[offset + 3])) {
						return byte2int(bStr[offset])
								* 1000
								+ byte2int(bStr[offset + 1])
								* 100
								+ byte2int(bStr[offset + 2])
								* 10
								+ byte2int(bStr[offset + 3]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1970;
	}

	public static String getStringByArray(String str, String deli, int idx) {
		if (isEmpty(str))
			return "";
		String[] strArray = str.split(deli);
		if (strArray.length <= idx)
			return "";
		return strArray[idx];
	}

	public static int editDistance(String s1, String s2) {
		EditDistance ed = new EditDistance() {
			@Override
			protected boolean isSame(byte a, byte b) {
				return toLowerCase(a) == toLowerCase(b);
			}
		};
		return ed.editDistance(s1, s2);
	}

	public static double editDistanceScore(String s1, String s2) {
		EditDistance ed = new EditDistance() {
			@Override
			protected boolean isSame(byte a, byte b) {
				return toLowerCase(a) == toLowerCase(b);
			}
		};
		return ed.editDistanceScore(s1, s2);
	}

	public static String titleHash(String s0) {
		if (Strings.isEmpty(s0))
			return "";
		StringBuffer sb = new StringBuffer();
		char[] ca = s0.replaceAll("[\\pP‘’“”]", " ").toLowerCase()
				.toCharArray();
		boolean toShow = true;
		for (char ch : ca) {
			if (toShow && ch != ' ') {
				sb.append(ch);
				toShow = false;
			}
			if (ch == ' ') {
				toShow = true;
			}
		}
		return sb.toString();
	}

	public static String nameHash(String s0) {
		if (Strings.isEmpty(s0))
			return "";
		StringBuffer sb = new StringBuffer();
		char[] ca = s0.replaceAll("[\\pP‘’“”]", " ").toLowerCase()
				.toCharArray();
		boolean toShow = true;
		for (char ch : ca) {
			if (toShow && ch != ' ') {
				sb.append(ch);
				toShow = false;
			}
			if (ch == ' ') {
				toShow = true;
			}
		}
		return sb.toString();
	}

	public static List<String> tokenSplit(String s) {
		List<String> tokens = new ArrayList<String>();
		String[] sarr = s.toLowerCase()
				.split("[\\.,\\?!？！。、‘’“”$\\s ]");
		// String[] sarr = s.toLowerCase().replace("\\-",
		// "").split("[^0-9a-zA-Z]");
		for (String is : sarr) {
			if (Strings.isNotEmpty(is)) {
				tokens.add(is);
			}
		}
		return tokens;
	}

	public static boolean nameComare(String n1, String n2) {
		String[] n1a = n1.toLowerCase().replaceAll("\\-", "")
				.replaceAll("[^a-zA-Z]", " ")
				.replaceAll("  ", " ").split(" ");
		String[] n2a = n2.toLowerCase().replaceAll("\\-", "")
				.replaceAll("[^a-zA-Z]", " ")
				.replaceAll("  ", " ").split(" ");
		if (n1a.length != n2a.length)
			return false;
		if (n1a.length == 2) {
			return (nameCompareChk(n1a[0], n2a[0]) && nameCompareChk(
					n1a[1], n2a[1]))
					|| (nameCompareChk(n1a[0], n2a[1]) && nameCompareChk(
							n1a[1], n2a[0]));
		}
		return false;
	}

	// a simple tokenlizer
	public static Map<String, Integer> tokenExtractor(String s) {
		Map<String, Integer> tokens = new HashMap<String, Integer>();
		// String[] raws = s.toLowerCase().replaceAll("\\-",
		// "").replaceAll("[^a-zA-Z]", " ").toLowerCase().split(" ");
		String[] raws = s.toLowerCase().replaceAll("and", "&")
				.replaceAll("[\\s\u00A0]", " ")
				.split("[,\\?!？！。、‘’“”$]");
		for (String raw : raws) {
			if (Strings.isNotEmpty(raw) && raw.trim().length() > 2) { // at
											// least
											// 2
											// character...
				raw = Stemmer.getStemmer(raw).trim();
				if (raw.startsWith("dept.")
						|| raw.startsWith("depart")) {
					if (!raw.contains("univ")) {
						continue;
					}
				}
				if (tokens.containsKey(raw)) {
					tokens.put(raw, tokens.get(raw) + 1);
				} else {
					tokens.put(raw, 1);
				}
			}
		}
		return tokens;
	}

	// static String [] orgTokens = {"",""};

	public static String stemZhOrganization(String s) {
		return Strings.isEmpty(s) ? null : s.replaceAll(
				"(大学|医院|[^科]学院|检察院|研究所|公司|水利局)[^附]*", "$1")
				.replaceAll("\\(.*\\)", "");
	}

	private static boolean nameCompareChk(String s1, String s2) {
		if (Strings.isEmpty(s1) || Strings.isEmpty(s2))
			return false;
		// int minLen = s1.length() < s2.length() ? s1.length() :
		// s2.length();
		// System.out.println((s1.length() < s2.length() ? s1 +
		// "[compare to]" + s2.substring(0, s1.length()) : s2 +
		// "[compare to]" + s1.substring(0, s2.length())));
		return s1.length() < s2.length() ? s1.equals(s2.substring(0,
				s1.length())) : s2.equals(s1.substring(0,
				s2.length()));
	}

	// public static void main(String[] args) {
	// List<String> names = new ArrayList<String>();
	// names.add("getDoi");
	// names.add("get_abstract");
	// names.add("getPageStart");
	// names.add("getClass");
	// names.add("getAuthorList");
	// for (String n : names) {
	// System.out.println(n + " => " + Strings.getterNameTransfer(n));
	// }
	// String originStr =
	// "Towards Mo-re Intell$ig\ne\rnt Mobi\tle vv.xiz,ff?13!fg‘df’df“d”fa$ Search.are";
	// System.out.println(originStr + " => " +
	// Strings.titleHash(originStr));
	// List<String> tokens = Strings.tokenSplit(originStr);
	// for (String t : tokens) {
	// System.out.println(">[" + t + "]");
	// }
	// String n1 = "Juan-zi Li";
	// String n2 = "Li, Juanzi";
	// System.out.println(Strings.nameComare(n1, n2));
	// System.out.println(Strings.stemZhOrganization("北京工商大学机械工程学院"));
	// Map<String, Integer> tokens =
	// tokenExtractor("EECS Department, Computer Science Division, University of California, Berkeley, CA");
	// for (Map.Entry<String, Integer> item : tokens.entrySet()) {
	// System.out.println("[" + item.getKey() + "] # [" + item.getValue() +
	// "]");
	// }
	// System.out.println("name hash :" +
	// Strings.titleHash("Philippe G. Ciarlet"));
	// public static boolean maySameAuthorList(List<AuthorInPub> al1,
	// List<AuthorInPub> al2) {
	// if (al1 == null || al1.size() == 0)
	// return true;
	// if (al2 == null || al2.size() == 0)
	// return true;
	// AuthorInPub aip1 = al1.get(0);
	// AuthorInPub aip2 = al2.get(0);
	// String aipnh1 = Strings.titleHash(aip1.getName());
	// String aipnh2 = Strings.titleHash(aip2.getName());
	// if (Strings.isEmpty(aipnh1) || Strings.isEmpty(aipnh2))
	// return true;
	// if (!aipnh1.equals(aipnh2))
	// return false;
	// return true;
	// }
	// List<AuthorInPub> al1 = new ArrayList<AuthorInPub>();
	// List<AuthorInPub> al2 = new ArrayList<AuthorInPub>();
	// al1.add(new AuthorInPub());
	// al1.get(0).setName("P. G. Ciarlet");
	// al2.add(new AuthorInPub());
	// al2.get(0).setName("Philippe G. Ciarlet");
	//
	// System.out.println("compare :" +
	// PubDuplication.maySameAuthorList(al1, al2));
	// System.out.println("Strings ed: " +
	// Strings.editDistanceScore("植物研究所", "植物研究所教授"));

	// String affRawStr = "原复旦大学教授，现南京邮电大学教授。";
	// List<String> affStrs = new ArrayList<String>();
	// affRawStr = affRawStr.replaceAll("教授", "");
	// affRawStr = affRawStr.replaceAll("研究员", "");
	// affRawStr = affRawStr.replaceAll("。", "");
	// if (affRawStr.contains("，")) {
	// String[] subs = affRawStr.split("，");
	// for (String sub : subs) {
	// if (sub.startsWith("原") || sub.startsWith("现")) {
	// affStrs.add(sub.substring(1));
	// }
	// }
	// } else {
	// affStrs.add(affRawStr);
	// }
	// for(String s : affStrs){
	// System.out.println("[" + s + "]");
	// }
	// System.out.println(affStrs);
	// }
}
