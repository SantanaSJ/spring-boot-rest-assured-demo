# INSERT INTO `users` (id, name, password)
# VALUES (1, 'Silvia Santana', 'testtest'),
#        (2, 'Gosho', 'testtest'),
#        (3, 'Pesho', 'testtest'),
#        (4, 'Tosho', 'testtest'),
#        (5, 'Teo', 'testtest');
#
# INSERT INTO `roles` (id, role)
# VALUES (1, 'USER'),
#        (2, 'ADMIN');
#
# INSERT INTO `users_roles` (users_id, roles_id)
# VALUES (1, 1),
#        (1, 2),
#        (2, 1),
#        (3, 1),
#        (4, 1);
#
# INSERT INTO `artists` (id, description, name)
# VALUES
#     (1, 'David Robert Jones (8 January 1947 – 10 January 2016), known professionally as David Bowie (/ˈboʊi/ BOH-ee), was an English singer-songwriter and actor. A leading figure in the music industry, he is regarded as one of the most influential musicians of the 20th century. Bowie was acclaimed by critics and musicians, particularly for his innovative work during the 1970s. His career was marked by reinvention and visual presentation, and his music and stagecraft had a significant impact on popular music.', 'David Bowie'), (2, 'Lewis Allan Reed (March 2, 1942 – October 27, 2013) was an American musician, singer, songwriter, and poet. He was the guitarist, singer, and principal songwriter for the rock band the Velvet Underground and had a solo career that spanned five decades. Although not commercially successful during its existence, the Velvet Underground became regarded as one of the most influential bands in the history of underground and alternative rock music. Reed''s distinctive deadpan voice, poetic and transgressive lyrics, and experimental guitar playing were trademarks throughout his long career.', 'Lou Reed'), (3, 'Deep Purple are an English rock band formed in London in 1968.[3] They are considered to be among the pioneers of heavy metal and modern hard rock, but their musical approach has changed over the years. Originally formed as a psychedelic rock and progressive rock band, they shifted to a heavier sound with their 1970 album Deep Purple in Rock. Deep Purple, together with Led Zeppelin and Black Sabbath, have been referred to as the "unholy trinity of British hard rock and heavy metal in the early to mid- seventies".They were listed in the 1975 Guinness Book of World Records as "the globe''s loudest band" for a 1972 concert at London''s Rainbow Theatre and have sold over 100 million albums worldwide.', 'Deep Purple');
#
# INSERT INTO `albums` (id, album_name, description, artist_id)
# VALUES (1, 'Aladdin Sane',
#         'Aladdin Sane is the sixth studio album by English musician David Bowie, released on 13 April 1973 through RCA Records. The follow-up to his breakthrough The Rise and Fall of Ziggy Stardust and the Spiders from Mars (1972), it was the first album he wrote and released from a position of stardom. It was produced by Bowie and Ken Scott and features contributions from Bowie''s backing band the Spiders from Mars — Mick Ronson, Trevor Bolder and Mick Woodmansey — as well as pianist Mike Garson, two saxophonists and three backing vocalists. Recorded at Trident Studios in London and RCA Studios in New York City between legs of the Ziggy Stardust Tour, the record was Bowie''s final album with the full Spiders lineup.',
#         1),
#        (2, 'The Man Who Sold the World',
#         'The Man Who Sold the World is the third studio album by English musician David Bowie. It was originally released through Mercury Records in the United States on 4 November 1970 and in the United Kingdom on 10 April 1971. The album was produced by Tony Visconti and recorded at Trident and Advision Studios in London during April and May 1970. It features the first appearances of guitarist Mick Ronson and drummer Mick Woodmansey on a Bowie record, who would later become famous as members of the Spiders from Mars.',
#         1),
#        (3, 'Transformer',
#         'Transformer is the second solo studio album by American recording artist Lou Reed. Produced by David Bowie and Mick Ronson, the album was released in November 1972 by RCA Records. It is considered an influential landmark of the glam rock genre, anchored by Reed''s most successful single, "Walk on the Wild Side", which touched on then-controversial topics of sexual orientation, gender identity, prostitution, and drug use. Though Reed''s self-titled debut solo album had been unsuccessful, Bowie had been an early fan of Reed''s former band The Velvet Underground, and used his own fame to promote Reed, who had not yet achieved mainstream success.',
#         2),
#        (4, 'Stormbringer',
#         'Stormbringer is the ninth studio album by the English rock band Deep Purple, released in November 1974 and the second studio album to feature the Mk III lineup including vocalist David Coverdale and bassist/vocalist Glenn Hughes.',
#         3);
#
# INSERT INTO `albums_users` (users_id, vinyls_id)
# VALUES (1, 1),
#        (1, 2),
#        (1, 3),
#        (1, 4),
#        (2, 4),
#        (2, 3),
#        (3, 1),
#        (3, 2),
#        (3, 4),
#        (4, 4),
#        (4, 1);
#
