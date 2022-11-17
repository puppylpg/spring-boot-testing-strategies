package io.tpd.superheroes.controller.realrepo;

import io.tpd.superheroes.controller.SuperHeroController;
import io.tpd.superheroes.domain.SuperHero;
import io.tpd.superheroes.repository.SuperHeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * This class demonstrates how to test a controller using MockMVC loading a Test Context
 *
 * ~~既然有WebApplicationContext了，直接autowired real repo就行了~~
 *
 * 大错特错！Spring will load only a partial context (the controller and its surrounding configuration like filters and advices).
 * Since Spring is smart enough to know that our Filter and the Controller Advice should also be injected, there is no need for explicit configuration in the setup() method.
 * spring值初始化了和controller相关的bean……比如filter、advice，没有其他bean……
 *
 * @author moises.macero
 */
@AutoConfigureJsonTesters
@WebMvcTest(SuperHeroController.class)
public class SuperHeroControllerCanNotBeRealRepoMockMvcWithContextTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    /**
     * No qualifying bean of type 'io.tpd.superheroes.repository.SuperHeroRepository' available
     */
//    @Autowired
    @MockBean
    private SuperHeroRepository superHeroRepository;

    /**
     * This object will be initialized thanks to {@link AutoConfigureJsonTesters}
     */
    @Autowired
    private JacksonTester<SuperHero> jsonSuperHero;

    /**
     * 虽然这个WebApplicationContext依然初始化了很多bean，但都是和mvc相关的，和repo无关
     * 虽然里面有个叫io.tpd.superheroes.repository.SuperHeroRepository#0的bean，
     * 但它是我们通过{@link MockBean} mock出来的假的。如果不mock这个repo，superHeroController这个bean创建不起来
     *
     * total bean=102
     * applicationTaskExecutor
     * basicErrorController
     * basicJsonTesterFactoryBean
     * beanNameHandlerMapping
     * beanNameViewResolver
     * characterEncodingFilter
     * defaultServletHandlerMapping
     * defaultViewResolver
     * dispatcherServlet
     * dispatcherServletPath
     * error
     * errorAttributes
     * errorPageCustomizer
     * flashMapManager
     * formContentFilter
     * handlerExceptionResolver
     * handlerFunctionAdapter
     * httpRequestHandlerAdapter
     * io.tpd.superheroes.repository.SuperHeroRepository#0
     * jacksonObjectMapper
     * jacksonObjectMapperBuilder
     * jacksonTesterFactoryBean
     * jsonComponentModule
     * jsonMarshalTestersBeanPostProcessor
     * localeCharsetMappingsCustomizer
     * localeResolver
     * mappingJackson2HttpMessageConverter
     * messageConverters
     * mockMvc
     * mockMvcBuilder
     * mvcContentNegotiationManager
     * mvcConversionService
     * mvcHandlerMappingIntrospector
     * mvcPathMatcher
     * mvcPatternParser
     * mvcResourceUrlProvider
     * mvcTestsApplication
     * mvcUriComponentsContributor
     * mvcUrlPathHelper
     * mvcValidator
     * mvcViewResolver
     * org.springframework.boot.autoconfigure.AutoConfigurationPackages
     * org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
     * org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration$StringHttpMessageConverterConfiguration
     * org.springframework.boot.autoconfigure.http.JacksonHttpMessageConvertersConfiguration
     * org.springframework.boot.autoconfigure.http.JacksonHttpMessageConvertersConfiguration$MappingJackson2HttpMessageConverterConfiguration
     * org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory
     * org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
     * org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$Jackson2ObjectMapperBuilderCustomizerConfiguration
     * org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperBuilderConfiguration
     * org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperConfiguration
     * org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$ParameterNamesModuleConfiguration
     * org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
     * org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration
     * org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
     * org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration$EnableWebMvcConfiguration
     * org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration$WebMvcAutoConfigurationAdapter
     * org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
     * org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration$DefaultErrorViewResolverConfiguration
     * org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration$WhitelabelErrorViewConfiguration
     * org.springframework.boot.context.internalConfigurationPropertiesBinder
     * org.springframework.boot.context.internalConfigurationPropertiesBinderFactory
     * org.springframework.boot.context.properties.BoundConfigurationProperties
     * org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor
     * org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar.methodValidationExcludeFilter
     * org.springframework.boot.test.autoconfigure.json.JsonTestersAutoConfiguration
     * org.springframework.boot.test.autoconfigure.json.JsonTestersAutoConfiguration$JacksonJsonTestersConfiguration
     * org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration
     * org.springframework.boot.test.context.ImportsContextCustomizer$ImportsCleanupPostProcessor
     * org.springframework.boot.test.mock.mockito.MockitoPostProcessor
     * org.springframework.boot.test.mock.mockito.MockitoPostProcessor$SpyPostProcessor
     * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
     * org.springframework.context.annotation.internalCommonAnnotationProcessor
     * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
     * org.springframework.context.event.internalEventListenerFactory
     * org.springframework.context.event.internalEventListenerProcessor
     * parameterNamesModule
     * preserveErrorControllerTargetClassPostProcessor
     * requestContextFilter
     * requestMappingHandlerAdapter
     * requestMappingHandlerMapping
     * resourceHandlerMapping
     * routerFunctionMapping
     * server-org.springframework.boot.autoconfigure.web.ServerProperties
     * simpleControllerHandlerAdapter
     * spring.jackson-org.springframework.boot.autoconfigure.jackson.JacksonProperties
     * spring.mvc-org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties
     * spring.resources-org.springframework.boot.autoconfigure.web.ResourceProperties
     * spring.task.execution-org.springframework.boot.autoconfigure.task.TaskExecutionProperties
     * spring.web-org.springframework.boot.autoconfigure.web.WebProperties
     * springBootMockMvcBuilderCustomizer
     * standardJacksonObjectMapperBuilderCustomizer
     * stringHttpMessageConverter
     * superHeroController
     * superHeroExceptionHandler
     * superHeroFilter
     * taskExecutorBuilder
     * themeResolver
     * viewControllerHandlerMapping
     * viewNameTranslator
     * viewResolver
     * welcomePageHandlerMapping
     */
    @BeforeEach
    public void init() {
        String[] beans = webApplicationContext.getBeanDefinitionNames();
        System.out.println("total bean=" + beans.length);
        Arrays.stream(beans)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        given(superHeroRepository.getSuperHero(2))
                .willReturn(new SuperHero("Rob", "Mannon", "RobotMan"));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/superheroes/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSuperHero.write(new SuperHero("Rob", "Mannon", "RobotMan")).getJson()
        );
    }
}
