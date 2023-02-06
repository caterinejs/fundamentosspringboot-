package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanImplementCate;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependencyCate;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.component.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import pojo.UserPojo;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithDependencyCate myBeanWithDependencyCate;

	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;

	private UserService userService;


	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean,
								  MyBeanWithDependency myBeanWithDependency, MyBeanWithDependencyCate myBeanWithDependencyCate,
								  MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository,
								  UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithDependencyCate = myBeanWithDependencyCate;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run (String... args) {
		//ejemplosAnteriores();
		saveWithErrorTransactional();
		saveUserInDataBase();
		getInformationJpqlFromUser();

	}

	private void saveWithErrorTransactional(){
		User test1 = new User("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		User test3 = new User("TestTransactional3", "TestTransactional3@domain.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());
		User test5 = new User("TestTransactional5", "TestTransactional5@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4, test5);
		try {
			userService.saveTransactional(users);
		}catch (Exception e){
			LOGGER.error("esta es una exception dentro del metodo transacional " + e);
		}
		userService.getAllUsers().stream()
				.forEach(user ->
						LOGGER.info("este es el usuario dentro del metodo trnsaccional " + user));
	}
	private void getInformationJpqlFromUser(){
		LOGGER.info("usuario con el metodo findByUserEmail" +
				userRepository.findByUserEmail("jhon@gmail.com")
				.orElseThrow(()-> new RuntimeException("no se encontro el usuario")));

		userRepository.fundAndSort("do", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("usuario con metodo sort " +user));

		userRepository.findByName("cate")
				.stream()
				.forEach(user -> LOGGER.info("usuario con query method " + user));

		userRepository.findByNameLike("%a%")
				.stream()
				.forEach(user -> LOGGER.info("usuario findByNameLike " + user));

		userRepository.findByNameOrEmail(null, "salome@gmail.com")
				.stream()
				.forEach(user -> LOGGER.info("usuario findByNameOrEmail " + user));

		userRepository.findByBirthDateBetween(LocalDate.of(2021,03,20),
						LocalDate.of(2023,01,25))
				.stream()
				.forEach(user -> LOGGER.info("usuario con intervalo de fechas:  " + user));

		/*userRepository.findByNameLikeOrderByIdDesc("%a%")
				.stream()
				.forEach(user -> LOGGER.info("usuario encontrado con like y ordenado " + user));*/


		userRepository.findByNameContainingOrderByIdDesc("a")
				.stream()
				.forEach(user -> LOGGER.info("usuario encontrado con like y ordenado " + user));


		LOGGER.info("el usuario a partir del named parameter es: " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2019,2,11),
						"milo@gmail.com")
				.orElseThrow(()->
						new RuntimeException("no se encontro el usuario a partir del named parameter")));


	}

	private void saveUserInDataBase(){
		User user1 = new User("jhon", "jhon@gmail.com", LocalDate.of(2021,03,20));
		User user2 = new User("cate", "cate@gmail.com", LocalDate.of(2023,01,25));
		User user3 = new User("pablo", "pablo@gmail.com", LocalDate.of(1994,04,01));
		User user4 = new User("sofia", "sofia@gmail.com", LocalDate.of(2021,12,27));
		User user5 = new User("ana", "ana@gmail.com", LocalDate.of(2021,12,27));
		User user6 = new User("salome", "salome@gmail.com", LocalDate.of(2014,1,07));
		User user7 = new User("milo", "milo@gmail.com", LocalDate.of(2019,2,11));
		User user8 = new User("annie", "annie@gmail.com", LocalDate.of(2020,07,12));
		User user9 = new User("juan", "juan@gmail.com", LocalDate.of(1991,07,17));
		User user10 = new User("angela", "angela@gmail.com", LocalDate.of(1996,06,19));
		User user11 = new User("maria", "maria@gmail.com", LocalDate.of(2020,01,13));
		User user12 = new User("dora", "dora@gmail.com", LocalDate.of(1995,04,18));
		List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12);
		list.stream().forEach(userRepository::save);
	}
	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		myBeanWithDependencyCate.saludoCate();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "_" + userPojo.getPassword());
		System.out.println(userPojo.getAge());
		try{
			//error
			int value = 10/0;
			LOGGER.debug("mi valor: " + value);
		} catch (Exception e) {
			LOGGER.error("esto es un eror al dividir por cero" + e.getMessage());
		}
	}

	}

	//http://localhost:4200/app/api/users/

