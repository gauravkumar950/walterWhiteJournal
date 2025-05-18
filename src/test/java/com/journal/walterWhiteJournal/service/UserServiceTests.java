package com.journal.walterWhiteJournal.service;

import com.journal.walterWhiteJournal.Repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testFindByUsername(){
        assertEquals(4,2+2);
//        assertNotNull(userRepository.findByUsername("gaurav620"));
    }
    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,2,4",
            "3,2,5"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
    @BeforeEach         //Before execution of each test, this annotated test will run
    public void setUP(){

    }
    @BeforeAll  //This will execute before execution of all the test cases
    public static void setUp(){

    }
}
