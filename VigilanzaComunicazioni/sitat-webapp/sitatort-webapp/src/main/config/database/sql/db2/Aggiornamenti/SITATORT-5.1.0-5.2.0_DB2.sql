------ SCHEMA SITAT ORT ----------
SET SCHEMA = SITATORT;
SET CURRENT PATH = SITATORT;


UPDATE COMMAND OPTIONS USING s OFF;
DROP procedure aggiorna;
UPDATE COMMAND OPTIONS USING s ON;

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @
create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and numver like '1.13.%')=1
	THEN
		
		Update C0ENTIT set COE_KEY_EX = 'RUP.W9GARA.W9' where C0E_NOM = 'TECNI.GENE#29';
		Update C0ENTIT set COE_KEY_EX = 'RUP.W9LOTT.W9' where C0E_NOM = 'TECNI.GENE#30';
		
		--- Tabellato per la conversione del codice nazione NAZIMP (Ag010) nel codice ISO_3166-1_alpha-2
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','16', 'AD','Andorra');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','17', 'AE','Emirati Arabi Uniti');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','18', 'AF','Afghanistan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','19', 'AG','Antigua e Barbuda');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','20', 'AI','Anguilla');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','21', 'AL','Albania');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','22', 'AM','Armenia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','23', 'AN','Antille Olandesi');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','24', 'AO','Angola');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','25', 'AQ','Antartico');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','26', 'AR','Argentina');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','27', 'AS','Samoa Americane');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','2',  'AT','Austria');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','28', 'AU','Australia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','29', 'AW','Aruba');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','30', 'AX','Isole Aland');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','31', 'AZ','Azerbaigian');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','32', 'BA','Bosnia-Erzegovina');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','33', 'BB','Barbados');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','34', 'BD','Bangladesh');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','3',  'BE','Belgio');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','35', 'BF','Burkina Faso');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','36', 'BG','Bulgaria');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','37', 'BH','Bahrain');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','38', 'BI','Burundi');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','39', 'BJ','Benin');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','40', 'BL','Saint-Barthelemy');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','41', 'BM','Bermuda');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','42', 'BN','Brunei');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','43', 'BO','Bolivia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','44', 'BR','Brasile');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','45', 'BS','Bahamas');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','46', 'BT','Bhutan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','47', 'BV','Isola Bouvet');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','48', 'BW','Botswana');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','49', 'BY','Bielorussia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','50', 'BZ','Belize');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','51', 'CA','Canada');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','52', 'CC','Isole Cocos');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','53', 'CD','Repubblica Democratica del Congo');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','54', 'CF','Repubblica Centrafricana');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','55', 'CG','Repubblica del Congo');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','56', 'CH','Svizzera');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','57', 'CI','Costa d''Avorio');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','58', 'CK','Isole Cook');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','59', 'CL','Cile');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','60', 'CM','Camerun');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','61', 'CN','Cina');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','62', 'CO','Colombia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','63', 'CR','Costa Rica');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','64', 'CU','Cuba');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','65', 'CV','Capo Verde');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','66', 'CX','Isola Christmas');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','67', 'CY','Cipro');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','68', 'CZ','Repubblica Ceca');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','7',  'DE','Germania');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','69', 'DJ','Gibuti');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','4',  'DK','Danimarca');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','70', 'DM','Dominica');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','71', 'DO','Repubblica Dominicana');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','72', 'DZ','Algeria');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','73', 'EC','Ecuador');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','74', 'EE','Estonia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','75', 'EG','Egitto');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','76', 'EH','Sahara Occidentale');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','77', 'ER','Eritrea');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','14', 'ES','Spagna');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','78', 'ET','Etiopia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','5',  'FI','Finlandia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','79', 'FJ','Figi');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','80', 'FK','Isole Falkland');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','81', 'FM','Stati Federati di Micronesia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','82', 'FO','Isole Faorer');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','6',  'FR','Francia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','83', 'GA','Gabon');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','13', 'GB','Regno Unito');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','84', 'GD','Grenada');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','85', 'GE','Georgia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','86', 'GF','Guyana Francese');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','87', 'GG','Guernsey');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','88', 'GH','Ghana');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','89', 'GI','Gibilterra');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','90', 'GL','Groenlandia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','91', 'GM','Gambia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','92', 'GN','Guinea');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','93', 'GP','Guadalupa');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','94', 'GQ','Guinea Equatoriale');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','8',  'GR','Grecia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','95', 'GS','Georgia del Sud e isole Sandwich meridionali');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','96', 'GT','Guatemala');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','97', 'GU','Guam');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','98', 'GW','Guinea-Bissau');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','247','GY','Guyana');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','100','HK','Hong Kong');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','101','HM','Isole Heard e McDonald');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','102','HN','Honduras');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','103','HR','Croazia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','104','HT','Haiti');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','105','HU','Ungheria');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','106','ID','Indonesia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','9',  'IE','Irlanda');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','107','IL','Israele');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','108','IM','Isola di Man');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','109','IN','India');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','110','IO','Territori Britannici dell''Oceano Indiano');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','111','IQ','Iraq');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','112','IR','Iran');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','113','IS','Islanda');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','114','JE','Jersey');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','115','JM','Giamaica');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','116','JO','Giordania');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','117','JP','Giappone');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','118','KE','Kenya');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','119','KG','Kirghizistan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','120','KH','Cambogia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','121','KI','Kiribati');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','122','KM','Comore');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','123','KN','Saint Kitts e Nevis');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','124','KP','Corea del Nord');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','125','KR','Corea del Sud');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','126','KW','Kuwait');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','127','KY','Isole Cayman');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','128','KZ','Kazakistan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','129','LA','Laos');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','130','LB','Libano');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','131','LC','Santa Lucia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','132','LI','Liechtenstein');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','133','LK','Sri Lanka');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','134','LR','Liberia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','135','LS','Lesotho');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','136','LT','Lituania');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','10', 'LU','Lussemburgo');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','137','LV','Lettonia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','138','LY','Libia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','139','MA','Marocco');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','140','MC','Monaco');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','141','MD','Moldavia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','142','ME','Montenegro');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','143','MF','Saint-Martin');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','144','MG','Madagascar');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','145','MH','Isole Marshall');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','146','MK','Macedonia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','147','ML','Mali');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','148','MM','Birmania');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','149','MN','Mongolia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','150','MO','Macao');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','151','MP','Isole Marianne Settentrionali');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','152','MQ','Martinica');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','153','MR','Mauritania');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','154','MS','Montserrat');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','155','MT','Malta');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','156','MU','Mauritius');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','157','MV','Maldive');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','158','MW','Malawi');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','159','MX','Messico');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','160','MY','Malesia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','161','MZ','Mozambico');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','162','NA','Namibia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','163','NC','Nuova Caledonia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','164','NE','Niger');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','165','NF','Isola Norfolk');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','166','NG','Nigeria');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','167','NI','Nicaragua');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','11', 'NL','Olanda');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','168','NO','Norvegia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','169','NP','Nepal');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','170','NR','Nauru');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','171','NU','Niue');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','172','NZ','Nuova Zelanda');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','173','OM','Oman');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','174','PA','Panama');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','175','PE','Peru''');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','176','PF','Polinesia Francese');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','177','PG','Papua Nuova Guinea');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','178','PH','Filippine');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','179','PK','Pakistan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','180','PL','Polonia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','181','PM','Saint Pierre e Miquelon');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','182','PN','Isole Pitcairn');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','183','PR','Porto Rico');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','184','PS','Territori Palestinesi Occupati');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','12', 'PT','Portogallo');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','185','PW','Palau');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','186','PY','Paraguay');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','187','QA','Qatar');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','188','RE','Reunion');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','189','RO','Romania');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','190','RS','Serbia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','191','RU','Russia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','192','RW','Ruanda');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','193','SA','Arabia Saudita');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','194','SB','Isole Salomone');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','195','SC','Seychelles');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','196','SD','Sudan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','15', 'SE','Svezia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','197','SG','Singapore');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','198','SH','Sant''Elena');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','199','SI','Slovenia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','200','SJ','Svalbard');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','201','SK','Slovacchia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','202','SL','Sierra Leone');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','203','SM','San Marino');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','204','SN','Senegal');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','205','SO','Somalia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','206','SR','Suriname');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','207','ST','Sao Tome''e Principe');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','208','SV','El Salvador');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','209','SY','Siria');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','210','SZ','Swaziland');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','211','TC','Isole Turks e Caicos');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','212','TD','Ciad');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','213','TF','Territori Francesi del Sud');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','214','TG','Togo');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','215','TH','Thailandia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','216','TJ','Tagikistan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','217','TK','Tokelau');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','218','TL','Timor Est');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','219','TM','Turkmenistan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','220','TN','Tunisia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','221','TO','Tonga');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','222','TR','Turchia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','223','TT','Trinidad e Tobago');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','224','TV','Tuvalu');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','225','TW','Repubblica di Cina');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','226','TZ','Tanzania');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','227','UA','Ucraina');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','228','UG','Uganda');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','229','UM','Isole minori esterne degli Stati Uniti');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','230','US','Stati Uniti d''America');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','231','UY','Uruguay');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','232','UZ','Uzbekistan');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','233','VA','Citta''del Vaticano');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','234','VC','Saint Vincent e Grenadine');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','235','VE','Venezuela');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','236','VG','Isole Vergini Britanniche');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','237','VI','Isole Vergini Americane');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','238','VN','Vietnam');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','239','VU','Vanuatu');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','240','WF','Wallis e Futuna');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','241','WS','Samoa');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','242','YE','Yemen');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','243','YT','Mayotte');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','244','ZA','Sudafrica');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','245','ZM','Zambia');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','246','ZW','Zimbabwe');
		Insert into TAB2 (TAB2COD,TAB2TIP,TAB2D1,TAB2D2) values ('G_z23','1',  'IT','Italia');

		-- Nuovo campo per ART80
		SET v_sql = 'ALTER TABLE IMPR ADD COLUMN ART80_UFF_CODEIN VARCHAR(16)';
		EXECUTE IMMEDIATE v_sql;
		
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'ART80_UFF_CODEIN.IMPR.GENE', 'G_A80UFCO', 'Ufficio di invio', 'Ufficio di invio', 'A16', NULL, NULL, 'Ufficio di invio');

		--Correzione CAP
		Update TABSCHE set tabcod4='36048' where tabcod='S2003' and tabcod1='09' and tabcod2='8639';
		Update TABSCHE set tabcod4='36064' where tabcod='S2003' and tabcod1='09' and tabcod2='8665';
		Update TABSCHE set tabcod4='36029' where tabcod='S2003' and tabcod1='09' and tabcod2='8664';

		Update ELDAVER set NUMVER = '1.14.0', DATVET = CURRENT_TIMESTAMP where CODAPP = 'G_';
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);

	IF (select count(*) from eldaver where codapp='G_' and numver = '1.14.0')=1
	THEN
		
		SET v_sql = 'ALTER TABLE TAB1 ALTER COLUMN TAB1DESC SET DATA TYPE VARCHAR(200)';
		EXECUTE IMMEDIATE v_sql;
		
        UPDATE C0CAMPI SET C0C_FS='A200' WHERE  C0C_MNE_BER='TAB1DESC';

		-- fix alle definizioni dei metadati
		
		DELETE FROM c0campi WHERE c0c_mne_ber = 'USCADATCANC';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (048001303, 'E', NULL, 'SYSDATCANC.USRCANC.GENE', 'USCADATCANC', 'Data eliminazione', 'Data eliminazione', 'A10', NULL, 'DATA_ELDA', NULL);
		
		DELETE FROM c0entit WHERE c0e_nom = 'COMP_G.GENE';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('COMP_G.GENE', 80010100, 'E', 'INT_UFF', 'Intestazione ufficio titolare', 'C_COMP.COMP_G.GENE,C_CENT.COMP_G.GENE,C_NUCL.COMP_G.GENE,C_SQUA.COMP_G.GENE', NULL, NULL, 'g_comp2', 'g_comp1', 'g_comp0');
		
		DELETE FROM c0entit WHERE c0e_nom = 'G_FUNZ.GENE';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('G_FUNZ.GENE', 80010200, 'E', 'FUNZ_I_UFF', 'Funzionari Intestazione Ufficio', 'C_COMP.G_FUNZ.GENE', 'COMP_G.GENE', 'C_COMP.COMP_G.GENE', NULL, NULL, 'g_funz0');

		DELETE FROM c0entit WHERE c0e_nom = 'C0RIC.GENE';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('C0RIC.GENE', 80040108, 'C', NULL, 'Testata delle ricerche generiche', 'C0RCOD.C0RIC.GENE', NULL, NULL, NULL, NULL, 'g__c0r0');

		DELETE FROM c0entit WHERE c0e_nom = 'PUNTICON.GENE#1';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('PUNTICON.GENE#1', 88000052, 'C', NULL, 'Punti di contatto dell''ufficio intestatario', 'CODEIN.PUNTICON.GENE,NUMPUN.PUNTICON.GENE', 'TORN.GARE', 'CENINT.TORN.GARE,PCOPRE.TORN.GARE', NULL, NULL, 'g_pcont0');
		DELETE FROM c0entit WHERE c0e_nom = 'PUNTICON.GENE#2';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('PUNTICON.GENE#2', 88000052, 'C', NULL, 'Punti di contatto dell''ufficio intestatario', 'CODEIN.PUNTICON.GENE,NUMPUN.PUNTICON.GENE', 'TORN.GARE', 'CENINT.TORN.GARE,PCODOC.TORN.GARE', NULL, NULL, 'g_pcont0');
		DELETE FROM c0entit WHERE c0e_nom = 'PUNTICON.GENE#3';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('PUNTICON.GENE#3', 88000052, 'C', NULL, 'Punti di contatto dell''ufficio intestatario', 'CODEIN.PUNTICON.GENE,NUMPUN.PUNTICON.GENE', 'TORN.GARE', 'CENINT.TORN.GARE,PCOOFF.TORN.GARE', NULL, NULL, 'g_pcont0');
		DELETE FROM c0entit WHERE c0e_nom = 'PUNTICON.GENE#4';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('PUNTICON.GENE#4', 88000052, 'C', NULL, 'Punti di contatto dell''ufficio intestatario', 'CODEIN.PUNTICON.GENE,NUMPUN.PUNTICON.GENE', 'TORN.GARE', 'CENINT.TORN.GARE,PCOGAR.TORN.GARE', NULL, NULL, 'g_pcont0');
		DELETE FROM c0entit WHERE c0e_nom = 'PUNTICON.GENE#5';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('PUNTICON.GENE#5', 88000052, 'C', NULL, 'Punti di contatto dell''ufficio intestatario', 'CODEIN.PUNTICON.GENE,NUMPUN.PUNTICON.GENE', 'GARECONT.GARE', 'CENINT.TORN.GARE,PCOESE.GARECONT.GARE', NULL, NULL, 'g_pcont0');
		DELETE FROM c0entit WHERE c0e_nom = 'PUNTICON.GENE#6';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('PUNTICON.GENE#6', 88000052, 'C', NULL, 'Punti di contatto dell''ufficio intestatario', 'CODEIN.PUNTICON.GENE,NUMPUN.PUNTICON.GENE', 'GARECONT.GARE', 'CENINT.TORN.GARE,PCOFAT.GARECONT.GARE', NULL, NULL, 'g_pcont0');

		Update ELDAVER set NUMVER = '1.14.1', DATVET = CURRENT_TIMESTAMP where CODAPP = 'G_';
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.9.%')=1
    THEN
	
		INSERT INTO w_oggetti (tipo, oggetto, descr, ord) VALUES ('FUNZ', 'ALT.GENE.SchedaUffint.Utenti', 'Associazioni utenti da scheda ufficio intestatario', 13540);
		
		INSERT INTO w_azioni (tipo, azione, oggetto, descr, valore, inor, viselenco, crc) VALUES ('FUNZ', 'VIS', 'ALT.GENE.SchedaUffint.Utenti', 'Visualizza', 0, 1, 1, 694064074);
		
		update eldaver set numver = '2.10.0', datvet=CURRENT_TIMESTAMP where codapp = 'W_';
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@
------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.10.%') = 1
    THEN

		SET v_sql = 'ALTER TABLE W_MESSAGE_IN DROP COLUMN MESSAGE_RECIPIENT_SYSCON';
		EXECUTE IMMEDIATE v_sql;
		SET v_sql = 'ALTER TABLE W_MESSAGE_IN ADD COLUMN MESSAGE_RECIPIENT_SYSCON DECIMAL(12)';
		EXECUTE IMMEDIATE v_sql;
		CALL SYSPROC.ADMIN_CMD ('REORG TABLE W_MESSAGE_IN');
		
		update eldaver set numver = '2.11.0M1', datvet=CURRENT_TIMESTAMP where codapp = 'W_';
		
	END IF;
