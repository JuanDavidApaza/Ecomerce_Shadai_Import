package edu.pe.idat.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/css/*","/imagenes/*","/js/*","/plugin/*","/images/*").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/catalogo").permitAll()
				.antMatchers("/buscar").permitAll()
				.antMatchers("/contactenos").hasAnyRole("USER")
				.antMatchers("/carrito").hasAnyRole("USER")
				.antMatchers("/productodetalle").hasAnyRole("USER")
				
				.antMatchers("/listarcliente").hasAnyRole("VENDEDOR")		
				.antMatchers("/venta/listarventas").hasAnyRole("VENDEDOR")
				.antMatchers("/compra/listarcompras").hasAnyRole("VENDEDOR")
				
				.antMatchers("/listaproveedor").hasAnyRole("VENDEDOR")
				.antMatchers("/listarproducto").hasAnyRole("VENDEDOR")
				.antMatchers("/formularioproducto").hasAnyRole("VENDEDOR")
				.antMatchers("/formproveedor").hasAnyRole("VENDEDOR")
				.antMatchers("/formulariocliente").hasAnyRole("VENDEDOR")
				.antMatchers("/venta/formularioventas/**").hasAnyRole("VENDEDOR")
				.antMatchers("/compra/formulariocompras/**").hasAnyRole("VENDEDOR")
				.antMatchers("/eliminarProducto/**").hasAnyRole("ADMIN")
				.antMatchers("/eliminarCliente/**").hasAnyRole("ADMIN")
				.antMatchers("/eliminarventa/**").hasAnyRole("ADMIN")
				.antMatchers("/eliminarcompra/**").hasAnyRole("ADMIN")
				
				.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				
				.anyRequest().authenticated()
				.and()
			.formLogin()
			.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
				
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = passwordEncoder;
		UserBuilder users =User.builder().passwordEncoder(encoder::encode);
		
		auth.inMemoryAuthentication()
		.withUser(users.username("admin").password("123456").roles("ADMIN","VENDEDOR"))
		.withUser(users.username("vendedor").password("123456").roles("VENDEDOR"))
		.withUser(users.username("usuario").password("123456").roles("USER"));
		
	}
	
	
}
	
	

