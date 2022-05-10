-- 책 MariaDB 유저 생성하고 권한 주기 쿼리
CREATE USER 'bootuser'@'localhost' IDENTIFIED BY 'bootuser';
GRANT ALL PRIVILEGES ON bootex.* TO 'bootuser'@'localhost';