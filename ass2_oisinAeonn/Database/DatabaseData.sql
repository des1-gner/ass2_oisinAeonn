INSERT INTO users (username, firstName, lastName, password, userType, profilePicture) VALUES
    ('3827F2', 'John', 'Doe', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin', 'assets/image1'),
    ('SD2C45', 'Jane', 'Smith', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'VIP', 'assets/image2'),
    ('A567VF', 'Alan', 'Turing', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'VIP', 'assets/image3'),
    ('1258XE', 'Ada', 'Lovelace', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'standard', 'assets/image4'),
    ('38726I', 'Grace', 'Hopper', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'standard', 'assets/image5');

INSERT INTO posts (content, author, likes, shares, dateTime, image) VALUES
    ('Are we into Christmas month already?!', '3827F2', 526, 25, '15/11/2022 23:30', 'assets/image1'),
    ('Come and meet us at Building 14 of RMIT.', 'SD2C45', 10, 24, '12/05/2023 10:10', 'assets/image2'),
    ('Check out this epic film.', 'A567VF', 1000, 1587, '01/06/2023 14:25', 'assets/image3'),
    ('Fantastic day today. Congratulations to all winners.', '1258XE', 230, 1214, '06/06/2023 21:00', 'assets/image4'),
    ('What a miracle!', '38726I', 2775, 13589, '12/02/2023 18:18', 'assets/image5');
