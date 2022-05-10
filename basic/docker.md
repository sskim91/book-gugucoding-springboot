## mariadb docker

- docker 이미지 다운로드
- 특정 버전 명시하지 않았을 시 최신버전으로 다운로드 ex) mariadb:{version}
```shell
docker pull mariadb

특정버전 명시
docker pull mariadb:10.4
```

- 다운받은 이미지 확인 명령어
```shell
docker images
```

- docker 실행
```shell
docker run --name mariadb-container \
-e MARIADB_ROOT_PASSWORD={password} \
-d \
-p 3306:3306 mariadb:latest \
--lower_case_table_names=1
```

- docker 컨테이너 bash 접속
```shell
docker exec -it {container-name} bash

여기선 위에 설정해준 mariadb-container가 container-name이 된다.
```

## 참고
[mariadb docker](https://hub.docker.com/_/mariadb)

[14.4 Docker를 사용하여 MySQL 설치하고 접속하기](https://poiemaweb.com/docker-mysql)

[[Database] Docker 로 MariaDB 설치하기](https://wecandev.tistory.com/63)

[lower_case_table_names 관련 공식문서](https://dev.mysql.com/doc/refman/8.0/en/identifier-case-sensitivity.html)