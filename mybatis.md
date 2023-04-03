# Mybatis

## Mybatis使用第三方数据源

- 可以通过实现接口 org.apache.ibatis.datasource.DataSourceFactory 来使用第三方数据源实现
```
	public interface DataSourceFactory {
	  void setProperties(Properties props);
	  DataSource getDataSource();
	}
```
- org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory 可被用作父类来构建新的数据源适配器
```
	import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
	import com.mchange.v2.c3p0.ComboPooledDataSource;

	public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {

	  public C3P0DataSourceFactory() {
		this.dataSource = new ComboPooledDataSource();
	  }
	}
```
- Mybatis多数据库厂商标识
```
	<databaseIdProvider type="DB_VENDOR" />
	<databaseIdProvider type="DB_VENDOR">
	  <property name="SQL Server" value="sqlserver"/>
	  <property name="DB2" value="db2"/>
	  <property name="Oracle" value="oracle" />
	</databaseIdProvider>

```
- 数据库支持自动生成主键的insert语句插入，可以设置useGeneratedKeys="true",然后再把keyProperty设置
为目标属性。
```
	<insert id="insertAuthor" useGeneratedKeys="true"
		keyProperty="id">
	  insert into Author (username,password,email,bio)
	  values (#{username},#{password},#{email},#{bio})
	</insert>
```
- selectKey标签的作用：
	- 获取自增主键: 首先在数据库插入user对象，然后执行 select LAST_INSERT_ID()获取数据库里自动生成的主键，最后赋值给user对象的id属性。
	- 自定义主键的生成方式：
	```
		<insert id="addUser" parameterType="cn.mybatis.entity.User">
		<selectKey resultType="string" order="BEFORE" keyProperty="id">
			select uuid()  
		</selectKey>
			insert into t_user(id,name,sex,phone) values (#{id},#{name},#{sex},#{phone})
		</insert>
	```
	- SelectKey注解用在已经被 @Insert 或 @InsertProvider 或 @Update 或 @UpdateProvider 注解了的方法上。若在未被上述四个注解的方法上作 @SelectKey 注解则视为无效。