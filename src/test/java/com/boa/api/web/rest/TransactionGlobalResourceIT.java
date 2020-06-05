package com.boa.api.web.rest;

import com.boa.api.BillerGateway;
import com.boa.api.domain.TransactionGlobal;
import com.boa.api.repository.TransactionGlobalRepository;
import com.boa.api.service.TransactionGlobalService;
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
 * Integration tests for the {@link TransactionGlobalResource} REST controller.
 */
@SpringBootTest(classes = BillerGateway.class)
public class TransactionGlobalResourceIT {

    private static final String DEFAULT_REFERENCE_FACTURE = "1L";
    private static final String UPDATED_REFERENCE_FACTURE = "2L";

    private static final String DEFAULT_NUMERO_FACTURE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FACTURE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_PAIEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PAIEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ECHEANCE = "AAAAAAAAAA";
    private static final String UPDATED_ECHEANCE = "BBBBBBBBBB";

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String DEFAULT_DEBIT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DEBIT_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_ACCOUNT = "BBBBBBBBBB";

    private static final Double DEFAULT_FRAIS = 1D;
    private static final Double UPDATED_FRAIS = 2D;

    private static final String DEFAULT_CODE_RETOUR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_RETOUR = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TransactionGlobalRepository transactionGlobalRepository;

    @Autowired
    private TransactionGlobalService transactionGlobalService;

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

    private MockMvc restTransactionGlobalMockMvc;