end@

call aggiorna@
drop procedure aggiorna@


create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	
	IF (select count(*) from eldaver where codapp = 'W_' and numver like '2.11.0M1') = 1
    THEN
	
		DELETE FROM W_AZIONI WHERE OGGETTO = 'GENE.SchedaUffint.UFFSET';
		DELETE FROM W_AZIONI WHERE OGGETTO = 'INS.GENE.SchedaUffint.INS-UFFSET';
		DELETE FROM W_AZIONI WHERE OGGETTO = 'DEL.GENE.SchedaUffint.DEL-UFFSET';
		
		-- fix definizioni dei metadati
		DELETE FROM c0campi WHERE c0c_mne_ber = 'WSCODAOO';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'CODAOO.WSFASCICOLO.GENEWEB', 'WSCODAOO', 'Codice AOO (Amministrazione organizzativa)', 'Codice AOO', 'A30', NULL, NULL, NULL);
		
		DELETE FROM c0campi WHERE c0c_mne_ber = 'WSCODUFF';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'CODUFF.WSFASCICOLO.GENEWEB', 'WSCODUFF', 'Codice ufficio responsabile', 'Codice ufficio', 'A30', NULL, NULL, NULL);

		DELETE FROM c0campi WHERE c0c_mne_ber = 'WSSTRUTTU';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'STRUTTURA.WSFASCICOLO.GENEWEB', 'WSSTRUTTU', 'Struttura competente', 'Struttura competente', 'A2000', NULL, NULL, NULL);
		
		DELETE FROM c0campi WHERE c0c_mne_ber = 'WSISRISERV';
		INSERT INTO c0campi (coc_conta, c0c_tip, c0c_chi, coc_mne_uni, c0c_mne_ber, coc_des, coc_des_frm, c0c_fs, c0c_tab1, coc_dom, coc_des_web) VALUES (0, 'E', NULL, 'ISRISERVA.WSFASCICOLO.GENEWEB', 'WSISRISERV', 'Riservatezza attiva per il fascicolo?', 'Riservatezza attiva?', 'A2', NULL, 'SN', NULL);

		DELETE FROM c0entit WHERE c0e_nom = 'WSFASCICOLO.GENEWEB';
		INSERT INTO c0entit (c0e_nom, c0e_num, c0e_tip, coe_arg, c0e_des, c0e_key, c0e_nom_ex, coe_key_ex, c0e_frm_ri, coe_frm_ca, coe_frm_re) VALUES ('WSFASCICOLO.GENEWEB', 10000500, 'E', 'WSFASCICOL', 'Fascicolo documentale', 'ID.WSFASCICOLO.GENEWEB', NULL, NULL, NULL, NULL, NULL);
		
		update eldaver set numver = '2.11.0', datvet = CURRENT_TIMESTAMP where codapp = 'W_';

	END IF;
