DROP SCHEMA IF EXISTS cinema;
CREATE SCHEMA IF NOT EXISTS cinema DEFAULT CHARACTER SET utf8 ;
USE cinema;

create table film
(
    id       int auto_increment
        primary key,
    img      varchar(100) null,
    duration int not null
);

create table hall
(
    id              int auto_increment
        primary key,
    number_of_rows  int null,
    number_of_seats int null
);

create table language
(
    id         int auto_increment
        primary key,
    locale     varchar(10) not null,
    short_name varchar(45) not null,
    full_name  varchar(45) not null
);

create table film_description
(
    id          int auto_increment
        primary key,
    film_id     int           not null,
    language_id int           not null,
    name        varchar(100)  not null,
    description varchar(2000) not null,
    constraint film_description_ibfk_1
        foreign key (film_id) references film (id)
            on delete cascade,
    constraint film_description_ibfk_2
        foreign key (language_id) references language (id)
            on delete cascade
);

create index film_id
    on film_description (film_id);

create index language_id
    on film_description (language_id);

create table seance
(
    id         int auto_increment
        primary key,
    film_id    int null,
    date       datetime not null,
    hall_id    int null,
    price      int      not null,
    free_seats int      not null,
    constraint film_id
        foreign key (film_id) references film (id)
            on delete cascade,
    constraint hall_id
        foreign key (hall_id) references hall (id)
            on delete cascade
);

create table user
(
    id       int auto_increment
        primary key,
    name     varchar(50) null,
    email    varchar(100) not null,
    password varchar(100) not null,
    role     enum ('admin', 'user') default 'user' null,
    date     datetime     not null,
    constraint email
        unique (email)
);

create table `order`
(
    id        int auto_increment
        primary key,
    user_id   int      not null,
    seance_id int      not null,
    price     int null,
    date      datetime not null,
    constraint seance_id
        foreign key (seance_id) references seance (id)
            on delete cascade,
    constraint user_id
        foreign key (user_id) references user (id)
);

create table order_item
(
    id           int auto_increment,
    order_id     int not null,
    seat_number  int not null,
    `row_number` int not null,
    constraint order_item_id_uindex
        unique (id),
    constraint order_id
        foreign key (order_id) references `order` (id)
            on delete cascade
);

alter table order_item
    add primary key (id);

create table password_recovery
(
    user_id         int          not null,
    expiration_date datetime     not null,
    token           varchar(100) not null,
    id              int auto_increment
        primary key,
    constraint password_recovery_token_uindex
        unique (token),
    constraint password_recovery_user_id_fk
        foreign key (user_id) references user (id)
);

-- INSERT films
INSERT INTO cinema.film (id, img, duration) VALUES (2, 'https://i.ibb.co/DKs8qPT/x4-poster-60efe9956ab68.jpg', 100);
INSERT INTO cinema.film (id, img, duration) VALUES (3, 'https://i.ibb.co/y0XRh4K/vend2.jpg', 70);
INSERT INTO cinema.film (id, img, duration) VALUES (4, 'https://i.ibb.co/HYVBv9T/vend1.jpg', 85);
INSERT INTO cinema.film (id, img, duration) VALUES (6, 'https://i.ibb.co/9sW847w/GOPR1704.jpg', 120);
INSERT INTO cinema.film (id, img, duration) VALUES (7, 'https://i.ibb.co/Syp9qNf/bad-housewives.jpg', 140);
INSERT INTO cinema.film (id, img, duration) VALUES (8, 'https://i.ibb.co/zmP01rR/vend-laput.jpg', 55);
INSERT INTO cinema.film (id, img, duration) VALUES (9, 'https://i.ibb.co/vXq0tCM/trapped.jpg', 80);
INSERT INTO cinema.film (id, img, duration) VALUES (66, 'https://i.ibb.co/dDRnHrv/vend1-reminis.jpg', 90);
INSERT INTO cinema.film (id, img, duration) VALUES (68, 'https://i.ibb.co/qpznyTm/fall.jpg', 110);
INSERT INTO cinema.film (id, img, duration) VALUES (70, 'https://i.ibb.co/XbW0p5G/vend.jpg', 130);
INSERT INTO cinema.film (id, img, duration) VALUES (100, 'https://i.ibb.co/vQRTSh5/vend-shanshi.jpg', 135);
INSERT INTO cinema.film (id, img, duration) VALUES (103, 'https://i.ibb.co/hV5Np7z/vend-duna.jpg', 180);
INSERT INTO cinema.film (id, img, duration) VALUES (104, 'https://i.ibb.co/F4Ws3Qg/vend-litl.jpg', 80);
INSERT INTO cinema.film (id, img, duration) VALUES (105, 'https://i.ibb.co/Jt7gfwL/vend1.jpg', 110);
INSERT INTO cinema.film (id, img, duration) VALUES (106, 'https://i.ibb.co/PsLFmT0/vend1-1.jpg', 11);
INSERT INTO cinema.film (id, img, duration) VALUES (107, 'https://i.ibb.co/q96GjX1/vend.jpg', 90);
INSERT INTO cinema.film (id, img, duration) VALUES (108, 'https://i.ibb.co/FwHc0tF/vend3-1.jpg', 80);
INSERT INTO cinema.film (id, img, duration) VALUES (109, 'https://i.ibb.co/714CQcP/vend1-2.jpg', 70);
INSERT INTO cinema.film (id, img, duration) VALUES (110, 'https://i.ibb.co/pXtDtbH/vend3.jpg', 95);

-- INSERT hall
INSERT INTO cinema.hall (id, number_of_rows, number_of_seats) VALUES (1, 10, 12);

-- INSERT language
INSERT INTO cinema.language (id, locale, short_name, full_name) VALUES (1, 'en_US', 'EN', 'English');
INSERT INTO cinema.language (id, locale, short_name, full_name) VALUES (2, 'uk_UA', 'UA', 'Ukrainian');