    private TransactionGlobal transactionGlobal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionGlobalResource transactionGlobalResource = new TransactionGlobalResource(transactionGlobalService);
        this.restTransactionGlobalMockMvc = MockMvcBuilders.standaloneSetup(transactionGlobalResource)
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
    public static TransactionGlobal createEntity(EntityManager em) {
        TransactionGlobal transactionGlobal = new TransactionGlobal()
            .referenceFacture(DEFAULT_REFERENCE_FACTURE)
            .numeroFacture(DEFAULT_NUMERO_FACTURE)
            .datePaiement(DEFAULT_DATE_PAIEMENT)
            .echeance(DEFAULT_ECHEANCE)
            .channel(DEFAULT_CHANNEL)
            .deviceId(DEFAULT_DEVICE_ID)
            .client(DEFAULT_CLIENT)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .telephone(DEFAULT_TELEPHONE)
            .montant(DEFAULT_MONTANT)
            .debitAccount(DEFAULT_DEBIT_ACCOUNT)
            .creditAccount(DEFAULT_CREDIT_ACCOUNT)
            .frais(DEFAULT_FRAIS)
            .codeRetour(DEFAULT_CODE_RETOUR)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return transactionGlobal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionGlobal createUpdatedEntity(EntityManager em) {
        TransactionGlobal transactionGlobal = new TransactionGlobal()
            .referenceFacture(UPDATED_REFERENCE_FACTURE)
            .numeroFacture(UPDATED_NUMERO_FACTURE)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .echeance(UPDATED_ECHEANCE)
            .channel(UPDATED_CHANNEL)
            .deviceId(UPDATED_DEVICE_ID)
            .client(UPDATED_CLIENT)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .telephone(UPDATED_TELEPHONE)
            .montant(UPDATED_MONTANT)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .frais(UPDATED_FRAIS)
            .codeRetour(UPDATED_CODE_RETOUR)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        return transactionGlobal;
    }

    @BeforeEach
    public void initTest() {
        transactionGlobal = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionGlobal() throws Exception {
        int databaseSizeBeforeCreate = transactionGlobalRepository.findAll().size();

        // Create the TransactionGlobal
        restTransactionGlobalMockMvc.perform(post("/api/transaction-globals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGlobal)))
            .andExpect(status().isCreated());

        // Validate the TransactionGlobal in the database
        List<TransactionGlobal> transactionGlobalList = transactionGlobalRepository.findAll();
        assertThat(transactionGlobalList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionGlobal testTransactionGlobal = transactionGlobalList.get(transactionGlobalList.size() - 1);
        assertThat(testTransactionGlobal.getReferenceFacture()).isEqualTo(DEFAULT_REFERENCE_FACTURE);
        assertThat(testTransactionGlobal.getNumeroFacture()).isEqualTo(DEFAULT_NUMERO_FACTURE);
        assertThat(testTransactionGlobal.getDatePaiement()).isEqualTo(DEFAULT_DATE_PAIEMENT);
        assertThat(testTransactionGlobal.getEcheance()).isEqualTo(DEFAULT_ECHEANCE);
        assertThat(testTransactionGlobal.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testTransactionGlobal.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testTransactionGlobal.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testTransactionGlobal.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testTransactionGlobal.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTransactionGlobal.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testTransactionGlobal.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testTransactionGlobal.getDebitAccount()).isEqualTo(DEFAULT_DEBIT_ACCOUNT);
        assertThat(testTransactionGlobal.getCreditAccount()).isEqualTo(DEFAULT_CREDIT_ACCOUNT);
        assertThat(testTransactionGlobal.getFrais()).isEqualTo(DEFAULT_FRAIS);
        assertThat(testTransactionGlobal.getCodeRetour()).isEqualTo(DEFAULT_CODE_RETOUR);
        assertThat(testTransactionGlobal.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTransactionGlobal.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createTransactionGlobalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionGlobalRepository.findAll().size();

        // Create the TransactionGlobal with an existing ID
        transactionGlobal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionGlobalMockMvc.perform(post("/api/transaction-globals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGlobal)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionGlobal in the database
        List<TransactionGlobal> transactionGlobalList = transactionGlobalRepository.findAll();
        assertThat(transactionGlobalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransactionGlobals() throws Exception {
        // Initialize the database
        transactionGlobalRepository.saveAndFlush(transactionGlobal);

        // Get all the transactionGlobalList
        restTransactionGlobalMockMvc.perform(get("/api/transaction-globals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionGlobal.getId().intValue())))
            //.andExpect(jsonPath("$.[*].referenceFacture").value(hasItem(DEFAULT_REFERENCE_FACTURE))
            .andExpect(jsonPath("$.[*].numeroFacture").value(hasItem(DEFAULT_NUMERO_FACTURE)))
            .andExpect(jsonPath("$.[*].datePaiement").value(hasItem(DEFAULT_DATE_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].echeance").value(hasItem(DEFAULT_ECHEANCE)))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].debitAccount").value(hasItem(DEFAULT_DEBIT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].creditAccount").value(hasItem(DEFAULT_CREDIT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS.doubleValue())))
            .andExpect(jsonPath("$.[*].codeRetour").value(hasItem(DEFAULT_CODE_RETOUR)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTransactionGlobal() throws Exception {
        // Initialize the database
        transactionGlobalRepository.saveAndFlush(transactionGlobal);

        // Get the transactionGlobal
        restTransactionGlobalMockMvc.perform(get("/api/transaction-globals/{id}", transactionGlobal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionGlobal.getId().intValue()))
            //.andExpect(jsonPath("$.referenceFacture").value(DEFAULT_REFERENCE_FACTURE)
            .andExpect(jsonPath("$.numeroFacture").value(DEFAULT_NUMERO_FACTURE))
            .andExpect(jsonPath("$.datePaiement").value(DEFAULT_DATE_PAIEMENT.toString()))
            .andExpect(jsonPath("$.echeance").value(DEFAULT_ECHEANCE))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.debitAccount").value(DEFAULT_DEBIT_ACCOUNT))
            .andExpect(jsonPath("$.creditAccount").value(DEFAULT_CREDIT_ACCOUNT))
            .andExpect(jsonPath("$.frais").value(DEFAULT_FRAIS.doubleValue()))
            .andExpect(jsonPath("$.codeRetour").value(DEFAULT_CODE_RETOUR))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionGlobal() throws Exception {
        // Get the transactionGlobal
        restTransactionGlobalMockMvc.perform(get("/api/transaction-globals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionGlobal() throws Exception {
        // Initialize the database
        transactionGlobalService.save(transactionGlobal);

        int databaseSizeBeforeUpdate = transactionGlobalRepository.findAll().size();

        // Update the transactionGlobal
        TransactionGlobal updatedTransactionGlobal = transactionGlobalRepository.findById(transactionGlobal.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionGlobal are not directly saved in db
        em.detach(updatedTransactionGlobal);
        updatedTransactionGlobal
            .referenceFacture(UPDATED_REFERENCE_FACTURE)
            .numeroFacture(UPDATED_NUMERO_FACTURE)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .echeance(UPDATED_ECHEANCE)
            .channel(UPDATED_CHANNEL)
            .deviceId(UPDATED_DEVICE_ID)
            .client(UPDATED_CLIENT)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .telephone(UPDATED_TELEPHONE)
            .montant(UPDATED_MONTANT)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .frais(UPDATED_FRAIS)
            .codeRetour(UPDATED_CODE_RETOUR)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restTransactionGlobalMockMvc.perform(put("/api/transaction-globals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionGlobal)))
            .andExpect(status().isOk());

        // Validate the TransactionGlobal in the database
        List<TransactionGlobal> transactionGlobalList = transactionGlobalRepository.findAll();
        assertThat(transactionGlobalList).hasSize(databaseSizeBeforeUpdate);
        TransactionGlobal testTransactionGlobal = transactionGlobalList.get(transactionGlobalList.size() - 1);
        assertThat(testTransactionGlobal.getReferenceFacture()).isEqualTo(UPDATED_REFERENCE_FACTURE);
        assertThat(testTransactionGlobal.getNumeroFacture()).isEqualTo(UPDATED_NUMERO_FACTURE);
        assertThat(testTransactionGlobal.getDatePaiement()).isEqualTo(UPDATED_DATE_PAIEMENT);
        assertThat(testTransactionGlobal.getEcheance()).isEqualTo(UPDATED_ECHEANCE);
        assertThat(testTransactionGlobal.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testTransactionGlobal.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testTransactionGlobal.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testTransactionGlobal.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testTransactionGlobal.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTransactionGlobal.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testTransactionGlobal.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testTransactionGlobal.getDebitAccount()).isEqualTo(UPDATED_DEBIT_ACCOUNT);
        assertThat(testTransactionGlobal.getCreditAccount()).isEqualTo(UPDATED_CREDIT_ACCOUNT);
        assertThat(testTransactionGlobal.getFrais()).isEqualTo(UPDATED_FRAIS);
        assertThat(testTransactionGlobal.getCodeRetour()).isEqualTo(UPDATED_CODE_RETOUR);
        assertThat(testTransactionGlobal.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTransactionGlobal.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionGlobal() throws Exception {
        int databaseSizeBeforeUpdate = transactionGlobalRepository.findAll().size();

        // Create the TransactionGlobal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionGlobalMockMvc.perform(put("/api/transaction-globals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGlobal)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionGlobal in the database
        List<TransactionGlobal> transactionGlobalList = transactionGlobalRepository.findAll();
        assertThat(transactionGlobalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionGlobal() throws Exception {
        // Initialize the database
        transactionGlobalService.save(transactionGlobal);

        int databaseSizeBeforeDelete = transactionGlobalRepository.findAll().size();

        // Delete the transactionGlobal
        restTransactionGlobalMockMvc.perform(delete("/api/transaction-globals/{id}", transactionGlobal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionGlobal> transactionGlobalList = transactionGlobalRepository.findAll();
        assertThat(transactionGlobalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
