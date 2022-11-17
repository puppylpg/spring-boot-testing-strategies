package io.tpd.superheroes.controller.realrepo;

import io.tpd.superheroes.domain.SuperHero;
import io.tpd.superheroes.repository.SuperHeroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * This class demonstrates how to test a controller using Spring Boot Test
 * (what makes it much closer to an Integration Test)
 *
 * @author moises.macero
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SuperHeroControllerRealRepoSpringBootTest {

    /**
     * you can still mock beans and replace them in the context
     */
    @Autowired
    private SuperHeroRepository superHeroRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final SuperHero HERO_2_ND = new SuperHero("Bruce", "Wayne", "Batman");

    @Test
    public void canRetrieveByIdWhenExists() {
        // no need to given since we are using real repo
//        // given
//        given(superHeroRepository.getSuperHero(2))
//                .willReturn(new SuperHero("Rob", "Mannon", "RobotMan"));

        // when
        ResponseEntity<SuperHero> superHeroResponse = restTemplate.getForEntity("/superheroes/2", SuperHero.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(superHeroResponse.getBody().equals(new SuperHero("Rob", "Mannon", "RobotMan")));
        assertThat(superHeroResponse.getBody()).isEqualTo(HERO_2_ND);
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() {
//        // given
//        given(superHeroRepository.getSuperHero(2))
//                .willThrow(new NonExistingHeroException());

        // when
//        ResponseEntity<SuperHero> superHeroResponse = restTemplate.getForEntity("/superheroes/2", SuperHero.class);
        ResponseEntity<SuperHero> superHeroResponse = restTemplate.getForEntity("/superheroes/12345", SuperHero.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(superHeroResponse.getBody()).isNull();
    }

    @Test
    public void canRetrieveByNameWhenExists() {
//        // given
//        given(superHeroRepository.getSuperHero("RobotMan"))
//                .willReturn(Optional.of(new SuperHero("Rob", "Mannon", "RobotMan")));

        // when
        ResponseEntity<SuperHero> superHeroResponse = restTemplate
//                .getForEntity("/superheroes/?name=RobotMan", SuperHero.class);
                .getForEntity("/superheroes/?name=Batman", SuperHero.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(superHeroResponse.getBody().equals(HERO_2_ND));
    }

    @Test
    public void canRetrieveByNameWhenDoesNotExist() {
//        // given
//        given(superHeroRepository.getSuperHero("RobotMan"))
//                .willReturn(Optional.empty());

        // when
        ResponseEntity<SuperHero> superHeroResponse = restTemplate
//                .getForEntity("/superheroes/?name=RobotMan", SuperHero.class);
                .getForEntity("/superheroes/?name=NotExistName", SuperHero.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(superHeroResponse.getBody()).isNull();
    }

    @Test
    public void canCreateANewSuperHero() {
        // when
        ResponseEntity<SuperHero> superHeroResponse = restTemplate.postForEntity("/superheroes/",
                new SuperHero("Rob", "Mannon", "RobotMan"), SuperHero.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void headerIsPresent() throws Exception {
        // when
        ResponseEntity<SuperHero> superHeroResponse = restTemplate.getForEntity("/superheroes/2", SuperHero.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(superHeroResponse.getHeaders().get("X-SUPERHERO-APP")).containsOnly("super-header");
    }

}
