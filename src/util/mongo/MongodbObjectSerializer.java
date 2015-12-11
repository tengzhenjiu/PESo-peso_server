package util.mongo;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import util.Strings;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongodbObjectSerializer {
	// private static boolean isBasicType(Class item) {
	// return item.isPrimitive() || item.equals(Integer.class) //
	// || item.equals(Long.class) || item.equals(String.class) //
	// || item.equals(Double.class) || item.equals(Float.class) ||
	// item.isEnum(); // is this ok ? i'm not sure
	// }

	// Serializable.class.isInstance(item)
	// @Deprecated
	private static boolean isBasicType(Object item) {
		return item.getClass().isPrimitive()
				|| Integer.class.isInstance(item)
				|| Long.class.isInstance(item)
				|| String.class.isInstance(item)
				|| Double.class.isInstance(item)
				|| Float.class.isInstance(item)
				|| item.getClass().isEnum()
				|| java.util.Date.class.isInstance(item);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getMapObject(final Map<?, ?> items) {
		Map map = new HashMap();
		Iterator<?> it = items.entrySet().iterator();
		Entry item = null;
		if (it.hasNext() && ((item = (Entry) it.next()) != null)) {
			if (!isBasicType(item.getKey())) {
				Logger.getLogger(MongodbObjectSerializer.class)
						.error("ERROR KEY TYPE !!!!"
								+ item.toString());
				return map;
			}
			if (isBasicType(item.getValue())) {
				return items;
			}
			if (List.class.isInstance(item)) {
				do {
					map.put(item.getKey(),
							getListObject((List) (item
									.getValue())));
				} while (it.hasNext()
						&& ((item = (Entry) it.next()) != null));
			} else if (Map.class.isInstance(item)) {
				do {
					map.put(item.getKey(),
							getMapObject((Map) (item
									.getValue())));
				} while (it.hasNext()
						&& ((item = (Entry) it.next()) != null));
			} else if (BasicMongoModel.class.isInstance(item)) {
				do {
					map.put(item.getKey(),
							((BasicMongoModel) item)
									.toMongodbObject());
				} while (it.hasNext()
						&& ((item = (Entry) it.next()) != null));
			} else {
				Logger.getLogger(MongodbObjectSerializer.class)
						.warn("[getMapObject] unknown type ...");
			}
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DBObject> getListObject(List<?> items) {
		List list = new ArrayList();
		for (Object item : items) {
			if (item != null && isBasicType(item)) {
				list.add(item);
			} else if (List.class.isInstance(item)) {
				list.add(getListObject((List) item));
			} else if (Map.class.isInstance(item)) {
				list.add(getMapObject((Map) item));
			} else if (BasicMongoModel.class.isInstance(item)) {
				list.add(((BasicMongoModel) item)
						.toMongodbObject());
			} else {
				Logger.getLogger(MongodbObjectSerializer.class)
						.warn("[getListObject] unknown type ...");
			}
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static DBObject toMongodbObject(Object destClass) {
		DBObject obj = new BasicDBObject();
		Method[] methods = destClass.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get")
					&& !method.getName().equals("getClass")) {
				String name = Strings.getterNameTransfer(method
						.getName());
				// Logger.getLogger(getClass()).info(name);
				if (name.equals("id")) {
					try {
						Object idVal = method
								.invoke(destClass);
						if (idVal != null
								&& Strings.isNotEmpty(idVal
										.toString())) {
							obj.put("_id",
									new ObjectId(
											idVal.toString()));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						Object item = null;
						if ((item = method
								.invoke(destClass)) != null) {
							if (isBasicType(item)) {
								obj.put(name,
										item);
							} else if (List.class
									.isInstance(item)) {
								obj.put(name,
										getListObject((List) item));
							} else if (Map.class
									.isInstance(item)) {
								obj.put(name,
										getMapObject((Map) item));
							} else if (BasicMongoModel.class
									.isInstance(item)) {
								obj.put(name,
										((BasicMongoModel) item)
												.toMongodbObject());
							} else {
								Logger.getLogger(
										destClass.getClass())
										.warn("unknown type :"
												+ name);
							}
						} else {
							// Logger.getLogger(destClass.getClass()).warn("item is null:"
							// + name);
						}
					} catch (Exception e) {
						Logger.getLogger(
								destClass.getClass())
								.error(e.getLocalizedMessage());
						for (Object es : e
								.getStackTrace()) {
							Logger.getLogger(
									destClass.getClass())
									.error(es.toString());
						}
					}
				}
			}
		}
		return obj;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////

	// TODO unfinished
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map setMapObject(final Map<?, ?> items) {
		Map map = new HashMap();
		Iterator<?> it = items.entrySet().iterator();
		Entry item = null;
		if (it.hasNext() && ((item = (Entry) it.next()) != null)) {
			if (!isBasicType(item.getKey())) {
				Logger.getLogger(MongodbObjectSerializer.class)
						.error("ERROR KEY TYPE !!!!"
								+ item.toString());
				return map;
			}
			if (isBasicType(item.getValue())) {
				return items;
			}
			if (List.class.isInstance(item)) {
				do {
					map.put(item.getKey(),
							getListObject((List) (item
									.getValue())));
				} while (it.hasNext()
						&& ((item = (Entry) it.next()) != null));
			} else if (Map.class.isInstance(item)) {
				do {
					map.put(item.getKey(),
							getMapObject((Map) (item
									.getValue())));
				} while (it.hasNext()
						&& ((item = (Entry) it.next()) != null));
			} else if (BasicMongoModel.class.isInstance(item)) {
				do {
					map.put(item.getKey(),
							((BasicMongoModel) item)
									.toMongodbObject());
				} while (it.hasNext()
						&& ((item = (Entry) it.next()) != null));
			} else {
				Logger.getLogger(MongodbObjectSerializer.class)
						.warn("[getMapObject] unknown type ...");
			}
		}
		return map;
	}

	// TODO unfinished
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<DBObject> setListObject(List<?> items) {
		List list = new ArrayList();
		for (Object item : items) {
			if (isBasicType(item)) {
				list.add(item);
			} else if (List.class.isInstance(item)) {
				list.add(getListObject((List) item));
			} else if (Map.class.isInstance(item)) {
				list.add(getMapObject((Map) item));
			} else if (BasicMongoModel.class.isInstance(item)) {
				list.add(((BasicMongoModel) item)
						.toMongodbObject());
			} else {
				Logger.getLogger(MongodbObjectSerializer.class)
						.warn("[getListObject] unknown type ...");
			}
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static void fromMongodbObject(Object destClass, DBObject obj) {
		Method[] methods = destClass.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				String name = Strings.setterNameTransfer(method
						.getName());
				// Logger.getLogger(getClass()).info(name);
				try {
					// Object ite
					Type[] ts = method
							.getGenericParameterTypes();
					if (ts.length > 0) {

						System.out.println(ts[0]
								.toString());
						try {
							String cName = ts[0]
									.toString();
							if (cName.startsWith("class")) {
								cName = cName.substring(6);
							} else if (cName.contains("<")) {
								cName = cName.substring(
										0,
										cName.indexOf("<"));
							}
							Class eClass = java.lang.Class
									.forName(cName);
							if (isBasicType(eClass)) {
								method.invoke(destClass,
										obj.get(name));
								// System.out.println(name
								// + "=>" +
								// "basic type");
								// obj.put(name,
								// eClass);
							} else if (List.class
									.isAssignableFrom(eClass)) {
								System.out.println(name
										+ "=>"
										+ "list type");
							} else if (Map.class
									.isAssignableFrom(eClass)) {
								// System.out.println(name
								// + "=>" +
								// "map type");
								// obj.put(name,
								// getMapObject((Map)
								// eClass));
							} else if (BasicMongoModel.class
									.isAssignableFrom(eClass)) {
								// BasicMongoModel.class.isInstance(eClass.newInstance())
								System.err.println(name
										+ "=>"
										+ "instance of Basic Mongo Model type");
								// obj.put(name,
								// ((BasicMongoModel)
								// eClass).toMongodbObject());
							} else {
								System.err.println(name
										+ "=>"
										+ "unknown type");
								Logger.getLogger(
										destClass.getClass())
										.warn("unknown type :"
												+ name);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					// AnnotatedType[]
					// annotatedParameterTypes =
					// method.getAnnotatedParameterTypes();
					// for (AnnotatedType at :
					// annotatedParameterTypes) {
					// System.out.println(name + "~" +
					// at.toString() +
					// (isBasicType(at.getClass())?"yes":"no")
					// + " basic");
					// System.out.println(name + "~" +
					// at.toString() +
					// (List.class.isInstance(at.getClass())?"yes":"no")
					// +
					// " list");
					// System.out.println(name + "~" +
					// at.toString() +
					// (Map.class.isInstance(at.getClass())?"yes":"no")
					// +
					// " map");
					// System.out.println(name + "~" +
					// at.getType().getTypeName() + "#" +
					// (isBasicType(at.getType().getClass())
					// ? "yes" : "no") +
					// " basic");
					// System.out.println(name + "~" +
					// at.getType().getTypeName() + "#" +
					// (List.class.isInstance(at.getType().getClass())
					// ? "yes" :
					// "no") + " list");
					// System.out.println(name + "~" +
					// at.getType().getTypeName() + "#" +
					// (Map.class.isInstance(at.getType().getClass())
					// ? "yes" :
					// "no") + " map");
					// }
					/*
					 * if((item = method.invoke(destClass))
					 * != null){ if(isBasicType(item)){
					 * obj.put(name, item); }else
					 * if(List.class.isInstance(item)){
					 * obj.put(name,
					 * getListObject((List)item)); }else
					 * if(Map.class.isInstance(item)){
					 * obj.put
					 * (name,getMapObject((Map)item)); }else
					 * if
					 * (BasicMongoModel.class.isInstance(item
					 * )){ obj.put(name,
					 * ((BasicMongoModel)item
					 * ).toMongdbObject()); }else { Logger
					 * .getLogger
					 * (destClass.getClass()).warn(
					 * "unknown type :" + name); } }else{
					 * Logger.getLogger(destClass.getClass
					 * ()).warn("item is null:" + name); }
					 */
				} catch (Exception e) {
					Logger.getLogger(destClass.getClass())
							.error(e.getLocalizedMessage());
					for (Object es : e.getStackTrace()) {
						Logger.getLogger(
								destClass.getClass())
								.error(es.toString());
					}
				}
			}
		}
	}
}
