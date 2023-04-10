# SpringSecurity

## SpringSecurity默认配置验证原理

- 身份验证过滤器将身份验证请求委托给身份验证管理器，并根据响应配置安全上下文
- 身份验证管理器使用身份验证提供程序处理身份验证
- 身份验证提供程序实现身份验证逻辑
- 用户详情服务UserDetailsService实现了用户管理职能，身份验证提供程序将在身份验证逻辑中使用它
- 密码编码器PasswordEncoder实现了密码管理，身份验证提供程序将在身份验证逻辑中使用它
- 安全上下文在身份验证过程结束后保留身份验证数据
- springboot:默认实现具有默认密码的user,这个密码是在加载Spring上下文时随机生成的，是一个UUID
- PasswordEncoder:主要用于将密码进行编码，验证密码是否与现有编码相互匹配

## UserDetailsService的几种实现方式
- 通过实现UserDetailsService接口创建自己的实现
- 通过使用Spring Security提供的预定义实现
- 通过使用Spring Security提供的一个名为inMemoryUserDetailsManager的实现

## 身份验证需要的类或者接口

- UserDetailsService,PasswordEncoder
- 需要创建的东西
	- 至少创建一个具有凭据(用户名和密码)的用户
	- 添加要由UserDetailsService实现管理的用户
	- 定义一个PasswordEncoder类型的bean，应用程序可以使用它验证为UserDetailsService存储和管理的用户所指定的密码
	- 在重写userDetailsService时，PasswordEncoder也要进行配置

## 重写端点授权配置

- 在默认情况下，应用程序会使用HTTP Basic身份验证作为授权方法，我们可以重写该配置。
- 首先需要扩展WebSecurityConfigurerAdapter类，然后重写configure(HttpSecurity http)方法
```
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .anyRequest().authenticated(); //所有请求都需要身份验证
    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.httpBasic();
	    http.authorizeRequests()
	            .anyRequest().permitAll(); //所有请求都需要身份验证
	}
	//configure(AuthenticationManagerBuilder auth)方法设置userDetailsService和passwordEncoder
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //声明UserDetailsService,以便将用户存储在内存中
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        //定义具有所有详情的用户
        UserDetails user = User.withUsername("mbw")
                .password("12345")
                .authorities("read")
                .build();
        //添加该用户以便让UserDetailsService对其进行管理
        userDetailsService.createUser(user);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
```

- 不能让两个类都扩展WebSecurityConfigurerAdapter,如果这样做，依赖注入将失败，可以通过使用@Order注解设置注入的优先级来解决依赖注入的问题。但是，从功能上讲，这是行不通的，因为配置会相互排除而不是合并。

## AuthenticationProvider

- 它实现了身份验证逻辑并且委托给UserDetailsService和PasswordEncoder以便进行用户和密码管理
- authenticate(Authentication authentication)方法表示所有用于身份验证的逻辑
```
	package com.example.config;

	import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
	import org.springframework.security.authentication.AuthenticationProvider;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.AuthenticationException;

	import java.util.Arrays;

	public class CustomAuthenticationProvider implements AuthenticationProvider {
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			//getName()方法被Authentication从Principal接口处继承
			String username = authentication.getName();
			String password = String.valueOf(authentication.getCredentials());
			//这个条件通常会调用UserDetailsService和PasswordEncoder用来调试用户名和密码
			if("mbw".equals(username) && "12345".equals(password)){
				return new UsernamePasswordAuthenticationToken(username,password, Arrays.asList());
			}else {
				throw new AuthenticationCredentialsNotFoundException("Error in authentication!");
			}
		}

		@Override
		public boolean supports(Class<?> authenticationType) {
			return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
		}
	}
```

## UserDetailsService和UserDetailsManager相关接口说明

- UserDetailsService只负责按用户名检索用户,此操作是框架完成身份验证所需的唯一操作。用于验证用户

```
	public interface UserDetailsService {
		UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	}
```
- UserDetailsManager则在其基础上添加了对用户添加、修改、删除的行为，这是大多数应用程序中必需的功能。用于管理用户
- Spring Security使用GrantedAuthority表示用户所具有的权限

## UserDetails

