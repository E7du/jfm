package vip.e7du.subsystem.api.model;

import java.util.*;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal-Ext2.
 */
public class GalaxyTableMappingKit {

	private final Map<Class<? extends Model<?>>, String> modelToTableMap = new HashMap<Class<? extends Model<?>>, String>();
	private final Map<String, Class<? extends Model<?>>> tableToModelMap = new HashMap<String, Class<? extends Model<?>>>();
	private static GalaxyTableMappingKit me = new GalaxyTableMappingKit();

	public static GalaxyTableMappingKit me() {
		return me;
	}

	public void putTable(Class<? extends Model<?>> modelClass, String tableName) {
		this.modelToTableMap.put(modelClass, tableName);
	}
	public void putTable(String tableName, Class<? extends Model<?>> modelClass) {
		this.tableToModelMap.put(tableName, modelClass);
	}

	public String getTableName(Class<? extends Model<?>> modelClass) {
		if (!this.modelToTableMap.containsKey(modelClass)) {
			return "";
		}
		return this.modelToTableMap.get(modelClass);
	}

	public Class<? extends Model<?>> getModelClass(String tableName) {
		if (!this.tableToModelMap.containsKey(tableName)) {
			return null;
		}
		return this.tableToModelMap.get(tableName);
	}

	private GalaxyTableMappingKit() {
		this.modelToTableMap.put(BaseCity.class, "base_city");
		this.tableToModelMap.put("base_city", BaseCity.class);
		this.modelToTableMap.put(FireCity.class, "fire_city");
		this.tableToModelMap.put("fire_city", FireCity.class);
		this.modelToTableMap.put(FireDistrict.class, "fire_district");
		this.tableToModelMap.put("fire_district", FireDistrict.class);
		this.modelToTableMap.put(FireProvince.class, "fire_province");
		this.tableToModelMap.put("fire_province", FireProvince.class);
	}
}