end@

--- Sono state volutamente escluse le tabelle RPA_LOG e RPA_SESSION e le viste RPA_C0CAMPI e RPA_C0ENTIT
--- perche' tabelle/viste specifiche del generatore modelli, che non e' usato in Regione Toscana.

call aggiorna@
drop procedure aggiorna@

--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 5.1.0 --> 5.1.1
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='5.1.0' or numver like '5.1.0.%') ) = 1
	THEN

		-----------------------------------------------------------------------------------------------------
		----------------------------    CREAZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
		-----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' ) = 0
		THEN
			Insert into ELDAVER (CODAPP,NUMVER,DATVET,MSG) values ('W9_UPDATE','1',CURRENT_TIMESTAMP,'Progressivo di aggiornamento (se presente significa che l''esecuzione di uno script di aggiornamento e'' terminato con errore)');
		END IF;

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='1' ) = 1
		THEN
		
			SET v_sql = 'Update ELDAVER set NUMVER=''2'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;
		
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='2' ) = 1
		THEN
			--- C0ENTIT e C0CAMPI 
			Update C0CAMPI set COC_DES='Numero offerte oltre la soglia di anomalia',COC_DES_FRM='Offerte oltre soglia', COC_DES_WEB='N. offerte oltre la soglia di anomalia' where COC_MNE_UNI='NUM_OFFERTE_FUORI_SOGLIA.W9APPA.W9';
			Update C0CAMPI set COC_DES='Numero di giorni di proroga non conseguenti a varianti', COC_DES_WEB='N. giorni di proroga non conseguenti a varianti' where COC_MNE_UNI='NUM_GIORNI_PROROGA.W9CONC.W9';

			SET v_sql = 'Update ELDAVER set NUMVER=''3'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='3' ) = 1
		THEN
			--- Modifiche tabellati
			Update TAB1 set TAB1DESC='Procedura negoziata senza previa indizione di gara (settori speciali)' where TAB1COD='W3005' and TAB1TIP=10;
			
			SET v_sql = 'Update ELDAVER set NUMVER=''4'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='4' ) = 1
		THEN
			--- W_OGGETTI e W_AZIONI
			--- Rimozione di voci profilabili per cancellazione delle funzioni associate
			DELETE from W_OGGETTI where OGGETTO like '%ALT.W9.SUBENTRO%';
			DELETE from W_AZIONI where OGGETTO like '%ALT.W9.SUBENTRO%';
			DELETE from W_OGGETTI where OGGETTO like '%ALT.W9.APPLICATION-KEYS%';
			DELETE from W_AZIONI where OGGETTO like '%ALT.W9.APPLICATION-KEYS%';

			Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('SUBMENU','UTILITA.Subentro','Trasferimento dati per subentro',13670);
			Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('SUBMENU','UTILITA.ApplicationKeys','Gestione credenziali servizi di pubblicazione',13680);
			
			INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('SUBMENU','VIS','UTILITA.Subentro','Visualizza',0,1,1,1294526338);
			INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('SUBMENU','VIS','UTILITA.ApplicationKeys','Visualizza',0,1,1,3501018188);
			INSERT INTO W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) VALUES ('FUNZ','VIS','ALT.W9.TRASFERISCI-APPALTO','Visualizza',0,1,1,1244507963);

			DELETE from W_CONFIG where CODAPP='W9' and CHIAVE='elencoCampi.solaLettura';
			INSERT INTO W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','elencoCampi.solaLettura',null,'Configurazione generale','Indicare l''elenco di campi in formato ENTITA.CAMPO separati da ";" per individuare i campi sempre in sola lettura indipendentemente dal profilo e per utenti NON AMMINISTRATORI DI SISTEMA (es.: IMPR.CFIMP;TEIM.CFTIM)',null);
			
			IF ( select count(*) from C0CAMPI where COC_MNE_UNI ='IMP_COMPL_INTERVENTO.W9VARI.W9' ) = 0
			THEN
				Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'IMP_COMPL_INTERVENTO.W9VARI.W9','W3VAIMPCOM','Importo complessivo intervento','Imp. compl. interv.','F15',null,'MONEY','Importo complessivo intervento');
			END IF;
			
			SET v_sql = 'Update ELDAVER set NUMVER=''5'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='5' ) = 1
		THEN
			
			SET v_sql = 'Update ELDAVER set NUMVER=''6'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='6' ) = 1
		THEN
			UPDATE ELDAVER set NUMVER='5.1.1', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='W9_UPDATE';
		END IF;

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 5.1.1 --> 5.1.2
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='5.1.1' or numver like '5.1.1.%') ) = 1
	THEN

		-----------------------------------------------------------------------------------------------------
		----------------------------    CREAZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
		-----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' ) = 0
		THEN
			Insert into ELDAVER (CODAPP,NUMVER,DATVET,MSG) values ('W9_UPDATE','1',CURRENT_TIMESTAMP,'Progressivo di aggiornamento (se presente significa che l''esecuzione di uno script di aggiornamento e'' terminato con errore)');
		END IF;

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='1' ) = 1
		THEN
		
			SET v_sql = 'Update ELDAVER set NUMVER=''2'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;
		
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='2' ) = 1
		THEN
			SET v_sql = 'Update ELDAVER set NUMVER=''3'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='3' ) = 1
		THEN
			SET v_sql = 'Update ELDAVER set NUMVER=''4'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='4' ) = 1
		THEN
			
			SET v_sql = 'Update ELDAVER set NUMVER=''5'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='5' ) = 1
		THEN
			
			SET v_sql = 'Update ELDAVER set NUMVER=''6'' where CODAPP=''W9_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='6' ) = 1
		THEN
			UPDATE ELDAVER set NUMVER='5.1.2', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='W9_UPDATE';
		END IF;

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------
-- AGGIORNAMENTO SITAT/VIGILANZA
-- Database: DB2
-- Versione: 5.1.2 --> 5.2.0
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(10000);

	IF ( select count(*) from eldaver where codapp='W9' and (numver='5.1.2' or numver like '5.1.2.%') ) = 1
	THEN

		-----------------------------------------------------------------------------------------------------
		----------------------------    CREAZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
		-----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' ) = 0
		THEN
			Insert into ELDAVER (CODAPP,NUMVER,DATVET,MSG) values ('W9_UPDATE','1',CURRENT_TIMESTAMP,'Progressivo di aggiornamento (se presente significa che l''esecuzione di uno script di aggiornamento e'' terminato con errore)');
		END IF;

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='1' ) = 1
		THEN
			SET v_sql= 'ALTER TABLE W_APPLICATION_KEYS ADD COLUMN CHIAVE VARCHAR(255)';
			EXECUTE immediate v_sql;
			SET v_sql= 'UPDATE W_APPLICATION_KEYS SET CHIAVE=KEY';
			EXECUTE immediate v_sql;
			SET v_sql= 'ALTER TABLE W_APPLICATION_KEYS DROP COLUMN KEY';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE W_APPLICATION_KEYS');
			
			SET v_sql= 'DROP view V_W9FLUSSI';
			EXECUTE immediate v_sql;
			SET v_sql= 'CREATE view V_W9FLUSSI(TIPO_INVIO, IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, AUTORE, NOTEINVIO)
				AS SELECT ''1'', IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, AUTORE, NOTEINVIO FROM W9FLUSSI
				UNION
				SELECT ''2'', IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, AUTORE, NOTEINVIO FROM W9FLUSSI_ELIMINATI';
			EXECUTE immediate v_sql;
			
			Update ELDAVER set NUMVER='100' where CODAPP='W9_UPDATE';
		END IF;
		
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='100' ) = 1
		THEN
			Delete from c0campi where COC_MNE_UNI in ('KEY.W_APPLICATION_KEYS.W9');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CHIAVE.W_APPLICATION_KEYS.W9','WAKCHIAVE','Chiave per l''autenticazione ai servizi di pubblicazione','Client Key','A255',null,null,'Chiave per l''autenticazione ai servizi di pubblicazione');
			Delete from c0campi where COC_MNE_UNI in ('DATA_CONSULTA_GARA.W9LOTT.W9');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DATA_CONSULTA_GARA.W9LOTT.W9','W9DIMPORTAZ','Data importazione lotto','Data importazione','A10',null,'DATA_ELDA','Data importazione');
			Update C0CAMPI set COC_DES='Numero progressivo lotto', COC_DES_WEB='Numero progressivo lotto',COC_DES_FRM='N. progr.' where COC_MNE_UNI='NLOTTO.W9LOTT.W9';
			Update C0CAMPI set COC_DES='CUP ancora da acquisire o esente', COC_DES_WEB='CUP ancora da acquisire o esente',COC_DES_FRM='Da acquisire/esente' where COC_MNE_UNI='CUPESENTE.W9LOTT.W9';
			
			Update W9CF_PUBB set NOME='Delibera/determina  a contrarre o atto equivalente' where ID=1;
			Update W9CF_PUBB set CL_WHERE_VIS='(L.ID_SCELTA_CONTRAENTE_50 IN (1,5,7,12,13,14,15,16,20,21,23) or L.ID_SCELTA_CONTRAENTE in (1,2,6,8,20,27,28,29,30,34) OR PREV_BANDO=''1'')' where ID=3;
			Update W9CF_PUBB set CL_WHERE_VIS='(L.ID_SCELTA_CONTRAENTE_50 in (10,11) or L.ID_SCELTA_CONTRAENTE in (18,19))',tipo='E' where id=14;

			--- W_CONFIG W_QUARTZ
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','it.eldasoft.wspubblicazioni.noAggiornaGaraSeEsiste','0','Servizio di pubblicazione atti','Non aggiornare i dati di gara e lotti quando esistenti. Valori ammessi: 1 = abilitata, 0 [default] = disabilitata',null);
			Insert into W_CONFIG (CODAPP,CHIAVE,VALORE,SEZIONE,DESCRIZIONE,CRIPTATO) values ('W9','it.eldasoft.wspubblicazioni.bloccaCigSeEsiste','0','Servizio di pubblicazione atti','Bloccare l''inserimento di gara e lotti se il CIG esiste giÃ . Valori ammessi: 1 = abilitata, 0 [default] = disabilitata',null);

			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','W9.W9INIZ.INCONTRI_VIGIL','Visualizza',0,1,1,3845712266);
			Insert into W_OGGETTI (TIPO,OGGETTO,DESCR,ORD) values ('FUNZ','ALT.W9.REPORTINDICATORI','Report indicatori',13690);
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('FUNZ','VIS','ALT.W9.REPORTINDICATORI','Visualizza',0,1,1,1650729717);
			
			Update C0CAMPI set COC_CONTA=3 where COC_MNE_UNI = 'NUM_APPA.W9APPA.W9';
			
			Update ELDAVER set NUMVER='200' where CODAPP='W9_UPDATE';
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='W9_UPDATE' and NUMVER='200' ) = 1
		THEN
			UPDATE ELDAVER set NUMVER='5.2.0', DATVET=CURRENT_TIMESTAMP where CODAPP='W9';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='W9_UPDATE';
		END IF;

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 5.1.0 --> 5.1.1
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '5.1.0' or numver like '5.1.0.%')) = 1
	THEN

		-----------------------------------------------------------------------------------------------------
		----------------------------    CREAZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
		-----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' ) = 0
		THEN
			Insert into ELDAVER (CODAPP,NUMVER,DATVET,MSG) values ('PT_UPDATE','1',CURRENT_TIMESTAMP,'Progressivo di aggiornamento (se presente significa che l''esecuzione di uno script di aggiornamento e'' terminato con errore)');
		END IF;

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		Update ELDAVER set NUMVER='100' where CODAPP='PT_UPDATE';
	
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------

		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='100' ) = 1
		THEN
			Update TAB_CONTROLLI set condizione='T.DISCONTINUITA_RETE is not null or T.INFRASTRUTTURA = ''2''' where codapp='PT' and entita='OITRI' and titolo='DISCONTINUITA_RETE';
			Update TAB_CONTROLLI set condizione='T.CODAUSA is not null or T.DELEGA=''2'' or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)' where codapp='PT' and entita='INTTRI' and titolo='CODAUSA';
			Update TAB_CONTROLLI set condizione='T.SOGAGG is not null or T.DELEGA=''2'' or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)' where codapp='PT' and entita='INTTRI' and titolo='SOGAGG';
			
			Update ELDAVER set NUMVER='101' where CODAPP='PT_UPDATE';
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='101' ) = 1
		THEN
			-- ELDAVER
			Update ELDAVER set NUMVER='5.1.1', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='PT_UPDATE';
		END IF;

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 5.1.1 --> 5.1.2
------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '5.1.1' or numver like '5.1.1.%')) = 1
	THEN

		-----------------------------------------------------------------------------------------------------
		----------------------------    CREAZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
		-----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' ) = 0
		THEN
			Insert into ELDAVER (CODAPP,NUMVER,DATVET,MSG) values ('PT_UPDATE','1',CURRENT_TIMESTAMP,'Progressivo di aggiornamento (se presente significa che l''esecuzione di uno script di aggiornamento e'' terminato con errore)');
		END IF;

		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='1' ) = 1
		THEN
			SET v_sql = 'ALTER TABLE FABBISOGNI ADD COLUMN QE_SL VARCHAR(1)';
			EXECUTE immediate v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE FABBISOGNI');
			
			SET v_sql = 'Update ELDAVER set NUMVER=''2'' where CODAPP=''PT_UPDATE''';
			EXECUTE immediate v_sql;
		END IF;
	
		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------

		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='2' ) = 1
		THEN
			--- C0CAMPI
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) Values (0,'E',null,'QE_SL.FABBISOGNI.PIANI','T2QE_SL','Quadro delle risorse in sola lettura','Risorse sola lettura','A2',null,'SN',null);
			Update C0CAMPI set COC_DES='CUP ancora da acquisire o esente', COC_DES_WEB='CUP ancora da acquisire o esente',COC_DES_FRM='Da acquisire/esente' where COC_MNE_UNI='FLAG_CUP.INTTRI.PIANI';
			--- TAB_CONTROLLI
			
			--- WS_CONTROLLI
			
			--- W_OGGETTI
			UPDATE W_OGGETTI SET OGGETTO='ALT.PIANI.RDS_CONTRASSEGNACOMPLETATO' WHERE OGGETTO='ALT.W9.RDS_CONTRASSEGNACOMPLETATO';
			UPDATE W_OGGETTI SET OGGETTO='ALT.PIANI.RDS_SOTTOPONIADAPPRFINANZ' WHERE OGGETTO='ALT.W9.RDS_SOTTOPONIADAPPRFINANZ';
			UPDATE W_OGGETTI SET OGGETTO='ALT.PIANI.RDS_INOLTRAALRDP' WHERE OGGETTO='ALT.W9.RDS_INOLTRAALRDP';
			UPDATE W_OGGETTI SET OGGETTO='ALT.PIANI.RDS_ANNULLA' WHERE OGGETTO='ALT.W9.RDS_ANNULLA';
			UPDATE W_OGGETTI SET OGGETTO='ALT.PIANI.RAF_APPROVARESPINGI' WHERE OGGETTO='ALT.W9.RAF_APPROVARESPINGI';
			UPDATE W_OGGETTI SET OGGETTO='ALT.PIANI.RDP_RICHIEDIREVISIONERESPINGI' WHERE OGGETTO='ALT.W9.RDP_RICHIEDIREVISIONERESPINGI';
			UPDATE W_OGGETTI SET OGGETTO='ALT.PIANI.RDP_INSERISCIINPROGRAMMAZIONE' WHERE OGGETTO='ALT.W9.RDP_INSERISCIINPROGRAMMAZIONE';
			UPDATE W_OGGETTI SET OGGETTO='ALT.PIANI.RDP_SEGNALAPROCEDURAAVVIATA' WHERE OGGETTO='ALT.W9.RDP_SEGNALAPROCEDURAAVVIATA';

			Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) Values ('FUNZ', 'ALT.PIANI.RDS_APPROVARICHIEDIREVISIONE', 'RDS Approva/Richiedi revisione', 13670);
			
			--- W_AZIONI
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('FUNZ','VIS','ALT.PIANI.RDS_INOLTRAALRDP','Visualizza',0,1,1,3770793108);
			
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','PIANI.RIS_CAPITOLO.RG1CB','Visualizza',0,1,1,3783667861);
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','PIANI.RIS_CAPITOLO.IMPRFSCB','Visualizza',0,1,1,2376581944);
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','PIANI.RIS_CAPITOLO.IMPALTCB','Visualizza',0,1,1,3337001478);
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('COLS','VIS','PIANI.RIS_CAPITOLO.VERIFBIL','Visualizza',0,1,1,3468429462);
			--- W_CONFIG
			
			--- TABELLATI
			Update TAB1 set TAB1ARC='1' WHERE TAB1COD='PT023' and TAB1TIP = 12;
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT023',15,'Approvato',null,null,null);
			UPDATE FABBISOGNI SET STATO = 11 WHERE STATO=12;
			
			Update ELDAVER set NUMVER='3' where CODAPP='PT_UPDATE';
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='3' ) = 1
		THEN
			-- ELDAVER
			Update ELDAVER set NUMVER='5.1.2', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='PT_UPDATE';
		END IF;

	END IF;
