-- INSERT ROLES  الادوار
insert into roles (role) values ("ROLE_GUEST");
insert into roles (role) values ("ROLE_LESSOR");

-- insert cities    المدن
insert into city (arabic_name, city) values ("طرابلس","Tripoli");
insert into city (arabic_name, city) values ("بنغازي","Benghazi");
insert into city (arabic_name, city) values ("مصراتة","Misrata");

-- insert Regions  المناطق
insert into region (arabic_name, region, city_id) values ("حي الأندلس","Al-Andalus neighborhood", 1);
insert into region (arabic_name, region, city_id) values ("الحي الإسلامي","Islamic Quarter", 1);
insert into region (arabic_name, region, city_id) values ("البركة","ُEl baraka", 2);
insert into region (arabic_name, region, city_id) values ("سيدي حسين","Sidi Hussein", 2);
insert into region (arabic_name, region, city_id) values ("الرويسات","ُRuwaisat", 3);
insert into region (arabic_name, region, city_id) values ("الرملة","Ramla", 3);

-- insert unit_type نوع الوحدة
insert into unit_type (type_arabic_name, type_name) values ("اقامات","RESIDENCIES");
insert into unit_type (type_arabic_name, type_name) values ("قاعات مناسبات","EVENT_HALLS");

-- insert accommodation_type   نوع الاقامة
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("فندق","HOTEL");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("شقة فندقية","HOTEL_APARTMENT");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("شالية","CHALET");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("منتجع","RESORT");
insert into accommodation_type  (accommodation_arabic_name, accommodation_name) values ("استراحة","LOUNGE");

-- insert hotel_classification   تصنيف الفندق
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("نجمة واحدة","ONE_STAR");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("نجمتان","TWO_STARS");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("3 نجوم","THREE_STARS");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("4 نجوم","FOUR_STARS");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("5 نجوم","FIVE_STARS");
insert into hotel_classification  (hotel_classification_arabic_name, hotel_classification_name) values ("غير مصنف","UNCLASSIFIED");


-- insert room_available الغرف المتاحة 

insert into room_available (room_available_name_arabic, room_available_name) values ("غرفة فردية","SINGLE_ROOM");
insert into room_available (room_available_name_arabic, room_available_name) values ("غرفة زوجية بسرير واحد","DOUBLE_ROOM_ONE_BED");
insert into room_available (room_available_name_arabic, room_available_name) values ("غرفة زوجية بسريرين","DOUBLE_ROOM_TWO_BED");
insert into room_available (room_available_name_arabic, room_available_name) values ("جناح","SUITE");

-- insert feature المميزات 
insert into feature (feature_arabic_name, feature_name) values ("حمام سباحة","SWIMMING_POOL");
insert into feature (feature_arabic_name, feature_name) values ("اطلالة علي البحر","SEA_VIEW");
insert into feature (feature_arabic_name, feature_name) values ("موقف سيارات","PARKING");

-- insert sub_feature مميزات فرعية 
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("واي فاي","Wi-Fi");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("تكيف","air conditioning");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("غسالة صحون","dishwasher");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("مطبخ","kitchen");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("غسالة ملابس","washing machine");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("تدفئة","heating");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("شاشة","screen");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("مصعد","elevator");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("توصيل واستقبال من المطار","airport pick-up and drop-off");
insert into sub_feature (sub_feature_arabic_name, sub_feature_name) values ("مكان مناسب لذوي الاحتياجات الخاصة","suitable place for people with special needs");

-- insert food_option امكانيات الطعام المتاحة 
insert into food_option (food_option_arabic_name, food_option_name) values ("افطار","Breakfast");
insert into food_option (food_option_arabic_name, food_option_name) values ("جميع الوجبات","all meals");
insert into food_option (food_option_arabic_name, food_option_name) values ("افطار+غذاء","breakfast + lunch");
insert into food_option (food_option_arabic_name, food_option_name) values ("افطار+عشاء","breakfast + dinner");
insert into food_option (food_option_arabic_name, food_option_name) values ("اعداد الوجبات ذاتيا","self-preparation of meals");

-- insert status_unit   	حالة الوحدة
insert into status_unit (status_arabic_name, status_name) values ("انتظار","PENDING");
insert into status_unit (status_arabic_name, status_name) values ("المقبولة","ACCEPTED");
insert into status_unit (status_arabic_name, status_name) values ("المرفوضة","REJECTED");
insert into status_unit (status_arabic_name, status_name) values ("الملغاة","CANCELED");