- 用户定义应该遵循UserDetails契约。UserDetails契约代表着Spring Security所理解的用户
```
public interface UserDetails extends Serializable {
	/**
	 * 将应用程序用户的权限返回成一个GrantedAuthority实例集合
	 */
	Collection<? extends GrantedAuthority> getAuthorities();

	/**
	 * 下面2个方法会返回用户凭据
	 */
	String getPassword();
	String getUsername();

	/**
	 * 出于不同的原因，这4个方法会启用或禁用账户
	 */
	boolean isAccountNonExpired();
	boolean isAccountNonLocked();
	boolean isCredentialsNonExpired();
	boolean isEnabled();
}

```
## UserDetailsService相关接口说明

- 方法通过指定的用户名获取用户的详细信息。用户名当然会被视作唯一的。此方法返回的用户是UserDetails契约的实现
```
	package org.springframework.security.core.userdetails;
	public interface UserDetailsService {
		UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	}
```
- 通过mybatis查询数据库，实现从数据库获取用户信息的操

```
	<?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
			"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.example.mapper.UserMapper">
		<resultMap id="queryUserMap" type="com.example.pojo.User" autoMapping="true">
			<id column="id" property="id"/>
			<collection property="authorities" ofType="com.example.pojo.Authority" autoMapping="true" columnPrefix="a_">
				<id column="id" property="id"/>
				<result column="authorityName" property="authorityName"/>
			</collection>
		</resultMap>
		<select id="queryUserByUsername" resultMap="queryUserMap">
			SELECT u.*,
				   a.id AS a_id,
				   a.authorityName AS a_authorityName
				   from users u
			LEFT JOIN user_authority ua
			ON u.id = ua.userId
			LEFT JOIN authorities a
			ON a.id = ua.authorityId
			WHERE u.username = #{username}
			AND u.enabled != 0
		</select>
	</mapper>


	package com.example.mapper;

	import com.baomidou.mybatisplus.core.mapper.BaseMapper;
	import com.example.pojo.User;
	import org.apache.ibatis.annotations.Mapper;

	import java.util.List;

	@Mapper
	public interface UserMapper extends BaseMapper<User> {
		List<User> queryUserByUsername(String username);
	}


	package com.example.service;

	import com.example.mapper.UserMapper;
	import com.example.pojo.User;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.core.userdetails.UsernameNotFoundException;
	import org.springframework.stereotype.Service;

	import java.util.List;

	@Service
	public class MybatisUserDetailsService implements UserDetailsService {

		@Autowired
		private UserMapper userMapper;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			List<User> users = userMapper.queryUserByUsername(username);
			return users.stream().findFirst().orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		}
	}
	
	package com.example.config;

	import com.example.service.MybatisUserDetailsService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
	import org.springframework.security.core.userdetails.User;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.crypto.password.NoOpPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.security.provisioning.InMemoryUserDetailsManager;

	@Configuration
	public class ProjectConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private MybatisUserDetailsService userDetailsService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic();
			http.authorizeRequests()
					.anyRequest().authenticated(); //所有请求都需要身份验证
		}


	//    @Bean
	//    public UserDetailsService userDetailsService(){
	//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
	//        UserDetails user = User.withUsername("mbw").password("123456").authorities("read").build();
	//        userDetailsService.createUser(user);
	//        return userDetailsService;
	//    }
	//
	//
	//    @Bean
	//    public PasswordEncoder passwordEncoder(){
	//        return NoOpPasswordEncoder.getInstance();
	//    }

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		   /* //声明UserDetailsService,以便将用户存储在内存中
			InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
			//定义具有所有详情的用户
			UserDetails user = User.withUsername("mbw")
					.password("12345")
					.authorities("read")
					.build();
			//添加该用户以便让UserDetailsService对其进行管理
			userDetailsService.createUser(user);
			auth.userDetailsService(userDetailsService)
					.passwordEncoder(NoOpPasswordEncoder.getInstance());*/
			auth.userDetailsService(userDetailsService)
					.passwordEncoder(NoOpPasswordEncoder.getInstance());
		}
	}


```