end@

call aggiorna@
drop procedure aggiorna@

--------------------------------
-- AGGIORNAMENTO PROGRAMMAZIONE
-- Database: DB2
-- Versione: 5.1.2 --> 5.2.0
--------------------------------

------ CARATTERE TERMINATORE ----------
--#SET TERMINATOR @

create procedure aggiorna ()
begin

	Declare v_sql varchar(5000);
	Declare v_max varchar(10);

	IF ( select count(*) from eldaver where codapp = 'PT' and (numver = '5.1.2' or numver like '5.1.2.%')) = 1
	THEN
		
		-----------------------------------------------------------------------------------------------------
		----------------------------    CREAZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
		-----------------------------------------------------------------------------------------------------
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' ) = 0
		THEN
			Insert into ELDAVER (CODAPP,NUMVER,DATVET,MSG) values ('PT_UPDATE','1',CURRENT_TIMESTAMP,'Progressivo di aggiornamento (se presente significa che l''esecuzione di uno script di aggiornamento e'' terminato con errore)');
		END IF;
		
		----------------------------------------------------------------------------------------------------
		----------------------------------------    MODIFICA STRUTTURA   -----------------------------------
		----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='1' ) = 1
		THEN
		
			SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN SOGGREF NUMERIC(3)';
			EXECUTE IMMEDIATE v_sql;
			SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN DIREZIONE NUMERIC(3)';
			EXECUTE IMMEDIATE v_sql;
			SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN OPERESCOMP VARCHAR(1)';
			EXECUTE IMMEDIATE v_sql;
			SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN CENTRICOSTO NUMERIC(3)';
			EXECUTE IMMEDIATE v_sql;
			SET v_sql = 'ALTER TABLE INTTRI ADD COLUMN AREA NUMERIC(3)';
			EXECUTE IMMEDIATE v_sql;
			CALL SYSPROC.ADMIN_CMD ('REORG TABLE INTTRI');			
			
			SET v_sql = 'CREATE TABLE IMPORTIINTERVENTO (
				CONTRI NUMERIC(10) NOT NULL,
				CONINT NUMERIC(5) NOT NULL,
				TIPIMP NUMERIC(5) NOT NULL,
				ANNO   NUMERIC(4) NOT NULL,
				IMPORTO NUMERIC(24,5),
				PRIMARY KEY (CONTRI, CONINT, TIPIMP, ANNO)
			)';
			EXECUTE IMMEDIATE v_sql;

			Update ELDAVER set NUMVER='2' where CODAPP='PT_UPDATE';
			
		END IF;

		-----------------------------------------------------------------------------------------------------
		-------------------------------------------    MODIFICA DATI    -------------------------------------
		-----------------------------------------------------------------------------------------------------
		
		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='2' ) = 1 THEN
			
			--- C0CAMPI
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'SOGGREF.INTTRI.PIANI','SOGGREF','Soggetto referente','Soggetto referente','A100','PT996',null,'Soggetto referente');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'DIREZIONE.INTTRI.PIANI','DIREZINTTRI','Direzione','Direzione','A100','PT997',null,'Direzione');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'OPERESCOMP.INTTRI.PIANI','OPESCINTTRI','Opere a scomputo','Opere scomputo','A2',null,'SN','Opere a scomputo');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'CENTRICOSTO.INTTRI.PIANI','CECOINTTRI','Centro di costo','Centro di costo','A100','PT998',null,'Centro di costo');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (0,'E',null,'AREA.INTTRI.PIANI','AREAINTTRI','Area geografica','Area geografica','A100','PT999',null,'Area geografica');

			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (1,'E','P','CONTRI.IMPORTIINTERVENTO.PIANI','IICONTRI1','Numero progressivo del programma','N.progr.piano trien.','N10',null,null,'Numero progressivo del programma');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (2,'E','P','CONINT.IMPORTIINTERVENTO.PIANI','IICONINT','Numero progressivo dell''intervento','N.progr.intervento','N5',null,null,'Numero progressivo dell''intervento');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (3,'E','P','TIPIMP.IMPORTIINTERVENTO.PIANI','IITIPIMP','Tipologia importo','Tipologia importo','A100','PT027',null,'Tipologia importo');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (4,'E','P','ANNO.IMPORTIINTERVENTO.PIANI','IIANNO','Anno dell''intervento','Anno.intervento','N5',null,null,'Anno progressivo dell''intervento');
			Insert into C0CAMPI (COC_CONTA,C0C_TIP,C0C_CHI,COC_MNE_UNI,C0C_MNE_BER,COC_DES,COC_DES_FRM,C0C_FS,C0C_TAB1,COC_DOM,COC_DES_WEB) values (5,'E',null,'IMPORTO.IMPORTIINTERVENTO.PIANI','IIIMPORT','Importo','Importo','F24.5',null,'MONEY','Importo');
			
			Update C0CAMPI set COC_DES='Concessi in diritto di godimento art. 21-c5',COC_DES_FRM='Dir.godimento art21',COC_DES_WEB='Concessi in diritto di godimento art. 21-c5' where COC_MNE_UNI='IMMDISP.IMMTRAI.PIANI';

			--- C0ENTIT
			Insert into C0ENTIT (C0E_NOM,C0E_NUM,C0E_TIP,COE_ARG,C0E_DES,C0E_KEY,C0E_NOM_EX,COE_KEY_EX,C0E_FRM_RI,COE_FRM_CA,COE_FRM_RE) values ('IMPORTIINTERVENTO.PIANI',0,'E','IMP_INTERV','Importi interventi','CONTRI.IMPORTIINTERVENTO.PIANI;CONINT.IMPORTIINTERVENTO.PIANI;TIPINT.IMPORTIINTERVENTO.PIANI;ANNO.IMPORTIINTERVENTO.PIANI','INTTRI.PIANI','CONTRI.INTTRI.PIANI,CONINT.INTTRI.PIANI',null,null,'pt_impin');
			
			--- TAB_CONTROLLI
			Update TAB_CONTROLLI set condizione='T.LOTFUNZ is not null' where codapp='PT' and codfunzione in ('INOLTRO_RAF','INOLTRO_RDP') and num=240;
			Delete from TAB_CONTROLLI where codapp='PT' and codfunzione in ('INOLTRO_RAF','INOLTRO_RDP') and num in (160,170);
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',160,'INTTRI','Dati generali','INTTRI.ACQALTINT','(T.ACQALTINT is not null or T.TIPINT=1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RAF',170,'INTTRI','Dati generali','INTTRI.CUICOLL','(T.CUICOLL is not null or coalesce(T.ACQALTINT,1)<>2) or T.TIPINT=3','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',160,'INTTRI','Dati generali','INTTRI.ACQALTINT','(T.ACQALTINT is not null or T.TIPINT=1) or exists (select 1 from piatri where piatri.contri=T.contri and tiprog=3)','Valorizzare il campo','E');
			Insert into TAB_CONTROLLI (codapp,codfunzione,num,entita,sezione,titolo,condizione,msg,tipo) values ('PT','INOLTRO_RDP',170,'INTTRI','Dati generali','INTTRI.CUICOLL','(T.CUICOLL is not null or coalesce(T.ACQALTINT,1)<>2) or T.TIPINT=3','Valorizzare il campo','E');
			--- WS_CONTROLLI

			--- TABELLATI
			--- TAB1
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',1, 'Destinazione vincolata per legge',null,null,null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',2, 'Contrazione di mutuo',null,null,null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',3, 'Apporti di capitale privato',null,null,null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',4, 'Stanziamenti di bilancio',null,null,null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',5, 'Finanziamenti art. 3 DL 310/1990',null,null,null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',6, 'Trasferimento immobili',null,null,null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',7, 'Importi della società in house',null,null,null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',8, 'Importi da altri soggetti',null,null,null);
			Insert into TAB1 (TAB1COD,TAB1TIP,TAB1DESC,TAB1MOD,TAB1NORD,TAB1ARC) values ('PT027',99,'Altro',null,null,null);

			--- W_OGGETTI
			Insert into W_OGGETTI (TIPO, OGGETTO, DESCR, ORD) values ('FUNZ', 'ALT.PIANI.INTTRI_CAMPI_COMUNE_PISA', 'Personalizzazioni Comune di Pisa', 13680);

			--- W_AZIONI
			Insert into W_AZIONI (TIPO,AZIONE,OGGETTO,DESCR,VALORE,INOR,VISELENCO,CRC) values ('FUNZ','VIS','ALT.PIANI.INTTRI_CAMPI_COMUNE_PISA','Visualizza',0,1,1,4215668971);

			--- W_CONFIG
			Insert into W_CONFIG (CODAPP, CHIAVE, VALORE, SEZIONE, DESCRIZIONE, CRIPTATO) Values ('W9', 'it.eldasoft.integrazione.LFS.url', null, 'Programmi triennali/biennali', 'URL servizio LFS/DEC per integrazione interventi/acquisti', NULL);

			Update ELDAVER set NUMVER='3' where CODAPP='PT_UPDATE';
		END IF;

		IF ( select count(*) from ELDAVER where CODAPP='PT_UPDATE' and NUMVER='3' ) = 1 THEN
			--- ELDAVER
			Update ELDAVER set NUMVER='5.2.0', DATVET=CURRENT_TIMESTAMP where CODAPP='PT';

			-----------------------------------------------------------------------------------------------------
			----------------------------    RIMOZIONE PROGRESSIVO DI AGGIORNAMENTO   ----------------------------
			-----------------------------------------------------------------------------------------------------
			DELETE from ELDAVER where CODAPP='PT_UPDATE';
		END IF;
		
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
	SET S_USER=S_USER || ',SREST_RW';	--------		Utente per i servizi rest  -------
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


