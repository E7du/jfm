package vip.e7du.jfm.testing;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jfinal.kit.JsonKit;

import e7du.monkey.model.User;

public class TestUserModel {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	
	@Test
	public void ttt() {
		String[] a = new String[]{"a","b"};
		
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		
		System.out.println(JsonKit.toJson(a));
		System.out.println(JsonKit.toJson(list));
	}
	
	@Test
	public void test() {
		User user = new User();
		//业务设置用户的 id 为123
		user.setId(new BigInteger("123"));
		System.out.println("业务设置用户的 id 为123=>"+user.efficientAttrs());
		System.out.println("业务设置用户的 id 为123=>"+JsonKit.toJson(user.efficientAttrs()));
		//DC查询 id=11的用户
		user.setId(new BigInteger("11"));
		System.out.println("DC查询 id=11的用户=>"+user.dcExport());
		System.out.println("DC查询 id=11的用户=>"+JsonKit.toJson(user.dcExport()));
		//DC查询 id 在12到15范围的用户
		//user.setIdScope(new BigInteger("12"), new BigInteger("15"));
		System.out.println("DC查询 id 在12到15范围的用户=>"+user.dcExport());
		System.out.println("DC查询 id 在12到15范围的用户=>"+JsonKit.toJson(user.dcExport()));
		//业务设置用户的name = zcq 
		user.setName("zcq");
		System.out.println("业务设置用户的name = zcq=>"+user.efficientAttrs());
		System.out.println("业务设置用户的name = zcq=>"+JsonKit.toJson(user.efficientAttrs()));
		//DC查询 name = zcq 的用户
		user.setName("zcq");
		System.out.println("DC查询 name = zcq 的用户=>"+user.dcExport());
		System.out.println("DC查询 name = zcq 的用户=>"+JsonKit.toJson(user.dcExport()));
		//DC模糊查询name为zcq 的用户 
		user.setName("zcq");
		System.out.println("DC模糊查询name为zcq 的用户=>"+user.dcExport());
		System.out.println("DC模糊查询name为zcq 的用户=>"+JsonKit.toJson(user.dcExport()));
		user = new User();
		user.setId(new BigInteger("11"));
		user.setName("zcq");
		user.setOutput("id", "cccccid");
		System.out.println("DC不相等查询name为zcq 的用户=>"+user.dcExport());
		System.out.println("DC不相等查询name为zcq 的用户=>"+JsonKit.toJson(user.dcExport()));
		System.out.println("DC不相等查询name为zcq 的用户EffectiveAttr=>"+JsonKit.toJson(user.efficientAttrs()));
	}

}