## PasswordEncoder接口说明
- PasswordEncoder相关实现
	- NoOpPasswordEncoder：不编码密码，而保持明文。我们仅将此实现用于示例。因为它不会对密码进行哈希化，所以永远不要在真实场景中使用它。
	- StandardPasswordEncoder：使用SHA-256对密码进行哈希化。这个实现现在已经不推荐了，不应该在新的实现中使用它。不建议使用它的原因是，它使用了一种目前看来不够强大的哈希算法，但我们可能仍然会在现有的应用程序中发现这种实现。
	- Pbkdf2PasswordEncoder：使用基于密码的密钥派生函数2（PBKDF2)
	- BCryptPasswordEncoder：使用bcrypt强哈希函数对密码进行编码
	- SCryptPasswordEncoder：使用scrypt强哈希函数对密码进行编码
```
	public interface PasswordEncoder {
		String encode(CharSequence rawPassword);
		boolean matches(CharSequence rawPassword, String encodedPassword);
		default boolean upgradeEncoding(String encodedPassword) {
			return false;
		}
	}	
```
- DelegatingPasswordEncoder是PasswordEncoder接口的一个实现，这个实现不是实现它自己的编码算法，而是委托给同一契约的另一个实现。其哈希值以一个前缀作为开头，该前缀表明了用于定义该哈希值的算法。
	- DelegatingPasswordEncoder会为前缀{noop}注册一个NoOpPasswordEncoder，
	- 为前缀{bcrypt}注册一个BCryptPasswordEncoder,并且为前缀{scrypt}注册一个SCryptPasswordEncoder,
	- 如果密码具有前缀{noop},则DelegatingPasswordEncoder会将该操作转发给NoOpPasswordEncoder实现

## AuthenticationProvider接口及说明

- 可以使用AuthenticationProvider接口来定义任何自定义的身份验证逻辑。
- Spring Security中的AuthenticationProvider负责身份验证逻辑。AuthenticationProvider接口的默认实现会将查找系统用户的职责委托给UserDetailsService。它还使用PasswordEncoder在身份验证过程中进行密码管理
```
package com.example.config;

import com.example.service.MybatisUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private MybatisUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //getName()方法被Authentication从Principal接口处继承
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        UserDetails u = userDetailsService.loadUserByUsername(username);
        //这个条件通常会调用UserDetailsService和PasswordEncoder用来调试用户名和密码
//        if("mbw".equals(username) && "12345".equals(password)){
//            return new UsernamePasswordAuthenticationToken(username,password, Arrays.asList());
//        }else {
//            throw new AuthenticationCredentialsNotFoundException("Error in authentication!");
//        }

        if (passwordEncoder.matches(password, u.getPassword())) {
            //如果密码匹配，则返回Authentication接口的实现以及必要的详细信息
            return new UsernamePasswordAuthenticationToken(username, password, u.getAuthorities());
        } else {	//密码不匹配，抛出异常
            throw new BadCredentialsException("Something went wrong!");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* //声明UserDetailsService,以便将用户存储在内存中
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        //定义具有所有详情的用户
        UserDetails user = User.withUsername("mbw")
                .password("12345")
                .authorities("read")
                .build();
        //添加该用户以便让UserDetailsService对其进行管理
        userDetailsService.createUser(user);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());*/
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
        auth.authenticationProvider(authenticationProvider);
    }

```

## SecurityContext

- Spring Security提供了3中策略来管理Spring Context，其中都用到了一个对象来扮演管理器的角色。该对象被命名为SecurityContextHolder
	- MODE_THREADLOCAL——允许每个线程在安全上下文中存储自己的详细信息。在每个请求一个线程的Web应用程序中，这是一种常见的方法，因为每个请求都有一个单独的线程。但若是该接口是需要异步的，那么此种策略便不再适用
	- MODE_INHERITABLETHREADLOCAL——类似于MODE_THREADLOCAL，但还会指示Spring Security在异步方法的情况下将安全上下文复制到下一个线程。这样，就可以说运行@Async方法的新线程继承了该安全上下文。
	- MODE_GLOBAL——使应用程序的所有线程看到相同的安全上下文实例
```
	public interface SecurityContext extends Serializable {
		Authentication getAuthentication();
		void setAuthentication(Authentication authentication);
	}
	

```