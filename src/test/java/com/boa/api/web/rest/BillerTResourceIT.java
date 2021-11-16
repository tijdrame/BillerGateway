package com.boa.api.web.rest;

import com.boa.api.BillerGatewayApp;
import com.boa.api.domain.BillerT;
import com.boa.api.repository.BillerTRepository;
import com.boa.api.service.BillerTService;
import com.boa.api.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.boa.api.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BillerTResource} REST controller.
 */
@SpringBootTest(classes = BillerGatewayApp.class)
public class BillerTResourceIT {

    private static final String DEFAULT_BILLER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BILLER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_BY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_BY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_LOCK_NUMBER = 1L;
    private static final Long UPDATED_LOCK_NUMBER = 2L;

    private static final String DEFAULT_BILLER_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_BILLER_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_DEVISE = "BBBBBBBBBB";

    @Autowired
    private BillerTRepository billerTRepository;

    @Autowired
    private BillerTService billerTService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBillerTMockMvc;

    private BillerT billerT;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillerTResource billerTResource = new BillerTResource(billerTService);
        this.restBillerTMockMvc = MockMvcBuilders.standaloneSetup(billerTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillerT createEntity(EntityManager em) {
        BillerT billerT = new BillerT()
            .billerCode(DEFAULT_BILLER_CODE)
            .name(DEFAULT_NAME)
            .pays(DEFAULT_PAYS)
            .channel(DEFAULT_CHANNEL)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .status(DEFAULT_STATUS)
            .address(DEFAULT_ADDRESS)
            //.logo(DEFAULT_LOGO)
            .website(DEFAULT_WEBSITE)
            //.createdBy(DEFAULT_CREATED_BY)
            //.createdDate(DEFAULT_CREATED_DATE)
            //.lastUpdatedBy(DEFAULT_LAST_UPDATED_BY)
            //.lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            //.lockNumber(DEFAULT_LOCK_NUMBER)
            .billerCategory(DEFAULT_BILLER_CATEGORY)
            .devise(DEFAULT_DEVISE);
        return billerT;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillerT createUpdatedEntity(EntityManager em) {
        BillerT billerT = new BillerT()
            .billerCode(UPDATED_BILLER_CODE)
            .name(UPDATED_NAME)
            .pays(UPDATED_PAYS)
            .channel(UPDATED_CHANNEL)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .status(UPDATED_STATUS)
            .address(UPDATED_ADDRESS)
            //.logo(UPDATED_LOGO)
            .website(UPDATED_WEBSITE)
            /*.createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .lockNumber(UPDATED_LOCK_NUMBER)*/
            .billerCategory(UPDATED_BILLER_CATEGORY)
            .devise(UPDATED_DEVISE);
        return billerT;
    }

    @BeforeEach
    public void initTest() {
        billerT = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillerT() throws Exception {
        int databaseSizeBeforeCreate = billerTRepository.findAll().size();

        // Create the BillerT
        restBillerTMockMvc.perform(post("/api/biller-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billerT)))
            .andExpect(status().isCreated());

        // Validate the BillerT in the database
        List<BillerT> billerTList = billerTRepository.findAll();
        assertThat(billerTList).hasSize(databaseSizeBeforeCreate + 1);
        BillerT testBillerT = billerTList.get(billerTList.size() - 1);
        assertThat(testBillerT.getBillerCode()).isEqualTo(DEFAULT_BILLER_CODE);
        assertThat(testBillerT.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBillerT.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testBillerT.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testBillerT.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBillerT.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testBillerT.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBillerT.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBillerT.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testBillerT.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        /*assertThat(testBillerT.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBillerT.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBillerT.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testBillerT.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testBillerT.getLockNumber()).isEqualTo(DEFAULT_LOCK_NUMBER);*/
        assertThat(testBillerT.getBillerCategory()).isEqualTo(DEFAULT_BILLER_CATEGORY);
        assertThat(testBillerT.getDevise()).isEqualTo(DEFAULT_DEVISE);
    }

    @Test
    @Transactional
    public void createBillerTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billerTRepository.findAll().size();

        // Create the BillerT with an existing ID
        billerT.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillerTMockMvc.perform(post("/api/biller-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billerT)))
            .andExpect(status().isBadRequest());

        // Validate the BillerT in the database
        List<BillerT> billerTList = billerTRepository.findAll();
        assertThat(billerTList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBillerTS() throws Exception {
        // Initialize the database
        billerTRepository.saveAndFlush(billerT);

        // Get all the billerTList
        restBillerTMockMvc.perform(get("/api/biller-ts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billerT.getId().intValue())))
            .andExpect(jsonPath("$.[*].billerCode").value(hasItem(DEFAULT_BILLER_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lockNumber").value(hasItem(DEFAULT_LOCK_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].billerCategory").value(hasItem(DEFAULT_BILLER_CATEGORY)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE)));
    }
    
    @Test
    @Transactional
    public void getBillerT() throws Exception {
        // Initialize the database
        billerTRepository.saveAndFlush(billerT);

        // Get the billerT
        restBillerTMockMvc.perform(get("/api/biller-ts/{id}", billerT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billerT.getId().intValue()))
            .andExpect(jsonPath("$.billerCode").value(DEFAULT_BILLER_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.lockNumber").value(DEFAULT_LOCK_NUMBER.intValue()))
            .andExpect(jsonPath("$.billerCategory").value(DEFAULT_BILLER_CATEGORY))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE));
    }

    @Test
    @Transactional
    public void getNonExistingBillerT() throws Exception {
        // Get the billerT
        restBillerTMockMvc.perform(get("/api/biller-ts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillerT() throws Exception {
        // Initialize the database
        billerTService.save(billerT);

        int databaseSizeBeforeUpdate = billerTRepository.findAll().size();

        // Update the billerT
        BillerT updatedBillerT = billerTRepository.findById(billerT.getId()).get();
        // Disconnect from session so that the updates on updatedBillerT are not directly saved in db
        em.detach(updatedBillerT);
        updatedBillerT
            .billerCode(UPDATED_BILLER_CODE)
            .name(UPDATED_NAME)
            .pays(UPDATED_PAYS)
            .channel(UPDATED_CHANNEL)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .status(UPDATED_STATUS)
            .address(UPDATED_ADDRESS)
            //.logo(UPDATED_LOGO)
            .website(UPDATED_WEBSITE)
            /*.createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .lockNumber(UPDATED_LOCK_NUMBER)*/
            .billerCategory(UPDATED_BILLER_CATEGORY)
            .devise(UPDATED_DEVISE);

        restBillerTMockMvc.perform(put("/api/biller-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillerT)))
            .andExpect(status().isOk());

        // Validate the BillerT in the database
        List<BillerT> billerTList = billerTRepository.findAll();
        assertThat(billerTList).hasSize(databaseSizeBeforeUpdate);
        BillerT testBillerT = billerTList.get(billerTList.size() - 1);
        assertThat(testBillerT.getBillerCode()).isEqualTo(UPDATED_BILLER_CODE);
        assertThat(testBillerT.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBillerT.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testBillerT.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testBillerT.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBillerT.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testBillerT.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBillerT.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBillerT.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testBillerT.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        /*assertThat(testBillerT.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBillerT.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBillerT.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testBillerT.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testBillerT.getLockNumber()).isEqualTo(UPDATED_LOCK_NUMBER);*/
        assertThat(testBillerT.getBillerCategory()).isEqualTo(UPDATED_BILLER_CATEGORY);
        assertThat(testBillerT.getDevise()).isEqualTo(UPDATED_DEVISE);
    }

    @Test
    @Transactional
    public void updateNonExistingBillerT() throws Exception {
        int databaseSizeBeforeUpdate = billerTRepository.findAll().size();

        // Create the BillerT

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillerTMockMvc.perform(put("/api/biller-ts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billerT)))
            .andExpect(status().isBadRequest());

        // Validate the BillerT in the database
        List<BillerT> billerTList = billerTRepository.findAll();
        assertThat(billerTList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillerT() throws Exception {
        // Initialize the database
        billerTService.save(billerT);

        int databaseSizeBeforeDelete = billerTRepository.findAll().size();

        // Delete the billerT
        restBillerTMockMvc.perform(delete("/api/biller-ts/{id}", billerT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillerT> billerTList = billerTRepository.findAll();
        assertThat(billerTList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
