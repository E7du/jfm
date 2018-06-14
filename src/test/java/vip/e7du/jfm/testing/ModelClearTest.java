package vip.e7du.jfm.testing;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jfinal.plugin.activerecord.DefaultValueKits;

import vip.e7du.subsystem.api.model.FireCity;

public class ModelClearTest {

	private static Map<Object, Object> matches = new HashMap<Object, Object>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	public void bool(boolean boo) {
		System.out.println("boo ==="+boo);
	}

	@Test
	public void test() {
		
		FireCity city = new FireCity();
		city.setAreaCode("areaCode");
		city.setIconImage("iconImage");
		city.setDescription("description");
		
		FireCity ccity = city.clear(FireCity.ICON_IMAGE);
		
		System.out.println(city.efficientAttrs());
		System.out.println("old=>\n"+city+"\ncleared=>\n"+ccity);
		
		FireCity cccity = city.clear(FireCity.ICON_IMAGE, FireCity.AREA_CODE);
		System.out.println("old=>\n"+city+"\ncleared=>\n"+cccity);
		
		System.out.println(new Date());
		
		
		Object a = new Boolean(true);

		for (int i = 0; i < 100; i++) {
			Object val = DefaultValueKits.getDefaultValue(a);
			System.out.println("val==="+val.hashCode());
			matches.put(val.hashCode(), val.toString()+i);
			System.out.println(matches.size());
		}
	}

}