-- INSERT film_description
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (1, 100, 2, 'Шан-Чі та легенда десяти кілець (12+)', '«Шан-Чі та легенда десяти кілець» – новий фільм від Marvel Studios з Сіму Лью у головній ролі. Минуле, яке, здавалось, залишилось позаду, знову постає перед Шан-Чі, коли його втягують у мережу містичної організації під назвою «Десять Кілець». У фільмі також знялись Тоні Люн як Венву, Аквафіна у ролі подруги Шан-Чі, Кеті, та Мішель Єо у ролі Цзян Нан. А також Фала Чен, Менґ Чжан, Флоріан Мунтяну та Ронні Чієнґ.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (2, 100, 1, 'Shang Chi and the legend of the ten rings (12+)', '"Shang Chi and the Legend of the Ten Rings" is a new film from Marvel Studios starring Simu Lew. The past, which seemed to be left behind, reappears before Shang Chi when he is drawn into a network of mystical organizations called the Ten Rings. The film also stars Tony Leung as Wenwu, Aquafina as Shang Chi''s girlfriend, Katie, and Michelle Yeo as Jiang Nan. And also Fala Chen, Meng Zhang, Florian Muntianu and Ronnie Chieng.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (3, 2, 2, 'Персонаж (12+)', 'Хлопчина на ім’я Гай (Раян Рейнольдс) працює касиром у великому банку. У його налагодженому житті не буває несподіванок. Одного дня його банк грабують 17 разів поспіль. Тоді Гай розуміє, що все, що його оточує – це майстерно спланована кимось відеогра, у якій він другорядний персонаж. Віртуальний світ, у якому він живе, дає можливість гравцю робити що йому заманеться. Попри такі «вигідні» умови перебування у цьому світі, Гай вирішує вирватися із гри. А заодно звільнити й увесь світ від перебування у примарній реальності.

Фантастичний комедійний бойовик «Персонаж» зняв режисер Шон Леві («Кадри», «Ніч у музеї») за сценарієм Метта Лібермана та Зака Пенна («Месники», «Першому гравцю приготуватися»). За словами виконавця головної ролі Раяна Рейнольдса, жоден проект не захоплював його так сильно з часів роботи над «Дедпулом». Актор вважає, що це новий погляд на супергероя: цього разу він не у плащі та колготках.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (4, 2, 1, 'Character (12+)', 'A boy named Guy (Ryan Reynolds) works as a cashier at a large bank. There are no surprises in his established life. One day his bank was robbed 17 times in a row. Then Guy realizes that all that surrounds him is a skillfully planned video game in which he is a minor character. The virtual world in which he lives allows the player to do whatever he wants. Despite such "favorable" conditions in this world, Guy decides to break out of the game. And at the same time to free the whole world from being in a phantom reality.

The fantastic comedy action movie "Character" was directed by Sean Levy ("Footage", "Night at the Museum") written by Matt Lieberman and Zach Penn ("Avengers", "The First Player to Prepare"). According to lead actor Ryan Reynolds, no project has fascinated him so much since working on Deadpool. The actor believes that this is a new look at the superhero: this time he is not in a cloak and tights.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (5, 66, 2, 'Ремінісценція (16+)', 'Події розгортаються у майбутньому. Через незворотні зміни клімату місто Майамі поступово йде під воду. Ветеран-одинак Ніколас Банністер (Г''ю Джекмен) є експертом у затребуваній царині. Він допомагає своїм клієнтам поринути у спогади. Когось це просто тішить, комусь допомагає встановити важливі, проте забуті факти. Якось до нього звертається Мей (Ребекка Фергюсон), яка просто забула де поклала ключі. Між героями спалахує пристрасть. Проте спогади жінки несуть у собі небезпеку. На поверхню виринають подробиці насильницьких злочинів. Аби зрозуміти в кого ж він закохався, Банністер також занурюється у світ минулого.

Фантастична мелодрама «Ремінісценція» є дебютною роботою режисерки та сценаристки Лізи Джой. У березні 2019 року стало відомо, що права на розповсюдження стрічки купила компанія Warner Bros. Основний знімальний процес розпочався у жовтні того ж року та проходив у Новому Орлеані та Майамі.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (6, 70, 2, 'Щенячий патруль (0+)', 'На диво розумний та кмітливий 10-річний хлопчик Райдер є керівником команди цуценят. Вони являюсь собою Щенячий патруль та залюбки роблять усе, аби у їхньому рідному місті було спокійно та затишно. Цуценята беруть на себе обов’язки пожежників, рятувальників чи поліції – дивлячись що необхідно саме зараз. Коли їхній затятий ворог мер Хамдінгер очолює сусіднє місто пригод, Райдер та цуценята поспішають туди, аби вберегти місцевих жителів від нерозумних дій нового керівника. Пліч-о-пліч з ними стане їхня нова подруга такса Ліберті.

Пригодницький сімейний мультфільм «Щенячий патруль у кіно» створено за мотивами однойменного дитячого мультсеріалу, знятого у Канаді. Перший епізод вийшов на екрани у серпні 2013 року. Відтоді з’явилось 8 сезонів по 26 серій кожен. Мультфільм транслюється у 160 країнах світу. Окрім безпосередньо мультсеріалу, автори проекту випустили лінійку іграшок для дошкільнят та відеоігри. Плани створити повнометражний мультфільм з’явилися у 2017 році.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (7, 4, 2, 'Гуллівер повертається (0+)', '                                                                                Гуллівер отримує листа, у якому його просять про допомогу жителі Ліліпутії. Край у страшному занепаді, тож без доброго та мудрого велетня їм не обійтися. Трон посів дивовижний бовдур і кожен відчуває на собі тяжкі наслідки його недоумкуватості. Але чи буде Гуллівер самовіддано вирішувати справи своїх друзів, коли в самого не все гаразд?

Анімаційний пригодницький фільм «Гуллівер повертається» створено студією «Квартал 95». За словами творчої команди, мультфільм буде цікавим як дітям через карколомні пригоди та гумор героїв, так і батькам завдяки актуальному соціальному і навіть політичному підтексту. Перший тизер було випущено ще в 2015 році. Копітка робота по створенню анімації велася не лише українцями, а й фахівцями з закордону. Працювали паралельно, регулярно спілкуючись он-лайн. Аби проект став успішним неабияких зусиль доклав Тоні Бонілла з США. За його словами, найбільше враження на нього справив саундтрек до українського мультфільму.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (8, 3, 2, 'Загін самогубців: Місія навиліт (18+)', 'Ласкаво просимо до пекла або Бель Рів, в''язниці із найвищим рівнем смертності в США. Місце, де утримуються найнебезпечніші лиходії світу. Злочинці згодні на все, аби вибратися, навіть приєднатись до суперсекретної оперативної групи Загін «Ікс»! Яке ж смертельно-небезпечне завдання заплановано на сьогодні? Збираємо разом Бладспорта, Миротворця, Капітана Бумеранга, Щуроловку 2, Саванта, Кінґ Шарка, Блекґарда, Джавеліна та улюбленицю публіки, неврівноважену Харлі Квін. Видаємо зброю і скидуємо (у прямому сенсі слова) на віддалений ворожий острів Корто Мальтезе. Просуваючись джунглями, повними бойовиків та партизанів, Загін прямує до місця виконання свого завдання. Разом з ними – лише полковник Рік Флеґ, який один може змусити поганців гарно поводитись та…урядова команда «технарів» Аманди Воллер, яка відстежує кожен їхній крок. Ну і як завжди, один неправильний хід – і неминуча смерть (від рук ворога, товариша по команді чи самої Воллер). Якщо хтось хотів би зробити ставку у цій грі, то найрозумніший хід – ставити проти всіх них.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (9, 66, 1, 'Reminiscence (16+)', 'Events unfold in the future. Due to irreversible climate change, the city of Miami is gradually falling under water. Veteran single Nicholas Bannister (Hugh Jackman) is an expert in the field. He helps his clients to immerse themselves in memories. For some it is just happy, for others it helps to establish important but forgotten facts. One day he is approached by May (Rebecca Ferguson), who simply forgot where she put the keys. Passion flares up between the characters. However, a woman''s memories are dangerous. Details of violent crimes are coming to the surface. To understand who he fell in love with, Bannister also plunges into the world of the past.

The fantastic melodrama "Reminiscence" is the debut work of director and screenwriter Lisa Joy. In March 2019, it became known that the rights to distribute the tape were purchased by Warner Bros. The main filming process began in October of that year and took place in New Orleans and Miami.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (10, 70, 1, 'Paw Patrol (0+)', 'Surprisingly smart and clever 10-year-old boy Ryder is the leader of the team of puppies. They are the Puppy Patrol and love to do everything to keep their hometown calm and cozy. Puppies take on the responsibilities of firefighters, rescuers or the police - depending on what is needed right now. When their staunch enemy, Mayor Hamdinger, heads a nearby adventure city, Ryder and the puppies rush there to protect the locals from the new leader''s foolish actions. Side by side with them will be their new friend Dachshund Liberty.

The adventure family cartoon "Puppy Patrol in the movies" is based on the children''s cartoon series of the same name, shot in Canada. The first episode was released in August 2013. Since then, there have been 8 seasons of 26 episodes each. The cartoon is broadcast in 160 countries. In addition to the cartoon series itself, the authors of the project have released a line of toys for preschoolers and video games. Plans to create a full-length cartoon appeared in 2017.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (11, 4, 1, 'Gulliver returns (0+)', '                                                                                Gulliver receives a letter asking him for help from the people of Lilliput. The land is in terrible decline, so they can''t do without a good and wise giant. The throne is occupied by an amazing buffoon and everyone feels the severe consequences of his stupidity. But will Gulliver be selfless in dealing with his friends when things aren''t going his way?

The animated adventure film "Gulliver Returns" was created by the studio "Quarter 95". According to the creative team, the cartoon will be interesting both for children because of the shocking adventures and humor of the characters, and for parents due to the current social and even political connotations. The first teaser was released in 2015. Hard work on the creation of animation was carried out not only by Ukrainians but also by specialists from abroad. Worked in parallel, communicating regularly online. Tony Bonilla from the USA made great efforts to make the project successful. According to him, he was most impressed by the soundtrack to the Ukrainian cartoon.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (12, 3, 1, 'Suicide Squad: Navilit Mission (18+)', 'Welcome to Hell or Belle Reve, the prison with the highest mortality rate in the United States. A place where the most dangerous villains of the world are kept. Criminals agree to do anything to get out, even to join the super-secret task force X-Squad! What deadly task is planned for today? We bring together Bloodsport, Peacemaker, Captain Boomerang, Rat Trap 2, Savant, King Shark, Blackgard, Javelin and the public favorite, the unbalanced Harley Quinn. We surrender our weapons and dump (literally) on the remote enemy island of Corto Maltese. Advancing through the jungle full of militants and guerrillas, the Squad goes to the place of its task. With them is only Colonel Rick Flagg, who alone can make the pagans behave well, and Amanda Waller''s government team of "techies," which monitors their every move. Well, as always, one wrong move - and the inevitable death (at the hands of the enemy, a teammate or Waller himself). If someone would like to bet in this game, the smartest move is to bet against all of them.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (13, 68, 2, 'Після падіння (16+)', 'У Сіетлі на Тессу (Джозефіна Ленгфорд) чекає робота, про яку вона давно мріяла. Дівчина не вагаючись переїхала б туди, якби не Хардін (Хіро Файнс-Тіффін), який про це місто навіть чути нічого не хоче. Ситуація ускладнюється тим, що Тесса випадково зустріла свого батька, якого не бачила від самого дитинства.

Драма «Після падіння» є продовженням фільмів «Після» та «Після сварки», знятих у 2019 та 2020 роках. У основі сюжету усіх фільмів – однойменні книжки американської письменниці Анни Тодд. Ці романи перекладені на понад 30 мов світу. Кожна з перших двох частин коштувала творцям по $14 млн., а загальні касові збори склали понад $117 млн. Попередні фільми направляли різні режисери. Цього разу запросили режисерку Кастіль Лендон. Через пандемію знімальний процес вирішили перенести до Болгарії, яка на той момент була безпечнішою за Атланту, де працювали над першими двома фільмами. У Софії відзняли матеріал і для наступної, заключної частини франшизи.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (14, 68, 1, 'After the fall (16+)', 'In Seattle, Tessa (Josephine Langford) has a job she has long dreamed of. The girl would not hesitate to move there, if not for Hardin (Hiro Fiennes-Tiffin), who does not even want to hear anything about this city. The situation is complicated by the fact that Tessa accidentally met her father, whom she had not seen since childhood.

The drama "After the Fall" is a sequel to the films "After" and "After the Quarrel", shot in 2019 and 2020. The plot of all films is based on the books of the same name by the American writer Anna Todd. These novels have been translated into more than 30 languages. Each of the first two parts cost the creators $ 14 million, and the total box office was over $ 117 million. Previous films were directed by different directors. This time the director Castil Landon was invited. Due to the pandemic, they decided to move the filming process to Bulgaria, which at that time was safer than Atlanta, where they were working on the first two films. The material for the next, final part of the franchise was filmed in Sofia.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (15, 6, 2, 'Навколо світу за 80 днів (0+)', 'Мавпеня Паспарту завжди мріяв про пригоди. Проте усе життя змушений слухатися матусю, яка не випускає його далі своєї кімнати. На щастя, він зустрічає ексцентричного та непосидючого Філеаса. Він жаба і він нічого не боїться. Ці двоє б’ються об заклад, що їм вдасться здійснити навколосвітню подорож за 80 днів. Захоплююча пригода не така вже й безпечна. Адже їм доведеться перетинати небезпечні ліси та безкраї океани. Добре, що обоє героїв доволі винахідливі, аби уникати халеп в останню хвилину.

Анімаційний фільм «Навколо світу за 80 днів» – це новий погляд французько-бельгійської команди мультиплікаторів на всесвітньовідомий твір. Режисером проекту став Самуель Турно. Сценарій належить Джері Суоллоу. Жюль Верн написав однойменний роман 1873 року. Відтоді з’явилось 10 повнометражних адаптацій та 4 серіали. Сюжет книги підбурив людей повторювати маршрут героїв – було щонайменше 9 спроб, більшість з них успішні.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (16, 6, 1, 'Around the world in 80 days (0+)', 'Monkey Passepartout has always dreamed of adventure. However, all his life he is forced to obey his mother, who does not let him out of her room. Fortunately, he meets the eccentric and restless Phileas. He is a frog and he is not afraid of anything. The two are betting that they will be able to travel around the world in 80 days. An exciting adventure is not so safe. After all, they will have to cross dangerous forests and endless oceans. It''s good that both characters are resourceful enough to avoid last-minute trouble.

The animated film "Around the World in 80 Days" is a new view of the French-Belgian team of animators on the world-famous work. The project was directed by Samuel Turno. The screenplay is by Jerry Swallow. Jules Verne wrote a novel of the same name in 1873. Since then, 10 full-length adaptations and 4 series have appeared. The plot of the book encouraged people to repeat the route of the heroes - there were at least 9 attempts, most of them successful.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (17, 7, 2, 'Погані домогосподарки (16+)', 'Чим ще займатися звичайним домогосподаркам, як не вишукувати акції та знижки у супермаркетах? Але справжнє везіння – мати у гаманці замість грошей купони, якими можна розплатитися на касі. Для когось це копійки. А от Конні (Крістен Белл) та Джоджо (Кірбі Хауелл-Баптист) думають інакше. Підприємливі жіночки вирішили не сидіти склавши руки та зробили все, аби мати повні кишені таких купонів. Більше того, вони охоче ділилися ними з усіма охочими. Звісно ж, за грошову винагороду. Зупинити подруг не так то легко, а допоки поліція збивається з ніг розслідуючи справу, компанії несуть мільярдні збитки.

Комедія «Погані домогосподарки», за словами її творців, заснована на реальних подіях. Сценарієм та режисурою займалися удвох Арон Годет та Гіта Пуллапіллі. Усі свої фільми вони знімають разом. Бен Стіллер став виконавчим продюсером стрічки. Про початок робіт оголосили навесні 2019 року, а основні зйомки тривали починаючи з жовтня 2020 року.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (18, 7, 1, 'Bad housewives (16+)', 'What else can ordinary housewives do, how not to look for promotions and discounts in supermarkets? But the real luck is to have coupons in your wallet instead of money, which you can use to pay at the box office. For some it''s a penny. But Connie (Kristen Bell) and Jojo (Kirby Howell-Baptist) think differently. Enterprising women decided not to sit idly by and did everything to have pockets full of such coupons. Moreover, they willingly shared them with everyone. Of course, for a monetary reward. Stopping girlfriends is not so easy, and as long as the police get off their feet investigating the case, companies incur billions in losses.

The comedy "Bad Housewives", according to its creators, is based on real events. Aron Godet and Gita Pullapilli co-wrote and directed. They make all their films together. Ben Stiller became the film''s executive producer. The start of work was announced in the spring of 2019, and the main filming has been going on since October 2020.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (19, 8, 2, 'Небесний замок Лапута (0+)', '«Небесний замок Лапута» став першим фільмом створеної у 1985 році студії Джіблі. Його було завершено лише за 10 днів до початку прокату. Сьогодні його статус — один із класичних шедеврів Міядзакі. Без цього фільму не можна уявити жоден топ найкращих аніме в історії. «Небесний замок Лапута» цитується у десятках фільмів та вважається однією з найвпливовіших для стилістики стімпанку робіт 80-х.

Падзу мешкає в шахтарському селищі. Одного дня на нього з неба падає Шііта, яка зірвалась з дирижабля у спробі втекти від розбійників. Вони обоє мають зв’язок із міфічним летючим островом Лапута. Їхня дивовижна зустріч між небом і землею покладе початок великій пригоді з пошуків цієї напівзабутої легенди — від давніх закинутих шахт до хмар, сповнених повітряних піратів, дирижаблів та небачених скарбів.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (20, 8, 1, 'Laputa Heavenly Castle (0+)', 'Laputa''s Heavenly Castle was the first film by Gibby''s 1985 studio. It was completed only 10 days before the rental. Today, its status is one of Miyazaki''s classic masterpieces. Without this film, you can not imagine any of the top best anime in history. "Laputa''s Heavenly Castle" is quoted in dozens of films and is considered one of the most influential for the style of steampunk works of the 80''s.

Padzu lives in a mining village. One day, a Shiite falls from the sky on him, who fell from the airship in an attempt to escape from the robbers. They both have a connection to the mythical flying island of Laputa. Their amazing encounter between heaven and earth will mark the beginning of a great adventure in the search for this half-forgotten legend - from ancient abandoned mines to clouds full of pirates, airships and unseen treasures.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (21, 9, 2, 'У пастці (18+)', 'Емілі (Меган Фокс) опинилася у ситуації, з якої вона вбачає один єдиний вихід – вижити за будь яку ціну. Її чоловіка вбили ті, хто дуже хотів йому помститися. Власне, кілерів найняли аби ті вбили подружжя удвох. Проте сталося так, що Емілі вижила. Тепер вбивці коханого прагнуть і її смерті, і вони вже в дорозі. Найскладніше у цій ситуації те, що Емілі не може вільно рухатися. Дівчину прикули кайданками до тіла вбитого чоловіка. У кого ж бажання помсти виявиться сильнішим – у найманих вбивць чи у вдови, поряд з якою понівечене тіло чоловіка.

Трилер та жахи «У пастці» зняв режисер-дебютант С.К. Дейл, за сценарієм досвідченішого Джейсона Карві, за плечима якого участь у 6 проектах. На головну роль запросили зірку «Трансформерів» Меган Фокс. Про старт робіт оголосили у лютому 2020 року. Тоді планувалося, що зможуть одразу приступити до зйомок, проте їх довелося переносити через пандемію. Фільмувати почали лише у серпні 2020 року у Софії, Болгарія.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (22, 9, 1, 'Trapped (18+)', 'Emily (Megan Fox) finds herself in a situation from which she sees only one way out - to survive at any cost. Her husband was killed by those who really wanted revenge on him. In fact, the killers were hired to kill the couple together. However, it so happened that Emily survived. Now the murderers of the beloved seek her death, and they are on their way. The hardest part about this situation is that Emily can''t move freely. The girl was handcuffed to the body of the murdered man. Who will have a stronger desire for revenge - the hired killers or the widow, next to whom the man''s body is mutilated.

The thriller and horrors "In the Trap" was shot by debutant director SK Dale, written by the more experienced Jason Carvey, who has participated in 6 projects. Transformers star Megan Fox was invited to star. The start of work was announced in February 2020. It was then planned that they would be able to start filming immediately, but they had to be postponed due to the pandemic. Filming began only in August 2020 in Sofia, Bulgaria.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (176, 103, 1, 'Dune (12+)', 'Events unfold in the distant future. Duke Leto Atreides (Oscar Isaac) takes control of the desert planet Arrakis, the only source of the rarest and most valuable substance in the universe. This substance, acting like a drug, prolongs human life and significantly improves mental abilities. Possession of matter guarantees dominance in the universe. However, now the planet Arrakis is filled with giant sand worms. The duke''s son, the young and talented Paul (Timothy Shalame), must travel to the terrible planet to ensure that his family rules it.

The fantastic adventure drama "Dune" will be the third film adaptation of the work of the same name by American writer Frank Herbert. The novel was published in 1965. In all, Herbert has written six books on a common theme. In 1984, a feature film was made, and in 2000 - a miniseries. Work on a new film adaptation began in 2008. The main filming process took place in 2019 in Hungary and Jordan.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (177, 103, 2, 'Дюна (12+)', 'Події розгортаються у далекому майбутньому. Герцог Лето Атрейдес (Оскар Айзек) бере під своє управління пустельну планету Арракіс, єдине джерело найбільш рідкісної та цінної речовини у всесвіті. Ця речовина, діючи подібно наркотикам, подовжує життя людини та суттєво покращує розумові здібності. Володіння речовиною гарантує панування у всесвіті. Проте наразі планета Арракіс наповнена велетенськими піщаними червами. Син герцога, юний та талановитий Пол (Тімоті Шаламе), мусить відправитися на страшну планету, аби забезпечити своїй родині володарювання нею.

Фантастична пригодницька драма «Дюна» стане третьою за рахунком екранізацією однойменного твору американського письменника Френка Герберта. Роман вийшов друком у 1965 році. Загалом Герберт написав шість книг, об’єднаних спільною тематикою. У 1984 році зняли повнометражний фільм, а у 2000-му – мінісеріал. Працювати над новою екранізацією почали ще в 2008 році. Основний знімальний процес тривав у 2019 році в Угорщині та Іорданії.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (178, 106, 1, 'The Eternals', 'For more than 7,000 years, there has been an alien race created by the celestials. They are immortal and it is they who must protect humanity from their aggressive counterparts, who also have supernatural powers.

Fantastic Action "Eternal" is a new film based on Marvel comics. The film is set to become the 25th in the Marvel movie universe. The film will enter the fourth phase of the franchise. The debut of the characters in the pages of comics took place in 1976. Their appearance is explained as an alternative branch of evolution, and humans are a primitive variant of this race. Kevin Faigy has announced the start of work on the project in 2018. Hollywood stars of the first magnitude, including Angelina Jolie, Salma Hayek, Gemma Chan and Keith Harrington, were invited to film. The director of the upcoming film was confirmed to be Chinese Chloe Zhao. The main shooting began in the summer of 2019 at film studios in the UK and the Canary Islands.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (179, 106, 2, 'Вічні', 'Понад 7000 років існує інопланетна раса, створена небожителями. Вони безсмертні і саме їм належить захистити людство від своїх агресивних співбратів, які також володіють надприродною силою.

Фантастичний бойовик «Вічні» – це новий фільм, створений на основі коміксів Marvel. Стрічка має стати 25-ю за рахунком у кіновсесвіті Marvel. Фільм увійде до четвертої фази франшизи. Дебют персонажів на сторінках коміксів відбувся у 1976 році. Їх поява пояснюється як альтернативна гілка еволюції, а люди є примітивним варіантом цієї раси. Кевін Файгі оголосив про початок роботи над проектом у 2018 році. Для зйомок було запрошено голлівудських зірок першої величини, серед яких Анджеліна Джолі, Сальма Хайек, Джемма Чан та Кіт Гарінґтон. Режисеркою майбутнього фільму затвердили китаянку Хлою Чжао. Основні зйомки почалися влітку 2019 року на кіностудіях Великобританії, а також Канарських островах.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (180, 104, 1, 'Yakari, le film', 'Let''s meet Yakari, an inquisitive boy from the Lakota Indian tribe. He dreams of conquering the fastest mustang. One day, while walking on the prairies, he saw a horse whose leg was stuck between the stones. Anchors released the animal. For his kindness, he received even more than he could have dreamed. The mustang released by him, Little Grimm, became his faithful friend. In addition, Yakari has acquired a magic pen, which gives him the gift of communication with animals.

The adventure family cartoon "Little Thunder" was co-produced by Belgium, France and Germany. The language of the original is French. The plot is based on popular in Europe comics about an Indian boy who understands the language of animals. The comics were published in 1969 and have 26 volumes. They have been translated into 20 languages. The cartoon series closest to the plot of the comics was shot from 1973 to 2020, it has 41 series. This project is the first full-length animated film about the hero.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (181, 104, 2, 'Літл Грім (0+)', 'Давайте познайомимося з Якарі – це допитливий хлопчик з індійського племені лакота. Він мріє підкорити собі найшвидшого мустанга. Одного разу, гуляючи преріями, він побачив коня, нога якого застрягла між камінням. Якарі звільнив тварину. За свою доброту він отримав навіть більше, ніж міг мріяти. Звільнений ним мустанг Літл Грім став йому вірним другом. До того ж Якарі заволодів чарівним пером, яке наділяє його даром спілкування із тваринами.

Пригодницький сімейний мультфільм «Літл Грім» зняли у копродукції Бельгія, Франція та Німеччина. Мова оригіналу – французька. У основу сюжету покладено популярні у Європі комікси про індійського хлопчика, який розуміє мову тварин. Комікси почали виходити у 1969 році та налічують 26 томів. Їх переклали на 20 мов. Найбільш близький до сюжету коміксів мультсеріал знімали з 1973 по 2020 рік, він налічує 41 серію. Даний проект є першим повнометражним анімаційним фільмом про героя.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (182, 105, 1, 'No Time to Die', 'Sir James Bond (Daniel Craig) has decided to take a break from the frantic pace of Agent 007''s life. He now enjoys peace in Jamaica. Here he is found by Felix Leiter of the CIA and tells the details of the disappearance of a scientist. Despite not wanting to take on new business, Bond agrees to help an old acquaintance.

Adventure action movie "007: No Time to Die" is the 25th film about a special agent MI-6 based on the works of Jan Fleming. Project development began in the spring of 2016. Since then, the screenwriters and directors have changed many times. Eventually, Carrie Fukunaga found himself in the director''s chair. The role of Bond was played by Daniel Craig for the fifth time, despite rumors that "007: Spectrum" will be his last Bond film. For the first time in the history of the franchise, the action movie will be filmed using IMAH digital cameras. Among the planned locations for filming are Italy, Norway, Jamaica and the United Kingdom, including the pavilions of Pinewood Studios.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (183, 105, 2, '007: Не час помирати (12+)', 'Сер Джеймс Бонд (Деніел Крейг) вирішив відпочити від скаженого ритму життя агента 007. Нині він насолоджується спокоєм на Ямайці. Тут його знаходить Фелікс Лейтер із ЦРУ та розповідає подробиці зникнення одного науковця. Попри не бажання братися за нові справи, Бонд погоджується допомогти давньому знайомому.

Пригодницький бойовик «007: Не час помирати» є 25-м за рахунком фільмом про спецагента МІ-6 за мотивами творів Яна Флемінга. Розробка проекту почалася навесні 2016 року. Відтоді неодноразово змінювалися автори сценарію та режисери. Зрештою у режисерському кріслі опинився Кері Фукунага. Роль Бонда вп’яте виконав Деніел Крейг, попри розмови, що «007: Спектр» стане для нього останнім фільмом бондіани. Вперше в історії франшизи бойовик буде знято з використанням цифрових камер ІМАХ. Серед запланованих локацій для зйомок – Італія, Норвегія, Ямайка та Великобританія, зокрема павільйони студії Pinewood Studios.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (184, 107, 1, 'Dating and New York (12+)', 'The events unfold in New York. Love can be found here at every turn, but the protagonist will soon stop believing in it. Twenty-year-old Milo (Jabuk Young-White) wants to find a girlfriend. He wants a long-term relationship, and chooses a special application for dating. One day Wendy (Francesca Reale) comes to meet him. Despite the fact that the date was quite successful and ended passionately, the girl does not want to continue the relationship. And yet they met again. Their best friends, Hank (Brian Mueller) and Jesse (Catherine Cohen), also met. The two agreed at once and became a couple. The experience of a best friend makes Milo want more of the same development for himself and Wendy.

The comedy "Dating in New York" was shot by a novice John Feingold - he wrote the script and directed. He selected more experienced actors. While working on the film, Feingold took the best of all romantic love comedies in the big city.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (185, 107, 2, 'Побачення в Нью-Йорку (12+)', 'Події розгортаються у Нью-Йорку. Кохання тут можна зустріти на кожному кроці, але головний герой скоро перестане в це вірити. Двадцятирічний Майло (Джабук Янг-Уайт) хоче знайти собі дівчину. Він прагне тривалих відносин, а для знайомств обирає спеціальний додаток. Якось на зустріч до нього приходить Венді (Франческа Реале). Попри те, що побачення пройшло доволі вдало та закінчилось пристрасно, дівчина не хоче продовження стосунків. І все ж таки вони зустрілись ще раз. Познайомилися і їхні кращі друзі – Хенк (Брайан Мюллер) та Джессі (Катрін Коен). Ці двоє порозумілися одразу та стали парою. Досвід кращого друга змушує Майло сильніше хотіти такого ж розвитку подій і для себе з Венді.

Комедію «Побачення в Нью-Йорку» зняв початківець Джона Фейнголд – сам написав сценарій та зайнявся режисурою. Акторів добирав більш досвідчених. Працюючи над фільмом Фейнголд взяв найкраще з усіх романтичних комедій про кохання у великому місті.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (186, 108, 1, 'Prisoners of the Ghostland (16+)', 'The events unfold in the surreal future. A bank robber (Nicholas Cage) staged a bloody massacre at the crime scene. He was imprisoned in the Samurai City Prison. The local governor (Bill Moseley) is furious over the abduction of his beloved granddaughter (Sophia Butella). The girl got to the cruel and obsessive inhabitants of the Land of Ghosts. In the middle of the desert live people who are terrified of the passage of time - even a second hand on the clock should never make a full turn. That''s where the protagonist has to go and release the captive girl. To prevent the man from escaping, the governor puts on a leather suit equipped with a watch and explosives. If 5 days pass and the governor does not see his grandson, the hero will explode.

The fantastic action movie "Prisoner of Ghosts" was shot by the outrageous Japanese director Zion Sono. Filming began in Mexico, but was transferred to Japan due to Sono''s disease. For Nicholas Cage, who starred in it, it''s 229 career films.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (187, 108, 2, 'В''язні Країни привидів (16+)', 'Події розгортаються в сюрреалістичному майбутньому. Грабіжник банків (Ніколас Кейдж) влаштував криваву бійню на місці злочину. Його ув’язнили у тюрмі Міста Самураїв. Місцевий губернатор (Білл Моуслі) лютує через те, що викрадено його улюблену онуку (Софія Бутелла). Дівчина потрапила до жорстоких та навіжених жителів Країни приведів. Посеред пустелі мешкають люди, які панічно бояться плину часу – навіть секундна стрілка на годиннику ніколи не має звершити повний оберт. Саме туди має вирушити головний герой та звільнити полонену дівчину. Аби чоловік не втік, губернатор одягає на нього шкіряний костюм, обладнаний годинником та вибухівкою. Якщо сплине 5 днів, а губернатор не побачить онуку, герой вибухне.

Фантастичний бойовик «В''язні Країни привидів» зняв епатажний японський режисер Сіон Соно. Зйомки розпочалися у Мексиці, проте були перенесені до Японії через хворобу Соно. Для Ніколаса Кейджа, який виконав головну роль, це 229 фільм у кар’єрі.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (188, 109, 1, 'Family Swap (16+)', 'The large and not always friendly Morel family suffered day by day from the fact that no one understood anyone. The husband and wife focused only on their problems, the teenage children are convinced that no one understands them, and 6-year-old Chacha rightly believes that she has been forgotten at all. One morning they wake up and realize that everyone is not in their body. The youngest child is in the body of the father, the father in the body of the teenage son, and he is in the body of the older sister. The older sister is in the mother, and the mother is in the body of the child. If you do not understand anything - do not be afraid. They also did not understand anything, but they still have to live with all this. It''s time to experience all the problems of your family members that you didn''t notice before.

The French comedy Let''s Wave Bodies is the brainchild of Jean-Patrick Benes. For him, this is the ninth screenplay and the sixth directorial work. Most of the filming took place in Dijon in October 2019. Several scenes were shot in Dunkirk. The film''s budget was about 10 million euros.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (189, 109, 2, 'Махнемось тілами (16+)', 'Велика та не завжди дружна родина Морелів день у день потерпала від того, що ніхто нікого не розуміє. Чоловік та дружина зосередились лише на своїх проблемах, діти-підлітки переконані, що ніхто їх не розуміє, а 6-річка Чача небезпідставно вважає, що про неї взагалі забули. Одного ранку вони прокидаються і розуміють, що кожен знаходиться не у своєму тілі. Наймолодша дитина у тілі тата, тато у тілі сина-підлітка, а той – у старшій сестрі. Старша сестра у мамі, а мама у тілі дитини. Якщо ти нічого не зрозумів – не лякайся. Вони також нічого не зрозуміли, але їм з усім цим ще й жити. Настав час на собі відчути усі ті проблеми членів своєї родини, які ти до того не помічав.

Французька комедія «Махнемось тілами» є дітищем Жан-Патріка Бенеса. Для нього це дев’ятий сценарій та шоста режисерська робота. Більшість зйомок проходили у місті Діжон у жовтні 2019 року. Кілька сцен зняли у Дюнкерку. Бюджет фільму склав близько 10 млн. євро.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (190, 110, 1, 'The Card Counter (16+)', 'A former acquaintance of Sirk (Ty Sheridan) addresses a former military and now a skilled card player William Tell (Oscar Isaac). In his actions he is guided only by the thirst for revenge. He seeks to harm Major John Gordo (Willem Dafoe), an old mutual acquaintance. Sirk is blind in his desire, so Tell tries to calm his impulses. However, it will not be possible to completely avoid participating in what the guy has planned.

The action movie and thriller "Cold Calculation" were shot jointly by filmmakers from Great Britain, China and the United States. The screenwriter and director of the film was Paul Schreider, and one of the executive producers was Martin Scorsese. Stars of the first magnitude were invited to play the leading roles. Each of the actors has received awards from leading international film festivals for their skills. Filming was scheduled for February 2020 in Biloxi, Mississippi. COVID-19 was detected in one of the minor actors 5 days before their completion. The rest of the scenes were filmed in July of the same year.');
INSERT INTO cinema.film_description (id, film_id, language_id, name, description) VALUES (191, 110, 2, 'Холодний розрахунок (16+)', 'До колишнього військового, а нині майстерного гравця у карти Вільяма Телла (Оскар Айзек) звертається давній знайомий Сірк (Тай Шерідан). У своїх діях він керується лише жагою помсти. Він прагне завдати шкоди майору Джону Гордо (Віллем Дефо), давньому спільному знайомому. Сірк сліпий у своєму бажанні, тож Телл намагається вгамувати його пориви. Однак, повністю уникнути участі у тому, що задумав хлопець, не вийде.

Бойовик та трилер «Холодний розрахунок» знято спільно кінематографістами із Великобританії, Китаю та США. Автором сценарію та режисером картини став Пол Шрейдер, а одним з виконавчих продюсерів – Мартін Скорсезе. Для виконання головних ролей запросили зірок першої величини. Кожен з акторів має нагороди провідних міжнародних кінофестивалів за свою майстерність. Зйомки фільму були заплановані на лютий 2020 року у місті Білоксі, штат Міссісіпі. За 5 днів до їх завершення у одного з другорядних акторів було виявлено COVID-19. Дознімали решту сцен аж у липні того ж року.');

-- INSERT user
INSERT INTO cinema.user (id, name, email, password, role, date) VALUES (1, 'ivanov', '111@111.com', '698d51a19d8a121ce581499d7b701668', 'user', '2021-09-10 20:05:55');
INSERT INTO cinema.user (id, name, email, password, role, date) VALUES (2, 'vasilisa', 'vas@vas.com', '233692e0aad5a445107564ca1bb68d51', 'admin', '2021-09-10 20:05:55');
INSERT INTO cinema.user (id, name, email, password, role, date) VALUES (23, 'Новий користувач', 'vaiman.vasilisa@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', 'user', '2021-09-25 19:57:21');
INSERT INTO cinema.user (id, name, email, password, role, date) VALUES (24, 'admin', 'admin@example.com', '21232f297a57a5a743894a0e4a801fc3', 'admin', '2021-10-07 20:14:00');

-- INSERT seance
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (68, 2, '2021-09-18 09:00:00', 1, 100, 95);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (72, 68, '2021-09-01 09:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (73, 68, '2021-09-18 18:00:00', 1, 150, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (74, 4, '2021-09-18 19:00:00', 1, 122, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (75, 4, '2021-09-18 20:00:00', 1, 122, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (76, 4, '2021-09-18 21:00:00', 1, 222, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (77, 70, '2021-09-18 22:00:00', 1, 111, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (78, 70, '2021-09-18 20:00:00', 1, 111, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (79, 66, '2021-09-18 21:00:00', 1, 111, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (80, 66, '2021-09-18 21:00:00', 1, 111, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (81, 4, '2021-09-19 16:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (82, 4, '2021-09-19 17:00:00', 1, 100, 111);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (83, 7, '2021-09-19 17:00:00', 1, 120, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (84, 7, '2021-09-19 18:00:00', 1, 120, 116);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (85, 7, '2021-09-20 09:00:00', 1, 111, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (86, 68, '2021-09-20 12:00:00', 1, 111, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (87, 4, '2021-09-21 21:00:00', 1, 111, 110);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (89, 2, '2021-09-22 11:00:00', 1, 111, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (90, 6, '2021-09-22 13:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (91, 4, '2021-09-22 16:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (92, 4, '2021-09-22 18:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (93, 8, '2021-09-22 20:00:00', 1, 150, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (94, 7, '2021-09-22 22:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (95, 8, '2021-09-21 20:00:00', 1, 100, 113);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (96, 3, '2021-09-21 23:00:00', 1, 150, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (97, 105, '2021-10-04 14:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (98, 105, '2021-10-04 18:00:00', 1, 120, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (99, 105, '2021-10-05 09:00:00', 1, 90, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (100, 105, '2021-10-03 19:00:00', 1, 130, 116);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (101, 104, '2021-10-04 16:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (102, 104, '2021-10-06 11:00:00', 1, 90, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (103, 104, '2021-10-09 09:00:00', 1, 90, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (104, 2, '2021-10-04 20:00:00', 1, 110, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (105, 103, '2021-10-05 20:00:00', 1, 120, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (106, 103, '2021-10-06 20:00:00', 1, 120, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (107, 103, '2021-10-11 21:00:00', 1, 120, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (108, 66, '2021-10-07 20:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (109, 66, '2021-10-08 20:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (110, 107, '2021-10-09 18:00:00', 1, 80, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (111, 103, '2021-10-10 20:00:00', 1, 150, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (112, 103, '2021-10-15 21:00:00', 1, 150, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (113, 103, '2021-10-22 18:00:00', 1, 150, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (114, 103, '2021-10-10 15:00:00', 1, 110, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (115, 68, '2021-10-12 18:00:00', 1, 100, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (116, 68, '2021-10-23 18:00:00', 1, 95, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (117, 68, '2021-10-25 18:00:00', 1, 95, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (118, 108, '2021-10-18 18:00:00', 1, 85, 120);
INSERT INTO cinema.seance (id, film_id, date, hall_id, price, free_seats) VALUES (119, 4, '2021-10-05 18:00:00', 1, 90, 120);

-- INSERT order
INSERT INTO cinema.`order` (id, user_id, seance_id, price, date) VALUES (57, 2, 84, 480, '2021-09-19 12:59:51');
INSERT INTO cinema.`order` (id, user_id, seance_id, price, date) VALUES (58, 2, 82, 900, '2021-09-19 13:09:35');
INSERT INTO cinema.`order` (id, user_id, seance_id, price, date) VALUES (59, 2, 87, 666, '2021-09-21 19:25:37');
INSERT INTO cinema.`order` (id, user_id, seance_id, price, date) VALUES (60, 2, 87, 444, '2021-09-21 19:27:28');
INSERT INTO cinema.`order` (id, user_id, seance_id, price, date) VALUES (61, 2, 95, 400, '2021-09-21 19:31:25');
INSERT INTO cinema.`order` (id, user_id, seance_id, price, date) VALUES (62, 2, 95, 300, '2021-09-21 19:41:17');
INSERT INTO cinema.`order` (id, user_id, seance_id, price, date) VALUES (63, 2, 100, 520, '2021-10-03 12:54:21');

-- INSERT order_item
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (96, 57, 5, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (97, 57, 7, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (98, 57, 5, 4);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (99, 57, 7, 4);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (100, 58, 1, 1);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (101, 58, 2, 1);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (102, 58, 3, 1);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (103, 58, 4, 1);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (104, 58, 5, 1);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (105, 58, 1, 2);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (106, 58, 2, 2);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (107, 58, 4, 2);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (108, 58, 5, 2);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (109, 59, 4, 2);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (110, 59, 5, 2);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (111, 59, 6, 2);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (112, 59, 4, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (113, 59, 5, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (114, 59, 6, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (115, 60, 7, 5);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (116, 60, 8, 5);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (117, 60, 7, 6);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (118, 60, 8, 6);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (119, 61, 4, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (120, 61, 5, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (121, 61, 6, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (122, 61, 7, 3);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (123, 62, 4, 4);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (124, 62, 5, 4);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (125, 62, 6, 4);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (126, 63, 6, 4);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (127, 63, 7, 4);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (128, 63, 6, 5);
INSERT INTO cinema.order_item (id, order_id, seat_number, `row_number`) VALUES (129, 63, 7, 5);

-- INSERT password_recovery
INSERT INTO cinema.password_recovery (id, user_id, token, expiration_date) VALUES (1, 23, 'a33498f402f502d458ac1a7d96874c91', '2021-09-26 21:50:31');
INSERT INTO cinema.password_recovery (id, user_id, token, expiration_date) VALUES (3, 23, '9559f0e6-bdfe-457b-b0b0-ec6e78426498', '2021-09-26 22:26:07');