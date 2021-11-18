------ SCHEMA SITAT SA ----------
SET SCHEMA = SITATSA;
SET CURRENT PATH = SITATSA;


UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @
------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp = 'G_' and (numver = '1.4.78' or numver like '1.4.78.%'))=1
	THEN
	
		-- revisione scadenzario
		SET v_sql = 'ALTER TABLE g_scadenz ADD stpromem NUMERIC(7)';
		EXECUTE immediate v_sql;


		-- Nuovi campi per la gestione dei tabellati archiviati
		SET v_sql = 'ALTER TABLE c0oggass ADD c0anatto VARCHAR(20)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE c0oggass ADD c0adatto TIMESTAMP';
		EXECUTE immediate v_sql;
		
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800127026, 'E', NULL, 'C0ANATTO.C0OGGASS.GENE', 'C0A_NATTO', 'Numero atto del file associato', 'Numero atto', 'A20', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800127027, 'E', NULL, 'C0ADATTO.C0OGGASS.GENE', 'C0A_DATTO', 'Data atto del file associato', 'Data atto', 'A10', NULL, 'DATA_ELDA', NULL);

		-- revisione scadenzario
		delete from c0campi where c0c_mne_ber = 'G_SCADTIT';
		delete from c0campi where c0c_mne_ber = 'G_SCADTIPOEV';
		delete from c0campi where c0c_mne_ber = 'G_SCADTIPOFI';
		delete from c0campi where c0c_mne_ber = 'G_SCADIDATTIV';
		delete from c0campi where c0c_mne_ber = 'G_SCADDATAFI';
		delete from c0campi where c0c_mne_ber = 'G_SCADDOPO';
		delete from c0campi where c0c_mne_ber = 'G_SCADDATASCAD';
		delete from c0campi where c0c_mne_ber = 'G_SCADDATACONS';
		
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (880038297, 'E', NULL, 'TIT.G_SCADENZ.GENE', 'G_SCADTIT', 'Titolo attivita''', 'Titolo', 'A400', NULL, NULL, 'Titolo');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (880038299, 'E', NULL, 'TIPOEV.G_SCADENZ.GENE', 'G_SCADTIPOEV', 'Tipo evento', 'Tipo evento', 'A100', 'G_050', NULL, 'Tipo');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (880038304, 'E', NULL, 'TIPOFI.G_SCADENZ.GENE', 'G_SCADTIPOFI', 'Tipo termine', 'Tipo termine', 'A100', 'G_052', NULL, 'Modalita'' definizione data scadenza prevista');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (880038306, 'E', NULL, 'IDATTIV.G_SCADENZ.GENE', 'G_SCADIDATTIV', 'Attivita'' di riferimento', 'Att. riferimento', 'N12', NULL, NULL, 'Attivita'' di riferimento');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (880038307, 'E', NULL, 'DATAFI.G_SCADENZ.GENE', 'G_SCADDATAFI', 'Data scadenza prevista', 'Data scadenza prev.', 'A10', NULL, 'DATA_ELDA', 'Data scadenza prevista');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (880038305, 'E', NULL, 'FINEDOPO.G_SCADENZ.GENE', 'G_SCADDOPO', 'N. giorni (+/-)', 'N. giorni (+/-)', 'N3', NULL, NULL, 'Numero giorni (+/-) rispetto alla data di scadenza dell''attivita'' di riferimento');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (880038311, 'E', NULL, 'DATASCAD.G_SCADENZ.GENE', 'G_SCADDATASCAD', 'Data scadenza aggiornata', 'Data scad. agg.', 'A10', NULL, 'DATA_ELDA', 'Data scadenza aggiornata');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (880038310, 'E', NULL, 'DATACONS.G_SCADENZ.GENE', 'G_SCADDATACONS', 'Data scadenza effettiva', 'Data scad. eff.', 'A10', NULL, 'DATA_ELDA', 'Data scadenza effettiva');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'STPROMEM.G_SCADENZ.GENE', 'G_STPROMEM', 'Promemoria inviato ?', 'Prom. inv.?', 'N7', NULL, NULL, NULL);
		
		update tab1 set tab1desc = 'Inserimento manuale della data di scadenza' where tab1cod = 'G_052' and tab1tip = 1;
		update tab1 set tab1desc = 'Data di scadenza calcolata in relazione ad altra attività' where tab1cod = 'G_052' and tab1tip = 2;
		
		SET v_sql = 'update g_scadenz set stpromem = 1 where datascad < (current_timestamp + ggpromem DAYS)';
		EXECUTE immediate v_sql;
		
		update eldaver set numver = '1.4.79', datvet = current_timestamp where codapp = 'G_';

			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin
	IF (select count(*) from eldaver where codapp = 'G_' and (numver = '1.4.79' or numver like '1.4.79.%'))=1
	THEN
	
		
		update eldaver set numver = '1.4.80', datvet = current_timestamp where codapp = 'G_';

			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	IF (select count(*) from eldaver where codapp = 'G_' and (numver = '1.4.80' or numver like '1.4.80.%'))=1 THEN
		-- Nuovo attributo per la gestione dei permessi in dl229
		SET v_sql = 'ALTER TABLE G_PERMESSI ADD IDCUP NUMERIC(12)';
		EXECUTE immediate v_sql;
		Insert into C0CAMPI (COC_CONTA, C0C_TIP, C0C_CHI, COC_MNE_UNI, C0C_MNE_BER, COC_DES, COC_DES_FRM, C0C_FS, C0C_TAB1, COC_DOM, COC_DES_WEB)
			Values(880038137, 'E', NULL, 'IDCUP.G_PERMESSI.GENE', 'G_IDCUP', 'Id cup', 'Id cup', 'N12', NULL, NULL, NULL);
		Insert into c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re)
			Values ('G_PERMESSI.GENE#3', 6800111, 'C', NULL, 'Configurazione condivisione e protezioni del cup', 'IDCUP.G_PERMESSI.GENE', 'PROG_ANAG.DL229', 'ID.PROG_ANAG.DL229', NULL, NULL, 'g_perm0');	

		-- Aggiornamento codici ISTAT
		Update tabsche set tabcod3='001096085' where tabcod='S2003' and tabcod1='09' and tabcod3='001096029';
		Update tabsche set tabcod3='001096086' where tabcod='S2003' and tabcod1='09' and tabcod3='001096011';
		Update tabsche set tabcod3='003013250' where tabcod='S2003' and tabcod1='09' and tabcod3='003013019';
		Update tabsche set tabcod3='003108052' where tabcod='S2003' and tabcod1='09' and tabcod3='003108047';
		Update tabsche set tabcod3='003108053' where tabcod='S2003' and tabcod1='09' and tabcod3='003108088';
		Update tabsche set tabcod3='003108054' where tabcod='S2003' and tabcod1='09' and tabcod3='003108119';
		Update tabsche set tabcod3='003108055' where tabcod='S2003' and tabcod1='09' and tabcod3='003108186';
		Update tabsche set tabcod3='005025071' where tabcod='S2003' and tabcod1='09' and tabcod3='005025031';
		Update tabsche set tabcod3='008099021' where tabcod='S2003' and tabcod1='09' and tabcod3='011041011';
		Update tabsche set tabcod3='008099022' where tabcod='S2003' and tabcod1='09' and tabcod3='011041024';
		Update tabsche set tabcod3='008099023' where tabcod='S2003' and tabcod1='09' and tabcod3='011041039';
		Update tabsche set tabcod3='008099024' where tabcod='S2003' and tabcod1='09' and tabcod3='011041042';
		Update tabsche set tabcod3='008099025' where tabcod='S2003' and tabcod1='09' and tabcod3='011041053';
		Update tabsche set tabcod3='008099026' where tabcod='S2003' and tabcod1='09' and tabcod3='011041055';
		Update tabsche set tabcod3='008099027' where tabcod='S2003' and tabcod1='09' and tabcod3='011041063';
		Update tabsche set tabcod3='009052037' where tabcod='S2003' and tabcod1='09' and tabcod3='009052014';
		Update tabsche set tabcod3='020090001' where tabcod='S2003' and tabcod1='09' and tabcod3='020104001';
		Update tabsche set tabcod3='020090002' where tabcod='S2003' and tabcod1='09' and tabcod3='020104003';
		Update tabsche set tabcod3='020090006' where tabcod='S2003' and tabcod1='09' and tabcod3='020104004';
		Update tabsche set tabcod3='020090009' where tabcod='S2003' and tabcod1='09' and tabcod3='020104006';
		Update tabsche set tabcod3='020090014' where tabcod='S2003' and tabcod1='09' and tabcod3='020104007';
		Update tabsche set tabcod3='020090017' where tabcod='S2003' and tabcod1='09' and tabcod3='020104008';
		Update tabsche set tabcod3='020090021' where tabcod='S2003' and tabcod1='09' and tabcod3='020104010';
		Update tabsche set tabcod3='020090035' where tabcod='S2003' and tabcod1='09' and tabcod3='020104012';
		Update tabsche set tabcod3='020090036' where tabcod='S2003' and tabcod1='09' and tabcod3='020104014';
		Update tabsche set tabcod3='020090037' where tabcod='S2003' and tabcod1='09' and tabcod3='020104015';
		Update tabsche set tabcod3='020090041' where tabcod='S2003' and tabcod1='09' and tabcod3='020104016';
		Update tabsche set tabcod3='020090047' where tabcod='S2003' and tabcod1='09' and tabcod3='020104017';
		Update tabsche set tabcod3='020090049' where tabcod='S2003' and tabcod1='09' and tabcod3='020104018';
		Update tabsche set tabcod3='020090054' where tabcod='S2003' and tabcod1='09' and tabcod3='020104020';
		Update tabsche set tabcod3='020090062' where tabcod='S2003' and tabcod1='09' and tabcod3='020104002';
		Update tabsche set tabcod3='020090063' where tabcod='S2003' and tabcod1='09' and tabcod3='020104022';
		Update tabsche set tabcod3='020090070' where tabcod='S2003' and tabcod1='09' and tabcod3='020104025';
		Update tabsche set tabcod3='020090074' where tabcod='S2003' and tabcod1='09' and tabcod3='020104026';
		Update tabsche set tabcod3='020090080' where tabcod='S2003' and tabcod1='09' and tabcod3='020104024';
		Update tabsche set tabcod3='020090081' where tabcod='S2003' and tabcod1='09' and tabcod3='020104005';
		Update tabsche set tabcod3='020090083' where tabcod='S2003' and tabcod1='09' and tabcod3='020104011';
		Update tabsche set tabcod3='020090084' where tabcod='S2003' and tabcod1='09' and tabcod3='020104013';
		Update tabsche set tabcod3='020090085' where tabcod='S2003' and tabcod1='09' and tabcod3='020104021';
		Update tabsche set tabcod3='020090090' where tabcod='S2003' and tabcod1='09' and tabcod3='020104019';
		Update tabsche set tabcod3='020090091' where tabcod='S2003' and tabcod1='09' and tabcod3='020104009';
		Update tabsche set tabcod3='020090092' where tabcod='S2003' and tabcod1='09' and tabcod3='020104023';
		Update tabsche set tabcod3='020091002' where tabcod='S2003' and tabcod1='09' and tabcod3='020105001';
		Update tabsche set tabcod3='020091005' where tabcod='S2003' and tabcod1='09' and tabcod3='020105002';
		Update tabsche set tabcod3='020091006' where tabcod='S2003' and tabcod1='09' and tabcod3='020105003';
		Update tabsche set tabcod3='020091019' where tabcod='S2003' and tabcod1='09' and tabcod3='020105005';
		Update tabsche set tabcod3='020091026' where tabcod='S2003' and tabcod1='09' and tabcod3='020105006';
		Update tabsche set tabcod3='020091031' where tabcod='S2003' and tabcod1='09' and tabcod3='020105007';
		Update tabsche set tabcod3='020091032' where tabcod='S2003' and tabcod1='09' and tabcod3='020105008';
		Update tabsche set tabcod3='020091035' where tabcod='S2003' and tabcod1='09' and tabcod3='020105009';
		Update tabsche set tabcod3='020091037' where tabcod='S2003' and tabcod1='09' and tabcod3='020105010';
		Update tabsche set tabcod3='020091039' where tabcod='S2003' and tabcod1='09' and tabcod3='020105011';
		Update tabsche set tabcod3='020091042' where tabcod='S2003' and tabcod1='09' and tabcod3='020105012';
		Update tabsche set tabcod3='020091069' where tabcod='S2003' and tabcod1='09' and tabcod3='020105013';
		Update tabsche set tabcod3='020091072' where tabcod='S2003' and tabcod1='09' and tabcod3='020105014';
		Update tabsche set tabcod3='020091088' where tabcod='S2003' and tabcod1='09' and tabcod3='020105016';
		Update tabsche set tabcod3='020091089' where tabcod='S2003' and tabcod1='09' and tabcod3='020105017';
		Update tabsche set tabcod3='020091095' where tabcod='S2003' and tabcod1='09' and tabcod3='020105018';
		Update tabsche set tabcod3='020091097' where tabcod='S2003' and tabcod1='09' and tabcod3='020105019';
		Update tabsche set tabcod3='020091098' where tabcod='S2003' and tabcod1='09' and tabcod3='020105020';
		Update tabsche set tabcod3='020091099' where tabcod='S2003' and tabcod1='09' and tabcod3='020105021';
		Update tabsche set tabcod3='020091100' where tabcod='S2003' and tabcod1='09' and tabcod3='020105022';
		Update tabsche set tabcod3='020091101' where tabcod='S2003' and tabcod1='09' and tabcod3='020105023';
		Update tabsche set tabcod3='020091103' where tabcod='S2003' and tabcod1='09' and tabcod3='020105004';
		Update tabsche set tabcod3='020111001' where tabcod='S2003' and tabcod1='09' and tabcod3='020106001';
		Update tabsche set tabcod3='020111002' where tabcod='S2003' and tabcod1='09' and tabcod3='020092002';
		Update tabsche set tabcod3='020111003' where tabcod='S2003' and tabcod1='09' and tabcod3='020092004';
		Update tabsche set tabcod3='020111004' where tabcod='S2003' and tabcod1='09' and tabcod3='020092005';
		Update tabsche set tabcod3='020111005' where tabcod='S2003' and tabcod1='09' and tabcod3='020106002';
		Update tabsche set tabcod3='020111006' where tabcod='S2003' and tabcod1='09' and tabcod3='020107001';
		Update tabsche set tabcod3='020111007' where tabcod='S2003' and tabcod1='09' and tabcod3='020092008';
		Update tabsche set tabcod3='020111008' where tabcod='S2003' and tabcod1='09' and tabcod3='020107002';
		Update tabsche set tabcod3='020111009' where tabcod='S2003' and tabcod1='09' and tabcod3='020107003';
		Update tabsche set tabcod3='020111010' where tabcod='S2003' and tabcod1='09' and tabcod3='020107004';
		Update tabsche set tabcod3='020111011' where tabcod='S2003' and tabcod1='09' and tabcod3='020092106';
		Update tabsche set tabcod3='020111012' where tabcod='S2003' and tabcod1='09' and tabcod3='020106003';
		Update tabsche set tabcod3='020111013' where tabcod='S2003' and tabcod1='09' and tabcod3='020092016';
		Update tabsche set tabcod3='020111014' where tabcod='S2003' and tabcod1='09' and tabcod3='020092017';
		Update tabsche set tabcod3='020111015' where tabcod='S2003' and tabcod1='09' and tabcod3='020092018';
		Update tabsche set tabcod3='020111016' where tabcod='S2003' and tabcod1='09' and tabcod3='020107005';
		Update tabsche set tabcod3='020111017' where tabcod='S2003' and tabcod1='09' and tabcod3='020092020';
		Update tabsche set tabcod3='020111018' where tabcod='S2003' and tabcod1='09' and tabcod3='020092110';
		Update tabsche set tabcod3='020111019' where tabcod='S2003' and tabcod1='09' and tabcod3='020092111';
		Update tabsche set tabcod3='020111020' where tabcod='S2003' and tabcod1='09' and tabcod3='020092112';
		Update tabsche set tabcod3='020111021' where tabcod='S2003' and tabcod1='09' and tabcod3='020107006';
		Update tabsche set tabcod3='020111022' where tabcod='S2003' and tabcod1='09' and tabcod3='020106004';
		Update tabsche set tabcod3='020111023' where tabcod='S2003' and tabcod1='09' and tabcod3='020095081';
		Update tabsche set tabcod3='020111024' where tabcod='S2003' and tabcod1='09' and tabcod3='020106005';
		Update tabsche set tabcod3='020111025' where tabcod='S2003' and tabcod1='09' and tabcod3='020092113';
		Update tabsche set tabcod3='020111026' where tabcod='S2003' and tabcod1='09' and tabcod3='020092024';
		Update tabsche set tabcod3='020111027' where tabcod='S2003' and tabcod1='09' and tabcod3='020106006';
		Update tabsche set tabcod3='020111028' where tabcod='S2003' and tabcod1='09' and tabcod3='020107007';
		Update tabsche set tabcod3='020111029' where tabcod='S2003' and tabcod1='09' and tabcod3='020092027';
		Update tabsche set tabcod3='020111030' where tabcod='S2003' and tabcod1='09' and tabcod3='020107008';
		Update tabsche set tabcod3='020111031' where tabcod='S2003' and tabcod1='09' and tabcod3='020106007';
		Update tabsche set tabcod3='020111032' where tabcod='S2003' and tabcod1='09' and tabcod3='020092030';
		Update tabsche set tabcod3='020111033' where tabcod='S2003' and tabcod1='09' and tabcod3='020092031';
		Update tabsche set tabcod3='020111034' where tabcod='S2003' and tabcod1='09' and tabcod3='020106008';
		Update tabsche set tabcod3='020111035' where tabcod='S2003' and tabcod1='09' and tabcod3='020107009';
		Update tabsche set tabcod3='020111036' where tabcod='S2003' and tabcod1='09' and tabcod3='020092114';
		Update tabsche set tabcod3='020111037' where tabcod='S2003' and tabcod1='09' and tabcod3='020106009';
		Update tabsche set tabcod3='020111038' where tabcod='S2003' and tabcod1='09' and tabcod3='020106010';
		Update tabsche set tabcod3='020111039' where tabcod='S2003' and tabcod1='09' and tabcod3='020092036';
		Update tabsche set tabcod3='020111040' where tabcod='S2003' and tabcod1='09' and tabcod3='020107010';
		Update tabsche set tabcod3='020111041' where tabcod='S2003' and tabcod1='09' and tabcod3='020092038';
		Update tabsche set tabcod3='020111042' where tabcod='S2003' and tabcod1='09' and tabcod3='020092039';
		Update tabsche set tabcod3='020111043' where tabcod='S2003' and tabcod1='09' and tabcod3='020107011';
		Update tabsche set tabcod3='020111044' where tabcod='S2003' and tabcod1='09' and tabcod3='020107012';
		Update tabsche set tabcod3='020111045' where tabcod='S2003' and tabcod1='09' and tabcod3='020092115';
		Update tabsche set tabcod3='020111046' where tabcod='S2003' and tabcod1='09' and tabcod3='020092116';
		Update tabsche set tabcod3='020111047' where tabcod='S2003' and tabcod1='09' and tabcod3='020092042';
		Update tabsche set tabcod3='020111048' where tabcod='S2003' and tabcod1='09' and tabcod3='020092117';
		Update tabsche set tabcod3='020111049' where tabcod='S2003' and tabcod1='09' and tabcod3='020107013';
		Update tabsche set tabcod3='020111050' where tabcod='S2003' and tabcod1='09' and tabcod3='020092118';
		Update tabsche set tabcod3='020111051' where tabcod='S2003' and tabcod1='09' and tabcod3='020092044';
		Update tabsche set tabcod3='020111052' where tabcod='S2003' and tabcod1='09' and tabcod3='020106011';
		Update tabsche set tabcod3='020111053' where tabcod='S2003' and tabcod1='09' and tabcod3='020106012';
		Update tabsche set tabcod3='020111054' where tabcod='S2003' and tabcod1='09' and tabcod3='020107014';
		Update tabsche set tabcod3='020111055' where tabcod='S2003' and tabcod1='09' and tabcod3='020092048';
		Update tabsche set tabcod3='020111056' where tabcod='S2003' and tabcod1='09' and tabcod3='020107015';
		Update tabsche set tabcod3='020111057' where tabcod='S2003' and tabcod1='09' and tabcod3='020107016';
		Update tabsche set tabcod3='020111058' where tabcod='S2003' and tabcod1='09' and tabcod3='020092119';
		Update tabsche set tabcod3='020111059' where tabcod='S2003' and tabcod1='09' and tabcod3='020106013';
		Update tabsche set tabcod3='020111060' where tabcod='S2003' and tabcod1='09' and tabcod3='020092053';
		Update tabsche set tabcod3='020111061' where tabcod='S2003' and tabcod1='09' and tabcod3='020092054';
		Update tabsche set tabcod3='020111062' where tabcod='S2003' and tabcod1='09' and tabcod3='020106014';
		Update tabsche set tabcod3='020111063' where tabcod='S2003' and tabcod1='09' and tabcod3='020107017';
		Update tabsche set tabcod3='020111064' where tabcod='S2003' and tabcod1='09' and tabcod3='020092058';
		Update tabsche set tabcod3='020111065' where tabcod='S2003' and tabcod1='09' and tabcod3='020092059';
		Update tabsche set tabcod3='020111066' where tabcod='S2003' and tabcod1='09' and tabcod3='020092064';
		Update tabsche set tabcod3='020111067' where tabcod='S2003' and tabcod1='09' and tabcod3='020106015';
		Update tabsche set tabcod3='020111068' where tabcod='S2003' and tabcod1='09' and tabcod3='020107018';
		Update tabsche set tabcod3='020111069' where tabcod='S2003' and tabcod1='09' and tabcod3='020092061';
		Update tabsche set tabcod3='020111070' where tabcod='S2003' and tabcod1='09' and tabcod3='020107019';
		Update tabsche set tabcod3='020111071' where tabcod='S2003' and tabcod1='09' and tabcod3='020107020';
		Update tabsche set tabcod3='020111072' where tabcod='S2003' and tabcod1='09' and tabcod3='020106016';
		Update tabsche set tabcod3='020111073' where tabcod='S2003' and tabcod1='09' and tabcod3='020106017';
		Update tabsche set tabcod3='020111074' where tabcod='S2003' and tabcod1='09' and tabcod3='020092069';
		Update tabsche set tabcod3='020111075' where tabcod='S2003' and tabcod1='09' and tabcod3='020092070';
		Update tabsche set tabcod3='020111076' where tabcod='S2003' and tabcod1='09' and tabcod3='020092071';
		Update tabsche set tabcod3='020111077' where tabcod='S2003' and tabcod1='09' and tabcod3='020106018';
		Update tabsche set tabcod3='020111078' where tabcod='S2003' and tabcod1='09' and tabcod3='020106019';
		Update tabsche set tabcod3='020111079' where tabcod='S2003' and tabcod1='09' and tabcod3='020092120';
		Update tabsche set tabcod3='020111080' where tabcod='S2003' and tabcod1='09' and tabcod3='020106020';
		Update tabsche set tabcod3='020111081' where tabcod='S2003' and tabcod1='09' and tabcod3='020105015';
		Update tabsche set tabcod3='020111082' where tabcod='S2003' and tabcod1='09' and tabcod3='020092121';
		Update tabsche set tabcod3='020111083' where tabcod='S2003' and tabcod1='09' and tabcod3='020106021';
		Update tabsche set tabcod3='020111084' where tabcod='S2003' and tabcod1='09' and tabcod3='020092078';
		Update tabsche set tabcod3='020111085' where tabcod='S2003' and tabcod1='09' and tabcod3='020092079';
		Update tabsche set tabcod3='020111086' where tabcod='S2003' and tabcod1='09' and tabcod3='020092081';
		Update tabsche set tabcod3='020111087' where tabcod='S2003' and tabcod1='09' and tabcod3='020092082';
		Update tabsche set tabcod3='020111088' where tabcod='S2003' and tabcod1='09' and tabcod3='020092083';
		Update tabsche set tabcod3='020111089' where tabcod='S2003' and tabcod1='09' and tabcod3='020092084';
		Update tabsche set tabcod3='020111090' where tabcod='S2003' and tabcod1='09' and tabcod3='020107021';
		Update tabsche set tabcod3='020111091' where tabcod='S2003' and tabcod1='09' and tabcod3='020106022';
		Update tabsche set tabcod3='020111092' where tabcod='S2003' and tabcod1='09' and tabcod3='020106023';
		Update tabsche set tabcod3='020111093' where tabcod='S2003' and tabcod1='09' and tabcod3='020092088';
		Update tabsche set tabcod3='020111094' where tabcod='S2003' and tabcod1='09' and tabcod3='020106024';
		Update tabsche set tabcod3='020111095' where tabcod='S2003' and tabcod1='09' and tabcod3='020092091';
		Update tabsche set tabcod3='020111096' where tabcod='S2003' and tabcod1='09' and tabcod3='020106025';
		Update tabsche set tabcod3='020111097' where tabcod='S2003' and tabcod1='09' and tabcod3='020106026';
		Update tabsche set tabcod3='020111098' where tabcod='S2003' and tabcod1='09' and tabcod3='020107022';
		Update tabsche set tabcod3='020111099' where tabcod='S2003' and tabcod1='09' and tabcod3='020092122';
		Update tabsche set tabcod3='020111100' where tabcod='S2003' and tabcod1='09' and tabcod3='020106027';
		Update tabsche set tabcod3='020111101' where tabcod='S2003' and tabcod1='09' and tabcod3='020106028';
		Update tabsche set tabcod3='020111102' where tabcod='S2003' and tabcod1='09' and tabcod3='020107023';
		Update tabsche set tabcod3='020111103' where tabcod='S2003' and tabcod1='09' and tabcod3='020092097';
		Update tabsche set tabcod3='020111104' where tabcod='S2003' and tabcod1='09' and tabcod3='020092098';
		Update tabsche set tabcod3='020111105' where tabcod='S2003' and tabcod1='09' and tabcod3='020092100';
		Update tabsche set tabcod3='020111106' where tabcod='S2003' and tabcod1='09' and tabcod3='020092101';
		Update tabsche set tabcod3='020111107' where tabcod='S2003' and tabcod1='09' and tabcod3='020092102';

		-- Nuovi comuni
		Delete from tabsche where tabcod='S2003' and tabcod1='09' and tabcod3 in 
			( '009047023', '005025072', '003013253', '004022235', '008037062', '004022236', '004022237', '004022238', '004022239', '003020071', '001103078', '009050040', '004022240', '009051040', 
			  '004022241', '011041069', '003013251', '004022228', '004022242', '003018191', '003018192', '009050041', '004022233', '009046036', '009048052', '008038027', '012058122', '006030042',
			  '003013249', '004022229', '003012142', '004022243', '008099029', '015064121', '004022234', '008099028', '008034050', '004022244', '009051041', '004022230', '004022245', '005025070', 
			  '006030188', '004022231', '009047024', '003016252', '009048053', '004022246', '009046037', '008034049', '008038028', '011041070', '004022247', '011042050', '003013252', '003016253', 
			  '005025073', '004022232', '011043058', '011041068', '004022248', '008037061', '006093053', '008035046', '003097091', '004022249');

		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8555', 'ABETONE CUTIGLIANO', '009047023', '51021', 'A275');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8556', 'ALPAGO', '005025072', '32016', 'G262');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8557', 'ALTA VALLE INTELVI', '003013253', '22024', 'G396');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8558', 'ALTAVALLE', '004022235', '38092', 'G462');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8559', 'ALTO RENO TERME', '008037062', '40046', 'G777');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8560', 'ALTOPIANO DELLA VIGOLANA', '004022236', '38049', 'G973');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8561', 'AMBLAR-DON', '004022237', '38011', 'H340');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8562', 'BORGO CHIESE', '004022238', '38083', 'D802');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8563', 'BORGO LARES', '004022239', '38079', 'E295');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8564', 'BORGO VIRGILIO', '003020071', '46034', 'F390');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8565', 'BORGOMEZZAVALLE', '001103078', '28846', 'G358');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8566', 'CASCIANA TERME LARI', '009050040', '56035', 'F828');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8567', 'CASTEL IVANO', '004022240', '38059', 'F070');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8568', 'CASTELFRANCO PIANDISCO''', '009051040', '52026', 'A413');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8569', 'CEMBRA LISIGNAGO', '004022241', '38034', 'E751');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8570', 'COLLI AL METAURO', '011041069', '61036', 'A690');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8571', 'COLVERDE', '003013251', '22041', 'B945');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8572', 'COMANO TERME', '004022228', '38077', 'C053');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8573', 'CONTA''', '004022242', '38093', 'E310');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8574', 'CORNALE E BASTIDA', '003018191', '27056', 'G851');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8575', 'CORTEOLONA E GENZONE', '003018192', '27014', 'I968');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8576', 'CRESPINA LORENZANA', '009050041', '56042', 'B129');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8577', 'DIMARO FOLGARIDA', '004022233', '38025', 'C118');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8578', 'FABBRICHE DI VERGEMOLI', '009046036', '55021', 'F265');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8579', 'FIGLINE E INCISA VALDARNO', '009048052', '50063', 'A376');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8580', 'FISCAGLIA', '008038027', '44027', 'E786');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8581', 'FONTE NUOVA', '012058122', '00013', 'D222');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8582', 'FORNI DI SOTTO', '006030042', '33020', 'G083');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8583', 'GRAVEDONA ED UNITI', '003013249', '22015', 'A954');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8584', 'LEDRO', '004022229', '38067', 'D578');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8585', 'MACCAGNO CON PINO E VEDDASCA', '003012142', '21061', 'B259');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8586', 'MADRUZZO', '004022243', '38076', 'D316');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8587', 'MONTESCUDO - MONTE COLOMBO', '008099029', '47854', 'G768');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8588', 'MONTORO', '015064121', '83025', 'D899');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8589', 'PIEVE DI BONO-PREZZO', '004022234', '38085', 'G232');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8590', 'POGGIO TORRIANA', '008099028', '47824', 'G704');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8591', 'POLESINE ZIBELLO', '008034050', '43016', 'H658');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8592', 'PORTE DI RENDENA', '004022244', '38094', 'D682');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8593', 'PRATOVECCHIO STIA', '009051041', '52015', 'C766');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8594', 'PREDAIA', '004022230', '38012', 'C866');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8595', 'PRIMIERO SAN MARTINO DI CASTROZZA', '004022245', '38054', 'G524');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8596', 'QUERO VAS', '005025070', '32038', 'A885');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8597', 'RIVIGNANO TEOR', '006030188', '33061', 'H495');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8598', 'SAN LORENZO DORSINO', '004022231', '38078', 'F397');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8599', 'SAN MARCELLO PITEGLIO', '009047024', '51028', 'F988');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8600', 'SANT''OMOBONO TERME', '003016252', '24038', 'F587');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8601', 'SCARPERIA E SAN PIERO', '009048053', '50038', 'H047');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8602', 'SELLA GIUDICARIE', '004022246', '38087', 'A482');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8603', 'SILLANO GIUNCUGNANO', '009046037', '55039', 'A053');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8604', 'SISSA TRECASALI', '008034049', '43018', 'B813');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8605', 'TERRE DEL RENO', '008038028', '44047', 'I537');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8606', 'TERRE ROVERESCHE', '011041070', '61038', 'I600');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8607', 'TRE VILLE', '004022247', '38095', 'H428');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8608', 'TRECASTELLI', '011042050', '60012', 'I188');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8609', 'TREMEZZINA', '003013252', '22016', 'L837');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8610', 'VAL BREMBILLA', '003016253', '24012', 'H159');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8611', 'VAL DI ZOLDO', '005025073', '32012', 'H743');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8612', 'VALDAONE', '004022232', '38091', 'I723');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8613', 'VALFORNACE', '011043058', '62035', 'I824');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8614', 'VALLEFOGLIA', '011041068', '61022', 'A766');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8615', 'VALLELAGHI', '004022248', '38096', 'A841');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8616', 'VALSAMOGGIA', '008037061', '40053', 'G597');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8617', 'VALVASONE ARZENE', '006093053', '33098', 'I202');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8618', 'VENTASSO', '008035046', '42032', 'B265');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8619', 'VERDERIO', '003097091', '23879', 'E425');
		Insert into tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) values ('S2003', '09', '8620', 'VILLE D''ANAUNIA', '004022249', '38019', 'L991');

		update eldaver set numver = '1.4.81', datvet = current_timestamp  where codapp = 'G_';
	END IF;
end@

call aggiorna@
drop procedure aggiorna@


------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp = 'G_' and (numver = '1.4.81' or numver like '1.4.81.%'))=1
	THEN
	
		--Definizione nuovi campi
		-- Nuovo campo 'Numero d'ordine' per ordinamento Configurazione codifica automatica
		SET v_sql = 'ALTER TABLE G_CONFCOD ADD NUMORD NUMERIC(3)';
		EXECUTE immediate v_sql;

		SET v_sql = 'Create Table UFFSET (
                ID NUMERIC(10) NOT NULL,
				CODEIN VARCHAR(16) NOT NULL,
				NOMSET NUMERIC(7),
				DATFIN TIMESTAMP,
                primary key (ID))';
 	    EXECUTE immediate v_sql;

		SET v_sql = 'ALTER TABLE UFFSET ADD CONSTRAINT FK_UFFSET_UFFINT FOREIGN KEY ( CODEIN ) REFERENCES UFFINT( CODEIN ) ON DELETE CASCADE';
		EXECUTE immediate v_sql;

                
        -- Inizializzazione nuovo campo 'Numero d'ordine' per ordinamento Configurazione codifica automatica
		SET v_sql = 'update g_confcod set numord=1 where noment=''TECNI'' and nomcam=''CODTEC'' and tipcam=-1';
		EXECUTE immediate v_sql;
		SET v_sql = 'update g_confcod set numord=2 where noment=''IMPR'' and nomcam=''CODIMP'' and tipcam=-1';
		EXECUTE immediate v_sql;
		SET v_sql = 'update g_confcod set numord=3 where noment=''TEIM'' and nomcam=''CODTIM'' and tipcam=-1';
		EXECUTE immediate v_sql;
		SET v_sql = 'update g_confcod set numord=4 where noment=''UFFINT'' and nomcam=''CODEIN'' and tipcam=-1';
		EXECUTE immediate v_sql;
		SET v_sql = 'update g_confcod set numord=5 where noment=''UTENT'' and nomcam=''CODUTE'' and tipcam=-1';
		EXECUTE immediate v_sql;
		
		IF (select count(*) from tab1 where tab1cod = 'A1092')=0
	        THEN
	            INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('A1092',1,'Servizio edilizia',NULL,0,NULL);
				INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('A1092',2,'Servizio viabilità',NULL,0,NULL);
				INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('A1092',3,'Servizio ambiente',NULL,0,NULL);
			END IF;
		if (select count(*) from tab6 where tab6tip='A1092')=0 then
			INSERT INTO TAB6 (TAB6COD,TAB6TIP,TAB6DESC) VALUES ('G__1','A1092','Ufficio della stazione appaltante');
		else 
			UPDATE TAB6 SET TAB6COD='G__1' where tab6cod='G1_1' and tab6tip='A1092';
		end if;

		--Aggiornamento C0CAMPI
	    delete from c0campi where c0c_mne_ber in ('G_NUMORDCC','G_IDUFFSET','G_CODEINSE','G_NOMSET','G_DATFINSE');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E',NULL,'NUMORD.G_CONFCOD.GENE', 'G_NUMORDCC', 'Numero d''ordine', 'Numero d''ordine', 'N3', NULL, NULL, NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E','P','ID.UFFSET.GENE','G_IDUFFSET','Id','Id','N12',NULL,NULL,NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E',NULL,'CODEIN.UFFSET.GENE','G_CODEINSE','Codice dell''ufficio intestatario','Codice uff.intestat.','A16',NULL,NULL,NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E',NULL,'NOMSET.UFFSET.GENE','G_NOMSET','Denominazione del settore','Denominaz.settore','A100','A1092',NULL,'Denominazione');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E',NULL,'DATFIN.UFFSET.GENE','G_DATFINSE','Data disattivazione del settore','Data disattivazione','A10',NULL,'DATA_ELDA',NULL);

		UPDATE C0CAMPI SET C0C_TIP='E' WHERE  C0C_MNE_BER in ('G_NOMENT1','G_NOMCAM1','G_TIPCAM1','G_CODAPP','G_DESCAM','G_CONTAT','G_CODCAL');		
		UPDATE C0CAMPI SET COC_DES='Descrizione oggetto della codifica',COC_DES_FRM='Oggetto codifica',COC_DES_WEB='Oggetto della codifica' WHERE  C0C_MNE_BER = 'G_DESCAM';
		UPDATE C0CAMPI SET COC_DES_FRM='Contatore' WHERE  C0C_MNE_BER = 'G_CONTAT';
		UPDATE C0CAMPI SET COC_DES_FRM='Criterio di calcolo' WHERE  C0C_MNE_BER = 'G_CODCAL';
		
		--Aggiornamento C0ENTIT
		delete from c0entit where c0e_nom in ('UFFSET.GENE');
		INSERT INTO C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) VALUES ('UFFSET.GENE',0,'E','ARC_UF_SET','Settori dell''ufficio intestatario','CODEIN.UFFSET.GENE','UFFINT.GENE','CODEIN.UFFINT.GENE',NULL,NULL,'g_setuf0');
                
		UPDATE C0ENTIT SET C0E_TIP='E' WHERE C0E_NOM='G_CONFCOD.GENE';
		
		-- Tabella GENE.G_DURC - Aumento dimensione campi a 20 caratteri
		SET v_sql = 'ALTER TABLE G_DURC ALTER COLUMN PROTOCOL SET DATA TYPE VARCHAR(20)';
		EXECUTE immediate v_sql;
		update c0campi set c0c_fs = 'A20' where coc_mne_uni = 'PROTOCOL.G_DURC.GENE';
		
		-- Tabella GENE.IMPANTIMAFIA - Aumento dimensione campi a 20 caratteri
		SET v_sql = 'ALTER TABLE IMPANTIMAFIA ALTER COLUMN NCDEDU SET DATA TYPE VARCHAR(20)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMPANTIMAFIA ALTER COLUMN NPRICH SET DATA TYPE VARCHAR(20)';
		EXECUTE immediate v_sql;
		update c0campi set c0c_fs = 'A20' where coc_mne_uni = 'NCDEDU.IMPANTIMAFIA.GENE';
		update c0campi set c0c_fs = 'A20' where coc_mne_uni = 'NPRICH.IMPANTIMAFIA.GENE';
		
		update eldaver set numver = '1.4.82', datvet = current_timestamp where codapp = 'G_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp = 'G_' and (numver = '1.4.82' or numver like '1.4.82.%'))=1
	THEN
	
		-- aggiornato ordinamento delle tipologie appalto in CAIS (prima le 2 di lavori, poi forniture, poi le 2 di servizi)
		update tab1 set tab1nord=1 where tab1cod='G_038' and tab1tip=1;
		update tab1 set tab1nord=2 where tab1cod='G_038' and tab1tip=4;
		update tab1 set tab1nord=3 where tab1cod='G_038' and tab1tip=2;
		update tab1 set tab1nord=4 where tab1cod='G_038' and tab1tip=3;
		update tab1 set tab1nord=5 where tab1cod='G_038' and tab1tip=5;
		
		-- definizione campo iscrcciaa in impr
		SET v_sql = 'ALTER TABLE IMPR ADD ISCRCCIAA VARCHAR(1)';
		EXECUTE immediate v_sql;
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800102129, 'E', NULL, 'ISCRCCIAA.IMPR.GENE', 'G_ISCRCCIAA', 'Iscritto alla Camera di Commercio?', 'Iscr. CCIAA?', 'A1', NULL, 'SN', 'Iscritto alla Camera di Commercio?');

		-- (S.Santi 10.07.2017) Inizializza flag 'Iscritto  C.C.I.A.A.'
		SET v_sql = 'update impr set iscrcciaa=''1'' where ncciaa is not null or dcciaa is not null or regdit is not null or discif is not null or pcciaa is not null or ncercc is not null or dcercc is not null or dantim is not null';
		EXECUTE immediate v_sql;
		SET v_sql = 'update impr set iscrcciaa=''2'' where tipimp=6 and ncciaa is null and dcciaa is null and regdit is null and discif is null and  pcciaa is null and ncercc is null and dcercc is null and dantim is null';
		EXECUTE immediate v_sql;

		update eldaver set numver = '1.4.83', datvet = current_timestamp where codapp = 'G_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp = 'G_' and (numver = '1.4.83' or numver like '1.4.83.%'))=1
	THEN
		
		SET v_sql = 'ALTER TABLE USRSYS ADD SYSABAP VARCHAR(5)';
		EXECUTE immediate v_sql;
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'SYSABAP.USRSYS.GENE', 'G_USYSABAP', 'Privilegi dell''utente su AP (Non definito,Ammin.,Utente)', 'Privilegi AP', 'A5', NULL, NULL, NULL);

		--Allineamento codice NUTS a SIMAP R2.0.9.S02
		delete from TABNUTS;
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('IT','IT',NULL,NULL,NULL,'ITALIA');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC','IT','C',NULL,NULL,'NORD-OVEST');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC1','IT','C','1',NULL,'Piemonte');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC11','IT','C','1','1','Torino');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC12','IT','C','1','2','Vercelli');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC13','IT','C','1','3','Biella');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC14','IT','C','1','4','Verbano-Cusio-Ossola');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC15','IT','C','1','5','Novara');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC16','IT','C','1','6','Cuneo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC17','IT','C','1','7','Asti');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC18','IT','C','1','8','Alessandria');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC2','IT','C','2',NULL,'Valle d’Aosta / Vallée d’Aoste');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC20','IT','C','2','0','Valle d’Aosta / Vallée d’Aoste');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC3','IT','C','3',NULL,'Liguria');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC31','IT','C','3','1','Imperia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC32','IT','C','3','2','Savona');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC33','IT','C','3','3','Genova');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC34','IT','C','3','4','La Spezia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC4','IT','C','4',NULL,'Lombardia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC41','IT','C','4','1','Varese');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC42','IT','C','4','2','Como');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC43','IT','C','4','3','Lecco');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC44','IT','C','4','4','Sondrio');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC46','IT','C','4','6','Bergamo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC47','IT','C','4','7','Brescia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC48','IT','C','4','8','Pavia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC49','IT','C','4','9','Lodi');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC4A','IT','C','4','A','Cremona');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC4B','IT','C','4','B','Mantova');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC4C','IT','C','4','C','Milano');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITC4D','IT','C','4','D','Monza e della Brianza');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF','IT','F',NULL,NULL,'SUD');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF1','IT','F','1',NULL,'Abruzzo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF11','IT','F','1','1','L’Aquila');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF12','IT','F','1','2','Teramo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF13','IT','F','1','3','Pescara');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF14','IT','F','1','4','Chieti');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF2','IT','F','2',NULL,'Molise');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF21','IT','F','2','1','Isernia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF22','IT','F','2','2','Campobasso');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF3','IT','F','3',NULL,'Campania');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF31','IT','F','3','1','Caserta');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF32','IT','F','3','2','Benevento');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF33','IT','F','3','3','Napoli');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF34','IT','F','3','4','Avellino');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF35','IT','F','3','5','Salerno');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF4','IT','F','4',NULL,'Puglia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF43','IT','F','4','3','Taranto');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF44','IT','F','4','4','Brindisi');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF45','IT','F','4','5','Lecce');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF46','IT','F','4','6','Foggia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF47','IT','F','4','7','Bari');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF48','IT','F','4','8','Barletta-Andria-Trani');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF5','IT','F','5',NULL,'Basilicata');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF51','IT','F','5','1','Potenza');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF52','IT','F','5','2','Matera');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF6','IT','F','6',NULL,'Calabria');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF61','IT','F','6','1','Cosenza');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF62','IT','F','6','2','Crotone');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF63','IT','F','6','3','Catanzaro');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF64','IT','F','6','4','Vibo Valentia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITF65','IT','F','6','5','Reggio di Calabria');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG','IT','G',NULL,NULL,'ISOLE');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG1','IT','G','1',NULL,'Sicilia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG11','IT','G','1','1','Trapani');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG12','IT','G','1','2','Palermo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG13','IT','G','1','3','Messina');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG14','IT','G','1','4','Agrigento');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG15','IT','G','1','5','Caltanissetta');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG16','IT','G','1','6','Enna');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG17','IT','G','1','7','Catania');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG18','IT','G','1','8','Ragusa');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG19','IT','G','1','9','Siracusa');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG2','IT','G','2',NULL,'Sardegna');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG25','IT','G','2','5','Sassari');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG26','IT','G','2','6','Nuoro');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG27','IT','G','2','7','Cagliari');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG28','IT','G','2','8','Oristano');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG29','IT','G','2','9','Olbia-Tempio');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG2A','IT','G','2','A','Ogliastra');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG2B','IT','G','2','B','Medio Campidano');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITG2C','IT','G','2','C','Carbonia-Iglesias');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH','IT','H',NULL,NULL,'NORD-EST');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH1','IT','H','1',NULL,'Provincia Autonoma di Bolzano / Bozen');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH10','IT','H','1','0','Bolzano-Bozen');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH2','IT','H','2',NULL,'Provincia Autonoma di Trento');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH20','IT','H','2','0','Trento');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH3','IT','H','3',NULL,'Veneto');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH31','IT','H','3','1','Verona');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH32','IT','H','3','2','Vicenza');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH33','IT','H','3','3','Belluno');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH34','IT','H','3','4','Treviso');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH35','IT','H','3','5','Venezia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH36','IT','H','3','6','Padova');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH37','IT','H','3','7','Rovigo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH4','IT','H','4',NULL,'Friuli-Venezia Giulia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH41','IT','H','4','1','Pordenone');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH42','IT','H','4','2','Udine');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH43','IT','H','4','3','Gorizia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH44','IT','H','4','4','Trieste');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH5','IT','H','5',NULL,'Emilia-Romagna');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH51','IT','H','5','1','Piacenza');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH52','IT','H','5','2','Parma');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH53','IT','H','5','3','Reggio nell’Emilia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH54','IT','H','5','4','Modena');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH55','IT','H','5','5','Bologna');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH56','IT','H','5','6','Ferrara');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH57','IT','H','5','7','Ravenna');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH58','IT','H','5','8','Forlì-Cesena');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITH59','IT','H','5','9','Rimini');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI','IT','I',NULL,NULL,'CENTRO (IT)');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI1','IT','I','1',NULL,'Toscana');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI11','IT','I','1','1','Massa-Carrara');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI12','IT','I','1','2','Lucca');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI13','IT','I','1','3','Pistoia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI14','IT','I','1','4','Firenze');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI15','IT','I','1','5','Prato');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI16','IT','I','1','6','Livorno');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI17','IT','I','1','7','Pisa');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI18','IT','I','1','8','Arezzo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI19','IT','I','1','9','Siena');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI1A','IT','I','1','A','Grosseto');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI2','IT','I','2',NULL,'Umbria');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI21','IT','I','2','1','Perugia');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI22','IT','I','2','2','Terni');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI3','IT','I','3',NULL,'Marche');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI31','IT','I','3','1','Pesaro e Urbino');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI32','IT','I','3','2','Ancona');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI33','IT','I','3','3','Macerata');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI34','IT','I','3','4','Ascoli Piceno');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI35','IT','I','3','5','Fermo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI4','IT','I','4',NULL,'Lazio');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI41','IT','I','4','1','Viterbo');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI42','IT','I','4','2','Rieti');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI43','IT','I','4','3','Roma');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI44','IT','I','4','4','Latina');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITI45','IT','I','4','5','Frosinone');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITZ','IT','Z',NULL,NULL,'EXTRA-REGIO NUTS 1');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITZZ','IT','Z','Z',NULL,'Extra-Regio NUTS 2');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE) VALUES ('ITZZZ','IT','Z','Z','Z','Extra-Regio NUTS 3');

		update eldaver set numver = '1.4.84', datvet = current_timestamp where codapp = 'G_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

---------------------------------------------
--- Aggiornamento GENE da 1.4.84 a 1.4.85 ---
---------------------------------------------

create procedure aggiorna ()
begin

	DECLARE v_sql VARCHAR(5000);
	DECLARE EOF INT DEFAULT 0;
	DECLARE maxID INT DEFAULT 0;
	DECLARE implegIdNull INT DEFAULT 0;
	DECLARE impdteIdNull INT DEFAULT 0;
	DECLARE ls_impleg_codimp2 VARCHAR(10);
	DECLARE ls_impleg_codleg VARCHAR(10);
	DECLARE ll_impleg_new_id INT DEFAULT 0;
	DECLARE ls_impdte_codimp3 VARCHAR(10);
	DECLARE ls_impdte_coddte VARCHAR(10);
	DECLARE ll_impdte_new_id INT DEFAULT 0;
	
	DECLARE ciclo_impleg CURSOR FOR select impleg.codimp2, impleg.codleg from impleg order by impleg.codimp2, impleg.codleg;
	DECLARE ciclo_impdte CURSOR FOR select impdte.codimp3, impdte.coddte from impdte order by impdte.codimp3, impdte.coddte;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET EOF = 1;
	
	IF (select count(*) from eldaver where codapp='G_' and (numver='1.4.84' or numver like '1.4.84.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------
	
		-- Nuovi campi per la gestione dei tabellati archiviati
		SET v_sql = 'ALTER TABLE IMPLEG ADD COLUMN ID NUMERIC(12)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPLEG');
		SET v_sql = 'ALTER TABLE IMPDTE ADD COLUMN ID NUMERIC(12)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPDTE');
		
		SET v_sql = 'ALTER TABLE IMPR ADD CLADIM NUMERIC(7)';
		EXECUTE immediate v_sql;
		-- Iscrizione white list antimafia 
		SET v_sql = 'ALTER TABLE IMPR ADD ISCRIWL VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD WLPREFE VARCHAR(50)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD WLSEZIO VARCHAR(20)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD WLDISCRI TIMESTAMP';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD WLDSCAD TIMESTAMP';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD WLINCORSO VARCHAR(1)';
		EXECUTE immediate v_sql;
		
		--CODATT.IMPR esteso a VC(15)
		SET v_sql = 'ALTER TABLE IMPR ALTER COLUMN CODATT SET DATA TYPE VARCHAR(15)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPR');
		
		--Nuova entità DURC 
		SET v_sql = 'CREATE TABLE IMPDURC (
			ID NUMERIC(12) NOT NULL,
			CODIMP VARCHAR(10) NOT NULL,
			NPROTO VARCHAR(20),
			DATRIC TIMESTAMP,
			DATSCA TIMESTAMP,
			TIPESI NUMERIC(7),
			NOTDUR VARCHAR(2000),
			PRIMARY KEY(ID))';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'ALTER TABLE IMPDURC ADD CONSTRAINT FK_IMPDURC_IMPR FOREIGN KEY (CODIMP) REFERENCES IMPR(CODIMP) ON DELETE CASCADE';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPDURC');
		
		-- Nuova view V_IMPR_VERIFICA. Indica per ogni ditta in anagrafica se ha DURC regolare e se è iscritta alla WhiteList.
		-- DURC regolare: è soggetta a DURC con durc regolare e con scadenza successiva alla data odierna oppure non è soggetta a DURC. Nel caso di RT,
		-- tutte le ditte componenti devono avere DURC regolare.
		-- WhiteList: è iscritta con scadenza successiva alla data odierna. Nel caso di RT, tutte le ditte componenti devono essere iscritte.
		SET v_sql = 'create view v_impr_verifica as 
			with durc_reg as (
				select CODIMP from impr where 
				((IMPR.TIPIMP is null or tipimp not in (3,10)) and 
				((IMPR.SOGGDURC = ''1'' and exists(select codimp from impdurc where impdurc.codimp = impr.codimp and timestamp(to_char(COALESCE(impdurc.datsca,  ''1900/01/01''),''YYYY/MM/DD'') || '' '' || ''23:59:59'', '' HH24:MI:SS YYYY/MM/DD'')>=CURRENT_TIMESTAMP and TIPESI=1 )) OR (IMPR.SOGGDURC = ''2''))
				)
				OR
				(IMPR.TIPIMP in (3,10) and exists (select codime9 from ragimp where ragimp.codime9=impr.codimp)
				and not exists(select codime9 from ragimp,impr imprcomp where ragimp.coddic=imprcomp.codimp and ragimp.codime9=impr.codimp
				and ((imprcomp.SOGGDURC = ''1'' and NOT exists(select codimp from impdurc where impdurc.codimp = imprcomp.codimp and timestamp(to_char(COALESCE(impdurc.datsca,  ''1900/01/01''),''YYYY/MM/DD'') || '' '' || ''23:59:59'', '' HH24:MI:SS YYYY/MM/DD'')>=CURRENT_TIMESTAMP and TIPESI=1 )) OR (imprcomp.SOGGDURC IS NULL)))
				)),
			wl_reg as (
				select codimp from impr where 
				((IMPR.TIPIMP is null or tipimp not in (3,10)) and iscriwl=''1'' and timestamp(to_char(COALESCE(wldscad,  ''1900/01/01''),''YYYY/MM/DD'') || '' '' || ''23:59:59'', '' HH24:MI:SS YYYY/MM/DD'')>=CURRENT_TIMESTAMP)
				OR 
				(IMPR.TIPIMP in (3,10) and exists (select codime9 from ragimp where ragimp.codime9=impr.codimp)
				and not exists (select codime9 from ragimp,impr imprcomp where ragimp.coddic=imprcomp.codimp and ragimp.codime9=impr.codimp
				and (imprcomp.iscriwl is null or imprcomp.iscriwl=''2'' or NOT(imprcomp.iscriwl=''1'' and timestamp(to_char(COALESCE(imprcomp.wldscad, ''1900/01/01''),''YYYY/MM/DD'') || '' '' || ''23:59:59'', '' HH24:MI:SS YYYY/MM/DD'')>=CURRENT_TIMESTAMP))))
				)
			select impr.codimp, 
				case when durc_reg.codimp is null then ''2'' else ''1'' end durc_reg,
				case when wl_reg.codimp is null then ''2'' else ''1'' end wl_reg
			from impr 
				left outer join durc_reg on (impr.codimp=durc_reg.codimp)
				left outer join wl_reg on (impr.codimp=wl_reg.codimp)';
		EXECUTE immediate v_sql;
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------
		if (select count(*) from W_GENCHIAVI where upper(TABELLA) = 'UFFSET')=0
		then
			INSERT INTO W_GENCHIAVI (tabella, chiave) VALUES ('UFFSET',0);
		end if;
		
		--Inizializzazione W_GENCHIAVI
		INSERT INTO W_GENCHIAVI (TABELLA,CHIAVE) VALUES ('IMPDURC',0);
		
		--Inizializzazione W_GENCHIAVI per IMPLEG e IMPDTE
		INSERT INTO W_GENCHIAVI (TABELLA,CHIAVE) VALUES ('IMPLEG',0);
		INSERT INTO W_GENCHIAVI (TABELLA,CHIAVE) VALUES ('IMPDTE',0);
		
		-- CAMBIA LA CHIAVE PRIMARIA DELL'ENTITA' IMPLEG:
		-- Valorizza il nuovo campo IMPLEG.ID (quì non ancora impostato come chiave)
		SET ll_impleg_new_id = 0;
		OPEN ciclo_impleg;
			WHILE EOF = 0 DO
				-- Inizializzazione i campi della query ciclo_impleg
				FETCH FROM ciclo_impleg INTO ls_impleg_codimp2, ls_impleg_codleg;

				-- Inizializza ll_impleg_new_id
				SET ll_impleg_new_id = ll_impleg_new_id + 1;
				-- Gestisce l'update in IMPLEG.ID
				IF (ls_impleg_codimp2 is not NULL) and (length(ls_impleg_codimp2) > 0) then
					IF (ls_impleg_codleg is not NULL) and (length(ls_impleg_codleg) > 0) then
						IF (ll_impleg_new_id is not NULL) and (ll_impleg_new_id > 0) then
							SET v_sql = 'UPDATE IMPLEG set ID = ' || cast(ll_impleg_new_id as char(10)) || ' where CODIMP2 = ''' || ls_impleg_codimp2 || ''' and CODLEG = ''' || ls_impleg_codleg || '''';
							EXECUTE IMMEDIATE v_sql;
						END IF;
					END IF;
				END IF;
			END WHILE;
		CLOSE ciclo_impleg;
		
		-- Imposta la constraint di tipo 'not null' sul campo ID
		SET v_sql = 'ALTER TABLE IMPLEG ALTER COLUMN ID SET NOT NULL';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPLEG');
		
		-- Sostituisce la definizione corrente dei campi chiave con la nuova definizione
		SET v_sql = 'SET implegIdNull = select count(1) from IMPLEG where (ID is null) or (ID=0)';
		if (implegIdNull)<=0
		then
			SET v_sql = 'ALTER TABLE IMPLEG DROP PRIMARY KEY';
			EXECUTE IMMEDIATE v_sql;
			SET v_sql = 'ALTER TABLE IMPLEG ADD PRIMARY KEY(ID)';
			EXECUTE IMMEDIATE v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPLEG');
			--- Elimina l'eventuale indice IMPLEG_PK che veniva creato nei DB inizializzati fino a 05/2009
			---if (select count(*) from SYSCAT.INDEXES where upper(TABNAME)='IMPLEG' and upper(TABSCHEMA)='SITATSA') = 1
			---then
			---	SET v_sql = 'DROP INDEX ' || (select INDNAME from SYSCAT.INDEXES where upper(TABNAME)='IMPLEG' and upper(TABSCHEMA)='SITATSA');
			---	EXECUTE IMMEDIATE v_sql;
			---end if;
		end if;
		
		
		-- Imposta l'occorrenza in W_GENCHIAVI
		SET v_sql = 'UPDATE W_GENCHIAVI set CHIAVE=(select coalesce(MAX(ID),0) FROM IMPLEG) where TABELLA=''IMPLEG''';
		EXECUTE IMMEDIATE v_sql;
		
		-- Imposta le occorrenze in C0CAMPI
		DELETE FROM c0campi WHERE coc_mne_uni LIKE '%.IMPLEG.%';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104001, 'E', 'P', 'ID.IMPLEG.GENE', 'G_IDLEG', 'Id', 'Id', 'N12', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104002, 'E', NULL, 'CODIMP2.IMPLEG.GENE', 'CODIMP2', 'Codice impresa', 'Codice impresa', 'A10', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104003, 'E', NULL, 'CODLEG.IMPLEG.GENE', 'CODLEG', 'Codice del legale rappresentante', 'Cod. legale rappres.', 'A10', NULL, NULL, 'Codice');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104004, 'E', NULL, 'NOMLEG.IMPLEG.GENE', 'NOMLEG', 'Nome del legale rappresentante', 'Nome legale rappres.', 'A161', NULL, NULL, 'Nome');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104005, 'E', NULL, 'LEGINI.IMPLEG.GENE', 'G_LEGINI', 'Data inizio dell''incarico', 'Inizio incarico', 'A10', NULL, 'DATA_ELDA', 'Data inizio incarico');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104006, 'E', NULL, 'LEGFIN.IMPLEG.GENE', 'G_LEGFIN', 'Data termine dell''incarico', 'Termine incarico', 'A10', NULL, 'DATA_ELDA', 'Data termine incarico');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104007, 'E', NULL, 'NOTLEG.IMPLEG.GENE', 'G_NOTLEG', 'Eventuali note', 'Note', 'A2000', NULL, 'NOTE', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104008, 'C', NULL, 'DAESTERN.IMPLEG.GENE', 'G_DAESTELR', 'Inserito da portale o esterno', 'Inserito da portale', 'A2', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800104009, 'E', NULL, 'RESPDICH.IMPLEG.GENE', 'G_RESDICLR', 'Responsabile delle dichiarazioni (DPR 445/2000)', 'Resp. dichiarazioni', 'A2', NULL, 'SN', 'Responsabile delle dichiarazioni (DPR 445/2000)');
		
		-- CAMBIA LA CHIAVE PRIMARIA DELL'ENTITA' IMPDTE:
		-- Valorizza il nuovo campo IMPDTE.ID (quì non ancora impostato come chiave)
		SET ll_impdte_new_id = 0;
		OPEN ciclo_impdte;
			WHILE EOF = 0 DO
			-- Inizializzazione i campi della query ciclo_impdte
			FETCH FROM ciclo_impdte INTO ls_impdte_codimp3, ls_impdte_coddte;
				-- Inizializza ll_impdte_new_id
				SET ll_impdte_new_id = ll_impdte_new_id + 1;
				-- Gestisce l'update in IMPDTE.ID
				if (ls_impdte_codimp3 is not NULL) and (length(ls_impdte_codimp3) > 0) then
					if (ls_impdte_coddte is not NULL) and (length(ls_impdte_coddte) > 0) then
						if (ll_impdte_new_id is not NULL) and (ll_impdte_new_id > 0) then
							SET v_sql = 'UPDATE IMPDTE set ID = ''' || cast(ll_impdte_new_id as char(10)) || ''' where CODIMP3 = ''' || ls_impdte_codimp3 || ''' and CODDTE = ''' || ls_impdte_coddte || '''';
							EXECUTE IMMEDIATE v_sql;
						END IF;
					END IF;
				END IF;
			END WHILE;
		CLOSE ciclo_impdte;
		
		-- Imposta la constraint di tipo 'not null' sul campo ID
		SET v_sql = 'ALTER TABLE IMPDTE ALTER COLUMN ID SET NOT NULL';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPDTE');
		
		-- Sostituisce la definizione corrente dei campi chiave con la nuova definizione
		SET v_sql = 'SET impdteIdNull = select count(*) from IMPDTE where (ID is null) or (ID=0)';
		if (impdteIdNull) <=0
		then 
			SET v_sql = 'ALTER TABLE IMPDTE DROP PRIMARY KEY';
			EXECUTE IMMEDIATE v_sql;
			SET v_sql = 'ALTER TABLE IMPDTE ADD PRIMARY KEY(ID)';
			EXECUTE IMMEDIATE v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMPDTE');
			-- Elimina l'eventuale indice IMPLEG_PK che veniva creato nei DB inizializzati fino a 05/2009
			---if (select count(*) from syscat.indexes where tabname = 'IMPDTE' and tabschema = 'SITATSA') = 1
			---then
			---	SET v_sql = 'DROP INDEX ' || (select indname from syscat.indexes where tabname = 'IMPLEG' and tabschema = 'SITATSA');
			---	EXECUTE IMMEDIATE v_sql;
			---end if;
		end if;

		-- Imposta l'occorrenza in W_GENCHIAVI
		SET v_sql = 'UPDATE W_GENCHIAVI set CHIAVE=(select coalesce(MAX(ID),0) FROM IMPDTE) where TABELLA=''IMPDTE''';
		EXECUTE IMMEDIATE v_sql;
		
		-- Imposta le occorrenze in C0CAMPI
		DELETE FROM c0campi WHERE coc_mne_uni LIKE '%IMPDTE%';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103001, 'E', 'P', 'ID.IMPDTE.GENE', 'G_IDDTE', 'Id', 'Id', 'N12', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103002, 'E', NULL, 'CODIMP3.IMPDTE.GENE', 'CODIMP3', 'Codice impresa', 'Codice impresa', 'A10', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103003, 'E', NULL, 'CODDTE.IMPDTE.GENE', 'CODDTE', 'Codice del direttore tecnico', 'Cod. dir.tecnico', 'A10', NULL, NULL, 'Codice');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103004, 'E', NULL, 'NOMDTE.IMPDTE.GENE', 'NOMDTE', 'Nome del direttore tecnico', 'Nome dir.tecnico', 'A161', NULL, NULL, 'Nome');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103005, 'E', NULL, 'DIRINI.IMPDTE.GENE', 'G_DIRINI', 'Data inizio dell''incarico', 'Inizio incarico', 'A10', NULL, 'DATA_ELDA', 'Data inizio incarico');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103006, 'E', NULL, 'DIRFIN.IMPDTE.GENE', 'G_DIRFIN', 'Data termine dell''incarico', 'Termine incarico', 'A10', NULL, 'DATA_ELDA', 'Data termine incarico');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103007, 'E', NULL, 'NOTDTE.IMPDTE.GENE', 'G_NOTDTE', 'Eventuali note', 'Note', 'A2000', NULL, 'NOTE', NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103008, 'C', NULL, 'DAESTERN.IMPDTE.GENE', 'G_DAESTEDT', 'Inserito da portale o esterno', 'Inserito da portale', 'A2', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (800103009, 'E', NULL, 'RESPDICH.IMPDTE.GENE', 'G_RESDICDT', 'Responsabile delle dichiarazioni (DPR 445/2000)', 'Resp. dichiarazioni', 'A2', NULL, 'SN', 'Responsabile delle dichiarazioni (DPR 445/2000)');
		
		--sbiancato campo CODATT.IMPR 
		UPDATE IMPR SET CODATT=NULL;
		
		-- G_060 - tabellato per TIPESI.IMPDURC
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_060', 1, 'Regolare', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_060', 2, 'Irregolare', NULL, 0);
		
		-- G_061 - tabellato per whitelist (art.53 L.190/2012)
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 1, 'Trasporto di materiali a discarica per conto di terzi', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 2, 'Trasporto, anche transfrontaliero, e smaltimento di rifiuti per conto di terzi', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 3, 'Estrazione, fornitura e trasporto di terra e materiali inerti', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 4, 'Confezionamento, fornitura e trasporto di calcestruzzo e di bitume', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 5, 'Noli a freddo di macchinari', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 6, 'Fornitura di ferro lavorato', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 7, 'Noli a caldo', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 8, 'Autotrasporto per conto di terzi', NULL, 0);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_061', 9, 'Guardiania dei cantieri', NULL, 0);

		-- G_062 - tabellato per il campo CLADIM.IMPR
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_062', 3, 'Micro impresa', NULL, 1);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_062', 2, 'Piccola impresa', NULL, 2);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_062', 1, 'Media impresa', NULL, 3);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_062', 4, 'Grande impresa', NULL, 4);		
		
		-- G_j07 tabellato per CODATT
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A011', 'COLTIVAZIONE DI COLTURE AGRICOLE NON PERMANENTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A012', 'COLTIVAZIONE DI COLTURE PERMANENTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A013', 'RIPRODUZIONE DELLE PIANTE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A014', 'ALLEVAMENTO DI ANIMALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A015', 'COLTIVAZIONI AGRICOLE ASSOCIATE ALL''ALLEVAMENTO DI ANIMALI: ATTIVITA'' MISTA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A016', 'ATTIVITA'' DI SUPPORTO ALL''AGRICOLTURA E ATTIVITA'' SUCCESSIVE ALLA RACCOLTA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A017', 'CACCIA, CATTURA DI ANIMALI E SERVIZI CONNESSI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A021', 'SILVICOLTURA ED ALTRE ATTIVITA'' FORESTALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A022', 'UTILIZZO DI AREE FORESTALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A023', 'RACCOLTA DI PRODOTTI SELVATICI NON LEGNOSI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A024', 'SERVIZI DI SUPPORTO PER LA SILVICOLTURA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A031', 'PESCA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007A032', 'ACQUACOLTURA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B051', 'ESTRAZIONE DI ANTRACITE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B052', 'ESTRAZIONE DI LIGNITE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B061', 'ESTRAZIONE DI PETROLIO GREGGIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B062', 'ESTRAZIONE DI GAS NATURALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B071', 'ESTRAZIONE DI MINERALI METALLIFERI FERROSI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B072', 'ESTRAZIONE DI MINERALI METALLIFERI NON FERROSI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B081', 'ESTRAZIONE DI PIETRA, SABBIA E ARGILLA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B089', 'ESTRAZIONE DI MINERALI DA CAVE E MINIERE NCA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B091', 'ATTIVITA'' DI SUPPORTO ALL''ESTRAZIONE DI PETROLIO E DI GAS NATURALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007B099', 'ATTIVITA'' DI SUPPORTO PER L''ESTRAZIONE DA CAVE E MINIERE DI ALTRI MINERALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C101', 'LAVORAZIONE E CONSERVAZIONE DI CARNE E PRODUZIONE DI PRODOTTI A BASE DI CARNE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C102', 'LAVORAZIONE E CONSERVAZIONE DI PESCE, CROSTACEI E MOLLUSCHI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C103', 'LAVORAZIONE E CONSERVAZIONE DI FRUTTA E ORTAGGI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C104', 'PRODUZIONE DI OLI E GRASSI VEGETALI E ANIMALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C105', 'INDUSTRIA LATTIERO-CASEARIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C106', 'LAVORAZIONE DELLE GRANAGLIE, PRODUZIONE DI AMIDI E DI PRODOTTI AMIDACEI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C107', 'PRODUZIONE DI PRODOTTI DA FORNO E FARINACEI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C108', 'PRODUZIONE DI ALTRI PRODOTTI ALIMENTARI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C109', 'PRODUZIONE DI PRODOTTI PER L''ALIMENTAZIONE DEGLI ANIMALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C110', 'INDUSTRIA DELLE BEVANDE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C120', 'INDUSTRIA DEL TABACCO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C131', 'PREPARAZIONE E FILATURA DI FIBRE TESSILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C132', 'TESSITURA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C133', 'FINISSAGGIO DEI TESSILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C139', 'ALTRE INDUSTRIE TESSILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C141', 'CONFEZIONE DI ARTICOLI DI ABBIGLIAMENTO (ESCLUSO ABBIGLIAMENTO IN PELLICCIA)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C142', 'CONFEZIONE DI ARTICOLI IN PELLICCIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C143', 'FABBRICAZIONE DI ARTICOLI DI MAGLIERIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C151', 'PREPARAZIONE E CONCIA DEL CUOIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C152', 'FABBRICAZIONE DI CALZATURE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C161', 'TAGLIO E PIALLATURA DEL LEGNO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C162', 'FABBRICAZIONE DI PRODOTTI IN LEGNO, SUGHERO, PAGLIA E MATERIALI DA INTRECCIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C171', 'FABBRICAZIONE DI PASTA-CARTA, CARTA E CARTONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C172', 'FABBRICAZIONE DI ARTICOLI DI CARTA E CARTONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C181', 'STAMPA E SERVIZI CONNESSI ALLA STAMPA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C182', 'RIPRODUZIONE DI SUPPORTI REGISTRATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C191', 'FABBRICAZIONE DI PRODOTTI DI COKERIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C192', 'FABBRICAZIONE DI PRODOTTI DERIVANTI DALLA RAFFINAZIONE DEL PETROLIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C201', 'FABBRICAZIONE DI PRODOTTI CHIMICI DI BASE, DI FERTILIZZANTI E COMPOSTI AZOTATI, DI MATERIE PLASTICHE E GOMMA SINTETICA IN FORME PRIMARIE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C202', 'FABBRICAZIONE DI AGROFARMACI E DI ALTRI PRODOTTI CHIMICI PER L''AGRICOLTURA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C203', 'FABBRICAZIONE DI PITTURE, VERNICI E SMALTI, INCHIOSTRI DA STAMPA E ADESIVI SINTETICI (MASTICI)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C204', 'FABBRICAZIONE DI SAPONI E DETERGENTI, DI PRODOTTI PER LA PULIZIA E LA LUCIDATURA, DI PROFUMI E COSMETICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C205', 'FABBRICAZIONE DI ALTRI PRODOTTI CHIMICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C206', 'FABBRICAZIONE DI FIBRE SINTETICHE E ARTIFICIALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C211', 'FABBRICAZIONE DI PRODOTTI FARMACEUTICI DI BASE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C239', 'FABBRICAZIONE DI PRODOTTI ABRASIVI E DI PRODOTTI IN MINERALI NON METALLIFERI NCA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C241', 'SIDERURGIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C242', 'FABBRICAZIONE DI TUBI, CONDOTTI, PROFILATI CAVI E RELATIVI ACCESSORI IN ACCIAIO (ESCLUSI QUELLI IN ACCIAIO COLATO)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C243', 'FABBRICAZIONE DI ALTRI PRODOTTI DELLA PRIMA TRASFORMAZIONE DELL''ACCIAIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C244', 'PRODUZIONE DI METALLI DI BASE PREZIOSI E ALTRI METALLI NON FERROSI, TRATTAMENTO DEI COMBUSTIBILI NUCLEARI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C245', 'FONDERIE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C251', 'FABBRICAZIONE DI ELEMENTI DA COSTRUZIONE IN METALLO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C252', 'FABBRICAZIONE DI CISTERNE, SERBATOI, RADIATORI E CONTENITORI IN METALLO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C253', 'FABBRICAZIONE DI GENERATORI DI VAPORE (ESCLUSI I CONTENITORI IN METALLO PER CALDAIE PER IL RISCALDAMENTO CENTRALE AD ACQUA CALDA)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C254', 'FABBRICAZIONE DI ARMI E MUNIZIONI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C255', 'FUCINATURA, IMBUTITURA, STAMPAGGIO E PROFILATURA DEI METALLI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C256', 'TRATTAMENTO E RIVESTIMENTO DEI METALLI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C257', 'FABBRICAZIONE DI ARTICOLI DI COLTELLERIA, UTENSILI E OGGETTI DI FERRAMENTA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C259', 'FABBRICAZIONE DI ALTRI PRODOTTI IN METALLO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C261', 'FABBRICAZIONE DI COMPONENTI ELETTRONICI E SCHEDE ELETTRONICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C262', 'FABBRICAZIONE DI COMPUTER E UNITÀ PERIFERICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C263', 'FABBRICAZIONE DI APPARECCHIATURE PER LE TELECOMUNICAZIONI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C264', 'FABBRICAZIONE DI PRODOTTI DI ELETTRONICA DI CONSUMO AUDIO E VIDEO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C265', 'FABBRICAZIONE DI STRUMENTI E APPARECCHI DI MISURAZIONE, PROVA E NAVIGAZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C266', 'FABBRICAZIONE DI STRUMENTI PER IRRADIAZIONE, APPARECCHIATURE ELETTROMEDICALI ED ELETTROTERAPEUTICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C267', 'FABBRICAZIONE DI STRUMENTI OTTICI E ATTREZZATURE FOTOGRAFICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C268', 'FABBRICAZIONE DI SUPPORTI MAGNETICI ED OTTICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C271', 'FABBRICAZIONE DI MOTORI, GENERATORI E TRASFORMATORI ELETTRICI E DI APPARECCHIATURE PER LA DISTRIBUZIONE E IL CONTROLLO DELL''ELETTRICITA''', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C272', 'FABBRICAZIONE DI BATTERIE DI PILE ED ACCUMULATORI ELETTRICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C273', 'FABBRICAZIONE DI CABLAGGI E APPARECCHIATURE DI CABLAGGIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C274', 'FABBRICAZIONE DI APPARECCHIATURE PER ILLUMINAZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C275', 'FABBRICAZIONE DI APPARECCHI PER USO DOMESTICO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C279', 'FABBRICAZIONE DI ALTRE APPARECCHIATURE ELETTRICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C281', 'FABBRICAZIONE DI MACCHINE DI IMPIEGO GENERALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C282', 'FABBRICAZIONE DI ALTRE MACCHINE DI IMPIEGO GENERALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C283', 'FABBRICAZIONE DI MACCHINE PER L''AGRICOLTURA E LA SILVICOLTURA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C284', 'FABBRICAZIONE DI MACCHINE PER LA FORMATURA DEI METALLI E DI ALTRE MACCHINE UTENSILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C289', 'FABBRICAZIONE DI ALTRE MACCHINE PER IMPIEGHI SPECIALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C291', 'FABBRICAZIONE DI AUTOVEICOLI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C292', 'FABBRICAZIONE DI CARROZZERIE PER AUTOVEICOLI, RIMORCHI E SEMIRIMORCHI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C293', 'FABBRICAZIONE DI PARTI ED ACCESSORI PER AUTOVEICOLI E LORO MOTORI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C301', 'COSTRUZIONE DI NAVI E IMBARCAZIONI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C302', 'COSTRUZIONE DI LOCOMOTIVE E DI MATERIALE ROTABILE FERRO-TRANVIARIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C303', 'FABBRICAZIONE DI AEROMOBILI, DI VEICOLI SPAZIALI E DEI RELATIVI DISPOSITIVI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C304', 'FABBRICAZIONE DI VEICOLI MILITARI DA COMBATTIMENTO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C309', 'FABBRICAZIONE DI MEZZI DI TRASPORTO NCA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C310', 'FABBRICAZIONE DI MOBILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C321', 'FABBRICAZIONE DI GIOIELLERIA, BIGIOTTERIA E ARTICOLI CONNESSI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C322', 'FABBRICAZIONE DI STRUMENTI MUSICALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C323', 'FABBRICAZIONE DI ARTICOLI SPORTIVI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C324', 'FABBRICAZIONE DI GIOCHI E GIOCATTOLI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C325', 'FABBRICAZIONE DI STRUMENTI E FORNITURE MEDICHE E DENTISTICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C329', 'INDUSTRIE MANIFATTURIERE NCA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C331', 'RIPARAZIONE E MANUTENZIONE DI PRODOTTI IN METALLO, MACCHINE ED APPARECCHIATURE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C332', 'INSTALLAZIONE DI MACCHINE ED APPARECCHIATURE INDUSTRIALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007D351', 'PRODUZIONE, TRASMISSIONE E DISTRIBUZIONE DI ENERGIA ELETTRICA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007D352', 'PRODUZIONE DI GAS', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007D353', 'FORNITURA DI VAPORE E ARIA CONDIZIONATA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007E360', 'RACCOLTA, TRATTAMENTO E FORNITURA DI ACQUA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007E370', 'GESTIONE DELLE RETI FOGNARIE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007E381', 'RACCOLTA DEI RIFIUTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007E382', 'TRATTAMENTO E SMALTIMENTO DEI RIFIUTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007E383', 'RECUPERO DEI MATERIALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007E390', 'ATTIVITA'' DI RISANAMENTO E ALTRI SERVIZI DI GESTIONE DEI RIFIUTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F411', 'SVILUPPO DI PROGETTI IMMOBILIARI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F412', 'COSTRUZIONE DI EDIFICI RESIDENZIALI E NON RESIDENZIALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F421', 'COSTRUZIONE DI STRADE E FERROVIE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F422', 'COSTRUZIONE DI OPERE DI PUBBLICA UTILITÀ', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F429', 'COSTRUZIONE DI ALTRE OPERE DI INGEGNERIA CIVILE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F431', 'DEMOLIZIONE E PREPARAZIONE DEL CANTIERE EDILE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F432', 'INSTALLAZIONE DI IMPIANTI ELETTRICI, IDRAULICI ED ALTRI LAVORI DI COSTRUZIONE E INSTALLAZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F433', 'COMPLETAMENTO E FINITURA DI EDIFICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007F439', 'ALTRI LAVORI SPECIALIZZATI DI COSTRUZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G451', 'COMMERCIO DI AUTOVEICOLI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G452', 'MANUTENZIONE E RIPARAZIONE DI AUTOVEICOLI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G453', 'COMMERCIO DI PARTI E ACCESSORI DI AUTOVEICOLI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G454', 'COMMERCIO, MANUTENZIONE E RIPARAZIONE DI MOTOCICLI E RELATIVE PARTI ED ACCESSORI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G461', 'INTERMEDIARI DEL COMMERCIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G462', 'COMMERCIO ALL''INGROSSO DI MATERIE PRIME AGRICOLE E DI ANIMALI VIVI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G463', 'COMMERCIO ALL''INGROSSO DI PRODOTTI ALIMENTARI, BEVANDE E PRODOTTI DEL TABACCO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G464', 'COMMERCIO ALL''INGROSSO DI BENI DI CONSUMO FINALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G465', 'COMMERCIO ALL''INGROSSO DI APPARECCHIATURE ICT', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G466', 'COMMERCIO ALL''INGROSSO DI ALTRI MACCHINARI, ATTREZZATURE E FORNITURE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G467', 'COMMERCIO ALL''INGROSSO SPECIALIZZATO DI ALTRI PRODOTTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G469', 'COMMERCIO ALL''INGROSSO NON SPECIALIZZATO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G471', 'COMMERCIO AL DETTAGLIO IN ESERCIZI NON SPECIALIZZATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G472', 'COMMERCIO AL DETTAGLIO DI PRODOTTI ALIMENTARI, BEVANDE E TABACCO IN ESERCIZI SPECIALIZZATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G473', 'COMMERCIO AL DETTAGLIO DI CARBURANTE PER AUTOTRAZIONE IN ESERCIZI SPECIALIZZATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G474', 'COMMERCIO AL DETTAGLIO DI APPARECCHIATURE INFORMATICHE E PER LE TELECOMUNICAZIONI (ICT) IN ESERCIZI SPECIALIZZATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G475', 'COMMERCIO AL DETTAGLIO DI ALTRI PRODOTTI PER USO DOMESTICO IN ESERCIZI SPECIALIZZATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G476', 'COMMERCIO AL DETTAGLIO DI ARTICOLI CULTURALI E RICREATIVI IN ESERCIZI SPECIALIZZATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G477', 'COMMERCIO AL DETTAGLIO DI ALTRI PRODOTTI IN ESERCIZI SPECIALIZZATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G478', 'COMMERCIO AL DETTAGLIO AMBULANTE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007G479', 'COMMERCIO AL DETTAGLIO AL DI FUORI DI NEGOZI, BANCHI E MERCATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H491', 'TRASPORTO FERROVIARIO DI PASSEGGERI (INTERURBANO)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H492', 'TRASPORTO FERROVIARIO DI MERCI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H493', 'ALTRI TRASPORTI TERRESTRI DI PASSEGGERI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H494', 'TRASPORTO DI MERCI SU STRADA E SERVIZI DI TRASLOCO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H495', 'TRASPORTO MEDIANTE CONDOTTE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H501', 'TRASPORTO MARITTIMO E COSTIERO DI PASSEGGERI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H502', 'TRASPORTO MARITTIMO E COSTIERO DI MERCI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H503', 'TRASPORTO DI PASSEGGERI PER VIE D''ACQUA INTERNE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H504', 'TRASPORTO DI MERCI PER VIE D''ACQUA INTERNE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H511', 'TRASPORTO AEREO DI PASSEGGERI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H512', 'TRASPORTO AEREO DI MERCI E TRASPORTO SPAZIALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H521', 'MAGAZZINAGGIO E CUSTODIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H522', 'ATTIVITA'' DI SUPPORTO AI TRASPORTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H531', 'ATTIVITA'' POSTALI CON OBBLIGO DI SERVIZIO UNIVERSALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007H532', 'ALTRE ATTIVITA'' POSTALI E DI CORRIERE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007I551', 'ALBERGHI E STRUTTURE SIMILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007I552', 'ALLOGGI PER VACANZE E ALTRE STRUTTURE PER BREVI SOGGIORNI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007I553', 'AREE DI CAMPEGGIO E AREE ATTREZZATE PER CAMPER E ROULOTTE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007I559', 'ALTRI ALLOGGI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007I561', 'RISTORANTI E ATTIVITA'' DI RISTORAZIONE MOBILE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007I562', 'FORNITURA DI PASTI PREPARATI (CATERING) E ALTRI SERVIZI DI RISTORAZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007I563', 'BAR E ALTRI ESERCIZI SIMILI SENZA CUCINA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J581', 'EDIZIONE DI LIBRI, PERIODICI ED ALTRE ATTIVITA'' EDITORIALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J582', 'EDIZIONE DI SOFTWARE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J591', 'ATTIVITA'' DI PRODUZIONE, POST-PRODUZIONE E DISTRIBUZIONE CINEMATOGRAFICA, DI VIDEO E DI PROGRAMMI TELEVISIVI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J592', 'ATTIVITA'' DI REGISTRAZIONE SONORA E DI EDITORIA MUSICALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J601', 'TRASMISSIONI RADIOFONICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J602', 'ATTIVITA'' DI PROGRAMMAZIONE E TRASMISSIONI TELEVISIVE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J611', 'TELECOMUNICAZIONI FISSE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J612', 'TELECOMUNICAZIONI MOBILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J613', 'TELECOMUNICAZIONI SATELLITARI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J619', 'ALTRE ATTIVITA'' DI TELECOMUNICAZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J620', 'PRODUZIONE DI SOFTWARE, CONSULENZA INFORMATICA E ATTIVITA'' CONNESSE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J631', 'ELABORAZIONE DEI DATI, HOSTING E ATTIVITA'' CONNESSE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007J639', 'ALTRE ATTIVITA'' DEI SERVIZI D''INFORMAZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K641', 'INTERMEDIAZIONE MONETARIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K642', 'ATTIVITA'' DELLE SOCIETÀ DI PARTECIPAZIONE (HOLDING)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K643', 'SOCIETA'' FIDUCIARIE, FONDI E ALTRE SOCIETÀ SIMILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K649', 'ALTRE ATTIVITA'' DI SERVIZI FINANZIARI (ESCLUSE LE ASSICURAZIONI E I FONDI PENSIONE)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K651', 'ASSICURAZIONI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K652', 'RIASSICURAZIONI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K653', 'FONDI PENSIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K661', 'ATTIVITA'' AUSILIARIE DEI SERVIZI FINANZIARI (ESCLUSE LE ASSICURAZIONI E I FONDI PENSIONE)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K662', 'ATTIVITA'' AUSILIARIE DELLE ASSICURAZIONI E DEI FONDI PENSIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007K663', 'ATTIVITA'' DI GESTIONE DEI FONDI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007L681', 'COMPRAVENDITA DI BENI IMMOBILI EFFETTUATA SU BENI PROPRI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007L682', 'AFFITTO E GESTIONE DI IMMOBILI DI PROPRIETÀ O IN LEASING', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007L683', 'ATTIVITA'' IMMOBILIARI PER CONTO TERZI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M691', 'ATTIVITA'' DEGLI STUDI LEGALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M692', 'CONTABILITÀ, CONTROLLO E REVISIONE CONTABILE, CONSULENZA IN MATERIA FISCALE E DEL LAVORO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M701', 'ATTIVITA'' DI DIREZIONE AZIENDALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M702', 'ATTIVITA'' DI CONSULENZA GESTIONALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M711', 'ATTIVITA'' DEGLI STUDI DI ARCHITETTURA, INGEGNERIA ED ALTRI STUDI TECNICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M712', 'COLLAUDI ED ANALISI TECNICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M721', 'RICERCA E SVILUPPO SPERIMENTALE NEL CAMPO DELLE SCIENZE NATURALI E DELL''INGEGNERIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M722', 'RICERCA E SVILUPPO SPERIMENTALE NEL CAMPO DELLE SCIENZE SOCIALI E UMANISTICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M731', 'PUBBLICITA''', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M732', 'RICERCHE DI MERCATO E SONDAGGI DI OPINIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M741', 'ATTIVITA'' DI DESIGN SPECIALIZZATE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M742', 'ATTIVITA'' FOTOGRAFICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M743', 'TRADUZIONE E INTERPRETARIATO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M749', 'ALTRE ATTIVITA'' PROFESSIONALI, SCIENTIFICHE E TECNICHE NCA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007M750', 'SERVIZI VETERINARI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N771', 'NOLEGGIO DI AUTOVEICOLI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N772', 'NOLEGGIO DI BENI PER USO PERSONALE E PER LA CASA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N773', 'NOLEGGIO DI ALTRE MACCHINE, ATTREZZATURE E BENI MATERIALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N774', 'CONCESSIONE DEI DIRITTI DI SFRUTTAMENTO DI PROPRIETÀ INTELLETTUALE E PRODOTTI SIMILI (ESCLUSE LE OPERE PROTETTE DAL COPYRIGHT)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N781', 'ATTIVITA'' DI AGENZIE DI COLLOCAMENTO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N782', 'ATTIVITA'' DELLE AGENZIE DI LAVORO TEMPORANEO (INTERINALE)', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N783', 'ALTRE ATTIVITA'' DI FORNITURA E GESTIONE DI RISORSE UMANE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N791', 'ATTIVITA'' DELLE AGENZIE DI VIAGGIO E DEI TOUR OPERATOR', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N799', 'ALTRI SERVIZI DI PRENOTAZIONE E ATTIVITA'' CONNESSE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N801', 'SERVIZI DI VIGILANZA PRIVATA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N802', 'SERVIZI CONNESSI AI SISTEMI DI VIGILANZA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N803', 'SERVIZI INVESTIGATIVI PRIVATI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N811', 'SERVIZI INTEGRATI DI GESTIONE AGLI EDIFICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N812', 'ATTIVITA'' DI PULIZIA E DISINFESTAZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N813', 'CURA E MANUTENZIONE DEL PAESAGGIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N821', 'ATTIVITA'' DI SUPPORTO PER LE FUNZIONI D''UFFICIO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N822', 'ATTIVITA'' DEI CALL CENTER', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N823', 'ORGANIZZAZIONE DI CONVEGNI E FIERE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007N829', 'SERVIZI DI SUPPORTO ALLE IMPRESE NCA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007O841', 'AMMINISTRAZIONE PUBBLICA: AMMINISTRAZIONE GENERALE, ECONOMICA E SOCIALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007O842', 'SERVIZI COLLETTIVI DELLE AMMINISTRAZIONI PUBBLICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007O843', 'ASSICURAZIONE SOCIALE OBBLIGATORIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007P851', 'ISTRUZIONE PRESCOLASTICA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007P852', 'ISTRUZIONE PRIMARIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007P853', 'ISTRUZIONE SECONDARIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007P854', 'ISTRUZIONE POST-SECONDARIA UNIVERSITARIA E NON UNIVERSITARIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007P855', 'ALTRI SERVIZI DI ISTRUZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007P856', 'ATTIVITA'' DI SUPPORTO ALL''ISTRUZIONE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q861', 'SERVIZI OSPEDALIERI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q862', 'SERVIZI DEGLI STUDI MEDICI E ODONTOIATRICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q869', 'ALTRI SERVIZI DI ASSISTENZA SANITARIA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q871', 'STRUTTURE DI ASSISTENZA INFERMIERISTICA RESIDENZIALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q872', 'STRUTTURE DI ASSISTENZA RESIDENZIALE PER PERSONE AFFETTE DA RITARDI MENTALI, DISTURBI MENTALI O CHE ABUSANO DI SOSTANZE STUPEFACENTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q873', 'STRUTTURE DI ASSISTENZA RESIDENZIALE PER ANZIANI E DISABILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q879', 'ALTRE STRUTTURE DI ASSISTENZA SOCIALE RESIDENZIALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q881', 'ASSISTENZA SOCIALE NON RESIDENZIALE PER ANZIANI E DISABILI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007Q889', 'ALTRE ATTIVITA'' DI ASSISTENZA SOCIALE NON RESIDENZIALE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007R900', 'ATTIVITA'' CREATIVE, ARTISTICHE E DI INTRATTENIMENTO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007R910', 'ATTIVITA'' DI BIBLIOTECHE, ARCHIVI, MUSEI ED ALTRE ATTIVITA'' CULTURALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007R920', 'ATTIVITA'' RIGUARDANTI LE LOTTERIE, LE SCOMMESSE, LE CASE DA GIOCO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007R931', 'ATTIVITA'' SPORTIVE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007R932', 'ATTIVITA'' RICREATIVE E DI DIVERTIMENTO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007S941', 'ATTIVITA'' DI ORGANIZZAZIONI ECONOMICHE, DI DATORI DI LAVORO E PROFESSIONALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007S942', 'ATTIVITA'' DEI SINDACATI DI LAVORATORI DIPENDENTI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007S949', 'ATTIVITA'' DI ALTRE ORGANIZZAZIONI ASSOCIATIVE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007S951', 'RIPARAZIONE DI COMPUTER E DI APPARECCHIATURE PER LE COMUNICAZIONI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007S952', 'RIPARAZIONE DI BENI PER USO PERSONALE E PER LA CASA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007S960', 'ALTRE ATTIVITA'' DI SERVIZI PER LA PERSONA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007T970', 'ATTIVITA'' DI FAMIGLIE E CONVIVENZE COME DATORI DI LAVORO PER PERSONALE DOMESTICO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007T981', 'PRODUZIONE DI BENI INDIFFERENZIATI PER USO PROPRIO DA PARTE DI FAMIGLIE E CONVIVENZE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007T982', 'PRODUZIONE DI SERVIZI INDIFFERENZIATI PER USO PROPRIO DA PARTE DI FAMIGLIE E CONVIVENZE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007U990', 'ORGANIZZAZIONI ED ORGANISMI EXTRATERRITORIALI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C212', 'FABBRICAZIONE DI MEDICINALI E PREPARATI FARMACEUTICI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C221', 'FABBRICAZIONE DI ARTICOLI IN GOMMA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C222', 'FABBRICAZIONE DI ARTICOLI IN MATERIE PLASTICHE', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C231', 'FABBRICAZIONE DI VETRO E DI PRODOTTI IN VETRO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C232', 'FABBRICAZIONE DI PRODOTTI REFRATTARI', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C233', 'FABBRICAZIONE DI MATERIALI DA COSTRUZIONE IN TERRACOTTA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C234', 'FABBRICAZIONE DI ALTRI PRODOTTI IN PORCELLANA E IN CERAMICA', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C235', 'PRODUZIONE DI CEMENTO, CALCE E GESSO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C236', 'FABBRICAZIONE DI PRODOTTI IN CALCESTRUZZO, CEMENTO E GESSO', NULL, NULL);
		INSERT INTO tab5 (tab5cod, tab5tip, tab5desc, tab5mod, tab5nord) VALUES ('G_j07', '2007C237', 'TAGLIO, MODELLATURA E FINITURA DI PIETRE', NULL, NULL);		
		
		
		--Aggiornamento C0CAMPI
		-- Inserimento in C0CAMPI nuovi campi in IMPR
		DELETE FROM C0CAMPI where C0C_MNE_BER in ('G_CLADIM','G_ISCRIWL','G_WLPREFE','G_WLSEZIO','G_WLDISCRI','G_WLDSCAD','G_WLINCORSO');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0, 'E', NULL, 'CLADIM.IMPR.GENE', 'G_CLADIM', 'Classe di dimensione dell''impresa', 'Classe di dimensione', 'A100', 'G_062', NULL, NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0, 'E', NULL, 'ISCRIWL.IMPR.GENE', 'G_ISCRIWL', 'Iscritto nella white list antimafia (dpcm 18 aprile 2013)?', 'Iscr.white list?', 'A2', NULL, 'SN', 'Iscritto nella white list?');		
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0, 'E', NULL, 'WLPREFE.IMPR.GENE', 'G_WLPREFE', 'Sede prefettura presso cui è istituita la white list antim.', 'Prefett. white list', 'A50', NULL, NULL, 'Sede prefettura competente');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0, 'E', NULL, 'WLSEZIO.IMPR.GENE', 'G_WLSEZIO', 'Sezioni di iscrizione white list antimafia', 'Sez.iscr.white list', 'A20', NULL, NULL, 'Sezioni di iscrizione');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0, 'E', NULL, 'WLDISCRI.IMPR.GENE', 'G_WLDISCRI', 'Data iscrizione white list antimafia', 'Data iscr.white list', 'A10', NULL, 'DATA_ELDA', 'Data iscrizione');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0, 'E', NULL, 'WLDSCAD.IMPR.GENE', 'G_WLDSCAD', 'Data scadenza iscrizione white list antimafia', 'Data scad.iscr.wh.li', 'A10', NULL, 'DATA_ELDA', 'Data scadenza iscrizione');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0, 'E', NULL, 'WLINCORSO.IMPR.GENE', 'G_WLINCORSO', 'Aggiornamento in corso white list antimafia?', 'Agg.in corso wh.li?', 'A2', NULL, 'SN', 'Aggiornamento in corso?');		
		
		--Reso non visibile SETATT.IMPR	
		UPDATE C0CAMPI SET C0C_TIP='C' WHERE  C0C_MNE_BER = 'SETATT';	
		--CODATT.IMPR cambiata descrizione e dimensione
		UPDATE C0CAMPI SET COC_DES='Settore attività economica (codice ATECO 2007)',COC_DES_FRM='Settore attiv.econ.',C0C_FS='A240',C0C_TAB1='G_j07',COC_DES_WEB='Settore attività economica' WHERE  C0C_MNE_BER = 'CODATT';
		
		--Aggiornamento campi SN da A1 a A2	
		UPDATE C0CAMPI SET C0C_FS='A2' WHERE  C0C_MNE_BER in ('G_ASSOBBL','G_ISMPMI','G_ISCRCCIAA');
		
		--Nuovi campi entità DURC 
		DELETE FROM C0CAMPI where C0C_MNE_BER in ('G_IDDURC','G_CODIMPDU','G_NPROTODU','G_DATRICDU','G_DATSCADU','G_TIPESIDU','G_NOTDUR');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', 'P', 'ID.IMPDURC.GENE', 'G_IDDURC', 'Id', 'Id', 'N12', NULL, NULL, NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', NULL, 'CODIMP.IMPDURC.GENE', 'G_CODIMPDU', 'Codice impresa', 'Codice impresa', 'A10', NULL, NULL, NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', NULL, 'NPROTO.IMPDURC.GENE', 'G_NPROTODU', 'Numero protocollo', 'N. protocollo', 'A20', NULL, NULL, 'Numero protocollo');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', NULL, 'DATRIC.IMPDURC.GENE', 'G_DATRICDU', 'Data di richiesta del DURC', 'Data richiesta', 'A10', NULL, 'DATA_ELDA',NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', NULL, 'DATSCA.IMPDURC.GENE', 'G_DATSCADU', 'Data scadenza validità del DURC', 'Data scadenza', 'A10', NULL, 'DATA_ELDA','Data scadenza validità');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', NULL, 'TIPESI.IMPDURC.GENE', 'G_TIPESIDU', 'Tipo di esito (regolare, irregolare)', 'Tipo esito', 'A100', 'G_060', NULL, NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', NULL, 'NOTDUR.IMPDURC.GENE', 'G_NOTDUR', 'Eventuali note', 'Note', 'A2000', NULL, 'NOTE', NULL);
		
		--Nuova view V_IMPR_VERIFICA
		DELETE FROM C0CAMPI where C0C_MNE_BER in ('G_CODIMP_V','G_DURC_REG','G_WL_REG');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', 'P', 'CODIMP.V_IMPR_VERIFICA.GENE', 'G_CODIMP_V', 'Codice impresa', 'Codice impresa', 'A10', NULL, NULL, NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', NULL, 'DURC_REG.V_IMPR_VERIFICA.GENE', 'G_DURC_REG', 'DURC regolare o non soggetto a obblighi DURC?', 'DURC regolare?', 'A2', NULL, 'SN', NULL);
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0, 'E', NULL, 'WL_REG.V_IMPR_VERIFICA.GENE', 'G_WL_REG', 'Iscrizione alla white list antimafia valida?', 'Iscr.wh.list valida?', 'A2', NULL, 'SN','Iscrizione white list?');
		
		
		--Aggiornamento C0ENTIT 
		----Nuova entità DURC 
		DELETE FROM C0ENTIT where C0E_NOM in ('IMPDURC.GENE');
		INSERT INTO C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('IMPDURC.GENE', 0, 'E', 'IMP_DURC', 'DURC on line emessi per l''impresa', 'CODIMP.IMPDURC.GENE', 'IMPR.GENE', 'CODIMP.IMPR.GENE', NULL, NULL, 'g_idurc0');
		
		---Nuova view V_IMPR_VERIFICA 
		delete from c0entit where C0E_NOM in ('V_IMPR_VERIFICA.GENE');
		INSERT INTO C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('V_IMPR_VERIFICA.GENE', 0, 'E', 'IMP_VERIFI', 'Dati di sintesi anagrafica impresa a scopo di verifiche', 'CODIMP.V_IMPR_VERIFICA.GENE', 'IMPR.GENE', 'CODIMP.IMPR.GENE', NULL, NULL, 'g_veri0');
		
		-- Modifica CAP comune di Rimini
		UPDATE TABSCHE set TABCOD4='47921' where TABCOD='S2003' and TABCOD1='09' and TABCOD2='8355';
		
		-- Corretta definizione dei campi chiave per entità UNIMIS nel c0campi: Non risultavano definiti
		UPDATE C0CAMPI set C0C_CHI='P' where COC_MNE_UNI='CONTA.UNIMIS.GENE';
		UPDATE C0CAMPI set C0C_CHI='P' where COC_MNE_UNI='TIPO.UNIMIS.GENE';
		
		UPDATE ELDAVER SET NUMVER = '1.4.85', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------------------
--- Aggiornamento GENE da 1.4.85 a 1.5.0 ---
--------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.4.85' or numver like '1.4.85.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------
		
		--Inserisce la riga relativa a IMPLEG (solamente se non è già esistente)
		if (select count(1) from w_genchiavi where tabella = 'IMPLEG') <= 0 
		then
			INSERT INTO w_genchiavi (tabella, chiave) VALUES ('IMPLEG', 0);
		end if;
		
		--Inserisce la riga relativa a IMPDTE (solamente se non è già esistente)
		if (select count(1) from w_genchiavi where tabella = 'IMPDTE') <= 0
		then
			INSERT INTO w_genchiavi (tabella, chiave) VALUES ('IMPDTE', 0);
		end if;
		
		UPDATE TAB1 set TAB1ARC='1' WHERE TAB1COD='G_044' AND TAB1TIP=37;
		
		UPDATE ELDAVER SET NUMVER = '1.5.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.5.0 a 1.5.1 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.5.0' or numver like '1.5.0.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------

		-- definizione tabella storico utenti rimossi
		SET v_sql = 'CREATE TABLE USRCANC (
				ID NUMERIC(12) not null,
				SYSCON NUMERIC(12) not null,
				SYSLOGIN VARCHAR(60) not null,
				SYSSCAD TIMESTAMP not null,
				primary key (ID))';
		EXECUTE IMMEDIATE v_sql;
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------

		-- Modifica dell'eventuale utente denominato ADMIN (diventa AdminOLD)
		update stoutesys set sysnom='!DDYz<dw8', syslogin='AdminOLD' where syscon=(select syscon from usrsys where upper(syslogin) = 'ADMIN' and syscon>50);
		update usrsys set sysute = 'Admin (OLD)', sysnom = '!DDYz<dw8', syslogin = 'AdminOLD' where upper(syslogin) = 'ADMIN' and syscon>50;
		
		-- Modifica dell'eventuale utente denominato EnteAdmin (diventa EnteAdminOLD)
		update stoutesys set sysnom='!DDYz<dw8E/z:', syslogin='EnteAdminOLD' where syscon=(select syscon from usrsys where upper(syslogin) = 'ENTEADMIN' and syscon>50);
		update usrsys set sysute = 'EnteAdmin (OLD)', sysnom = '!DDYz<dw8E/z:', syslogin = 'EnteAdminOLD' where upper(syslogin) = 'ENTEADMIN' and syscon>50;
		
		-- Utenti di default precaricati in USRSYS: Inserimento nuovo utente "48 - ADMIN" (include "ou89", non include "ou39" e SYSDISAB=NULL)
		if (select count(1) from usrsys where syscon=48) <= 0
		then
			INSERT INTO usrsys (syscon, sysute, sysnom, syspwd, sysdat, sysab1, sysab2, sysab3, sysliv, syspwbou, codimp, impimp, sysabg, syslig, sysabc, syslic, sysabu, codtec, abilap, syspri, codtei, uducla1, flag_ldap, dn, syscongrp, sysdisab, email, sysscad, sysuffapp, emailpec, meruolo, syscateg, syscf, syslogin) VALUES (48, 'ADMIN', '!G_)Li', '!r<m% 98Hls&A&S)', NULL, NULL, NULL, 'A', 0, 'ou8|ou11|ou91|ou1|ou43|ou13|ou48|ou50|ou56|ou59|ou62|ou89|', NULL, 0, 'A', 0, 'NDEFM', 0, '$'||'$'||'22', NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ADMIN');
		end if;
		
		-- Utenti di default precaricati in USRSYS: Inserimento nuovo utente "49 - EnteAdmin" (include "ou89", include "ou39" e SYSDISAB='1')
		if (select count(1) from usrsys where syscon=49) <= 0
		then
			INSERT INTO usrsys (syscon, sysute, sysnom, syspwd, sysdat, sysab1, sysab2, sysab3, sysliv, syspwbou, codimp, impimp, sysabg, syslig, sysabc, syslic, sysabu, codtec, abilap, syspri, codtei, uducla1, flag_ldap, dn, syscongrp, sysdisab, email, sysscad, sysuffapp, emailpec, meruolo, syscateg, syscf, syslogin) VALUES (49, 'EnteAdmin', '!z,Wmic/z=', '!0l]wQfTlNb', NULL, NULL, NULL, 'A', 0, 'ou8|ou11|ou91|ou1|ou43|ou13|ou39|ou48|ou50|ou56|ou59|ou62|ou89|', NULL, 0, 'A', 0, 'NDEFM', 0, '$'||'$'||'22', NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'EnteAdmin');
		end if;
		
		-- Utenti di default precaricati in USRSYS:  Modifica vecchio utente "50 - MANAGER" (adesso non include "ou89" ed include "ou39")
		UPDATE usrsys SET syspwbou=REPLACE(syspwbou,'ou89|','') where SYSCON=50;
		UPDATE usrsys set syspwbou=syspwbou||'ou39|' where SYSCON=50 and syspwbou not like '%ou39|%';
		UPDATE usrsys set SYSUTE='MANAGER', SYSLOGIN='MANAGER' where SYSCON=50 and SYSUTE='Super Utente';
		
		-- Modifica del dato tabellato relativo a "Giorni di validità password (sicurezza)" impostando "90" giorni qualora sia superiore
		UPDATE TAB1 SET TAB1DESC = '90' where TAB1COD='G_048' and TAB1TIP=1 and CAST(TAB1DESC AS INT)>90;
		
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('USRCANC.GENE', 6800111, 'E', 'USRCANC', 'Storico utenti amministratori rimossi', 'ID.USRCANC.GENE', NULL, NULL, NULL, NULL, 'g_usrca0');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (048001300, 'E', 'P', 'ID.USRCANC.GENE', 'USCAID', 'Id registrazione', 'Id', 'N12', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (048001301, 'E', NULL, 'SYSCON.USRCANC.GENE', 'USCASYSCON', 'Codice utente', 'Codice utente', 'N12', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (048001302, 'E', NULL, 'SYSLOGIN.USRCANC.GENE', 'USCASYLOGIN', 'Login', 'Login', 'A60', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (048001303, 'E', NULL, 'SYSDATCANC.USRCANC.GENE ', 'USCADATCANC', 'Data eliminazione', 'Data eliminazione', 'A10', NULL, 'DATA_ELDA', NULL);
		INSERT INTO w_genchiavi (tabella, chiave) VALUES ('USRCANC',0);

		-- Aggiunta nuovi comuni
		Delete from tabsche where tabcod='S2003' and tabcod1='09' and tabcod3 in ('008033049', '006030190', '006030191', '007008068', '003013254', '003097093', '003098062', '003020072', '001006192', '001006191', '001002170', '001002171', '009051042', '009049021', '004022250','005024124','005028107');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8624', 'ALTA VAL TIDONE', '008033049', '29010', 'M386');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8625', 'FIUMICELLO VILLA VICENTINA', '006030190', '33050', 'M399');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8626', 'TREPPO LIGOSULLO', '006030191', '33020', 'M400');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8627', 'MONTALTO CARPASIO', '007008068', '18010', 'M387');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8628', 'CENTRO VALLE INTELVI', '003013254', '22028', 'M394');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8629', 'VALVARRONE', '003097093', '23836', 'M395');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8630', 'CASTELGERUNDO', '003098062', '26823', 'M393');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8631', 'BORGO MANTOVANO', '003020072', '46036', 'M396');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8632', 'ALLUVIONI PIOVERA', '001006192', '15040', 'M397');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8633', 'CASSANO SPINOLA', '001006191', '15063', 'M388');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8634', 'ALTO SERMENZA', '001002170', '13026', 'M389');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8635', 'CELLIO CON BREIA', '001002171', '13024', 'M398');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8636', 'LATERINA PERGINE VALDARNO', '009051042', '52020', 'M392');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8637', 'RIO', '009049021', '57039', 'M391');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8638', 'SEN JAN DI FASSA', '004022250', '38036', 'M390');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8639', 'BARBARANO MOSSANO', '005024124', '36021', 'M401');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8640', 'BORGO VENETO', '005028107', '35046', 'M402');
		
		-- delete G_022 rimasti con il valore di default delle precedenti installazioni
		delete from tab1 where tab1cod='G_022' and tab1tip =1 and tab1desc='Uffico appartenenza  A';
		delete from tab1 where tab1cod='G_022' and tab1tip =2 and tab1desc='Ufficio appartenenza B';
		delete from tab1 where tab1cod='G_022' and tab1tip =3 and tab1desc='Ufficio appartenenza C';
		delete from tab1 where tab1cod='G_022' and tab1tip =4 and tab1desc='Ufficio appartenenza D';
		delete from tab1 where tab1cod='G_022' and tab1tip =5 and tab1desc='Ufficio appartenenza E';
		delete from tab1 where tab1cod='G_022' and tab1tip =6 and tab1desc='Ufficio appartenenza F';
		
		UPDATE ELDAVER SET NUMVER = '1.5.1', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.5.1 a 1.5.2 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.5.1' or numver like '1.5.1.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------

		-- delete G_059 rimasti al valore di default delle precedenti installazioni
		delete from tab1 where tab1cod='G_059' and tab1tip =1 and tab1desc='Categoria 1';
		delete from tab1 where tab1cod='G_059' and tab1tip =2 and tab1desc='Categoria 2';
	
		-- Join di TECNI ed IMPR con tabelle di SITAT e Programmi triennali
		Delete from c0entit where c0e_nom in ('TECNI.GENE#28','TECNI.GENE#29','TECNI.GENE#30','TECNI.GENE#31','TECNI.GENE#32','IMPR.GENE#33','IMPR.GENE#34','IMPR.GENE#35');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('TECNI.GENE#28', 0, 'C', NULL, 'Archivio Tecnici Esterni', 'CODTEC.TECNI.GENE', 'W9INCA.W9', 'CODTEC.W9INCA.W9', NULL, NULL, 'g_tece0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('TECNI.GENE#29', 0, 'C', NULL, 'Archivio Tecnici Esterni', 'CODTEC.TECNI.GENE', 'W9GARA.W9', 'RUP.W9GARA.W9INCA.W9', NULL, NULL, 'g_tece0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('TECNI.GENE#30', 0, 'C', NULL, 'Archivio Tecnici Esterni', 'CODTEC.TECNI.GENE', 'W9LOTT.W9', 'RUP.W9LOTT.W9INCA.W9', NULL, NULL, 'g_tece0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('TECNI.GENE#31', 0, 'C', NULL, 'Archivio Tecnici Esterni', 'CODTEC.TECNI.GENE', 'PIATRI.PIANI', 'RESPRO.PIATRI.PIANI', NULL, NULL, 'g_tece0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('TECNI.GENE#32', 0, 'C', NULL, 'Archivio Tecnici Esterni', 'CODTEC.TECNI.GENE', 'INTTRI.PIANI', 'CODRUP.INTTRI.PIANI', NULL, NULL, 'g_tece0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('IMPR.GENE#33', 0, 'C', NULL, 'Anagrafico delle IMPRESE', 'CODIMP.IMPR.GENE', 'W9AGGI.W9', 'CODIMP_AUSILIARIA.W9AGGI.W9', NULL, NULL, 'g_impr0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('IMPR.GENE#34', 0, 'C', NULL, 'Anagrafico delle IMPRESE', 'CODIMP.IMPR.GENE', 'W9IMPRESE.W9', 'CODIMP.W9IMPRESE.W9', NULL, NULL, 'g_impr0');
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('IMPR.GENE#35', 0, 'C', NULL, 'Anagrafico delle IMPRESE', 'CODIMP.IMPR.GENE', 'W9SUBA.W9', 'CODIMP.W9SUBA.W9', NULL, NULL, 'g_impr0');
		
		UPDATE ELDAVER SET NUMVER = '1.5.2', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.5.2 a 1.5.3 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.5.2' or numver like '1.5.2.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------
		
				-- Nuovi campi IMPR per ART.80
		SET v_sql = 'ALTER TABLE IMPR ADD COLUMN ART80_STATO NUMERIC(7)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD COLUMN ART80_DATA_RICHIESTA TIMESTAMP';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD COLUMN ART80_DATA_LETTURA TIMESTAMP';
		EXECUTE IMMEDIATE v_sql;
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------

		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'ART80_STATO.IMPR.GENE', 'G_A80STATO', 'Stato documentale', 'Stato documentale', 'A100', 'G_063', NULL, 'Stato documentale');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'ART80_DATA_RICHIESTA.IMPR.GENE', 'G_A80DRICH', 'Data invio anagrafica', 'Data invio', 'A10', NULL, 'DATA_ELDA', 'Data invio anagrafica');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'ART80_DATA_LETTURA.IMPR.GENE', 'G_A80DLETT', 'Data ultima lettura stato', 'Data lett. stato', 'A10', NULL, 'DATA_ELDA', 'Data ultima lettura stato');		
		
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_063', 1, 'In lavorazione', NULL, 1);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_063', 2, 'Non anomalo', NULL, 2);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_063', -1, 'Anomalo', NULL, 3);
		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_063', 10, 'Anagrafica inviata', NULL, 10);
		
		UPDATE ELDAVER SET NUMVER = '1.5.3', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.5.3 a 1.5.4 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.5.3' or numver like '1.5.3.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------

		-- Nuovo campo di IMPR per codice BIC
		SET v_sql = 'ALTER TABLE IMPR ADD CODBIC VARCHAR(11)';
		EXECUTE IMMEDIATE v_sql;

		SET v_sql = 'ALTER TABLE TABNUTS ADD SIGLAPROV VARCHAR(5)';
		EXECUTE IMMEDIATE v_sql;
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------

		--Aggiornamento del campo TABNUTS.SIGLAPROV con i valori di TAB3COD=Agx15
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=(select TAB3TIP from TAB3 where tab3cod=''Agx15'' and upper(TAB3DESC)=upper(DESCRIZIONE))';
		EXECUTE IMMEDIATE v_sql;
		
		--Si deve procedere all'aggiornamento manuale di alcuni campi per cui non c'è corrispondenza fra le descrizioni nelle due tabelle
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''VB'' where codice=''ITC14''';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''AO'' where codice=''ITC20''';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''RC'' where codice=''ITF65''';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''OT'' where codice=''ITG29''';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''CI'' where codice=''ITG2C''';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''BZ'' where codice=''ITH10''';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''RE'' where codice=''ITH53''';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''FO'' where codice=''ITH58''';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'UPDATE TABNUTS set SIGLAPROV=''MS'' where codice=''ITI11''';
		EXECUTE IMMEDIATE v_sql;

		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'CODBIC.IMPR.GENE', 'G_CODBIC', 'Conto corrente dedicato: codice BIC (o SWIFT)', 'Coord.banc.:cod.BIC', 'A11', NULL, NULL, 'Conto corrente dedicato:codice BIC');
		UPDATE c0campi set  coc_des='Conto corrente dedicato: codice IBAN', coc_des_frm='Coord.banc.:cod.IBAN', coc_des_web='Conto corrente dedicato:codice IBAN' where c0c_mne_ber='G_COORBA';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'P', NULL, 'SIGLAPROV.TABNUTS.GENE', 'NUTSPROVSI', 'Sigla della provincia', 'Sigla provincia', 'A5', NULL, NULL, NULL);
		
		UPDATE ELDAVER SET NUMVER = '1.5.4', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.5.4 a 1.5.5 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.5.4' or numver like '1.5.4.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------

		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------

		Update C0CAMPI set c0c_tip='E' where coc_mne_uni in ('CGENTEI.TECNI.GENE','CGENUTE.UTENT.GENE','CGENIMP.IMPR.GENE','CGENTIM.TEIM.GENE');

		if (select count(*) from tabsche where tabcod='S2003' and tabcod1='09' and tabcod2='8641')=0 
		then
			INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8641', 'CASALI DEL MANCO', '018078156', '87050', 'M385');
		end if;
		
		UPDATE ELDAVER SET NUMVER = '1.5.5', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.5.5 a 1.6.0 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.5.5' or numver like '1.5.5.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------

		SET v_sql = 'ALTER TABLE UFFINT ADD CODIPA VARCHAR(20)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD COGNOME VARCHAR(80)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE IMPR ADD NOME VARCHAR(80)';
		EXECUTE IMMEDIATE v_sql;
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------
                
		--Aggiornamento C0CAMPI
        INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'CODIPA.UFFINT.GENE', 'G_CODIPA', 'Codice iPA (indice delle Pubbliche Amministrazioni)', 'Codice iPA', 'A20', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'COGNOME.IMPR.GENE', 'G_COGNIMPR', 'Cognome dell''impresa', 'Cognome', 'A80', NULL, NULL, 'Cognome');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'NOME.IMPR.GENE', 'G_NOMIMPR', 'Nome dell''impresa', 'Nome', 'A80', NULL, NULL, 'Nome');
		
		if (select count(1) from c0entit where c0e_nom='UFFINT.GENE#6' and c0e_key='CODEIN.UFFINT.GENE' and c0e_nom_ex='PIATRI.PIANI' and coe_key_ex='CENINT.PIATRI.PIANI')<=0
		then
			INSERT INTO c0entit (c0e_nom,c0e_num,c0e_tip,coe_arg,c0e_des,c0e_key,c0e_nom_ex,coe_key_ex,c0e_frm_ri,coe_frm_ca,coe_frm_re) VALUES ('UFFINT.GENE#6',88020110,'C',null,null,'CODEIN.UFFINT.GENE','PIATRI.PIANI','CENINT.PIATRI.PIANI',null,null,'g_10uffi');
		end if;
		
		UPDATE ELDAVER SET NUMVER = '1.6.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.6.0 a 1.7.0 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.6.0' or numver like '1.6.0.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------
        
		UPDATE tab1 set tab1nord=3 where tab1cod='Ag008' and tab1tip=2;
		UPDATE tab1 set tab1nord=4 where tab1cod='Ag008' and tab1tip=3;
		UPDATE tab1 set tab1nord=6 where tab1cod='Ag008' and tab1tip=5;
		UPDATE tab1 set tab1nord=7 where tab1cod='Ag008' and tab1tip=6;
		UPDATE tab1 set tab1nord=9 where tab1cod='Ag008' and tab1tip=7;
		UPDATE tab1 set tab1nord=10 where tab1cod='Ag008' and tab1tip=8;
		UPDATE tab1 set tab1nord=11 where tab1cod='Ag008' and tab1tip=9;
		UPDATE tab1 set tab1nord=12 where tab1cod='Ag008' and tab1tip=10;
		UPDATE tab1 set tab1nord=13 where tab1cod='Ag008' and tab1tip=11;
		UPDATE tab1 set tab1nord=8 where tab1cod='Ag008' and tab1tip=12;

		INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('G_045', 2, '1   obbligatoria per Impresa sociale(0) non obbligatoria(1)', NULL, 0);
		UPDATE tab1 SET tab1desc=substr(tab1desc,1,1)||'   obbligatoria per Libero professionista(0) non obbligatoria(1)' where tab1cod='G_045' and tab1tip=1;
		UPDATE tab4 SET tab4desc='Partita IVA obbligatoria per tipologia impresa' WHERE tab4cod='G__1' and tab4tip='G_045';
		
		UPDATE ELDAVER SET NUMVER = '1.7.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.7.0 a 1.8.0 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.7.0' or numver like '1.7.0.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------

		-- aumento campo DN a 2000 caratteri
		SET v_sql = 'ALTER TABLE USRSYS ALTER COLUMN DN set DATA TYPE VARCHAR(2000)';
		EXECUTE IMMEDIATE v_sql;
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------
		
		UPDATE ELDAVER SET NUMVER = '1.8.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.8.0 a 1.9.0 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.8.0' or numver like '1.8.0.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------

		-- esteso campo CODFE a 7 caratteri
		SET v_sql = 'ALTER TABLE UFFINT ALTER COLUMN CODFE set DATA TYPE varchar(7)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE PUNTICON ALTER COLUMN CODFE set DATA TYPE varchar(7)';
		EXECUTE IMMEDIATE v_sql;
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------
                
		UPDATE C0CAMPI set COC_DES='Codice Univoco Ufficio o cod.destinat. per fatturaz.elettr.',C0C_FS='A7',COC_DES_WEB='Codice Univoco Ufficio o codice destinatario per fatturazione elettronica' where C0C_MNE_BER = 'G_CODFE';
		UPDATE C0CAMPI set COC_DES='Codice Univoco Ufficio o cod.destinat. per fatturaz.elettr.',C0C_FS='A7',COC_DES_WEB='Codice Univoco Ufficio o codice destinatario per fatturazione elettronica' where C0C_MNE_BER = 'G_CODFEPC';

		--Forzato inserimento voci vers.1.6.0 erroneamente non inserite in inizializzazione
		delete from c0campi where c0c_mne_ber in ('G_COGNIMPR','G_NOMIMPR');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'COGNOME.IMPR.GENE', 'G_COGNIMPR', 'Cognome dell''impresa', 'Cognome', 'A80', NULL, NULL, 'Cognome');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'NOME.IMPR.GENE', 'G_NOMIMPR', 'Nome dell''impresa', 'Nome', 'A80', NULL, NULL, 'Nome');
		
		UPDATE ELDAVER SET NUMVER = '1.9.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.9.0 a 1.10.0 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.9.0' or numver like '1.9.0.%'))=1
	THEN
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA STRUTTURA TABELLE             ------------------------
		------------------------------------------------------------------------------------------------
		
		------------------------------------------------------------------------------------------------
		------------------------         MODIFICA DATI                           -----------------------
		------------------------------------------------------------------------------------------------
                
		Delete from TABSCHE where tabcod='S2003' and tabcod1='09' and tabcod2 in ('8642');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8642', 'CORIGLIANO-ROSSANO', '018078157', '87064', 'M403');

		-- fix errori sulle descrizioni introdotte con l'aggiornamento alla 1.4.84 causa codifica UTF8 del file di aggiornamento
		DELETE FROM TABNUTS WHERE CODICE IN ('ITC2', 'ITC20', 'ITF11', 'ITH53', 'ITH58');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE,SIGLAPROV) VALUES ('ITC2','IT','C','2',NULL,'Valle d''Aosta / Vallée d''Aoste',NULL);
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE,SIGLAPROV) VALUES ('ITC20','IT','C','2','0','Valle d''Aosta / Vallée d''Aoste','AO');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE,SIGLAPROV) VALUES ('ITF11','IT','F','1','1','L''Aquila','AQ');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE,SIGLAPROV) VALUES ('ITH53','IT','H','5','3','Reggio nell''Emilia','RE');
		INSERT INTO TABNUTS (CODICE,PAESE,AREA,REGIONE,PROVINCIA,DESCRIZIONE,SIGLAPROV) VALUES ('ITH58','IT','H','5','8','Forlì-Cesena','FO');

		Delete from tabsche where tabcod='S2003' and tabcod1='09' and tabcod3 in ('001103079','001001317','003020073','004022251','009048054','008038030','008038029','008034051','003013255','011041071','001096087','001001318','001003166','001096088','003019116','003018193');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8643', 'VALLE CANNOBINA', '001103079', '28825', 'M404');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8644', 'VAL DI CHY', '001001317', '10010', 'M405');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8645', 'BORGOCARBONARA  ', '003020073', '46020', 'M406');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8646', 'TERRE D''ADIGE', '004022251', '38010', 'M407');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8647', 'BARBERINO TAVARNELLE', '009048054', '50028', 'M408');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8648', 'TRESIGNANA', '008038030', '44035', 'M409');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8649', 'RIVA DEL PO', '008038029', '44030', 'M410');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8650', 'SORBOLO MEZZANI', '008034051', '43058', 'M411');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8651', 'SOLBIATE CON CAGNO', '003013255', '22070', 'M412');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8652', 'SASSOCORVARO AUDITORE', '011041071', '61028', 'M413');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8653', 'QUAREGNA CERRETO ', '001096087', '13854', 'M414');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8654', 'VALCHIUSA', '001001318', '10080', 'M415');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8655', 'GATTICO-VERUNO', '001003166', '28013', 'M416');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8656', 'VALDILANA  ', '001096088', '13825', 'M417');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8657', 'PIADENA DRIZZONA', '003019116', '26034', 'M418');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8658', 'COLLI VERDI', '003018193', '27040', 'M419');
		
		UPDATE ELDAVER SET NUMVER = '1.10.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

-------------------------------------------
--- Aggiornamento GENE da 1.10.0 a 1.11.0 ---
-------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.10.0' or numver like '1.10.0.%'))=1
	THEN
		
		--Aggiunge o aggiorna nuovi comuni 
		Delete from tabsche where tabcod='S2003' and tabcod1='09' and tabcod3 in ('003020061','003020057','003015251','003012143','001006193','005025074','005026096','005024125');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '3055', 'SERMIDE E FELONICA', '003020061', '46028', 'I632');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '3051', 'SAN GIORGIO BIGARELLO', '003020057', '46030', 'H883');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8659', 'VERMEZZO CON ZELO', '003015251', '20080', 'M424');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8660', 'CADREZZATE CON OSMATE', '003012143', '21020', null);
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8661', 'LU E CUCCARO MONFERRATO', '001006193', '15040', 'M420');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8662', 'BORGO VALBELLUNA', '005025074', '32026', 'M421');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8663', 'PIEVE DEL GRAPPA', '005026096', '31017', 'M422');
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '09', '8664', 'VALBRENTA', '005024125', '36020', 'M423');
		
		Update tabsche set tabcod5='D066',tabdesc='CORTENUOVA' where tabcod='S2003' and tabcod1='09' and tabcod3='003016083';
		Update tabsche set tabcod5='D065',tabdesc='CORTENOVA' where tabcod='S2003' and tabcod1='09' and tabcod3='003097025';
		Update tabsche set tabcod5='G416',tabdesc='PEGLIO (PU)' where tabcod='S2003' and tabcod1='09' and tabcod3='011041041';
		Update tabsche set tabcod5='A165' where tabcod='S2003' and tabcod1='09' and tabcod3='007009003';
		Update tabsche set tabcod5='A581' where tabcod='S2003' and tabcod1='09' and tabcod3='007008007';
		Update tabsche set tabcod5='A618' where tabcod='S2003' and tabcod1='09' and tabcod3='003015250';
		Update tabsche set tabcod5='A710' where tabcod='S2003' and tabcod1='09' and tabcod3='010054002';
		Update tabsche set tabcod5='B184' where tabcod='S2003' and tabcod1='09' and tabcod3='003017030';
		Update tabsche set tabcod5='B185' where tabcod='S2003' and tabcod1='09' and tabcod3='004022028';
		Update tabsche set tabcod5='B259' where tabcod='S2003' and tabcod1='09' and tabcod3='006030013';
		Update tabsche set tabcod5='B385' where tabcod='S2003' and tabcod1='09' and tabcod3='019081003';
		Update tabsche set tabcod5='B418' where tabcod='S2003' and tabcod1='09' and tabcod3='001005014';
		Update tabsche set tabcod5='B419' where tabcod='S2003' and tabcod1='09' and tabcod3='004022035';
		Update tabsche set tabcod5='M311' where tabcod='S2003' and tabcod1='09' and tabcod3='006030138';
		Update tabsche set tabcod5='C337' where tabcod='S2003' and tabcod1='09' and tabcod3='003016065';
		Update tabsche set tabcod5='M261' where tabcod='S2003' and tabcod1='09' and tabcod3='016075096';
		Update tabsche set tabcod5='M308' where tabcod='S2003' and tabcod1='09' and tabcod3='005027044';
		Update tabsche set tabcod5='E623' where tabcod='S2003' and tabcod1='09' and tabcod3='003013130';
		Update tabsche set tabcod5='E624' where tabcod='S2003' and tabcod1='09' and tabcod3='004022106';
		Update tabsche set tabcod5='F910' where tabcod='S2003' and tabcod1='09' and tabcod3='018079087';
		Update tabsche set tabcod5='G232' where tabcod='S2003' and tabcod1='09' and tabcod3='012057048';
		Update tabsche set tabcod5='G415' where tabcod='S2003' and tabcod1='09' and tabcod3='003013178';
		Update tabsche set tabcod5='H224' where tabcod='S2003' and tabcod1='09' and tabcod3='018080063';
		Update tabsche set tabcod5='H223' where tabcod='S2003' and tabcod1='09' and tabcod3='008035033';
		Update tabsche set tabcod5='H396' where tabcod='S2003' and tabcod1='09' and tabcod3='003018125';
		Update tabsche set tabcod5='H754' where tabcod='S2003' and tabcod1='09' and tabcod3='004022165';
		Update tabsche set tabcod5='H753' where tabcod='S2003' and tabcod1='09' and tabcod3='001001235';
		Update tabsche set tabcod5='I162' where tabcod='S2003' and tabcod1='09' and tabcod3='003013248';
		Update tabsche set tabcod5='I328' where tabcod='S2003' and tabcod1='09' and tabcod3='019083090';
		Update tabsche set tabcod5='I329' where tabcod='S2003' and tabcod1='09' and tabcod3='020090092';
		Update tabsche set tabcod5='I138' where tabcod='S2003' and tabcod1='09' and tabcod3='007008055';
		Update tabsche set tabcod5='I778' where tabcod='S2003' and tabcod1='09' and tabcod3='020095078';
		Update tabsche set tabcod5='L658' where tabcod='S2003' and tabcod1='09' and tabcod3='019087052';
		Update tabsche set tabcod5='L659' where tabcod='S2003' and tabcod1='09' and tabcod3='003018170';
		
		--Nuova provincia 'Sud Sardegna'
		INSERT INTO tab3 (tab3cod, tab3tip, tab3desc, tab3mod, tab3nord) VALUES ('Agx15', 'SU', 'Sud Sardegna', NULL, NULL);
		INSERT INTO tabsche (tabcod, tabcod1, tabcod2, tabdesc, tabcod3, tabcod4, tabcod5) VALUES ('S2003', '07', '0111', 'SUD SARDEGNA', 'SU', NULL, NULL);
		--Archivia le province soppresse
		Update tab3 set tab3arc='1' where tab3cod='Agx15' and tab3tip='CI';
		Update tab3 set tab3arc='1' where tab3cod='Agx15' and tab3tip='OT';
		Update tab3 set tab3arc='1' where tab3cod='Agx15' and tab3tip='VS';
		Update tab3 set tab3arc='1' where tab3cod='Agx15' and tab3tip='OG';
		
		UPDATE ELDAVER SET NUMVER = '1.11.0', DATVET=CURRENT_TIMESTAMP WHERE CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

---------------------------------------------
--- Aggiornamento GENE da 1.11.0 a 1.12.0 ---
---------------------------------------------

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and (numver='1.11.0' or numver like '1.11.0.%'))=1
	THEN

		SET v_sql = 'ALTER TABLE IMPR ALTER COLUMN WLSEZIO SET DATA TYPE VARCHAR(100)';
		EXECUTE IMMEDIATE v_sql;

		-- Aggiunge tabellato 'Impresa sociale', non inserito in seguito a errore nell'aggiornamento alla versione 1.7.0
		IF (SELECT count(TAB1TIP) FROM TAB1 WHERE TAB1COD='Ag008' and TAB1TIP=13 and TAB1DESC='Impresa sociale (art.45 c.2/a DLgs 50/2016)')=0
		THEN
			INSERT INTO tab1 (tab1cod, tab1tip, tab1desc, tab1mod, tab1nord) VALUES ('Ag008', 13, 'Impresa sociale (art.45 c.2/a DLgs 50/2016)', NULL, 2);
		END IF;

		Update C0CAMPI set C0C_FS='A100' where C0C_MNE_BER='G_WLSEZIO';

		Update ELDAVER set NUMVER = '1.12.0', DATVET = CURRENT_TIMESTAMP where CODAPP = 'G_';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.2' or numver like '2.1.2.%'))=1
	THEN
	
		SET v_sql = 'ALTER TABLE W_PROFILI ALTER COLUMN NOME SET DATA TYPE VARCHAR(100)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W_PROFILI ALTER COLUMN DESCRIZIONE SET DATA TYPE VARCHAR(500)';
		EXECUTE immediate v_sql;

		DELETE FROM c0campi WHERE coc_mne_uni = 'NOME.W_PROFILI.GENEWEB';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (100000003, 'P', NULL, 'NOME.W_PROFILI.GENEWEB', 'W_NOME', 'Nome profilo (obbligatorio)', 'Nome profilo', 'A100', NULL, NULL, NULL);
		DELETE FROM c0campi WHERE coc_mne_uni = 'DESCRIZIONE.W_PROFILI.GENEWEB';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (100000004, 'P', NULL, 'DESCRIZIONE.W_PROFILI.GENEWEB', 'W_DESCRIZ', 'Descrizione del profilo', 'Descrizione', 'A500', NULL, 'NOTE', NULL);

		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('MASC', 'GENE.G_SCADENZ-timeline', 'Sequenza temporale', 1855);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'ALT.GENE.G_SCADENZ.Timeline', 'Sequenza temporale', 1935);
		
		update eldaver set numver = '2.1.3', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.3' or numver like '2.1.3.%'))=1
	THEN
	
		update c0campi set c0c_fs = 'N6' where coc_mne_uni = 'COD_CLIENTE.W_PROFILI.GENEWEB';
		--gestione permessi dl229
		INSERT INTO w_accliv (tabella, discr, liv1, liv2, valore) VALUES ('PROG_ANAG', 'SYSAB3', 'ID', NULL, 'IDCUP');
		
		-- Nuovi campi per integrazione protocollo ENGINEERING
		SET v_sql = 'ALTER TABLE WSLOGIN ADD COLUMN IDUTENTE VARCHAR(50)';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'ALTER TABLE WSLOGIN ADD COLUMN IDUTENTEUNOP VARCHAR(50)';
		EXECUTE immediate v_sql;
		
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'IDUTENTE.WSLOGIN.GENEWEB', 'WSLOIDUTENTE', 'Id. utente', 'Id. utente', 'A50', NULL, NULL, NULL);
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'IDUTENTEUNOP.WSLOGIN.GENEWEB', 'WSLOIDUTENTEUNOP', 'Id. UO utente', 'Id. UO utente', 'A50', NULL, NULL, NULL);
		
		update eldaver set numver = '2.1.4', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.4' or numver like '2.1.4.%'))=1
	THEN
	
		-- aumento la dimensione del campo  per compatibilita' con il codice applicazione '229'
		SET v_sql = 'ALTER TABLE W_DOCDIG ALTER COLUMN IDPRG SET DATA TYPE VARCHAR(3)';
		EXECUTE immediate v_sql;

		DELETE FROM c0campi WHERE coc_mne_uni = 'IDPRG.W_DOCDIG.GENEWEB';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (100000301, 'E', 'P', 'IDPRG.W_DOCDIG.GENEWEB', 'DIGIDPRG', 'Codice applicativo', 'Codice applicativo', 'A3', NULL, NULL, NULL);
		
		--Nuova voce per funzione 'Nuovo' nella lista di selezione da archivio TEIM
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'INS.GENE.ListaTeim.nuovo', 'Nuovo tecnico (popup selezione archivio)', 1685);

		update eldaver set numver = '2.1.5', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.5' or numver like '2.1.5.%'))=1
	THEN
	
		if (select count(*) from W_GENCHIAVI where TABELLA='UFFSET')=0
		then 
			INSERT INTO W_GENCHIAVI (TABELLA,CHIAVE) VALUES ('UFFSET',0);
		end if;

        --Aggiornamento profili
		-- Aggiornamento per Configurazione codifica automatica
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('MASC','GENE.G_CONFCOD-lista','Lista configurazione codifica automatica',1990);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('MASC','GENE.G_CONFCOD-scheda','Scheda configurazione codifica automatica',2000);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','MOD.GENE.G_CONFCOD-lista.MOD','Modifica',2010);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','MOD.GENE.G_CONFCOD-scheda.SCHEDAMOD','Modifica',2020);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('SUBMENU','AMMINISTRAZIONE.Configurazione-G_CONFCOD','Configurazione codifica automatica',2030);
		
		--Nuova sezione dinamica Settori per gli uffici intestatari
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('SEZ', 'GENE.SchedaUffint.UFFSET', 'Sezione dinamica settori', 1736);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'INS.GENE.SchedaUffint.INS-UFFSET', 'Nuovo settore', 1739);
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'DEL.GENE.SchedaUffint.DEL-UFFSET', 'Elimina settore', 1740);

        INSERT INTO w_azioni (tipo, azione, oggetto, descr, valore, inor, viselenco, crc) values ('SEZ', 'VIS', 'GENE.SchedaUffint.UFFSET', 'Visualizza', 0, 1, 1, 2354783444);
        INSERT INTO w_azioni (tipo, azione, oggetto, descr, valore, inor, viselenco, crc) values ('FUNZ', 'VIS', 'INS.GENE.SchedaUffint.INS-UFFSET', 'Visualizza', 0, 1, 1, 1133224913);
        INSERT INTO w_azioni (tipo, azione, oggetto, descr, valore, inor, viselenco, crc) values ('FUNZ', 'VIS', 'DEL.GENE.SchedaUffint.DEL-UFFSET', 'Visualizza', 0, 1, 1, 1651293914);

		update eldaver set numver = '2.1.6', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.6' or numver like '2.1.6.%'))=1
	THEN
	
		update eldaver set numver = '2.1.7', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.7' or numver like '2.1.7.%'))=1
	THEN
	
		update eldaver set numver = '2.1.8', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.8' or numver like '2.1.8.%'))=1
	THEN
		
		SET v_sql = 'ALTER TABLE W_GRUPPI ALTER COLUMN NOME SET DATA TYPE VARCHAR(100)';
		EXECUTE IMMEDIATE v_sql;
		
		-- Nuovo campo 'Richiesta firma?'
		SET v_sql = 'ALTER TABLE W_DOCDIG ADD COLUMN DIGFIRMA VARCHAR(1)';
		EXECUTE IMMEDIATE v_sql;
	
		DELETE FROM c0campi WHERE c0c_mne_ber in ('DIGFIRMA');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'DIGFIRMA.W_DOCDIG.GENEWEB', 'DIGFIRMA', 'Documento in attesa di firma?', 'Richiesta firma?', 'A2', NULL, 'SN', NULL);
		
		-- Aggiornamenti per Anagrafica impresa: nuova sezione 'White list'
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('SEZ','GENE.ImprScheda.DATIGEN.WHLA','Iscrizione white list antimafia per l''impresa',1006);
		
		-- Aggiornamenti per Anagrafica impresa: gestione DURC
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('PAGE','GENE.ImprScheda.DURC','Pagina "DURC on line"',940);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','DEL.GENE.ImprScheda.DURC.DEL','Elimina DURC on line',13420);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','DEL.GENE.ImprScheda.DURC.LISTADELSEL','Elimina DURC on line selezionati',13430);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','INS.GENE.ImprScheda.DURC.LISTANUOVO','Nuovo DURC on line',13440);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','MOD.GENE.ImprScheda.DURC.MOD','Modifica DURC on line',13450);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('MASC','GENE.ImpdurcScheda','Scheda DURC on line per l''impresa',13460);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','MOD.GENE.ImpdurcScheda.SCHEDAMOD','Modifica DURC on line per l''impresa',13470);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','INS.GENE.ImpdurcScheda.SCHEDANUOVO','Nuovo DURC on line per l''impresa',13480);
		
		-- aggiunta menu dell'utente loggato della voce "Il mio account" voce nascosta da profilo di default
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('SUBMENU', 'UTILITA.Mio-account', 'Il mio account', 1448);
		INSERT INTO w_azioni (tipo, azione, oggetto, descr, valore, inor, viselenco, crc) VALUES ('SUBMENU', 'VIS', 'UTILITA.Mio-account', 'Visualizza', 0, 1, 1, 998611131);

		-- mappatura mancante del tipo comunicazione
		INSERT INTO tab3 (tab3cod, tab3tip, tab3desc, tab3mod, tab3nord) VALUES ('G_x10', 'FS12', 'Comunicazione generica verso l''Ente', NULL, NULL);

		-- Aggiornamenti per C0CAMPI
		--Modifica il formato del campo COMDATAPUB a TIMESTAMP 
		UPDATE C0CAMPI SET COC_DOM='TIMESTAMP' WHERE C0C_MNE_BER='COMDATPUB';
		--Abilitata la visibilita' (C0C_TIP da 'P' a 'E') per i campi COMPUB, COMDATAPUB, COMNUMPROT, COMDATPROT 
		UPDATE C0CAMPI SET C0C_TIP='E' WHERE  C0C_MNE_BER in ('COMPUB','COMDATPUB','COMNUMPROT','COMDATPROT');
	
		update eldaver set numver = '2.1.9', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.9' or numver like '2.1.9.%'))=1
	THEN
	
		-- Nuovo campo 'Codice AOO'
		SET v_sql = 'ALTER TABLE WSFASCICOLO ADD COLUMN CODAOO VARCHAR(30)';
		EXECUTE IMMEDIATE v_sql;
		
		DELETE FROM c0campi WHERE c0c_mne_ber in ('WSCODAOO');
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'CODAOO.WSFASCICOLO.GENEWEB ', 'WSCODAOO', 'Codice AOO (Amministrazione organizzativa)', 'Codice AOO', 'A30', NULL, NULL, NULL);

		-- Nuovo campo delay
		SET v_sql = 'ALTER TABLE W_MAIL ADD COLUMN DELAY VARCHAR(6)';
		EXECUTE IMMEDIATE v_sql;
		
		update eldaver set numver = '2.1.10', datvet = current_timestamp  where codapp = 'W_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.10' or numver like '2.1.10.%'))=1
	THEN
	
		-- aggiornata configurazione sulla durata password con miglioramento descrizione ed abbassamento del tempo massimo della durata
		update w_config set descrizione = 'Durata della password (espressa in GIORNI) per utenti con abilitazione alla sicurezza password' where chiave='it.eldasoft.account.durataPassword';	
		update w_config set valore = '90' where chiave='it.eldasoft.account.durataPassword' and CAST( valore AS INT ) > 90;
		
		-- in seguito alla definizione dei nuovi utenti 48 (ADMIN) e 49 (EnteAdmin) si creano le medesime associazioni con gruppi e profili dell'utente 50 (MANAGER)
		insert into w_accgrp (id_account, id_gruppo, priorita) select 48, id_gruppo, priorita from w_accgrp where id_account = 50;
		insert into w_accgrp (id_account, id_gruppo, priorita) select 49, id_gruppo, priorita from w_accgrp where id_account = 50;
		insert into w_accpro (id_account, cod_profilo) select 48, cod_profilo from w_accpro where id_account = 50;
		insert into w_accpro (id_account, cod_profilo) select 49, cod_profilo from w_accpro where id_account = 50;

		-- aumento del numero di caratteri del campo descrizione nei parametri di un report
		SET v_sql = 'ALTER TABLE W_RICPARAM ALTER COLUMN DESCR SET DATA TYPE VARCHAR(2000)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE W_MODPARAM ALTER COLUMN DESCR SET DATA TYPE VARCHAR(2000)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE W_COMPARAM ALTER COLUMN DESCR SET DATA TYPE VARCHAR(2000)';
		EXECUTE IMMEDIATE v_sql;

		-- tabella per semaforizzare i nodi che lanciano task di quartz sullo stesso db  (una sola esecuzione per ogni task indipendentemente dai nodi)
		SET v_sql = 'CREATE TABLE W_QUARTZLOCK (
			codapp VARCHAR(10) NOT NULL,
			job VARCHAR(200) NOT NULL,
			lock_date TIMESTAMP NOT NULL,
			server_name VARCHAR(100),
			node_name VARCHAR(100),
			primary key (CODAPP, JOB))';
		EXECUTE IMMEDIATE v_sql;
		
		update eldaver set numver = '2.1.11', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.11' or numver like '2.1.11.%'))=1
	THEN
	
		--Comunicazione archiviata
        INSERT INTO tab2 (tab2cod, tab2tip, tab2d1, tab2d2, tab2mod, tab2nord) VALUES ('G_z20', '12', 'StatoC', 'Archiviata', NULL, NULL);
                
        insert into W_OGGETTI( ORD, OGGETTO, TIPO, DESCR) values (13500, 'GENEWEB.LogEventiTrova', 'MASC', 'Trova eventi log');
        insert into W_OGGETTI( ORD, OGGETTO, TIPO, DESCR) values (13510, 'GENEWEB.LogEventiLista', 'MASC', 'Lista eventi log');
        insert into W_OGGETTI( ORD, OGGETTO, TIPO, DESCR) values (13520, 'GENEWEB.LogEventiScheda', 'MASC', 'Scheda eventi log');

		--Nascosto pagina 'Casellario giudiziale' in anagrafica impresa
		INSERT INTO w_azioni (tipo, azione, oggetto, descr, valore, inor, viselenco, crc) VALUES ('PAGE', 'VIS', 'GENE.ImprScheda.CASE', 'Visualizza', 0, 1, 1, 3116177681);
	
		update eldaver set numver = '2.1.12', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.12' or numver like '2.1.12.%'))=1
	THEN
		
		INSERT INTO w_oggetti (tipo, oggetto, descr, ORD) VALUES ('SEZ','GENE.ImprScheda.DATIGEN.ART80','Verifiche art. 80',13530);
		
		update eldaver set numver = '2.1.13', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.13' or numver like '2.1.13.%'))=1
	THEN
	
		update eldaver set numver = '2.1.14', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.14' or numver like '2.1.14.%'))=1
	THEN
	
		UPDATE W_CONFIG SET DESCRIZIONE = 'Tipologia protocollo SSO. Valori ammessi: 0 [default]=non prevista, 1=Shibboleth, 2=Cohesion, 3=SSOBart, 4=OpenID' WHERE CHIAVE = 'sso.protocollo';
	
		update eldaver set numver = '2.1.15', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.1.15' or numver like '2.1.15.%'))=1
	THEN
	
		SET v_sql = 'ALTER TABLE WSDOCUMENTO ALTER COLUMN NUMERODOC SET DATA TYPE VARCHAR(40)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE WSFASCICOLO ADD COLUMN CODUFF VARCHAR(30)';
		EXECUTE IMMEDIATE v_sql;
		
		UPDATE C0CAMPI set C0C_FS = 'A40' where C0C_MNE_BER='WSDOCNDOC';

        DELETE FROM C0CAMPI WHERE C0C_MNE_BER in ('WSCODUFF');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E',NULL,'CODUFF.WSFASCICOLO.GARE','WSCODUFF','Codice ufficio responsabile','Codice ufficio','A30',NULL,NULL,NULL);
	
		update eldaver set numver = '2.2.0', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and (numver = '2.2.0' or numver like '2.2.0.%'))=1
	THEN
	
		SET v_sql = 'ALTER TABLE WSFASCICOLO ADD STRUTTURA VARCHAR(2000)';
		EXECUTE IMMEDIATE v_sql;

		DELETE FROM C0CAMPI where C0C_MNE_BER in ('WSSTRUTTU');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E',NULL,'STRUTTURA.WSFASCICOLO.GARE','WSSTRUTTU','Struttura competente','Struttura competente','A2000',NULL,NULL,NULL);
	
		update eldaver set numver = '2.3.0', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.3.%')=1
	THEN
	
		update eldaver set numver = '2.4.0', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.4.%')=1
	THEN
		--Nuovo stato per identificare comunicazioni da portale di cui in acquisizione deve essere solo cambiato lo stato
		INSERT INTO tab2 (tab2cod, tab2tip, tab2d1, tab2d2, tab2mod, tab2nord) VALUES ('G_z20', '13', 'StatoC', 'Evento da processare dopo correzione dati', NULL, NULL);

		update eldaver set numver = '2.5.0', datvet = current_timestamp  where codapp = 'W_';
			
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.5.%')=1
	THEN
	
		INSERT INTO tab2 (tab2cod, tab2tip, tab2d1, tab2d2, tab2mod, tab2nord) VALUES ('G_z20', '99', 'StatoC', 'Annullata', NULL, NULL);

		Update ELDAVER set NUMVER = '2.6.0', DATVET = CURRENT_TIMESTAMP where CODAPP = 'W_';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 4.0.0 --> 4.0.1
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='4.0.0' or numver like '4.0.0%') ) = 1
	THEN

		------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA DATI    -----------------------------------
		------------------------------------------------------------------------------------------------
		
		-- Aggiornamento di W9CF_PUBB per visualizzare il bando nei casi in cui PREV_BANDO='1' (retrocompatibilità)
		UPDATE W9CF_PUBB set CL_WHERE_VIS='(L.ID_SCELTA_CONTRAENTE_50 IN (1,5,7,12,13,14,15,16,20,21,23) OR G.PREV_BANDO=''1'')' where ID=3;
		
		
		-- Modifica alla descrizione di parametri
		Update W_CONFIG set DESCRIZIONE='Tipo di accesso ai servizi CIPE per richiesta CUP: 0 accesso diretto al WS; 1 accesso tramite PDD' where CODAPP='W9' and CHIAVE='it.eldasoft.richiestacup.type';
		Update W_CONFIG set DESCRIZIONE='URL del WS dei servizi CIPE per richiesta CUP' where CODAPP='W9' and CHIAVE='it.eldasoft.richiestacup.ws.url';
		Update W_CONFIG set DESCRIZIONE='URL della PDD per accesso ai servizi CIPE per richiesta CUP' where CODAPP='W9' and CHIAVE='it.eldasoft.richiestacup.pdd.url';
		Update W_CONFIG set DESCRIZIONE='Username della PDD per accesso ai servizi CIPE per richiesta CUP' where CODAPP='W9' and CHIAVE='it.eldasoft.richiestacup.pdd.username';
		Update W_CONFIG set DESCRIZIONE='Password della PDD per accesso ai servizi CIPE per richiesta CUP' where CODAPP='W9' and CHIAVE='it.eldasoft.richiestacup.pdd.password';
		
		Insert into TAB1 (TAB1COD, TAB1TIP,TAB1DESC,TAB1NORD) values ('W3998', -2, 'Cancellazione', 6);
		
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('FUNZ','VIS','ALT.W9.W9HOME.SA-ESITI','Visualizza',0,1,1,3942342027);

		Update ELDAVER set NUMVER='4.0.1', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 4.0.1 --> 4.1.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='4.0.1' or numver like '4.0.1.%') ) = 1
	THEN

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		
		SET v_sql=
			'CREATE TABLE W9OUTBOX (
				IDCOMUN NUMERIC(10) NOT NULL,
				AREA NUMERIC(7), 
				KEY01 NUMERIC(10), 
				KEY02 NUMERIC(10), 
				KEY03 NUMERIC(10), 
				KEY04 NUMERIC(10), 
				DATINV TIMESTAMP, 
				FILE_JSON CLOB (100 M),
				STATO NUMERIC(2),
				CFSA VARCHAR(16),
				MSG VARCHAR(2000),
				ID_RICEVUTO NUMERIC (7),
				PRIMARY KEY (IDCOMUN)
				)';
		EXECUTE immediate v_sql;
		
		------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA DATI    -----------------------------------
		------------------------------------------------------------------------------------------------
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W9OUTBOX.W9',0,'E','OUTBOX','Outbox','IDCOMUN.W9OUTBOX.W9',null,null,null,null,'w9_outbox');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (1,'E','P','IDCOMUN.W9OUTBOX.W9','W9OBID','Numero della comunicazione','Numero comunicazione','N10',null,null,'Numero della comunicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'AREA.W9OUTBOX.W9','W9OBAREA','Area dell''invio','Area invio','A100','W9004',null,'Area dell''invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY01.W9OUTBOX.W9','W9OBKEY01','Codice della gara, del programma o dell''avviso','Key 01','N10',null,null,'Codice della gara, del programma o dell''avviso');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY02.W9OUTBOX.W9','W9OBKEY02','Codice del lotto','Key 02','N10',null,null,'Codice del lotto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY03.W9OUTBOX.W9','W9OBKEY03','Fase di esecuzione','Key 03','A100','W3020',null,'Tipologia della fase di esecuzione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'KEY04.W9OUTBOX.W9','W9OBKEY04','Numero progressivo fase','Key 04','N3',null,null,'Numero progressivo fase');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATINV.W9OUTBOX.W9','W9OBDATINV','Data invio','Data invio','A10',null,'DATA_ELDA','Data invio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'STATO.W9OUTBOX.W9','W9OBSTATO','Stato comunicazione','Stato','A100','W9022',null,'Stato comunicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CFSA.W9OUTBOX.W9','W9OBCFSA','Codice fiscale della stazione appaltante','C.F. stazione appalt','A11',null,null,'Cod.fiscale stazione appaltante');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'MSG.W9OUTBOX.W9','W9OBMSG','Messaggio','Messaggio','A2000',null,'NOTE','Messaggio');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_RICEVUTO.W9OUTBOX.W9','W9OBIDRIC','Id ricevuto','Id ricevuto','N7',null,null,'Id ricevuto');

		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9022',1,'Da esportare',1);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9022',2,'Esito positivo',2);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W9022',3,'Esito negativo',3);
		
		Insert into W_QUARTZ (CODAPP,BEAN_ID,CRON_EXPRESSION,CRON_SECONDS,CRON_MINUTES,CRON_HOURS,CRON_DAY_OF_MONTH,CRON_MONTH,CRON_DAY_OF_WEEK,CRON_YEAR,DESCRIZIONE) values ('W9','inviaORtoSCPSchedulerTrigger','0 0 0 1 1 ? 2099','0','0','0','1','1','?','2099','Schedulazione dell''invio delle comunicazione da OR a SCP');

		UPDATE TAB1 SET TAB1DESC='Programma Biennale Acquisti' WHERE TAB1COD='W3020' AND TAB1TIP=981;
		UPDATE TAB1 SET TAB1DESC='Programma Triennale Opere Pubbliche' where TAB1COD='W3020' AND TAB1TIP=982;
		
		UPDATE W9LOTT set CUPESENTE='2' where CUP is not null;
		
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('FUNZ','ALT.W9.INVIAINDICISIMOG','Riallinea indici SIMOG',6360);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','W9.W9LOTT.CODCUI','Visualizza',0,1,1,2851720536);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','ALT.W9.INVIAINDICISIMOG','Visualizza',0,1,1,2245550442);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','MOD','W9.W9LOTT.CUPESENTE','Modifica',0,1,1,2254106059);
		
		DELETE from W_NOTE where oggetto='W3.W3GARA-scheda.W3GARADOC';
		INSERT into W_NOTE (TIPO,OGGETTO,PROG,MODOVIS,NOTA) VALUES (2,'W3.W3GARA-scheda.W3GARADOC',0,'3','<b>Nel caso di procedura ristretta</b><br>-  La valorizzazione delle date 1 e 3 dara'' luogo alla pubblicazione della prima fase in modalita'' "due fasi"; <b>la seconda fase dovra'' essere completata sul SIMOG</b><br>-  La valorizzazione di tutte le date dara'' luogo alla pubblicazione contestuale della prima e della seconda fase<br><b>Nel caso di procedura negoziata</b><br>-  La valorizzazione delle date 1 e 2 dara'' luogo alla pubblicazione in modalita'' "standard"<br>-  La valorizzazione delle date 1 e 3 dara'' luogo alla pubblicazione della prima fase in modalita'' "due fasi"; <b>la seconda fase dovra'' essere completata sul SIMOG</b><br>-  La valorizzazione di tutte le date dara'' luogo alla pubblicazione contestuale della prima e della seconda fase<br><b>Nel caso di procedure non rientranti nelle fattispecie precedenti</b><br>-  La valorizzazione delle date 1 e 2 dara'' luogo alla pubblicazione in modalita'' "standard", l''inserimento delle date 3 e 4 non e'' consentito<br>');
		DELETE from W_NOTE where oggetto='W3.W3GARA-scheda.W3LOTT';
		
		DELETE from W_QUARTZ where CODAPP='W9' and BEAN_ID='allineamentoRichiesteCancellazioneTrigger';
		Insert into W_QUARTZ (CODAPP,BEAN_ID,CRON_EXPRESSION,CRON_SECONDS,CRON_MINUTES,CRON_HOURS,CRON_DAY_OF_MONTH,CRON_MONTH,CRON_DAY_OF_WEEK,CRON_YEAR,DESCRIZIONE) values ('W9','allineamentoRichiesteCancellazioneTrigger','0 0 0 1 1 ? 2099','0','0','0','1','1','?','2099','Schedulazione SA per richiesta Eliminazione');
		
		UPDATE W_CONFIG set VALORE='4.1.0' where CODAPP='W9' and CHIAVE='it.eldasoft.sitatproxy.versioneTracciatoXML';
		IF (select count(*) from w_config where chiave='it.eldasoft.rt.sitatproxy.versioneTracciatoXML') = 1
		THEN
			UPDATE W_CONFIG set VALORE='4.1.0' where CODAPP='W9' and CHIAVE='it.eldasoft.rt.sitatproxy.versioneTracciatoXML';
		END IF;
		
		SET v_sql = 'ALTER TABLE TAB_CONTROLLI ADD COLUMN CODFUNZIONE VARCHAR(20)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE TAB_CONTROLLI');
		
		SET v_sql = 'UPDATE TAB_CONTROLLI set CODFUNZIONE=''APPALTILIGURIA'' where CODAPP=''W9''';
		EXECUTE immediate v_sql;
		SET v_sql = 'UPDATE TAB_CONTROLLI set CODFUNZIONE=''PUBB_SCP'' where CODAPP=''PT''';
		EXECUTE immediate v_sql;
		
		-- Imposta la constraint di tipo 'not null' sul campo CODFUNZIONE
		SET v_sql = 'ALTER TABLE TAB_CONTROLLI ALTER COLUMN CODFUNZIONE SET NOT NULL';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE TAB_CONTROLLI');
		
		-- Sostituisce la definizione corrente dei campi chiave con la nuova definizione
		SET v_sql = 'ALTER TABLE TAB_CONTROLLI DROP PRIMARY KEY';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE TAB_CONTROLLI ADD PRIMARY KEY(CODAPP,CODFUNZIONE,NUM)';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE TAB_CONTROLLI');

		Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','it.eldasoft.pubblicazioni.ws.urlAvvisiDoc',null,'Servizi di pubblicazione','Url di default per la pubblicazione dei documenti degli avvisi in SCP',null);
		Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','it.eldasoft.pubblicazioni.ws.urlAttiDoc',null,'Servizi di pubblicazione','Url di default per la pubblicazione dei documenti degli atti in SCP',null);

		UPDATE ELDAVER set NUMVER='4.1.0', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 4.1.0 --> 5.0.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='4.1.0' or numver like '4.1.0%') ) = 1
	THEN

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		
		SET v_sql = 'ALTER TABLE W9GARA ADD COLUMN SOMMA_URGENZA VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W9GARA ADD COLUMN DURATA_ACCQUADRO NUMERIC(5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W9GARA ADD COLUMN VER_SIMOG NUMERIC(5)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W9GARA ADD COLUMN DATA_PUBBLICAZIONE TIMESTAMP';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W9GARA');
		
		SET v_sql = 'ALTER TABLE W9VARI ADD COLUMN IMP_NON_ASSOG NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W9VARI');
		
		SET v_sql = 'Update W9GARA set SOMMA_URGENZA=''1'' where CODGARA in (select CODGARA from W9LOTT where SOMMA_URGENZA=''1'')';
		EXECUTE immediate v_sql;
		SET v_sql = 'Update W9GARA set SOMMA_URGENZA=''2'' where SOMMA_URGENZA is null';
		EXECUTE immediate v_sql;
		SET v_sql = 'Update W9GARA set DATA_PUBBLICAZIONE = (select min(DATA_PUBBLICAZIONE) from W9LOTT where W9LOTT.CODGARA = W9GARA.CODGARA and W9LOTT.DATA_PUBBLICAZIONE is not null)';
		EXECUTE immediate v_sql;
		
		SET v_sql = 'Update W9GARA set DURATA_ACCQUADRO = (select max(DURATA_ACCQUADRO) from W9APPA where W9APPA.CODGARA = W9GARA.CODGARA and W9APPA.DURATA_ACCQUADRO is not null)';
		EXECUTE immediate v_sql;

		SET v_sql = 'Update W9GARA set VER_SIMOG=1';
		EXECUTE immediate v_sql;
		
		--- Valorizzazione del campo W9LOTT.CUPESENTE
		UPDATE W9LOTT set CUPESENTE = '2' where CUP is not null;
		UPDATE W9LOTT set CUPESENTE = '1' where CUP is null;
		
		SET v_sql ='CREATE TABLE WS_CONTROLLI (
			CODAPP VARCHAR(5) NOT NULL,
			CODFUNZIONE VARCHAR(20) NOT NULL,
			NUM NUMERIC(7) NOT NULL,
			ENTITA VARCHAR(50),
			SEZIONE VARCHAR(50),
			TITOLO VARCHAR(100),
			MSG VARCHAR(2000),
			TIPO VARCHAR(1),
			PRIMARY KEY (CODAPP,CODFUNZIONE,NUM)
		)';
		EXECUTE immediate v_sql;

		SET v_sql = 'Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values 
		(''W9'', ''PUBBLICA_ATTO'', 10, ''W9PUBBLICAZIONI'', ''Pubblicazioni'', ''DataPubblicazione'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 11, ''W9PUBBLICAZIONI'', ''Pubblicazioni'', ''DataPubblicazione'', ''Deve essere <= alla data di oggi'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 20, ''W9PUBBLICAZIONI'', ''Pubblicazioni'', ''DataScadenza'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 21, ''W9PUBBLICAZIONI'', ''Pubblicazioni'', ''DataScadenza'', ''Deve essere >= alla DataPubblicazione'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 22, ''W9PUBBLICAZIONI'', ''Pubblicazioni'', ''DataProvvedimento'', ''Deve essere <= alla data di oggi'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 30, ''W9PUBBLICAZIONI'', ''Pubblicazioni'', ''Aggiudicatari'', ''Aggiudicatari previsti per Avviso di aggiudicazione o affidamento'', ''W''),
		(''W9'', ''PUBBLICA_ATTO'', 40, ''W9GARA'', ''Gara'', ''Settore'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 41, ''W9GARA'', ''Gara'', ''IdAnac'', ''Obbligatorio per richiesta CIG'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 50, ''W9GARA'', ''Gara'', ''Realizzazione'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 60, ''W9LOTT'', ''Lotto'', ''NumeroLotto'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 70, ''W9LOTT'', ''Lotto'', ''Cpv'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 80, ''W9LOTT'', ''Lotto'', ''IdSceltaContraente50'', ''Valorizzare il campo'', ''''),
		(''W9'', ''PUBBLICA_ATTO'', 90, ''W9LOTT'', ''Lotto'', ''Categoria'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 100, ''W9LOTT'', ''Lotto'', ''Classe'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 110, ''W9LOTT'', ''Lotto'', ''CriterioAggiudicazione'', ''Valorizzare il campo'', ''''),
		(''W9'', ''PUBBLICA_ATTO'', 120, ''W9LOTT'', ''Lotto'', ''Codice ISTAT o NUTS del comune'', ''Valorizzare uno dei due campi'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 130, ''W9LOTT'', ''Lotto'', ''CupEsente'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 140, ''W9LOTT'', ''Lotto'', ''Cup'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 150, ''ESITI_AGGIUDICATARI'', ''Aggiudicatari'', ''Ruolo'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 160, ''ESITI_AGGIUDICATARI'', ''Aggiudicatari'', ''IdGruppo'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_ATTO'', 170, ''W9DOCGARA'', ''Documenti'', ''Url'', ''Valorizzare il campo, deve essere un URL valido'', ''E'')';
		EXECUTE IMMEDIATE v_sql;
		
		SET v_sql = 'Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values (''W9'', ''PUBBLICA_AVVISO'', 10, ''AVVISO'', ''Avvisi'', ''Data'', ''Valorizzare il campo'', ''E''),
		(''W9'', ''PUBBLICA_AVVISO'', 11, ''AVVISO'', ''Avvisi'', ''Data'', ''Deve essere <= alla data di oggi'', ''E''),
		(''W9'', ''PUBBLICA_AVVISO'', 20, ''AVVISO'', ''Avvisi'', ''Scadenza'', ''Deve essere >= alla Data'', ''E''),
		(''W9'', ''PUBBLICA_AVVISO'', 30, ''W9DOCAVVISO'', ''Documenti'', ''Url'', ''Valorizzare il campo, deve essere un URL valido'', '''')';
		EXECUTE IMMEDIATE v_sql;
		
		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA CIG  --------------------------------
		----------------------------------------------------------------------------------------------------
		SET v_sql = 'ALTER TABLE w3usrsys DROP PRIMARY KEY';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE w3usrsys');
		SET v_sql = 'UPDATE w3usrsys SET RUP_CODTEC = '''' WHERE RUP_CODTEC IS NULL';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE w3usrsys ALTER COLUMN RUP_CODTEC SET NOT NULL';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE w3usrsys');
		SET v_sql = 'ALTER TABLE w3usrsys ADD PRIMARY KEY (SYSCON,RUP_CODTEC)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE w3usrsys');
		
		SET v_sql = 'ALTER TABLE w3usrsyscoll ADD COLUMN RUP_CODTEC VARCHAR(10)';
		EXECUTE immediate v_sql;
		SET v_sql = 'UPDATE w3usrsyscoll A SET A.RUP_CODTEC=(select B.RUP_CODTEC from w3usrsys B where A.syscon=B.syscon)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE w3usrsyscoll ALTER COLUMN RUP_CODTEC SET NOT NULL';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE w3usrsyscoll');
		SET v_sql = 'ALTER TABLE w3usrsyscoll DROP PRIMARY KEY';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE w3usrsyscoll');
		SET v_sql = 'ALTER TABLE w3usrsyscoll ADD PRIMARY KEY (SYSCON,W3AZIENDAUFFICIO_ID,RUP_CODTEC)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE w3usrsyscoll');
		
		SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN ALLEGATO_IX NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN STRUMENTO_SVOLGIMENTO NUMERIC(7)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3GARA');
		SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN ESTREMA_URGENZA NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql = 'ALTER TABLE W3GARA ADD COLUMN CODEIN VARCHAR(16)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3GARA');
		
		SET v_sql= 'ALTER TABLE W3GARA ADD COLUMN TEMP01 NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql= 'Update W3GARA SET TEMP01 = CAST(MODO_INDIZIONE AS NUMERIC(7))';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE W3GARA DROP COLUMN MODO_INDIZIONE';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE W3GARA ADD COLUMN MODO_INDIZIONE NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql= 'Update W3GARA SET MODO_INDIZIONE = TEMP01';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE W3GARA DROP COLUMN TEMP01';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3GARA');
		
		SET v_sql= 'ALTER TABLE W3GARA ADD COLUMN TEMP01 NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql= 'Update W3GARA SET TEMP01 = CAST(MODO_REALIZZAZIONE AS NUMERIC(7))';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE W3GARA DROP COLUMN MODO_REALIZZAZIONE';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE W3GARA ADD COLUMN MODO_REALIZZAZIONE NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql= 'Update W3GARA SET MODO_REALIZZAZIONE = TEMP01';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE W3GARA DROP COLUMN TEMP01';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3GARA');
		
		SET v_sql= 'CREATE TABLE W3COND
		(	NUMGARA NUMERIC(10) NOT NULL,
			NUMLOTT NUMERIC(10) NOT NULL,
			NUM_COND NUMERIC(3) NOT NULL,
			ID_CONDIZIONE NUMERIC(7),
			PRIMARY KEY (NUMGARA, NUMLOTT, NUM_COND)
		)';
		EXECUTE immediate v_sql;
		
		SET v_sql= 'ALTER TABLE W3LOTT ADD COLUMN ID_AFF_RISERVATI NUMERIC(7)';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE W3LOTT ADD COLUMN FLAG_REGIME VARCHAR(1)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3LOTT');
		SET v_sql= 'ALTER TABLE W3LOTT ADD COLUMN ART_REGIME VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE W3LOTT ADD COLUMN FLAG_DL50 VARCHAR(1)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3LOTT');
		SET v_sql= 'ALTER TABLE W3LOTT ADD COLUMN PRIMA_ANNUALITA NUMERIC(5)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3LOTT');
		
		SET v_sql= 'ALTER TABLE W3SMARTCIG ADD COLUMN CODEIN VARCHAR(16)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W3SMARTCIG');
		
		IF (select count(*) from SYSCAT.TABLES where TABSCHEMA=CURRENT SCHEMA and TABNAME = 'V_W3GARE') = 1
		THEN
			SET v_sql= 'DROP VIEW V_W3GARE';
			EXECUTE immediate v_sql;
		END IF;
		SET v_sql= 'CREATE VIEW V_W3GARE AS
		SELECT ''G_'' || cast(INT(NUMGARA) as char(10)) AS CODICE, ''G'' AS TIPO_GARA, NUMGARA, ID_GARA, STATO_SIMOG, DATA_CONFERMA_GARA, OGGETTO, IMPORTO_GARA, SYSCON, RUP_CODTEC, CODEIN FROM W3GARA 
		UNION
		SELECT ''S_'' || cast(INT(CODRICH) as char(10)) AS CODICE, ''S'' AS TIPO_GARA, CODRICH AS NUMGARA, CIG AS ID_GARA, STATO AS STATO_SIMOG, DATA_OPERAZIONE AS DATA_CONFERMA_GARA, OGGETTO, IMPORTO AS IMPORTO_GARA, SYSCON, RUP AS RUP_CODTEC, CODEIN FROM W3SMARTCIG';
		EXECUTE immediate v_sql;
		
		------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA DATI CIG   --------------------------------
		------------------------------------------------------------------------------------------------
		
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('W3COND.W3',0,'E','CONDIZNEG','Condizioni che giustificano il ricorso proc.negoziata','NUMGARA.W3COND.W3,NUMLOTT.W3COND.W3','W3LOTT.W3','NUMGARA.W3LOTT.W3,NUMLOTT.W3LOTT.W3',null,null,'w3_cond0');
		Update C0ENTIT set C0E_KEY='SYSCON.W3USRSYS,RUP_CODTEC.W3USRSYS' Where C0E_NOM='W3USRSYS.W3';
		Update C0ENTIT set C0E_KEY='SYSCON.W3USRSYSCOLL,W3AZIENDAUFFICIO_ID.W3USRSYSCOLL,RUP_CODTEC.W3USRSYSCOLL' Where C0E_NOM='W3USRSYSCOLL.W3';
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CODEIN.V_W3GARE.W3','W3_V_W3GARE_CODEIN','Codice stazione appaltante','Cod. Staz.appaltante','A16',null,null,'Codice stazione appaltante');
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ALLEGATO_IX.W3GARA.W3','W3_GARA_ALLEG_IX','Modalità di indizione allegato IX','Modalita allegato IX','A100','W3033',null,'Modalità di indizione allegato IX');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'STRUMENTO_SVOLGIMENTO.W3GARA.W3','W3_GARA_STR_SVOL','Strumenti per lo svolgimento delle procedure','Stru. svolgimento','A100','W3034',null,'Strumenti per lo svolgimento delle procedure');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ESTREMA_URGENZA.W3GARA.W3','W3_GARA_ESTR_URG','Deroghe ai sensi dell''articolo','Deroghe','A100','W3035',null,'Deroghe ai sensi dell''articolo');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CODEIN.W3GARA.W3','W3_GARA_CODEIN','Codice stazione appaltante','Cod. Staz.appaltante','A16',null,null,'Codice stazione appaltante');
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUMGARA.W3COND.W3','W3CONDGARA','Codice della gara','Cod. gara','N10',null,null,'Codice della gara');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUMLOTT.W3COND.W3','W3CONDLOTT','Numero del lotto','Num. lotto','N10',null,null,'Numero del lotto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','NUM_COND.W3COND.W3','W3CONDNUM','Numero progressivo condizione','Numero condizione','N3',null,null,'Numero progressivo condizione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_CONDIZIONE.W3COND.W3','W3CONDID','Condizione','Condizione','A100','W3006',null,'Condizione');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_AFF_RISERVATI.W3LOTT.W3','W3_LOTT_ID_AFFI','Tipo appalto riservato','Appalto riservato','A100','W3036',null,'Tipo appalto riservato');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FLAG_REGIME.W3LOTT.W3','W3_LOTT_FL_REGIME','Regimi particolari ?','Regimi particolari ?','A2',null,'SN','Regimi particolari ?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ART_REGIME.W3LOTT.W3','W3_LOTT_ART_REGIME','Art.regimi particolari','Art.regimi particol.','A100','W3z21',null,'Art.regimi particolari');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FLAG_DL50.W3LOTT.W3','W3_LOTT_FL_DL50','Interno alla programmazione ?','Interno al progr. ?','A2',null,'SN','Interno alla programmazione ?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'PRIMA_ANNUALITA.W3LOTT.W3','W3_LOTT_PRIMA_ANN','Prima annualità inserimento','Prima annualità','N5',null,null,'Prima annualità inserimento');
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E','P','RUP_CODTEC.W3USRSYSCOLL.W3','W3_USRSYSCOLL_RUP','Identificativo Rup','Identificativo Rup','A10',null,null,'');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CODEIN.W3SMARTCIG.W3','W3_SMARTCIG_CODEIN','Codice stazione appaltante','Cod. Staz.appaltante','A16',null,null,'Codice stazione appaltante');
		
		Update C0CAMPI set C0C_CHI='P' Where COC_MNE_UNI='RUP_CODTEC.W3USRSYS.W3';
		Update C0CAMPI set COC_DES='CUI programmazione',COC_DES_FRM='CUI programmazione',COC_DES_WEB='CUI programmazione' where COC_MNE_UNI='CUIINT.W9LOTT.W9';		
				
		
		Update TAB1 set TAB1ARC='1' WHERE TAB1COD='W3013' and TAB1TIP=3;
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3013',6,'Recesso dal contratto (codice antimafia)',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3013',7,'Fallimento, liquidazione coatta o concordato prev., insolv. concorsuale o liquidazione appaltatore',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3013',8,'Dichiarazione giudiziale di inefficacia del contratto',null);

		Update TAB1 set TAB1ARC='1' WHERE TAB1COD='W3014' and TAB1TIP=3;
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3014',6,'Superamento delle soglie delle modifiche contrattuali',0);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3014',7,'Modifica sostanziale del contratto',0);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3014',8,'Carenza dei requisiti al momento dell''aggiudicazione',0);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3014',9,'Violazione degli obblighi derivanti dai trattati',0);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3014',10,'Decadenza dell''attestazione di qualificazione',0);

		delete from TAB1 where TAB1COD='W3030';
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3030',1,'Stazione appaltante non soggetta agli obblighi di cui al dPCM 24 dicembre 2015',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3030',2,'Acquisto espletato mediante adesione a contratto di soggetto aggregatore',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3030',3,'Soggetto aggregatore iscritto nell''elenco di cui alla delibera ANAC n. 784 del 20 luglio 2016',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3030',4,'Soglie massime annuali di cui all''art. 1 del dPCM 24 dicembre 2015',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3030',5,'5Contratto non attivo presso il soggetto aggregatore',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3030',6,'Il fornitore non ha accettato ordinativi di importi minimi previsti dai contratti attivi',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3030',7,'Procedura avviata prima dell''entrata in vigore del DPCM 24 dicembre 2015',null);

		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3033',1,'Avviso di gara',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3033',2,'Avviso periodico indicativo',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3033',3,'Avviso sull''esistenza di un sistema di qualificazione',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3033',4,'Bando di gara',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3033',5,'Avviso di preinformazione',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3033',6,'Procedura negoziata senza previo avviso di gara conformemente all''art.63',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3033',7,'Procedura che non prevede indizione di gara',null);

		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3034',1,'Procedura svolta in modalità tradizionale o "cartacea"',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3034',2,'Asta elettronica Art.56',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3034',3,'Catalogo elettronico Art.57: acquisto diretto',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3034',4,'Catalogo elettronico Art.57: richiesta di offerta',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3034',5,'Procedure svolte attraverso piattaforme telematiche di negoziazione Art.58',null);

		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3035',1,'Somma urgenza beni culturali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3035',2,'Somma urgenza e protezione civile',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3035',3,'Estrema urgenza settori speciali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3035',4,'Procedura negoziata per estrema urgenza',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3035',5,'Estrema urgenza per vincolo idrogeologico, normativa antisismica e messa in sicurezza scuole',null);
		
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3036',1,'Art. 5 L. 381/1991',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3036',2,'At. 112 D.Lgs. 50/2016',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3036',3,'Art. 143 D.Lgs. 50/2016',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3036',4,'Art. 56 D. Lgs 117/2017',null);
		
		Update TAB1 set TAB1ARC='1' WHERE TAB1COD='W3027' and TAB1TIP in (1,2,3,4,5,6,7,8,9,10,11);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',13,'Contratti nel settore dell''acqua, dell''energia e dei trasporti',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',14,'Contratti nei settori dei servizi postali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',15,'Contratti nei settori delle comunicazioni elettroniche',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',16,'Contratti e concorsi di progettazione aggiudicati in base a norme internazionali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',17,'Contratti nei settori media audiovisi o radiofonici',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',18,'Servizi legali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',19,'Acquisto titoli e strumenti finanziari',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',20,'Prestiti',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',21,'Servizi di difesa e protezione civile forniti da organizzazioni e associazioni senza scopo di lucro',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',22,'Servizi di trasporto pubblico di passeggeri per ferrovia o metropolitana',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',23,'Servizi connessi a campagne politiche fino a €40.000 agg. da un partito in una campagna elettorale',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',24,'Contratti di sponsorizzazione tecnica',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',25,'Opera pubblica realizzata a spese del privato',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',26,'Concessioni, appalti pubblici e accordi tra enti e amministrazioni aggiudicatrici settore pubblico',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',27,'Appalti e concessioni aggiudicati a un''impresa collegata',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',28,'Appalti settori speciali e concessioni aggiudicati a joint-venture o facente parte di joint venture',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',29,'Contratti di servizi aggiudicati in base a un diritto esclusivo',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',30,'Appalti aggiudicati da particolari enti per l''acquisto di acqua e fornitura energia o combustibili',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',31,'Concessioni nel settore idrico escluse dall''applicazione del codice dei contratti pubblici',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',32,'Servizi di arbitrato e di conciliazione',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',33,'Appalti con oggetto l''acquisto di prodotti agricoli/alimentari fino €10,000 annui da imp.agricole',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',34,'Concessioni di servizi di trasporto aereo CE n. 1008/2008 e ai sensi del regolamento n. 1370/2007',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',35,'Concessioni di servizi di lotterie aggiudicate sulla base di un diritto esclusivo',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3027',36,'Opere a scomputo',null);

		Delete from TAB1 where TAB1COD='W3031';
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',1,'Farmaci',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',2,'Vaccini',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',3,'Stent',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',4,'Ausili per incontinenza (ospedalieri e territoriali)',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',5,'Protesi d''anca',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',6,'Medicazioni generali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',7,'Defibrillatori',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',8,'Pace-maker',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',9,'Aghi e siringhe',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',10,'Servizi integrati per la gestione delle apparecchiature elettromedicali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',11,'Servizi di pulizia per gli enti del Servizio Sanitario Nazionale',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',12,'Servizi di ristorazione per gli enti del Servizio Sanitario Nazionale',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',13,'Servizi di lavanderia per gli enti del Servizio Sanitario Nazionale',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',14,'Servizi di smaltimento rifiuti sanitari',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',15,'Vigilanza armata',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',16,'Facility management immobili',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',17,'Pulizia immobili',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',18,'Guardiania',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',19,'Manutenzione immobili e impianti',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',20,'Guanti (chirurgici e non)',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',21,'Suture',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',22,'Ossigenoterapia',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',23,'Diabetologia territoriale',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',24,'Servizio di trasporto scolastico',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3031',25,'Manutenzione strade (servizi e forniture)',null);



---- QUI----

		
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','01','01','Servizi sociali e altri servizi specifici di cui all''allegato IX esclusi appalti artt.143 e 144');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','02','02','Concorsi di progettazione nei settori speciali');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','03','03','Appalti riservati per determinati servizi');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','04','04','Servizi di ristorazione');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','05','05','Appalti nel settore dei beni culturali');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','06','06','Concorsi di progettazione e di idee');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','07','07','Servizi di ricerca e sviluppo');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','08','08','Appalti nei settori della Difesa e della Sicurezza');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','09','09','Contratti misti concernenti aspetti di difesa e sicurezza');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z21','10','10','Contratti secretati');
		
		delete from TAB2 where TAB2COD='W3z18';
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z18','EAM-1','EAM-1','Stazione appaltante non soggetta agli obblighi di cui al dPCM 24 dicembre 2015');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z18','EAM-2','EAM-2','Acquisto espletato mediante adesione a contratto di soggetto aggregatore');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z18','EAM-3','EAM-3','Soggetto aggregatore iscritto nell''elenco di cui alla delibera dell''ANAC n.784 del 20/07/2016');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z18','EAM-4','EAM-4','Soglie massime annuali di cui all''art. 1 del dPCM 24 dicembre 2015 non raggiunte');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z18','EAM-5','EAM-5','Contratto non attivo presso il soggetto aggregatore');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z18','EAM-6','EAM-6','Il fornitore non ha accettato ordinativi di importi minimi previsti dalle iniziative attive');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z18','EAM-7','EAM-7','Procedura avviata prima del''entrata in vigore del DPCM 24 dicembre 2015');
	
		delete from TAB2 where TAB2COD='W3z20';
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','01','01','PROCEDURA APERTA');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','02','02','PROCEDURA RISTRETTA');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','03','03','PROCEDURA COMPETITIVA CON NEGOZIAZIONE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','04','04','PROCEDURA NEGOZIATA SENZA PREVIA PUBBLICAZIONE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','05','05','DIALOGO COMPETITIVO');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','06','06','PROCEDURA NEGOZIATA SENZA PREVIA INDIZIONE DI GARA');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','07','07','SISTEMA DINAMICO DI ACQUISIZIONE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z20','08','08','AFFIDAMENTO IN ECONOMIA - COTTIMO FIDUCIARIO','1');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','14','14','PROCEDURA DISCIPLINATA DA REGOLAMENTO INTERNO PER SETTORI SPECIALI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','17','17','AFFIDAMENTO RISERVATO EX ART. 5 DELLA LEGGE N.381/92');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z20','21','21','PROCEDURA RISTRETTA DERIVANTE DA AVVISI CON CUI SI INDICE LA GARA','1');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','22','22','PROCEDURA NEGOZIATA CON PREVIA INDIZIONE DI GARA (SETTORI SPECIALI)');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','23','23','AFFIDAMENTO DIRETTO');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','24','24','AFFIDAMENTO DIRETTO A SOCIETA'' IN HOUSE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','25','25','AFFIDAMENTO DIRETTO A SOCIETA'' RAGGRUPPATE O CONTROLLATE NELLE CONCESSIONI E NEI PARTENARIATI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','26','26','AFFIDAMENTO DIRETTO IN ADESIONE AD ACCORDO QUADRO/CONVENZIONE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','27','27','CONFRONTO COMPETITIVO IN ADESIONE AD ACCORDO QUADRO/CONVENZIONE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','28','28','PROCEDURA AI SENSI DEI REGOLAMENTI DEGLI ORGANI COSTITUZIONALI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2,TAB2ARC) values ('W3z20','29','29','PROCEDURA RISTRETTA SEMPLIFICATA','1');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','30','30','PROCEDURA DERIVANTE DA LEGGE REGIONALE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','31','31','AFFIDAMENTO DIRETTO PER VARIANTE SUPERIORE AL 20% DELL''IMPORTO CONTRATTUALE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','32','32','AFFIDAMENTO RISERVATO EX Art. 112 D.Lgs. 50/2016');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','33','33','AFFIDAMENTO RISERVATO EX Art. 143 D.Lgs. 50/2016');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','34','34','AFFIDAMENTO RISERVATO EX Art. 56 D. Lgs 117/2017');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','35','35','PROCEDURA NEGOZIATA PER AFFIDAMENTI SOTTO SOGLIA');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','36','36','PROCEDURA ART.16 COMMA 2-BIS DPR 380/2001 PER OPERE URBANIZZAZIONE A SCOMPUTO PRIMARIE SOTTO SOGLIA');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','37','37','PARTERNARIATO PER L''INNOVAZIONE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','38','38','CONCORSO DI PROGETTAZIONE O DI IDEE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','39','39','PAGAMENTI DEI CONCESSIONARI DI FINANZIAMENTI PUBBLICI ANCHE EUROPEI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z20','999','999','NON CLASSIFICATO');

		Update TAB2 set TAB2ARC='1' WHERE TAB2COD='W3z19' and TAB2TIP in ('12','20','21','22','23','24','25','26','27','28');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','29','29','CONTRATTI DI ACQUISTO E LOCAZIONE DI IMMOBILI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','30','30','OPERE A SCOMPUTO FINO A € 40.000');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','31','31','COPROGETTAZIONE FINO A € 40.000');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','32','32','CONTRATTI FINO A € 40.000 NEL SETTORE DELL’ACQUA, DELL''ENERGIA E DEI TRASPORTI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','33','33','CONTRATTI FINO A € 40.000 NEI SETTORI DEI SERVIZI POSTALI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','34','34','APPALTI FINO A € 40.000 AGGIUDICATI DA PARTICOLARI ENTI PER ACQUISTO ACQUA E PRODUZIONE DI ENERGIA');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','35','35','CONCESSIONI FINO A € 40.000 SETTORE IDRICO ESCLUSE DALL''APPLICAZIONE DEL CODICE CONTRATTI PUBBLICI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','36','36','CONTRATTI FINO A € 40.000 NEI SETTORI DELLE COMUNICAZIONI ELETTRONICHE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','37','37','APPALTI/CONCORSI DI PROGETTAZIONE AGGIUDICATI/ORGANIZZATI PER ALTRI FINI O ESERCIZIO IN PAESE TERZO');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','38','38','CONTRATTI E CONCORSI DI PROGETTAZIONE FINO A € 40.000 AGGIUDICATI IN BASE A NORME INTERNAZIONALI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','39','39','CONTRATTI FINO A € 40.000 NEI SETTORI MEDIA AUDIOVISI O RADIOFONICI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','40','40','SERVIZI DI ARBITRATO E DI CONCILIAZIONE FINO A € 40.000');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','41','41','SERVIZI LEGALI FINO A € 40.000 ESCLUSI DALL''APPLICAZIONE DEL CODICE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','42','42','ACQUISTO TITOLI E STRUMENTI FINANZIARI FINO A € 40.000');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','43','43','PRESTITI FINO A € 40.000');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','44','44','SERVIZI DIFESA E PROTEZIONE CIVILE FINO A € 40.000 FORNITI DA ORGANIZZAZIONI SENZA SCOPO DI LUCRO');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','45','45','SERVIZI DI TRASPORTO PUBBLICO DI PASSEGGERI PER FERROVIA O METROPOLITANA FINO A € 40.000');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','46','46','SERVIZI CONNESSI A CAMPAGNE POLITICHE FINO A € 40.000 AGGIUDICATI DA UN PARTITO POLITICO');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','47','47','APPALTI CON OGGETTO ACQUISTO PRODOTTI AGRICOLI/ALIMENTARI FINO A € 10.000 ANNUI E € 40.000 TOT');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','48','48','SERVIZI TRASPORTO AEREO NORMA CE N.1008/2008 E TRASPORTO PUBBLICO NORMA N.1370/2007 FINO € 40.000');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','49','49','CONCESSIONI DI SERVIZI DI LOTTERIE FINO A € 40.000 AGGIUDICATE SULLA BASE DI UN DIRITTO ESCLUSIVO');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','50','50','CONCESSIONI IN PAESE TERZO IN CIRCOSTANZE CHE NON COMPORTINO MATERIALE DI UNA RETE O DI UN''AREA UE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','51','51','CONTRATTI FINO A € 40.000 DI SPONSORIZZAZIONE TECNICA');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','52','52','OPERA PUBBLICA REALIZZATA A SPESE DEL PRIVATO FINO A € 40.000');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','53','53','PAGAMENTI DEI CONCESSIONARI DI FINANZIAMENTI PUBBLICI ANCHE EUROPEI');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','54','54','CONCESSIONI, APPALTI E ACCORDI FINO A € 40.000 TRA ENTI E AMMINISTRAZIONI AMBITO SETTORE PUBBLICO');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','55','55','APPALTI E CONCESSIONI FINO A € 40.000 AGGIUDICATI A UN''IMPRESA COLLEGATA');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','56','56','APPALTI NEI SETTORI SPECIALI E CONCESSIONI FINO A € 40.000 AGGIUDICATI AD UNA JOINT-VENTURE');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('W3z19','57','57','CONTRATTI DI SERVIZI FINO A € 40.000 AGGIUDICATI IN BASE A UN DIRITTO ESCLUSIVO');
		
		------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA DATI    -----------------------------------
		------------------------------------------------------------------------------------------------
		Delete from C0CAMPI where COC_MNE_UNI like '%.W9ACCO.W9';
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (1,'E','P','CODGARA.W9ACCO.W9','W3ACCODGAR','Codice della gara','Cod. gara','N10',null,null,'Codice della gara');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (2,'E','P','CODLOTT.W9ACCO.W9','W3ACNUMLOT','Numero del lotto','Num. lotto','N10',null,null,'Numero del lotto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (3,'E','P','NUM_ACCO.W9ACCO.W9','W3ACNUMCON','Numero progressivo accordo','Numero accordo','N3',null,null,'Numero progressivo accordo bonario');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATA_ACCORDO.W9ACCO.W9','W3DATA_ACC','Data dell''accordo bonario','Data accordo','A10',null,'DATA_ELDA','Data dell''accordo bonario');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUM_RISERVE.W9ACCO.W9','W3NUM_RISE','Num. riserve transate','Num. riserve','N5',null,null,'Num. riserve transate');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ONERI_DERIVANTI.W9ACCO.W9','W3ONERI_DE','Oneri derivanti','Oneri derivanti','F15',null,'MONEY','Oneri derivanti');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATA_INIZIO_ACC.W9ACCO.W9','W9ACCODTINAC','Data inizio accordo bonario','Data inizio accordo','A10',null,'DATA_ELDA','Data inizio accordo bonario');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATA_FINE_ACC.W9ACCO.W9','W9ACCODTFIAC','Data fine accordo bonario','Data fine accordo','A10',null,'DATA_ELDA','Data fine accordo bonario');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SOMMA_URGENZA.W9GARA.W9','W3GASOMMUR','Estrema urgenza o esecuzione di lavori di somma urgenza?','Estrema urgenza?','A2',null,'SN','Estrema urgenza o esecuzione di lavori di somma urgenza?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DURATA_ACCQUADRO.W9GARA.W9','W9GADURACCQ','Durata accordo quadro','Modalità indizione','N5',null,null,'Durata accordo quadro');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VER_SIMOG.W9GARA.W9','W9GAVERSIM','Versione SIMOG','Versione SIMOG','N5',null,null,'Versione SIMOG');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATA_PUBBLICAZIONE.W9GARA.W9','W9GADATPUB','Data perfezionamento bando','Data perfez.to bando','A10',null,'DATA_ELDA','Data perfezionamento bando');
		
		Update C0CAMPI set COC_DES='Data scadenza presentazione manifestazioni interesse', COC_DES_WEB='Data di scadenza per la presentazione delle manifestazioni di interesse' where COC_MNE_UNI = 'DATA_MANIF_INTERESSE.W9APPA.W9';
		Update C0CAMPI set COC_DES='N. soggetti che hanno presentato manifestazione interesse', COC_DES_WEB='Numero soggetti che hanno presentato manifestazione interesse' where COC_MNE_UNI = 'NUM_MANIF_INTERESSE.W9APPA.W9';
		Update C0CAMPI set COC_DES='N. soggetti che hanno presentato richiesta di invito', COC_DES_WEB='Numero soggetti che hanno presentato richiesta di invito' where COC_MNE_UNI = 'NUM_IMPRESE_RICHIEDENTI.W9APPA.W9';
		Update C0CAMPI set COC_DES='N. soggetti invitati a presentare offerta', COC_DES_WEB='Numero soggetti invitate a presentare l''offerta' where COC_MNE_UNI = 'NUM_IMPRESE_INVITATE.W9APPA.W9';
		Update C0CAMPI set COC_DES='N. soggetti che hanno presentato offerta', COC_DES_WEB='Numero soggetti che hanno presentato offerta' where COC_MNE_UNI = 'NUM_IMPRESE_OFFERENTI.W9APPA.W9';
		Update C0CAMPI set COC_DES='N. giorni proroga concessi (non conseguenti a varianti)', COC_DES_WEB='Numero di giorni di proroga concessi (non conseguenti a varianti)' where COC_MNE_UNI ='NUM_GIORNI_PROROGA.W9AVAN.W9';
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IMP_NON_ASSOG.W9VARI.W9','W3I_NON_AS1','Eventuali ulteriori somme non assoggettate al ribasso d''asta','Ulteriori somme non','F15',null,'MONEY','Eventuali ulteriori somme non assoggettate al ribasso d''asta');
		
		Update TAB2 set TAB2D2='Puntuale' where TAB2COD='W3z04' and TAB2TIP='P';
		Update TAB2 set TAB2D2='Riconoscimento del mancato utile' where TAB2COD='W3z06' and TAB2TIP='1';
		Update TAB1 set TAB1DESC='Presidente Commissione di Collaudo/Collaudatore' where TAB1COD='W3004' and TAB1TIP=12;
		Update TAB1 set TAB1DESC='Senza esito a seguito di offerte irregolari/inammissibili, non congrue o non appropriate' where TAB1COD='W3022' and TAB1TIP=4;

		Update TAB1 set TAB1ARC='1' WHERE TAB1COD='W3999' and TAB1TIP in (7,9);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3999',13,'Contratto di disponibilità',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3999',14,'Interventi di sussidiarietà orizzontale',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3999',15,'Contratti di parternariato sociale (baratto amministrativo)',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3999',16,'Co-progettazione di servizi sociali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3999',17,'Accordo quadro',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3999',18,'Convenzione',null);

		Update TAB1 set TAB1ARC='1' where TAB1COD='W3007' and TAB1TIP in (1,2);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3007',3,'Offerta economicamente più vantaggiosa: miglior rapporto qualità / prezzo',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3007',4,'Offerta economicamente più vantaggiosa: criterio del minor prezzo',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3007',5,'Offerta economicamente più vantaggiosa: competizione solo in base a criteri qualitativi',null);

		Update TAB1 set TAB1ARC='1' where TAB1COD='W3005' and TAB1TIP in (7,9,11,12,13,14,25);
		Update TAB1 set TAB1DESC='Dialogo competitivo' where TAB1COD='W3005' and TAB1TIP=8;
		Update TAB1 set TAB1DESC='Procedura negoziata senza previa indizione di gara' where TAB1COD='W3005' and TAB1TIP=10;
		Update TAB1 set TAB1DESC='Affidamento diretto' where TAB1COD='W3005' and TAB1TIP=15;
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3005',27,'Procedura disciplinata da regolamento interno per settori speciali',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3005',28,'Procedura negoziata per affidamenti sotto soglia',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3005',29,'Procedura competitiva con negoziazione',null);            
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3005',30,'Procedura negoziata con previa indizione di gara (settori speciali)',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3005',33,'Procedura art.16 c2-bis DPR 380/2001 per opere urb. a scomputo primarie sotto soglia comunitaria',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3005',34,'Parternariato per l''innovazione',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3005',32,'Affidamento riservato',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3005',35,'Affidamento diretto per lavori, servizi o forniture supplementari',null);
		
		Update TAB1 set TAB1ARC='1' where TAB1COD='W3006';
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',33,'Procedura a seguito di precedente gara annullata o deserta o senza esito',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',34,'Lavori, beni e servizi infungibili per opera d''arte',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',35,'Lavori, beni e servizi infungibili per motivi tecnici',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',36,'Lavori, beni e servizi infungibili per diritti esclusivi',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',37,'Estrema urgenza',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',38,'Scopo di ricerca',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',39,'Consegne complementari',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',40,'Forniture quotate e acquistate sul mercato delle materie prime',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',41,'Condizioni particolarmente vantaggiose',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',42,'II fase Concorso di progettazione e idee',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',43,'Ripetizione lavori o servizi analoghi',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3006',44,'Procedura di affidamento a contraente vincolato da disposizioni sovraordinate',null);
		
		Update TAB1 set TAB1ARC='1' where TAB1COD='W3017' and TAB1TIP in (1,2,3,4,5,6,9,10,11,12,13);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3017',14,'Errori o omissioni nel progetto esecutivo',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3017',15,'Lavori, servizi o forniture supplementari',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3017',16,'Modifiche di importo inferiore a soglia comunitaria e a 10% (FS) o 15% (L) del valore contrattuale',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1NORD) values ('W3017',17,'Modifiche previste dai documenti di gara iniziali',null);
		
		Insert into TAB4 (TAB4COD,TAB4TIP,TAB4DESC,TAB4GRP) values ('W3_1','W9023','Data attuazione Simog 3.04.2',null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD) values ('W9023',1,'19/06/2019     Data attuazione Simog 3.04.2','1',null);
		
		Delete from W9CF_PUBB;
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (1,'Delibera/determina  a contrarre o atto equivalente o provvedimento di adesione ',10,'L.ID_SCELTA_CONTRAENTE_50<>10 or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,18,19,20,27,28,29,30,31,32,33,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (2,'Avviso per manifestazione di interesse, indagini di mercato (art.36 comma 2b,2c,2d)',20,'L.ID_SCELTA_CONTRAENTE_50 IN (6,8,23,24) or L.ID_SCELTA_CONTRAENTE in (4,10,28)','DATAPUBB|DATASCAD|URL_COMMITTENTE|URL_EPROC|','DATAPUBB|DATASCAD|','B');
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (3,'Documentazione di gara (bando di gara di appalto, concessione o concorso e altra documentazione di gara, lettere di invito ecc.)',30,'(L.ID_SCELTA_CONTRAENTE_50 IN (1,5,7,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,6,8,20,27,28,29,30,32,34) OR PREV_BANDO=''1'')','DATAPUBB|DATASCAD|URL_COMMITTENTE|URL_EPROC|','DATAPUBB|DATASCAD|','B');
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (4,'Avviso in merito alla modifica dell''ordine di importanza dei criteri, bando di concessione (art.173)',40,'L.ID_SCELTA_CONTRAENTE_50 IN (1,5,7,12,13,14,20,21) or L.ID_SCELTA_CONTRAENTE in (1,2,20,29,30,32,34)','DATAPUBB|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (5,'Avviso costituzione del privilegio',50,'L.ID_SCELTA_CONTRAENTE =1','DATAPUBB|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (6,'Lettera di invito',0,'1=2','DATAPUBB|DATASCAD|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (7,'Provvedimento che determina le esclusioni dalla procedura di affidamento e le ammissioni all''esito delle valutazioni dei requisiti soggettivi, economico-finanziari, e tecnico-professionali',70,'L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,4,10,20,28,29,30,32,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (8,'Provvedimento di nomina commissione di aggiudicazione e Curricula dei componenti della stessa in caso di criterio di aggiudicazione oepv ',80,'L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,4,10,20,28,29,30,32,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (9,'Provvedimento per eventuali esclusioni a seguito verifica offerte tecniche',90,'L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,4,10,20,28,29,30,32,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (10,'Provvedimento per eventuali esclusioni a seguito apertura offerte economiche',100,'L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,4,10,20,28,29,30,32,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (11,'Provvedimento per formazione Commissione per la valutazione dell''offerta anomala nel caso del criterio del prezzo più basso',110,'L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,4,10,20,28,29,30,32,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (12,'Provvedimento per eventuale esclusione offerta anomala',120,'L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,4,10,20,28,29,30,32,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (13,'Provvedimento di aggiudicazione non efficace con elenco verbali delle commissione di gara',130,'L.ID_SCELTA_CONTRAENTE_50 not in (10,11) or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,17,20,27,28,29,30,32,33,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (14,'Provvedimento di adesione',140,'L.ID_SCELTA_CONTRAENTE_50 in (10,11)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (15,'Provvedimento di aggiudicazione decreto o ordinativo',150,'L.ID_SCELTA_CONTRAENTE_50 IN (2,3,9,11,13,14,24)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (16,'Provvedimento di revoca dell''aggiudicazione o dell''adesione',160,'L.ID_SCELTA_CONTRAENTE_50 IS NOT NULL or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,17,18,19,20,27,28,29,30,32,33,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (17,'Provvedimento di gara non aggiudicata o deserta',170,'L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,4,10,20,28,29,30,32,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|','DATAPUBB|','D');
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (18,'Provvedimento di aggiudicazione efficace',180,'L.ID_SCELTA_CONTRAENTE_50 IN (1,4,5,6,7,8,12,13,14,15,16,17,18,19,20,21,22,23) or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,16,20,28,29,30,32,33,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (19,'Decreto o determina di affidamento  di lavori, servizi e forniture di somma urgenza e di protezione civile (art.163)',190,'L.ID_SCELTA_CONTRAENTE_50 IN (2,24) or L.ID_SCELTA_CONTRAENTE in (15,16)','DATAPUBB|PERC_RIBASSO_AGG|PERC_OFF_AUMENTO|IMPORTO_AGGIUDICAZIONE|DATA_VERB_AGGIUDICAZIONE|','DATAPUBB|','E');
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (20,'Avviso di aggiudicazione o affidamento (esito di gara)',210,'L.ID_SCELTA_CONTRAENTE_50<>10 or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,16,17,19,20,27,28,29,30,32,33,34)','DATAPUBB|PERC_RIBASSO_AGG|PERC_OFF_AUMENTO|IMPORTO_AGGIUDICAZIONE|DATA_VERB_AGGIUDICAZIONE|','DATAPUBB|','A');
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (21,'Testo integrale contratto di acquisto beni e servizi di importo unitario superiore al milione di euro',0,'1=2','DATAPUBB|DATA_STIPULA|NUM_REPERTORIO',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (22,'Provvedimento di autorizzazione subappalto',220,'L.ID_SCELTA_CONTRAENTE_50 NOT IN (10,15,16) or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,17,19,20,27,28,29,30,32,33,34)','DATAPUBB|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (23,'Atto per eventuale scioglimento contratto per eccesso durata sospensione esecuzione',230,'L.ID_SCELTA_CONTRAENTE_50 NOT IN (10,15,16) or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,17,19,20,27,28,29,30,32,33,34)','DATAPUBB|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (24,'Provvedimento di eventuali modifiche al contratto d''appalto',240,'L.ID_SCELTA_CONTRAENTE_50 NOT IN (10,15,16) or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,16,17,19,20,27,28,29,30,32,33,34)','DATAPUBB|',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (25,'Provvedimento di eventuale recesso dal contratto',250,'L.ID_SCELTA_CONTRAENTE_50 NOT IN (15,16) or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,16,17,18,19,20,27,28,29,30,32,33,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (26,'Provvedimento di eventuale risoluzione del contratto',260,'L.ID_SCELTA_CONTRAENTE_50 NOT IN (15,16) or L.ID_SCELTA_CONTRAENTE in (1,2,4,6,8,10,15,16,17,18,19,20,27,28,29,30,32,33,34)','DATAPUBB|DATA_PROVVEDIMENTO|NUM_PROVVEDIMENTO',null,null);
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (27,'Avviso contratto di sponsorizzazione (art.19)',25,'1=2','DATAPUBB|DATASCAD|URL_COMMITTENTE|URL_EPROC|','DATAPUBB|DATASCAD|','B');
		Insert into W9CF_PUBB (ID,NOME,NUMORD,CL_WHERE_VIS,CAMPI_VIS,CAMPI_OBB,TIPO) values (99,'Altro documento',990,'1=1','DATAPUBB|',null,null);

		
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','MOD','W9.W9GARA.ID_MODO_INDIZIONE','Modifica',0,1,1,662036795);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','MOD','W9.W9GARA.SOMMA_URGENZA','Modifica',0,1,1,1134617568);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','MOD','W9.W9GARA.DATA_PUBBLICAZIONE','Modifica',0,1,1,1499296147);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','MOD','W9.W9GARA.TIPO_APP','Modifica',0,1,1,204121018);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','MOD','W9.W9LOTT.ID_SCELTA_CONTRAENTE','Modifica',0,1,1,3726712814);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','MOD','W9.W9LOTT.ART_E1','Modifica',0,1,1,1667451274);
		
		UPDATE W_CONFIG set VALORE='5.0.0' where CODAPP='W9' and CHIAVE like 'it.eldasoft.%sitatproxy.versioneTracciatoXML';

		UPDATE ELDAVER set NUMVER='5.0.0', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 3.2.0 - aggiornamento di PT alla versione 3.2.1
--------------------------------------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='PT' and (numver='3.2.0' or numver like '3.2.0.%'))=1
	THEN
		
		SET v_sql= 'CREATE TABLE RIS_CAPITOLO (
			CONTRI NUMERIC(10) NOT NULL,
			CONINT NUMERIC(5) NOT NULL,
			NUMCB NUMERIC(5) NOT NULL,
			NCAPBIL VARCHAR(100),
			IMPSPE VARCHAR(100),
			RG1CB NUMERIC(24,5),
			IMPRFSCB NUMERIC(24,5),
			IMPALTCB NUMERIC(24,5),
			VERIFBIL NUMERIC(1),
			DV1CB NUMERIC(24,5),
			DV2CB NUMERIC(24,5),
			DV3CB NUMERIC(24,5),
			DV9CB NUMERIC(24,5),
			MU1CB NUMERIC(24,5),
			MU2CB NUMERIC(24,5),
			MU3CB NUMERIC(24,5),
			MU9CB NUMERIC(24,5),
			BI1CB NUMERIC(24,5),
			BI2CB NUMERIC(24,5),
			BI3CB NUMERIC(24,5),
			BI9CB NUMERIC(24,5),
			AP1CB NUMERIC(24,5),
			AP2CB NUMERIC(24,5),
			AP3CB NUMERIC(24,5),
			AP9CB NUMERIC(24,5),
			AL1CB NUMERIC(24,5),
			AL2CB NUMERIC(24,5),
			AL3CB NUMERIC(24,5),
			AL9CB NUMERIC(24,5),
			TO1CB NUMERIC(24,5),
			TO2CB NUMERIC(24,5),
			TO3CB NUMERIC(24,5),
			TO9CB NUMERIC(24,5),
			IV1CB NUMERIC(24,5),
			IV2CB NUMERIC(24,5),
			IV9CB NUMERIC(24,5),
			SPESESOST NUMERIC(24,5),
			PRIMARY KEY (CONTRI,CONINT,NUMCB)
		)';
		EXECUTE IMMEDIATE v_sql;
		
		SET v_sql = 'GRANT SELECT,INSERT,UPDATE,DELETE ON TABLE RIS_CAPITOLO TO USER SitatSA, SSELDAS, LM07421';
		EXECUTE IMMEDIATE v_sql;
		
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN VALUTAZIONE NUMERIC(1)';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE INTTRI DROP COLUMN IMPSPE';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE INTTRI DROP COLUMN NCAPBIL';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE INTTRI DROP COLUMN VERIFBIL';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN SPESESOST NUMERIC(24,5)';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		
		DELETE FROM C0CAMPI WHERE COC_MNE_UNI in ('IMPSPE.INTTRI.PIANI','NCAPBIL.INTTRI.PIANI','VERIFBIL.INTTRI.PIANI');
		
		INSERT INTO C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) VALUES ('RIS_CAPITOLO.PIANI',80030500,'E','INTRISCAPI','Risorse capitolo di bilancio','CONTRI.RIS_CAPITOLO.PIANI,CONINT.RIS_CAPITOLO.PIANI,NCAPBIL.RIS_CAPITOLO.PIANI','INTTRI.PIANI','CONTRI.INTTRI.PIANI,CONINT.INTTRI.PIANI',null,null,'pt_riscap');

		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E','P','CONINT.RIS_CAPITOLO.PIANI','T2CONCB','Numero progressivo dell''intervento','N.progr.intervento','N5',null,null,'Numero progressivo dell''intervento');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E','P','CONTRI.RIS_CAPITOLO.PIANI','T2CONTRICB','Numero progressivo del programma','N.progr.piano trien.','N10',null,null,'Numero progressivo del programma');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E','P','NUMCB.RIS_CAPITOLO.PIANI','T2NUMCB','Numero progressivo capitolo','N.progr.ris.cap.','N10',null,null,'Numero progressivo del capitolo');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'NCAPBIL.RIS_CAPITOLO.PIANI','T2NCAPBILCB','Numero capitolo di bilancio','N.capitolo bilancio','A100',null,null,'Numero capitolo di bilancio');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IMPSPE.RIS_CAPITOLO.PIANI','T2IMPSPECB','Impegni di spesa sui capitoli','Impegni spesa capit.','A100',null,null,'Impegni di spesa sui capitoli');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'RG1CB.RIS_CAPITOLO.PIANI','RG1CB','Importo risorse finanziarie regionali','Imp.ris. finanz.reg.','F15',null,'MONEY','Importo risorse finanziarie regionali');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IMPRFSCB.RIS_CAPITOLO.PIANI','T2IMPRFSCB','Importo risorse finanziarie stato/UE','Imp.risorse finanz.','F15',null,'MONEY','Importo risorse finanziarie stato/UE');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IMPALTCB.RIS_CAPITOLO.PIANI','T2IMPALTCB','Importo risorse finanziarie altro','Imp.risorse.finan.al','F15',null,'MONEY','Importo risorse finanziarie altro');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'VERIFBIL.RIS_CAPITOLO.PIANI','T2VERBILCB','Esito positivo verifica coerenza con bilancio ufficio?','Coerente con bilanc?','A2',null,'SN','Esito positivo verifica coerenza con bilancio da parte dell''ufficio?');
																																																	   
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DV1CB.RIS_CAPITOLO.PIANI','T2IDV1CB','Entrate aventi destinazione vincolata per legge (1'' anno)','Ris.dest.vinc. 1''','F15',null,'MONEY', 'Entrate aventi destinazione vincolata per legge (1''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DV2CB.RIS_CAPITOLO.PIANI','T2IDV2CB','Entrate aventi destinazione vincolata per legge (2'' anno)','Ris.dest.vinc. 2''','F15',null,'MONEY', 'Entrate aventi destinazione vincolata per legge (2''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DV3CB.RIS_CAPITOLO.PIANI','T2IDV3CB','Entrate aventi destinazione vincolata per legge (3'' anno)','Ris.dest.vinc. 3''','F15',null,'MONEY', 'Entrate aventi destinazione vincolata per legge (3''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'DV9CB.RIS_CAPITOLO.PIANI','T2DV9CB', 'Entrate aventi destinazione vincolata per legge (anni succ)','Ris.dest.vinc. suc','F15',null,'MONEY','Entrate aventi destinazione vincolata per legge (anni successivi)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'MU1CB.RIS_CAPITOLO.PIANI','T2IMU1CB','Entrate acquisite mediante contrazione di mutuo (1'' anno)','Ris.acquis.mutuo 1''','F15',null,'MONEY','Entrate acquisite mediante contrazione di mutuo (1''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'MU2CB.RIS_CAPITOLO.PIANI','T2IMU2CB','Entrate acquisite mediante contrazione di mutuo (2'' anno)','Ris.acquis.mutuo 2''','F15',null,'MONEY','Entrate acquisite mediante contrazione di mutuo (2''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'MU3CB.RIS_CAPITOLO.PIANI','T2IMU3CB','Entrate acquisite mediante contrazione di mutuo (3'' anno)','Ris.acquis.mutuo 3''','F15',null,'MONEY','Entrate acquisite mediante contrazione di mutuo (3''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'MU9CB.RIS_CAPITOLO.PIANI','T2MU9CB', 'Entrate acquisite mediante contrazione di mutuo (anni succ)','Ris.acquis.mutuo suc','F15',null,'MONEY','Entrate acquisite mediante contrazione di mutuo (anni successivi)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'BI1CB.RIS_CAPITOLO.PIANI','T2IBI1CB','Stanziamenti di bilancio (1'' anno)','Imp.stanz.bilanc. 1''','F15',null,'MONEY','Stanziamenti di bilancio (1'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'BI2CB.RIS_CAPITOLO.PIANI','T2IBI2CB','Stanziamenti di bilancio (2'' anno)','Imp.stanz.bilanc. 2''','F15',null,'MONEY','Stanziamenti di bilancio (2'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'BI3CB.RIS_CAPITOLO.PIANI','T2IBI3CB','Stanziamenti di bilancio (3'' anno)','Imp.stanz.bilanc. 3''','F15',null,'MONEY','Stanziamenti di bilancio (3'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'BI9CB.RIS_CAPITOLO.PIANI','T2BI9CB','Stanziamenti di bilancio (anni successivi)','Imp.stanz.bilan.succ','F15',null,'MONEY','Stanziamenti di bilancio anni successivi"');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AP1CB.RIS_CAPITOLO.PIANI','T2AP1CB','Finanziamenti art. 3 DL 310/1990 (1''anno)','Imp.finanz.1''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (1''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AP2CB.RIS_CAPITOLO.PIANI','T2AP2CB','Finanziamenti art. 3 DL 310/1990 (2''anno)','Imp.finanz.2''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (2''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AP3CB.RIS_CAPITOLO.PIANI','T2AP3CB','Finanziamenti art. 3 DL 310/1990 (3''anno)','Imp.finanz.3''anno','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (3''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AP9CB.RIS_CAPITOLO.PIANI','T2AP9CB','Finanziamenti art. 3 DL 310/1990 (anni successivi)','Imp.finanz. anni suc','F15',null,'MONEY','Finanziamenti art. 3 DL 310/1990 (anni successivi)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AL1CB.RIS_CAPITOLO.PIANI','T2IAL1CB','Altre risorse disponibili (1'' anno)','Imp.altre risors. 1''','F15',null,'MONEY','Altre risorse disponibili (1'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AL2CB.RIS_CAPITOLO.PIANI','T2IAL2CB','Altre risorse disponibili (2'' anno)','Imp.altre risors. 2''','F15',null,'MONEY','Altre risorse disponibili (2'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AL3CB.RIS_CAPITOLO.PIANI','T2IAL3CB','Altre risorse disponibili (3'' anno)','Imp.altre risors. 3''','F15',null,'MONEY','Altre risorse disponibili (3'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'AL9CB.RIS_CAPITOLO.PIANI','T2AL9CB','Altre risorse disponibili (anni successivi)','Imp.altre risors.suc','F15',null,'MONEY','Altre risorse disponibili (anni successivi)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'TO1CB.RIS_CAPITOLO.PIANI','T2ITO1CB','Importo complessivo (1'' anno)','Importo compless. 1''','F15',null,'MONEY','Importo complessivo (1'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'TO2CB.RIS_CAPITOLO.PIANI','T2ITO2CB','Importo complessivo (2'' anno)','Importo compless. 2''','F15',null,'MONEY','Importo complessivo (2'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'TO3CB.RIS_CAPITOLO.PIANI','T2ITO3CB','Importo complessivo (3'' anno)','Importo compless. 3''','F15',null,'MONEY','Importo complessivo (3'' anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'TO9CB.RIS_CAPITOLO.PIANI','T2TO9CB','Importo complessivo (anni successivi)','Importo comples .suc','F15',null,'MONEY','Importo complessivo disponibili (anni successivi)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IV1CB.RIS_CAPITOLO.PIANI','T2IV1CB','Importo IVA (1''anno)','Importo IVA 1''anno','F24.5',null,'MONEY','Importo IVA (1''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IV2CB.RIS_CAPITOLO.PIANI','T2IV2CB','Importo IVA (2''anno)','Importo IVA 2''anno','F24.5',null,'MONEY','Importo IVA (2''anno)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'IV9CB.RIS_CAPITOLO.PIANI','T2IV9CB','Importo IVA (anni successivi)','Importo IVA anni suc','F24.5',null,'MONEY','Importo IVA (anni successivi)');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'SPESESOST.RIS_CAPITOLO.PIANI','T2SPESECB','Spese già sostenute','Spese sostenute','F15',null,'MONEY','Spese già sostenute');
		
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'VALUTAZIONE.INTTRI.PIANI','T2VALUTAINT','Valutazione del responsabile dl programma','Valut. responsabile','T15','PT021',null,'Valutazione del responsabile dl programma');
		INSERT INTO C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) VALUES (0,'E',null,'SPESESOST.INTTRI.PIANI','T2SPESESOST','Spese già sostenute','Spese sostenute','F15',null,'MONEY','Spese già sostenute');
		
		UPDATE C0CAMPI set COC_DES='Annualità avvio procedura di affidamento', COC_DES_FRM='Anno avvio affidam.', COC_DES_WEB='Annualità avvio procedura di affidamento' where COC_MNE_UNI = 'ANNRIF.INTTRI.PIANI';
		
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT021',1,'Accolto',null,null,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT021',2,'Sospeso',null,null,null);
		INSERT INTO TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT021',3,'Respinto',null,null,null);

		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('PAGE','PIANI.INTTRI-scheda.INTERVENTO','Pagina Intervento - Scheda intervento',6020);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('PAGE','PIANI.INTTRI-scheda.RISORSEBILANCIO','Pagina Intervento - Scheda Risorse per capitolo di bilancio',6025);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('SEZ','PIANI.INTTRI-scheda.INTERVENTO.ACQVERDI','Pagina Intervento - Scheda Acquisti verdi',6030);
		INSERT INTO W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) VALUES ('SEZ','PIANI.INTTRI-scheda.INTERVENTO.MATRIC','Pagina Intervento - Scheda Materiali riciclati',6035);
		
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.ACQVERDI','Visualizza',0,1,1,3378577521);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.DIRGEN','Visualizza',0,1,1,1662235308);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.IMPALT','Visualizza',0,1,1,1701084351);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.IMPRFS','Visualizza',0,1,1,927018130);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.MATRIC','Visualizza',0,1,1,453282956);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.QUANTIT','Visualizza',0,1,1,2133630041);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.REFERE','Visualizza',0,1,1,821971293);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.RESPUF','Visualizza',0,1,1,4137032418);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.RG1TRI','Visualizza',0,1,1,3645427203);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.STRUOP','Visualizza',0,1,1,4167311987);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.UNIMIS','Visualizza',0,1,1,1907123350);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('COLS','VIS','PIANI.INTTRI.VALUTAZIONE','Visualizza',0,1,1,451053860);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('PAGE','VIS','PIANI.INTTRI-scheda.RISORSEBILANCIO','Visualizza',0,1,1,792596830);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('SEZ','VIS','PIANI.INTTRI-scheda.INTERVENTO.ACQVERDI','Visualizza',0,1,1,2612833523);
		INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('SEZ','VIS','PIANI.INTTRI-scheda.INTERVENTO.MATRIC','Visualizza',0,1,1,2239696433);
		
		UPDATE ELDAVER SET NUMVER = '3.2.1', DATVET=CURRENT_TIMESTAMP WHERE CODAPP='PT';
		
		COMMIT;
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 3.2.1 --> 4.0.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '3.2.1' or numver like '3.2.1.%')) = 1
	THEN

		---  OPERE INCOMPIUTE
		SET v_sql= 'DROP TABLE OITRI';
		EXECUTE immediate v_sql;
		
		SET v_sql= 'CREATE TABLE OITRI (
			CONTRI NUMERIC(10) NOT NULL,
			NUMOI NUMERIC(6) NOT NULL,
			CUP  VARCHAR(15),
			CUPMASTER  VARCHAR(15),
			DESCRIZIONE VARCHAR(2000),
			DETERMINAZIONI VARCHAR(5),
			AMBITOINT VARCHAR(5),
			ANNOULTQE  NUMERIC(4),
			IMPORTOINT NUMERIC(24,5),
			IMPORTOLAV NUMERIC(24,5),
			ONERIULTIM NUMERIC(24,5),
			IMPORTOSAL NUMERIC(24,5),
			AVANZAMENTO NUMERIC(13,9),
			CAUSA VARCHAR(5),
			FRUIBILE VARCHAR(1),
			STATOREAL VARCHAR(5),
			UTILIZZORID VARCHAR(1),
			REQ_CAP VARCHAR(1),
			REQ_PRGE VARCHAR(1),
			DIM_UM  VARCHAR(10),
			DIM_QTA  NUMERIC(24,5),
			DESTINAZIONEUSO VARCHAR(5),
			CESSIONE VARCHAR(1),
			INFRASTRUTTURA VARCHAR(1),
			VENDITA VARCHAR(1),
			DEMOLIZIONE VARCHAR(1),
			ISTAT VARCHAR(20),
			NUTS VARCHAR(5),
			SEZINT VARCHAR(3),
			INTERV VARCHAR(6),
			SPONSORIZZAZIONE VARCHAR(1),
			FINANZA_PROGETTO VARCHAR(1),
			COSTO NUMERIC(24,5),
			FINANZIAMENTO NUMERIC(24,5),
			COP_STATALE VARCHAR(1),
			COP_REGIONALE VARCHAR(1),
			COP_PROVINCIALE VARCHAR(1),
			COP_COMUNALE VARCHAR(1),
			COP_ALTRAPUBBLICA VARCHAR(1),
			COP_COMUNITARIA VARCHAR(1),
			COP_PRIVATA VARCHAR(1),	
			ONERI_SITO NUMERIC(24,5),
			PRIMARY KEY (CONTRI, NUMOI)
		)';
		EXECUTE immediate v_sql;

		IF NOT EXISTS (SELECT * FROM SYSCAT.TABLES where TABSCHEMA =CURRENT SCHEMA AND TABNAME='TAB_CONTROLLI') THEN 
			SET v_sql= 'CREATE TABLE TAB_CONTROLLI (
				CODAPP VARCHAR(5) NOT NULL,
				NUM NUMERIC(7) NOT NULL,
				ENTITA VARCHAR(50),
				SEZIONE VARCHAR(50),
				TITOLO VARCHAR(100),
				CONDIZIONE VARCHAR(2000),
				MSG VARCHAR(2000),
				TIPO VARCHAR(1),
				PRIMARY KEY (CODAPP,NUM))';
			EXECUTE immediate v_sql;
		END IF;

		IF EXISTS (SELECT * FROM SYSCAT.COLUMNS where TABSCHEMA =CURRENT SCHEMA AND TABNAME='INTTRI' AND COLNAME='IM1INT') THEN 
			SET v_sql= 'ALTER TABLE INTTRI DROP COLUMN IM1INT';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		END IF;
		IF EXISTS (SELECT * FROM SYSCAT.COLUMNS where TABSCHEMA =CURRENT SCHEMA AND TABNAME='INTTRI' AND COLNAME='IM2INT') THEN 
			SET v_sql= 'ALTER TABLE INTTRI DROP COLUMN IM2INT';
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
			EXECUTE immediate v_sql;
		END IF;
		IF EXISTS (SELECT * FROM SYSCAT.COLUMNS where TABSCHEMA =CURRENT SCHEMA AND TABNAME='INTTRI' AND COLNAME='IM3INT') THEN 
			SET v_sql= 'ALTER TABLE INTTRI DROP COLUMN IM3INT';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		END IF;
		IF EXISTS (SELECT * FROM SYSCAT.COLUMNS where TABSCHEMA =CURRENT SCHEMA AND TABNAME='INTTRI' AND COLNAME='TIPCAPPRIV') THEN 
			SET v_sql= 'ALTER TABLE INTTRI DROP COLUMN TIPCAPPRIV';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		END IF;
		
		SET v_sql= 'ALTER TABLE INTTRI
			ALTER COLUMN SEZINT set DATA TYPE VARCHAR(3)
			ALTER COLUMN CUIINT set DATA TYPE VARCHAR(25)
			ALTER COLUMN FIINTR set DATA TYPE VARCHAR(10)
			ADD COLUMN IM9TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;

		SET v_sql= 'ALTER TABLE INRTRI ALTER COLUMN CUIINT set DATA TYPE VARCHAR(25)';
		EXECUTE immediate v_sql;

		SET v_sql= 'ALTER TABLE IMMTRAI
			ADD COLUMN ALIENATI VARCHAR(1)
			ADD COLUMN VA9IMM NUMERIC(24,5)
			ADD COLUMN NUMOI NUMERIC(6)';
		EXECUTE immediate v_sql;
		
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO ADD COLUMN CBNOTE VARCHAR(2000)';
		EXECUTE immediate v_sql;

		SET v_sql= 'ALTER TABLE PIATRI
			ADD COLUMN URLAPPROV VARCHAR(2000)
			ADD COLUMN URLADOZI VARCHAR(2000)
			ADD COLUMN TITADOZI VARCHAR(250)
			ADD COLUMN TITAPPROV VARCHAR(250)
			ADD COLUMN DPADOZI TIMESTAMP
			ADD COLUMN DPAPPROV TIMESTAMP
			ADD COLUMN ID_GENERATO NUMERIC(10)
			ADD COLUMN ID_RICEVUTO NUMERIC(10)
			ADD COLUMN ID_CLIENT VARCHAR(255)
			ADD COLUMN SYSCON NUMERIC(12)
			ADD COLUMN IDUFFICIO NUMERIC(10)
			ALTER COLUMN NADOZI set DATA TYPE VARCHAR(50)
			ALTER COLUMN NAPTRI set DATA TYPE VARCHAR(50)';
		EXECUTE immediate v_sql;
		
		--- Aggiunto campo temporaneo di appoggio per conversione dei campi PIATRI.AP1TRi, PIATRI.AP2TRI e PIATRI.AP3TRI
		SET v_sql= 'ALTER TABLE PIATRI ADD COLUMN TEMP_AP123TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		
		--- Conversione del campo PIATRI.AP1TRI da NUMERIC(24) a NUMERIC(24,5)
		SET v_sql= 'UPDATE PIATRI set TEMP_AP123TRI=AP1TRI';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE PIATRI DROP COLUMN AP1TRI';
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'ALTER TABLE PIATRI ADD COLUMN AP1TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'UPDATE PIATRI set AP1TRI=TEMP_AP123TRI';
		EXECUTE immediate v_sql;
		SET v_sql= 'UPDATE PIATRI set TEMP_AP123TRI=null';
		EXECUTE immediate v_sql;
		
		--- Conversione del campo PIATRI.AP2TRI da NUMERIC(24) a NUMERIC(24,5)
		SET v_sql= 'UPDATE PIATRI set TEMP_AP123TRI=AP2TRI';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE PIATRI DROP COLUMN AP2TRI';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'ALTER TABLE PIATRI ADD COLUMN AP2TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'UPDATE PIATRI set AP2TRI=TEMP_AP123TRI';
		EXECUTE immediate v_sql;
		SET v_sql= 'UPDATE PIATRI set TEMP_AP123TRI=null';
		EXECUTE immediate v_sql;
		
		--- Conversione del campo PIATRI.AP3TRI da NUMERIC(24) a NUMERIC(24,5)
		SET v_sql= 'UPDATE PIATRI set TEMP_AP123TRI=AP3TRI';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE PIATRI DROP COLUMN AP3TRI';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'ALTER TABLE PIATRI ADD COLUMN AP3TRI NUMERIC(24,5)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'UPDATE PIATRI set AP3TRI=TEMP_AP123TRI';
		EXECUTE immediate v_sql;
		
		--- Rimosso campo temporaneo di appoggio
		SET v_sql= 'ALTER TABLE PIATRI DROP COLUMN TEMP_AP123TRI';
		EXECUTE immediate v_sql;

		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE IMMTRAI');
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE RIS_CAPITOLO');
		
		-- Modifica tipo di campo da numerico a varchar o viceversa
		SET v_sql= 'ALTER TABLE PIATRI ADD COLUMN TEMP_PUBBLICA VARCHAR(1)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'Update PIATRI SET TEMP_PUBBLICA = CAST(CAST(PUBBLICA AS CHAR) AS VARCHAR(1))';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE PIATRI DROP COLUMN PUBBLICA';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');
		SET v_sql= 'ALTER TABLE PIATRI ADD COLUMN PUBBLICA VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql= 'Update PIATRI SET PUBBLICA = TEMP_PUBBLICA';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE PIATRI DROP COLUMN TEMP_PUBBLICA';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE PIATRI');

		SET v_sql= 'ALTER TABLE INTTRI 
									ADD COLUMN TEMP_FLAG_CUP VARCHAR(1)
									ADD COLUMN TEMP_DELEGA VARCHAR(1)
									ADD COLUMN TEMP_COPFIN VARCHAR(1)
									ADD COLUMN TEMP_MATRIC NUMERIC(7)
									ADD COLUMN TEMP_ACQALTINT NUMERIC(7)
									ADD COLUMN TEMP_VARIATO NUMERIC(7)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql= 'Update INTTRI SET TEMP_FLAG_CUP = CAST(CAST(FLAG_CUP AS CHAR) AS VARCHAR(1)),
									TEMP_DELEGA = CAST(CAST(DELEGA AS CHAR) AS VARCHAR(1)),
									TEMP_COPFIN = CAST(CAST(COPFIN AS CHAR) AS VARCHAR(1)),
									TEMP_MATRIC =  CAST(MATRIC AS INT ),
									TEMP_ACQALTINT = case ACQALTINT when ''1'' Then 1 when ''2'' then 2 end,
									TEMP_VARIATO =  CAST(VARIATO AS INT)';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE INTTRI 
									DROP COLUMN FLAG_CUP
									DROP COLUMN DELEGA
									DROP COLUMN COPFIN
									DROP COLUMN MATRIC
									DROP COLUMN ACQALTINT
									DROP COLUMN VARIATO';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');	
		SET v_sql= 'ALTER TABLE INTTRI 
									ADD COLUMN FLAG_CUP VARCHAR(1)
									ADD COLUMN DELEGA VARCHAR(1)
									ADD COLUMN COPFIN VARCHAR(1)
									ADD COLUMN MATRIC NUMERIC(7)
									ADD COLUMN ACQALTINT NUMERIC(7)
									ADD COLUMN VARIATO NUMERIC(7)';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');
		SET v_sql= 'Update INTTRI SET FLAG_CUP = TEMP_FLAG_CUP,
									DELEGA = TEMP_DELEGA,
									COPFIN = TEMP_COPFIN,
									MATRIC = TEMP_MATRIC,
									ACQALTINT = TEMP_ACQALTINT,
									VARIATO = TEMP_VARIATO';
		EXECUTE immediate v_sql;	
		SET v_sql= 'ALTER TABLE INTTRI 
									DROP COLUMN TEMP_FLAG_CUP
									DROP COLUMN TEMP_DELEGA
									DROP COLUMN TEMP_COPFIN
									DROP COLUMN TEMP_MATRIC
									DROP COLUMN TEMP_ACQALTINT
									DROP COLUMN TEMP_VARIATO';
		EXECUTE immediate v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');	
	
	
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO ADD COLUMN TEMP_VERIFBIL VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql= 'Update RIS_CAPITOLO SET TEMP_VERIFBIL = CAST(CAST(VERIFBIL AS CHAR) AS VARCHAR(1))';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO DROP COLUMN VERIFBIL';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO ADD COLUMN VERIFBIL VARCHAR(1)';
		EXECUTE immediate v_sql;
		SET v_sql= 'Update RIS_CAPITOLO SET VERIFBIL = TEMP_VERIFBIL';
		EXECUTE immediate v_sql;
		SET v_sql= 'ALTER TABLE RIS_CAPITOLO DROP COLUMN TEMP_VERIFBIL';
		EXECUTE immediate v_sql;


		SET v_sql= 'CREATE VIEW V_REPORT_SOGG_AGGR as 
				SELECT u.cfein AS codicefiscaleente,
				cast (null as varchar(20)) AS codiceipa,
				u.nomein AS amministrazione,
				cast (null as varchar(20)) AS dipartimento,
				cast (null as varchar(20)) AS ufficio,
				s.tabdesc AS regione,
				u.proein AS provincia,
				COALESCE(u.viaein, '') || COALESCE(u.nciein, '') AS indirizzoente,
				u.telein AS telefonoente,
				u.emaiin AS mailente,
				u.emai2in AS pecente,
				t.nometei AS nomereferente,
				t.cogtei AS cognomereferente,
				t.cftec AS codicefiscalereferente,
				t.teltec AS telefonoreferente,
				t.ematec AS mailreferente,
				a.cuiint AS numerointerventocui,
				u.cfein AS codicefiscaleamministrazione,
				    CASE
				        WHEN substr(a.cuiint, 1, 1) in (''L'', ''F'', ''S'') THEN substr(a.cuiint, 13, 4)
				    END AS primaannualita,
				p.anntri + COALESCE(a.annrif, 0) - 1 AS anno,
				cast(''001'' as varchar(3))  AS identificativoproceduraacquisto,
				a.cupprg AS codicecup,
				    CASE
				        WHEN a.lotfunz = ''1'' THEN ''Si''
				        WHEN a.lotfunz = ''2'' THEN ''No''
				    END AS lottofunzionale,
				round(COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0), 2) AS importostimatolotto,
				n.descrizione AS ambitogeografico,
				a.cupmst AS codicecupmaster,
				t2.tab2d2 AS settore,
				a.codcpv AS cpv,
				a.desint AS descrizioneacquisto,
				cast (null as varchar(2)) AS conformitaambientale,
				    CASE
				        WHEN a.prgint IS NOT NULL THEN ''Livello '' || cast(a.prgint as char(1))
				    END AS priorita,
				rup.cftec AS codiceriscaleresponsabileprocedimento,
				rup.cogtei AS cognomeresponsabileprocedimento,
				rup.nometei AS nomeresponsabileprocedimento,
				a.quantit AS quantita,
				um.tab1desc AS unitamisura,
				a.durcont AS duratacontratto,
				round(COALESCE(a.di1int, 0) + COALESCE(a.pr1tri, 0), 2) AS stimacostiprogrammaprimoanno,
				round(COALESCE(a.di2int, 0) + COALESCE(a.pr2tri, 0), 2) AS stimacostiprogrammasecondoanno,
				round(COALESCE(a.di9int, 0) + COALESCE(a.pr9tri, 0), 2) AS costiannualitasuccessive,
				round(COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0), 2) AS stimacostiprogrammatotale,
				round(a.icpint, 2) AS importoapportocapitaleprivato,
				t3.tab3desc AS tipologiaapportocapitaleprivato,
				    CASE
				        WHEN a.delega = ''1'' THEN ''Si''
				        WHEN a.delega = ''2'' THEN ''No''
				    END AS delega,
				a.codausa AS codiceausa,
				a.sogagg AS denominazioneamministrazionedelegata,
				p.contri
		   FROM piatri p
				 JOIN uffint u ON u.codein = p.cenint
				 LEFT JOIN tecni t ON t.codtec = p.respro
				 LEFT JOIN inttri a ON a.contri = p.contri
				 LEFT JOIN tabnuts n ON n.codice = substr(a.nuts, 1, 4)
				 LEFT JOIN tab2 t2 ON t2.tab2cod = ''W3z05'' AND t2.tab2tip = a.tipoin
				 LEFT JOIN tecni rup ON rup.codtec = a.codrup
				 LEFT JOIN tab3 t3 ON t3.tab3cod = ''PTx08'' AND t3.tab3tip = a.tcpint
				 LEFT JOIN tabsche s ON s.tabcod = ''S2003'' AND s.tabcod1 = ''05'' AND s.tabcod2 = (''0'' || substr(u.codcit, 1, 3))
				 LEFT JOIN tab1 um ON um.tab1cod = ''PT019'' AND um.tab1tip = a.unimis
		  WHERE p.tiprog = 2 AND (COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0)) > 1000000';
		EXECUTE immediate v_sql;
		  
		  		----------------------------------------------
		---   Aggiornamento dati Piani Triennali   ---
		----------------------------------------------
		
		Update INTTRI set ANNRIF=1 where ANNRIF is null and TIPINT in (2,3);
		Update INTTRI set TIPINT= (select TIPROG from PIATRI P where P.CONTRI=INTTRI.CONTRI) where TIPINT is null;
		
		Delete from TAB_CONTROLLI where codapp='PT';
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',10,'PIATRI','Programma',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',20,'PIATRI','Dati generali','CENINT','EXISTS (SELECT 1 FROM UFFINT A WHERE A.CODEIN= T.CENINT and A.NOMEIN is not null)','Inserire l''Amministrazione','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',30,'PIATRI','Dati generali','RESPRO','EXISTS (SELECT 1 FROM TECNI A WHERE A.CODTEC= T.RESPRO and A.NOMTEC is not null)','Indicare il responsabile del programma','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',40,'PIATRI','Dati generali','ID','T.ID is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',50,'PIATRI','Dati generali','BRETRI','T.BRETRI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',60,'PIATRI','Dati generali','ANNTRI','T.ANNTRI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',70,'PIATRI','Dati generali','Elenco interventi','EXISTS (SELECT 1 FROM INTTRI I WHERE I.CONTRI=T.CONTRI)','Il programma non contiene interventi','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',80,'PIATRI','Archivio tecnici','TECNI.CITTEC','EXISTS (SELECT 1 from TECNI A WHERE A.CODTEC=T.RESPRO and A.CITTEC is not null)','Indicare il codice ISTAT del comune del responsabile del programma','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',81,'PIATRI','Archivio tecnici','TECNI.CFTEC','EXISTS (SELECT 1 from TECNI A WHERE A.CODTEC=T.RESPRO and A.CFTEC is not null)','Indicare il codice fiscale del responsabile del programma','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',90,'PIATRI','Adozione/approvazione','Adozione o approvazione','(T.DADOZI is not null or T.DAPTRI is not null) or tiprog in (2,3)','Inserire i dati di adozione e/o di approvazione del programma','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',91,'PIATRI','Adozione','DPADOZI','(T.DADOZI is null or T.DPADOZI is not null) or tiprog=3','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',92,'PIATRI','Adozione','TITADOZI','(T.DADOZI is null or T.TITADOZI is not null) or tiprog=3','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',93,'PIATRI','Adozione','URLADOZI','(T.DADOZI is null or T.URLADOZI is not null) or tiprog=3','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',94,'PIATRI','Approvazione','DPAPPROV','T.DAPTRI is null or T.DPAPPROV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',95,'PIATRI','Approvazione','TITAPPROV','T.DAPTRI is null or T.TITAPPROV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',96,'PIATRI','Approvazione','URLAPPROV','T.DAPTRI is null or T.URLAPPROV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',97,'PIATRI','Approvazione','Approvazione','T.DAPTRI is not null or tiprog=1','Inserire i dati di approvazione del programma','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',100,'INTTRI','Intervento n. {0} del programma',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',110,'INTTRI','Dati generali','CUIINT','T.CUIINT is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',120,'INTTRI','Dati generali','DESINT','T.DESINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',130,'INTTRI','Dati generali','ANNRIF','T.ANNRIF is not null or exists (select 1 from piatri p join inttri i on i.contri=p.contri and i.tipint=2 where p.tiprog=3 and p.contri=T.contri)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',140,'INTTRI','Dati generali','FLAG_CUP','T.FLAG_CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',150,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is not null or T.FLAG_CUP=''1'' or ANNINT=''2''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',160,'INTTRI','Dati generali','INTTRI.ACQALTINT','(T.ACQALTINT is not null or T.TIPINT=1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',170,'INTTRI','Dati generali','INTTRI.CUICOLL','(T.CUICOLL is not null or coalesce(T.ACQALTINT,1)<>2) or  T.TIPINT=3','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',180,'INTTRI','Dati generali','Codice ISTAT o NUTS del comune','T.COMINT is not null or T.TIPINT=2 or T.NUTS is not null','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',190,'INTTRI','Dati generali','NUTS','(T.NUTS is not null or T.TIPINT=1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',200,'INTTRI','Dati generali','TIPOIN','T.TIPOIN is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',210,'INTTRI','Dati generali','CODCPV','(T.CODCPV is not null or T.TIPINT=1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',220,'INTTRI','Dati generali','PRGINT','T.PRGINT is not null ','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',230,'INTTRI','Dati generali','CODRUP','EXISTS (SELECT 1 FROM TECNI A WHERE A.CODTEC= T.CODRUP and A.NOMTEC is not null)','Indicare il responsabile dell''intervento','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',240,'INTTRI','Dati generali','LOTFUNZ','T.LOTFUNZ is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',250,'INTTRI','Dati generali','LAVCOMPL','T.LAVCOMPL is not null or T.TIPINT=2 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',260,'INTTRI','Dati generali','DURCONT','T.DURCONT is not null or T.TIPINT=1 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',270,'INTTRI','Dati generali','CONTESS','T.CONTESS is not null or T.TIPINT=1 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',280,'INTTRI','Dati generali','SEZINT','T.SEZINT is not null or T.TIPINT=2 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',290,'INTTRI','Dati generali','INTERV','T.INTERV is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',300,'INTTRI','Dati generali','ANNINT','T.ANNINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',310,'INTTRI','Dati generali','TCPINT','T.TCPINT is not null or coalesce(PR1TRI,0)+coalesce(PR2TRI,0)+coalesce(PR3TRI,0)+coalesce(PR9TRI,0)=0','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',320,'INTTRI','Dati elenco annuale','FIINTR','T.FIINTR is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',330,'INTTRI','Dati elenco annuale','URCINT','T.URCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',340,'INTTRI','Dati elenco annuale','APCINT','T.APCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',350,'INTTRI','Dati elenco annuale','STAPRO','T.STAPRO is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',490,'INTTRI','Modalit&agrave; di affidamento','DELEGA','T.DELEGA is not null or T.ANNINT<>''1'' or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',500,'INTTRI','Modalit&agrave; di affidamento','CODAUSA','T.CODAUSA is not null or T.DELEGA=2 or (T.TIPINT=1 and T.ANNINT<>''1'') or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',510,'INTTRI','Modalit&agrave; di affidamento','SOGAGG','T.SOGAGG is not null or T.DELEGA=2 or (T.TIPINT=1 and T.ANNINT<>''1'') or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',800,'OITRI','Opera incompiuta n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',810,'OITRI',null,'NUMOI','T.NUMOI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',820,'OITRI',null,'CUP','T.CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',830,'OITRI',null,'DESCRIZIONE','T.DESCRIZIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',840,'OITRI',null,'DETERMINAZIONI','T.DETERMINAZIONI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',850,'OITRI',null,'AMBITOINT','T.AMBITOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',860,'OITRI',null,'ANNOULTQE','T.ANNOULTQE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',870,'OITRI',null,'IMPORTOINT','T.IMPORTOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',880,'OITRI',null,'IMPORTOLAV','T.IMPORTOLAV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',890,'OITRI',null,'ONERIULTIM','T.ONERIULTIM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',900,'OITRI',null,'IMPORTOSAL','T.IMPORTOSAL is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',910,'OITRI',null,'AVANZAMENTO','T.AVANZAMENTO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',920,'OITRI',null,'CAUSA','T.CAUSA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',930,'OITRI',null,'STATOREAL','T.STATOREAL is not null','Indicare lo stato di realizzazione','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',940,'OITRI',null,'INFRASTRUTTURA','T.INFRASTRUTTURA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',950,'OITRI',null,'FRUIBILE','T.FRUIBILE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',960,'OITRI',null,'UTILIZZORID','T.UTILIZZORID is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',970,'OITRI',null,'DESTINAZIONEUSO','T.DESTINAZIONEUSO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',980,'OITRI',null,'CESSIONE','T.CESSIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',990,'OITRI',null,'VENDITA','T.VENDITA is not null','Indicare se &egrave; prevista la vendita','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1000,'OITRI',null,'DEMOLIZIONE','T.DEMOLIZIONE is not null or coalesce(T.VENDITA,''1'')=''1''','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1200,'IMMTRAI','Immobile da trasferire n. {0} dell''intervento',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1210,'IMMTRAI',null,'NUMIMM','T.NUMIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1220,'IMMTRAI',null,'CUIIMM','T.CUIIMM is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1230,'IMMTRAI',null,'DESIMM','T.DESIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1240,'IMMTRAI',null,'Codice ISTAT o NUTS del comune','T.COMIST is not null or T.NUTS is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1250,'IMMTRAI',null,'TITCOR','T.TITCOR is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1260,'IMMTRAI',null,'IMMDISP','T.IMMDISP is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1270,'IMMTRAI',null,'PROGDISM','T.PROGDISM is not null or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1280,'IMMTRAI',null,'TIPDISP','(T.TIPDISP is not null or T.NUMOI is null) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1290,'IMMTRAI',null,'VALIMM','T.VALIMM is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1400,'INRTRI','Intervento non riproposto n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1410,'INRTRI',null,'CUPPRG','T.CUPPRG is not null','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1420,'INRTRI',null,'DESINT','T.DESINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1430,'INRTRI',null,'TOTINT','T.TOTINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1440,'INRTRI',null,'PRGINT','T.PRGINT is not null','Valorizzare il campo','W');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1450,'INRTRI',null,'MOTIVO','T.MOTIVO is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1600,'RIS_CAPITOLO','Risorse per capitolo di bilancio n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,num,entita,sezione,titolo,condizione,msg,tipo) VALUES ('PT',1610,'RIS_CAPITOLO',null,'NCAPBIL','T.NCAPBIL is not null','Valorizzare il campo','E');


		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.PIATRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.INRTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONINT.INRTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.OITRI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='NUMOI.OITRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='ID.CUPDATI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='ID.CUPLOC.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='NUM.CUPLOC.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.ECOTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONECO.ECOTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.IMMTRAI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONINT.IMMTRAI.PIANI';
		Update C0CAMPI SET COC_CONTA=3 WHERE COC_MNE_UNI='NUMIMM.IMMTRAI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.INTTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONINT.INTTRI.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='CONTRI.RIS_CAPITOLO.PIANI';
		Update C0CAMPI SET COC_CONTA=2 WHERE COC_MNE_UNI='CONINT.RIS_CAPITOLO.PIANI';
		Update C0CAMPI SET COC_CONTA=3 WHERE COC_MNE_UNI='NUMCB.RIS_CAPITOLO.PIANI';
		Update C0CAMPI SET COC_CONTA=1 WHERE COC_MNE_UNI='IDFLUSSO.PTFLUSSI.PIANI';

		Delete from C0CAMPI where COC_MNE_UNI in ('URCINT.INTTRI.PIANI','APCINT.INTTRI.PIANI', 'IM1INT.INTTRI.PIANI', 'IM2INT.INTTRI.PIANI', 'IM3INT.INTTRI.PIANI','TIPCAPPRIV.INTTRI.PIANI') or  COC_MNE_UNI like 'VA_TRI.IMMTRAI.PIANI';

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUMOI.IMMTRAI.PIANI','NUMOIIMMTRAI','Contatore opera (progressivo per programma)','Contatore opera','N6',null,null,'Contatore opera (progressivo per programma)');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DIM_QTA.OITRI.PIANI','DIMQTOITRP','Dimensionamento dell''opera (valore)','Dim.Val.Opera','F24.5',null,null,'Dimensionamento dell''opera (valore)');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DIM_UM.OITRI.PIANI','DIMUMOITRIP','Dimensionamento dell''opera (unita'' di misura)','Dim. opera','A15',null,null,'Dimensionamento dell''opera (unita'' di misura)');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FINANZIAMENTO.OITRI.PIANI','FINANOITRP','Finanziamento assegnato','Fin. assegnato','F24.5',null,'MONEY','Finanziamento assegnato');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FINANZA_PROGETTO.OITRI.PIANI','FINPRGOITRI','Finanza di progetto','Fin. progetto','A2',null,'SN','Finanza di progetto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ISTAT.OITRI.PIANI','ISTATOIT','Codice ISTAT','Codice ISTAT','A20',null,null,'Codice ISTAT');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NUTS.OITRI.PIANI','NUTCODOI','Codice NUTS','Codice NUTS','A5',null,null,'Codice NUTS');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'REQ_CAP.OITRI.PIANI','REQCAPOITRI','Opera rispondente a tutti i requisiti di capitolato?','Req. Capitolato?','A2',null,'SN','Opera rispondente a tutti i requisiti di capitolato?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'REQ_PRGE.OITRI.PIANI','REQPRGOITRI','Rispondente a tutti i requisiti ultimo progetto approvato?','Req.Ult.Progetto?','A2',null,'SN','Opera rispondente a tutti i requisiti dell''ultimo progetto approvato?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SPONSORIZZAZIONE.OITRI.PIANI','SPONSOITRIP','Sponsorizzazione','Sponsorizzazione','A2',null,'SN','Sponsorizzazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'INTERV.OITRI.PIANI','T2INTEOI','Classificazione intervento: categoria','Categoria','A100','PTj01',null,'Classificazione intervento: categoria');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SEZINT.OITRI.PIANI','T2SEZOIT','Classificazione intervento: Tipologia','Tipologia intervento','A100','PTx01',null,'Classificazione intervento: Tipologia');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COSTO.OITRI.PIANI','COSTOOITRP','Costo progetto','Costo progetto','F24.5',null,'MONEY','Costo progetto');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_ALTRAPUBBLICA.OITRI.PIANI','CPAPOITRI','Altra Pubblica','Altra Pubblica','A2',null,'SN','Altra Pubblica');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_COMUNITARIA.OITRI.PIANI','CPCMOITRI','Comunitaria','Comunitaria','A2',null,'SN','Comunitaria');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_COMUNALE.OITRI.PIANI','CPCOOITRI','Comunale','Comunale','A2',null,'SN','Comunale');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_PROVINCIALE.OITRI.PIANI','CPPROITRI','Provinciale','Provinciale','A2',null,'SN','Provinciale');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_PRIVATA.OITRI.PIANI','CPPVOITRI','Privata','Privata','A2',null,'SN','Privata');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_REGIONALE.OITRI.PIANI','CPREOITRI','Regionale','Regionale','A2',null,'SN','Regionale');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'COP_STATALE.OITRI.PIANI','CPSTOITRI','Statale','Statale','A2',null,'SN','Statale');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ONERI_SITO.OITRI.PIANI','ONESIOITRP','Oneri Sito','Oneri Sito','F24.5',null,'MONEY','Oneri Sito');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CBNOTE.RIS_CAPITOLO.PIANI','T2NOTEECB','Note','Note','A2000',null,'NOTE','Note');
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'URCINT.INTTRI.PIANI','T2URCINT','Svolta verifica conformità urbanistica?','Conforme urb.ter.?','A2',null,'SN','Svolta verifica conformità urbanistica?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'APCINT.INTTRI.PIANI','T2APCINT','Svolta verifica conformità vincoli ambientali?','Conforme vinc.amb.?','A2',null,'SN','Svolta verifica conformità vincoli ambientali?');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VA1IMM.IMMTRAI.PIANI','T2IVA1INT','Valore stimato dell''immobile - primo anno','Val. stimato 1'' anno','F15',null,'MONEY','Valore stimato dell''immobile - primo anno');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VA2IMM.IMMTRAI.PIANI','T2IVA2INT','Valore stimato dell''immobile - secondo anno ','Val. stimato 2'' anno','F15',null,'MONEY','Valore stimato dell''immobile - secondo anno');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VA3IMM.IMMTRAI.PIANI','T2IVA3INT','Valore stimato dell''immobile - terzo anno','Val. stimato 3'' anno','F15',null,'MONEY','Valore stimato dell''immobile - terzo anno');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'VA9IMM.IMMTRAI.PIANI','T2IVA9INT','Valore stimato dell''immobile - annualità successive','Valore ann. succ.','F24.5',null,'MONEY','Valore stimato dell''immobile - annualità successive');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ALIENATI.IMMTRAI.PIANI','T2ALIEN','Alienati per finanziamento e realizzazione di oopp','Alienati?','A2',null,'SN','Alienati per finanziamento e realizzazione di oopp');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IM9TRI.INTTRI.PIANI','T2IIM9TRI','Trasferimento immobili (annualità successive)','Imp. immob. succ.','F15',null,'MONEY','Trasferimento immobili (annualità successive)');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'URLAPPROV.PIATRI.PIANI','T2URLAPPROV','Url atto di approvazione','Url atto approvaz.','A2000',null,null,'Url atto di approvazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'URLADOZI.PIATRI.PIANI','T2URLADOZI','Url atto di adozione','Url atto adozione','A2000',null,null,'URL atto di adozione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_RICEVUTO.PIATRI.PIANI','T2IDR','ID ricevuto','ID ricevuto','N10',null,null,'ID ricevuto');
		
		Update C0CAMPI set COC_DES='Opera fruibile parzialmente?',COC_DES_WEB='Opera fruibile parzialmente?' where COC_MNE_UNI='FRUIBILE.OITRI.PIANI';
		Update C0CAMPI set COC_DES='Cessione o trasferimento immobile a titolo corrispettivo',COC_DES_WEB='Cessione o trasferimento immobile a titolo corrispettivo' where COC_MNE_UNI='TITCOR.IMMTRAI.PIANI';
		Update C0CAMPI set COC_DES='Concessi in diritto di godimento',COC_DES_WEB='Concessi in diritto di godimento',coc_des_frm='Diritto di godimento' where COC_MNE_UNI='IMMDISP.IMMTRAI.PIANI';
		Update C0CAMPI set COC_DES='Tipologia apporto di capitale privato' where COC_MNE_UNI='TCPINT.INTTRI.PIANI';
		Update C0CAMPI set C0C_FS='A25' where COC_MNE_UNI='CUIINT.INTTRI.PIANI';
		Update C0CAMPI set C0C_FS='A25' where COC_MNE_UNI='CUIINT.INRTRI.PIANI';
		Update C0CAMPI set c0c_fs='A100',c0c_tab1='PT022',coc_dom=null where COC_MNE_UNI='ACQALTINT.INTTRI.PIANI';
		
		Update C0CAMPI set COC_DES='Sono presenti acq. verdi art. 34 Dlgs 50/2016', COC_DES_WEB='Sono presenti acq. verdi art. 34 Dlgs 50/2016', COC_DES_FRM='Acq.VerdiAr.34-Dlgs' where coc_des='Intervento riguardante acquisti verdi?';
		Update C0CAMPI set COC_DES='Codice AUSA del Soggetto delegato', COC_DES_WEB='Codice AUSA del Soggetto delegato' where coc_des='Codice AUSA del Soggetto aggregatore';
		Update C0CAMPI set COC_DES='Denominazione del Soggetto delegato', COC_DES_WEB='Denominazione del Soggetto delegato' where coc_des='Denominazione del Soggetto aggregatore';

		Update C0CAMPI set COC_DES='Subcategoria intervento' where COC_MNE_UNI='CATINT.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Classificazione intervento: ex sottocategoria' where COC_MNE_UNI='CLAINT.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Finalità dell''intervento',C0C_FS='A100' where COC_MNE_UNI='FIINTR.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Classificazione intervento: ex categoria' where COC_MNE_UNI='SETINT.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Classificazione intervento: Tipologia' where COC_MNE_UNI='SEZINT.INTTRI.PIANI';
		Update C0CAMPI set COC_DES='Stato Progettazione approvata' where COC_MNE_UNI='STAPRO.INTTRI.PIANI';
		
		Update C0CAMPI set C0C_TAB1='PTx08' WHERE COC_MNE_UNI = 'TCPINT.INTTRI.PIANI';
		Update C0CAMPI set C0C_TAB1='PTx09' WHERE COC_MNE_UNI = 'STAPRO.INTTRI.PIANI';

		Update C0CAMPI set C0C_FS='A2000' where COC_MNE_UNI='NOTSCHE4.PIATRI.PIANI';

		Delete from  TAB1 where TAB1COD in ('PT010','PT011');
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',1,'Modifica ex art.7 comma 8 lettera b)','2',1,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',2,'Modifica ex art.7 comma 8 lettera c)','2',2,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',3,'Modifica ex art.7 comma 8 lettera d)','2',3,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',4,'Modifica ex art.7 comma 8 lettera e)','2',4,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT010',5,'Modifica ex art.7 comma 9','2',5,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',1,'Modifica ex art.5 comma 9 lettera b)','2',1,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',2,'Modifica ex art.5 comma 9 lettera c)','2',2,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',3,'Modifica ex art.5 comma 9 lettera d)','2',3,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',4,'Modifica ex art.5 comma 9 lettera e)','2',4,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT011',5,'Modifica ex art.5 comma 11','2',5,null);
		
		Delete from TAB1 where TAB1COD='PT016';
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',1,'Cessione della titolarità dell''opera ad altro ente pubblico',null,1.00,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',2,'Cessione della titolarità dell''opera a soggetto esercente una funzione pubblica',null,2.00,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',3,'Vendita al mercato privato',null,3.00,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) VALUES ('PT016',4,'Disponibilità come fonte di finanziamento per realizzazione di intervento ai sensi del c. 5 art.21',null,4.00,null);
		
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',1,'No','2',1,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',2,'Sì','2',2,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',3,'Sì, con CUI non ancora attribuito','2',3,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT022',4,'Sì, interventi o acquisti diversi','2',4,null);
		
		-- tipologia apporto capitale privato
		Delete from TAB1 where TAB1COD='PT009';
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','1','Finanza di progetto',null,1,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','2','Concessione',null,2,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','3','Sponsorizzazione',null,3,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','4','Società partecipate o di scopo',null,4,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','5','Locazione finanziaria',null,5,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','6','Contratto di disponibilità ',null,6,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx08','9','Altro',null,9,null);

		-- Satato progettazione approvato
		Delete from TAB1 where TAB1COD='PT012';
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx09','1','Progetto di fattibilità tecnico-economica: documento di fattibilità delle alternative progettuali',null,1,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx09','2','Progetto di fattibilità tecnico-economica: documento finale',null,2,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx09','3','Progetto definitivo',null,3,null);
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) values ('PTx09','4','Progetto esecutivo',null,4,null);


		-- Classificazione intervento: Tipologia
		Delete from TAB3 where TAB3cod='PTx01';
		Insert into TAB3 (TAB3COD,TAB3TIP,TAB3DESC,TAB3MOD,TAB3NORD,TAB3ARC) VALUES
		('PTx01','01','01 - Nuova realizzazione',null,1,null),
		('PTx01','02','02 - Demolizione',null,2,null),
		('PTx01','03','03 - Recupero',null,3,null),
		('PTx01','04','04 - Ristrutturazione',null,4,null),
		('PTx01','05','05 - Restauro',null,5,null),
		('PTx01','08','08 - Ristrutturazione con efficientamento energetico',null,6,null),
		('PTx01','09','09 - Manutenzione straordinaria con efficientamento energetico',null,7,null),
		('PTx01','06','06 - Manutenzione ordinaria',null,8,null),
		('PTx01','07','07 - Manutenzione straordinaria',null,9,null),
		('PTx01','58','58 - Ampliamento o potenziamento',null,10,null),
		('PTx01','60','60 - Ammodernamento tecnologico e laboratoriale',null,11,null),
		('PTx01','59','59 - Lavori socialmente utili',null,12,null),
		('PTx01','99','99 - Altro',null,13,null);

		-- Classificazione intervento: categoria
		Delete from TAB5 where TAB5COD='PTj01';
		Insert into TAB5 (TAB5COD,TAB5TIP,TAB5DESC,TAB5MOD,TAB5NORD,TAB5ARC) VALUES
		('PTj01','01.01','01.01 - Stradali',null,1,null),
		('PTj01','01.02','01.02 - Aeroportuali',null,2,null),
		('PTj01','01.03','01.03 - Ferrovie',null,3,null),
		('PTj01','01.04','01.04 - Marittime lacuali e fluviali',null,4,null),
		('PTj01','01.05','01.05 - Trasporto urbano',null,5,null),
		('PTj01','01.06','01.06 - Trasporti multimodali e altre modalita'' di trasporto',null,6,null),
		('PTj01','02.05','02.05 - Difesa del suolo',null,7,null),
		('PTj01','02.10','02.10 - Smaltimento rifiuti',null,8,null),
		('PTj01','02.11','02.11 - Protezione, valorizzazione e fruizione dell''ambiente',null,9,null),
		('PTj01','02.12','02.12 - Riassetto e recupero di siti urbani e produttivi',null,10,null),
		('PTj01','02.15','02.15 - Risorse idriche e acque reflue',null,11,null),
		('PTj01','03.06','03.06 - Produzione di energia',null,12,null),
		('PTj01','03.16','03.16 - Distribuzione di energia',null,13,null),
		('PTj01','04.39','04.39 - Infrastrutture per l''attrezzatura di aree produttive',null,14,null),
		('PTj01','05.08','05.08 - Sociali e scolastiche',null,15,null),
		('PTj01','05.10','05.10 - Abitative',null,16,null),
		('PTj01','05.11','05.11 - Beni culturali',null,17,null),
		('PTj01','05.12','05.12 - Sport, spettacolo e tempo libero',null,18,null),
		('PTj01','05.30','05.30 - Sanitarie',null,19,null),
		('PTj01','05.31','05.31 - Culto',null,20,null),
		('PTj01','05.32','05.32 - Difesa',null,21,null),
		('PTj01','05.33','05.33 - Direzionali e amministrative',null,22,null),
		('PTj01','05.34','05.34 - Giudiziarie e penitenziarie',null,23,null),
		('PTj01','05.36','05.36 - Pubblica sicurezza',null,24,null),
		('PTj01','05.99','05.99 - Altre infrastrutture sociali',null,25,null),
		('PTj01','06.02','06.02 - Opere, impianti ed attrezzature per il settore silvo-forestale',null,26,null),
		('PTj01','06.13','06.13 - Opere, impianti ed attrezzature per l''agricoltura, la zootecnia e l''agroalimentare',null,27,null),
		('PTj01','06.14','06.14 - Impianti ed attrezzature per la pesca e l''acquacoltura',null,28,null),
		('PTj01','06.39','06.39 - Opere, impianti ed attrezzature per attivita'' industriali e l''artigianato',null,29,null),
		('PTj01','06.40','06.40 - Opere e infrastrutture per la ricerca',null,30,null),
		('PTj01','06.41','06.41 - Opere e strutture per il turismo',null,31,null),
		('PTj01','06.42','06.42 - Strutture ed attrezzature per il commercio e i servizi',null,32,null),
		('PTj01','06.43','06.43 - Opere e infrastrutture per l''impresa sociale',null,33,null),
		('PTj01','07.17','07.17 - Infrastrutture per telecomunicazioni',null,34,null),
		('PTj01','07.18','07.18 - Tecnologie informatiche',null,35,null),
		-- servizi
		('PTj01','08.60','08.60 - Progetti di diffusione e cooperazione pubblico-privata',null,36,null),
		('PTj01','08.61','08.61 - Progetti di ricerca presso universita'' e istituti di ricerca',null,37,null),
		('PTj01','08.62','08.62 - Progetti di ricerca e  di innovazione presso imprese',null,38,null),
		('PTj01','09.20','09.20 - Servizi alle imprese agricole, forestali e della pesca',null,39,null),
		('PTj01','09.21','09.21 - Servizi alle imprese industriali',null,40,null),
		('PTj01','09.22','09.22 - Servizi alle imprese turistiche',null,41,null),
		('PTj01','09.23','09.23 - Servizi di intermediazione finanziaria',null,42,null),
		('PTj01','09.25','09.25 - Servizi alle imprese del commercio',null,43,null),
		('PTj01','09.26','09.26 - Servizi alle nuove imprese (start up) e alle imprese sociali',null,44,null),
		('PTj01','10.01','10.01 - Servizi e tecnologie per l''informazione e le comunicazioni',null,45,null),
		('PTj01','10.02','10.02 - Servizi e applicazioni informatiche per i cittadini e le imprese',null,46,null),
		('PTj01','10.03','10.03 - Azioni innovatrici',null,47,null),
		('PTj01','10.30','10.30 - Servizi a supporto dello sviluppo e della qualificazione del sistema del lavoro',null,48,null),
		('PTj01','10.32','10.32 - Dispositivi e strumenti a supporto della qualificazione del sistema dell''offerta di formazione',null,49,null),
		('PTj01','10.33','10.33 - Dispositivi e strumenti a supporto della qualificazione del sistema dell''offerta di istruzione',null,50,null),
		('PTj01','10.34','10.34 - Dispositivi e strumenti a supporto dell''integrazione fra sistemi',null,51,null),
		('PTj01','10.35','10.35 - Dispositivi e strumenti a supporto della qualificazione nel sistema dei servizi sociali',null,52,null),
		('PTj01','10.41','10.41 - Servizi di assistenza tecnica alla P.A.',null,53,null),
		('PTj01','10.43','10.43 - Servizi di orientamento e accompagnamento al lavoro',null,54,null),
		('PTj01','10.92','10.92 - Servizi ai dipendenti di imprese produttive',null,55,null),
		('PTj01','10.93','10.93 - Servizi essenziali per la popolazione rurale',null,56,null),
		('PTj01','10.94','10.94 - Assistenza sociale e servizi alla persona',null,57,null),
		('PTj01','10.99','10.99 - Altri servizi per la collettivita''',null,58,null),
		('PTj01','11.70','11.70 - Scuola e istruzione',null,59,null),
		('PTj01','11.71','11.71 - Formazione per il lavoro',null,60,null),
		('PTj01','11.72','11.72 - Altri strumenti formativi e di work-experience',null,61,null),
		('PTj01','11.75','11.75 - Contributi ed incentivi al lavoro',null,62,null),
		('PTj01','11.80','11.80 - Altri sostegni per il mercato del lavoro',null,63,null);

		
		
		Update C0CAMPI SET C0C_TAB1='PT010', C0C_FS = 'A100', COC_DOM = null WHERE COC_MNE_UNI = 'VARIATO.INTTRI.PIANI';
		
		Update C0CAMPI set COC_DES='Numero provvedimento adozione', COC_DES_WEB='Numero provvedimento adozione', C0C_FS='A50' where COC_MNE_UNI = 'NADOZI.PIATRI.PIANI';
		Update C0CAMPI set COC_DES='Numero provvedimento approvazione', COC_DES_WEB='Numero provvedimento approvazione', C0C_FS='A50' where COC_MNE_UNI = 'NAPTRI.PIATRI.PIANI';

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DPADOZI.PIATRI.PIANI','T2DTPUBADOZ','Data pubblicazione','Data pubblicazione','A10',null,'DATA_ELDA','Data pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DPAPPROV.PIATRI.PIANI','T2DTPUBAPP','Data pubblicazione','Data pubblicazione','A10',null,'DATA_ELDA','Data pubblicazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TITADOZI.PIATRI.PIANI','T2TITOLOADOZ','Titolo del documento','Titolo','A250',null,null,'Titolo');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'TITAPPROV.PIATRI.PIANI','T2TITOLOAPP','Titolo del documento','Titolo','A250',null,null,'Titolo');

		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SYSCON.PIATRI.PIANI','T2SYSCON','Utente','Utente','N12',null,null,'Utente');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IDUFFICIO.PIATRI.PIANI','PIAIDUFF','ID ufficio','ID ufficio','N10',null,null,'ID ufficio');

		Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('FUNZ','ALT.PIANI.APPLICA-FILTRO-UTENTE','Applica filtro utente su Programmi',6100);

		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('FUNZ','VIS','ALT.PIANI.APPLICA-FILTRO-UTENTE','Visualizza',0,1,1,3894478462);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','PIANI.PIATRI.SYSCON','Visualizza',0,1,1,3792932967);
		Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','PIANI.INTTRI.COPFIN','Visualizza',0,1,1,1479473519);

		Insert into w_note (tipo,oggetto,prog,modovis,nota) VALUES (2,'PIANI.PIATRI-scheda.FLUSSIPROGRAMMI',0,'1','<b>ATTENZIONE: solo per i programmi nel nuovo tracciato, dopo aver effettuato la Pubblicazione del programma non sarà più possibile effettuare modifiche!</b><div>Per aggiornare il programma è possibile effettuarne una copia, ritornando alla lista dei programmi, dal menu pop-up.&nbsp;</div><div>Si rimanda la manuale d''uso per ulteriori dettagli.&nbsp;<span style="font-size: smaller;"><span style="font-size: larger;">Per i programmi antecedenti il DM 14 del 16/01/2018 rimane invece la possibilità di apportare aggiornamenti e ripubblicare il medesimo programma.</span></span></div>');


		Update W_CONFIG set valore='2' where chiave like 'it.eldasoft.pt.norma.%';

		Update ELDAVER set NUMVER='4.0.0', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';


	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 4.0.0 --> 4.1.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '4.0.0' or numver like '4.0.%')) = 1
	THEN

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
	
		IF (select count(*) from SYSCAT.TABLES where TABSCHEMA=CURRENT SCHEMA and TYPE='V' and TABNAME = 'V_REPORT_SOGG_AGGR')=1
		THEN
			SET v_sql = 'DROP view V_REPORT_SOGG_AGGR';
			EXECUTE IMMEDIATE v_sql;
			SET v_sql= 'CREATE VIEW V_REPORT_SOGG_AGGR as 
					SELECT u.cfein AS codicefiscaleente,
					cast (null as varchar(20)) AS codiceipa,
					u.nomein AS amministrazione,
					cast (null as varchar(20)) AS dipartimento,
					cast (null as varchar(20)) AS ufficio,
					s.tabdesc AS regione,
					u.proein AS provincia,
					COALESCE(u.viaein, '') || COALESCE(u.nciein, '') AS indirizzoente,
					u.telein AS telefonoente,
					u.emaiin AS mailente,
					u.emai2in AS pecente,
					t.nometei AS nomereferente,
					t.cogtei AS cognomereferente,
					t.cftec AS codicefiscalereferente,
					t.teltec AS telefonoreferente,
					t.ematec AS mailreferente,
					a.cuiint AS numerointerventocui,
					u.cfein AS codicefiscaleamministrazione,
						CASE
							WHEN substr(a.cuiint, 1, 1) in (''L'', ''F'', ''S'') THEN substr(a.cuiint, 13, 4)
						END AS primaannualita,
					p.anntri + COALESCE(a.annrif, 0) - 1 AS anno,
					cast(''001'' as varchar(3))  AS identificativoproceduraacq,
					a.cupprg AS codicecup,
						CASE
							WHEN a.lotfunz = ''1'' THEN ''Si''
							WHEN a.lotfunz = ''2'' THEN ''No''
						END AS lottofunzionale,
					round(COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0), 2) AS importostimatolotto,
					n.descrizione AS ambitogeografico,
					a.cupmst AS codicecupmaster,
					t2.tab2d2 AS settore,
					a.codcpv AS cpv,
					a.desint AS descrizioneacquisto,
					cast (null as varchar(2)) AS conformitaambientale,
						CASE
							WHEN a.prgint IS NOT NULL THEN ''Livello '' || cast(a.prgint as char(1))
						END AS priorita,
					rup.cftec AS cfresponsabileprocedimento,
					rup.cogtei AS cognomeresponsprocedimento,
					rup.nometei AS nomeresponsabileprocedimento,
					a.quantit AS quantita,
					um.tab1desc AS unitamisura,
					a.durcont AS duratacontratto,
					round(COALESCE(a.di1int, 0) + COALESCE(a.pr1tri, 0), 2) AS stimacostiprogrammaprimoanno,
					round(COALESCE(a.di2int, 0) + COALESCE(a.pr2tri, 0), 2) AS stimacostiprogrammasecondoanno,
					round(COALESCE(a.di9int, 0) + COALESCE(a.pr9tri, 0), 2) AS costiannualitasuccessive,
					round(COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0), 2) AS stimacostiprogrammatotale,
					round(a.icpint, 2) AS importoapportocapitaleprivato,
					t3.tab3desc AS tipoapportocapitaleprivato,
						CASE
							WHEN a.delega = ''1'' THEN ''Si''
							WHEN a.delega = ''2'' THEN ''No''
						END AS delega,
					a.codausa AS codiceausa,
					a.sogagg AS denomamministrazionedelegata,
					p.contri
			   FROM piatri p
					 JOIN uffint u ON u.codein = p.cenint
					 LEFT JOIN tecni t ON t.codtec = p.respro
					 LEFT JOIN inttri a ON a.contri = p.contri
					 LEFT JOIN tabnuts n ON n.codice = substr(a.nuts, 1, 4)
					 LEFT JOIN tab2 t2 ON t2.tab2cod = ''W3z05'' AND t2.tab2tip = a.tipoin
					 LEFT JOIN tecni rup ON rup.codtec = a.codrup
					 LEFT JOIN tab3 t3 ON t3.tab3cod = ''PTx08'' AND t3.tab3tip = a.tcpint
					 LEFT JOIN tabsche s ON s.tabcod = ''S2003'' AND s.tabcod1 = ''05'' AND s.tabcod2 = (''0'' || substr(u.codcit, 1, 3))
					 LEFT JOIN tab1 um ON um.tab1cod = ''PT019'' AND um.tab1tip = a.unimis
			  WHERE p.tiprog = 2 AND (COALESCE(a.totint, 0) - COALESCE(a.spesesost, 0)) > 1000000';
			EXECUTE immediate v_sql;
		END IF;
		
		UPDATE C0CAMPI SET C0C_TAB1='PTx09' where C0C_MNE_BER='T2STAPRO';
		
		Update TAB_CONTROLLI set condizione='COALESCE(T.TOTINT,0)>0 or COALESCE(T.di1int,0)>0 or exists (select 1 from piatri where piatri.contri=T.contri and TIPROG=3 and T.TIPINT=2)' where codapp='PT' and num=520;
		Update TAB_CONTROLLI set condizione='T.PRGINT is not null or exists (select 1 from piatri where piatri.contri=T.contri and TIPROG=3 and T.TIPINT=2)' where titolo='PRGINT' and codapp='PT' and entita='INTTRI';
		Update TAB_CONTROLLI set msg='Campo non valorizzato',tipo='W' where CODAPP='PT' and num=350;
		Delete from TAB_CONTROLLI where codapp='PT' and num in (151,500,510);
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',151,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is null or T.CUPPRG like ''_______________''','Codice CUP non valido','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',500,'INTTRI','Modalit&agrave; di affidamento','CODAUSA','T.CODAUSA is not null or T.DELEGA=''2'' or (T.TIPINT=1 and T.ANNINT<>''1'') or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',510,'INTTRI','Modalit&agrave; di affidamento','SOGAGG','T.SOGAGG is not null or T.DELEGA=''2'' or (T.TIPINT=1 and T.ANNINT<>''1'') or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		
		Update W_NOTE set nota='<b>ATTENZIONE:  per i programmi nel tracciato previsto dal DM 14 del 16/01/2018, dopo aver effettuato la pubblicazione non sarà più possibile effettuare modifiche!</b><div>Per aggiornare il programma è possibile utilizzare la funzione "Copia per aggiornamento" ritornando alla lista dei programmi, dal menu pop-up. Si rimanda al manuale d''uso per ulteriori dettagli.</div><div><span style="font-size: smaller;"><span style="font-size: larger;">Per i programmi antecedenti il DM 14 del 16/01/2018 rimane invece la possibilità di apportare aggiornamenti e ripubblicare il medesimo programma.</span></span></div>' where oggetto='PIANI.PIATRI-scheda.FLUSSIPROGRAMMI';

		Update ELDAVER set NUMVER='4.1.0', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@
--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 4.1.0 --> 5.0.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '4.1.0' or numver like '4.1.0.%')) = 1
	THEN

		---  FABBISOGNI
		SET v_sql= 'CREATE TABLE FABBISOGNI (
			CONINT NUMERIC(5) NOT NULL,
			SYSCON NUMERIC(12) ,
			STATO NUMERIC(7) ,
			CODEIN VARCHAR(16),
			PRIMARY KEY (CONINT))';
		EXECUTE immediate v_sql;

		---  PTAPPROVAZIONI
		SET v_sql= 'CREATE TABLE PTAPPROVAZIONI (
			CONINT NUMERIC(5) NOT NULL,
			NUMAPPR NUMERIC(5) NOT NULL,
			DATAAPPR DATE,
			SYSAPPR NUMERIC(12),
			UTENTEAPPR VARCHAR(61),
			FASEAPPR NUMERIC(7) ,
			ESITOAPPR NUMERIC(7) ,
			NOTEAPPR VARCHAR(2000),
			ID_PROGRAMMA NUMERIC(12),
			ID_INTERV_PROGRAMMA NUMERIC(12),
			PRIMARY KEY (CONINT,NUMAPPR))';
		EXECUTE immediate v_sql;
		 
			 
		----------------------------------------------
		---   Aggiornamento dati Piani Triennali   ---
		----------------------------------------------
		
		-- C0ENTIT
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE)
		values ('FABBISOGNI.PIANI',0,'E','FABBISOGNI','Fabbisogni dei programmi','CONINT.FABBISOGNI.PIANI','FABBISOGNI.PIANI','CONINT.INTTRI.PIANI',null,null,'pt_fabb');
		Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE)
		values ('PTAPPROVAZIONI.PIANI',0,'E','APPR_FABB','Approvazioni dei fabbisogni','CONINT.PTAPPROVAZIONI.PIANI;NUMAPPR.PTAPPROVAZIONI.PIANI','PTAPPROVAZIONI.PIANI','CONINT.INTTRI.PIANI',null,null,'pt_appr');

		-- C0CAMPI
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (2,'E','P','CONINT.FABBISOGNI.PIANI','T2CONINT2','Numero progressivo dell''intervento','N.progr.intervento','N5',null,null,'Numero progressivo dell''intervento');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SYSCON.FABBISOGNI.PIANI','T2SYSCON2','Codice dell''utente','Cod. utente','N12',null,null,null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'STATO.FABBISOGNI.PIANI','T2STATO2','Stato','Stato','A100','PT023',null,null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CODEIN.FABBISOGNI.PIANI','T2CODEIN2','Codice della stazione appaltante','Cod. staz. app.','A16',null,null,null);
		
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (2,'E','P','CONINT.PTAPPROVAZIONI.PIANI','T2CONINT3','Numero progressivo dell'' intervento','N.progr.intervento','N5',null,null,'Numero progressivo dell''intervento');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (2,'E','P','NUMAPPR.PTAPPROVAZIONI.PIANI','T2NUMAPPR3','Numero progressivo dell'' approvazione','N.progr.approvazione','N5',null,null,'Numero progressivo dell''approvazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SYSAPPR.PTAPPROVAZIONI.PIANI','T2SYSCON3','Codice dell''utente','Cod. utente','N12',null,null,null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'UTENTEAPPR.PTAPPROVAZIONI.PIANI','T2UTEAPPR3','Utente','Utente','A200',null,null,'Utente');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'FASEAPPR.PTAPPROVAZIONI.PIANI','T2FASEAPPR3','Fase approvazione','Fase approvazione','A100','PT024',null,null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ESITOAPPR.PTAPPROVAZIONI.PIANI','T2ESIAPPR3','Esito approvazione','Esito approvazione','A100','PT025',null,null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATAAPPR.PTAPPROVAZIONI.PIANI','T2DATAPPR3','Data approvazione','Data approvazione','A10',null,'DATA_ELDA','Data approvazione');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'NOTEAPPR.PTAPPROVAZIONI.PIANI','T2NOTE3','Note','Note','A2000',null,'NOTE','Note');
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_PROGRAMMA.PTAPPROVAZIONI.PIANI','T2IDPROG3','Id programma','Id prog.','N12',null,null,null);
		Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'ID_INTERV_PROGRAMMA.PTAPPROVAZIONI.PIANI','T2IDINTPROG3','Id intervento programma','Id int. prog.','N12',null,null,null);

		Update C0CAMPI SET COC_DES='CUP non richiesto o esente', COC_DES_FRM='CUP non rich./esente', COC_DES_WEB='CUP non richiesto o esente' WHERE COC_MNE_UNI='FLAG_CUP.INTTRI.PIANI';
		-- TAB1
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',1,'In compilazione',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',2,'Completato',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',3,'Inoltrato a RAF',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',4,'Inoltrato a RdP',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',11,'Da revisionare',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',12,'Da revisionare',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',21,'Inserito in programmazione',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',22,'Procedura avviata',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',31,'Respinto',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',32,'Annullato',null,null,null);
		
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT024',1,'Approvazione finanziaria',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT024',2,'Approvazione funzionale',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT024',3,'Inserimento in programmazione',null,null,null);
		
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT025',1,'Approvato',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT025',2,'Respinto',null,null,null);
		Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT025',3,'Da revisionare',null,null,null);
		
		-- TAB3
		Update TAB3 set TAB3DESC='E'' stata dichiarata l''insussistenza dell''interesse pubblico al completamento ed alla fruibilità' where TAB3COD='PTx02' and TAB3TIP='a';
		Update TAB3 set TAB3DESC='Si intende riprendere l''esecuzione - per il  completamento non sono necessari finanz. aggiuntivi' where TAB3COD='PTx02' and TAB3TIP='b';
		Update TAB3 set TAB3DESC='Si intende riprendere l''esecuzione dell''opera avendo già reperito i finanziamenti aggiuntivi' where TAB3COD='PTx02' and TAB3TIP='c';

		
		-- W_OGGETTI
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'ALT.W9.W9HOME.FABBISOGNI', 'Home page Fabbisogni', 6200);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'ALT.W9.W9HOME.SA-FABB_APPRFIN', 'Home page Approvazione Finanziaria Fabbisogni', 6210);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('MASC', 'PIANI.FABBISOGNI-scheda', 'Scheda Fabbisogni', 6220);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('PAGE', 'PIANI.FABBISOGNI-scheda.FABBDATIGEN', 'Pagina Dati Generali - Scheda Fabbisogni', 6230);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('SEZ', 'PIANI.FABBISOGNI-scheda.FABBDATIGEN.DATIELANN', 'Sezione Dati Elenco Annuale', 6240);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('SEZ', 'PIANI.FABBISOGNI-scheda.FABBDATIGEN.ACQVERDI', 'Sezione Acquisti Verdi', 6250);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('SEZ', 'PIANI.FABBISOGNI-scheda.FABBDATIGEN.MATRIC', 'Sezione Materiali Riciclati', 6260);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('SEZ', 'PIANI.FABBISOGNI-scheda.FABBDATIGEN.MODAFF', 'Sezione Modalità Affidamento', 6270);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('SEZ', 'PIANI.FABBISOGNI-scheda.FABBDATIGEN.ALTDAT', 'Sezione Altri Dati', 6280);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'MOD.PIANI.FABBISOGNI-scheda.FABBDATIGEN.SCHEDAMOD', 'Scheda Fabbisogni - Modifica', 6290);
		
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'MOD.PIANI.RISCAPITOLO-scheda.SCHEDAMOD', 'Pagina Capitoli Spesa - Scheda Modifica', 6300);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'DEL.PIANI.FABBISOGNI-scheda.FABBCAPSPESA.DEL', 'Pagina Capitoli di Spesa- Lista Elimina', 6310);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'DEL.PIANI.FABBISOGNI-scheda.FABBCAPSPESA.LISTADELSEL', 'Pagina Capitoli di Spesa- Lista Elimina Selezione', 6320);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'INS.PIANI.FABBISOGNI-scheda.FABBCAPSPESA.LISTANUOVO', 'Pagina Capitoli di Spesa- Lista Nuovo', 6330);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'INS.PIANI.RISCAPITOLO-scheda.SCHEDANUOVO', 'Pagina Capitoli di Spesa- Scheda Nuovo', 6340);
		Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('PAGE', 'PIANI.FABBISOGNI-scheda.FABBCAPSPESA', 'Pagina Capitoli Spesa - Scheda Fabbisogni', 6360);
		
		-- W_AZIONI
		Insert into W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) Values   ('FUNZ', 'VIS', 'ALT.W9.W9HOME.FABBISOGNI', 'Visualizza', 0, 1, 1, 4056027049);
		Insert into W_AZIONI (TIPO, AZIONE, OGGETTO, DESCR, VALORE, INOR, VISELENCO, CRC) Values   ('FUNZ', 'VIS', 'ALT.W9.W9HOME.SA-FABB_APPRFIN', 'Visualizza', 0, 1, 1, 1260186725);
		
		-- W_CONFIG
		Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) VALUES ('W9','it.eldasoft.pt.moduloFabbisogniAttivo','0','Programmi triennali/biennali','Modulo fabbisogni attivo. Valori ammessi: 0 [default] = non attivo, 1 = attivo',null);
		Update W_CONFIG set valore='IMPR;TECNI;TEIM;FABBISOGNI' where codapp='W9' and chiave = 'it.eldasoft.associazioneUffintAbilitata.archiviFiltrati';

		-- TAB_CONTROLLI
		Delete from TAB_CONTROLLI where codapp='PT' and codfunzione='PUBB_SCP' and num in (150,300,320,330,340,350,490,500,510);
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',150,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is not null or T.FLAG_CUP=''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',300,'INTTRI','Dati generali','ANNINT','T.ANNINT is not null','Valorizzare il campo','N');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',320,'INTTRI','Dati elenco annuale','FIINTR','T.FIINTR is not null or T.TIPINT=2 or T.ANNRIF>1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',330,'INTTRI','Dati elenco annuale','URCINT','T.URCINT is not null or T.TIPINT=2 or T.ANNRIF>1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',340,'INTTRI','Dati elenco annuale','APCINT','T.APCINT is not null or T.TIPINT=2 or T.ANNRIF>1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',350,'INTTRI','Dati elenco annuale','STAPRO','T.STAPRO is not null or T.TIPINT=2 or T.ANNRIF>1','Campo non valorizzato','W');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',490,'INTTRI','Modalit&agrave; di affidamento','DELEGA','T.DELEGA is not null or T.ANNRIF>1 or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',500,'INTTRI','Modalit&agrave; di affidamento','CODAUSA','T.CODAUSA is not null or T.DELEGA=''2'' or (T.TIPINT=1 and T.ANNRIF>1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','PUBB_SCP',510,'INTTRI','Modalit&agrave; di affidamento','SOGAGG','T.SOGAGG is not null or T.DELEGA=''2'' or (T.TIPINT=1 and T.ANNRIF>1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
		
		Delete from TAB_CONTROLLI where CODAPP='PT' and CODFUNZIONE in ('INOLTRO_RAF','INOLTRO_RDP');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',100,'INTTRI','Intervento n. {0} del programma',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',120,'INTTRI','Dati generali','DESINT','T.DESINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',130,'INTTRI','Dati generali','AILINT','T.AILINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',140,'INTTRI','Dati generali','FLAG_CUP','T.FLAG_CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',150,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is not null or T.FLAG_CUP=''1'' or ANNINT=''2''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',151,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is null or T.CUPPRG like ''_______________''','Codice CUP non valido','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',180,'INTTRI','Dati generali','Codice ISTAT o NUTS del comune','T.COMINT is not null or T.TIPINT=2 or T.NUTS is not null','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',190,'INTTRI','Dati generali','NUTS','(T.NUTS is not null or T.TIPINT=1)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',200,'INTTRI','Dati generali','TIPOIN','T.TIPOIN is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',210,'INTTRI','Dati generali','CODCPV','(T.CODCPV is not null or T.TIPINT=1)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',220,'INTTRI','Dati generali','PRGINT','T.PRGINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',230,'INTTRI','Dati generali','CODRUP','EXISTS (SELECT 1 FROM TECNI A WHERE A.CODTEC= T.CODRUP and A.NOMTEC is not null)','Indicare il responsabile dell''intervento','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',240,'INTTRI','Dati generali','LOTFUNZ','T.LOTFUNZ is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',250,'INTTRI','Dati generali','LAVCOMPL','T.LAVCOMPL is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',260,'INTTRI','Dati generali','DURCONT','T.DURCONT is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',270,'INTTRI','Dati generali','CONTESS','T.CONTESS is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',280,'INTTRI','Dati generali','SEZINT','T.SEZINT is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',290,'INTTRI','Dati generali','INTERV','T.INTERV is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',310,'INTTRI','Dati generali','TCPINT','T.TCPINT is not null or coalesce(PR1TRI,0)+coalesce(PR2TRI,0)+coalesce(PR3TRI,0)+coalesce(PR9TRI,0)=0','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',320,'INTTRI','Dati elenco annuale','FIINTR','T.FIINTR is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',330,'INTTRI','Dati elenco annuale','URCINT','T.URCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',340,'INTTRI','Dati elenco annuale','APCINT','T.APCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',350,'INTTRI','Dati elenco annuale','STAPRO','T.STAPRO is not null or T.TIPINT=2 or T.ANNINT<>''1''','Campo non valorizzato','W');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',490,'INTTRI','Modalit&agrave; di affidamento','DELEGA','T.DELEGA is not null or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',500,'INTTRI','Modalit&agrave; di affidamento','CODAUSA','T.CODAUSA is not null or T.DELEGA is null or T.DELEGA=''2'' or (T.TIPINT=1 and T.ANNINT<>''1'')','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',510,'INTTRI','Modalit&agrave; di affidamento','SOGAGG','T.SOGAGG is not null or T.DELEGA is null or T.DELEGA=''2'' or (T.TIPINT=1 and T.ANNINT<>''1'')','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',520,'INTTRI','Quadro delle risorse','Importo totale intervento ','COALESCE(T.TOTINT,0)>0 or COALESCE(T.di1int,0)>0','Compilare il quadro delle risorse','E');

		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',800,'OITRI','Opera incompiuta n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',810,'OITRI',null,'NUMOI','T.NUMOI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',820,'OITRI',null,'CUP','T.CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',830,'OITRI',null,'DESCRIZIONE','T.DESCRIZIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',840,'OITRI',null,'DETERMINAZIONI','T.DETERMINAZIONI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',850,'OITRI',null,'AMBITOINT','T.AMBITOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',860,'OITRI',null,'ANNOULTQE','T.ANNOULTQE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',870,'OITRI',null,'IMPORTOINT','T.IMPORTOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',880,'OITRI',null,'IMPORTOLAV','T.IMPORTOLAV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',890,'OITRI',null,'ONERIULTIM','T.ONERIULTIM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',900,'OITRI',null,'IMPORTOSAL','T.IMPORTOSAL is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',910,'OITRI',null,'AVANZAMENTO','T.AVANZAMENTO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',920,'OITRI',null,'CAUSA','T.CAUSA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',930,'OITRI',null,'STATOREAL','T.STATOREAL is not null','Indicare lo stato di realizzazione','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',940,'OITRI',null,'INFRASTRUTTURA','T.INFRASTRUTTURA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',950,'OITRI',null,'FRUIBILE','T.FRUIBILE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',960,'OITRI',null,'UTILIZZORID','T.UTILIZZORID is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',970,'OITRI',null,'DESTINAZIONEUSO','T.DESTINAZIONEUSO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',980,'OITRI',null,'CESSIONE','T.CESSIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',990,'OITRI',null,'VENDITA','T.VENDITA is not null','Indicare se &egrave; prevista la vendita','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1000,'OITRI',null,'DEMOLIZIONE','T.DEMOLIZIONE is not null or coalesce(T.VENDITA,''1'')=''1''','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1200,'IMMTRAI','Immobile da trasferire n. {0} dell''intervento',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1210,'IMMTRAI',null,'NUMIMM','T.NUMIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1220,'IMMTRAI',null,'CUIIMM','T.CUIIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1230,'IMMTRAI',null,'DESIMM','T.DESIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1240,'IMMTRAI',null,'Codice ISTAT o NUTS del comune','T.COMIST is not null or T.NUTS is not null','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1250,'IMMTRAI',null,'TITCOR','T.TITCOR is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1260,'IMMTRAI',null,'IMMDISP','T.IMMDISP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1270,'IMMTRAI',null,'PROGDISM','T.PROGDISM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1280,'IMMTRAI',null,'TIPDISP','(T.TIPDISP is not null or T.NUMOI is null)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1290,'IMMTRAI',null,'VALIMM','T.VALIMM is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1600,'RIS_CAPITOLO','Risorse per capitolo di bilancio n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',1610,'RIS_CAPITOLO',null,'NCAPBIL','T.NCAPBIL is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',100,'INTTRI','Intervento n. {0} del programma',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',120,'INTTRI','Dati generali','DESINT','T.DESINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',130,'INTTRI','Dati generali','AILINT','T.AILINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',140,'INTTRI','Dati generali','FLAG_CUP','T.FLAG_CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',150,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is not null or T.FLAG_CUP=''1'' or ANNINT=''2''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',151,'INTTRI','Dati generali','INTTRI.CUPPRG','T.CUPPRG is null or T.CUPPRG like ''_______________''','Codice CUP non valido','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',180,'INTTRI','Dati generali','Codice ISTAT o NUTS del comune','T.COMINT is not null or T.TIPINT=2 or T.NUTS is not null','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',190,'INTTRI','Dati generali','NUTS','(T.NUTS is not null or T.TIPINT=1)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',200,'INTTRI','Dati generali','TIPOIN','T.TIPOIN is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',210,'INTTRI','Dati generali','CODCPV','(T.CODCPV is not null or T.TIPINT=1)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',220,'INTTRI','Dati generali','PRGINT','T.PRGINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',230,'INTTRI','Dati generali','CODRUP','EXISTS (SELECT 1 FROM TECNI A WHERE A.CODTEC= T.CODRUP and A.NOMTEC is not null)','Indicare il responsabile dell''intervento','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',240,'INTTRI','Dati generali','LOTFUNZ','T.LOTFUNZ is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',250,'INTTRI','Dati generali','LAVCOMPL','T.LAVCOMPL is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',260,'INTTRI','Dati generali','DURCONT','T.DURCONT is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',270,'INTTRI','Dati generali','CONTESS','T.CONTESS is not null or T.TIPINT=1','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',280,'INTTRI','Dati generali','SEZINT','T.SEZINT is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',290,'INTTRI','Dati generali','INTERV','T.INTERV is not null or T.TIPINT=2','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',310,'INTTRI','Dati generali','TCPINT','T.TCPINT is not null or coalesce(PR1TRI,0)+coalesce(PR2TRI,0)+coalesce(PR3TRI,0)+coalesce(PR9TRI,0)=0','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',320,'INTTRI','Dati elenco annuale','FIINTR','T.FIINTR is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',330,'INTTRI','Dati elenco annuale','URCINT','T.URCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',340,'INTTRI','Dati elenco annuale','APCINT','T.APCINT is not null or T.TIPINT=2 or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',350,'INTTRI','Dati elenco annuale','STAPRO','T.STAPRO is not null or T.TIPINT=2 or T.ANNINT<>''1''','Campo non valorizzato','W');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',490,'INTTRI','Modalit&agrave; di affidamento','DELEGA','T.DELEGA is not null or T.ANNINT<>''1''','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',500,'INTTRI','Modalit&agrave; di affidamento','CODAUSA','T.CODAUSA is not null or T.DELEGA is null or T.DELEGA=''2'' or (T.TIPINT=1 and T.ANNINT<>''1'')','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',510,'INTTRI','Modalit&agrave; di affidamento','SOGAGG','T.SOGAGG is not null or T.DELEGA is null or T.DELEGA=''2'' or (T.TIPINT=1 and T.ANNINT<>''1'')','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',520,'INTTRI','Quadro delle risorse','Importo totale intervento ','COALESCE(T.TOTINT,0)>0 or COALESCE(T.di1int,0)>0','Compilare il quadro delle risorse','E');

		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',800,'OITRI','Opera incompiuta n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',810,'OITRI',null,'NUMOI','T.NUMOI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',820,'OITRI',null,'CUP','T.CUP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',830,'OITRI',null,'DESCRIZIONE','T.DESCRIZIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',840,'OITRI',null,'DETERMINAZIONI','T.DETERMINAZIONI is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',850,'OITRI',null,'AMBITOINT','T.AMBITOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',860,'OITRI',null,'ANNOULTQE','T.ANNOULTQE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',870,'OITRI',null,'IMPORTOINT','T.IMPORTOINT is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',880,'OITRI',null,'IMPORTOLAV','T.IMPORTOLAV is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',890,'OITRI',null,'ONERIULTIM','T.ONERIULTIM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',900,'OITRI',null,'IMPORTOSAL','T.IMPORTOSAL is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',910,'OITRI',null,'AVANZAMENTO','T.AVANZAMENTO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',920,'OITRI',null,'CAUSA','T.CAUSA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',930,'OITRI',null,'STATOREAL','T.STATOREAL is not null','Indicare lo stato di realizzazione','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',940,'OITRI',null,'INFRASTRUTTURA','T.INFRASTRUTTURA is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',950,'OITRI',null,'FRUIBILE','T.FRUIBILE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',960,'OITRI',null,'UTILIZZORID','T.UTILIZZORID is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',970,'OITRI',null,'DESTINAZIONEUSO','T.DESTINAZIONEUSO is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',980,'OITRI',null,'CESSIONE','T.CESSIONE is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',990,'OITRI',null,'VENDITA','T.VENDITA is not null','Indicare se &egrave; prevista la vendita','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1000,'OITRI',null,'DEMOLIZIONE','T.DEMOLIZIONE is not null or coalesce(T.VENDITA,''1'')=''1''','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1200,'IMMTRAI','Immobile da trasferire n. {0} dell''intervento',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1210,'IMMTRAI',null,'NUMIMM','T.NUMIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1220,'IMMTRAI',null,'CUIIMM','T.CUIIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1230,'IMMTRAI',null,'DESIMM','T.DESIMM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1240,'IMMTRAI',null,'Codice ISTAT o NUTS del comune','T.COMIST is not null or T.NUTS is not null','Valorizzare uno dei due campi','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1250,'IMMTRAI',null,'TITCOR','T.TITCOR is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1260,'IMMTRAI',null,'IMMDISP','T.IMMDISP is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1270,'IMMTRAI',null,'PROGDISM','T.PROGDISM is not null','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1280,'IMMTRAI',null,'TIPDISP','(T.TIPDISP is not null or T.NUMOI is null)','Valorizzare il campo','E');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1290,'IMMTRAI',null,'VALIMM','T.VALIMM is not null','Valorizzare il campo','E');

		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1600,'RIS_CAPITOLO','Risorse per capitolo di bilancio n. {0}',null,null,null,'T');
		Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',1610,'RIS_CAPITOLO',null,'NCAPBIL','T.NCAPBIL is not null','Valorizzare il campo','E');
		
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 10, 'PIATRI', 'Dati generali', 'Acquisti', 'Il programma non contiene acquisti', 'W');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 20, 'PIATRI', 'Approvazione', 'Approvazione', 'Inserire i dati di approvazione del programma', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 30, 'PIATRI', 'Approvazione', 'DataPubblicazioneApprovazione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 40, 'PIATRI', 'Approvazione', 'TitoloAttoApprovazione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 50, 'PIATRI', 'Approvazione', 'UrlAttoApprovazione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 60, 'INTTRI', 'Dati generali', 'EsenteCup', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 70, 'INTTRI', 'Dati generali', 'Cup', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 80, 'INTTRI', 'Dati generali', 'AcquistoRicompreso', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 90, 'INTTRI', 'Dati generali', 'CuiCollegato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 100, 'INTTRI', 'Dati generali', 'Nuts', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 110, 'INTTRI', 'Dati generali', 'Cpv', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 120, 'INTTRI', 'Dati generali', 'Priorita', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 130, 'INTTRI', 'Dati generali', 'Rup', 'Indicare il responsabile dell''intervento', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 140, 'INTTRI', 'Dati generali', 'LottoFunzionale', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 150, 'INTTRI', 'Dati generali', 'DurataInMesi', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 160, 'INTTRI', 'Dati generali', 'NuovoAffidamento', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 170, 'INTTRI', 'Dati generali', 'TipologiaCapitalePrivato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 180, 'INTTRI', 'Modalità di affidamento', 'Delega', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 190, 'INTTRI', 'Modalità di affidamento', 'CodiceSoggettoDelegato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 200, 'INTTRI', 'Modalità di affidamento', 'NomeSoggettoDelegato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 210, 'INTTRI', 'Dati generali', 'Importo totale intervento', 'Compilare il quadro delle risorse', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_FS', 220, 'INRTRI', 'Acquisto non riproposto', 'Cup', 'Valorizzare il campo', 'W');
		
		
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 10, 'PIATRI', 'Dati generali', 'Interventi', 'Il programma non contiene interventi', 'W');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 20, 'PIATRI', 'Adozione o approvazione', 'Adozione o approvazione', 'Inserire i dati di adozione e/o di approvazione del programma', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 30, 'PIATRI', 'Adozione', 'DataPubblicazioneAdozione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 40, 'PIATRI', 'Adozione', 'TitoloAttoAdozione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 50, 'PIATRI', 'Adozione', 'UrlAttoAdozione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 60, 'PIATRI', 'Approvazione', 'DataPubblicazioneApprovazione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 70, 'PIATRI', 'Approvazione', 'TitoloAttoApprovazione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 80, 'PIATRI', 'Approvazione', 'UrlAttoApprovazione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 90, 'INTTRI', 'Dati generali', 'EsenteCup', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 100, 'INTTRI', 'Dati generali', 'Cup', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 110, 'INTTRI', 'Dati generali', 'Codice ISTAT o NUTS del comune', 'Valorizzare uno dei due campi', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 120, 'INTTRI', 'Dati generali', 'Priorita', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 130, 'INTTRI', 'Dati generali', 'Rup', 'Indicare il responsabile dell''intervento', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 140, 'INTTRI', 'Dati generali', 'LottoFunzionale', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 150, 'INTTRI', 'Dati generali', 'LavoroComplesso', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 160, 'INTTRI', 'Dati generali', 'Tipologia', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 170, 'INTTRI', 'Dati generali', 'Categoria', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 180, 'INTTRI', 'Dati generali', 'TipologiaCapitalePrivato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 190, 'INTTRI', 'Dati elenco annuale', 'Finalita', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 200, 'INTTRI', 'Dati elenco annuale', 'ConformitaUrbanistica', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 210, 'INTTRI', 'Dati elenco annuale', 'ConformitaAmbientale', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 220, 'INTTRI', 'Dati elenco annuale', 'StatoProgettazione', 'Valorizzare il campo', 'W');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 230, 'INTTRI', 'Modalità di affidamento', 'Delega', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 240, 'INTTRI', 'Modalità di affidamento', 'CodiceSoggettoDelegato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 250, 'INTTRI', 'Modalità di affidamento', 'NomeSoggettoDelegato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 260, 'OITRI', 'Opera incompiuta', 'DeterminazioneAmministrazione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 270, 'OITRI', 'Opera incompiuta', 'Ambito', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 280, 'OITRI', 'Opera incompiuta', 'Anno', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 290, 'OITRI', 'Opera incompiuta', 'ImportoIntervento', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 300, 'OITRI', 'Opera incompiuta', 'ImportoLavori', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 310, 'OITRI', 'Opera incompiuta', 'Oneri', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 320, 'OITRI', 'Opera incompiuta', 'ImportoAvanzamento', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 330, 'OITRI', 'Opera incompiuta', 'PercentualeAvanzamento', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 340, 'OITRI', 'Opera incompiuta', 'Causa', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 350, 'OITRI', 'Opera incompiuta', 'Stato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 360, 'OITRI', 'Opera incompiuta', 'Infrastruttura', 'Valorizzare il campo', '');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 370, 'OITRI', 'Opera incompiuta', 'Fruibile', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 380, 'OITRI', 'Opera incompiuta', 'Ridimensionato', 'Valorizzare il campo', '');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 390, 'OITRI', 'Opera incompiuta', 'DestinazioneUso', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 400, 'OITRI', 'Opera incompiuta', 'Cessione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 410, 'OITRI', 'Opera incompiuta', 'PrevistaVendita', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 420, 'OITRI', 'Opera incompiuta', 'Demolizione', 'Valorizzare il campo se PrevistaVendita = No', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 421, 'OITRI', 'Opera incompiuta', 'OneriSito', 'Valorizzare il campo se Demolizione = Si', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 430, 'IMMTRAI', 'Immobile', 'Descrizione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 440, 'IMMTRAI', 'Immobile', 'Codice ISTAT o NUTS del comune', 'Valorizzare uno dei due campi', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 450, 'IMMTRAI', 'Immobile', 'TrasferimentoTitoloCorrispettivo', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 460, 'IMMTRAI', 'Immobile', 'ImmobileDisponibile', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 470, 'IMMTRAI', 'Immobile', 'InclusoProgrammaDismissione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 480, 'IMMTRAI', 'Immobile', 'TipoDisponibilita', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 490, 'IMMTRAI', 'Immobile', 'ValoreStimato', 'Valorizzare almeno un campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 500, 'INRTRI', 'Intervento non riproposto', 'Cup', 'Valorizzare il campo', 'W');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 510, 'INTTRI', 'Dati generali', 'Importo totale intervento', 'Compilare il quadro delle risorse', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 520, 'OITRI', 'Opera incompiuta', 'AltriDati.Codice ISTAT o NUTS', 'Valorizzare uno dei due campi', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 521, 'OITRI', 'Opera incompiuta', 'AltriDati.TipologiaIntervento', 'Valorizzare il campo se ImportoIntervento<100000', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 522, 'OITRI', 'Opera incompiuta', 'AltriDati.CategoriaIntervento', 'Valorizzare il campo se ImportoIntervento<100000', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 523, 'OITRI', 'Opera incompiuta', 'AltriDati.RequisitiCapitolato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 524, 'OITRI', 'Opera incompiuta', 'AltriDati.RequisitiApprovato', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 525, 'OITRI', 'Opera incompiuta', 'AltriDati.UnitaMisura', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 526, 'OITRI', 'Opera incompiuta', 'AltriDati.Dimensione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 527, 'OITRI', 'Opera incompiuta', 'AltriDati.Sponsorizzazione', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 528, 'OITRI', 'Opera incompiuta', 'AltriDati.FinanzaDiProgetto', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 529, 'OITRI', 'Opera incompiuta', 'AltriDati.CostoProgetto', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 530, 'OITRI', 'Opera incompiuta', 'AltriDati.Finanziamento', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 531, 'OITRI', 'Opera incompiuta', 'AltriDati.CoperturaStatale', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 532, 'OITRI', 'Opera incompiuta', 'AltriDati.CoperturaRegionale', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 533, 'OITRI', 'Opera incompiuta', 'AltriDati.CoperturaProvinciale', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 534, 'OITRI', 'Opera incompiuta', 'AltriDati.CoperturaComunale', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 535, 'OITRI', 'Opera incompiuta', 'AltriDati.CoperturaComunitaria', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 536, 'OITRI', 'Opera incompiuta', 'AltriDati.CoperturaAltro', 'Valorizzare il campo', 'E');
		Insert into WS_CONTROLLI(CODAPP,CODFUNZIONE,NUM,ENTITA,SEZIONE,TITOLO,MSG,TIPO) values ('PT', 'PUBBLICA_LAVORI', 537, 'OITRI', 'Opera incompiuta', 'AltriDati.CoperturaPrivata', 'Valorizzare il campo', 'E');

		-- ELDAVER
		Update ELDAVER set NUMVER='5.0.0', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--#SET TERMINATOR @

UPDATE COMMAND OPTIONS USING s OFF@
DROP procedure ASSEGNA_GRANT@


CREATE PROCEDURE ASSEGNA_GRANT ()
BEGIN
  DECLARE EOF INT DEFAULT 0;
  DECLARE S_SQL VARCHAR(200);
  DECLARE S_TABELLA VARCHAR(50);
  DECLARE S_USER VARCHAR(50);
  
  DECLARE C1 CURSOR FOR select TABNAME from SYSCAT.TABLES where TABSCHEMA =CURRENT SCHEMA and BASE_TABSCHEMA is null and status='N';
  DECLARE C2 CURSOR FOR select SEQNAME from SYSCAT.SEQUENCES where SEQSCHEMA =CURRENT SCHEMA;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET EOF = 1;

	SET S_USER=CURRENT SCHEMA;
	SET S_USER=S_USER || ',SSELDAS';	--------		Utente Eldasoft			----------------
	SET S_USER=S_USER || ',LM07421';	--------	Utente Luca Menegatti	----------------

  OPEN C1;
  WHILE EOF = 0 DO
    FETCH FROM C1 INTO S_TABELLA;
  	SET S_SQL = 'GRANT SELECT,INSERT,UPDATE,DELETE ON TABLE "' || S_TABELLA || '" TO USER ' || S_USER;
  	EXECUTE IMMEDIATE S_SQL;
  END WHILE;
  CLOSE C1;


  OPEN C2;
  WHILE EOF = 0 DO
    FETCH FROM C2 INTO S_TABELLA;
  	SET S_SQL = 'GRANT USAGE ON SEQUENCE ' || S_TABELLA || ' TO ' || S_USER;
  	EXECUTE IMMEDIATE S_SQL;
  END WHILE;
  CLOSE C2;
END@

call ASSEGNA_GRANT@
drop procedure ASSEGNA_GRANT@
UPDATE COMMAND OPTIONS USING s ON@

--#SET TERMINATOR ;



Delete from W_ACCPRO where COD_PROFILO='SA-APPA' and ID_ACCOUNT in (48,49,50);
Delete from W_PROAZI where COD_PROFILO='SA-APPA';
Delete from W_PROFILI where COD_PROFILO='SA-APPA';

Insert into W_PROFILI (COD_PROFILO,CODAPP,NOME,DESCRIZIONE,FLAG_INTERNO,DISCRIMINANTE,COD_CLIENTE,CRC) values ('SA-APPA','W9','Comunicazioni di eventi di contratti','Comunicazioni di eventi di contratti di lavori, forniture e servizi',0,null,1091,2973266656);

Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MAN','GENE.IMPR.CFIMP',1,2065006782);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MAN','GENE.IMPR.NOMEST',1,3277781792);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MAN','GENE.TECNI.CFTEC',1,3376415354);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MAN','GENE.TECNI.NOMTEC',1,3922502992);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MAN','GENE.TEIM.NOMTIM',1,570191537);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MAN','GENE.UFFINT.NOMEIN',1,231133415);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','GENE.UFFINT.CFEIN',0,1172411222);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.CENTRICOSTO.CODCENTRO',0,355651080);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.CENTRICOSTO.DENOMCENTRO',0,3492203994);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9GARA.CIG_ACCQUADRO',0,1019789729);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9GARA.IDAVGARA',0,4149243513);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9GARA.IMPORTO_GARA',0,3761403478);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9GARA.OGGETTO',0,3152215435);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9GARA.TIPO_APP',0,3563532698);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9LOTT.CIG',0,2486431594);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9LOTT.CUP',0,2223780328);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9LOTT.IMPORTO_ATTUAZIONE_SICUREZZA',0,3038640184);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9LOTT.IMPORTO_LOTTO',0,3161837935);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','MOD','W9.W9LOTT.OGGETTO',0,2619871705);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','*',1,4179291998);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.CATE.*',0,370569137);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPDTE.*',0,330687099);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.CODIMP',0,3867247338);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.DANTIM',0,160075319);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.DCCIAA',0,3154606715);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.DCERCC',0,2246961577);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.DISCIF',0,1402202500);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.GFIIMP',0,3444650880);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.MGSFLG',0,3557805759);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.NCERCC',0,563984103);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.PCCIAA',0,798409382);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.REGDIT',0,1344076067);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.IMPR.TIPIMP',0,130151157);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.*',0,2991376634);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.CAPTEC',1,2262503981);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.CFTEC',1,3479026769);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.CITTEC',1,2668115584);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.CODTEC',0,1589015177);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.COGTEI',1,2499727429);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.EMA2TEC',1,3901951948);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.EMATEC',1,3477690718);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.FAXTEC',1,3834869323);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.INDTEC',1,1184747252);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.LOCTEC',1,1842999014);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.MGSFLG',0,2713166260);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.NCITEC',1,3342083861);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.NOMETEI',1,3837335197);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.NOMTEC',1,1165377966);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.PROTEC',1,3072320536);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.TELTEC',1,438536440);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.WEBPWD',0,1014852124);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TECNI.WEBUSR',0,271746853);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TEIM.*',0,1916466235);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TEIM.CFTIM',1,1497752044);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TEIM.CODTIM',0,1008853949);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TEIM.COGTIM',1,210151931);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TEIM.NOMETIM',1,3897678699);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.TEIM.NOMTIM',1,669110426);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.CODEIN',0,1286571583);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.CODNAZ',0,3138619347);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.CODRES',0,1998027873);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.CODSTA',0,2864423568);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.DATICC',0,554502752);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.DNAEIN',0,2327110756);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.DOFEIN',0,4090031057);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.INDWEB',0,2084316072);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.IVAEIN',0,82183506);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.LNAEIN',0,3109792259);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.NUMICC',0,1892343888);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.PROICC',0,552494107);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.PROUFF',0,1100855954);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','COLS','VIS','GENE.UFFINT.USERID',0,921977655);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.GENE.C0OGGASS',0,1888279893);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.GENE.G_NOTEAVVISI',0,4289628934);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.GENE.HELPPAGINA',0,2611775279);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.GENE.UTENT-lista.HELPPAGINA',0,1352063578);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.GENE.UTENT-scheda.HELPPAGINA',0,2022395450);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.GENE.UTENT-trova.HELPPAGINA',0,4258458524);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.GENE.W_MODELLI',0,2702599471);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.W9.TRASFERISCI-APPALTO',0,228161805);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','ALT.W9.W9HOME.APPA',1,2116347254);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.GENE.ImprScheda.DATIGEN.INS-AIN',0,3816322368);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.GENE.ImprScheda.DATIGEN.INS-IMPDTE',0,3504095061);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-G_IMPCOL',0,1319656519);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-IMPAZI',0,147612543);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-IMPDTE',0,2570711371);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.GENE.SchedaUffint.SCHEDANUOVO',0,1565704981);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.W9.W9GARA-lista.LISTANUOVO',0,1387876598);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.W9.W9GARA-scheda.DETTGARA.SCHEDANUOVO',0,240874585);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.W9.W9GARA-scheda.LOTTI.LISTANUOVO',0,1386100504);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','INS.W9.W9LOTT-scheda.DATIDET.SCHEDANUOVO',0,1136804901);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','FUNZ','VIS','MOD.GENE.SchedaUffint.SCHEDAMOD',0,4129789294);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','MENU','VIS','REPORT',0,3456026856);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','MENU','VIS','STRUMENTI',0,2622732340);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','PAGE','VIS','GENE.ImprScheda.CASE',0,322106228);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','PAGE','VIS','GENE.ImprScheda.CATE',0,377349617);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','PAGE','VIS','GENE.ImprScheda.RAGIMP',0,4033516002);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.ABI',0,4156912262);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.AIN',0,3033598880);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.ALT',0,37172112);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.IMPDTE',0,1737007254);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.IND',1,4190301654);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.INPS',0,3614861476);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.ISO1',0,900991630);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.SOA',0,3453285577);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.DATIGEN.XIMPR',0,2833513384);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.LEGALI.IMPAZI',0,3404995677);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.ImprScheda.LEGALI.IMPDTE',0,1526828649);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.SchedaTecni.ALT',0,835318545);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SEZ','VIS','GENE.SchedaTeim.ALT',0,3279515167);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SUBMENU','VIS','ARCHIVI.Archivio-staz-appaltanti',0,1400821008);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SUBMENU','VIS','ARCHIVI.Archivio-uffici-intestatari',0,2798010941);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SUBMENU','VIS','ARCHIVI.Archivio-utenti',0,3100152466);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SUBMENU','VIS','UTILITA.Import-export-modelli',0,3037951636);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SUBMENU','VIS','UTILITA.Import-export-report',0,2149004479);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-APPA','SUBMENU','VIS','UTILITA.Manuali',0,3283288817);



Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (48,'SA-APPA');
Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (49,'SA-APPA');
Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (50,'SA-APPA');


Insert into W_GRUPPI (ID_GRUPPO,NOME,DESCR,COD_PROFILO) Select distinct (select Coalesce(max(ID_GRUPPO),0)+1 from W_GRUPPI),'Comunicazioni di eventi di contratti','default per il profilo SA-APPA','SA-APPA' from W_PROFILI where not exists (select 1 from W_GRUPPI where cod_profilo='SA-APPA');
Update W_GENCHIAVI Set CHIAVE=(Select max(ID_GRUPPO) from W_GRUPPI) where TABELLA='W_GRUPPI';
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 48,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-APPA'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-APPA' and id_account=48);
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 49,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-APPA'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-APPA' and id_account=49);
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 50,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-APPA'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-APPA' and id_account=50);

Delete from W_ACCPRO where COD_PROFILO='SA-AVVISI' and ID_ACCOUNT in (48,49,50);
Delete from W_PROAZI where COD_PROFILO='SA-AVVISI';
Delete from W_PROFILI where COD_PROFILO='SA-AVVISI';

Insert into W_PROFILI (COD_PROFILO,CODAPP,NOME,DESCRIZIONE,FLAG_INTERNO,DISCRIMINANTE,COD_CLIENTE,CRC) values ('SA-AVVISI','W9','Avvisi pre-CIG e smartCIG','Comunicazioni di avvisi per procedure per le quali non è ancora stato richiesto il CIG o per le quali è previsto lo smartCIG',0,null,1091,800826667);

Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','MAN','GENE.IMPR.CFIMP',1,2065006782);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','MAN','GENE.IMPR.NOMEST',1,3277781792);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','MAN','GENE.TECNI.CFTEC',1,3376415354);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','MAN','GENE.TECNI.NOMTEC',1,3922502992);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','MAN','GENE.TEIM.NOMTIM',1,570191537);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','MAN','GENE.UFFINT.NOMEIN',1,231133415);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','MOD','GENE.UFFINT.CFEIN',0,1172411222);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','*',1,4179291998);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.CATE.*',0,370569137);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.IMPDTE.*',0,330687099);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.IMPR.CODIMP',0,3867247338);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.IMPR.GFIIMP',0,3444650880);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.IMPR.MGSFLG',0,3557805759);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.IMPR.NAZIMP',0,1653386169);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.IMPR.TIPIMP',0,130151157);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.*',0,2991376634);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.CAPTEC',1,2262503981);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.CFTEC',1,3479026769);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.CITTEC',1,2668115584);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.CODTEC',0,1589015177);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.COGTEI',1,2499727429);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.EMATEC',1,3477690718);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.FAXTEC',1,3834869323);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.INDTEC',1,1184747252);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.LOCTEC',1,1842999014);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.MGSFLG',0,2713166260);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.NCITEC',1,3342083861);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.NOMETEI',1,3837335197);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.NOMTEC',1,1165377966);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.PROTEC',1,3072320536);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.TELTEC',1,438536440);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.WEBPWD',0,1014852124);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TECNI.WEBUSR',0,271746853);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TEIM.*',0,1916466235);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TEIM.CFTIM',1,1497752044);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TEIM.CODTIM',0,1008853949);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TEIM.COGTIM',1,210151931);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TEIM.NOMETIM',1,3897678699);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.TEIM.NOMTIM',1,669110426);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.CODEIN',0,1286571583);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.CODNAZ',0,3138619347);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.CODRES',0,1998027873);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.CODSTA',0,2864423568);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.DATICC',0,554502752);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.DNAEIN',0,2327110756);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.DOFEIN',0,4090031057);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.INDWEB',0,2084316072);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.IVAEIN',0,82183506);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.LNAEIN',0,3109792259);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.NUMICC',0,1892343888);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.PROICC',0,552494107);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.PROUFF',0,1100855954);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','COLS','VIS','GENE.UFFINT.USERID',0,921977655);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.GENE.C0OGGASS',0,1888279893);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.GENE.G_NOTEAVVISI',0,4289628934);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.GENE.HELPPAGINA',0,2611775279);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.GENE.UTENT-lista.HELPPAGINA',0,1352063578);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.GENE.UTENT-scheda.HELPPAGINA',0,2022395450);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.GENE.UTENT-trova.HELPPAGINA',0,4258458524);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.GENE.W_MODELLI',0,2702599471);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.W9.W9HOME.APPA',0,153343456);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.W9.W9HOME.ORT-INBX',0,306086409);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.W9.W9HOME.PIATRI',0,2048444531);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.W9.W9HOME.SA-ADMIN',0,1456500244);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.W9.W9HOME.SA-AVVISI',1,712842299);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.W9.TRASFERISCI-APPALTO',0,228161805);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','ALT.W9.W9SCP',1,1534573450);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','FUNZ','VIS','INS.GENE.ImprScheda.DATIGEN.INS-AIN',0,3816322368);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','MENU','VIS','REPORT',0,3456026856);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','MENU','VIS','STRUMENTI',0,2622732340);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','PAGE','VIS','GENE.ImprScheda.CASE',0,322106228);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','PAGE','VIS','GENE.ImprScheda.CATE',0,377349617);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','PAGE','VIS','GENE.ImprScheda.RAGIMP',0,4033516002);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.ImprScheda.DATIGEN.ABI',0,4156912262);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.ImprScheda.DATIGEN.AIN',0,3033598880);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.ImprScheda.DATIGEN.ALT',0,37172112);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.ImprScheda.DATIGEN.IMPDTE',0,1737007254);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.ImprScheda.DATIGEN.IND',1,4190301654);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.ImprScheda.DATIGEN.INPS',0,3614861476);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.ImprScheda.DATIGEN.SOA',0,3453285577);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.ImprScheda.DATIGEN.XIMPR',0,2833513384);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SEZ','VIS','GENE.SchedaTecni.ALT',0,835318545);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SUBMENU','VIS','ARCHIVI.*',0,797864829);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SUBMENU','VIS','ARCHIVI.Archivio-tecnici',1,2191674685);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SUBMENU','VIS','UTILITA.Cambia-password',1,2174818627);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SUBMENU','VIS','UTILITA.Import-export-modelli',0,3037951636);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SUBMENU','VIS','UTILITA.Import-export-report',0,2149004479);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SUBMENU','VIS','UTILITA.Manuali',1,3031708263);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-AVVISI','SUBMENU','VIS','UTILITA.Mio-account',1,2762046477);


Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (48,'SA-AVVISI');
Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (49,'SA-AVVISI');
Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (50,'SA-AVVISI');


Insert into W_GRUPPI (ID_GRUPPO,NOME,DESCR,COD_PROFILO) Select distinct (select Coalesce(max(ID_GRUPPO),0)+1 from W_GRUPPI),'Manifestazione di interesse ed altri avvisi','default per il profilo SA-AVVISI','SA-AVVISI' from W_PROFILI where not exists (select 1 from W_GRUPPI where cod_profilo='SA-AVVISI');
Update W_GENCHIAVI Set CHIAVE=(Select max(ID_GRUPPO) from W_GRUPPI) where TABELLA='W_GRUPPI';
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 48,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-AVVISI'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-AVVISI' and id_account=48);
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 49,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-AVVISI'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-AVVISI' and id_account=49);
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 50,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-AVVISI'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-AVVISI' and id_account=50);

Delete from W_ACCPRO where COD_PROFILO='SA-PIATRI' and ID_ACCOUNT in (48,49,50);
Delete from W_PROAZI where COD_PROFILO='SA-PIATRI';
Delete from W_PROFILI where COD_PROFILO='SA-PIATRI';

Insert into W_PROFILI (COD_PROFILO,CODAPP,NOME,DESCRIZIONE,FLAG_INTERNO,DISCRIMINANTE,COD_CLIENTE,CRC) values ('SA-PIATRI','W9','Sitat SA - Comunicazioni di programmi','Comunicazioni di eventi di programmi triennali/annuali di lavori, forniture e servizi',1,null,1091,80257539);

Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','MAN','GENE.IMPR.CFIMP',1,2065006782);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','MAN','GENE.IMPR.NOMEST',1,3277781792);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','MAN','GENE.TECNI.CFTEC',1,3376415354);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','MAN','GENE.TECNI.NOMTEC',1,3922502992);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','MAN','GENE.TEIM.NOMTIM',1,570191537);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','MAN','GENE.UFFINT.NOMEIN',1,231133415);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','MOD','GENE.UFFINT.CFEIN',0,1172411222);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','*',1,4179291998);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.CATE.*',0,370569137);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPDTE.*',0,330687099);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.*',0,4259985762);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.CAPIMP',1,1055711822);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.CFIMP',1,3491673339);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.CODCIT',1,2640199616);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.EMAI2IP',1,2274275292);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.EMAIIP',1,1886847457);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.FAXIMP',1,1554286120);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.INDIMP',1,4272570007);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.LOCIMP',1,3589152389);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.NATGIUI',1,3278708774);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.NAZIMP',1,361470767);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.NCCIAA',1,1869616547);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.NCIIMP',1,2130958198);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.NOMEST',1,3309520139);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.PIVIMP',1,197727184);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.PROIMP',1,254284923);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.IMPR.TELIMP',1,2719250587);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.*',0,2991376634);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.CAPTEC',1,2262503981);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.CFTEC',1,3479026769);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.CITTEC',1,2668115584);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.CODTEC',0,1589015177);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.COGTEI',1,2499727429);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.EMATEC',1,3477690718);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.FAXTEC',1,3834869323);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.INDTEC',1,1184747252);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.LOCTEC',1,1842999014);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.MGSFLG',0,2713166260);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.NCITEC',1,3342083861);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.NOMETEI',1,3837335197);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.NOMTEC',1,1165377966);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.PROTEC',1,3072320536);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.TELTEC',1,438536440);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.WEBPWD',0,1014852124);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TECNI.WEBUSR',0,271746853);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TEIM.*',0,1916466235);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TEIM.CFTIM',1,1497752044);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TEIM.CODTIM',0,1008853949);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TEIM.COGTIM',1,210151931);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TEIM.NOMETIM',1,3897678699);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.TEIM.NOMTIM',1,669110426);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.CODEIN',0,1286571583);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.CODNAZ',0,3138619347);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.CODRES',0,1998027873);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.CODSTA',0,2864423568);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.DATICC',0,554502752);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.DNAEIN',0,2327110756);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.DOFEIN',0,4090031057);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.INDWEB',0,2084316072);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.IVAEIN',0,82183506);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.LNAEIN',0,3109792259);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.NUMICC',0,1892343888);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.PROICC',0,552494107);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.PROUFF',0,1100855954);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','GENE.UFFINT.USERID',0,921977655);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.PIATRI.STATRI',0,1851973007);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.PIATRI.TADOZI',0,3107290751);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.PIATRI.TAPTRI',0,2774453787);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','MOD','PIANI.PIATRI.BRETRI',0,2882420043);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.INTTRI.ACQVERDI',1,4026006493);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.INTTRI.DIRGEN',1,381433749);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.INTTRI.MATRIC',1,994321679);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.INTTRI.QUANTIT',1,3937949287);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.INTTRI.REFERE',1,2693057517);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.INTTRI.STRUOP',1,676045197);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.INTTRI.UNIMIS',1,1156269016);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','COLS','VIS','PIANI.INTTRI.VALUTAZIONE',1,2541728618);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.GENE.C0OGGASS',0,1888279893);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.GENE.G_NOTEAVVISI',0,4289628934);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.GENE.HELPPAGINA',0,2611775279);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.GENE.Homepage-EseguiReport',0,3712486845);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.GENE.UTENT-lista.HELPPAGINA',0,1352063578);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.GENE.UTENT-scheda.HELPPAGINA',0,2022395450);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.GENE.UTENT-trova.HELPPAGINA',0,4258458524);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.GENE.W_MODELLI',0,2702599471);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.PIANI.CREATE-XML',0,214293566);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.PIANI.SITAT',1,3454430183);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.W9.W9HOME.APPA',0,153343456);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.W9.W9HOME.ORT-INBX',0,306086409);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.W9.W9HOME.PIATRI',1,220199141);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.W9.W9HOME.SA-ADMIN',0,1456500244);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.W9.W9HOME.SA-AVVISI',0,1568287917);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.W9.TRASFERISCI-APPALTO',0,228161805);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','ALT.W9.W9SCP',1,1534573450);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','INS.GENE.ImprScheda.DATIGEN.INS-AIN',0,3816322368);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-G_IMPCOL',0,1319656519);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-IMPAZI',0,147612543);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-IMPDTE',0,2570711371);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','MENU','VIS','REPORT',0,3456026856);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','MENU','VIS','STRUMENTI',0,2622732340);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','PAGE','VIS','GENE.ImprScheda.CASE',0,322106228);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','PAGE','VIS','GENE.ImprScheda.CATE',0,377349617);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','PAGE','VIS','GENE.ImprScheda.CATEGARE',0,1917287626);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','PAGE','VIS','GENE.ImprScheda.IMPANTIMAFIA',0,960791731);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','PAGE','VIS','GENE.ImprScheda.RAGIMP',0,4033516002);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','MOD','W9.W9LOTT-scheda.DATIDET.MODLAV',1,1184464117);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.ABI',0,4156912262);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.AIN',0,3033598880);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.ALT',0,37172112);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.IMPDTE',0,1737007254);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.IND',1,4190301654);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.INPS',0,3614861476);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.ISO1',0,900991630);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.PERSDIP',0,507325010);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.SOA',0,3453285577);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.DATIGEN.XIMPR',0,2833513384);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.LEGALI.IMPAZI',0,3404995677);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.ImprScheda.LEGALI.IMPDTE',0,1526828649);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.SchedaTecni.ALT',0,835318545);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','GENE.SchedaTeim.ALT',0,3279515167);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','PIANI.INTTRI-scheda.INTERVENTO.ACQVERDI',1,1905031520);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','PIANI.INTTRI-scheda.INTERVENTO.MATRIC',1,4141436124);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SEZ','VIS','W9.W9LOTT-scheda.DATIDET.MODLAV',1,3404640381);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SUBMENU','VIS','ARCHIVI.Archivio-staz-appaltanti',0,1400821008);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SUBMENU','VIS','ARCHIVI.Archivio-uffici-intestatari',0,2798010941);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SUBMENU','VIS','ARCHIVI.Archivio-utenti',0,3100152466);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SUBMENU','VIS','UTILITA.Import-export-modelli',0,3037951636);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SUBMENU','VIS','UTILITA.Import-export-report',0,2149004479);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI','SUBMENU','VIS','UTILITA.Manuali',0,3283288817);


Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (48,'SA-PIATRI');
Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (49,'SA-PIATRI');
Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (50,'SA-PIATRI');


Insert into W_GRUPPI (ID_GRUPPO,NOME,DESCR,COD_PROFILO) Select distinct (select Coalesce(max(ID_GRUPPO),0)+1 from W_GRUPPI),'Comunicazioni programmi triennali','default per il profilo SA-PIATRI','SA-PIATRI' from W_PROFILI where not exists (select 1 from W_GRUPPI where cod_profilo='SA-PIATRI');
Update W_GENCHIAVI Set CHIAVE=(Select max(ID_GRUPPO) from W_GRUPPI) where TABELLA='W_GRUPPI';
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 48,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-PIATRI'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-PIATRI' and id_account=48);
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 49,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-PIATRI'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-PIATRI' and id_account=49);
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 50,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-PIATRI'  and not exists 
	(select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-PIATRI' and id_account=50);

Delete from W_ACCPRO where COD_PROFILO='SA-PIATRI-RT' and ID_ACCOUNT in (48,49,50);
Delete from W_PROAZI where COD_PROFILO='SA-PIATRI-RT';
Delete from W_PROFILI where COD_PROFILO='SA-PIATRI-RT';

Insert into W_PROFILI (COD_PROFILO,CODAPP,NOME,DESCRIZIONE,FLAG_INTERNO,DISCRIMINANTE,COD_CLIENTE,CRC) values ('SA-PIATRI-RT','W9','Comunicazioni di programmi (Regione Toscana)','Comunicazioni di eventi di programmi di lavori, forniture e servizi (Regione Toscana)',1,null,1091,1270918309);

Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','MAN','GENE.IMPR.CFIMP',1,2065006782);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','MAN','GENE.IMPR.NOMEST',1,3277781792);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','MAN','GENE.TECNI.CFTEC',1,3376415354);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','MAN','GENE.TECNI.NOMTEC',1,3922502992);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','MAN','GENE.TEIM.NOMTIM',1,570191537);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','MAN','GENE.UFFINT.NOMEIN',1,231133415);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','MOD','GENE.UFFINT.CFEIN',0,1172411222);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','*',1,4179291998);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.CATE.*',0,370569137);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPDTE.*',0,330687099);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.*',0,4259985762);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.CAPIMP',1,1055711822);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.CFIMP',1,3491673339);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.CODCIT',1,2640199616);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.EMAI2IP',1,2274275292);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.EMAIIP',1,1886847457);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.FAXIMP',1,1554286120);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.INDIMP',1,4272570007);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.LOCIMP',1,3589152389);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.NATGIUI',1,3278708774);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.NAZIMP',1,361470767);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.NCCIAA',1,1869616547);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.NCIIMP',1,2130958198);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.NOMEST',1,3309520139);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.PIVIMP',1,197727184);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.PROIMP',1,254284923);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.IMPR.TELIMP',1,2719250587);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.*',0,2991376634);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.CAPTEC',1,2262503981);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.CFTEC',1,3479026769);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.CITTEC',1,2668115584);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.CODTEC',0,1589015177);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.COGTEI',1,2499727429);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.EMATEC',1,3477690718);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.FAXTEC',1,3834869323);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.INDTEC',1,1184747252);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.LOCTEC',1,1842999014);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.MGSFLG',0,2713166260);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.NCITEC',1,3342083861);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.NOMETEI',1,3837335197);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.NOMTEC',1,1165377966);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.PROTEC',1,3072320536);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.TELTEC',1,438536440);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.WEBPWD',0,1014852124);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TECNI.WEBUSR',0,271746853);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TEIM.*',0,1916466235);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TEIM.CFTIM',1,1497752044);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TEIM.CODTIM',0,1008853949);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TEIM.COGTIM',1,210151931);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TEIM.NOMETIM',1,3897678699);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.TEIM.NOMTIM',1,669110426);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.CODEIN',0,1286571583);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.CODNAZ',0,3138619347);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.CODRES',0,1998027873);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.CODSTA',0,2864423568);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.DATICC',0,554502752);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.DNAEIN',0,2327110756);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.DOFEIN',0,4090031057);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.INDWEB',0,2084316072);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.IVAEIN',0,82183506);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.LNAEIN',0,3109792259);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.NUMICC',0,1892343888);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.PROICC',0,552494107);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.PROUFF',0,1100855954);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','GENE.UFFINT.USERID',0,921977655);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.RESPUF',1,1092694516);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.PIATRI.STATRI',0,1851973007);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.PIATRI.TADOZI',0,3107290751);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.PIATRI.TAPTRI',0,2774453787);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.ACQVERDI',1,4026006493);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.DIRGEN',1,381433749);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.MATRIC',1,994321679);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.QUANTIT',1,3937949287);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.REFERE',1,2693057517);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.STRUOP',1,676045197);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.UNIMIS',1,1156269016);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.INTTRI.VALUTAZIONE',1,2541728618);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.RIS_CAPITOLO.IMPALTCB',1,2216124492);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.RIS_CAPITOLO.IMPRFSCB',1,3593613409);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','COLS','VIS','PIANI.RIS_CAPITOLO.RG1CB',1,2989739536);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.GENE.C0OGGASS',0,1888279893);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.GENE.G_NOTEAVVISI',0,4289628934);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.GENE.HELPPAGINA',0,2611775279);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.GENE.Homepage-EseguiReport',0,3712486845);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.GENE.UTENT-lista.HELPPAGINA',0,1352063578);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.GENE.UTENT-scheda.HELPPAGINA',0,2022395450);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.GENE.UTENT-trova.HELPPAGINA',0,4258458524);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.GENE.W_MODELLI',0,2702599471);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.PIANI.CREATE-XML',0,214293566);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.PIANI.SITAT',1,3454430183);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.W9.W9HOME.APPA',0,153343456);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.W9.W9HOME.ORT-INBX',0,306086409);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.W9.W9HOME.PIATRI',1,220199141);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.W9.W9HOME.SA-ADMIN',0,1456500244);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.W9.W9HOME.SA-AVVISI',0,1568287917);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.W9.TRASFERISCI-APPALTO',0,228161805);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','ALT.W9.W9SCP',1,1534573450);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','INS.GENE.ImprScheda.DATIGEN.INS-AIN',0,3816322368);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-G_IMPCOL',0,1319656519);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-IMPAZI',0,147612543);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','FUNZ','VIS','INS.GENE.ImprScheda.LEGALI.INS-IMPDTE',0,2570711371);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','MENU','VIS','REPORT',0,3456026856);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','MENU','VIS','STRUMENTI',0,2622732340);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','PAGE','VIS','GENE.ImprScheda.CASE',0,322106228);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','PAGE','VIS','GENE.ImprScheda.CATE',0,377349617);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','PAGE','VIS','GENE.ImprScheda.CATEGARE',0,1917287626);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','PAGE','VIS','GENE.ImprScheda.IMPANTIMAFIA',0,960791731);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','PAGE','VIS','GENE.ImprScheda.RAGIMP',0,4033516002);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','PAGE','VIS','PIANI.INTTRI-scheda.RISORSEBILANCIO',1,2705155577);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','MOD','W9.W9LOTT-scheda.DATIDET.MODLAV',1,1184464117);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.ABI',0,4156912262);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.AIN',0,3033598880);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.ALT',0,37172112);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.IMPDTE',0,1737007254);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.IND',1,4190301654);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.INPS',0,3614861476);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.ISO1',0,900991630);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.PERSDIP',0,507325010);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.SOA',0,3453285577);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.DATIGEN.XIMPR',0,2833513384);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.LEGALI.IMPAZI',0,3404995677);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.ImprScheda.LEGALI.IMPDTE',0,1526828649);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.SchedaTecni.ALT',0,835318545);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','GENE.SchedaTeim.ALT',0,3279515167);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','PIANI.INTTRI-scheda.INTERVENTO.ACQVERDI',1,1905031520);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','PIANI.INTTRI-scheda.INTERVENTO.MATRIC',1,4141436124);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SEZ','VIS','W9.W9LOTT-scheda.DATIDET.MODLAV',1,3404640381);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SUBMENU','VIS','ARCHIVI.Archivio-staz-appaltanti',0,1400821008);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SUBMENU','VIS','ARCHIVI.Archivio-uffici-intestatari',0,2798010941);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SUBMENU','VIS','ARCHIVI.Archivio-utenti',0,3100152466);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SUBMENU','VIS','UTILITA.Import-export-modelli',0,3037951636);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SUBMENU','VIS','UTILITA.Import-export-report',0,2149004479);
Insert into W_PROAZI (COD_PROFILO,TIPO,AZIONE,OGGETTO,VALORE,CRC) values ('SA-PIATRI-RT','SUBMENU','VIS','UTILITA.Manuali',0,3283288817);


Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (48,'SA-PIATRI-RT');
Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (49,'SA-PIATRI-RT');
Insert into W_ACCPRO (ID_ACCOUNT,COD_PROFILO) values (50,'SA-PIATRI-RT');


Insert into W_GRUPPI (ID_GRUPPO,NOME,DESCR,COD_PROFILO) Select distinct (select Coalesce(max(ID_GRUPPO),0)+1 from W_GRUPPI),'Comunicazioni di programmi (Regione Toscana)','default per il profilo SA-PIATRI-RT','SA-PIATRI-RT' from W_PROFILI where not exists (select 1 from W_GRUPPI where cod_profilo='SA-PIATRI-RT');
Update W_GENCHIAVI Set CHIAVE=(Select max(ID_GRUPPO) from W_GRUPPI) where TABELLA='W_GRUPPI';
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 48,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-PIATRI-RT'  and not exists (
		select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-PIATRI-RT' and id_account=48);
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 49,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-PIATRI-RT'  and not exists (
		select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-PIATRI-RT' and id_account=49);
Insert into W_ACCGRP (ID_ACCOUNT,ID_GRUPPO,PRIORITA) select 50,ID_GRUPPO,0 from W_GRUPPI where COD_PROFILO='SA-PIATRI-RT'  and not exists (
		select * from W_ACCGRP A join W_GRUPPI G on A.id_gruppo=G.id_gruppo where cod_profilo='SA-PIATRI-RT' and id_account=50);
